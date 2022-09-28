import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthComponent } from '@common';
import { CustomPreloadingStrategy, AuthAdminGuard } from '@app/core';

const appRoutes: Routes = [
    {
        path: 'admin',
        canActivate: [AuthAdminGuard],
        loadChildren: './admin-console.module#AdminConsoleModuleLazy',
        data: { preload: false }
    },
    {
        path: 'auth',
        component: AuthComponent
    },
    {
        path: '',
        loadChildren: './app/app.module#AppModule',
        data: { preload: false, delay: 5000 }
    }
];

@NgModule({
    providers: [AuthAdminGuard],
    imports: [
        RouterModule.forRoot(appRoutes, {
            useHash: false,
            preloadingStrategy: CustomPreloadingStrategy,
            onSameUrlNavigation: 'reload',
            scrollPositionRestoration: 'top',
            //anchorScrolling: 'enabled'
            // enableTracing: true
        })
    ],
    exports: [RouterModule]
})
export class MainRoutingModule {}
