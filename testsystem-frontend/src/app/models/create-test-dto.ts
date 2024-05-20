import {CreateQuestionDto} from "./create-question-dto";

class CreateAssessmentDto {
}

export interface CreateTestDto {
  testName: string;
  description: string;
  questions: Array<CreateQuestionDto>;
  assessments: Array<CreateAssessmentDto>;
}
