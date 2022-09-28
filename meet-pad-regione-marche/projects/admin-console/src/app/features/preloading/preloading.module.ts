import { NgModule } from '@angular/core';
import { CommonModule } from '@common';
import { MainPreloadingComponent } from './main/main.component';
import { PreloadingService } from './core/services/preloading.service';
import { SharedAdminModule } from '../../shared/shared.module';
import { ApplicantMainComponent } from './features/applicant/main.component';
import { ParticipantMainComponent } from './features/participant/main.component';
import { CompanyMainComponent } from './features/company/main.component';
import { ApplicantListComponent } from './features/applicant/components/applicant-list/applicant-list.component';
import { ApplicantSearchComponent } from './features/applicant/components/applicant-search/applicant-search.component';
import { CompanyComponent } from './features/company/components/company/company.component';
import { ParticipantsListComponent } from './features/participant/components/participants-list/participants-list.component';
import { ParticipantsService } from './features/participant/services/participant.service';
import { ParticipantsSearchComponent } from './features/participant/components/participants-search/participants-search.component';
import { ParticipantComponent } from './features/participant/components/participant/participant.component';
import { CompanyListComponent } from './features/company/components/company-list/company-list.component';
import { CompanySearchComponent } from './features/company/components/company-search/company-search.component';
import { CompanyService } from './features/company/services/company.service';
import { ApplicantService } from './features/applicant/services/applicant.service';
import { ApplicantComponent } from './features/applicant/components/applicant/applicant.component';
import { DelegateComponent } from './features/delegate/components/delegate/delegate.component';
import { DelegatesListComponent } from './features/delegate/components/delegates-list/delegates-list.component';
import { DelegatesSearchComponent } from './features/delegate/components/delegates-search/delegates-search.component';
import { DelegateService } from './features/delegate/services/delegate.service';
import { DelegateMainComponent } from './features/delegate/main.component';
import { PreaccreditationSearchComponent } from './features/preaccreditation/components/preaccreditation-search/preaccreditation-search.component';
import { PreaccreditationListComponent } from './features/preaccreditation/components/preaccreditation-list/preaccreditation-list.component';
import { PreaccreditationComponent } from './features/preaccreditation/components/preaccreditation/preaccreditation.component';
import { PreaccreditationMainComponent } from './features/preaccreditation/main.component';
import { PreaccreditationService } from './features/preaccreditation/services/preaccreditation.service';

@NgModule({
    imports: [CommonModule, SharedAdminModule],
    declarations: [
        MainPreloadingComponent,
        ApplicantMainComponent,
        ParticipantMainComponent,
        CompanyMainComponent,
        ApplicantListComponent,
        ApplicantSearchComponent,
        CompanyComponent,
        ParticipantsListComponent,
        ParticipantsSearchComponent,
        ParticipantComponent,
        CompanyListComponent,
        CompanySearchComponent,
        ApplicantComponent,
        DelegateComponent,
        DelegatesListComponent,
        DelegatesSearchComponent,
        DelegateMainComponent,
        PreaccreditationMainComponent,
        PreaccreditationSearchComponent,
        PreaccreditationListComponent,
        PreaccreditationComponent
    ],
    exports: [
        MainPreloadingComponent,
        ApplicantMainComponent,
        CompanyMainComponent,
        ParticipantMainComponent,
        DelegateMainComponent,
        PreaccreditationMainComponent
    ],
    providers: [
        PreloadingService,
        ParticipantsService,
        CompanyService,
        ApplicantService,
        DelegateService,
        PreaccreditationService
    ]
})
export class PreloadingModule {}
