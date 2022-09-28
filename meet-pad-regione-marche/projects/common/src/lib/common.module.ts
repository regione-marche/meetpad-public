import { NgModule, ModuleWithProviders, InjectionToken } from '@angular/core';
import * as AngularCommon from '@angular/common';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';

import { ConfirmationPopoverModule } from 'angular-confirmation-popover';
import { NgSelectModule } from '@ng-select/ng-select';
import { ToastrModule } from 'ngx-toastr';
import { FileDropModule } from 'ngx-file-drop';
import { TranslateModule } from '@ngx-translate/core';
import { NgxEngToolkitModule, MessageService } from '@eng-ds/ng-toolkit';

import { EngFooterComponent } from './components/eng-footer/eng-footer.component';
import { EngHeaderComponent } from './components/eng-header/eng-header.component';
import { AuthComponent } from './components/auth/auth.component';
import { Bs3SwitchComponent } from './components/bs3-switch/bs3-switch.component';
import { EngFileDropComponent } from './components/eng-file-drop/eng-file-drop.component';
import { FileInputComponent } from './components/file-input/file-input.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { PageComponent } from './components/page/page.component';
import { SectionComponent } from './components/section/section.component';
import { GetValuesPipe } from './pipes/get-values.pipe';
import { AlertDirective } from './components/form/directives/alert-host/alert.directive';
import { FooterButtonsComponent } from './components/footer-buttons/components/footer-buttons.component';
import { FormComponent } from './components/form/main/form.component';
import { OneToManyFormComponent } from './components/form/components/one-to-many/one-to-many-form.component';
import { InputsFormComponent } from './components/form/components/inputs/inputs-form.component';
import { ListTableComponent } from './components/form/components/one-to-many/components/list-table/list-table.component';
import { FormStoreService } from './components/form/services/form-store.service';
import { EngTemplateComponent } from './components/eng-template/eng-template.component';

import { MenuGuard } from './services/menu-guard/menu.guard';
import { AuthService } from './services/auth/auth.service';
import { LoaderService } from './services/loader/loader.service';
import { HeaderService } from './services/header/header.service';
import { UploadComponent } from './components/upload-status/components/upload/upload.component';
import { UploadStatusComponent } from './components/upload-status/upload-status.component';
import { AlertWithLinkComponent } from './components/alert-with-link/alert-with-link.component';
import { DownloadComponent } from './components/progress-download/components/download/download.component';

export const API_BASE_URL = new InjectionToken<string>('api.base.url');

@NgModule({
    imports: [
        FileDropModule,
        ReactiveFormsModule,
        FormsModule,
        TranslateModule,
        NgxEngToolkitModule,
        NgSelectModule,
        ToastrModule.forRoot({
            timeOut: 20000,
            positionClass: 'toast-top-right'
        }),
        ConfirmationPopoverModule.forRoot({
            confirmButtonType: 'danger' // set defaults here
        }),
        RouterModule,
        AngularCommon.CommonModule
    ],
    declarations: [
        EngFooterComponent,
        EngHeaderComponent,
        AuthComponent,
        Bs3SwitchComponent,
        EngFileDropComponent,
        NotFoundComponent,
        PageComponent,
        SectionComponent,
        GetValuesPipe,
        FormComponent,
        FileInputComponent,
        OneToManyFormComponent,
        InputsFormComponent,
        ListTableComponent,
        AlertDirective,
        EngTemplateComponent,
        FooterButtonsComponent,
        UploadComponent,
        UploadStatusComponent,
        AlertWithLinkComponent,
        DownloadComponent
    ],
    exports: [
        AuthComponent,
        Bs3SwitchComponent,
        EngFileDropComponent,
        NotFoundComponent,
        PageComponent,
        SectionComponent,
        AngularCommon.CommonModule,
        ReactiveFormsModule,
        FormsModule,
        FormComponent,
        EngTemplateComponent,
        OneToManyFormComponent,
        InputsFormComponent,
        ListTableComponent,
        FileInputComponent,
        FooterButtonsComponent,
        EngHeaderComponent,
        EngFooterComponent,
        NgxEngToolkitModule,
        FileDropModule,
        TranslateModule,
        GetValuesPipe,
        NgSelectModule,
        ToastrModule,
        ConfirmationPopoverModule,
        UploadComponent,
        UploadStatusComponent,
        AlertWithLinkComponent,
        DownloadComponent
    ],
    providers: [MessageService],
    entryComponents: [AlertWithLinkComponent]
})
export class CommonModule {
    static forRoot(): ModuleWithProviders {
        return {
            ngModule: CommonModule,
            providers: [
                FormStoreService,
                AuthService,
                HeaderService,
                LoaderService,
                MenuGuard
            ]
        };
    }
}
