import { NgModule } from '@angular/core';
import { SharedModule } from '@app/shared';
import { SharedPrivateModule } from '../shared';

import { AccreditationComponent } from '@features/private/accreditation/main/accreditation/accreditation.component';
import { PendingAccreditationPageComponent } from './components/pending-accreditation-page/pending-accreditation-page.component';
import { PendingListPageComponent } from './components/pending-list-page/pending-list-page.component';
import { AccreditationRoutingModule } from './routing/accreditation.routing';
import { FileService } from '@common';

@NgModule({
    imports: [SharedModule, SharedPrivateModule, AccreditationRoutingModule],
    declarations: [
        AccreditationComponent,
        PendingAccreditationPageComponent,
        PendingListPageComponent
    ],
    providers: [FileService]
})
export class AccreditationModule {}
