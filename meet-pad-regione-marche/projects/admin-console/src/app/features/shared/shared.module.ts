import { NgModule } from '@angular/core';
import { CommonModule } from '@common';
import { ConferenceSearchComponent } from './components/conference-search/conference-search.component';
import { ConferenceListComponent } from './components/conference-list/conference-list.component';

@NgModule({
    imports: [CommonModule],
    declarations: [ConferenceSearchComponent, ConferenceListComponent],
    exports: [ConferenceSearchComponent, ConferenceListComponent]
})
export class SharedFeaturesModule {}
