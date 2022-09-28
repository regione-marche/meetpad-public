import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { MenuGuard, NotFoundComponent } from '@common';

import { AccreditationComponent } from '@features/private/accreditation/main/accreditation/accreditation.component';

import { PendingAccreditationPageComponent } from '../components/pending-accreditation-page/pending-accreditation-page.component';
import { PendingListPageComponent } from '../components/pending-list-page/pending-list-page.component';
import { AccreditationResolver } from './accreditation.resolve';
import { JoinGuard } from './join.guard';

const routes: Routes = [
    // TODO: aggiungere guardia per evitare di entrare in questa pagina
    // se l'utente è censito oppure non esiste alcun accreditamento
    {
        path: 'pending/list',
        canActivate: [MenuGuard],
        component: PendingListPageComponent,
        data: {
            breadcrumb: 'CONFERENCE.JOIN.PENDING_LIST_PAGE.TITLE'
        }
    },
    {
        path: 'pending',
        canActivate: [MenuGuard],
        component: PendingAccreditationPageComponent,
        data: {
            hasMenu: true,
            breadcrumb: 'CONFERENCE.JOIN.PENDING_PAGE.TITLE'
        }
    },
    {
        path: ':token_1/:token_2',
        component: AccreditationComponent,
        data: {
            breadcrumb: 'CONFERENCE.JOIN.TITLE',
            readonly: true,
            hasMenu: false
        },
        canActivate: [JoinGuard, MenuGuard],
        resolve: {
            accreditationInfo: AccreditationResolver
        }
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
    providers: [AccreditationResolver, JoinGuard],
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class AccreditationRoutingModule {}
