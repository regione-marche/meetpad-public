import { NgModule } from '@angular/core';

import { EventsComponent } from './main/events.component';
import { Routes, RouterModule } from '@angular/router';
import { EventsModule } from './events.module';
const routes: Routes = [
    {
        path: '',
        component: EventsComponent
    }
];

@NgModule({
    imports: [EventsModule, RouterModule.forChild(routes)],
    exports: [EventsComponent]
})
export class EventsWithRoutingModule {}
