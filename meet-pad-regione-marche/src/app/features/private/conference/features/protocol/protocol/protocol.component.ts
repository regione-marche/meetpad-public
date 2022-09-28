import {
    Component,
    OnInit,
    Output,
    EventEmitter,
    ViewChild
} from '@angular/core';

import { FormControl } from '@angular/forms';

import { Observable, of } from 'rxjs';
import { takeUntil, filter, catchError } from 'rxjs/operators';

import { ToastrService } from 'ngx-toastr';

import { I18nService } from '@eng-ds/ng-core';
import { ModalComponent, ActionItem, ErrorMessage } from '@eng-ds/ng-toolkit';

import { FormFieldGroup, BaseFile, FileService, WrapperPostPutData, AutoUnsubscribe } from '@common';

import { ActionForm } from '@app/core/enums/action-form.enum';

import { environment } from '@env/environment';

import { ProtocollingService } from '@app/features/private/conference/core/services/protocol/protocol.service';

import { ConferenceStoreService } from '../../../core';

import { Protocol } from '@app/features/private/conference/core/models/protocol/protocolling.model';

@Component({
    selector: 'app-protocol',
    templateUrl: './protocol.component.html',
    styleUrls: ['./protocol.component.scss']
})
export class ProtocolTabComponent extends AutoUnsubscribe implements OnInit {
    @ViewChild('confirmModal') confirmModal: ModalComponent;

    @Output('onNext') onNext: EventEmitter<void> = new EventEmitter();

    confirmModalTitle: string = '';
    confirmModalText: string = '';

    protocol: Protocol;

    action: ActionForm = this.conferenceStoreService.getStepActionAfterIndiction();

    rejectSaveFn: (data: any) => Observable<any>;
    rejectSaveComplete: () => void;

    confirmModalButtons: ActionItem[] = [];

    constructor(
        private protocolService: ProtocollingService,
        private toastr: ToastrService,
        private i18nService: I18nService,
        private conferenceStoreService: ConferenceStoreService
    ) {
        super();
    }

    ngOnInit() {
    }


    viewModalButtons(): void {
        this.confirmModalButtons = [
            new ActionItem(
                'CONFERENCE.WIZARD.PROTOCOL.MODAL.CONFIRM_BUTTON',
                (action: ActionItem) => {
                    this.confirmModal.close();
                    this.protocolService.updateProtocolState(
                        this.protocol
                    ).subscribe(
                        (response: WrapperPostPutData) => {
                           // this.protocolService.updateProtocolState(this.protocol);
                           this.protocolService.protocolSaveComplete(this.protocol);
                        },
                        (error: any) => {
                          
                            const errorMex = new ErrorMessage();
                            errorMex.title = 'ERRORE.GENERICO_SERVER';
                            errorMex.message = error.message;
                            this.protocolService.saveError(errorMex);
                          
                        }                        
                    );
                }
            ),
            new ActionItem(
                'CONFERENCE.WIZARD.PROTOCOL.MODAL.CANCEL_BUTTON',
                (action: ActionItem) => {
                    this.confirmModal.close();

                    
                }
            )
        ];     
    }

    showFooterBtn(): boolean {
        return this.action === ActionForm.CREATE;
    }

    isReadonly(): boolean {
        return this.action === ActionForm.READONLY;
    }

    // apertura modale
    updateProtocolState(protocol: Protocol): void {
        this.confirmModalTitle =
                    'CONFERENCE.WIZARD.PROTOCOL.MODAL.TITLE';
        this.confirmModalText =
                    'CONFERENCE.WIZARD.PROTOCOL.MODAL.PROTOCOL_EDIT_STATE';
        this.protocol = protocol;
        this.viewModalButtons();
        this.openModal();
    }

    openModal(): void {
        this.confirmModal.open();
    }    
   
}
