import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {ResultDto} from "../models/result-dto";
import {TestDto} from "../models/test-dto";
import {TestWithQuestionDto} from "../models/test-with-question-dto";
import {QuestionWithOptionDto} from "../models/question-with-option-dto";
import {CreateResultDto} from "../models/create-result-dto";
import {AssessmentDto} from "../models/assessment-dto";
import {CreateAnswerDto} from "../models/create-answer-dto";
import {AnswerDto} from "../models/answer-dto";

const API = '/api/v1/test-system';
const HOST = environment.apiUrl;

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class TestSystemService {

  constructor(private http: HttpClient) { }

  getTests() {
    return this.http.get<Array<TestDto>>(HOST + API + '/tests');
  }

  getTestById(id: number) {
    return this.http.get<TestWithQuestionDto>(HOST + API + '/test' + '/' + id);
  }

  getQuestionById(id: number) {
    return this.http.get<QuestionWithOptionDto>(HOST + API + '/question' + '/' + id);
  }

  createResult(result: CreateResultDto) {
    return this.http.post<ResultDto>(HOST + API + '/create/result', result, httpOptions);
  }

  getAssessmentByTestIdAndScore(testId: number, score: number) {
    return this.http.get<AssessmentDto>(HOST + API + '/assessment' + '/' + testId + '/' + score, httpOptions);
  }

  acceptResult(resultId: number, accept: boolean) {
    return this.http.get<any>(HOST + API + '/accept/result' + '/' + resultId + '/' + accept);
  }

  createAnswers(answers: Array<CreateAnswerDto>) {
    return this.http.post<Array<AnswerDto>>(HOST + API + '/create/answers', answers, httpOptions);
  }

  getAnswersByResultId(resultId: number) {
    return this.http.get<Array<AnswerDto>>(HOST + API + '/answers/result' + '/' + resultId, httpOptions);
  }
}
