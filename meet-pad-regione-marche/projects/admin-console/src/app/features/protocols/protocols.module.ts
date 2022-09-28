import { NgModule } from '@angular/core';
import { CommonModule, FileService } from '@common';
import { MainProtocolComponent } from './main/main.component';
import { SharedFeaturesModule } from '../shared/shared.module';
import { ProtocolListComponent } from './components/protocol-list/protocol-list.component';
import { ProtocolSearchComponent } from './components/protocol-search/protocol-search.component';
import { ProtocolService } from './core/services/protocol.services';

@NgModule({
    imports: [CommonModule, SharedFeaturesModule],
    declarations: [
        MainProtocolComponent,
        ProtocolListComponent,
        ProtocolSearchComponent
    ],
    providers: [ProtocolService]
})
export class ProtocolModule {}