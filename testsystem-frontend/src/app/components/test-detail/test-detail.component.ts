import {Component} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {TestSystemService} from "../../services/test-system.service";
import {TestWithQuestionDto} from "../../models/test-with-question-dto";
import {NgClass, NgForOf, NgIf} from "@angular/common";
import {MatProgressBarModule} from "@angular/material/progress-bar";
import {MatCardModule} from "@angular/material/card";
import {MatButton} from "@angular/material/button";
import {MatRadioModule} from "@angular/material/radio";
import {TokenStorageService} from "../../services/token-storage.service";
import {CreateResultDto} from "../../models/create-result-dto";
import {ResultDto} from "../../models/result-dto";
import {CreateAnswerDto} from "../../models/create-answer-dto";
import {AnswerDto} from "../../models/answer-dto";
import {MatSlideToggleModule} from "@angular/material/slide-toggle";
import {MatExpansionModule} from "@angular/material/expansion";
import {FormsModule} from "@angular/forms";

@Component({
    selector: 'app-test-detail',
    standalone: true,
    imports: [
        NgIf,
        MatProgressBarModule,
        MatCardModule,
        NgForOf,
        MatButton,
        MatRadioModule,
        MatSlideToggleModule,
        MatExpansionModule,
        FormsModule,
        NgClass
    ],
    templateUrl: './test-detail.component.html',
    styleUrl: './test-detail.component.css'
})
export class TestDetailComponent {
    testId!: number;
    userId!: number;
    selectedOptions: { [questionId: number]: number } = {};
    test!: TestWithQuestionDto;
    progress: number = 0;
    result: ResultDto | undefined;
    saveAnswers: boolean = false;
    answers: Array<AnswerDto> | undefined;

    constructor(private tokenStorageService: TokenStorageService,
                private activateRoute: ActivatedRoute,
                private testSystemService: TestSystemService,
                private router: Router) {
        activateRoute.params.subscribe(params => this.testId = params["id"]);
    }

    ngOnInit(): void {
        this.testSystemService.getTestById(this.testId)
            .subscribe({
                next: (test) => {
                    this.test = test;
                    this.updateProgress();
                },
                error: err => {
                    alert("Ошибка получения данных! " + err.message);
                }
            });
        this.userId = this.tokenStorageService.getUser().id;
    }

    onSelectOption(questionId: number, optionId: number) {
        this.selectedOptions[questionId] = optionId;
        this.updateProgress();
    }

    updateProgress() {
        const answeredQuestions = Object.keys(this.selectedOptions).length;
        const totalQuestions = this.test?.questions.length || 0;
        this.progress = (answeredQuestions / totalQuestions) * 100;
    }

    allQuestionsAnswered(): boolean {
        return this.test.questions.length === Object.keys(this.selectedOptions).length;
    }


    submitTest() {
        const result: CreateResultDto = {
            score: this.calculateScore(),
            acceptResult: null, // Initially accepting the result
            userId: this.userId,
            testId: this.testId
        };

        this.testSystemService.createResult(result).subscribe({
            next: (result) => {
                //передать данные на другую страницу для отображения и если пользователь выбрал сохранить ответы, то сохранить их
                this.result = result;
                if (this.saveAnswers) {
                    const answers: CreateAnswerDto[] = Object.keys(this.selectedOptions).map(questionId => {
                        return {
                            resultId: result.id, // Placeholder, will be set after result is created
                            optionId: this.selectedOptions[+questionId]
                        };
                    });
                    this.testSystemService.createAnswers(answers).subscribe({
                        next: (answersResponse) => {
                            this.answers = answersResponse;
                            this.navigateToResultPage();
                        },
                        error: err => {
                            alert("Ошибка получения данных! " + err.message);
                        }
                    })
                } else {
                    this.navigateToResultPage();
                }
            },
            error: err => {
                alert("Ошибка получения данных! " + err.message);
            }
        });
    }

    calculateScore(): number {
        let score = 0;
        for (const question of this.test.questions) {
            const selectedOptionId = this.selectedOptions[question.id];
            const selectedOption = question.options.find(option => option.id === selectedOptionId);
            if (selectedOption) {
                score += selectedOption.score;
            }
        }
        return score;
    }

    navigateToResultPage() {
        this.router.navigate(['test', this.testId, 'result'], {
          state: {
            result: this.result,
            answers: this.answers
          }
        }).then(r => window.scrollTo(0, 0));
    }
}
