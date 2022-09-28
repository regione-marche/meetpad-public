import { Component } from '@angular/core';
import { FormControl, Validators, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

import { Observable, Subscriber, of } from 'rxjs';
import { map, tap, catchError } from 'rxjs/operators';

import { ToastrService } from 'ngx-toastr';

import {
    ErrorMessage,
    ErrorLabelConstants,
    AbstractTableField,
    TableField
} from '@eng-ds/ng-toolkit';
import { I18nService, LoggerService } from '@eng-ds/ng-core';

import {
    WrapperPostPutData,
    LoaderService,
    SectionLoading,
    ComboBox,
    WrapperError,
    FormFieldGroup,
    HttpInternalErrorResponse
} from '@common';

import { UtilityService, ConferenceService, ConferenceType } from '@app/core';

import { PaleoFile, PaleoConference } from '../../../core';

import { environment } from '@env/environment';

@Component({
    templateUrl: './paleo.component.html',
    styleUrls: ['./paleo.component.scss']
})
export class PaleoComponent {
    save: (data: any) => Observable<WrapperPostPutData>;
    saveComplete: (response: WrapperPostPutData | WrapperError) => void;
    saveError: (error: ErrorMessage) => void;
    extractDataToSubmit: (form: FormGroup) => PaleoConference;

    groupFields: Map<string, FormFieldGroup> = new Map();

    conferenceAdministations: Observable<
        ComboBox[] & any
    > = this.utilityService.getConferenceAdministrations();

    model: PaleoConference;

    constructor(
        private loaderService: LoaderService,
        private i18nService: I18nService,
        private utilityService: UtilityService,
        private toastr: ToastrService,
        private conferenceService: ConferenceService,
        private router: Router,
        private loggerService: LoggerService,
    ) {
        this._createForm();
        this._initCallback();
        this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
    }

    private _initCallback(): void {
        this.save = this._save.bind(this);
        this.saveComplete = this._saveComplete.bind(this);
        this.saveError = this._saveError.bind(this);
        this.extractDataToSubmit = this._extractDataToSubmit.bind(this);
    }

    private _extractDataToSubmit(form: FormGroup): PaleoConference {
        return new PaleoConference({
            ...form.value.paleo,
            files: this.model.files
        });
    }

    private _save(data: PaleoConference): Observable<WrapperPostPutData> {
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
        return this.conferenceService
            .paleoCreate(data)
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
                    //     'CONFERENCE.PALEO.TOASTR.SAVE_ERROR_404.TEXT'
                    // ),
                    err.body.msg,
                    this.i18nService.translate(
                        'CONFERENCE.PALEO.TOASTR.SAVE_ERROR_404.TITLE'
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
                    'CONFERENCE.PALEO.TOASTR.SAVE_COMPLETE.TEXT'
                ),
                this.i18nService.translate(
                    'CONFERENCE.PALEO.TOASTR.SAVE_COMPLETE.TITLE'
                )
            );
        }
    }
    private _saveError(error: ErrorMessage): void {
        this.toastr.warning(
            this.i18nService.translate(
                'CONFERENCE.PALEO.TOASTR.SAVE_ERROR.TEXT'
            ),
            this.i18nService.translate(
                'CONFERENCE.PALEO.TOASTR.SAVE_ERROR.TITLE'
            )
        );
        this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
    }

    private _createForm(): void {
        this.model = new PaleoConference();
        this.groupFields
            .set('paleo', {
                panel: true,
                panelHead: null,
                fields: new Map()
                    .set('administration', {
                        type: 'select',
                        label:
                            'CONFERENCE.PALEO.INPUTS_LABEL.ADMINISTRATION',
                        control: new FormControl(
                            {
                                value: this.model.administration.key,
                                disabled: false
                            },
                            [Validators.required]
                        ),
                        valueChange: (administration: ComboBox) => {
                            this.loggerService.log('valueChange', administration);
                            const managerField = this.groupFields
                                .get('paleo')
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
                        size: '12|12|4',
                        //offset: '0|1|3',
                        options: this.conferenceAdministations.pipe(
                            tap((c: ComboBox[]) => {  
                                this.groupFields
                                .get('paleo')
                                .fields.get('administration').control.setValue(environment.defaultComboBox.conference.administration);
                            })
                        ),
                        errorLabels: [ErrorLabelConstants.REQUIRED]
                    })
                    .set('manager', {
                        type: 'select',
                        label:
                            'CONFERENCE.PALEO.INPUTS_LABEL.MANAGER',
                        control: new FormControl(
                            {
                                value: this.model.manager.key,
                                disabled: false
                            },
                            [Validators.required]
                        ),
                        size: '12|12|4',
                        //offset: '0|1|3',
                        options: this.utilityService.getConferenceManagersWithAdmin(
                            this.model.manager.key
                        ).pipe(
                            tap((c: ComboBox[]) => {  
                                this.groupFields
                                .get('paleo')
                                .fields.get('manager').control.setValue(environment.defaultComboBox.conference.manager);
                            })
                        ),
                        errorLabels: [ErrorLabelConstants.REQUIRED]
                    })
                    .set('folderCode', {
                        type: 'text',
                        label: 'CONFERENCE.PALEO.INPUTS_LABEL.FOLDER_CODE',
                        control: new FormControl('', [Validators.required]),
                        errorLabels: [ErrorLabelConstants.REQUIRED],
                        size: '12|12|4'
                    })
                    .set('folderDescription', {
                        type: 'text',
                        label:
                            'CONFERENCE.PALEO.INPUTS_LABEL.FOLDER_DESCRIPTION',
                        control: new FormControl('', [Validators.required]),
                        errorLabels: [ErrorLabelConstants.REQUIRED],
                        size: '12|12|4'
                    })
                    .set('conferenceType', {
                        type: 'select',
                        label: 'CONFERENCE.PALEO.INPUTS_LABEL.CONFERENCE_TYPE',
                        control: new FormControl(''),
                        options: this.utilityService.getConferenceTypes().pipe(
                            map(
                                (types: ComboBox[]): ComboBox[] =>
                                    types.filter(
                                        (type: ComboBox): boolean =>
                                            type.key === ConferenceType.SIMPLIFY ||
                                            type.key === ConferenceType.CONCURRENTLY ||
                                            type.key === ConferenceType.REGIONAL ||
                                            type.key === ConferenceType.ENVIRONMENT_INVESTIGATION_VIA ||
                                            type.key === ConferenceType.ENVIRONMENT_DEFINITION_VIA ||
                                            type.key === ConferenceType.ENVIRONMENT_INVESTIGATION_AIA ||
                                            type.key === ConferenceType.ENVIRONMENT_DEFINITION_AIA ||
                                            type.key === ConferenceType.INFORMATICS
                                    )
                            ),
                            // TODO: adesso la conferenza è una sola quindi
                            // si può prendere il primo elemento per il valore iniziale
                            tap(
                                (types: ComboBox[]): void => {
                                    this.groupFields
                                        .get('paleo')
                                        .fields.get('conferenceType')
                                        .control.setValue(types[0]);
                                }
                            )
                        ),
                        size: '12|12|4'
                    })
            })
            .set('files', {
                panel: true,
                panelHead: 'CONFERENCE.PALEO.INPUTS_LABEL.FILES',
                oneToMany: true,
                model: PaleoFile,
                saveFn: this._saveFiles.bind(this),
                listStructure: this._defineFilesStructure(),
                listTitle: 'CONFERENCE.PALEO.INPUTS_LABEL.FILES_LIST',
                emptyTextList: 'CONFERENCE.PALEO.TABLE.EMPTY_TEXT_LIST',
                listMany: this.model.files,
                readonly: false,
                fields: new Map()
                    .set('fileCode', {
                        type: 'text',
                        label: 'CONFERENCE.PALEO.INPUTS_LABEL.FILE_CODE',
                        validators: [Validators.required],
                        errorLabels: [ErrorLabelConstants.REQUIRED],
                        size: '12|6|6'
                    })
                    .set('fileDescription', {
                        type: 'text',
                        label: 'CONFERENCE.PALEO.INPUTS_LABEL.FILE_DESCRIPTION',
                        validators: [Validators.required],
                        errorLabels: [ErrorLabelConstants.REQUIRED],
                        size: '12|6|6'
                    })
            });
    }

    private _saveFiles(file: PaleoFile): Observable<any> {
        return new Observable(
            (observer: Subscriber<void | WrapperPostPutData>): void => {
                this.model.files.push(new PaleoFile(file));
                observer.next();
                observer.complete();
            }
        );
    }

    private _defineFilesStructure(): AbstractTableField[] {
        const tableStructure: AbstractTableField[] = [];

        tableStructure.push(
            new TableField(
                this.i18nService.translate(
                    'CONFERENCE.PALEO.INPUTS_LABEL.FILE_CODE'
                ),
                'NORMAL',
                'fileCode',
                true
            )
        );
        tableStructure.push(
            new TableField(
                this.i18nService.translate(
                    'CONFERENCE.PALEO.INPUTS_LABEL.FILE_DESCRIPTION'
                ),
                'NORMAL',
                'fileDescription',
                true
            )
        );

        return tableStructure;
    }

    resetForm(): void {
        this.model.files = [];
        this.groupFields.get('files').listMany = this.model.files;
        this.groupFields = new Map(this.groupFields);
    }
}
