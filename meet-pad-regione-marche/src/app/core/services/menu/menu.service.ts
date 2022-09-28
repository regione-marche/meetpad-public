import { Injectable } from '@angular/core';

import { MenuLoader, MenuItem } from '@eng-ds/ng-toolkit';
import { I18nService } from '@eng-ds/ng-core';

import { environment } from '@env/environment';

import {
    MenuGuard,
    AppSections,
    AuthService,
    HeaderService,
    LoginType
} from '@common';
import { AppPermissionsService } from '../permissions/app-permissions.service';

@Injectable()
export class MenuService {
    constructor(
        private authService: AuthService,
        private appPermissions: AppPermissionsService,
        public i18nService: I18nService,
        public menuLoader: MenuLoader,
        public menuGuard: MenuGuard,
        public headerService: HeaderService
    ) {}

    get publicMenuItems(): MenuItem[] {
        return [
            new MenuItem(
                this.i18nService.translate('DOCUMENT'),
                () => {
                    window.open(
                        'authInstructions.pdf',
                        '_blank',
                        'fullscreen=yes'
                    );
                },
                null,
                null,
                'CALLBACK'
            ),
            new MenuItem(
                this.i18nService.translate('LOGIN.TITLE'),
                null,
                null,
                null,
                'NORMAL',
                null,
                false,
                [
                    new MenuItem(
                        this.i18nService.translate('LOGIN.COHESION'),
                        () => {
                            this.authService.login(LoginType.COHESION);
                        },
                        null,
                        null,
                        'CALLBACK'
                    ),
                    new MenuItem(
                        this.i18nService.translate('LOGIN.SPID'),
                        () => {
                            this.authService.login(LoginType.SPID);
                        },
                        null,
                        null,
                        'CALLBACK'
                    )
                ]
            )
        ];
    }

    get privateBaseMenuItems(): MenuItem[] {
        return [
            new MenuItem(
                this.i18nService.translate('MENU.LINKS.DESKTOP'),
                null,
                '/'
            ),
            new MenuItem(
                this.i18nService.translate('MENU.LINKS.SEARCH'),
                null,
                '/search'
            ),
            new MenuItem(
                this.i18nService.translate('MENU.LINKS.MEDIA_LIBRARY'),
                null,
                '/media-library'
            ),
            new MenuItem(
                this.i18nService.translate('MENU.LINKS.CALENDAR'),
                null,
                '/calendar'
            ),
            new MenuItem(
                this.i18nService.translate(
                    'MENU.LINKS.CREATE_CONFERENCE.PARENT'
                ),
                null,
                null,
                null,
                'NORMAL',
                null,
                false,
                [
                    new MenuItem(
                        this.i18nService.translate(
                            'MENU.LINKS.CREATE_CONFERENCE.DOMUS'
                        ),
                        null,
                        '/conference/domus'
                    )
                ]
            ),
            new MenuItem(
                this.i18nService.translate('MENU.LINKS.JITSI'),
                function () {
                    window.open(environment.jitsi, '_blank').focus();
                },
                null,
                null,
                "CALLBACK",
                null,
                false

            )
        ];
    }

    get privateRoleMenuItems(): MenuItem[] {
        return [
            new MenuItem(
                this.i18nService.translate(
                    'MENU.LINKS.CREATE_CONFERENCE.PARENT'
                ),
                null,
                null,
                null,
                'NORMAL',
                null,
                false,
                [
                    new MenuItem(
                        this.i18nService.translate(
                            'MENU.LINKS.CREATE_CONFERENCE.MEET_PAD'
                        ),
                        null,
                        '/conference/procedure'
                    ),
                    new MenuItem(
                        this.i18nService.translate(
                            'MENU.LINKS.CREATE_CONFERENCE.PALEO'
                        ),
                        null,
                        '/conference/paleo'
                    ),
                    new MenuItem(
                        this.i18nService.translate(
                            'MENU.LINKS.CREATE_CONFERENCE.DOMUS'
                        ),
                        null,
                        '/conference/domus'
                    )
                ]
            )
        ];
    }

    get privateSignMenuItem(): MenuItem[]{
        return [
            new MenuItem(
                this.i18nService.translate('MENU.LINKS.SIGN'),
                null,
                '/sign'
            ),
        ];
    }

    loadPublicMenu(): void {
        this.menuLoader.setMenu(this.publicMenuItems);
    }

    loadPrivateMenu(): void {
        this.appPermissions.apply(this, AppSections.SIDE_MENU);
    }

    isSignatoryUser(): boolean{
       return this.authService.authSignatoryRole();
    }
}
