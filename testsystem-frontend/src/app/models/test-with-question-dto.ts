import {QuestionWithOptionDto} from "./question-with-option-dto";

export interface TestWithQuestionDto {
  id: number;
  testName: string;
  description: string;
  questions: QuestionWithOptionDto[];
}
