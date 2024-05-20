import {CanActivateFn, Router} from '@angular/router';
import {inject} from "@angular/core";
import {TokenStorageService} from "../services/token-storage.service";

export const adminGuard: CanActivateFn = (route, state) => {
  const tokenStorage = inject(TokenStorageService);
  const router = inject(Router);
  if (tokenStorage.isAdmin()) {
    return true;
  } else {
    router.navigate(['/']);
    return false;
  }
};
