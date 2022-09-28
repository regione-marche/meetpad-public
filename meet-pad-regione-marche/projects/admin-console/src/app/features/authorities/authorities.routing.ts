import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MainProceedingComponent } from './main/main.component';

const routes: Routes = [
    {
        path: '',
        component: MainProceedingComponent,
        children: [
            {
                path: 'edit',
                data: {
                    hasMenu: true
                },
                loadChildren:
                    './edit-authorities/edit-authorities.module#EditAuthoritiesModule'
            }
        ]
    }
];

@NgModule({
    providers: [],
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class AuthoritiesRoutingModule {}
