import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MenuGuard, NotFoundComponent, HeaderGuard } from '@common';
import { PendingAccreditationGuard, UserGuard } from '@app/core';

import { SearchComponent as SearchPage } from '../search/main/search.component';
import { PrivateComponent } from '../main/private.component';
import { DesktopComponent } from '../desktop/components/desktop/desktop.component';
import { CalendarComponent } from '../calendar/calendar.component';
import { RelevantInformationComponent } from '../relevant-information/relevant-information.component';
import { NothingToDoComponent } from '../nothing-to-do/nothing-to-do.component';
import { NotAuthorizedComponent } from '../not-authorized/not-authorized.component';

import { CalendarResolver } from './resolve/calendar.resolve';
import { SignComponent } from '../sign/components/sign/sign.component';

const routes: Routes = [
    {
        path: '',
        component: PrivateComponent,
        children: [
            {
                path: '',
                component: DesktopComponent,
                canActivate: [
                    UserGuard,
                    PendingAccreditationGuard,
                    MenuGuard,
                    HeaderGuard
                ],
                data: { breadcrumb: 'DESKTOP.TITLE', hasMenu: true }
            },
            {
                path: 'search',
                component: SearchPage,
                canActivate: [
                    UserGuard,
                    PendingAccreditationGuard,
                    MenuGuard,
                    HeaderGuard
                ],
                data: { breadcrumb: 'SEARCH.TITLE', hasMenu: true }
            },
            {
                path: 'conference',
                canActivateChild: [
                    UserGuard,
                    PendingAccreditationGuard,
                    MenuGuard,
                    HeaderGuard
                ],
                loadChildren:
                    '../conference/conference.module#ConferenceModule',
                data: { preload: true, hasMenu: true, delay: 2000 }
            },
            {
                path: 'join',
                loadChildren:
                    '../accreditation/accreditation.module#AccreditationModule',
                canActivate: [UserGuard, HeaderGuard],
                data: { preload: false, hasMenu: false }
            },
            {
                path: 'calendar',
                data: { breadcrumb: 'CALENDAR.TITLE', hasMenu: true },
                canActivate: [
                    UserGuard,
                    PendingAccreditationGuard,
                    MenuGuard,
                    HeaderGuard
                ],
                resolve: {
                    events: CalendarResolver
                },
                component: CalendarComponent
            },
            {
                path: 'media-library',
                loadChildren:
                    '../media-library/media-library.module#MediaLibraryModule',
                data: {
                    hasMenu: true,
                    preload: true,
                    delay: 2000
                },
                canActivate: [
                    UserGuard,
                    PendingAccreditationGuard,
                    MenuGuard,
                    HeaderGuard
                ]               
            },
            {
                path: 'sign',
                component: SignComponent,
                data: { breadcrumb: 'SIGN.TITLE', hasMenu: true },
                canActivate: [
                    UserGuard,
                    PendingAccreditationGuard,
                    MenuGuard,
                    HeaderGuard
                ]
            },
            {
                path: 'information',
                data: { breadcrumb: 'INFORMATION.TITLE', hasMenu: true },
                canActivate: [
                    UserGuard,
                    PendingAccreditationGuard,
                    MenuGuard,
                    HeaderGuard
                ],
                component: RelevantInformationComponent
            },
            // TODO: inserire una guardia che impedisca l'accesso a questa pagina in condizioni normali
            {
                path: 'nothing-to-do',
                component: NothingToDoComponent,
                canActivate: [UserGuard],
                data: {
                    breadcrumb: 'PAGE_NOTHING_TO_DO.TITLE'
                }
            },
            // TODO: inserire una guardia che impedisca l'accesso a questa pagina in condizioni normali
            {
                path: 'not-authorized',
                component: NotAuthorizedComponent,
                canActivate: [UserGuard],
                data: {
                    breadcrumb: 'PAGE_NOT_AUTHORIZED.TITLE'
                }
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
    providers: [UserGuard, PendingAccreditationGuard, CalendarResolver],
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class PrivateRoutingModule {}
