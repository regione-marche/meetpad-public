import { Injectable } from '@angular/core';

import { AppSections, BasePermission, User, ConferenceRole } from '@common';

import { appRoleAuthMappingConfig, UserPortalService } from '@app/core';

import { ConferenceStoreService } from '../conference-store/conference-store.service';
import { conferenceRoleAuthMappingConfig } from '../../permissions/conference-role-auth-mapping.config';

@Injectable()
export class ConferencePermissionsService extends BasePermission {
    protected _params: any;
    protected _sectionName: AppSections;
    private __hostComponent: any;

    constructor(
        userService: UserPortalService,
        private conferenceStoreService: ConferenceStoreService
    ) {
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

        this._subscription = this.conferenceStoreService.userProfile$.subscribe(
            (user: User) => {
                // controllo se l'utente loggato ha un ruolo per la conferensa
                if (user) {
                    this._checkSectionPermission(
                        conferenceRoleAuthMappingConfig,
                        user.profile.key as ConferenceRole
                    );
                }
                // se non ha un ruolo per la conferenza applica i permessi applicativi
                else {
                    this._authAppRoleInit(appRoleAuthMappingConfig);
                }
            }
        );
    }
}
