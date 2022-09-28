import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import {
    ConferenceCreateGuard,
    ConferenceDetailGuard,
    ConferenceStepGuard
} from './guards';

import {
    ConferenceBreadcrumbResolver,
    ConferenceContactsResolver,
    ConferenceDocumentsResolver,
    ConferenceParticipantsResolver,
    ConferenceResolver
} from './resolve/';

import { ConferenceComponent } from '../main/conference.component';

const routes: Routes = [
    {
        path: 'paleo',
        loadChildren: '../features/paleo/paleo.module#PaleoModule',
        data: { initStore: false },
        canActivate: [ConferenceCreateGuard]
    },
    {
        path: 'domus',
        loadChildren: '../features/domus/domus.module#DomusModule',
        data: { initStore: false },
        canActivate: [ConferenceCreateGuard]
    },
    {
        path: '',
        component: ConferenceComponent,
        canActivate: [ConferenceCreateGuard],
        data: {
            breadcrumb: 'CONFERENCE.ADD.TITLE',
            initStore: true,
            openModal: true,
            hasMenu: true
        },
        children: [
            {
                path: 'procedure',
                data: {
                    hasMenu: true,
                    openModal: false
                },
                loadChildren:
                    '../features/procedure/procedure-routing.module#ProcedureWithRoutingModule'
            }
        ]
    },
    {
        path: ':id',
        component: ConferenceComponent,
        canActivate: [ConferenceDetailGuard],
        resolve: {
            breadcrumb: ConferenceBreadcrumbResolver
        },
        data: { initStore: false },
        children: [
            {
                path: '',
                resolve: {
                    conference: ConferenceResolver
                },
                data: {
                    hasMenu: true,
                    initStore: false
                }
            },
            {
                path: 'procedure',
                data: {
                    hasMenu: true,
                    initStore: false,
                    openModal: false
                },
                loadChildren:
                    '../features/procedure/procedure-routing.module#ProcedureWithRoutingModule',
                canActivate: [ConferenceStepGuard]
            },
            {
                path: 'definition',
                loadChildren:
                    '../features/definition/definition-routing.module#DefinitionWithRoutingModule',
                data: {
                    hasMenu: true,
                    initStore: false,
                    openModal: false
                },
                resolve: {
                    contacts: ConferenceContactsResolver
                },
                canActivate: [ConferenceStepGuard]
            },
            {
                path: 'participant',
                loadChildren:
                    '../features/participants/participants-routing.module#ParticipantsWithRoutingModule',
                resolve: {
                    participant: ConferenceParticipantsResolver
                },
                data: {
                    hasMenu: true,
                    initStore: false
                },
                canActivate: [ConferenceStepGuard]
            },
            {
                path: 'documentation',
                loadChildren:
                    '../features/documentation/documentation-routing.module#DocumentationWithRoutingModule',
                resolve: {
                    documents: ConferenceDocumentsResolver
                },
                canActivate: [ConferenceStepGuard],
                data: {
                    hasMenu: true,
                    initStore: false
                }
            },
            {
                path: 'summary',
                loadChildren:
                    '../features/summary/summary.module#SummaryModule',
                data: {
                    hasMenu: true
                },
                resolve: {
                    documents: ConferenceDocumentsResolver
                },
                canActivate: [ConferenceStepGuard]
            },
            {
                path: 'accreditation',
                loadChildren:
                    '../features/accreditation/accreditation-routing.module#AccreditationTabWithRoutingModule',
                data: {
                    hasMenu: true,
                    initStore: false
                },
                canActivate: [ConferenceStepGuard]
            },
            {
                path: 'pec',
                loadChildren:
                    '../features/pec/pec-routing.module#PecWithRoutingModule',
                data: {
                    hasMenu: true,
                    initStore: false
                },
                canActivate: [ConferenceStepGuard]
            },
            {
                path: 'event',
                loadChildren:
                    '../features/events/events-routing.module#EventsWithRoutingModule',
                data: {
                    hasMenu: true,
                    initStore: false
                },
                canActivate: [ConferenceStepGuard]
            },
            {
                path: 'votings',
                loadChildren:
                    '../features/voting/voting-routing.module#VotingRoutingModule',
                data: {
                    hasMenu: true,
                    initStore: false
                },
                canActivate: [ConferenceStepGuard]
            },
            {
                path: 'protocol',
                loadChildren:
                    '../features/protocol/protocol-routing.module#ProtocolModuleWithRoutingModule',
                data: {
                    hasMenu: true,
                    initStore: false
                },
                canActivate: [ConferenceStepGuard]
            }
        ]
    }
];

@NgModule({
    providers: [
        ConferenceCreateGuard,
        ConferenceDetailGuard,
        ConferenceStepGuard,
        ConferenceResolver,
        ConferenceParticipantsResolver,
        ConferenceDocumentsResolver,
        ConferenceContactsResolver,
        ConferenceBreadcrumbResolver
    ],
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ConferenceRoutingModule {}
