import { NgModule } from '@angular/core';
import { SharedModule } from '@app/shared';
import { SharedPrivateModule } from '@app/features/private/shared';

import { ProtocolTabComponent } from './protocol/protocol.component';
import { ProtocolTableComponent } from './components/protocolling-table/protocolling-table.component';

@NgModule({
    imports: [SharedModule, SharedPrivateModule],
    declarations: [ProtocolTableComponent, ProtocolTabComponent],
    exports: [ProtocolTabComponent]
})
export class ProtocolModule {}
