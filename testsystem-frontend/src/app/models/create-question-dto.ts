import {CreateOptionDto} from "./create-option-dto";

export interface CreateQuestionDto {
  questionText: string;
  options: Array<CreateOptionDto>;
}
