import {Injectable} from '@angular/core';
import {JwtDto} from "../models/jwt-dto";

const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {

  constructor() {
  }

  signOut(): void {
    window.sessionStorage.clear();
  }

  public saveToken(token: string): void {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY, token);
  }

  public getToken(): string | null {
    return window.sessionStorage.getItem(TOKEN_KEY);
  }

  public saveUser(user: JwtDto): void {
    window.sessionStorage.removeItem(USER_KEY);
    window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  public getUser(): JwtDto {
    const user: string | null = window.sessionStorage.getItem(USER_KEY);
    if (user) {
      return JSON.parse(user);
    }
    return <JwtDto>{};
  }

  public isUser(): boolean {
    const user = this.getUser();
    if (user) {
      return this.isLoggedIn() && user.role.includes("ROLE_USER");
    }
    return false;
  }

  public isAdmin(): boolean {
    const user = this.getUser();
    if (user) {
      return this.isLoggedIn() && user.role.includes("ROLE_ADMIN");
    }
    return false;
  }

  public isLoggedIn(): boolean {
    return !!this.getToken();
  }
}
