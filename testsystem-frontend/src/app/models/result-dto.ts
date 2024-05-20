import {TestDto} from "./test-dto";
import {UserDto} from "./user-dto";
import {AssessmentDto} from "./assessment-dto";

export interface ResultDto {
  id: number;
  score: number;
  acceptResult: boolean;
  user: UserDto;
  test: TestDto;
  assessment: AssessmentDto | null;
}
