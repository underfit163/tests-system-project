import {Component} from '@angular/core';
import {TokenStorageService} from "../../services/token-storage.service";
import {
  MatCard,
  MatCardContent,
  MatCardHeader,
  MatCardImage,
  MatCardModule,
  MatCardTitle
} from "@angular/material/card";
import {NgIf} from "@angular/common";
import {JwtDto} from "../../models/jwt-dto";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    MatCardHeader,
    MatCard,
    MatCardContent,
    MatCardTitle,
    MatCardImage,
    NgIf,
    MatCardModule
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  currentUser: JwtDto | undefined;
  isLogged: boolean | undefined;
  isAdmin: boolean | undefined;
  isUser: boolean | undefined;

  constructor(private token: TokenStorageService) {
  }

  ngOnInit(): void {
    if (this.token.isLoggedIn()) {
      this.isLogged = this.token.isLoggedIn();
      this.currentUser = this.token.getUser();
      this.isAdmin = this.token.isAdmin();
      this.isUser = this.token.isUser();
    } else {
      this.isLogged = false;
    }
  }
}
