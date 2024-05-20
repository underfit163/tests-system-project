import {TicketDto} from "./ticket-dto";

export interface UserWithTicketDto {
  id: number;
  name: string;
  login: string;
  role: string;
  email: string;
  birthday: any; // или можно использовать тип Date
  startWork: any; // или можно использовать тип Date
  workNumber: number;
  tickets: TicketDto[];
}
