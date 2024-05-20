export interface RegistrationUserDto {
  name: string;
  login: string;
  password: string;
  email: string;
  birthday: any; // Дата в формате 'yyyy-MM-dd'
  startWork: any; // Дата в формате 'yyyy-MM-dd'
  workNumber: number;
}
