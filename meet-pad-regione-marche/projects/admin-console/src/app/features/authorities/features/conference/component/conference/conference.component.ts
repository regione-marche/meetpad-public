import { Component, OnInit } from '@angular/core';
import { ConferenceService } from '../../../../core/services/conference.service';
import {
    AutoUnsubscribe,
    WrapperPostPutData,
    FormFieldGroup,
    LoaderService,
    SectionLoading,
    FormField,
    ComboBox,
    WrapperResponse,
    SelectValueValidator,
    MessageToastrService
} from '@common';
import { Observable, of } from 'rxjs';
import { ErrorMessage, ErrorLabelConstants } from '@eng-ds/ng-toolkit';
import { ActivatedRoute } from '@angular/router';
import { UtilityService } from '../../../../../../core/services/utility.service';
import { FormControl, Validators } from '@angular/forms';
import { tap, catchError } from 'rxjs/operators';
import { Conference } from '../../../../core/models/conference.model';
import { HttpResponse } from '@angular/common/http';

@Component({
    // tslint:disable-next-line: component-selector
    selector: 'admin-conference',
    templateUrl: './conference.component.html',
    styleUrls: ['./conference.component.scss']
})
export class ConferenceComponent extends AutoUnsubscribe implements OnInit {
    onNext: (data: Conference) => Observable<WrapperPostPutData>;
    saveComplete: (response: WrapperPostPutData) => void;
    saveError: (error: ErrorMessage) => void;
    groupFields: Map<string, FormFieldGroup> = new Map();
    model: Conference = new Conference();

    conferenceAdministations: Observable<
        ComboBox[] & any
    > = this.utilityService.getConferenceAdministrations();

    constructor(
        private route: ActivatedRoute,
        private loaderService: LoaderService,
        private conferenceService: ConferenceService,
        private utilityService: UtilityService,
        private messageToastr: MessageToastrService
    ) {
        super();
    }

    ngOnInit() {
        if (this.route.snapshot.data.conference) {
            this.model = Conference.fromDto(
                this.route.snapshot.data.conference
            );
        }

        this._createForm();
        this._initCallbacks();
        this.loaderService.hideLoading(SectionLoading.ALL_CONTENT, 500);
    }

    private _createForm(): void {
        this.groupFields = new Map<string, FormFieldGroup>().set('conference', {
            panel: true,
            panelHead: 'AUTHORITIES.PROCEDING.CONFERENCE.EDIT.TITLE',
            fields: new Map<string, FormField>()

                .set('request_reference', {
                    type: 'text',
                    label:
                        'AUTHORITIES.PROCEDING.CONFERENCE.EDIT.REQUEST_REFERENCE',
                    control: new FormControl({
                        value: this.model.requestReference,
                        disabled: true
                    }),
                    size: '12|12|12'
                })
                .set('type', {
                    type: 'text',
                    label: 'AUTHORITIES.PROCEDING.CONFERENCE.EDIT.TYPE',
                    control: new FormControl({
                        value: this.model.conferenceType.value,
                        disabled: true
                    }),
                    size: '12|6|6'
                })
                .set('state', {
                    type: 'text',
                    label: 'AUTHORITIES.PROCEDING.CONFERENCE.EDIT.STATE',
                    control: new FormControl({
                        value: this.model.state.value,
                        disabled: true
                    }),
                    size: '12|6|6'
                })
                .set('administration', {
                    type: 'select',
                    label:
                        'AUTHORITIES.PROCEDING.CONFERENCE.EDIT.ADMINISTRATION_LABEL',
                    control: new FormControl(this.model.administration, [
                        Validators.required,
                        SelectValueValidator
                    ]),
                    valueChange: (administration: ComboBox) => {
                        const managerField = this.groupFields
                            .get('conference')
                            .fields.get('manager');
                        managerField.options = this.utilityService
                            .getConferenceManagersWithAdmin(administration.key)
                            .pipe(
                                // preimpostando di default la prima
                                // viene aggiornata anche la select delle action
                                tap((c: ComboBox[]) => {
                                    managerField.control.setValue(c[0]);
                                })
                            );
                    },
                    size: '12|12|12',
                    options: this.conferenceAdministations,
                    errorLabels: [ErrorLabelConstants.REQUIRED]
                })
                .set('manager', {
                    type: 'select',
                    label:
                        'AUTHORITIES.PROCEDING.CONFERENCE.EDIT.MANAGER_LABEL',
                    control: new FormControl(this.model.manager, [
                        Validators.required,
                        SelectValueValidator
                    ]),
                    size: '12|12|12',
                    options: of([]),
                    errorLabels: [ErrorLabelConstants.REQUIRED]
                })
        });
        this._initManager();
    }

    private _initManager() {
        const managerField: FormField = this.groupFields
            .get('conference')
            .fields.get('manager');
        const administrationField: FormField = this.groupFields
            .get('conference')
            .fields.get('administration');
        if (
            administrationField.control.value &&
            administrationField.control.value.key !== '-1'
        ) {
            managerField.options = this.utilityService.getConferenceManagersWithAdmin(
                this.model.administration.key
            );
        }
    }

    private _initCallbacks(): void {
        this.onNext = this._onNext.bind(this);
        this.saveComplete = this._saveComplete.bind(this);
        this.saveError = () => {};
    }

    private _saveComplete(response: WrapperResponse<any>): void {
        this.messageToastr.showMessage(
            response,
            'AUTHORITIES.PROCEDING.CONFERENCE',
            'EDIT'
        );
    }

    private _saveError(error: HttpResponse<WrapperResponse>): Observable<null> {
        return this.messageToastr.showErrorMessage(
            error,
            'AUTHORITIES.PROCEDING.CONFERENCE',
            'EDIT'
        );
    }

    private _onNext(data: Conference): Observable<WrapperPostPutData> {
        return this.conferenceService
            .editConference(data, this.route.snapshot.params.id)
            .pipe(
                catchError((response: HttpResponse<WrapperResponse>) => {
                    return this._saveError(response);
                })
            );
    }
}
