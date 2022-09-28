import { NgModule, LOCALE_ID } from '@angular/core';
import localeIt from '@angular/common/locales/it';
import { registerLocaleData } from '@angular/common';

import { CalendarModule } from 'angular-calendar';

import { NgxEngToolkitModule } from '@eng-ds/ng-toolkit';

import { SharedModule } from '@app/shared';
import { ConferenceService } from '@app/core';

import { SearchModule } from './search';
import { DesktopModule } from './desktop';

import { PrivateRoutingModule } from './routing/private.routing';
import { AccreditationService } from './core/services/accreditation/accreditation.service';

import { PrivateComponent } from './main/private.component';
import { CalendarComponent } from './calendar/calendar.component';
import { RelevantInformationComponent } from './relevant-information/relevant-information.component';
import { NothingToDoComponent } from './nothing-to-do/nothing-to-do.component';
import { NotAuthorizedComponent } from './not-authorized/not-authorized.component';
import { CalendarService } from './calendar/services/calendar.service';
import { SignPanelComponent } from './sign/components/sign-panel/sign-panel.component';
import { SignTableComponent } from './sign/components/sign-table/sign-table.component';
import { SignComponent } from './sign/components/sign/sign.component';

registerLocaleData(localeIt);

@NgModule({
    imports: [
        SharedModule,
        SearchModule,
        DesktopModule,
        NgxEngToolkitModule,
        CalendarModule,
        PrivateRoutingModule
    ],
    declarations: [
        PrivateComponent,
        CalendarComponent,
        RelevantInformationComponent,
        NothingToDoComponent,
        NotAuthorizedComponent,
        SignComponent,
        SignTableComponent,
        SignPanelComponent
    ],
    providers: [
        ConferenceService,
        AccreditationService,
        CalendarService,
        { provide: LOCALE_ID, useValue: 'it' }
    ]
})
export class PrivateModule {}
