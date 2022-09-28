import { NgModule } from '@angular/core';
import { SharedModule } from '@app/shared';
import { SharedPrivateModule } from '../../shared';
import { RouterModule } from '@angular/router';

import { AuthConferenceDirective } from './directives/auth/auth-conference.directive';

import {
    HeadComponent,
    ConferenceModalBeforeCreateComponent,
    AlertInfoComponent
} from './components';

@NgModule({
    imports: [SharedModule, SharedPrivateModule, RouterModule],
    declarations: [
        AuthConferenceDirective,
        ConferenceModalBeforeCreateComponent,
        HeadComponent,
        AlertInfoComponent
    ],
    exports: [
        AuthConferenceDirective,
        ConferenceModalBeforeCreateComponent,
        HeadComponent,
        AlertInfoComponent
    ]
})
export class ConferenceSharedModule {}
