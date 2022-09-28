import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { MenuGuard, NotFoundComponent } from '@common';

import { MainComponent } from '../dashboard/main/main.component';
import { PublicComponent } from '../main/public.component';

// attualmente c'è un solo modulo pubblico
// quindi lo gestisco direttamente da qui
const routes: Routes = [
    {
        path: '',
        component: PublicComponent,
        children: [
            {
                path: '',
                component: MainComponent,
                data: { hasMenu: true, breadcrumb: 'HOME' },
                canActivate: [MenuGuard]
            },
            {
                path: '**',
                component: NotFoundComponent,
                data: {
                    breadcrumb: 'PAGE_NOT_FOUND.TITLE'
                }
            }
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class PublicRoutingModule {}
