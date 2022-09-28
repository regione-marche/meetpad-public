import { NgModule, Inject } from '@angular/core';

import { EngCoreLibModule, EnvironmentService } from '@eng-ds/ng-core';
import { MenuLoader } from '@eng-ds/ng-toolkit';

import { IT } from '../../assets/i18n/it';
import { backend } from '../../config/backend';
// import { IT } from '@console-test/assets/i18n/it';

import { AdminConsoleComponent } from './admin-console.component';
import { AdminAppRoutingModule } from './admin-console-routing.module';
import {
    CommonModule,
    MenuGuard,
    HttpInterceptorService,
    FileService,
    API_BASE_URL,
    MessageToastrService
} from '@common';
import { AuthoritiesProcedingModule } from '../features/authorities/authorities.module';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { DefaultOAuthInterceptor } from 'angular-oauth2-oidc';
import { ManagerModule } from '../features/authorities/features/manager/manager.module';
import { AuthoritiesConferenceModule } from '../features/authorities/features/conference/conference.module';
import { UsersModule } from '../features/users/users.module';

import { PreloadingModule } from '../features/preloading/preloading.module';
import { MainModule } from '../features/main/main.module';
import { AuthoritiesModule } from '../features/authorities/features/authorities/authorities.module';
import { ConferenceModule } from '../features/conference/conference.module';
import { ProtocolModule } from '../features/protocols/protocols.module';

@NgModule({
    declarations: [AdminConsoleComponent],
    imports: [
        CommonModule,
        AdminAppRoutingModule,
        AuthoritiesModule,
        AuthoritiesProcedingModule,
        ManagerModule,
        AuthoritiesConferenceModule,
        ConferenceModule,
        UsersModule,
        ProtocolModule,

        PreloadingModule,
        MainModule,
        EngCoreLibModule.forRoot({
            environmentServiceConfig: {
                selectedEnvironment: {
                    appName: 'APP_NAME',
                    backend: {
                        environment: backend.environment,
                        api: backend.api
                    }
                }
            },
            i18nServiceConfig: {
                langs: [
                    {
                        code: 'it',
                        label: 'Italiano',
                        translations: IT,
                        isDefault: true
                    }
                ]
            }
        })
    ],
    providers: [
        MenuLoader,
        MenuGuard,
        {
            provide: HTTP_INTERCEPTORS,
            useClass: DefaultOAuthInterceptor,
            multi: true
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: HttpInterceptorService,
            multi: true
        },
        FileService,
        MessageToastrService
    ]
})
export class AdminConsoleModule {
    constructor(
        @Inject(API_BASE_URL) private backendBaseUrl: string,
        private environmentService: EnvironmentService
    ) {
        this.environmentService.setNew({
            ...this.environmentService.selectedEnvironment,
            ...{
                backend: {
                    ...this.environmentService.selectedEnvironment.backend,
                    baseUrl: this.backendBaseUrl
                }
            }
        });
    }
}
