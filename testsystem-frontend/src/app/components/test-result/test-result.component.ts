import {Component, OnInit} from '@angular/core';
import {ResultDto} from "../../models/result-dto";
import {AnswerDto} from "../../models/answer-dto";
import {ActivatedRoute} from "@angular/router";
import {TestSystemService} from "../../services/test-system.service";
import {MatCardModule} from "@angular/material/card";
import {NgForOf, NgIf} from "@angular/common";
import {MatButton} from "@angular/material/button";

@Component({
    selector: 'app-test-result',
    standalone: true,
    imports: [MatCardModule, NgForOf, MatButton, NgIf],
    templateUrl: './test-result.component.html',
    styleUrl: './test-result.component.css'
})
export class TestResultComponent implements OnInit {
    testId!: number;
    result!: ResultDto;
    answers!: Array<AnswerDto>;

    constructor(private activateRoute: ActivatedRoute, private testSystemService: TestSystemService) {
        activateRoute.params.subscribe(params => this.testId = params["id"]);
    }

    ngOnInit() {
        if (history.state.result) {
            this.result = history.state.result;
            this.answers = history.state.answers;
        } else {
            alert('Не удалось получить данные результатов теста.');
        }
    }


    acceptResult(accept: boolean) {
        this.testSystemService.acceptResult(this.result.id, accept).subscribe({
            next: () => {
                alert(`Результат ${accept ? 'принят' : 'отклонен'}`);
            },
            error: err => {
                alert('Ошибка при обновлении принятия результата! ' + err.error);
            }
        });
    }
}
