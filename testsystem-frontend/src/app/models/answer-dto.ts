import {OptionDto} from "./option-dto";
import {ResultDto} from "./result-dto";

export interface AnswerDto {
  id: number;
  result: ResultDto;
  option: OptionDto;
}
