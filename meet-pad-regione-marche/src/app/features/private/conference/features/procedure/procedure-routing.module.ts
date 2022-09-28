import { ProcedureComponent } from './main/procedure.component';
import { NgModule } from '@angular/core';
import { SharedModule } from '@app/shared';
import { RouterModule, Routes } from '@angular/router';
import { ProcedureModule } from './procedure.module';

const routes: Routes = [
    {
        path: '',
        component: ProcedureComponent
    }
];

@NgModule({
    imports: [SharedModule, RouterModule.forChild(routes), ProcedureModule],
    exports: [RouterModule]
})
export class ProcedureWithRoutingModule {}
