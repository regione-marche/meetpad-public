import { Component, ViewChild, NgZone } from '@angular/core';
import { Router } from '@angular/router';

import { takeUntil, auditTime } from 'rxjs/operators';

import { ToastrService } from 'ngx-toastr';

import { MenuItem, ModalComponent, ActionItem } from '@eng-ds/ng-toolkit';
import { I18nService } from '@eng-ds/ng-core';

import { AuthService, AutoUnsubscribe } from '@common';

import { MenuService, UserPortalService } from '@app/core';

@Component({
    template: `
        <eng-template role="region" aria-label="Recommendations"><router-outlet></router-outlet></eng-template>
        <eng-modal
            class="smallModal"
            #confirmLoginRedirectModal
            [buttons]="confirmLoginRedirectBtnModal"
            [title]="'PRIVATE.MODAL.CONFIRM_LOGIN_REDIRECT.TITLE' | translate"
            ><p
                [translate]="'PRIVATE.MODAL.CONFIRM_LOGIN_REDIRECT.MESSAGE'"
            ></p>
        </eng-modal>
    `
})
export class PrivateComponent extends AutoUnsubscribe {
    @ViewChild('confirmLoginRedirectModal')
    confirmLoginRedirectModalconfirmModal: ModalComponent;

    links: MenuItem[] = [];

    confirmLoginRedirectBtnModal: ActionItem[] = [
        new ActionItem(
            'PRIVATE.MODAL.CONFIRM_LOGIN_REDIRECT.CANCEL_BUTTON',
            (action: ActionItem) => {
                this.confirmLoginRedirectModalconfirmModal.close();
                this.authService.redirectToPublicRoute();
            },
            'close'
        ),
        new ActionItem(
            'PRIVATE.MODAL.CONFIRM_LOGIN_REDIRECT.OK_BUTTON',
            async (action: ActionItem) => {
                await this.authService.storeRedirectAppUri(this.router.url);
                this.authService.login();
            },
            'check'
        )
    ];

    constructor(
        private router: Router,
        private toastr: ToastrService,
        private authService: AuthService,
        private userService: UserPortalService,
        private menuService: MenuService,
        private i18nService: I18nService,
        private ngZone: NgZone
    ) {
        super();
        this._init();
    }

    private _init(): void {
        //this.authService.setupAutomaticSilentRefresh();
        this.menuService.loadPrivateMenu();
        this._subscribeToUnauthenticated();
        this._subscribeToSystemMaintenance();
    }

    private _subscribeToSystemMaintenance() {
        this.userService.systemMaintenance$
            .pipe(
                takeUntil(this.destroy$),
                auditTime(3000)
            )
            .subscribe(() => {
                this.toastr.warning(
                    this.i18nService.translate('TOASTR.MAINTENANCE.TEXT'),
                    this.i18nService.translate('TOASTR.MAINTENANCE.TITLE'),
                    {
                        disableTimeOut: true
                    }
                );
                // workaround for angular bug https://github.com/angular/angular/issues/25837
                this.ngZone.run(() => {
                    this.router.navigate(['/contact']);
                });
            });
    }

    private _subscribeToUnauthenticated(): void {
        this.userService.unauthenticated$
            .pipe(
                takeUntil(this.destroy$),
                auditTime(500)
            )
            .subscribe(() => {
                this.toastr.error(
                    this.i18nService.translate('TOASTR.UNAUTHENTICATED.TEXT'),
                    this.i18nService.translate('TOASTR.UNAUTHENTICATED.TITLE')
                );
                this.confirmLoginRedirectModalconfirmModal.open();
            });
    }
}
