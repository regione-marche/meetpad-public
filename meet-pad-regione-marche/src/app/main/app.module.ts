import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { MenuLoader } from '@eng-ds/ng-toolkit';
import { EngCoreLibModule } from '@eng-ds/ng-core';

import { environment } from '@env/environment';
import { IT } from '@assets/i18n/it';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { SharedModule } from '@app/shared/shared.module';
import { FeaturesModule } from '@app/features/features.module';

@NgModule({
    declarations: [AppComponent],
    imports: [
        BrowserModule,
        SharedModule,
        AppRoutingModule,
        FeaturesModule,
        EngCoreLibModule.forRoot({
            loggerServiceConfig: {
                level: environment.logger.level,
                hasRemote: environment.logger.hasRemote,
                remoteLogUrl: environment.logger.remoteLogUrl
            },
            apiServiceConfig: {
                testConf: 'testConf'
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
        })
    ],
    providers: [MenuLoader],
    bootstrap: [AppComponent]
})
export class AppModule {}
