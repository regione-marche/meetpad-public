import { NgModule } from '@angular/core';
import { SharedModule } from '@app/shared';

import { SharedPrivateModule } from '@app/features/private/shared';
import { ConferenceSharedModule } from '../../shared';
import {
    EditModalFileComponent,
    FileTableComponent,
    FileTableSignComponent,
    FilePanelComponent,
    FilePanelSignComponent,
} from './components';

import { DocumentationComponent } from './main/documentation.component';

@NgModule({
    imports: [SharedModule, SharedPrivateModule, ConferenceSharedModule],
    declarations: [
        EditModalFileComponent,
        FileTableComponent,
        FileTableSignComponent,
        DocumentationComponent,
        FilePanelComponent,
        FilePanelSignComponent,
    ],
    exports: [DocumentationComponent]
})
export class DocumentationModule {}
