import { Injectable } from '@angular/core';
import {
    CanActivate,
    ActivatedRouteSnapshot,
    RouterStateSnapshot,
    CanActivateChild
} from '@angular/router';

import { Observable, forkJoin, of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

import { AuthService } from '@common';
import { UserPortalService } from '../user-portal/user-portal.service';

@Injectable()
export class PublicGuard implements CanActivate, CanActivateChild {
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

    canActivateChild(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<boolean> | Promise<boolean> | boolean {
        return this._can(state);
    }

    private _can(
        state: RouterStateSnapshot
    ): Observable<boolean> | Promise<boolean> | boolean {
        return forkJoin([
            // observable della validità dei token oauth2
            of(this.authService.hasValidToken()),
            // observable dell'utente
            this.userService.getInfo(false).pipe(
                // se la chiamata ritorna le info dell'utente allora la si va sulla sezione privata
                map(() => true),
                // in ogni caso di errore la pagina pubblica si puo vedere
                catchError(() => of(false))
            )
        ]).pipe(
            map(([validToken, userExist]: [boolean, boolean]) => {
                // token non valido si può entrare nella public
                if (!validToken) {
                    return true;
                }

                // utente non esistente si può entrare nella public
                if (!userExist) {
                    return true;
                }

                // token valido e utente esistente si va nella private
                if (validToken && userExist) {
                    this.authService.redirectToDefaultLoginRoute();
                    return false;
                }

                // non dovremmo mai cadere in questo caso
                return false;
            })
        );
    }
}
