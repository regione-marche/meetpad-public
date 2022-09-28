import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MainEditProceedingComponent } from './main/main.component';

const routes: Routes = [
    {
        path: ':id',
        data: {
            hasMenu: true,
            openModal: false
        }
    },
    {
        path: '',
        data: {
            hasMenu: true,
            openModal: false
        },
        component: MainEditProceedingComponent
    }
];

@NgModule({
    providers: [],
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class EditAuthoritiesRoutingModule {}
