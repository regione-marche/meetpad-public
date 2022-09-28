import { NgModule } from '@angular/core';

import { SharedModule } from '@app/shared';

import { PublicRoutingModule } from './routing/public.routing';

import { DashboardModule } from './dashboard/dashboard.module';
import { PublicComponent } from './main/public.component';

@NgModule({
    imports: [SharedModule, DashboardModule, PublicRoutingModule],
    declarations: [PublicComponent]
})
export class PublicModule {}
