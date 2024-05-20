import {Routes} from '@angular/router';
import {RegisterComponent} from "./components/auth/register/register.component";
import {LoginComponent} from "./components/auth/login/login.component";
import {HomeComponent} from "./components/home/home.component";
import {ProfileComponent} from "./components/profile/profile.component";
import {AdminResultsComponent} from "./components/admin-results/admin-results.component";
import {CreateTestComponent} from "./components/create-test/create-test.component";
import {TestDetailComponent} from "./components/test-detail/test-detail.component";
import {TestsComponent} from "./components/tests/tests.component";
import {TestResultComponent} from "./components/test-result/test-result.component";
import {authGuard} from "./guards/auth.guard";
import {adminGuard} from "./guards/admin.guard";
import {UserResultsComponent} from "./components/user-results/user-results.component";

export const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'profile', component: ProfileComponent, canActivate: [authGuard]},
  {path: 'tests', component: TestsComponent, canActivate: [authGuard]},
  {path: 'test/:id', component: TestDetailComponent, canActivate: [authGuard]},
  {path: 'test/:id/result', component: TestResultComponent, canActivate: [authGuard]},
  {path: 'user/results', component: UserResultsComponent, canActivate: [authGuard]},
  {path: 'admin/create-test', component: CreateTestComponent, canActivate: [adminGuard]},
  {path: 'admin/results', component: AdminResultsComponent, canActivate: [adminGuard]},
  {path: '**', redirectTo: '/'}
];
