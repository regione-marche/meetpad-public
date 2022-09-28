import { NgModule } from '@angular/core';
import { SharedModule } from '@app/shared';
import { SummaryComponent } from './main/summary.component';
import { ProcedureModule } from '../procedure/procedure.module';
import { DefinitionModule } from '../definition/definition.module';
import { ParticipantsModule } from '../participants';
import { DocumentationModule } from '../documentation';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { SharedPrivateModule } from '@app/features/private/shared';
const routes: Routes = [
    {
        path: '',
        component: SummaryComponent
    }
];

@NgModule({
    imports: [
        CommonModule,
        RouterModule.forChild(routes),
        SharedModule,
        SharedPrivateModule,
        ProcedureModule,
        DefinitionModule,
        ParticipantsModule,
        DocumentationModule
    ],

    declarations: [SummaryComponent],
    exports: [RouterModule, SummaryComponent]
})
export class SummaryModule {}
