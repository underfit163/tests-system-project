import {Component} from '@angular/core';
import {MatCard, MatCardContent, MatCardFooter, MatCardTitle} from "@angular/material/card";
import {MatFormField, MatFormFieldModule} from "@angular/material/form-field";
import {MatIcon} from "@angular/material/icon";
import {MatInput} from "@angular/material/input";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatButton} from "@angular/material/button";
import {TokenStorageService} from "../../../services/token-storage.service";
import {AuthService} from "../../../services/auth.service";
import {NgIf} from "@angular/common";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    MatCardFooter,
    MatCard,
    MatCardTitle,
    MatCardContent,
    MatFormField,
    MatIcon,
    MatInput,
    ReactiveFormsModule,
    MatButton,
    NgIf,
    MatFormFieldModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  form: FormGroup;
  flag: boolean = true;
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  role: string | undefined;


  constructor(private authService: AuthService, private tokenStorage: TokenStorageService,
              private fb: FormBuilder) {
    this.form = this.fb.group({
      login: [null, [Validators.required]],
      password: [null, [Validators.required, Validators.minLength(5)]],
    });
  }

  ngOnInit(): void {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.role = this.tokenStorage.getUser().role;
    }
  }

  onSubmit(): void {
    this.authService.login(this.form.value).subscribe({
      next: data => {
        this.tokenStorage.saveToken(data.jwt);
        this.tokenStorage.saveUser(data);
        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.role = this.tokenStorage.getUser().role;
        this.redirectPage();
      },
      error: err => {
        this.errorMessage = err.error;
        this.isLoginFailed = true;
      }
    });
  }

  redirectPage(): void {
    window.location.href = "/";
  }
}
