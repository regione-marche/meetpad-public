import { NgModule } from '@angular/core';
import { CommonModule, FileService } from '@common';
import { MainConferenceComponent } from './main/main.component';
import { SharedFeaturesModule } from '../shared/shared.module';
import { SendMailComponent } from './components/send-mail/send-mail.component';
import { UploadFileComponent } from './components/upload-file/upload-file.component';
import { ChangeStateComponent } from './components/change-state/change-state.component';

@NgModule({
    imports: [CommonModule, SharedFeaturesModule],
    declarations: [
        MainConferenceComponent,
        SendMailComponent,
        UploadFileComponent,
        ChangeStateComponent
    ]
})
export class ConferenceModule {}
