import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

import { SharedModule } from '@app/shared';
import { SharedPrivateModule } from '@app/features/private/shared';

import { PaleoComponent } from './main/paleo.component';

const routes: Routes = [
    {
        path: '',
        component: PaleoComponent,
        data: { breadcrumb: 'CONFERENCE.PALEO.TITLE' }
    }
];

@NgModule({
    imports: [
        CommonModule,
        RouterModule.forChild(routes),
        SharedModule,
        SharedPrivateModule
    ],
    declarations: [PaleoComponent]
})
export class PaleoModule {}
