import {CanActivateFn, Router} from '@angular/router';
import {inject} from "@angular/core";
import {TokenStorageService} from "../services/token-storage.service";

export const authGuard: CanActivateFn = (route, state) => {
  const tokenStorage = inject(TokenStorageService);
  const router = inject(Router);
  if (tokenStorage.isLoggedIn()) {
    return true;
  } else {
    router.navigate(['/login']);
    return false;
  }
};
