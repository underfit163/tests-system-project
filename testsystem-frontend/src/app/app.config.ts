import {ApplicationConfig} from '@angular/core';
import {provideRouter} from '@angular/router';

import {routes} from './app.routes';
import {provideAnimationsAsync} from '@angular/platform-browser/animations/async';
import {provideHttpClient, withInterceptors} from "@angular/common/http";
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE} from "@angular/material/core";
import {httpInterceptor} from "./interceptors/http.interceptor";
import {MY_DATE_FORMATS} from "./configs/my-date-formats";
import {DateFnsAdapter} from "@angular/material-date-fns-adapter";
import {ru} from "date-fns/locale";


export const appConfig: ApplicationConfig = {
  providers: [provideRouter(routes),
    provideAnimationsAsync(),
    provideHttpClient(withInterceptors([httpInterceptor])),
    { provide: MAT_DATE_LOCALE, useValue: ru }, // Установите нужную локаль
    { provide: MAT_DATE_FORMATS, useValue: MY_DATE_FORMATS },
    {provide: DateAdapter, useClass: DateFnsAdapter, deps: [MAT_DATE_LOCALE]}]
};
