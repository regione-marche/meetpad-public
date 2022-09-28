import { Injectable } from '@angular/core';
import {
    CanActivate,
    ActivatedRouteSnapshot,
    RouterStateSnapshot,
    CanActivateChild
} from '@angular/router';
import { Observable, BehaviorSubject } from 'rxjs';

@Injectable()
export class MenuGuard implements CanActivate, CanActivateChild {
    show$: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
    constructor() {}

    canActivate(
        next: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<boolean> | Promise<boolean> | boolean {
        return this._can(next);
    }

    canActivateChild(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<boolean> | Promise<boolean> | boolean {
        return this._can(route);
    }

    private _can(
        nextState: ActivatedRouteSnapshot
    ): Observable<boolean> | Promise<boolean> | boolean {
        this.show$.next(!!nextState.data.hasMenu);
        return true;
    }
}
