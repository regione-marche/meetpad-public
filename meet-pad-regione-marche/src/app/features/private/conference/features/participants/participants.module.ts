import { NgModule } from '@angular/core';
import { SharedModule } from '@app/shared';
import { ParticipantsTableComponent } from './components';
import { ParticipantsComponent } from './main/participants.component';
import { SharedPrivateModule } from '@app/features/private/shared';

@NgModule({
    imports: [SharedModule, SharedPrivateModule],
    declarations: [ParticipantsTableComponent, ParticipantsComponent],
    exports: [ParticipantsComponent]
})
export class ParticipantsModule {}
