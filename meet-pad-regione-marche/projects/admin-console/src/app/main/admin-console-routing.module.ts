import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AdminConsoleComponent } from './admin-console.component';
import {
    MenuGuard,
    NotFoundComponent,
    HeaderGuard,
    UploadStatusComponent
} from '@common';
import { MainProceedingComponent } from '../features/authorities/main/main.component';
import { MainEditProceedingComponent } from '../features/authorities/features/edit-authorities/main/main.component';
import { MainManagerComponent } from '../features/authorities/features/manager/main/main.component';
import { AuthorityProceedingResolver } from '../shared/routing/authorityProceeding.resolve';
import { ManagerComponent } from '../features/authorities/features/manager/components/manager/manager.component';
import { AuthoritiesProceedingComponent } from '../features/authorities/features/edit-authorities/components/proceeding/proceeding.component';
import { MainAuthoritiesConferenceComponent } from '../features/authorities/features/conference/main/main.component';
import { MainUsersComponent } from '../features/users/features/main/main.component';
import { UsersComponent } from '../features/users/features/components/users/users.component';
import { UserResolver } from '../shared/routing/user.resolve';
import { ConferenceComponent } from '../features/authorities/features/conference/component/conference/conference.component';
import { ConferenceResolver } from '../shared/routing/conference.resolve';
import { MainPreloadingComponent } from '../features/preloading/main/main.component';
import { ApplicantMainComponent } from '../features/preloading/features/applicant/main.component';
import { ParticipantMainComponent } from '../features/preloading/features/participant/main.component';
import { CompanyMainComponent } from '../features/preloading/features/company/main.component';
import { CompanyComponent } from '../features/preloading/features/company/components/company/company.component';
import { MainComponent } from '../features/main/main.component';
import { ParticipantComponent } from '../features/preloading/features/participant/components/participant/participant.component';
import { ParticipantResolver } from '../shared/routing/participant.resolve';
import { CompanyResolver } from '../shared/routing/company.resolve';
import { ApplicantComponent } from '../features/preloading/features/applicant/components/applicant/applicant.component';
import { ApplicantResolver } from '../shared/routing/applicant.resolve';
import { SendMailComponent } from '../features/conference/components/send-mail/send-mail.component';
import { MainAuthoritiesComponent } from '../features/authorities/features/authorities/main/main.component';
import { AuthorityComponent } from '../features/authorities/features/authorities/components/authority/authority.component';
import { AuthorityResolver } from '../shared/routing/authority.resolve';
import { MainConferenceComponent } from '../features/conference/main/main.component';
import { UploadFileComponent } from '../features/conference/components/upload-file/upload-file.component';
import { DelegateMainComponent } from '../features/preloading/features/delegate/main.component';
import { DelegateComponent } from '../features/preloading/features/delegate/components/delegate/delegate.component';
import { DelegateResolver } from '../shared/routing/delegate.resolve';
import { MainProtocolComponent } from '../features/protocols/main/main.component';
import { PreaccreditationMainComponent } from '../features/preloading/features/preaccreditation/main.component';
import { PreaccreditationResolver } from '../shared/routing/preaccreditation.resolve';
import { PreaccreditationComponent } from '../features/preloading/features/preaccreditation/components/preaccreditation/preaccreditation.component';
import { ChangeStateComponent } from '../features/conference/components/change-state/change-state.component';


const routes: Routes = [
    {
        path: '',
        component: AdminConsoleComponent,
        canActivate: [HeaderGuard, MenuGuard],
        data: { breadcrumb: 'DASHBOARD.TITLE', hasMenu: true },
        children: [
            {
                path: '',
                component: MainComponent,
                canActivate: [MenuGuard],
                data: { breadcrumb: '', hasMenu: true }
            },
            {
                path: 'upload-status',
                component: UploadStatusComponent,
                canActivate: [MenuGuard],
                data: { breadcrumb: 'UPLOAD.BREADCRUMB', hasMenu: true }
            },
            {
                path: 'authorities',
                canActivate: [MenuGuard],
                data: { breadcrumb: '', hasMenu: true },
                children: [
                    {
                        path: 'proceeding',
                        canActivate: [MenuGuard],
                        data: {
                            breadcrumb: 'AUTHORITIES.PROCEDING.TITLE',
                            hasMenu: true
                        },
                        children: [
                            {
                                path: '',
                                canActivate: [MenuGuard],
                                data: {
                                    breadcrumb: '',
                                    hasMenu: true
                                },
                                component: MainProceedingComponent
                            },
                            {
                                path: 'edit',
                                canActivate: [MenuGuard],
                                data: {
                                    breadcrumb:
                                        'AUTHORITIES.PROCEDING.EDIT.TITLE',
                                    hasMenu: true
                                },
                                children: [
                                    {
                                        path: '',
                                        canActivate: [MenuGuard],
                                        data: {
                                            breadcrumb: '',
                                            hasMenu: true
                                        },
                                        component: MainEditProceedingComponent
                                    },
                                    {
                                        path: ':id',
                                        canActivate: [MenuGuard],
                                        data: {
                                            breadcrumb: '',
                                            hasMenu: true
                                        },
                                        resolve: {
                                            authority: AuthorityProceedingResolver
                                        },
                                        component: AuthoritiesProceedingComponent
                                    },
                                    {
                                        path: '*',
                                        component: NotFoundComponent
                                    }
                                ]
                            },
                            {
                                path: 'manager',
                                canActivate: [MenuGuard],
                                data: {
                                    breadcrumb:
                                        'AUTHORITIES.PROCEDING.MANAGER.TITLE',
                                    hasMenu: true
                                },
                                children: [
                                    {
                                        path: '',
                                        component: MainManagerComponent,
                                        canActivate: [MenuGuard],
                                        data: {
                                            breadcrumb: '',
                                            hasMenu: true
                                        }
                                    },
                                    {
                                        path: 'new',
                                        component: ManagerComponent,
                                        canActivate: [MenuGuard],
                                        data: {
                                            breadcrumb:
                                                'AUTHORITIES.PROCEDING.MANAGER.EDIT.BREADCRUMB.NEW',
                                            hasMenu: true
                                        }
                                    },
                                    {
                                        path: '*',
                                        redirectTo: ''
                                    }
                                ]
                            },
                            {
                                path: 'conference',
                                canActivate: [MenuGuard],
                                data: {
                                    breadcrumb:
                                        'AUTHORITIES.PROCEDING.CONFERENCE.TITLE',
                                    hasMenu: true
                                },
                                children: [
                                    {
                                        path: '',
                                        component: MainAuthoritiesConferenceComponent,
                                        canActivate: [MenuGuard],
                                        data: {
                                            breadcrumb: '',
                                            hasMenu: true
                                        }
                                    },
                                    {
                                        path: ':id',
                                        canActivate: [MenuGuard],
                                        data: {
                                            breadcrumb: '',
                                            hasMenu: true
                                        },
                                        children: [
                                            {
                                                path: '',
                                                component: ConferenceComponent,
                                                canActivate: [MenuGuard],
                                                resolve: {
                                                    conference: ConferenceResolver
                                                },
                                                data: {
                                                    breadcrumb: '',
                                                    hasMenu: true
                                                }
                                            }
                                        ]
                                    },
                                    {
                                        path: '*',
                                        component: NotFoundComponent
                                    }
                                ]
                            }
                        ]
                    },
                    {
                        path: '',
                        canActivate: [MenuGuard],
                        data: {
                            breadcrumb: 'AUTHORITIES.EDIT.TITLE',
                            hasMenu: true
                        },
                        children: [
                            {
                                path: '',
                                canActivate: [MenuGuard],
                                data: {
                                    breadcrumb: '',
                                    hasMenu: true
                                },
                                component: MainAuthoritiesComponent
                            },

                            {
                                path: 'new',
                                canActivate: [MenuGuard],
                                data: {
                                    breadcrumb:
                                        'AUTHORITIES.EDIT.BREADCRUMB.NEW',
                                    hasMenu: true
                                },
                                component: AuthorityComponent
                            },
                            {
                                path: ':id',
                                canActivate: [MenuGuard],
                                data: {
                                    breadcrumb:
                                        'AUTHORITIES.EDIT.BREADCRUMB.EDIT',
                                    hasMenu: true
                                },
                                resolve: {
                                    authority: AuthorityResolver
                                },
                                component: AuthorityComponent
                            },
                            {
                                path: '*',
                                component: NotFoundComponent
                            }
                        ]
                    },
                    {
                        path: '*',
                        component: NotFoundComponent
                    }
                ]
            },
            {
                path: 'conference',
                canActivate: [MenuGuard],
                data: {
                    breadcrumb: 'CONFERENCE.TITLE',
                    hasMenu: true
                },
                children: [
                    {
                        path: '',
                        component: MainConferenceComponent,
                        canActivate: [MenuGuard],
                        data: {
                            breadcrumb: '',
                            hasMenu: true
                        }
                    },
                    {
                        path: ':id',
                        canActivate: [MenuGuard],
                        data: {
                            breadcrumb: '',
                            hasMenu: true
                        },
                        children: [
                            {
                                path: 'sendMail',
                                component: SendMailComponent,
                                canActivate: [MenuGuard],
                                data: {
                                    breadcrumb:
                                        'CONFERENCE.SEND_MAIL.BREADCRUMB',
                                    hasMenu: true
                                }
                            },
                            {
                                path: 'upload',
                                component: UploadFileComponent,
                                canActivate: [MenuGuard],
                                resolve: { conference: ConferenceResolver },
                                data: {
                                    breadcrumb: 'CONFERENCE.UPLOAD.BREADCRUMB',
                                    hasMenu: true
                                }
                            },
                            {
                                path: 'changeState',
                                component: ChangeStateComponent,
                                canActivate: [MenuGuard],
                                data: {
                                    breadcrumb:
                                        'CONFERENCE.CHANGE_STATE.BREADCRUMB',
                                    hasMenu: true
                                }
                            }
                        ]
                    },
                    {
                        path: '*',
                        component: NotFoundComponent
                    }
                ]
            },
            {
                path: 'users',
                canActivate: [MenuGuard],
                data: {
                    breadcrumb: 'USERS.TITLE',
                    hasMenu: true
                },
                children: [
                    {
                        path: '',
                        component: MainUsersComponent,
                        canActivate: [MenuGuard],
                        data: {
                            breadcrumb: '',
                            hasMenu: true
                        }
                    },
                    {
                        path: 'new',
                        component: UsersComponent,
                        canActivate: [MenuGuard],
                        data: {
                            breadcrumb: 'USERS.EDIT.BREADCRUMB.NEW',
                            hasMenu: true
                        }
                    },
                    {
                        path: ':id',
                        component: UsersComponent,
                        canActivate: [MenuGuard],
                        resolve: { user: UserResolver },
                        data: {
                            breadcrumb: 'USERS.EDIT.BREADCRUMB.EDIT',
                            hasMenu: true
                        }
                    },
                    {
                        path: '*',
                        component: NotFoundComponent
                    }
                ]
            },
            {
                path: 'protocols',
                canActivate: [MenuGuard],
                data: {
                    breadcrumb: 'PROTOCOLS.TITLE',
                    hasMenu: true
                },
                children: [
                    {
                        path: '',
                        component: MainProtocolComponent,
                        canActivate: [MenuGuard],
                        data: {
                            breadcrumb: '',
                            hasMenu: true
                        }
                    },
                    {
                        path: ':id',
                        canActivate: [MenuGuard],
                        data: {
                            breadcrumb: '',
                            hasMenu: true
                        },
                    },
                    {
                        path: '*',
                        component: NotFoundComponent
                    }
                ]
            },
            {
                path: 'preloading',
                canActivate: [MenuGuard],
                data: { breadcrumb: 'PRELOADING.TITLE', hasMenu: true },
                children: [
                    {
                        path: '',
                        component: MainPreloadingComponent,
                        canActivate: [MenuGuard],
                        data: {
                            breadcrumb: '',
                            hasMenu: true
                        }
                    },
                    {
                        path: ':conferenceType',
                        canActivate: [MenuGuard],
                        data: {
                            breadcrumb: '',
                            hasMenu: true
                        },
                        children: [
                            {
                                path: 'applicants',
                                canActivate: [MenuGuard],
                                data: {
                                    breadcrumb: 'PRELOADING.APPLICANT.TITLE',
                                    hasMenu: true
                                },
                                children: [
                                    {
                                        path: '',
                                        component: ApplicantMainComponent,
                                        canActivate: [MenuGuard],
                                        data: {
                                            breadcrumb: '',

                                            hasMenu: true
                                        }
                                    },
                                    {
                                        path: 'new',
                                        component: ApplicantComponent,
                                        canActivate: [MenuGuard],
                                        data: {
                                            breadcrumb:
                                                'PRELOADING.APPLICANT.EDIT.BREADCRUMB.NEW',
                                            hasMenu: true
                                        }
                                    },
                                    {
                                        path: ':id',
                                        component: ApplicantComponent,
                                        canActivate: [MenuGuard],
                                        resolve: {
                                            applicant: ApplicantResolver
                                        },
                                        data: {
                                            breadcrumb:
                                                'PRELOADING.APPLICANT.EDIT.BREADCRUMB.EDIT',
                                            hasMenu: true
                                        }
                                    }
                                ]
                            },
                            {
                                path: 'participants',
                                canActivate: [MenuGuard],
                                data: {
                                    breadcrumb: 'PRELOADING.PARTICIPANT.TITLE',
                                    hasMenu: true
                                },
                                children: [
                                    {
                                        path: '',
                                        component: ParticipantMainComponent,
                                        canActivate: [MenuGuard],
                                        data: {
                                            breadcrumb: '',
                                            hasMenu: true
                                        }
                                    },
                                    {
                                        path: 'new',
                                        component: ParticipantComponent,
                                        canActivate: [MenuGuard],
                                        data: {
                                            breadcrumb:
                                                'PRELOADING.PARTICIPANT.EDIT.BREADCRUMB.NEW',
                                            hasMenu: true
                                        }
                                    },
                                    {
                                        path: ':id',
                                        component: ParticipantComponent,
                                        canActivate: [MenuGuard],
                                        resolve: {
                                            participant: ParticipantResolver
                                        },

                                        data: {
                                            breadcrumb:
                                                'PRELOADING.PARTICIPANT.EDIT.BREADCRUMB.EDIT',
                                            hasMenu: true
                                        }
                                    }
                                ]
                            },
                            {
                                path: 'companies',
                                canActivate: [MenuGuard],
                                data: {
                                    breadcrumb: 'PRELOADING.COMPANY.TITLE',
                                    hasMenu: true
                                },
                                children: [
                                    {
                                        path: '',
                                        component: CompanyMainComponent,
                                        canActivate: [MenuGuard],
                                        data: {
                                            breadcrumb: '',
                                            hasMenu: true
                                        }
                                    },
                                    {
                                        path: 'new',
                                        component: CompanyComponent,
                                        canActivate: [MenuGuard],
                                        data: {
                                            breadcrumb:
                                                'PRELOADING.COMPANY.EDIT.BREADCRUMB.NEW',
                                            hasMenu: true
                                        }
                                    },
                                    {
                                        path: ':id',
                                        component: CompanyComponent,
                                        canActivate: [MenuGuard],
                                        data: {
                                            breadcrumb:
                                                'PRELOADING.COMPANY.EDIT.BREADCRUMB.EDIT',
                                            hasMenu: true
                                        },
                                        resolve: {
                                            company: CompanyResolver
                                        }
                                    }
                                ]
                            },
                            {
                                path: 'delegates',
                                canActivate: [MenuGuard],
                                data: {
                                    breadcrumb: 'PRELOADING.DELEGATE.TITLE',
                                    hasMenu: true
                                },
                                children: [
                                    {
                                        path: '',
                                        component: DelegateMainComponent,
                                        canActivate: [MenuGuard],
                                        data: {
                                            breadcrumb: '',

                                            hasMenu: true
                                        }
                                    },
                                    {
                                        path: 'new',
                                        component: DelegateComponent,
                                        canActivate: [MenuGuard],
                                        data: {
                                            breadcrumb:
                                                'PRELOADING.DELEGATE.EDIT.BREADCRUMB.NEW',
                                            hasMenu: true
                                        }
                                    },
                                    {
                                        path: ':id',
                                        component: DelegateComponent,
                                        canActivate: [MenuGuard],
                                        resolve: {
                                            delegate: DelegateResolver
                                        },
                                        data: {
                                            breadcrumb:
                                                'PRELOADING.DELEGATE.EDIT.BREADCRUMB.EDIT',
                                            hasMenu: true
                                        }
                                    }
                                ]
                            },
                            {
                                path: 'preaccreditation',
                                canActivate: [MenuGuard],
                                data: {
                                    breadcrumb: 'PRELOADING.PREACCREDITATION.TITLE',
                                    hasMenu: true
                                },
                                children: [
                                    {
                                        path: '',
                                        canActivate: [MenuGuard],
                                        data: {
                                            breadcrumb: '',
                                            hasMenu: true
                                        },
                                        component: PreaccreditationMainComponent
                                    },
        
                                    {
                                        path: 'new',
                                        canActivate: [MenuGuard],
                                        data: {
                                            breadcrumb:
                                                'PRELOADING.PREACCREDITATION.BREADCRUMB.NEW',
                                            hasMenu: true
                                        },
                                        component: PreaccreditationComponent
                                    },
                                    {
                                        path: ':id',
                                        canActivate: [MenuGuard],
                                        data: {
                                            breadcrumb:
                                                'PRELOADING.PREACCREDITATION.BREADCRUMB.EDIT',
                                            hasMenu: true
                                        },
                                        resolve: {
                                            preaccreditation: PreaccreditationResolver
                                        },
                                        component: PreaccreditationComponent
                                    },
                                    {
                                        path: '*',
                                        component: NotFoundComponent
                                    }
                                ]
                            }
                        ]
                    }
                ]
            }
        ]
    }
];

@NgModule({
    providers: [
        HeaderGuard,
        AuthorityProceedingResolver,
        AuthorityResolver,
        UserResolver,
        ConferenceResolver,
        ParticipantResolver,
        CompanyResolver,
        ApplicantResolver,
        DelegateResolver,
        PreaccreditationResolver
    ],
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class AdminAppRoutingModule {}
