import { Injectable } from '@angular/core';
import {
    CanActivate,
    ActivatedRouteSnapshot,
    RouterStateSnapshot
} from '@angular/router';

import { Observable, from, of, forkJoin, throwError } from 'rxjs';
import { map, catchError, switchMap } from 'rxjs/operators';

import { AuthService, HttpInternalErrorResponse } from '@common';

import { UserPortalService } from '@app/core';

@Injectable()
export class JoinGuard implements CanActivate {
    constructor(
        private authService: AuthService,
        private userService: UserPortalService
    ) {}

    canActivate(
        next: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<boolean> | Promise<boolean> | boolean {
        return this._can(state);
    }

    private _can(
        state: RouterStateSnapshot
    ): Observable<boolean> | Promise<boolean> | boolean {
        // si gestisce tutto con gli observable
        return forkJoin([
            // observable della validità dei token oauth2
            of(this.authService.hasValidToken()),
            // observable dell'utente
            this.userService.getInfo().pipe(
                // se la chiamata ritorna le info dell'utente allora torna true
                map(() => true),
                catchError((response: HttpInternalErrorResponse) => {
                    // se token scaduto o accesso da non loggato torna false
                    if (response.status === 401) {
                        return of(false);
                    }
                    // altri errori tipo il 500, 403 etc.. non vengono gestiti qui
                    return throwError(response);
                })
            )
        ]).pipe(
            // switchMap e non map perche la funzione tornerà un altro observble
            switchMap(([tokenValid, userExist]: [boolean, boolean]) => {
                // se i token non sono validi o l'utente non è loggato
                if (!tokenValid || !userExist) {
                    // observable dell'operazione (asincorna) di memorizzazione dell'url di accreditamento
                    return from(
                        this.authService.storeRedirectAppUri(state.url)
                    ).pipe(
                        map(() => {
                            // redirect alla pagina di login
                            this.authService.login();
                            // navigazione interrotta
                            return false;
                        })
                    );
                } else {
                    // tutto ok si accede alla maschera di accreditamento
                    return of(true);
                }
            }),
            // altri tipi di errori vengono gestiti nel componente PrivateComponent
            // per cui si lascia accedere
            catchError((response: HttpInternalErrorResponse) => {
                // utente non censito deve poter accedere agli accreditamenti
                // if (response.status === 403) {
                //     return of(true);
                // }
                // altri errori tipo il 500 etc.. vengono gestiti nel componente Private
                // per cui deve passare
                // return of(true);
                return of(true);
            })
        );
    }
}
