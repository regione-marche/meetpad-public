import { Component, ViewChild, Output, EventEmitter } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';

import { Observable, of } from 'rxjs';
import { mergeMap, catchError, takeUntil, tap, map } from 'rxjs/operators';

import { LoggerService } from '@eng-ds/ng-core';

import {
    ModalComponent,
    ErrorLabelConstants,
    ErrorMessage
} from '@eng-ds/ng-toolkit';

import {
    ComboBox,
    ApplicationRole,
    User,
    LoaderService,
    SectionLoading,
    AutoUnsubscribe,
    FormFieldGroup,
    FormButton,
    FormField,
    FooterButtons
} from '@common';

import { ConferenceType, UserPortalService } from '@app/core';

import { UtilityService } from '@app/core/services/utilities/utility.service';
import { environment } from '@env/environment';

import {
    ConferencePreliminary,
    ConferenceStoreService,
    ConferenceTemplate,
    ConferenceTemplateToApply
} from '../../../core';

@Component({
    selector: 'app-conference-modal-before-create',
    templateUrl: './conference-modal-before-create.component.html',
    styleUrls: ['./conference-modal-before-create.component.scss']
})
export class ConferenceModalBeforeCreateComponent extends AutoUnsubscribe {
    @ViewChild('modal') modal: ModalComponent;

    @Output('cancel') cancel: EventEmitter<void> = new EventEmitter();
    @Output('close') close: EventEmitter<void> = new EventEmitter<void>();

    @ViewChild('conferenceType') conferenceTypeSelect: any;

    save: (data: any) => Observable<any>;
    saveComplete: () => void;

    groupFields: Map<string, FormFieldGroup> = new Map();
    groupButtons: Map<FooterButtons, FormButton> = new Map();
    conferenceManagers: Observable<ComboBox[] & any> = this.utilityService.getConferenceManagers();
   // conferenceManagersDomus: Observable<ComboBox[] & any> = this.utilityService.getConferenceManagersDomus();
    conferenceAdministations: Observable<
        ComboBox[] & any
    > = this.utilityService.getConferenceAdministrations();

    model: ConferencePreliminary;
    typeRadioOptions: Observable<
        ComboBox[]
    > = this.utilityService.getConferenceTypes();

    delegationFlag: boolean = false;

    selectLoading$ = this.loaderService.getLoading$(
        SectionLoading.CONFERENCE_MODAL_BEFORE_CREATE
    );

    private _userProfile: ApplicationRole;
    private _blockCancel = false;
    private _isFirstLoad = true;

    constructor(
        private utilityService: UtilityService,
        private loggerService: LoggerService,
        private userService: UserPortalService,
        private loaderService: LoaderService,
        private conferenceStoreService: ConferenceStoreService
    ) {
        super();
        this._initCallback();
    }

    createForm(): void {
        this.model = new ConferencePreliminary();
        this.groupFields = new Map<string, FormFieldGroup>().set('panel', {
            panel: false,
            panelHead: null,
            fields: new Map<string, FormField>()
                .set('administration', {
                    type: 'select',
                    label:
                        'CONFERENCE.WIZARD.MODAL_BEFORE_CREATE.ADMINISTRATION_LABEL',
                    control: new FormControl(
                        {
                            value: this.model.administration,
                            disabled: false
                        },
                        [Validators.required]
                    ),
                    valueChange: (administration: ComboBox) => {
                        this.loggerService.log('valueChange', administration);
                        const managerField = this.groupFields
                            .get('panel')
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
                    size: '12|10|6',
                    offset: '0|1|3',
                    options: this.conferenceAdministations,
                    errorLabels: [ErrorLabelConstants.REQUIRED]
                })
                .set('manager', {
                    type: 'select',
                    label:
                        'CONFERENCE.WIZARD.MODAL_BEFORE_CREATE.MANAGER_LABEL',
                    control: new FormControl(
                        {
                            value: '',
                            disabled: false
                        },
                        [Validators.required]
                    ),
                    size: '12|10|6',
                    offset: '0|1|3',
                    options: this.utilityService.getConferenceManagersWithAdmin(
                        this.model.manager.key
                    ),
                    errorLabels: [ErrorLabelConstants.REQUIRED]
                })
        });

        if (this._userProfile === ApplicationRole.CONFERENCE_CREATOR) {
            if (this.delegationFlag) {
                this.groupFields
                    .get('panel')
                    .fields.get('manager')
                    .control.setValue(this.model.manager);
            } else {
                this.groupFields.get('panel').fields.delete('administration');
                this.groupFields
                    .get('panel')
                    .fields.get('manager')
                    .control.setValue(
                        environment.defaultComboBox.conference.manager
                    );
            }
        } else {
            this.groupFields.get('panel').fields.delete('administration');
            this.groupFields.get('panel').fields.delete('manager');
        }

        this.groupFields.get('panel').fields.set('conferenceType', {
            type: 'select',
            label: 'CONFERENCE.WIZARD.MODAL_BEFORE_CREATE.TYPE_LABEL',
            control: new FormControl(this.model.conferenceType, [
                Validators.required
            ]),
            valueChange: (conferenceType: ComboBox) => {
                this.loggerService.log('valueChange', conferenceType);
                this._getTemplate(conferenceType.key);
            },
            errorLabels: [ErrorLabelConstants.REQUIRED],
            size: '12|10|6',
            offset: '0|1|3',
            options: this.typeRadioOptions.pipe(
                tap((c: ComboBox[]) => {
                    if(this._isFirstLoad && this.model.conferenceType.key !== c[0].key) {
                        this._isFirstLoad = false;
                        this._getTemplate(c[0].key);
                    }
                }))
        });
    }

    private _deleteCompany(): void {
        const fields = this.groupFields.get('panel').fields;
        fields.delete('company');
        this.groupFields = new Map(this.groupFields);
        this.stopPageLoading();
    }

    private _getTemplate(conferenceType: string): void {
        this.startPageLoading();
        this.utilityService
            .getConferenceTemplate(conferenceType as ConferenceType)
            .pipe(
                takeUntil(this.destroy$),
                catchError((err: ErrorMessage) => {
                    this.saveError(err);
                    return of();
                })
            )
            .subscribe((result: ConferenceTemplate) => {
                const conferenceTemplate = new ConferenceTemplate(result);
                if (
                    conferenceTemplate &&
                    conferenceTemplate.procedure.hasCompanies()
                ) {
                    this._setCompany(conferenceTemplate);
                } else {
                    this._deleteCompany();
                }
            });
    }

    private _setCompany(conferenceTemplate: ConferenceTemplate): void {
        const selectOptions = conferenceTemplate.procedure.getCompanies();

        this.groupFields.get('panel').fields.set('company', {
            type: 'select',
            label: 'CONFERENCE.WIZARD.MODAL_BEFORE_CREATE.COMPANY',
            control: new FormControl(
                {
                    value: selectOptions[0],
                    disabled: false
                },
                [Validators.required]
            ),
            size: '12|10|6',
            offset: '0|1|3',
            valueChange: (company: ComboBox) => {
                conferenceTemplate.procedure.selectCompanyAndApplicant(
                    company.key
                );
            },
            options: of(selectOptions),
            errorLabels: [ErrorLabelConstants.REQUIRED]
        });

        this.groupFields = new Map(this.groupFields);
        this.stopPageLoading();
    }

    onCancel(): void {
        if (!this._blockCancel) {
            this.cancel.emit();
        }
    }

    openModal(): void {
        this._checkUserInfo().subscribe((user: User) => {
            this.delegationFlag = user.creationOtherAuthorities;
            this._userProfile = user.profile.key as ApplicationRole;
            this.createForm();
            setTimeout(() => {
                this.save = this._save.bind(this);
            }, 0);
            this._getTemplate(this.model.conferenceType.key);
            this.modal.open();
        });
    }

    private _initCallback(): void {
        this.save = this._save.bind(this);
        this.saveComplete = this._saveComplete.bind(this);
    }

    private _checkUserInfo(): Observable<User> {
        return this.userService
            .getInfo()
            .pipe(takeUntil(this.destroy$)) as Observable<User>;
    }

    private _save(data: {
        panel: ConferencePreliminary;
    }): Observable<any> | any {
        this.loggerService.log('_save', data);
        if (this._userProfile === ApplicationRole.CONFERENCE_CREATOR) {
            return this._saveManager(data).pipe(
                mergeMap(_ => this._setTemplate(data))
            );
        }

        return this._setTemplate(data);
    }

    private _setTemplate(data: {
        panel: ConferencePreliminary;
    }): Observable<ConferenceTemplateToApply> {
        return this.conferenceStoreService.setTemplate(
            data.panel.conferenceType.key as ConferenceType,
            data.panel.company && data.panel.company.key
        );
    }

    private _saveManager(data: any): Observable<ComboBox> {
        this.loggerService.log('_saveManager', data);
        if (this.delegationFlag) {
            return this.utilityService
                .getConferenceManagersWithAdminByKey(
                    data.panel.administration.key,
                    data.panel.manager.key
                )
                .pipe(
                    takeUntil(this.destroy$),
                    mergeMap(_manager =>
                        this.conferenceStoreService.editManager(
                            _manager,
                            data.panel.administration.key
                        )
                    ),
                    catchError((err: ErrorMessage) => {
                        this.saveError(err);
                        return of();
                    })
                );
        } else {
            return this.utilityService
                .getConferenceManagerByKey(data.panel.manager.key)
                .pipe(
                    takeUntil(this.destroy$),
                    mergeMap(_manager =>
                        this.conferenceStoreService.editManager(_manager, null)
                    ),
                    catchError((err: ErrorMessage) => {
                        this.saveError(err);
                        return of();
                    })
                );
        }
    }

    private _saveComplete(): void {
        this._blockCancel = true;
        this.modal.close();
        this.close.emit();
    }

    public startPageLoading() {
        this.loaderService.showLoading(
            SectionLoading.CONFERENCE_MODAL_BEFORE_CREATE
        );
    }

    public stopPageLoading() {
        this.loaderService.hideLoading(
            SectionLoading.CONFERENCE_MODAL_BEFORE_CREATE
        );
    }

    saveError(error: ErrorMessage): void {
        // TODO gestire gli errori
    }

    get loading(): Observable<boolean> {
        return this.loaderService.getLoading$(
            SectionLoading.CONFERENCE_MODAL_BEFORE_CREATE
        );
    }
}
