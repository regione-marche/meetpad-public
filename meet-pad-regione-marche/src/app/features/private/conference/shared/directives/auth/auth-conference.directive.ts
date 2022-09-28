import {
    Directive,
    ElementRef,
    Input,
    ViewContainerRef,
    DoCheck
} from '@angular/core';

import { Subscription } from 'rxjs/internal/Subscription';

import { AuthService, ConferenceRole, User, AppSections } from '@common';

import { appRoleAuthMappingConfig, UserPortalService } from '@app/core';

import { BaseAuthDirective } from '@app/shared/directives/auth/base-auth.directive';
import {
    conferenceRoleAuthMappingConfig,
    ConferenceStoreService
} from '../../../core';

@Directive({
    // tslint:disable-next-line:directive-selector
    selector: '[conferenceAuth]'
})
export class AuthConferenceDirective extends BaseAuthDirective
    implements DoCheck {
    // tslint:disable-next-line:no-input-rename
    @Input('conferenceAuth') _sectionName: AppSections;
    @Input('doCheck') doCheck: boolean = false;

    constructor(
        el: ElementRef,
        _view: ViewContainerRef,
        userService: UserPortalService,
        private authService: AuthService,
        private conferenceStoreService: ConferenceStoreService
    ) {
        super(el, _view, userService);
    }

    // tslint:disable-next-line:use-life-cycle-interface
    ngOnInit(): void {
        this._check();
    }

    ngDoCheck(): void {
        if (this.doCheck) {
            if (
                this._subscription &&
                this._subscription instanceof Subscription
            ) {
                this._subscription.unsubscribe();
            }
            this._check();
        }
    }

    private _check(): void {
        
        if (this.authService.hasValidToken()) {
            this._subscription = this.conferenceStoreService.userProfile$.subscribe(
                (user: User) => {
                    // controllo se l'utente loggato ha un ruolo per la conferensa
                    if (user) {
                        this._checkSectionPermission(
                            conferenceRoleAuthMappingConfig,
                            user.profile.key as ConferenceRole
                        );
                    }
                    // se non ha un ruolo per la conferensa applica i permessi applicativi
                    else {
                        this._authAppRoleInit(appRoleAuthMappingConfig);
                    }
                }
            );
        }
    }
}
