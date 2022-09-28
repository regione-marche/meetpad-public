import { NgModule } from '@angular/core';
import { SharedModule } from '@app/shared';
import { ConferenceService } from '@app/core';

import { SearchComponent } from './components';
import { SearchComponent as SearchPage } from './main/search.component';
import { SharedPrivateModule } from '@features/private/shared';

@NgModule({
    imports: [SharedModule, SharedPrivateModule],
    declarations: [SearchPage, SearchComponent],
    providers: [ConferenceService]
})
export class SearchModule {}
