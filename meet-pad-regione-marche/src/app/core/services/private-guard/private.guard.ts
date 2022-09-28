import { Injectable } from '@angular/core';
import {
    CanActivate,
    ActivatedRouteSnapshot,
    RouterStateSnapshot,
    CanActivateChild
} from '@angular/router';

import { Observable } from 'rxjs';

import { AuthService } from '@common';

@Injectable()
export class PrivateGuard implements CanActivate, CanActivateChild {
    constructor(private authService: AuthService) {}

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

    private async _can(state: RouterStateSnapshot): Promise<boolean> {
        // se il token oauth2 è valido
        // si lascia passare
        // la guarda più interna UserGuard farà altre valutazioni
        if (this.authService.hasValidToken()) {
            return true;
        }
        // se è una richiesta di accreditamento si lascia passare
        if (PrivateGuard.isRequestForAccreditation(state)) {
            return true;
        }

        // in ogni altro caso si memorizza l'url a cui
        // si è tentato l'accesso
        // si esegue un redirect alla pagina pubblica
        // e si blocca la navigazione attuale
        await this.authService.storeRedirectAppUri(state.url);
        this.authService.redirectToPublicRoute();
        return false;
    }

    /**
     * Controlla se si sta tentando di eseguire una rchiesta di accreditamento
     * verificando le configurazioni della di destinazione
     * @param state
     */
    // tslint:disable-next-line: member-ordering
    static isRequestForAccreditation(state: RouterStateSnapshot): boolean {
        const targetActivatedRouteSnapshot = this._getTargetActivatedRouteSnapshot(
            state
        );

        return (
            targetActivatedRouteSnapshot &&
            targetActivatedRouteSnapshot.routeConfig.path ===
                ':token_1/:token_2' &&
            state.url.includes('join')
        );
    }

    /**
     * Ottiene l'oggetto ActivatedRouteSnapshot rappresentate la rotta di destinazione
     * @param state
     */
    // tslint:disable-next-line: member-ordering
    static _getTargetActivatedRouteSnapshot(
        state: RouterStateSnapshot
    ): ActivatedRouteSnapshot {
        let targetActivatedRouteSnapshot: ActivatedRouteSnapshot = state.root;
        while (targetActivatedRouteSnapshot.children.length) {
            targetActivatedRouteSnapshot =
                targetActivatedRouteSnapshot.children[0];
        }
        return targetActivatedRouteSnapshot;
    }
}
