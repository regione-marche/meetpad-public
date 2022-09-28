import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import {
    SectionLoading,
    LoaderService,
    AutoUnsubscribe,
    WrapperPostPutData,
    FormFieldGroup,
    ComboBox,
    FormField,
    WrapperResponse,
    MessageToastrService,
    SelectValueValidator,
} from '@common';
import { ToastrService } from 'ngx-toastr';
import { ConferenceService } from '../../../authorities/core/services/conference.service';
import { ActivatedRoute } from '@angular/router';

import { SendMail } from '../../../authorities/core/models/sendMail.model';
import { Observable, of } from 'rxjs';
import { tap } from 'rxjs/operators';
import { ErrorMessage, ErrorLabelConstants } from '@eng-ds/ng-toolkit';
import { UtilityService } from '../../../../core/services/utility.service';

@Component({
    // tslint:disable-next-line: component-selector
    selector: 'admin-send-mail',
    templateUrl: './send-mail.component.html',
    styleUrls: ['./send-mail.component.scss']
})
export class SendMailComponent extends AutoUnsubscribe implements OnInit {
    onNext: (data: SendMail) => Observable<WrapperPostPutData>;
    saveComplete: (response: WrapperPostPutData) => void;
    saveError: (error: ErrorMessage) => void;
    groupFields: Map<string, FormFieldGroup> = new Map();
    model: SendMail = new SendMail();

    conferenceAdministations: Observable<ComboBox[] & any> = of([]);
    private _isFirstLoad = true;

    constructor(
        private toastr: ToastrService,
        private route: ActivatedRoute,
        private loaderService: LoaderService,
        private conferenceService: ConferenceService,
        private utilityService: UtilityService,
        private messageToastr: MessageToastrService
    ) {
        super();
    }

    ngOnInit() {
       

        if (this.route.snapshot.parent.params.id) {
            this.model.idConference = this.route.snapshot.parent.params.id;
            this.conferenceAdministations = this.utilityService.getParticipantForConference(
                this.model.idConference
            );   
        }

        this._createForm(); 
        this._initCallbacks();
        this.loaderService.hideLoading(SectionLoading.ALL_CONTENT, 500);
    }

    private _createForm(): void {
        this.groupFields = new Map<string, FormFieldGroup>().set(
            'sendIndictionMail',
            {
                panel: true,
                panelHead: 'CONFERENCE.SEND_MAIL.TITLE',
                fields: new Map<string, FormField>()
                    .set('newMail', {
                        type: 'text',
                        label: 'CONFERENCE.SEND_MAIL.MAIL',
                        control: new FormControl(this.model.newMail),
                        size: '12|12|12'
                    })
                    .set('participant', {
                        type: 'select',
                        label: 'CONFERENCE.SEND_MAIL.PARTICIPANT',
                        control: new FormControl(this.model.idParticipant, [
                            Validators.required,
                            SelectValueValidator
                        ]),
                        size: '12|12|12',
                        //options: this.conferenceAdministations,
                        options: this.conferenceAdministations.pipe(
                            tap((c: ComboBox[]) => {
                                if(this._isFirstLoad) {
                                    this._isFirstLoad = false;
                                   // this.groupFields
                                   // .get('sendIndictionMail')
                                   // .fields.get('participant').control.setValue(c[0].key);

                                    this.groupFields
                                    .get('sendIndictionMail')
                                    .fields.get('participant').control.setValue(c[0]);

                            
                                }
                            })),
                        errorLabels: [ErrorLabelConstants.REQUIRED]
                    })
                    .set('sendnonpec', {
                        type: 'switch',
                        label:
                            'Segna questa mail come non PEC',
                        control: new FormControl({
                            value: false, 
                        }),
                                /*
                        tooltip: new TooltipModel(
                                'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.TOOLTIP.CALAMAIO.TEXT',
                                undefined,
                                'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.TOOLTIP.CALAMAIO.TITLE'
                        ),
                        valueChange: (val) => {
                            $("input[id^='input-switch-CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.FILE.CALAMAIO_REMOTE.']").parent().parent().parent().toggle(!val);
                            $("#CALAMAIO_DOWNLOAD_MESSAGE").toggle(val);
                            $("eng-input[data-test-id^='CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.FILE.CALAMAIO_']").hide();
                            
                            this.uploadSignatureFileWithCalamaio = val;
                        },
                        placeholder: 'COMMON.ALL',
                        */

                        size: '12|12|12',
                    })
            }
        );
    }

    private _initCallbacks(): void {
        this.onNext = this._onNext.bind(this);
        this.saveComplete = this._saveComplete.bind(this);
        this.saveError = this._saveError.bind(this);
    }

    private _saveComplete(response: WrapperResponse<any>): void {
        this.messageToastr.showMessage(response, 'CONFERENCE', 'SEND_MAIL');
    }

    private _saveError(error: ErrorMessage): void {}

    private _onNext(data: SendMail): Observable<WrapperPostPutData> {
        return this.conferenceService.sendIndictionMail(
            data,
            this.route.snapshot.parent.params.id,
            this.groupFields.get('sendIndictionMail').fields.get('sendnonpec').control.value
        );
    }
}
