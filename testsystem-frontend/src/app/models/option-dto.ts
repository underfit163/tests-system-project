import {QuestionDto} from "./question-dto";

export interface OptionDto {
  id: number;
  optionText: string;
  score: number;
  question: QuestionDto
}
