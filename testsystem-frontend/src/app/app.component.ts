import { Component } from '@angular/core';
import {RouterLink, RouterLinkActive, RouterOutlet} from '@angular/router';
import {MatToolbar} from "@angular/material/toolbar";
import {MatAnchor} from "@angular/material/button";
import {TokenStorageService} from "./services/token-storage.service";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, MatToolbar, RouterLink, MatAnchor, RouterLinkActive, NgIf],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {

  private role: string = '';
  isLoggedIn = false;
  isAdmin = false;
  isUser = false;
  login?: string;

  constructor(private tokenStorageService: TokenStorageService) {
  }

  ngOnInit(): void {
    this.isLoggedIn = this.tokenStorageService.isLoggedIn();

    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.role = user.role;

      this.isAdmin = this.tokenStorageService.isAdmin();
      this.isUser = this.tokenStorageService.isUser();

      this.login = user.login;
    }
  }

  logout(): void {
    this.tokenStorageService.signOut();
    //this.router.navigateByUrl("/");
    window.location.reload();
  }
}
