import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { MenuGuard, NotFoundComponent } from '@common';
import { MediaMainComponent } from './main/media-main.component';

const routes: Routes = [
    {
        path: '',
        component: MediaMainComponent,
        data: { hasMenu: true, breadcrumb: 'MEDIA_LIBRARY.TITLE' },
        canActivate: [MenuGuard]
    },
    {
        path: '**',
        component: NotFoundComponent,
        data: {
            breadcrumb: 'PAGE_NOT_FOUND.TITLE'
        }
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class MediaRoutingModule {}
