import { Injectable } from '@angular/core';
import {
    CanActivate,
    ActivatedRouteSnapshot,
    RouterStateSnapshot,
    CanActivateChild
} from '@angular/router';

import { Observable, of } from 'rxjs';
import { map } from 'rxjs/internal/operators/map';
import { catchError } from 'rxjs/internal/operators/catchError';

import { HttpInternalErrorResponse } from '../../interfaces/wrapers-http-response.interface';
import { User } from '../../interfaces/user.interface';

import { UserService } from '../user/user.service';
import { HeaderService } from '../header/header.service';

@Injectable()
export class HeaderGuard implements CanActivate, CanActivateChild {
    constructor(
        private userService: UserService,
        private headerService: HeaderService
    ) {}

    canActivate(
        next: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<boolean> | Promise<boolean> | boolean {
        return this._can(state);
    }

    canActivateChild(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<boolean> | Promise<boolean> | boolean {
        return this._can(state);
    }

    private _can(
        state: RouterStateSnapshot
    ): Observable<boolean> | Promise<boolean> | boolean {
        return this.userService.getInfo().pipe(
            map((user: User) => {
                this.headerService.showMenu();
                this.headerService.showUser();
                return true;
            }),
            catchError((response: HttpInternalErrorResponse) => {
                if (response.status === 403) {
                    // in questo caso si prende il codice fiscale dall'id_token di oauth2
                    // vedere header.service.ts:33
                    this.headerService.showFiscalCode();
                } else {
                    this.headerService.hideFiscalCode();
                }
                this.headerService.hideUser();
                return of(true);
            })
        );
    }
}
