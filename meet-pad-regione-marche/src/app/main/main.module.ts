import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';

import { OAuthModule } from 'angular-oauth2-oidc';

import { EngCoreLibModule } from '@eng-ds/ng-core';

import { environment } from '@env/environment';
import { IT } from '@assets/i18n/it';

import { CoreModule } from '@app/core';
import { SharedModule } from '@app/shared';

import { MainComponent } from './main.component';
import { MainRoutingModule } from './main-routing.module.';

@NgModule({
    declarations: [MainComponent],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        SharedModule,
        OAuthModule.forRoot({
            resourceServer: {
                allowedUrls: [environment.backend.baseUrl],
                sendAccessToken: true
            }
        }),
        EngCoreLibModule.forRoot({
            loggerServiceConfig: {
                level: environment.logger.level,
                hasRemote: environment.logger.hasRemote,
                remoteLogUrl: environment.logger.remoteLogUrl
            },
            environmentServiceConfig: {
                selectedEnvironment: environment
            },
            storageServiceConfig: {
                driverOrder: ['localstorage']
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
        }),
        CoreModule,
        MainRoutingModule
    ],
    bootstrap: [MainComponent]
})
export class MainModule {}
