import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {ResultDto} from "../../models/result-dto";
import {UserService} from "../../services/user.service";
import {TokenStorageService} from "../../services/token-storage.service";
import {TestSystemService} from "../../services/test-system.service";
import {MatTableDataSource, MatTableModule} from "@angular/material/table";
import {MatSort, MatSortModule} from "@angular/material/sort";
import {MatPaginator, MatPaginatorModule} from "@angular/material/paginator";
import {MatDialog, MatDialogModule} from "@angular/material/dialog";
import {MatListModule} from "@angular/material/list";
import {MatButton} from "@angular/material/button";
import {MatLine} from "@angular/material/core";
import {MatFormField, MatFormFieldModule} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {NgForOf} from "@angular/common";
import {AnswerDialogComponent} from "../answer-dialog/answer-dialog.component";

@Component({
  selector: 'app-user-results',
  standalone: true,
  imports: [MatTableModule,
    MatFormFieldModule,
    MatSortModule,
    MatPaginatorModule,
    MatDialogModule,
    MatListModule, MatButton, MatLine, MatFormField, MatInput, NgForOf],
  templateUrl: './user-results.component.html',
  styleUrl: './user-results.component.css'
})
export class UserResultsComponent implements OnInit, AfterViewInit {
  displayedColumns: string[] = ['testName', 'id', 'score', 'assessmentName', 'acceptResult'];
  dataSource: MatTableDataSource<ResultDto> = new MatTableDataSource<ResultDto>();
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  userId: number | undefined;

  constructor(private tokenStorageService: TokenStorageService,
              private userService: UserService,
              private testService: TestSystemService,
              public dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.fetchUserResults();
  }

  fetchUserResults() {
    this.userId = this.tokenStorageService.getUser().id;
    this.userService.getResultsByUserId(this.userId).subscribe({
      next: (results) => {
        this.dataSource.data = results;
      },
      error: err => {
        alert("Ошибка получения данных! " + err.message);
      }
    });
  }

  ngAfterViewInit() {
    this.dataSource.filterPredicate = (data: ResultDto, filter: string): boolean => {
      const transformedFilter = filter.trim().toLowerCase();
      const dataStr = Object.keys(data)
        .reduce((currentTerm: string, key: string) => {
          let element;
          switch (key) {
            case 'acceptResult':
              element = (data[key] != null ? (data[key] ? 'да ' : 'нет ') : '');
              break;
            case 'test':
              element = data.test.testName;
              console.log(element)
              break;
            case 'assessment':
              element = data.assessment?.assessmentName;
              break;
            default: // @ts-ignore
              element = data[key];
              break;
          }
          return currentTerm + element + ' ';
        }, '').toLowerCase().trim();
      return dataStr.indexOf(transformedFilter) !== -1;
    };

    this.dataSource.sortingDataAccessor = (item, property) => {
      switch (property) {
        case 'testName':
          return item.test.testName;
        case 'assessmentName':
          return item.assessment?.assessmentName;
        default: // @ts-ignore
          return item[property];
      }
    };
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  openResultDetails(result: ResultDto) {
    this.testService.getAnswersByResultId(result.id).subscribe(answers => {
      this.dialog.open(AnswerDialogComponent, {
        data: {answers}
      });
    });
  }
}
