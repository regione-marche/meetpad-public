import { NgModule } from '@angular/core';

import { SharedModule } from '@app/shared';

import { SharedPrivateModule } from '@features/private/shared';
import { MediaSearchComponent } from './components/search/media-search.component';
import { MediaMainComponent } from './main/media-main.component';
import { MediaTableComponent } from './components/table/media-table.component';
import { MediaRoutingModule } from './media-routing.module';
import { MediaLibraryService } from './services/media-library.service';

@NgModule({
    imports: [SharedModule, SharedPrivateModule, MediaRoutingModule],
    declarations: [
        MediaMainComponent,
        MediaSearchComponent,
        MediaTableComponent
    ],
    providers: [MediaLibraryService]
})
export class MediaLibraryModule {}
