import { Component, ViewChild, AfterViewInit, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormControl, Validators, FormGroup } from '@angular/forms';

import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { ToastrService } from 'ngx-toastr';

import {
    ErrorMessage,
    ErrorLabelConstants,
    ModalComponent,
    TooltipModel,
    ActionItem
} from '@eng-ds/ng-toolkit';
import { LoggerService, I18nService } from '@eng-ds/ng-core';

import { environment } from '@env/environment';

import {
    WrapperPostPutAccreditation,
    LoaderService,
    SectionLoading,
    WrapperError,
    FormFieldGroup,
    HttpInternalErrorResponse
} from '@common';

import { UtilityService } from '@app/core';

import { AccreditationService } from '@features/private/core/services/accreditation/accreditation.service';

import {
    AccreditamentPreview,
    AccreditamentInfo
} from '@features/private/conference/core';

@Component({
    selector: 'app-accreditation',
    templateUrl: './accreditation.component.html',
    styleUrls: ['./accreditation.component.scss']
})
export class AccreditationComponent implements OnInit, AfterViewInit {
    @ViewChild('modal') modal: ModalComponent;

    saveAccreditation: (
        data: AccreditamentPreview
    ) => Observable<WrapperPostPutAccreditation>;
    saveAccreditationComplete: (response: WrapperPostPutAccreditation) => void;
    saveAccreditationError: (error: ErrorMessage) => void;
    extractDataToSubmit: (form: FormGroup) => Event;

    groupFields: Map<string, FormFieldGroup> = new Map();
    modalTitle: string = '';
    modalButtons: ActionItem[] = [];

    private _modalOpen: boolean = false;
    private _editQuestionMask: boolean = false;
    private _accreditationFile: File;
    private _accreditamentInfo: AccreditamentInfo;

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private loggerService: LoggerService,
        private accreditationService: AccreditationService,
        private toastrService: ToastrService,
        private i18nService: I18nService,
        private utilityService: UtilityService,
        private loaderService: LoaderService
    ) {}

    ngOnInit() {
        this._accreditamentInfo = new AccreditamentInfo(
            this.route.snapshot.data.accreditationInfo
        );
        this._initCallback();
        this._initAccreditation();
        this._initModal();
    }

    ngAfterViewInit() {
        if (this._modalOpen) {
            this.modal.open();
        }
    }

    get loading$(): Observable<boolean> {
        return this.loaderService.getLoading$(
            SectionLoading.ACCREDITATION_MODAL
        );
    }

    get showAccreditmentMask(): boolean {
        return !this._editQuestionMask;
    }

    get showEditQuestion(): boolean {
        return this._editQuestionMask;
    }

    private _initModal(): void {
        this.modal.manageClose = () => {
            return false;
        };
        this._initModalTitle();
    }

    private _initModalTitle(): void {
        if (environment.customConfigurationConf.accreditationMpTitle) {
            this.modalTitle = this.i18nService.translate(
                'CONFERENCE.JOIN.MASKS.ACCREDITMENT.TITLE',
                { conferenceId: this._accreditamentInfo.idConference }
            );
        } else {
            this.modalTitle = this.i18nService.translate(
                'CONFERENCE.JOIN.MASKS.ACCREDITMENT.TITLE_SUAP',
                { conferenceId: this._accreditamentInfo.idConference }
            );
        }
    }

    private _initCallback(): void {
        this.saveAccreditation = this._saveAccreditationForm.bind(this);
        this.saveAccreditationError = this._saveAccreditationError.bind(this);
        this.saveAccreditationComplete = this._saveAccreditationComplete.bind(
            this
        );
        this.extractDataToSubmit = this._extractDataToSubmit.bind(this);
    }

    private _extractDataToSubmit(
        form: FormGroup
    ): Partial<AccreditamentPreview> {
        return form.getRawValue();
    }

    private _saveAccreditationForm(data: {
        accreditation: AccreditamentPreview;
    }): Observable<WrapperPostPutAccreditation> {
        this.loggerService.log('_saveAccreditation', data);
        this.loaderService.showLoading(SectionLoading.ACCREDITATION_MODAL);
        const newData = Object.assign({}, data.accreditation);
        newData.file = this._accreditationFile;

        if (this._accreditamentInfo.existAccreditation()) {
            newData.id = this._accreditamentInfo.accreditation.id;
            return this.accreditationService
                .put(newData)
                .pipe(
                    catchError((err: HttpInternalErrorResponse) =>
                        this._catchError(err)
                    )
                );
        } else {
            newData.token1 = this.route.snapshot.params.token_1;
            newData.token2 = this.route.snapshot.params.token_2;
            return this.accreditationService
                .post(newData)
                .pipe(
                    catchError((err: HttpInternalErrorResponse) =>
                        this._catchError(err)
                    )
                );
        }
    }

    private _catchError(err: HttpInternalErrorResponse): Observable<null> {
        if (err.status === 422 && err.body && err.body.errors) {
            const _error = err.body.errors as WrapperError[];

            if (_error[0] && _error[0].code === '422.1') {
                this.groupFields
                    .get('accreditation')
                    .fields.get('fiscalCode')
                    .control.setErrors({});
                this.toastrService.error(
                    this.i18nService.translate(
                        'CONFERENCE.JOIN.TOASTR.POST_PUT_ERROR_422_1.TEXT'
                    ),
                    this.i18nService.translate(
                        'CONFERENCE.JOIN.TOASTR.POST_PUT_ERROR_422_1.TITLE'
                    )
                );
            }
        }
        if (err.status === 404 && err.body && err.body.errors) {
            const _error = err.body.errors as WrapperError[];

            if (_error[0] && _error[0].code === '001') {
                this.groupFields
                    .get('accreditation')
                    .fields.get('fiscalCode')
                    .control.setErrors({});
                this.toastrService.error(
                    this.i18nService.translate(
                        'CONFERENCE.JOIN.TOASTR.POST_PUT_ERROR_404_001.TEXT'
                    ),
                    this.i18nService.translate(
                        'CONFERENCE.JOIN.TOASTR.POST_PUT_ERROR_404_001.TITLE'
                    )
                );
            }
        }
        return of(null);
    }

    private _saveAccreditationComplete(
        response: WrapperPostPutAccreditation
    ): void {
        if (response) {
            this.toastrService.info(
                this.i18nService.translate(
                    'CONFERENCE.JOIN.TOASTR.SUCCESS_SAVE.TEXT'
                ),
                this.i18nService.translate(
                    'CONFERENCE.JOIN.TOASTR.SUCCESS_SAVE.TITLE'
                )
            );

            if (!response.accreditamentFlag) {
                this._navigateToPendingPage();
            } else {
                this._navigateToConference();
            }
        }
        this.loaderService.hideLoading(
            SectionLoading.ACCREDITATION_MODAL,
            2000
        );
    }

    private _saveAccreditationError(error: ErrorMessage): void {
        this.loaderService.hideLoading(SectionLoading.ACCREDITATION_MODAL);
        this.toastrService.error(
            this.i18nService.translate(
                'CONFERENCE.JOIN.TOASTR.POST_PUT_ERROR.TEXT'
            ),
            this.i18nService.translate(
                'CONFERENCE.JOIN.TOASTR.POST_PUT_ERROR.TITLE'
            )
        );
    }

    private _prepareAccreditationMask(): void {
        this.modalButtons = [];
        this._editQuestionMask = false;
        this._createForm();
        this._modalOpen = true;
    }

    private _prepareEditQuestionMask(): void {
        this.modalButtons = [
            new ActionItem(
                'CONFERENCE.JOIN.MASKS.EDIT_QUESTION.NOK_BTN',
                (action: ActionItem) => {
                    this._navigateToPendingPage();
                }
            ),
            new ActionItem(
                'CONFERENCE.JOIN.MASKS.EDIT_QUESTION.OK_BTN',
                (action: ActionItem) => {
                    this._prepareAccreditationMask();
                }
            )
        ];
        this._editQuestionMask = true;
        this._modalOpen = true;
    }

    private _navigateToPendingPage(): void {
        this.router.navigate(['join', 'pending']);
    }

    private _navigateToConference(): void {
        this.router.navigate([
            '/conference',
            this._accreditamentInfo.idConference
        ]);
    }

    private _initAccreditation(): void {
        // se la risposta è vuota apro la maschera e faccio la post
        if (!this._accreditamentInfo.existAccreditation()) {
            this._prepareAccreditationMask();
        } else {
            if (this._accreditamentInfo.isAccreditated()) {
                // se accreditamentFlag è true vado direttamente alla view della conferenza
                this._navigateToConference();
            } else {
                // se flagAccreditato è false vuol dire che esiste già un accreditamento pendente
                // che deve essere approvato dal BO per cui viene mostrata una maschera all'utente
                // che gli permetta di scegliere se modificare i dati della precedenta richiesta
                // di acrreditamento
                this._prepareEditQuestionMask();
            }
        }
    }

    private _createForm(): void {
        this.groupFields = new Map();
        this.groupFields.set('accreditation', {
            panel: false,
            panelHead: null,
            fields: new Map()
                .set('name', {
                    type: 'text',
                    label: 'CONFERENCE.JOIN.MODAL.FORM.NAME',
                    control: new FormControl(
                        this._accreditamentInfo.accreditation.name,
                        [Validators.required]
                    ),
                    errorLabels: [ErrorLabelConstants.REQUIRED],
                    size: '12|6|6'
                })
                .set('surname', {
                    type: 'text',
                    label: 'CONFERENCE.JOIN.MODAL.FORM.SURNAME',
                    control: new FormControl(
                        this._accreditamentInfo.accreditation.surname,
                        [Validators.required]
                    ),
                    errorLabels: [ErrorLabelConstants.REQUIRED],
                    size: '12|6|6'
                })
                .set('fiscalCode', {
                    type: 'text',
                    label: 'CONFERENCE.JOIN.MODAL.FORM.FISCAL_CODE',
                    pattern:
                        '^[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]$',
                    control: new FormControl({
                        value: this._accreditamentInfo.accreditation.fiscalCode,
                        disabled: true
                    }),
                    errorLabels: [
                        ErrorLabelConstants.REGEX('ERROR.CF.INVALID')
                    ],
                    size: '12|6|6'
                })
                .set('profile', {
                    type: 'select',
                    label: 'CONFERENCE.JOIN.MODAL.FORM.PROFILE',
                    size: '12|6|6',
                    options: this.utilityService.getAccreditationRoles(),
                    control: new FormControl(
                        this._accreditamentInfo.accreditation.profile,
                        [Validators.required]
                    ),
                    errorLabels: [ErrorLabelConstants.REQUIRED]
                })
                .set('email', {
                    type: 'text',
                    pattern: '^[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,4}$',
                    label: 'CONFERENCE.JOIN.MODAL.FORM.EMAIL',
                    control: new FormControl(
                        this._accreditamentInfo.accreditation.email,
                        [Validators.required]
                    ),
                    errorLabels: [
                        ErrorLabelConstants.REQUIRED,
                        ErrorLabelConstants.REGEX('ERROR.EMAIL.INVALID')
                    ],
                    size: '12|6|6'
                })
                /*.set('file', {
                    type: 'file',
                    onSelect: (file?: File): void => {
                        this._accreditationFile = file;
                    },
                    tooltip: new TooltipModel(
                        'CONFERENCE.JOIN.MODAL.FORM.FILE_TOOLTIP_TEXT',
                        undefined,
                        'CONFERENCE.JOIN.MODAL.FORM.FILE_TOOLTIP_TITLE'
                    ),
                    hideSubmitBtn: true,
                    required:
                        environment.customConfigurationConf
                            .accreditationFileRequired
                })*/
        });
    }
}
