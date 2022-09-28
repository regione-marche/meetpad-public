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

import { HttpInternalErrorResponse } from '@common';

import { UserPortalService } from '../user-portal/user-portal.service';
import { PrivateGuard } from '../private-guard/private.guard';

@Injectable()
export class UserGuard implements CanActivate, CanActivateChild {
    constructor(private userService: UserPortalService) {}

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
            map(() => true),
            catchError((response: HttpInternalErrorResponse) => {
                // token errato o wso2 blocca la sessione
                if (response.status === 401) {
                    // se è una richiesta di accreditamento si lascia passare
                    // si passa la pala alla guardia più interna JoinGuard
                    if (PrivateGuard.isRequestForAccreditation(state)) {
                        return of(true);
                    }

                    // caso in cui il token è presente sullo storage
                    // nella maggior parte dei casi l'utente
                    // sta usando l'applicativo ed è scaduta la sessione
                    // per cui si delega la gestione dell'evento non autenticato
                    // al modulo Private
                    this.userService.unauthenticated$.next();
                    return of(true);
                }
                // altri errori tipo il 500 etc.. non vengono gestiti qui
                // return throwError(response);
                return of(true);
            })
        );
    }
}
