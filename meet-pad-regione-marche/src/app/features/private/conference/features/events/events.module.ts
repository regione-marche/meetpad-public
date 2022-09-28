import { NgModule } from '@angular/core';

import { SharedModule } from '@app/shared';
import { SharedPrivateModule } from '@app/features/private/shared';

import { EventsTableComponent } from './components';
import { EventGroupFieldsService, EventStoreService } from './services';
import { EventsComponent } from './main/events.component';
import { EventsSearchComponent } from './components/events-search/events-search.component';

@NgModule({
    imports: [SharedModule, SharedPrivateModule],
    declarations: [
        EventsTableComponent,
        EventsComponent,
        EventsSearchComponent
    ],
    exports: [EventsComponent],
    providers: [EventStoreService, EventGroupFieldsService]
})
export class EventsModule {}
