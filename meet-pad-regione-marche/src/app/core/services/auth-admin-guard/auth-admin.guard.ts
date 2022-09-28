import { Injectable } from '@angular/core';
import {
    CanActivate,
    ActivatedRouteSnapshot,
    RouterStateSnapshot,
    CanActivateChild,
    Router
} from '@angular/router';

import { Observable, of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

import { ApplicationRole } from '@common';

import { UserPortalService } from '../user-portal/user-portal.service';

@Injectable()
export class AuthAdminGuard implements CanActivate, CanActivateChild {
    constructor(
        private router: Router,
        private userService: UserPortalService
    ) {}

    canActivate(
        next: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<boolean> | Promise<boolean> {
        return this._can(state);
    }

    canActivateChild(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<boolean> | Promise<boolean> {
        return this._can(state);
    }

    private _can(
        state: RouterStateSnapshot
    ): Observable<boolean> | Promise<boolean> {
        return this.userService.getRole().pipe(
            map((value: ApplicationRole) => {
                if (value !== ApplicationRole.ADMINISTRATOR) {
                    this.router.navigate(['/']);
                }
                return value === ApplicationRole.ADMINISTRATOR;
            }),
            catchError(error => {
                return of(false);
            })
        );
    }
}
