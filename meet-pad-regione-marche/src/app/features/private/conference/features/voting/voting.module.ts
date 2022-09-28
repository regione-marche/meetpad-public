import { NgModule } from '@angular/core';
import { VotingComponent } from './voting/voting.component';
import { SharedModule } from '@app/shared';
import { SharedPrivateModule } from '@app/features/private/shared';
import { VotingTableComponent } from './components/voting-table/voting-table.component';
import { MessageToastrService } from '@common';

@NgModule({
    imports: [SharedModule, SharedPrivateModule],
    declarations: [VotingComponent, VotingTableComponent],
    exports: [VotingComponent],
    providers: [MessageToastrService]
})
export class VotingModule {}
