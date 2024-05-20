import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {RegistrationUserDto} from "../models/registration-user-dto";
import {LoginDto} from "../models/login-dto";
import {parseDateToString} from "../helpers/help-functions";
import {JwtDto} from "../models/jwt-dto";

const AUTH_API = '/api/v1/auth/';
const HOST = environment.apiUrl;

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) { }

  login(data: LoginDto): Observable<JwtDto> {
    return this.http.post<JwtDto>(
      HOST + AUTH_API + 'login',
      data,
      httpOptions
    );
  }

  register(data: RegistrationUserDto): Observable<any> {
    data.birthday = parseDateToString(data.birthday);
    data.startWork = parseDateToString(data.startWork);

    return this.http.post(
      HOST + AUTH_API + 'signup',
      data,
      httpOptions
    );
  }
}
