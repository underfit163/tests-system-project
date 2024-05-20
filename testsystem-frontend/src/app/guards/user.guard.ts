import { CanActivateFn } from '@angular/router';
import {inject} from "@angular/core";
import {TokenStorageService} from "../services/token-storage.service";

export const userGuard: CanActivateFn = (route, state) => {
  const tokenStorage = inject(TokenStorageService);
  return !!tokenStorage.getToken();
};
