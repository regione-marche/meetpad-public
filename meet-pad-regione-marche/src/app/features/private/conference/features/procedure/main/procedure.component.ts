import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { Validators, FormControl } from '@angular/forms';

import { Observable, of, Subject } from 'rxjs';
import { tap, takeUntil } from 'rxjs/operators';

import { ErrorLabelConstants, ErrorMessage } from '@eng-ds/ng-toolkit';
import { LoggerService } from '@eng-ds/ng-core';

import {
    ComboBox,
    WrapperPostPutData,
    AutoUnsubscribe,
    FormFieldGroup,
    FormButton,
    FormField,
    FooterButtons
} from '@common';

import { UtilityService, ActionForm, StepName, Mixin, ConferenceType } from '@app/core';

import { FormStep } from '../../../core/mixins';
import {
    Procedure,
    ConferenceStoreService,
    ConferencePermissionsService,
    Conference
} from '../../../core';
import { IfStmt } from '@angular/compiler';

declare var $: any;
declare function openGeomapWindow(): any;

@Component({
    selector: 'app-conference-procedure',
    templateUrl: './procedure.component.html',
    styleUrls: ['./procedure.component.scss']
})
@Mixin([FormStep])
export class ProcedureComponent extends AutoUnsubscribe implements OnInit {
    @Input('summary') summary: boolean = false;
    @Output('openConfirmModal') openConfirmModal = new EventEmitter<
        FooterButtons
    >();

    onNext: (data: Procedure) => Observable<WrapperPostPutData>;
    saveComplete: (response: WrapperPostPutData) => void;
    saveError: (error: ErrorMessage) => void;
    groupFields: Map<string, FormFieldGroup> = new Map();
    groupButtons: Map<FooterButtons, FormButton> = new Map();
    buttons$: Subject<Map<string, FormButton>>;

    constructor(
        private loggerService: LoggerService,
        private utilityService: UtilityService,
        private conferenceStoreService: ConferenceStoreService,
        private conferencePermissionsService: ConferencePermissionsService
    ) {
        super();
    }

    getNewGuid(): string {
        return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
          var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
          return v.toString(16);
        });
    }
    
    ngOnInit() {
        this._initPermission();
        this._initCallbacks();
        this._createForm();
        this._initLocalizationAutocomplete();

        if (this.isReadonly()) {
            this.setProcedureFieldsAsReadOnly();
        }

        if(!this.conferenceStoreService.conference.geomapGuid)
            this.conferenceStoreService.conference.geomapGuid = this.getNewGuid();

        const viewOnly = this.action === ActionForm.CREATE || this.action === ActionForm.EDIT?'no':'yes';
        const guid = this.conferenceStoreService.conference.geomapGuid;
        const domusFoglioMappale = this.conferenceStoreService.conference.foglioMappale;
        $(document).ready(function(){ 
            setTimeout(function() {
                // xmf: open geobutton
                var cartograficoStr = '';
                cartograficoStr += '<button id="openGeolocalization" style="padding: 5px 40px 5px 40px" onclick="openGeomapWindow(\''+guid+'\', \''+viewOnly+'\')">APRI MAPPA</button>';

                var domusFoglioMappaleStr = '';
                if(domusFoglioMappale) domusFoglioMappaleStr += '<span style="padding-left: 20px;">Foglio e Mappale: ' + domusFoglioMappale + '</span>';

                var geomapButton = '<div class="form-group col-xs-12 col-sm-12 col-lg-6 noPadding"><div class="mx-auto" style="margin-top: 24px; ">'+ cartograficoStr + domusFoglioMappaleStr+'</div></div>';
                $('eng-input[data-test-id="CONFERENCE.WIZARD.PROCEDURE.LOCALIZATION.INPUTS_LABEL.ADDRESS"]').append(geomapButton);
            }, 100);
        });

        this.conference$
            .pipe(takeUntil(this.destroy$))
            .subscribe((conference: Conference) => {
                if (conference.isPreliminary() || conference.isBroadband()) {
                    this.model.applicant.setPreliminaryType();
                    this.groupFields
                        .get('applicant')
                        .fields.get('type')
                        .control.setValue(this.model.applicant.type);

                    this.groupFields
                        .get('applicant')
                        .fields.get(
                            'activity'
                        ).options = this.utilityService
                        .getApplicantActivities(this.model.applicant.type.key)
                        .pipe(
                            // preimpostando di default la prima
                            // viene aggiornata anche la select delle action
                            tap((c: ComboBox[]) => {
                                this.groupFields
                                    .get('applicant')
                                    .fields.get('activity')
                                    .control.setValue(c[0]);
                            })
                        );
                }

                this._setDefaultApplicant(conference);
                this._setDefaultCompany(conference);
            });
        if (!this.summary) {
            this.conferenceStoreService.hidePageLoader();
        }
    }

    get conference$(): Observable<Conference> {
        return this.conferenceStoreService.conference$.asObservable();
    }

    get model(): Procedure {
        return this.conferenceStoreService.conference.procedure;
    }

    get action(): ActionForm {
        return this.conferenceStoreService.getStepActionForm(
            StepName.PROCEDURE
        );
    }

    private _initPermission(): void {
        this.conferencePermissionsService.apply(
            this,
            this.appSections.CONFERENCE_STEP,
            this.conferenceStoreService
        );
    }

    private _setDefaultField(
        props: string[],
        model: any,
        groupfields: FormFieldGroup
    ): void {
        for (const k of props) {
            if (model[k]) {
                groupfields.fields.get(k).control.setValue(model[k]);
                groupfields.fields.get(k).control.markAsTouched();
            }
        }
    }

    private _setDefaultApplicant(conference: Conference): void {
        this._setDefaultField(
            ['name', 'surname', 'fiscalCode', 'pec'],
            conference.procedure.applicant,
            this.groupFields.get('applicant')
        );
    }

    private _setDefaultCompany(conference: Conference): void {
        this._setDefaultField(
            [
                'denomination',
                'fiscalCode',
                'vatNumber',
                'legalForm',
                'area',
                'province',
                'city',
                'address'
            ],
            conference.procedure.company,
            this.groupFields.get('company')
        );
    }

    private _initLocalizationAutocomplete(): void {
        
        this._initFieldFormAutocomplete(
            this.groupFields.get('localization').fields.get('province'),
            this.utilityService.getProvinciesAutocomplete.bind(
                this.utilityService,
                null
            )
        );

        if(this.groupFields.get('localization').fields.get('province').control.value){
            this._initFieldFormAutocomplete(
                this.groupFields.get('localization').fields.get('city'),
                this.utilityService.getCitiesAutocomplete.bind(
                    this.utilityService,
                    this.groupFields.get('localization').fields.get('province')
                        .control.value.key
                )
            );
        }
        

        this._initFieldFormAutocomplete(
            this.groupFields.get('company').fields.get('area'),
            this.utilityService.getAreasAutocomplete.bind(this.utilityService)
        );

        if (this.model.company.area && this.model.company.area.key) {
            this._initFieldFormAutocomplete(
                this.groupFields.get('company').fields.get('province'),
                this.utilityService.getProvinciesAutocomplete.bind(
                    this.utilityService,
                    this.model.company.area.key
                )
            );
        }

        if (this.model.company.province && this.model.company.province.key) {
            this._initFieldFormAutocomplete(
                this.groupFields.get('company').fields.get('city'),
                this.utilityService.getCitiesAutocomplete.bind(
                    this.utilityService,
                    this.model.company.province.key
                )
            );
        }
    }

    public setFooterButtons(buttons: Map<FooterButtons, FormButton>): void {
        this.groupButtons = new Map<FooterButtons, FormButton>();
        if (buttons != null) {
            buttons.forEach((value, key) => {
                value.onClick = this[key].bind(this);
            });
        }
        setTimeout(() => {
            this.groupButtons = buttons;
        }, 0);
    }

    private _complete(): void {
        this.conferenceStoreService.openConfirmationModal(
            FooterButtons.COMPLETE
        );
    }

    private _authorize(): void {
        this.conferenceStoreService.openConfirmationModal(
            FooterButtons.AUTHORIZE
        );
    }

    private _unauthorize(): void {
        this.conferenceStoreService.openConfirmationModal(
            FooterButtons.UNAUTHORIZE
        );
    }

    private _initCallbacks(): void {
        this.onNext = this._onNext.bind(this);
        this.saveComplete = this._saveComplete.bind(this);
        this.saveError = this._saveError.bind(this);
    }

    private _onNext(data: Procedure): Observable<WrapperPostPutData> {
        return this.conferenceStoreService.submitProcedure(data);
    }

    private _saveComplete(response: WrapperPostPutData): void {
        return this.conferenceStoreService.saveCompleteProcedure(response);
    }

    private _saveError(error: ErrorMessage): void {
        return this.conferenceStoreService.saveErrorProcedure(error);
    }

    private _createForm(): void {
        var panelHeader = 'CONFERENCE.WIZARD.PROCEDURE.COMPANY.TITLE';
        var definition=this.conferenceStoreService.conference.definition;
        if(definition.instance.conferenceType.key === ConferenceType.DOMUS)
            panelHeader = 'CONFERENCE.WIZARD.PROCEDURE.COMPANY.TITLE_USR';
        else if(definition.instance.conferenceType.key === ConferenceType.REGIONAL 
                    || definition.instance.conferenceType.key === ConferenceType.BROADBAND)
            panelHeader = 'CONFERENCE.WIZARD.PROCEDURE.COMPANY.TITLE_BUL';
        else if(definition.instance.conferenceType.key === ConferenceType.ENVIRONMENT_DEFINITION_VIA 
                || definition.instance.conferenceType.key === ConferenceType.ENVIRONMENT_DEFINITION_AIA)
            panelHeader = 'CONFERENCE.WIZARD.PROCEDURE.COMPANY.TITLE_VIA_AIA';

        var companyFields = new Map()
            .set('denomination', {
                type: 'text',
                label:
                    'CONFERENCE.WIZARD.PROCEDURE.COMPANY.INPUTS_LABEL.DENOMINATION',
                control: new FormControl(
                    this.model.company.denomination
                ),
                size: '12|6|6'
            })
            .set('fiscalCode', {
                type: 'text',
                label:
                    'CONFERENCE.WIZARD.PROCEDURE.COMPANY.INPUTS_LABEL.FISCAL_CODE',
                pattern:
                    '(^[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]$)|(^[0-9]{11}$)',
                control: new FormControl(
                    this.model.company.fiscalCode,
                    []
                ),
                errorLabels: [ErrorLabelConstants.REGEX('ERROR.REGEX')],
                size: '12|6|6'
            });

            if(definition.instance.conferenceType.key !== ConferenceType.DOMUS)
                companyFields.set('vatNumber', {
                    type: 'text',
                    label:
                        'CONFERENCE.WIZARD.PROCEDURE.COMPANY.INPUTS_LABEL.VAT_NUMBER',
                    pattern: '^(IT)?[0-9]{11}$',
                    control: new FormControl(
                        this.model.company.vatNumber,
                        []
                    ),
                    errorLabels: [ErrorLabelConstants.REGEX('ERROR.REGEX')],
                    size: '12|6|6'
                });

            companyFields.set('legalForm', {
                type: 'select',
                label:
                    'CONFERENCE.WIZARD.PROCEDURE.COMPANY.INPUTS_LABEL.LEGAL_FORM',
    
                control: new FormControl(this.model.company.legalForm),
                size: '12|6|6',
                options: this.utilityService.getLegalForms()
            })
            .set('area', {
                type: 'autocomplete',
                label:
                    'CONFERENCE.WIZARD.PROCEDURE.COMPANY.INPUTS_LABEL.AREA',
                control: new FormControl({
                    value: this.model.company.area,
                    disabled: this.isReadonly()
                }),
                size: '12|6|6',
                notFoundText:
                    'CONFERENCE.AUTOCOMPLETE.INSERT_MIN_CHARACTERS',
                loading: false,
                valueChange: (area: ComboBox) => {
                    this.loggerService.log('valueChange', area);
    
                    if (area && area.key) {
                        this.groupFields
                            .get('company')
                            .fields.get('province')
                            .control.reset();
    
                        this.groupFields
                            .get('company')
                            .fields.get('city')
                            .control.reset();
    
                        this._initFieldFormAutocomplete(
                            this.groupFields
                                .get('company')
                                .fields.get('province'),
                            this.utilityService.getProvinciesAutocomplete.bind(
                                this.utilityService,
                                area.key
                            )
                        );
                    }
                },
                onClear: () => {
                    this.groupFields
                        .get('company')
                        .fields.get('province').options = of([]);
    
                    this.groupFields
                        .get('company')
                        .fields.get('province')
                        .control.reset();
    
                    this.groupFields
                        .get('company')
                        .fields.get('city').options = of([]);
    
                    this.groupFields
                        .get('company')
                        .fields.get('city')
                        .control.reset();
                }
            })
            .set('province', {
                type: 'autocomplete',
                label:
                    'CONFERENCE.WIZARD.PROCEDURE.COMPANY.INPUTS_LABEL.PROVINCE',
                control: new FormControl({
                    value: this.model.company.province,
                    disabled: this.isReadonly()
                }),
                size: '12|6|6',
                notFoundText:
                    'CONFERENCE.AUTOCOMPLETE.INSERT_MIN_CHARACTERS',
                loading: false,
                valueChange: (province: ComboBox) => {
                    this.loggerService.log('valueChange', province);
    
                    if (province && province.key) {
                        this.groupFields
                            .get('company')
                            .fields.get('city')
                            .control.reset();
    
                        this._initFieldFormAutocomplete(
                            this.groupFields
                                .get('company')
                                .fields.get('city'),
                            this.utilityService.getCitiesAutocomplete.bind(
                                this.utilityService,
                                province.key
                            )
                        );
                    }
                },
                onClear: () => {
                    this.groupFields
                        .get('company')
                        .fields.get('city').options = of([]);
    
                    this.groupFields
                        .get('company')
                        .fields.get('city')
                        .control.reset();
                }
            })
            .set('city', {
                type: 'autocomplete',
                label:
                    'CONFERENCE.WIZARD.PROCEDURE.COMPANY.INPUTS_LABEL.CITY',
                control: new FormControl({
                    value: this.model.company.city,
                    disabled: this.isReadonly()
                }),
                size: '12|6|6',
                notFoundText:
                    'CONFERENCE.AUTOCOMPLETE.INSERT_MIN_CHARACTERS',
                loading: false
            })
            .set('address', {
                type: 'text',
                label:
                    'CONFERENCE.WIZARD.PROCEDURE.COMPANY.INPUTS_LABEL.ADDRESS',
    
                control: new FormControl(this.model.company.address),
                size: '12|6|6'
            });
    
        if (this.model.applicant != null &&
            this.model.applicant.activity.key == "32") {
            this.groupFields
                .set('applicant', {
                    panel: true,
                    panelHead: 'CONFERENCE.WIZARD.PROCEDURE.APPLICANT.TITLE',
                    accordion: true,
                    fields: new Map()
                        .set('requestReference', {
                            type: 'text',
                            label:
                                'CONFERENCE.WIZARD.PROCEDURE.APPLICANT.INPUTS_LABEL.REQUEST_REFERENCE',
                            control: new FormControl(
                                this.model.applicant.requestReference,
                                [Validators.required]
                            ),
                            errorLabels: [ErrorLabelConstants.REQUIRED],
                            size: '12|6|6'
                        })
                        .set('startDate', {
                            type: 'date',
                            label:
                                'CONFERENCE.WIZARD.PROCEDURE.APPLICANT.INPUTS_LABEL.START_DATE',
                            control: new FormControl(
                                this.model.applicant.startDate,
                                [Validators.required]
                            ),
                            errorLabels: [ErrorLabelConstants.REQUIRED],
                            size: '12|6|6'
                        })
                        .set('surname', {
                            type: 'text',
                            label:
                                'CONFERENCE.WIZARD.PROCEDURE.APPLICANT.INPUTS_LABEL.PUBIC_NAME',
                            control: new FormControl(this.model.applicant.surname, [
                                Validators.required
                            ]),
                            errorLabels: [ErrorLabelConstants.REQUIRED],
                            size: '12|6|6'
                        })
                        .set('fiscalCode', {
                            type: 'text',
                            //pattern:
                              //  '^[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]$',
                            label:
                                'CONFERENCE.WIZARD.PROCEDURE.APPLICANT.INPUTS_LABEL.FISCAL_CODE',
                            control: new FormControl(
                                this.model.applicant.fiscalCode,
                                [Validators.required]
                            ),
                            errorLabels: [
                                ErrorLabelConstants.REQUIRED,
                                ErrorLabelConstants.REGEX('ERROR.REGEX')
                            ],
                            size: '12|6|6'
                        })
                        .set('pec', {
                            type: 'text',
                            pattern: '^[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,4}$',
                            label:
                                'CONFERENCE.WIZARD.PROCEDURE.APPLICANT.INPUTS_LABEL.PEC',
                            control: new FormControl(this.model.applicant.pec, [Validators.required]),
                            errorLabels: [
                                ErrorLabelConstants.REGEX('ERROR.EMAIL.INVALID')
                            ],
                            size: '12|6|6'
                        })
                        .set('type', {
                            type: 'select',
                            label:
                                'CONFERENCE.WIZARD.PROCEDURE.APPLICANT.INPUTS_LABEL.TYPE',
                            control: new FormControl(this.model.applicant.type, []),
                            size: '12|6|6',
                            options: this.utilityService.getApplicantTypes(),
                            valueChange: (type: ComboBox) => {
                                this.loggerService.log('valueChange', type);
                                const activityField = this.groupFields
                                    .get('applicant')
                                    .fields.get('activity');
    
                                activityField.options = this.utilityService
                                    .getApplicantActivities(type.key)
                                    .pipe(
                                        // preimpostando di default la prima
                                        // viene aggiornata anche la select delle action
                                        tap((c: ComboBox[]) => {
                                            activityField.control.setValue(c[0]);
                                        })
                                    );
                            }
                        })
                        .set('activity', {
                            type: 'select',
                            label:
                                'CONFERENCE.WIZARD.PROCEDURE.APPLICANT.INPUTS_LABEL.ACTIVITY',
                            control: new FormControl(
                                this.model.applicant.activity,
                                []
                            ),
                            size: '12|6|6',
                            options: this.utilityService.getApplicantActivities(
                                this.model.applicant.type.key
                            ),
                            valueChange: (activity: ComboBox) => {
                                this.loggerService.log('valueChange', activity);
    
                                this.groupFields
                                    .get('applicant')
                                    .fields.get(
                                        'action'
                                    ).options = this.utilityService
                                    .getApplicantActions(activity.key)
                                    .pipe(
                                        // preimpostando di default la prima
                                        // viene aggiornata anche la select delle action
                                        tap((c: ComboBox[]) => {
                                            this.groupFields
                                                .get('applicant')
                                                .fields.get('action')
                                                .control.setValue(c[0]);
                                        })
                                    );
                            }
                        })
                        .set('action', {
                            type: 'select',
                            label:
                                'CONFERENCE.WIZARD.PROCEDURE.APPLICANT.INPUTS_LABEL.ACTION',
                            control: new FormControl(
                                this.model.applicant.action,
                                []
                            ),
                            size: '12|6|6',
                            options: this.utilityService.getApplicantActions(
                                this.model.applicant.activity.key
                            )
                        })
                });
        } else {
            this.groupFields
                .set('applicant', {
                    panel: true,
                    panelHead: 'CONFERENCE.WIZARD.PROCEDURE.APPLICANT.TITLE',
                    accordion: true,
                    fields: new Map()
                        .set('requestReference', {
                            type: 'text',
                            label:
                                'CONFERENCE.WIZARD.PROCEDURE.APPLICANT.INPUTS_LABEL.REQUEST_REFERENCE',
                            control: new FormControl(
                                this.model.applicant.requestReference,
                                [Validators.required]
                            ),
                            errorLabels: [ErrorLabelConstants.REQUIRED],
                            size: '12|6|6'
                        })
                        .set('startDate', {
                            type: 'date',
                            label:
                                'CONFERENCE.WIZARD.PROCEDURE.APPLICANT.INPUTS_LABEL.START_DATE',
                            control: new FormControl(
                                this.model.applicant.startDate,
                                [Validators.required]
                            ),
                            errorLabels: [ErrorLabelConstants.REQUIRED],
                            size: '12|6|6'
                        })
                        .set('name', {
                            type: 'text',
                            label:
                                'CONFERENCE.WIZARD.PROCEDURE.APPLICANT.INPUTS_LABEL.NAME',
                            control: new FormControl(this.model.applicant.name, [
                                Validators.required
                            ]),
                            errorLabels: [ErrorLabelConstants.REQUIRED],
                            size: '12|6|6'
                        })
                        .set('surname', {
                            type: 'text',
                            label:
                                'CONFERENCE.WIZARD.PROCEDURE.APPLICANT.INPUTS_LABEL.SURNAME',
                            control: new FormControl(this.model.applicant.surname, [
                                Validators.required
                            ]),
                            errorLabels: [ErrorLabelConstants.REQUIRED],
                            size: '12|6|6'
                        })
                        .set('fiscalCode', {
                            type: 'text',
                            pattern:
                                '^[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]$',
                            label:
                                'CONFERENCE.WIZARD.PROCEDURE.APPLICANT.INPUTS_LABEL.FISCAL_CODE',
                            control: new FormControl(
                                this.model.applicant.fiscalCode,
                                [Validators.required]
                            ),
                            errorLabels: [
                                ErrorLabelConstants.REQUIRED,
                                ErrorLabelConstants.REGEX('ERROR.REGEX')
                            ],
                            size: '12|6|6'
                        })
                        .set('pec', {
                            type: 'text',
                            pattern: '^[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,4}$',
                            label:
                                'CONFERENCE.WIZARD.PROCEDURE.APPLICANT.INPUTS_LABEL.PEC',
                            control: new FormControl(this.model.applicant.pec, [Validators.required]),
                            errorLabels: [
                                ErrorLabelConstants.REGEX('ERROR.EMAIL.INVALID')
                            ],
                            size: '12|6|6'
                        })
                        .set('type', {
                            type: 'select',
                            label:
                                'CONFERENCE.WIZARD.PROCEDURE.APPLICANT.INPUTS_LABEL.TYPE',
                            control: new FormControl(this.model.applicant.type, []),
                            size: '12|6|6',
                            options: this.utilityService.getApplicantTypes(),
                            valueChange: (type: ComboBox) => {
                                this.loggerService.log('valueChange', type);
                                const activityField = this.groupFields
                                    .get('applicant')
                                    .fields.get('activity');
    
                                activityField.options = this.utilityService
                                    .getApplicantActivities(type.key)
                                    .pipe(
                                        // preimpostando di default la prima
                                        // viene aggiornata anche la select delle action
                                        tap((c: ComboBox[]) => {
                                            activityField.control.setValue(c[0]);
                                        })
                                    );
                            }
                        })
                        .set('activity', {
                            type: 'select',
                            label:
                                'CONFERENCE.WIZARD.PROCEDURE.APPLICANT.INPUTS_LABEL.ACTIVITY',
                            control: new FormControl(
                                this.model.applicant.activity,
                                []
                            ),
                            size: '12|6|6',
                            options: this.utilityService.getApplicantActivities(
                                this.model.applicant.type.key
                            ),
                            valueChange: (activity: ComboBox) => {
                                this.loggerService.log('valueChange', activity);
    
                                this.groupFields
                                    .get('applicant')
                                    .fields.get(
                                        'action'
                                    ).options = this.utilityService
                                    .getApplicantActions(activity.key)
                                    .pipe(
                                        // preimpostando di default la prima
                                        // viene aggiornata anche la select delle action
                                        tap((c: ComboBox[]) => {
                                            this.groupFields
                                                .get('applicant')
                                                .fields.get('action')
                                                .control.setValue(c[0]);
                                        })
                                    );
                            }
                        })
                        .set('action', {
                            type: 'select',
                            label:
                                'CONFERENCE.WIZARD.PROCEDURE.APPLICANT.INPUTS_LABEL.ACTION',
                            control: new FormControl(
                                this.model.applicant.action,
                                []
                            ),
                            size: '12|6|6',
                            options: this.utilityService.getApplicantActions(
                                this.model.applicant.activity.key
                            )
                        })
                });
        }
        
        
        this.groupFields .set('localization', {
                panel: true,
                panelHead: 'CONFERENCE.WIZARD.PROCEDURE.LOCALIZATION.TITLE',
                accordion: true,
                fields: new Map()
                    .set('province', {
                        type: 'autocomplete',
                        label:
                            'CONFERENCE.WIZARD.PROCEDURE.LOCALIZATION.INPUTS_LABEL.PROVINCE',
                        control: new FormControl(
                            {
                                value: this.model.localization.province,
                                disabled: this.isReadonly()
                            },
                            [Validators.required]
                        ),
                        size: '12|6|6',
                        errorLabels: [ErrorLabelConstants.REQUIRED],
                        notFoundText:
                            'CONFERENCE.AUTOCOMPLETE.INSERT_MIN_CHARACTERS',
                        loading: false,
                        valueChange: (province: ComboBox) => {
                            this.loggerService.log('valueChange', province);
                            
                            if (province && province.key) {
                                this.groupFields
                                    .get('localization')
                                    .fields.get('city')
                                    .control.reset();

                                this._initFieldFormAutocomplete(
                                    this.groupFields
                                        .get('localization')
                                        .fields.get('city'),
                                    this.utilityService.getCitiesAutocomplete.bind(
                                        this.utilityService,
                                        province.key
                                    )
                                );
                            }
                        },
                        onClear: () => {
                            this.groupFields
                                .get('localization')
                                .fields.get('province')
                                .control.markAsTouched();

                            this.groupFields
                                .get('localization')
                                .fields.get('city').options = of([]);

                            this.groupFields
                                .get('localization')
                                .fields.get('city')
                                .control.reset();

                            this.groupFields
                                .get('localization')
                                .fields.get('city')
                                .control.markAsTouched();
                        }
                    })
                    .set('city', {
                        type: 'autocomplete',
                        label:
                            'CONFERENCE.WIZARD.PROCEDURE.LOCALIZATION.INPUTS_LABEL.CITY',
                        control: new FormControl(
                            {
                                value: this.model.localization.city,
                                disabled: this.isReadonly()
                            },
                            [Validators.required]
                        ),
                        errorLabels: [ErrorLabelConstants.REQUIRED],
                        size: '12|6|6',
                        notFoundText:
                            'CONFERENCE.AUTOCOMPLETE.INSERT_MIN_CHARACTERS',
                        loading: false
                    })
                    .set('address', {
                        type: 'text',
                        label:
                            'CONFERENCE.WIZARD.PROCEDURE.LOCALIZATION.INPUTS_LABEL.ADDRESS',
                        control: new FormControl(
                            this.model.localization.address,
                            []
                        ),
                        size: '12|12|6'
                    })
            })
        this.groupFields.set('company', {
                panel: true,
                panelHead: panelHeader,
                accordion: true,
                fields: companyFields
            });
    }

    isReadonly(): boolean {
        return this.action === ActionForm.READONLY;
    }

    setProcedureFieldsAsReadOnly() {
        this.groupFields.forEach((value: FormFieldGroup, key: string) => {
            value.fields.forEach((_value: FormField, _key: string) => {
                _value.disabled = true;
                _value.control.disable();
            });
        });
    }

    setStepAsReadOnly() {
        this.setProcedureFieldsAsReadOnly();
    }

    getFooterTextFormButton(): string {
        return this.action === ActionForm.CREATE
            ? 'BUTTON.SAVE_NEXT'
            : 'BUTTON.SAVE';
    }
}
