import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

import { SharedModule } from '@app/shared';
import { SharedPrivateModule } from '@app/features/private/shared';

import { DomusComponent } from './main/domus.component';

const routes: Routes = [
    {
        path: '',
        component: DomusComponent,
        data: { breadcrumb: 'CONFERENCE.DOMUS.TITLE' }
    }
];

@NgModule({
    imports: [
        CommonModule,
        RouterModule.forChild(routes),
        SharedModule,
        SharedPrivateModule
    ],
    declarations: [DomusComponent]
})
export class DomusModule {}
