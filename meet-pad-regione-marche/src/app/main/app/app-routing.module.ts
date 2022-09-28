import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MenuGuard, HeaderGuard } from '@common';
import { PublicGuard, PrivateGuard } from '@app/core';
import { ContactComponent } from '@app/shared';
import { AppComponent } from './app.component';
import {OpendataComponent} from "@app/shared/components/opendata/opendata.component";

const appRoutes: Routes = [
    {
        path: '',
        component: AppComponent,
        children: [
            {
                path: 'contact',
                component: ContactComponent,
                data: { breadcrumb: 'CONTACT.TITLE', hasMenu: true },
                canActivate: [HeaderGuard, MenuGuard]
            },
            {
                path: 'opendata',
                component: OpendataComponent,
                data: { breadcrumb: 'OPENDATA.TITLE', hasMenu: true },
                canActivate: [HeaderGuard, MenuGuard]
            },
            {
                path: 'public',
                loadChildren:
                    '../../features/public/public.module#PublicModule',
                canActivateChild: [PublicGuard, HeaderGuard],
                data: { preload: true }
            },
            {
                path: '',
                loadChildren:
                    '../../features/private/private.module#PrivateModule',
                canActivateChild: [PrivateGuard],
                data: { preload: true }
            }
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(appRoutes)],
    exports: [RouterModule],
    providers: [PublicGuard, PrivateGuard]
})
export class AppRoutingModule {}
