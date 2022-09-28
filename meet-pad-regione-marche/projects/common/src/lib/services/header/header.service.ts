import { Injectable } from '@angular/core';

import { Observable, BehaviorSubject, throwError, combineLatest } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

import { User } from '../../interfaces/user.interface';
import { HttpInternalErrorResponse } from '../../interfaces/wrapers-http-response.interface';

import { UserService } from '../user/user.service';
import { AuthService } from '../auth/auth.service';

@Injectable()
export class HeaderService {
    private _showUser$ = new BehaviorSubject(false);
    private _showFiscalCode$ = new BehaviorSubject(false);
    private _showMenu$ = new BehaviorSubject(false);
    private _showLogin$ = new BehaviorSubject(false);

    constructor(
        private userService: UserService,
        private authService: AuthService
    ) {}

    get user$(): Observable<string> {
        return this.userService.getInfo().pipe(
            map((u: User) => `${u.name} ${u.lastname}`),
            catchError((response: HttpInternalErrorResponse) => {
                // in questo caso si visualizza il codice fiscale dall'id_token di oauth2
                if (response.status === 403) {
                    this.showFiscalCode();
                } else {
                    this.hideFiscalCode();
                }

                this.hideLogin();
                this.hideMenu();
                this.hideUser();
                return throwError(response);
            })
        );
    }

    get fiscalCode() {
        const claims: any = this.authService.getIdentityClaims();
        return claims && claims.sub ? claims.sub : '';
    }

    get showFiscalCode$(): Observable<boolean> {
        return this._showFiscalCode$;
    }

    get showUser$(): Observable<boolean> {
        return this._showUser$;
    }

    get showMenu$(): Observable<boolean> {
        return this._showMenu$;
    }

    get showLogin$(): Observable<boolean> {
        return this._showLogin$;
    }

    get emptyHeader$(): Observable<boolean> {
        return combineLatest(
            this._showLogin$.asObservable(),
            this._showMenu$.asObservable(),
            this._showUser$.asObservable()
        ).pipe(
            map(
                ([showLogin, showMenu, showUser]: [
                    boolean,
                    boolean,
                    boolean
                ]) => !(showLogin || showMenu || showUser)
            )
        );
    }

    showFiscalCode(): void {
        this._showFiscalCode$.next(true);
    }

    hideFiscalCode(): void {
        this._showFiscalCode$.next(false);
    }

    showUser(): void {
        this._showUser$.next(true);
    }

    hideUser(): void {
        this._showUser$.next(false);
    }

    showMenu(): void {
        this._showMenu$.next(true);
    }

    hideMenu(): void {
        this._showMenu$.next(false);
    }

    showLogin(): void {
        this._showLogin$.next(true);
    }

    hideLogin(): void {
        this._showLogin$.next(false);
    }
}
