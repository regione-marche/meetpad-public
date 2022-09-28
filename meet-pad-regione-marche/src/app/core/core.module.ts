import { NgModule } from '@angular/core';
import { HTTP_INTERCEPTORS } from '@angular/common/http';

import { NgSelectConfig } from '@ng-select/ng-select';

import { NgxEngToolkitModule, MenuLoader } from '@eng-ds/ng-toolkit';

import {
    CommonModule,
    UserService,
    HttpInterceptorService,
    API_BASE_URL
} from '@common';

import { environment } from '@env/environment';

import {
    UtilityService,
    CustomPreloadingStrategy,
    AppPermissionsService,
    MenuService,
    UserPortalService
} from '@app/core/services';

@NgModule({
    imports: [CommonModule.forRoot(), NgxEngToolkitModule],
    providers: [
        UtilityService,
        UserPortalService,
        AppPermissionsService,
        MenuLoader,
        MenuService,
        CustomPreloadingStrategy,
        {
            provide: HTTP_INTERCEPTORS,
            useClass: HttpInterceptorService,
            multi: true
        },
        {
            provide: NgSelectConfig,
            useValue: environment.autocompleteConf
        },
        {
            provide: UserService,
            useExisting: UserPortalService,
            multi: false
        },
        {
            provide: API_BASE_URL,
            useValue: environment.backend.baseUrl
        }
    ]
})
export class CoreModule {}
