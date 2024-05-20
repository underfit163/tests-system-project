import { Component } from '@angular/core';
import {TestDto} from "../../models/test-dto";
import {TestSystemService} from "../../services/test-system.service";
import {MatGridList, MatGridListModule, MatGridTile} from "@angular/material/grid-list";
import {NgForOf} from "@angular/common";
import {MatCard, MatCardContent, MatCardHeader, MatCardModule} from "@angular/material/card";
import {Router} from "@angular/router";

@Component({
  selector: 'app-tests',
  standalone: true,
  imports: [
    MatGridList,
    MatGridTile,
    NgForOf,
    MatCard,
    MatCardHeader,
    MatCardContent,
    MatCardModule,
    MatGridListModule
  ],
  templateUrl: './tests.component.html',
  styleUrl: './tests.component.css'
})
export class TestsComponent {
  tests!: Array<TestDto>;

  constructor(private router: Router, private testSystemService: TestSystemService) {
  }

  ngOnInit(): void {
    this.testSystemService.getTests()
      .subscribe({
        next: (data) => {
          this.tests = data;
        },
        error: err => {
          alert("Ошибка получения данных! " + err.message);
        }
      });
  }

  navigateToTest(testId: number) {
    this.router.navigate(['/test', testId]);
  }
}
