import { Component } from '@angular/core';
import { FormControl, Validators, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

import { Observable, of } from 'rxjs';
import { map, tap, catchError } from 'rxjs/operators';

import { ToastrService } from 'ngx-toastr';

import { ErrorMessage, ErrorLabelConstants } from '@eng-ds/ng-toolkit';
import { I18nService } from '@eng-ds/ng-core';

import {
    WrapperPostPutData,
    LoaderService,
    SectionLoading,
    ComboBox,
    WrapperError,
    FormFieldGroup,
    HttpInternalErrorResponse,
    SelectValueValidator
} from '@common';

import { UtilityService, ConferenceService, ConferenceType } from '@app/core';

import { DomusConference } from '../../../core';

@Component({
    templateUrl: './domus.component.html',
    styleUrls: ['./domus.component.scss']
})
export class DomusComponent {
    save: (data: any) => Observable<WrapperPostPutData>;
  //saveWithManager: (data: any) => Observable<WrapperPostPutData>;
    saveComplete: (response: WrapperPostPutData | WrapperError) => void;
    saveError: (error: ErrorMessage) => void;
    extractDataToSubmit: (form: FormGroup) => DomusConference;

    groupFields: Map<string, FormFieldGroup> = new Map();
    model: DomusConference;
    conferenceAdministations: Observable<ComboBox[] & any> = of([]);
    private _isFirstLoad = true;

    constructor(
        private loaderService: LoaderService,
        private i18nService: I18nService,
        private utilityService: UtilityService,
        private toastr: ToastrService,
        private conferenceService: ConferenceService,
        private router: Router
    ) {
        this._createForm();
        this._initCallback();
        this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
    }

    private _initCallback(): void {
        this.save = this._save.bind(this);
        //this.saveWithManager = this._saveWithManager(this);
        this.saveComplete = this._saveComplete.bind(this);
        this.saveError = this._saveError.bind(this);
        this.extractDataToSubmit = this._extractDataToSubmit.bind(this);
    }

    private _extractDataToSubmit(form: FormGroup): DomusConference {
        return new DomusConference(form.value.domus);
    }

    /*private _save(data: DomusConference): Observable<WrapperPostPutData> {
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
        return this.conferenceService
            .domusCreate(data.folderCode.replace('#', '-'))
            .pipe(
                catchError((err: HttpInternalErrorResponse) =>
                    this._catchError(err)
                )
            );
    }*/

    private _save(data: DomusConference): Observable<WrapperPostPutData> {
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
        return this.conferenceService
            .domusCreateWithManager(data.folderCode.replace('#', '-'), data.conferenceManagersDomus.key)
            .pipe(
                catchError((err: HttpInternalErrorResponse) =>
                    this._catchError(err)
                )
            );
    }

    private _catchError(err: HttpInternalErrorResponse): Observable<null> {
        if (err.status === 404 && err.body && err.body.errors) {
            const _error = err.body.errors as WrapperError[];

            if (_error[0] && _error[0].code === '001') {
                this.toastr.warning(
                    // this.i18nService.translate(
                    //     'CONFERENCE.DOMUS.TOASTR.SAVE_ERROR_404.TEXT'
                    // ),
                    err.body.msg,
                    this.i18nService.translate(
                        'CONFERENCE.DOMUS.TOASTR.SAVE_ERROR_404.TITLE'
                    )
                );
            }
        }
        this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
        return of(null);
    }

    private _saveComplete(response: WrapperPostPutData): void {
        if (response && response.id) {
            this.router.navigate(['/conference', response.id]);

            this.toastr.info(
                this.i18nService.translate(
                    'CONFERENCE.DOMUS.TOASTR.SAVE_COMPLETE.TEXT'
                ),
                this.i18nService.translate(
                    'CONFERENCE.DOMUS.TOASTR.SAVE_COMPLETE.TITLE'
                )
            );
        }
    }
    private _saveError(error: ErrorMessage): void {
        this.toastr.warning(
            this.i18nService.translate(
                'CONFERENCE.DOMUS.TOASTR.SAVE_ERROR.TEXT'
            ),
            this.i18nService.translate(
                'CONFERENCE.DOMUS.TOASTR.SAVE_ERROR.TITLE'
            )
        );
        this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
    }

    private _createForm(): void {
        this.model = new DomusConference();
        this.groupFields.set('domus', {
            panel: true,
            panelHead: null,
            fields: new Map()
                .set('folderCode', {
                    type: 'text',
                    label: 'CONFERENCE.DOMUS.INPUTS_LABEL.FOLDER_CODE',
                    control: new FormControl('', [Validators.required]),
                    errorLabels: [ErrorLabelConstants.REQUIRED],
                    size: '12|12|4'
                })
                .set('conferenceType', {
                    type: 'select',
                    label: 'CONFERENCE.DOMUS.INPUTS_LABEL.CONFERENCE_TYPE',
                    control: new FormControl(''),
                    options: this.utilityService.getConferenceTypes().pipe(
                        map(
                            (types: ComboBox[]): ComboBox[] =>
                                types.filter(
                                    (type: ComboBox): boolean =>
                                        type.key === ConferenceType.DOMUS
                                )
                        ),
                        tap(
                            (types: ComboBox[]): void => {
                                this.groupFields
                                    .get('domus')
                                    .fields.get('conferenceType')
                                    .control.setValue(types[0]);
                            }
                        )
                    ),
                    size: '12|12|4'
                })
                .set('conferenceManagersDomus', {
                    type: 'select',
                    label: 'CONFERENCE.DOMUS.INPUTS_LABEL.ACCOUNTABLE',
                    control: new FormControl(''),
                    options: this.utilityService.getConferenceManagersDomus()
                    .pipe(
                        tap(
                            (types: ComboBox[]): void => {
                                this.groupFields
                                    .get('domus')
                                    .fields.get('conferenceManagersDomus')
                                    .control.setValue(types[0]);
                            }
                        )
                    ),
                    size: '12|12|4'
                })
        }); 
    }
}
