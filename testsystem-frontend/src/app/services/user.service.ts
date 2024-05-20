import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {UserWithTicketDto} from "../models/user-with-ticket-dto";
import {ResultDto} from "../models/result-dto";
import {CreateTestDto} from "../models/create-test-dto";
import {ResultFilter} from "../models/result-filter";
import {UserDto} from "../models/user-dto";

const API = '/api/v1/user';
const HOST = environment.apiUrl;

const httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
    providedIn: 'root'
})
export class UserService {

    constructor(private http: HttpClient) {
    }

    getUserWithTicket(id: number) {
        return this.http.get<UserWithTicketDto>(HOST + API + '/' + id + '/info');
    }

    getResultsByUserId(id: number) {
        return this.http.get<Array<ResultDto>>(HOST + API + '/' + id + '/results');
    }

    createTest(test: CreateTestDto) {
        return this.http.post<CreateTestDto>(HOST + API + '/create/test', test, httpOptions);
    }

    createResultsByCSV(file: File) {
        const formData: FormData = new FormData();
        formData.append('file', file, file.name);
        return this.http.post<Array<ResultDto>>(HOST + API + '/results/upload', formData);
    }

    filterResults(filter: ResultFilter) {
        return this.http.post<Array<ResultDto>>(HOST + API + '/filter/results', filter, httpOptions);
    }

    getUsers() {
        return this.http.get<Array<UserDto>>(HOST + API + '/users', httpOptions);
    }
}
