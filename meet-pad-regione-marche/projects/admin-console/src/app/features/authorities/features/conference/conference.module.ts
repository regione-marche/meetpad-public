import { NgModule } from '@angular/core';
import { CommonModule } from '@common';
import { MainAuthoritiesConferenceComponent } from './main/main.component';
import { ConferenceService } from '../../core/services/conference.service';
import { ConferenceComponent } from './component/conference/conference.component';
import { SharedAdminModule } from '../../../../shared/shared.module';
import { SharedFeaturesModule } from '../../../shared/shared.module';

@NgModule({
    imports: [CommonModule, SharedAdminModule, SharedFeaturesModule],
    declarations: [MainAuthoritiesConferenceComponent, ConferenceComponent],
    providers: [ConferenceService],
    exports: [ConferenceComponent]
})
export class AuthoritiesConferenceModule {}
