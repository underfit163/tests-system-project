import {OptionDto} from "./option-dto";

export interface QuestionWithOptionDto {
  id: number;
  questionText: string;
  options: OptionDto[];
}
