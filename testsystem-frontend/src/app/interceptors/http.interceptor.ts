import {
  HttpInterceptorFn
} from '@angular/common/http';
import {inject} from "@angular/core";
import {TokenStorageService} from "../services/token-storage.service";
export const httpInterceptor: HttpInterceptorFn = (req, next) => {
  const tokenStorage = inject(TokenStorageService);
  let authReq = req;

  const token = tokenStorage.getToken();
  if (token != null) {
    authReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
  }
  return next(authReq);
};

