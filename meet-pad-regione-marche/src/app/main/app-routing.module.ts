import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DesktopComponent } from '@app/features/desktop/main/desktop.component';
import { PageNotFoundComponent } from '@eng-ds/ng-toolkit';


const appRoutes: Routes = [
    {
      path: '',
      component: DesktopComponent,
      data: { breadcrumb: 'SCRIVANIA.TITLE' }
    },
    { path: '**', component: PageNotFoundComponent }
  ];

@NgModule({
    imports: [
      RouterModule.forRoot(appRoutes,  { useHash: false })
    ],
    exports: [
      RouterModule
    ],
    declarations: []
  })
export class AppRoutingModule { }
