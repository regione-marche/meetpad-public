import { Injectable } from '@angular/core';
import {
    CanActivate,
    ActivatedRouteSnapshot,
    RouterStateSnapshot,
    CanActivateChild,
    Router
} from '@angular/router';

import { Observable, of } from 'rxjs';
import { map } from 'rxjs/internal/operators/map';
import { catchError } from 'rxjs/internal/operators/catchError';

import { HttpInternalErrorResponse } from '@common';

import { AccreditamentPreview } from '@app/features/private/conference/core/models/accreditation/accreditament-preview.model';
import { UserPortalService } from '../user-portal/user-portal.service';

@Injectable()
export class PendingAccreditationGuard
    implements CanActivate, CanActivateChild {
    constructor(
        private userService: UserPortalService,
        private router: Router
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
            map(() => true),
            catchError((response: HttpInternalErrorResponse) => {
                // l'utente non è autorizzato per due motivi
                // l'utente non è ancora censito sull'applicativo
                // in quessto caso si controllano se esistono accreditamenti appesi
                if (response.status === 403) {
                    // si controlla se esistono accreditamenti pendenti dell'utente loggato
                    return this.userService.getPendingAccreditations().pipe(
                        map((pendingAccreditations: AccreditamentPreview[]) => {
                            // se non ci sono stati errori nella chiamata ad es. 401
                            if (pendingAccreditations) {
                                // ci sono accreditamenti pendenti
                                if (pendingAccreditations.length) {
                                    // redirect alla pagina con lista delle richieste di accreditamento pendenti
                                    this.router.navigate([
                                        'join',
                                        'pending',
                                        'list'
                                    ]);
                                    return false;
                                } else {
                                    // non ci sono accreditamenti
                                    // redirect sulla pagina di cortesia
                                    this.router.navigate(['nothing-to-do']);
                                    return false;
                                }
                            }
                            // altri casi non vengono gestiti qua
                            return true;
                        }),
                        catchError(() => {
                            // non ci sono accreditamenti
                            // redirect sulla pagina di cortesia
                            this.router.navigate(['nothing-to-do']);
                            return of(false);
                        })
                    );
                } else {
                    // altri casi non vengono gestiti qua
                    return of(true);
                }
            })
        );
    }
}
