import { ProcedureComponent } from './main/procedure.component';
import { NgModule } from '@angular/core';
import { SharedModule } from '@app/shared';
import { RouterModule, Routes } from '@angular/router';
import { SharedPrivateModule } from '@app/features/private/shared';

@NgModule({
    imports: [SharedModule, SharedPrivateModule],
    declarations: [ProcedureComponent],
    exports: [ProcedureComponent]
})
export class ProcedureModule {}
