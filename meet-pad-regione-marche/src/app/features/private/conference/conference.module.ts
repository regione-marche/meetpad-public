import { NgModule } from '@angular/core';

import { MessageService } from '@eng-ds/ng-toolkit';

import { SharedModule } from '@app/shared';

import {
    ParticipantsService,
    ConferenceStoreService,
    EventsService,
    ConferencePermissionsService,
    VotingService
} from './core';

import { SharedPrivateModule } from '../shared';
import { ConferenceRoutingModule } from './routing/conference.routing';
import { ConferenceSharedModule } from './shared';
import { ConferenceComponent } from './main/conference.component';
import { FileService } from '@common';

@NgModule({
    imports: [
        SharedModule,
        SharedPrivateModule,
        ConferenceRoutingModule,
        ConferenceSharedModule
    ],
    declarations: [ConferenceComponent],
    providers: [
        ConferencePermissionsService,
        MessageService,
        ParticipantsService,
        FileService,
        VotingService,
        EventsService,
        ConferenceStoreService
    ]
})
export class ConferenceModule {}
