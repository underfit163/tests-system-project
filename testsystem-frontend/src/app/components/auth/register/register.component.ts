import {Component} from '@angular/core';
import {MatCard, MatCardContent, MatCardFooter, MatCardTitle} from "@angular/material/card";
import {MatFormField, MatFormFieldModule} from "@angular/material/form-field";
import {MatIcon} from "@angular/material/icon";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {AuthService} from "../../../services/auth.service";
import {MatInput} from "@angular/material/input";
import {NgIf} from "@angular/common";
import {MatButton} from "@angular/material/button";
import {
  MatDatepickerModule
} from "@angular/material/datepicker";
import {subYears} from "date-fns";

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    MatCardFooter,
    MatFormField,
    MatCardTitle,
    MatCard,
    MatCardContent,
    MatIcon,
    ReactiveFormsModule,
    MatInput,
    NgIf,
    MatButton,
    MatFormFieldModule,
    MatDatepickerModule,
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  form: FormGroup;
  flag: boolean = true;
  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';
  maxBirthdayDate: Date = subYears(new Date(), 14);
  maxStartWorkDate: Date = new Date();

  constructor(private authService: AuthService, private fb: FormBuilder) {
    this.form = this.fb.group({
      name: [null, [Validators.required, Validators.minLength(3)]],
      login: [null, [Validators.required, Validators.minLength(3)]],
      password: [null, [Validators.required, Validators.minLength(5)]],
      email: [null, [Validators.required, Validators.email]],
      birthday: [this.maxBirthdayDate, [Validators.required]],
      startWork: [this.maxStartWorkDate, [Validators.required]],
      workNumber: [null, [Validators.required]]
    });
  }
  onSubmit(): void {
    this.authService.register(this.form.value).subscribe({
      next: data => {
        this.isSuccessful = true;
        this.isSignUpFailed = false;
      },
      error: err => {
        if (!(err.status == 200)) {
          this.errorMessage = err.error;
          this.isSignUpFailed = true;
          this.isSuccessful = false;
        } else {
          this.isSuccessful = true;
          this.isSignUpFailed = false;
        }
      }
    });
  }
}
