import { NgModule } from '@angular/core';
import { SharedModule } from '@app/shared';
import { PecComponent } from './main/pec.component';
import { Routes, RouterModule } from '@angular/router';
import { PecModule } from './pec.module';

const routes: Routes = [
    {
        path: '',
        component: PecComponent
    }
];

@NgModule({
    imports: [SharedModule, PecModule, RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class PecWithRoutingModule {}
