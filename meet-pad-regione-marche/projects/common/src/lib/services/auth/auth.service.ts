import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

import { BehaviorSubject, Subject, throwError } from 'rxjs';

import { OAuthService, AuthConfig } from 'angular-oauth2-oidc';

import { ConfigService, StorageService } from '@eng-ds/ng-core';

import { UserService } from '../user/user.service';
import { catchError } from 'rxjs/operators';

const keyPostLoginUrl = 'postLoginUrl';
const keyLoginType = 'lognType';

export enum LoginType {
    SPID = 'oauth2Spid',
    COHESION = 'oauth2Cohesion'
}

@Injectable()
export class AuthService {
    public onLogin$: BehaviorSubject<boolean> = new BehaviorSubject(false);
    public onLoginError$: Subject<any> = new Subject();

    constructor(
        private oauthService: OAuthService,
        private configService: ConfigService,
        private storageService: StorageService,
        private router: Router,
        private userService: UserService
    ) {}

    hasValidToken(): boolean {
        if (!this.configService.get('auth.enabled')) {
            return true;
        }

        return (
            this.oauthService.hasValidAccessToken() &&
            this.oauthService.hasValidIdToken()
        );
    }

    async storeRedirectAppUri(url: string): Promise<void> {
        await this.storageService.ready();
        return this.storageService.set(keyPostLoginUrl, url);
    }

    async storeLoginType(type: LoginType): Promise<void> {
        await this.storageService.ready();
        return this.storageService.set(keyLoginType, type);
    }

    async login(type: LoginType = LoginType.COHESION): Promise<void> {
        await this.storageService.ready();
        this.storeLoginType(type);
        await this.initOauth2(type);

        if (this.configService.get('auth.discovery')) {
            await this.loadDiscoveryDocument();
        }
        this.oauthService.initImplicitFlow();
    }

    silentRefresh(): void {
        this.oauthService.silentRefresh();
    }

    getIdentityClaims(): object {
        return this.oauthService.getIdentityClaims();
    }

    getIdToken(): string {
        return this.oauthService.getIdToken();
    }

    getAccessToken(): string {
        return this.oauthService.getAccessToken();
    }

    async logout(): Promise<void> {
        if (!this.oauthService.loginUrl) {
            const loginType: LoginType = await this._getLoginTypeByStore();
            await this.initOauth2(loginType);
        }
        this.oauthService.logOut();
    }

    redirectToDefaultLoginRoute(): void {
        this.router.navigate([
            this.configService.get('auth.defaultLoginRoute')
        ]);
    }

    redirectTodefaultAdminLoginRoute(): void {
        this.router.navigate([
            this.configService.get('auth.defaultAdminLoginRoute')
        ]);
    }

    redirectToPublicRoute(): void {
        this.router.navigate([
            this.configService.get('auth.defaultPublicRoute')
        ]);
    }

    async initOauth2(type: LoginType = LoginType.COHESION): Promise<void> {
        const authConfig: AuthConfig = this.configService.get(`auth.${type}`);
        this.oauthService.configure(authConfig);

        if (this.configService.get('auth.discovery')) {
            await this.loadDiscoveryDocument();
        }
    }

    async tryLogin(): Promise<void> {
        if (this.configService.get('auth.enabled')) {
            const loginType: LoginType = await this._getLoginTypeByStore();
            await this.initOauth2(loginType);
            this._tryLogin();
        } else {
            this.onLogin$.next(true);
        }
    }

    async setupAutomaticSilentRefresh(): Promise<void> {
        await this.initOauth2();
        this.oauthService.setupAutomaticSilentRefresh();
    }

    loadDiscoveryDocument(): Promise<object> {
        return this.oauthService.loadDiscoveryDocument();
    }

    private async _getLoginTypeByStore(): Promise<LoginType> {
        await this.storageService.ready();
        return await this.storageService.get(
            keyLoginType
        );
    }

    private async _tryLogin(): Promise<void> {
        await this.oauthService
            .tryLogin({
                onLoginError: err => {
                    this.onLoginError$.next(err);
                },
                onTokenReceived: () => {
                    if (this.hasValidToken()) {
                        this._checkRedirectAppUri();
                        this.onLogin$.next(true);
                    }
                }
            })
            .catch(err => {
                this.onLoginError$.next(err);
            });
    }

    private _checkRedirectAppUri() {
        this.userService
            .isAdmin()
            .pipe(
                catchError(e => {
                    this._checkRedirect();
                    return throwError(e);
                })
            )
            .subscribe((isAdmin: boolean) => {
                this._checkRedirect(isAdmin);
            });
    }

    private async _checkRedirect(isAdmin: boolean = false) {
        const url: string = await this.storageService.get(keyPostLoginUrl);

        if (isAdmin) {
            this.redirectTodefaultAdminLoginRoute();
        }

        if (url) {
            this.storageService.remove(keyPostLoginUrl);

            if (!isAdmin) {
                this.router.navigate([url]);
            }
        } else {
            if (!isAdmin) {
                this.redirectToDefaultLoginRoute();
            }
        }
    }

    authSignatoryRole(): boolean {
        let isFlagSignatory = false;
        this.userService
           .isSignatoryUser().subscribe((isSignatory: boolean) => {
            if(isSignatory === true){
                isFlagSignatory = true;
            }
           });
           return isFlagSignatory;
   }  
}
