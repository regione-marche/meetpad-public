import { NgModule } from '@angular/core';
import { AccreditationTabComponent } from '@features/private/conference/features';
import { Routes, RouterModule } from '@angular/router';
import { AccreditationTabModule } from './accreditation-tab.module';
const routes: Routes = [
    {
        path: '',
        component: AccreditationTabComponent
    }
];

@NgModule({
    imports: [AccreditationTabModule, RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class AccreditationTabWithRoutingModule {}
