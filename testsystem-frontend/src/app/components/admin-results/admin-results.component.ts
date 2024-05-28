import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {UserService} from "../../services/user.service";
import {MatDialog, MatDialogModule} from "@angular/material/dialog";
import {MatSort, MatSortModule} from "@angular/material/sort";
import {MatPaginator, MatPaginatorModule} from "@angular/material/paginator";
import {MatTableDataSource, MatTableModule} from "@angular/material/table";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatListModule} from "@angular/material/list";
import {ResultDto} from "../../models/result-dto";
import {ResultFilter} from "../../models/result-filter";
import {AnswerDialogComponent} from "../answer-dialog/answer-dialog.component";
import {MatCardModule} from "@angular/material/card";
import {MatSelectModule} from "@angular/material/select";
import {NgForOf} from "@angular/common";
import {MatInputModule} from "@angular/material/input";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {UserDto} from "../../models/user-dto";
import {TestDto} from "../../models/test-dto";
import {TestSystemService} from "../../services/test-system.service";
import {MatButton} from "@angular/material/button";

@Component({
  selector: 'app-admin-results',
  standalone: true,
  imports: [MatTableModule,
    MatCardModule,
    MatFormFieldModule,
    MatSortModule,
    MatPaginatorModule,
    MatDialogModule,
    MatListModule,
    ReactiveFormsModule,
    MatSelectModule,
    MatInputModule,
    NgForOf,
    MatButton, MatCheckboxModule, FormsModule],
  templateUrl: './admin-results.component.html',
  styleUrl: './admin-results.component.css'
})
export class AdminResultsComponent implements OnInit, AfterViewInit {
  filterForm!: FormGroup;
  dataSource: MatTableDataSource<ResultDto> = new MatTableDataSource<ResultDto>();
  displayedColumns: string[] = ['testName', 'userName', 'birthday', 'startWork', 'workNumber', 'id', 'score', 'assessmentName', 'acceptResult'];
  users: Array<UserDto> | undefined;
  tests: Array<TestDto> | undefined;
  csvFile!: File;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private testService: TestSystemService,
    public dialog: MatDialog
  ) {
  }

  ngOnInit(): void {
    this.filterForm = this.fb.group({
      testId: [''],
      userId: [''],
      scoreGte: [''],
      scoreLte: [''],
      countYearWorkGte: [''],
      countYearWorkLte: [''],
      ageGte: [''],
      ageLte: [''],
      userWorkNumber: [''],
      acceptResult: ['']
    });

    this.userService.getUsers().subscribe(users => this.users = users);
    this.testService.getTests().subscribe(tests => this.tests = tests);
  }

  fetchResults(filter: ResultFilter) {
    this.userService.filterResults(filter).subscribe(results => {
      this.dataSource.data = results;
    });
  }

  ngAfterViewInit() {
    //'testName', 'userName', 'birthday', 'startWork', 'workNumber', 'resultId', 'score', 'assessmentName', 'acceptResult'
    this.dataSource.sortingDataAccessor = (item, property) => {
      switch (property) {
        case 'testName':
          return item.test.testName;
        case 'userName':
          return item.user.name;
        case 'birthday':
          return item.user.birthday;
        case 'startWork':
          return item.user.startWork;
        case 'workNumber':
          return item.user.workNumber;
        case 'assessmentName':
          return item.assessment?.assessmentName;
        default: // @ts-ignore
          return item[property];
      }
    };
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  applyFilter() {
    const filter: ResultFilter = this.filterForm.value;
    this.fetchResults(filter);
  }

  openResultDetails(result: ResultDto) {
    this.testService.getAnswersByResultId(result.id).subscribe(answers => {
      this.dialog.open(AnswerDialogComponent, {
        data: {answers}
      });
    });
  }

  onFileChange(event: any) {
    if (event.target.files.length > 0) {
      this.csvFile = event.target.files[0];
    }
  }

  uploadCsv() {
    const formData = new FormData();
    formData.append('file', this.csvFile);

    this.userService.createResultsByCSV(formData).subscribe({
      next: response => {
        console.log(response)
        alert('Файл успешно загружен');
        this.applyFilter();
      },
      error: err => {
        alert('Возникла ошибка при загрузке файла: ' + err.error);
      }
    });
  }
}
