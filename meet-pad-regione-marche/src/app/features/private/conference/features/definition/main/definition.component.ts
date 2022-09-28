import {
    Component,
    OnInit,
    ViewChild,
    Output,
    EventEmitter,
    Input
} from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { HttpResponse } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { map, takeUntil, catchError } from 'rxjs/operators';

import * as business from 'moment-business-days';

import { ToastrService } from 'ngx-toastr';

import {
    TooltipModel,
    DateModel,
    ErrorMessage,
    ErrorLabelConstants,
    AbstractTableField,
    TableField,
    TemplateField,
    ModalComponent,
    ActionItem
} from '@eng-ds/ng-toolkit';

import { LoggerService, I18nService } from '@eng-ds/ng-core';

import {
    ComboBox,
    WrapperPostPutData,
    LoaderService,
    WrapperDeleteData,
    SectionLoading,
    AutoUnsubscribe,
    FormFieldGroup,
    FormField,
    FooterButtons,
    FormButton,
    WrapperResponse
} from '@common';

import { UtilityService, ActionForm, StepName, Mixin } from '@app/core';

import { environment } from '@env/environment';
import { customConfigurationConf } from '@config';

import { AddressType } from '@app/core/enums/address-type.enum';

import {
    Definition,
    SupportContact,
    MeetAddress,
    ConferenceStoreService,
    ConferencePermissionsService,
    SupportContactService,
    FieldDateName,
    DateRule
} from '@features/private/conference/core';

import { FormStep } from '../../../core/mixins';

@Component({
    selector: 'app-conference-definition',
    templateUrl: './definition.component.html',
    styleUrls: ['./definition.component.scss']
})
@Mixin([FormStep])
export class DefinitionComponent extends AutoUnsubscribe implements OnInit {
    @ViewChild('actionTemplate') actionTemplate;
    @ViewChild('contactModal') contactModal: ModalComponent;

    @Input('summary') summary: boolean = false;
    @Output('openConfirmModal') openConfirmModal = new EventEmitter<
        FooterButtons
    >();

    extractDataToSubmit: (form: FormGroup) => Definition;
    onNext: (data: Definition) => Observable<WrapperPostPutData>;
    saveComplete: (response: WrapperPostPutData) => void;
    saveError: (error: ErrorMessage) => void;

    groupFields: Map<string, FormFieldGroup> = new Map();
    contactGroupFields: Map<string, FormFieldGroup> = new Map();

    typeRadioOptions: Observable<
        ComboBox[]
    > = this.utilityService.getConferenceTypes();
    typeAddressOptions: Observable<
        ComboBox[]
    > = this.utilityService.getAddressTypes();

    editContact: ActionItem;

    footerContactButtons: boolean = true;
    footerTextSubmitBtn: string = 'BUTTON.UPDATE';

    groupButtons: Map<FooterButtons, FormButton> = new Map();

    extractSupportContact: (form: FormGroup) => SupportContact;
    updateSupportContact: (
        contact: SupportContact
    ) => Observable<WrapperPostPutData>;
    updateContactCompleteFn: (response: WrapperPostPutData) => void;
    updateContactErrorFn: (error: ErrorMessage) => void;

    tmpContact: SupportContact = null;

    constructor(
        private loggerService: LoggerService,
        private i18nService: I18nService,
        private utilityService: UtilityService,
        private toastr: ToastrService,
        private conferenceStoreService: ConferenceStoreService,
        private supportContactService: SupportContactService,
        private loaderService: LoaderService,
        private conferencePermissionsService: ConferencePermissionsService
    ) {
        super();
    }

    get model(): Definition {
        return this.conferenceStoreService.conference.definition;
    }

    get action(): ActionForm {
        return this.conferenceStoreService.getStepActionForm(
            StepName.DEFINITION
        );
    }

    get conferenceId(): string {
        return this.conferenceStoreService.conference.id;
    }

    get template() {
        return this.conferenceStoreService.template.definition;
    }

    ngOnInit(): void {
        this._initPermission();
        this._initCallbacks();
        this._createForm();
    }

    deleteSupportContact(supportContact: SupportContact): void {
        this.supportContactService
            .deleteContact(supportContact.id)
            .subscribe((res: WrapperDeleteData) => {
                this.model.deleteSupportContact(supportContact);

                this.toastr.info(
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.DEFINITION.SUPPORT_CONTACTS.TOASTR.SUCCESS_DELETE.TEXT'
                    ),
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.DEFINITION.SUPPORT_CONTACTS.TOASTR.SUCCESS_DELETE.TITLE'
                    )
                );
            });
    }

    /**
     *
     * @param buttons richiamata dalla direttiva per i ruoli
     */
    setFooterButtons(buttons: Map<FooterButtons, FormButton>): void {
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

    /**
     * Sembra non essere richiamata
     */
    setStepAsReadOnly() {
        this._setDefinitionFieldsAsReadOnly();
        this.groupFields.get('supportContactsPanel').readonly = true;
    }

    getFooterTextFormButton(): string {
        return this.action === ActionForm.CREATE
            ? 'BUTTON.SAVE_NEXT'
            : 'BUTTON.SAVE';
    }

    isReadonly(): boolean {
        return this.action === ActionForm.READONLY;
    }

    private _saveSupportContact(
        supportContact: SupportContact
    ): Observable<void | WrapperPostPutData> {
        let obs: Observable<void | WrapperPostPutData> = null;

        obs = this.supportContactService
            .createContact(this.conferenceId, supportContact)
            .pipe(
                map((res: WrapperPostPutData) => {
                    supportContact.id = res.id;
                    this.model.supportContacts.push(
                        new SupportContact(supportContact)
                    );
                    this.toastr.info(
                        this.i18nService.translate(
                            'CONFERENCE.WIZARD.DEFINITION.SUPPORT_CONTACTS.TOASTR.SUCCESS_SAVE.TEXT'
                        ),
                        this.i18nService.translate(
                            'CONFERENCE.WIZARD.DEFINITION.SUPPORT_CONTACTS.TOASTR.SUCCESS_SAVE.TITLE'
                        )
                    );
                })
            );

        return obs;
    }

    private _setDefinitionFieldsAsReadOnly() {
        this.groupFields.forEach((value: FormFieldGroup, key: string) => {
            value.fields.forEach((_value: FormField, _key: string) => {
                _value.disabled = true;
                _value.control.disable();
            });
        });
    }

    private _createForm(): void {
        this._createGroupFields();
        this._applyTemplate();

        if (
            this.model.instance.address &&
            this.model.instance.address.type &&
            this.model.instance.address.type.key ===
                AddressType.PHYSICAL_MEETING
        ) {
            this._setPhysicalMeetFields();
        } else {
            this._setOnlineMeetFields();
        }

        if (this.isReadonly()) {
            this._setDefinitionFieldsAsReadOnly();
        }

        this._setSupportContactsGroupFields();
        this._applyGroupFieldsChange();

        if (!this.summary) {
            this.conferenceStoreService.hidePageLoader();
        }
    }

    private _createGroupFields(): void {
        this.groupFields
            .set('determination', {
                panel: true,
                panelHead: 'CONFERENCE.WIZARD.DEFINITION.DETERMINATION.TITLE',
                accordion: true,
                fields: new Map().set('determinationObject', {
                    type: 'text-area',
                    label:
                        'CONFERENCE.WIZARD.DEFINITION.DETERMINATION.DETERMINATION_OBJECT_INPUT',
                    control: new FormControl(
                        this.model.determination.determinationObject
                    ),
                    valueChange: evt => {
                        this.loggerService.log('valueChange', evt);
                    },
                    size: '12|12|12',
                    maxLength: 2000,
                    rows: 15
                })
            })
            .set('instance', {
                panel: true,
                panelHead: 'CONFERENCE.WIZARD.DEFINITION.INSTANCE.TITLE',
                accordion: true,
                fields: new Map()
                    .set('requestReference', {
                        type: 'text',
                        label:
                            'CONFERENCE.WIZARD.DEFINITION.INSTANCE.INSTANCE_REFERENCE_LABEL',
                        control: new FormControl(
                            this.model.instance.requestReference,
                            []
                        ),
                        size: '12|6|6'
                    })
                    .set('conferenceType', {
                        type: 'select',
                        label:
                            'CONFERENCE.WIZARD.DEFINITION.INSTANCE.CONFERENCE_TYPE',
                        control: new FormControl(
                            {
                                value: this.model.instance.conferenceType,
                                disabled: true
                            },
                            []
                        ),
                        size: '12|6|6',
                        options: this.typeRadioOptions
                    })
                    .set('creationDate', {
                        type: 'date',
                        label:
                            'CONFERENCE.WIZARD.DEFINITION.INSTANCE.CREATION_DATE',
                        control: new FormControl(
                            this.model.instance.creationDate,
                            []
                        ),
                        size: '12|6|6',
                        valueChange: evt => {
                            const today = new Date();
                            if (evt) {
                                if (evt.date <= today) {
                                    this.model.instance.creationDate = new DateModel(
                                        evt.date
                                    );
                                } else {
                                    this.groupFields
                                        .get('instance')
                                        .fields.get('creationDate')
                                        .control.setValue(today);

                                    this.model.instance.creationDate = new DateModel(
                                        today
                                    );

                                    this.toastr.warning(
                                        this.i18nService.translate(
                                            'CONFERENCE.WIZARD.DEFINITION.TOASTR.DATE_WARNING.TEXT'
                                        ),
                                        this.i18nService.translate(
                                            'CONFERENCE.WIZARD.DEFINITION.TOASTR.DATE_WARNING.TITLE'
                                        )
                                    );
                                }
                            }
                        }
                    })
                    .set('endIntegrationDate', {
                        type: 'date',
                        label:
                            'CONFERENCE.WIZARD.DEFINITION.INSTANCE.END_INTEGRATION_DATE',
                        control: new FormControl(
                            this.model.instance.endIntegrationDate,
                            []
                        ),
                        size: '12|6|6',
                        tooltip: customConfigurationConf.definitionDatesControl
                            ? new TooltipModel(
                                  'CONFERENCE.WIZARD.DEFINITION.INSTANCE.TOOLTIP.TEXT_END_INTEGRATION_DATE',
                                  undefined,
                                  'CONFERENCE.WIZARD.DEFINITION.INSTANCE.TOOLTIP.TITLE_END_INTEGRATION_DATE'
                              )
                            : null,
                        valueChange: evt => {
                            const creationField = this.groupFields
                                .get('instance')
                                .fields.get('creationDate').control;
                                
                            if (!evt) {
                                if (
                                    this.template.instance.mustBeEmptyDate(
                                        FieldDateName.END_INTEGRATION_DATE
                                    )
                                ) {
                                    this._cancelDateError(
                                        FieldDateName.END_INTEGRATION_DATE
                                    );
                                }
                            } else if (
                                creationField.value &&
                                creationField.value.date
                            ) {
                                if (evt.date >= creationField.value.date) {
                                    this.model.instance.endIntegrationDate = new DateModel(
                                        evt.date
                                    );
                                } else {
                                    this.groupFields
                                        .get('instance')
                                        .fields.get('endIntegrationDate')
                                        .control.setValue('');

                                    this.toastr.warning(
                                        this.i18nService.translate(
                                            'CONFERENCE.WIZARD.DEFINITION.TOASTR.DATE_CREATION_WARNING.TEXT'
                                        ),
                                        this.i18nService.translate(
                                            'CONFERENCE.WIZARD.DEFINITION.TOASTR.DATE_CREATION_WARNING.TITLE'
                                        )
                                    );
                                }
                            }
                        }
                    })
                    .set('endOpinionDate', {
                        type: 'date',
                        label:
                            'CONFERENCE.WIZARD.DEFINITION.INSTANCE.END_OPINION_DATE',
                        control: new FormControl(
                            this.model.instance.endOpinionDate,
                            []
                        ),
                        size: '12|6|6',
                        tooltip: customConfigurationConf.definitionDatesControl
                            ? new TooltipModel(
                                  'CONFERENCE.WIZARD.DEFINITION.INSTANCE.TOOLTIP.TEXT_END_OPINION_DATE',
                                  undefined,
                                  'CONFERENCE.WIZARD.DEFINITION.INSTANCE.TOOLTIP.TITLE_END_OPINION_DATE'
                              )
                            : null,
                        valueChange: evt => {
                            const creationField = this.groupFields
                                .get('instance')
                                .fields.get('creationDate').control;

                            if (!evt) {
                                if (
                                    this.template.instance.mustBeEmptyDate(
                                        FieldDateName.END_OPINION_DATE
                                    )
                                ) {
                                    this._cancelDateError(
                                        FieldDateName.END_OPINION_DATE
                                    );
                                }
                            } else if (
                                creationField.value &&
                                creationField.value.date
                            ) {
                                if (evt.date >= creationField.value.date) {
                                    this.model.instance.endOpinionDate = new DateModel(
                                        evt.date
                                    );
                                } else {
                                    this.groupFields
                                        .get('instance')
                                        .fields.get('endOpinionDate')
                                        .control.setValue('');

                                    this.toastr.warning(
                                        this.i18nService.translate(
                                            'CONFERENCE.WIZARD.DEFINITION.TOASTR.DATE_CREATION_WARNING.TEXT'
                                        ),
                                        this.i18nService.translate(
                                            'CONFERENCE.WIZARD.DEFINITION.TOASTR.DATE_CREATION_WARNING.TITLE'
                                        )
                                    );
                                }
                            }
                        }
                    })
                    .set('firstSessionDate', {
                        type: 'date',
                        label:
                            'CONFERENCE.WIZARD.DEFINITION.INSTANCE.FIRST_SESSION_DATE',
                        control: new FormControl(
                            this.model.instance.firstSessionDate,
                            []
                        ),
                        size: '12|6|6',
                        tooltip: customConfigurationConf.definitionDatesControl
                            ? new TooltipModel(
                                  'CONFERENCE.WIZARD.DEFINITION.INSTANCE.TOOLTIP.TEXT_FIRST_SESSION_DATE',
                                  undefined,
                                  'CONFERENCE.WIZARD.DEFINITION.INSTANCE.TOOLTIP.TITLE_FIRST_SESSION_DATE'
                              )
                            : null,
                        valueChange: evt => {
                            const creationField = this.groupFields
                                .get('instance')
                                .fields.get('creationDate').control;
                            if (
                                evt &&
                                creationField.value &&
                                creationField.value.date
                            ) {
                                if (evt.date >= creationField.value.date) {
                                    this.model.instance.firstSessionDate = new DateModel(
                                        evt.date
                                    );
                                } else {
                                    this.groupFields
                                        .get('instance')
                                        .fields.get('firstSessionDate')
                                        .control.setValue('');

                                    this.toastr.warning(
                                        this.i18nService.translate(
                                            'CONFERENCE.WIZARD.DEFINITION.TOASTR.DATE_CREATION_WARNING.TEXT'
                                        ),
                                        this.i18nService.translate(
                                            'CONFERENCE.WIZARD.DEFINITION.TOASTR.DATE_CREATION_WARNING.TITLE'
                                        )
                                    );
                                }
                            }
                        }
                    })
                    .set('expirationDate', {
                        type: 'date',
                        label:
                            'CONFERENCE.WIZARD.DEFINITION.INSTANCE.EXPIRATION_DATE',
                        control: new FormControl(
                            this.model.instance.expirationDate,
                            []
                        ),
                        size: '12|6|6',
                        tooltip: customConfigurationConf.definitionDatesControl
                            ? new TooltipModel(
                                  'CONFERENCE.WIZARD.DEFINITION.INSTANCE.TOOLTIP.TEXT_EXPIRATION_DATE',
                                  undefined,
                                  'CONFERENCE.WIZARD.DEFINITION.INSTANCE.TOOLTIP.TITLE_EXPIRATION_DATE'
                              )
                            : null,
                        valueChange: evt => {
                            const creationField = this.groupFields
                                .get('instance')
                                .fields.get('creationDate').control;

                            if (
                                evt &&
                                creationField.value &&
                                creationField.value.date
                            ) {
                                if (evt.date >= creationField.value.date) {
                                    this.model.instance.expirationDate = new DateModel(
                                        evt.date
                                    );
                                } else {
                                    this.groupFields
                                        .get('instance')
                                        .fields.get('expirationDate')
                                        .control.setValue('');

                                    this.toastr.warning(
                                        this.i18nService.translate(
                                            'CONFERENCE.WIZARD.DEFINITION.TOASTR.DATE_CREATION_WARNING.TEXT'
                                        ),
                                        this.i18nService.translate(
                                            'CONFERENCE.WIZARD.DEFINITION.TOASTR.DATE_CREATION_WARNING.TITLE'
                                        )
                                    );
                                }
                            }
                        }
                    })
            });

        if(this.model.instance.dateUpdateList.length > 0){

            let fields = new Map();
            this.model.instance.dateUpdateList.forEach( field => {

                fields.set(field.code,
                {
                    type: 'date',
                    label: this.i18nService.translate(
                        'CONFERENCE.WIZARD.DEFINITION.INSTANCE.' + field.code
                    ) ,
                    control: new FormControl(
                        {
                            value: field.date,
                            disabled: true
                        }
                    ),
                    size: '12|6|6'
                });

            })            

            this.groupFields.set('dateUpdate', {
                panel: true,
                panelHead: 'CONFERENCE.WIZARD.DEFINITION.DATE_UPDATE.TITLE',
                accordion: true,
                fields: fields
            });
        }

        this.groupFields.set('meetFields', {
            panel: true,
            panelHead:
                'CONFERENCE.WIZARD.DEFINITION.INSTANCE.ADDRESS_TYPE_TITLE',
            accordion: true,
            fields: new Map().set('type', {
                type: 'select',
                label: 'CONFERENCE.WIZARD.DEFINITION.INSTANCE.ADDRESS_TYPE',
                control: new FormControl(
                    this.model.instance.address
                        ? this.model.instance.address.type
                        : '',
                    []
                ),
                size: '12|6|6',
                options: this.typeAddressOptions,
                valueChange: (addressType: ComboBox) => {
                    if (addressType.key === AddressType.PHYSICAL_MEETING) {
                        this._deleteOnlineMeetFields();
                        this._setPhysicalMeetFields();
                    } else {
                        this._deletePhysicalMeetFields();
                        this._setOnlineMeetFields();
                    }
                    this._applyGroupFieldsChange();
                }
            })
        });
    }

    private _initPermission(): void {
        this.conferencePermissionsService.apply(
            this,
            this.appSections.CONFERENCE_STEP,
            this.conferenceStoreService
        );
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

    private _deleteOnlineMeetFields(): void {
        const fields = this.groupFields.get('meetFields').fields;

        fields.delete('address');
        this.model.instance.address.address = '';
    }

    private _setOnlineMeetFields(): void {
        this.groupFields.get('meetFields').fields.set('address', {
            type: 'text',
            label: 'CONFERENCE.WIZARD.DEFINITION.INSTANCE.ADDRESS',
            control: new FormControl(
                {
                    value: this.model.instance.address
                        ? this.model.instance.address.address
                        : '',
                    disabled: this.isReadonly()
                },
                [
                    Validators.required,
                    // tslint:disable-next-line:max-line-length
                    Validators.pattern(
                        '^(http://www.|https://www.|http://|https://)?[a-z0-9]+([-.]{1}[a-z0-9]+)*.[a-z]{2,5}(:[0-9]{1,5})?(/.*)?$'
                    )
                ]
            ),
            errorLabels: [
                ErrorLabelConstants.REQUIRED,
                ErrorLabelConstants.REGEX('ERROR.REGEX')
            ],
            size: '12|6|6',
            tooltip: new TooltipModel(
                'CONFERENCE.WIZARD.DEFINITION.INSTANCE.TOOLTIP.TEXT_ADDRESS',
                undefined,
                'CONFERENCE.WIZARD.DEFINITION.INSTANCE.TOOLTIP.TITLE_ADDRESS'
            )
        });
    }

    private _deletePhysicalMeetFields(): void {
        const fields = this.groupFields.get('meetFields').fields;

        fields.delete('address');
        fields.delete('cap');
        fields.delete('city');
        fields.delete('province');

        this.model.instance.address.address = '';
        delete this.model.instance.address.cap;
        delete this.model.instance.address.city;
        delete this.model.instance.address.province;
    }

    private _setPhysicalMeetFields(): void {
        this.groupFields
            .get('meetFields')
            .fields.set('province', {
                type: 'autocomplete',
                label: 'CONFERENCE.WIZARD.DEFINITION.INSTANCE.PROVINCE',
                control: new FormControl(
                    {
                        value: this.model.instance.address
                            ? this.model.instance.address.province
                            : '',
                        disabled: this.isReadonly()
                    },
                    [Validators.required]
                ),
                size: '12|6|6',
                errorLabels: [ErrorLabelConstants.REQUIRED],
                notFoundText: 'CONFERENCE.AUTOCOMPLETE.INSERT_MIN_CHARACTERS',
                loading: false,
                valueChange: (province: ComboBox) => {
                    this.loggerService.log('valueChange', province);

                    if (province && province.key) {
                        this.groupFields
                            .get('meetFields')
                            .fields.get('city')
                            .control.reset();

                        this._initFieldFormAutocomplete(
                            this.groupFields
                                .get('meetFields')
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
                        .get('meetFields')
                        .fields.get('province')
                        .control.markAsTouched();

                    this.groupFields
                        .get('meetFields')
                        .fields.get('city').options = of([]);

                    this.groupFields
                        .get('meetFields')
                        .fields.get('city')
                        .control.reset();

                    this.groupFields
                        .get('meetFields')
                        .fields.get('city')
                        .control.markAsTouched();
                }
            })
            .set('city', {
                type: 'autocomplete',
                label: 'CONFERENCE.WIZARD.DEFINITION.INSTANCE.CITY',
                control: new FormControl(
                    {
                        value: this.model.instance.address
                            ? this.model.instance.address.city
                            : '',
                        disabled: this.isReadonly()
                    },
                    [Validators.required]
                ),
                errorLabels: [ErrorLabelConstants.REQUIRED],
                size: '12|6|6',
                notFoundText: 'CONFERENCE.AUTOCOMPLETE.INSERT_MIN_CHARACTERS',
                loading: false
            })
            .set('cap', {
                type: 'text',
                label: 'CONFERENCE.WIZARD.DEFINITION.INSTANCE.CAP',
                control: new FormControl(
                    {
                        value: this.model.instance.address
                            ? this.model.instance.address.cap
                            : '',
                        disabled: this.isReadonly()
                    },
                    [Validators.required]
                ),
                errorLabels: [ErrorLabelConstants.REQUIRED],
                size: '12|6|6'
            })
            .set('address', {
                type: 'text',
                label: 'CONFERENCE.WIZARD.DEFINITION.INSTANCE.PHYSICAL_ADDRESS',
                control: new FormControl(
                    {
                        value: this.model.instance.address
                            ? this.model.instance.address.address
                            : '',
                        disabled: this.isReadonly()
                    },
                    [Validators.required]
                ),
                errorLabels: [ErrorLabelConstants.REQUIRED],
                size: '12|6|6'
            });

        this._initFieldFormAutocomplete(
            this.groupFields.get('meetFields').fields.get('province'),
            this.utilityService.getProvinciesAutocomplete.bind(
                this.utilityService,
                null
            )
        );
    }

    private _applyGroupFieldsChange(): void {
        this.groupFields = new Map(this.groupFields);
    }

    private _initCallbacks(): void {
        this.updateSupportContact = this._updateSupportContact.bind(this);
        this.updateContactCompleteFn = this._updateContactCompleteFn.bind(this);
        this.updateContactErrorFn = this._updateContactErrorFn.bind(this);
        this.onNext = this._onNext.bind(this);
        this.saveComplete = this._saveComplete.bind(this);
        this.saveError = () => {}; // fake save error form perchè gestiamo gli errori diversmente
        this.extractDataToSubmit = this._extractDataToSubmit.bind(this);
        this.extractSupportContact = this._extractSupportContact.bind(this);
    }

    // TODO: da constrollare come funziona sugli accreditamenti
    private _onNext(data: Definition): Observable<WrapperPostPutData> {
        return this.conferenceStoreService.submitDefinition(data).pipe(
            catchError((response: HttpResponse<WrapperResponse>) => {
                return this._saveError(response);
            })
        );
    }

    private _saveComplete(response: WrapperPostPutData): void {
        return this.conferenceStoreService.saveCompleteDefinition(response);
    }

    private _saveError(error: HttpResponse<WrapperResponse>): Observable<null> {
        return this.conferenceStoreService.saveErrorDefinition(
            error,
            this.groupFields
        );
    }

    // trasform form value
    private _extractDataToSubmit(form: FormGroup): Definition {
        const val = {
            ...form.getRawValue()
        };

        val.instance.conferenceTime = form.value.meetFields.conferenceTime;
        val.instance.address = new MeetAddress(form.value.meetFields);

        return val;
    }

    private _defineSupportContactsTableStructure(): AbstractTableField[] {
        const structure: AbstractTableField[] = [];

        this.editContact = new ActionItem(
            'BUTTON.UPDATE',

            (a, item: SupportContact) => {
                this._createContactGroupFieldsModal(item);
                this.tmpContact = item;
                this.contactModal.open();
            },
            'pencil'
        );

        structure.push(
            new TableField(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.DEFINITION.SUPPORT_CONTACTS.FORM.NAME'
                ),
                'NORMAL',
                'name',
                false
            )
        );
        structure.push(
            new TableField(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.DEFINITION.SUPPORT_CONTACTS.FORM.SURNAME'
                ),
                'NORMAL',
                'surname',
                false
            )
        );
        structure.push(
            new TableField(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.DEFINITION.SUPPORT_CONTACTS.FORM.EMAIL'
                ),
                'NORMAL',
                'email',
                false
            )
        );
        structure.push(
            new TableField(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.DEFINITION.SUPPORT_CONTACTS.FORM.PHONE'
                ),
                'NORMAL',
                'phone',
                false
            )
        );

        if (this.action !== ActionForm.READONLY) {
            structure.push(
                new TemplateField(
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.PARTICIPANTS.MODAL.USER.ACTIONS'
                    ),
                    this.actionTemplate
                )
            );
        }

        return structure;
    }

    private _createContactGroupFieldsModal(contact: SupportContact) {
        this.contactGroupFields = new Map();
        this.contactGroupFields.set('supportContactsPanelModal', {
            panel: false,
            panelHead: null,
            fields: new Map()
                .set('name', {
                    type: 'text',
                    validators: [Validators.required],
                    control: new FormControl(contact.name),
                    label:
                        'CONFERENCE.WIZARD.DEFINITION.SUPPORT_CONTACTS.FORM.NAME',
                    errorLabels: [ErrorLabelConstants.REQUIRED],
                    size: '12|12|6'
                })
                .set('surname', {
                    type: 'text',
                    validators: [Validators.required],
                    control: new FormControl(contact.surname),
                    label:
                        'CONFERENCE.WIZARD.DEFINITION.SUPPORT_CONTACTS.FORM.SURNAME',
                    errorLabels: [ErrorLabelConstants.REQUIRED],
                    size: '12|12|6'
                })
                .set('email', {
                    type: 'text',
                    pattern: '^[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,4}$',
                    control: new FormControl(contact.email),
                    validators: [Validators.required],
                    label:
                        'CONFERENCE.WIZARD.DEFINITION.SUPPORT_CONTACTS.FORM.EMAIL',
                    errorLabels: [
                        ErrorLabelConstants.REQUIRED,
                        ErrorLabelConstants.REGEX('ERROR.EMAIL.INVALID')
                    ],
                    size: '12|12|6'
                })
                .set('phone', {
                    type: 'text',
                    pattern: '[0-9]{8,11}',
                    control: new FormControl(contact.phone),
                    validators: [Validators.required],
                    label:
                        'CONFERENCE.WIZARD.DEFINITION.SUPPORT_CONTACTS.FORM.PHONE',
                    errorLabels: [
                        ErrorLabelConstants.REQUIRED,
                        ErrorLabelConstants.REGEX('ERROR.REGEX')
                    ],
                    size: '12|12|6'
                })
        });
    }

    private _setSupportContactsGroupFields(): void {
        this.groupFields.set('supportContactsPanel', {
            panel: true,
            panelHead: 'CONFERENCE.WIZARD.DEFINITION.SUPPORT_CONTACTS.TITLE',
            oneToMany: true,
            accordion: true,
            model: SupportContact,
            saveFn: this._saveSupportContact.bind(this),
            listStructure: this._defineSupportContactsTableStructure(),
            listTitle:
                'CONFERENCE.WIZARD.DEFINITION.SUPPORT_CONTACTS.TABLE.TITLE',
            emptyTextList:
                'CONFERENCE.WIZARD.DEFINITION.SUPPORT_CONTACTS.TABLE.EMPTY_TEXT_LIST',
            listMany: this.model.supportContacts,
            readonly: this.isReadonly(),
            fields: new Map()
                .set('name', {
                    type: 'text',
                    validators: [Validators.required],
                    label:
                        'CONFERENCE.WIZARD.DEFINITION.SUPPORT_CONTACTS.FORM.NAME',
                    errorLabels: [ErrorLabelConstants.REQUIRED],
                    size: '12|12|6'
                })
                .set('surname', {
                    type: 'text',
                    validators: [Validators.required],
                    label:
                        'CONFERENCE.WIZARD.DEFINITION.SUPPORT_CONTACTS.FORM.SURNAME',
                    errorLabels: [ErrorLabelConstants.REQUIRED],
                    size: '12|12|6'
                })
                .set('email', {
                    type: 'text',
                    pattern: '^[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,4}$',
                    validators: [Validators.required],
                    label:
                        'CONFERENCE.WIZARD.DEFINITION.SUPPORT_CONTACTS.FORM.EMAIL',
                    errorLabels: [
                        ErrorLabelConstants.REQUIRED,
                        ErrorLabelConstants.REGEX('ERROR.EMAIL.INVALID')
                    ],
                    size: '12|12|6'
                })
                .set('phone', {
                    type: 'text',
                    pattern: '[0-9]{8,11}',
                    validators: [Validators.required],
                    label:
                        'CONFERENCE.WIZARD.DEFINITION.SUPPORT_CONTACTS.FORM.PHONE',
                    errorLabels: [
                        ErrorLabelConstants.REQUIRED,
                        ErrorLabelConstants.REGEX('ERROR.REGEX')
                    ],
                    size: '12|12|6'
                })
        });
    }

    private _extractSupportContact(form: FormGroup): SupportContact {
        return form.value.supportContactsPanelModal;
    }

    private _updateSupportContact(
        supportContact: SupportContact
    ): Observable<void | WrapperPostPutData> {
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
        this.groupFields.delete('supportContactsPanel');
        this.tmpContact = Object.assign(this.tmpContact || {}, supportContact);
        return this.supportContactService
            .editContact(this.tmpContact)
            .pipe(takeUntil(this.destroy$));
    }

    private _updateContactCompleteFn(response: WrapperPostPutData): void {
        this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
        this._setSupportContactsGroupFields();
        this._applyGroupFieldsChange();
        this.model.editSupportContact(this.tmpContact);

        this.toastr.info(
            this.i18nService.translate(
                'CONFERENCE.WIZARD.DEFINITION.SUPPORT_CONTACTS.TOASTR.SUCCESS_UPDATE.TEXT'
            ),
            this.i18nService.translate(
                'CONFERENCE.WIZARD.DEFINITION.SUPPORT_CONTACTS.TOASTR.SUCCESS_UPDATE.TITLE'
            )
        );
        this.tmpContact = null;
        this.contactModal.close();
    }

    private _updateContactErrorFn(error: ErrorMessage): void {
        this.toastr.error(
            this.i18nService.translate(
                'CONFERENCE.WIZARD.DEFINITION.SUPPORT_CONTACTS.TOASTR.ERROR_UPDATE.TITLE'
            ),
            this.i18nService.translate(
                'CONFERENCE.WIZARD.DEFINITION.SUPPORT_CONTACTS.TOASTR.ERROR_UPDATE.TITLE'
            )
        );

        this.contactModal.close();
    }

    private _deleteTimeField(): void {
        const fields = this.groupFields.get('meetFields').fields;

        fields.delete('conferenceTime');
        this.model.instance.conferenceTime = '';
    }

    private _setTimeField(): void {
        const meetFields = this.groupFields.get('meetFields').fields;

        this.groupFields.get('meetFields').fields = new Map([
            ...new Map().set('conferenceTime', {
                type: 'text',
                label: 'CONFERENCE.WIZARD.DEFINITION.INSTANCE.CONFERENCE_TIME',
                pattern: '^([01]?[0-9]|2[0-3]):[0-5][0-9]$',
                control: new FormControl(
                    {
                        value: this.model.instance.conferenceTime,
                        disabled: this.isReadonly()
                    },
                    [Validators.required]
                ),
                placeholder: 'HH:mm',
                errorLabels: [ErrorLabelConstants.REGEX('ERROR.REGEX')],
                size: '12|6|6'
            }),
            ...meetFields
        ]);
    }

    private _controlDates(): void {
        if (environment.customConfigurationConf.definitionDatesControl) {
            this._setDatesByRule();
        }

        this._controlMandatoryDates();
    }

    private _controlTime(): void {
        const templateTime = this.template.instance.conferenceTime;
        if (templateTime.visible) {
            this._setTimeField();
        } else {
            this._deleteTimeField();
        }
    }

    private _controlAddressType(): void {
        const templateAddress = this.template.instance.address;

        this.groupFields
            .get('meetFields')
            .fields.get('type')
            .control.setValue(this.template.instance.getAddressTypeComboBox());

        if (templateAddress.disabled) {
            this.groupFields
                .get('meetFields')
                .fields.get('type')
                .control.disable();
        }
    }

    private _applyTemplate() {
        this._controlDates();
        this._controlAddressType();
        this._controlTime();
    }

    private _setDatesByRule(): void {
        this.template.instance.setDates(this._setDateByRule.bind(this));
    }

    private _setDateByRule(
        fieldDateName: FieldDateName,
        fieldDateRule: DateRule
    ): void {
        const toDate = this._calcDate(
            this.model.instance[fieldDateRule.baseDate] as DateModel,
            fieldDateRule.offsetBusinessDay,
            fieldDateRule.offsetDay
        );
        if (
            typeof (this.model.instance[fieldDateName] as DateModel).date ===
            'undefined'
        ) {
            this.model.instance[fieldDateName] =
                toDate || this.model.instance[fieldDateName];
            this._setDate(fieldDateName, toDate);
        }
    }

    private _calcDate(
        fromDate: DateModel,
        offsetBusinessDay: string,
        offsetDay: string
    ): DateModel {
        if (this._isDateDefined(fromDate)) {
            const _fromDate = business(fromDate.date).businessAdd(
                parseInt(offsetBusinessDay, 10)
            );
            return new DateModel(_fromDate.add(offsetDay, 'days'));
        }
        return null;
    }

    private _isDateDefined(date: string | DateModel): boolean {
        return typeof (date as DateModel).date !== 'undefined';
    }

    private _setEmptyDates(): void {
        this._setEmptyDate(FieldDateName.END_INTEGRATION_DATE);
        this._setEmptyDate(FieldDateName.END_OPINION_DATE);
        this._setEmptyDate(FieldDateName.FIRST_SESSION_DATE);
        this._setEmptyDate(FieldDateName.EXPIRATION_DATE);
    }

    private _setEmptyDate(dateField: FieldDateName): void {
        this._setDate(dateField);
    }

    private _setDate(
        dateField: FieldDateName,
        value: string | DateModel = ''
    ): void {
        this.groupFields
            .get('instance')
            .fields.get(dateField)
            .control.setValue(value);
    }

    private _controlMandatoryDate(fieldDateName: FieldDateName): void {
        if (this.template.instance.mustBeMandatoryDate(fieldDateName)) {
            this._setMandatoryDate(fieldDateName, true);
        } else {
            // TODO verificare se necessario
            // dipende da come viene creato il form
            // this._cancelDateError(fieldDateName);

            this._setMandatoryDate(fieldDateName, false);
        }
    }

    private _cancelDateError(fieldDateName: FieldDateName): void {
        this.groupFields
            .get('instance')
            .fields.get(fieldDateName)
            .control.markAsTouched();
        this.groupFields
            .get('instance')
            .fields.get(fieldDateName)
            .control.setErrors(null);
    }

    private _setMandatoryDate(
        fieldDateName: FieldDateName,
        mandatory: boolean
    ): void {
        const validators = [];
        if (mandatory) {
            validators.push(Validators.required);
        }
        this.groupFields
            .get('instance')
            .fields.get(fieldDateName)
            .control.setValidators(validators);
    }

    private _controlMandatoryDates() {
        this._controlMandatoryDate(FieldDateName.END_OPINION_DATE);
        this._controlMandatoryDate(FieldDateName.FIRST_SESSION_DATE);
        this._controlMandatoryDate(FieldDateName.END_INTEGRATION_DATE);
        this._controlMandatoryDate(FieldDateName.EXPIRATION_DATE);
    }
}
