import {Component, OnInit} from '@angular/core';
import {HttpClientModule} from "@angular/common/http";
import {MatInputModule} from "@angular/material/input";
import {FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatButtonModule} from "@angular/material/button";
import {MatCardModule} from "@angular/material/card";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatIconModule} from "@angular/material/icon";
import {MatDividerModule} from "@angular/material/divider";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {NgForOf} from "@angular/common";
import {Router} from "@angular/router";
import {CreateTestDto} from "../../models/create-test-dto";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-create-test',
  standalone: true,
  imports: [ReactiveFormsModule,
    HttpClientModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    MatIconModule,
    MatDividerModule,
    MatCheckboxModule, NgForOf],
  templateUrl: './create-test.component.html',
  styleUrl: './create-test.component.css'
})
export class CreateTestComponent  implements OnInit {
  testForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.testForm = this.fb.group({
      testName: ['', Validators.required],
      description: [''],
      questions: this.fb.array([]),
      assessments: this.fb.array([])
    });
  }

  get questions(): FormArray {
    return this.testForm.get('questions') as FormArray;
  }

  get assessments(): FormArray {
    return this.testForm.get('assessments') as FormArray;
  }

  addQuestion(): void {
    const questionGroup = this.fb.group({
      questionText: ['', Validators.required],
      options: this.fb.array([])
    });
    this.questions.push(questionGroup);
  }

  addOption(questionIndex: number): void {
    const options = this.getOptionsControls(questionIndex);
    const optionGroup = this.fb.group({
      optionText: ['', Validators.required],
      score: [0, Validators.required]
    });
    options.push(optionGroup);
  }

  getOptionsControls(questionIndex: number): FormArray {
    return this.questions.at(questionIndex).get('options') as FormArray;
  }

  addAssessment(): void {
    const assessmentGroup = this.fb.group({
      assessmentName: ['', Validators.required],
      minScore: [0, Validators.required],
      maxScore: [0, Validators.required],
      resultDescription: ['']
    });
    this.assessments.push(assessmentGroup);
  }

  removeQuestion(index: number): void {
    this.questions.removeAt(index);
  }

  removeOption(questionIndex: number, optionIndex: number): void {
    const options = this.getOptionsControls(questionIndex);
    options.removeAt(optionIndex);
  }

  removeAssessment(index: number): void {
    this.assessments.removeAt(index);
  }

  onSubmit(): void {
    if (this.testForm.valid) {
      const createTestDto: CreateTestDto = this.testForm.value;
      this.userService.createTest(createTestDto).subscribe({
        next: (response) => {
          alert('Тест создан!');
          this.router.navigate(['/tests']);
        },
        error: (err) => {
          alert('Ошибка создания теста: ' + err.error);
        }
      });
    } else {
      alert('Заполните тест корректно!');
    }
  }
}
