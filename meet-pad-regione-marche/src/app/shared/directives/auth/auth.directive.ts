import { Directive, ElementRef, Input, ViewContainerRef } from '@angular/core';

import { AuthService, AppSections } from '@common';

import { appRoleAuthMappingConfig } from '@app/core/permissions';

import { BaseAuthDirective } from './base-auth.directive';
import { UserPortalService } from '@app/core';

@Directive({
    selector: '[appAuth]'
})
export class AuthDirective extends BaseAuthDirective {
    // tslint:disable-next-line:no-input-rename
    @Input('appAuth') _sectionName: AppSections;

    constructor(
        el: ElementRef,
        _view: ViewContainerRef,
        userService: UserPortalService,
        private authService: AuthService
    ) {
        super(el, _view, userService);
    }

    ngOnInit(): void {
        if (this.authService.hasValidToken()) {
            this._authAppRoleInit(appRoleAuthMappingConfig);
        }
    }
}
