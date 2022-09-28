import { Injectable } from '@angular/core';

import { AppSections, BasePermission } from '@common';

import { appRoleAuthMappingConfig } from '../../permissions/app-role-auth-mapping.config';
import { UserPortalService } from '../user-portal/user-portal.service';

@Injectable()
export class AppPermissionsService extends BasePermission {
    protected _params: any;
    protected _sectionName: AppSections;
    private __hostComponent: any;

    constructor(userService: UserPortalService) {
        super(userService);
    }

    set _hostComponent(value: any) {
        this.__hostComponent = value;
    }

    get _hostComponent(): any {
        return this.__hostComponent;
    }

    protected _noPermission(): void {}

    apply(hostComponent: any, sectionName: AppSections, params?: any) {
        this.__hostComponent = hostComponent;
        this._sectionName = sectionName;
        this._params = params;
        this._authAppRoleInit(appRoleAuthMappingConfig);
    }
}
