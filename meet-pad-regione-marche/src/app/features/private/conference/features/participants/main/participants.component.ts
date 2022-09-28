import {
    Component,
    ViewChild,
    OnInit,
    Output,
    EventEmitter,
    Input
} from '@angular/core';
import { FormControl, Validators, FormGroup } from '@angular/forms';

import { Observable, of, Subscriber } from 'rxjs';
import { takeUntil, map, catchError, tap } from 'rxjs/operators';

import { ToastrService } from 'ngx-toastr';

import {
    ModalComponent,
    ErrorLabelConstants,
    AbstractTableField,
    ActionItem,
    TableField,
    TemplateField,
    ErrorMessage,
    TooltipModel
} from '@eng-ds/ng-toolkit';

import { LoggerService, I18nService } from '@eng-ds/ng-core';

import {
    ComboBox,
    WrapperPostPutData,
    WrapperDeleteData,
    LoaderService,
    SectionLoading,
    AutoUnsubscribe,
    FormFieldGroup,
    FormButton,
    FormField,
    FooterButtons
} from '@common';

import { environment } from '@env/environment';

import {
    ActionForm,
    UtilityService,
    StepName,
    Mixin,
    ConferenceType,
    ParticipantRole
} from '@app/core';

import { customConfigurationConf } from '@config';
import { FormStep } from '../../../core/mixins';
import {
    Participant,
    ParticipantsService,
    ConferenceStoreService,
    ConferencePermissionsService,
    Conference,
    User
} from '../../../core';

@Component({
    selector: 'app-conference-participants',
    templateUrl: './participants.component.html',
    styleUrls: ['./participants.component.scss']
})
@Mixin([FormStep])
export class ParticipantsComponent extends AutoUnsubscribe implements OnInit {
    @ViewChild('docModal') modal: ModalComponent;
    @ViewChild('personRolesTemplate') personRolesTemplate;
    @ViewChild('confirmModal') confirmModal: ModalComponent;
    @ViewChild('actionTemplate') actionTemplate;
    @ViewChild('deleteListParticipantsConfirmModal') deleteListParticipantsConfirmModal: ModalComponent;

    @Input('summary') summary: boolean = false;
    @Output('openConfirmModal') openConfirmModal = new EventEmitter<
        FooterButtons
    >();

    isOpen: boolean = false;

    popoverTitle: string = this.i18nService.translate(
        'CONFERENCE.WIZARD.PARTICIPANTS.POPOVER.TITLE'
    );
    popoverText: string = this.i18nService.translate(
        'CONFERENCE.WIZARD.PARTICIPANTS.POPOVER.TEXT'
    );
    cancelButton: string = this.i18nService.translate(
        'CONFERENCE.WIZARD.PARTICIPANTS.POPOVER.CANCEL_BTN'
    );
    okButton: string = this.i18nService.translate(
        'CONFERENCE.WIZARD.PARTICIPANTS.POPOVER.OK_BTN'
    );
    modalText: string = this.i18nService.translate(
        'CONFERENCE.WIZARD.PARTICIPANTS.CONFIRM_DELETE_MODAL.MESSAGE'
    );
    cancelButtonTable: string = this.i18nService.translate(
        'CONFERENCE.WIZARD.PARTICIPANTS.TABLE.ACTIONS_BUTTONS.DELETE'
    );

    idToDelete: string = '';
    participantToDelete: Participant;
    participantsListToDelete: Participant[] = [];

    modalButtons: ActionItem[] = [
        new ActionItem(
            'SEARCH.CONFIRM_DELETE_MODAL.CANCEL_BUTTON',
            (action: ActionItem): void => {
                this.confirmModal.close();
            },
            'close'
        ),
        new ActionItem(
            'SEARCH.CONFIRM_DELETE_MODAL.OK_BUTTON',
            (action: ActionItem): void => {
                this.deleteParticipant();
            },
            'trash'
        )
    ];

    groupFields: Map<string, FormFieldGroup> = new Map();
    footerButtons: boolean = true;
    footerTextSubmitBtn: string = 'BUTTON.ADD';
    // modalità del partecipante
    actionParticipant: ActionForm;
    tmpUsers: User[] = [];
    tmpParticipant: Participant;

    groupButtons: Map<FooterButtons, FormButton> = new Map();

    saveFn: (participant: Participant) => Observable<WrapperPostPutData>;
    saveCompleteFn: (response: WrapperPostPutData) => void;
    saveErrorFn: (error: ErrorMessage) => void;
    extractDataToSubmit: (form: FormGroup) => Participant;

    constructor(
        private loggerService: LoggerService,
        private i18nService: I18nService,
        private participantsService: ParticipantsService,
        private utilityService: UtilityService,
        private toastr: ToastrService,
        private conferenceStoreService: ConferenceStoreService,
        private loaderService: LoaderService,
        private conferencePermissionsService: ConferencePermissionsService
    ) {
        super();
    }

    ngOnInit(): void {
        this._initPermission();
        this._initCallbacks();
        if (!this.summary) {
            this.conferenceStoreService.hidePageLoader();
        }
    }

    // TODO REMOVE
    get loadingFooterButtons(): Observable<boolean> {
        return this.loaderService.getLoading$(
            SectionLoading.CONFERENCE_FOOTER_BUTTON
        );
    }

    get conference(): Conference {
        return this.conferenceStoreService.conference;
    }

    get action(): ActionForm {
        return this.conferenceStoreService.getStepActionForm(
            StepName.PARTICIPANT
        );
    }

    get loading$(): Observable<boolean> {
        return this.loaderService.getLoading$(SectionLoading.PARTICIPANT_MODAL);
    }

    get template() {
        return this.conferenceStoreService.template.participant;
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

    private _onNext(): void {
        return this.onNext();
    }

    deleteUser(user: User): void {
        // se si sta creando un nuovo partecipante
        // vuol dire che tutti i dati sono ancora
        // in locale e non ha senso contattare il BE
        if (this.actionParticipant !== ActionForm.CREATE) {
            this.participantsService
                .deleteUser(user.id)
                .subscribe((res: WrapperDeleteData) => {
                    this.conference
                        .getParticipant(this.tmpParticipant.id)
                        .deleteUser(user.id);

                    this.toastr.info(
                        this.i18nService.translate(
                            'CONFERENCE.WIZARD.PARTICIPANTS.TOASTR.SUCCESS_USER_DELETE.TEXT'
                        ),
                        this.i18nService.translate(
                            'CONFERENCE.WIZARD.PARTICIPANTS.TOASTR.SUCCESS_USER_DELETE.TITLE'
                        )
                    );
                });
        }
    }

    saveUser(user: User): Observable<void | WrapperPostPutData> {
        let obs: Observable<void | WrapperPostPutData> = null;
        if (this.actionParticipant === ActionForm.CREATE) {
            this.tmpUsers.push(new User(user));
            obs = new Observable(
                (observer: Subscriber<void | WrapperPostPutData>): void => {
                    observer.next();
                    observer.complete();
                }
            );
        } else {
            this.loaderService.showLoading(SectionLoading.PARTICIPANT_MODAL);
            obs = this.participantsService
                .createUser(this.conference.id, this.tmpParticipant.id, user)
                .pipe(
                    catchError((res) => {
                        if(res.status && res.status === 200){
                            return of();
                        }else if(res.status && res.status != 200 && res.body && res.body.errors != undefined && res.body.errors.length > 0){

                            res.body.errors.forEach(error => {
                                this.toastr.error(
                                    error.msg,
                                    this.i18nService.translate(
                                        'CONFERENCE.WIZARD.PARTICIPANTS.TOASTR.ERROR_USER_SAVE.TITLE'
                                    )
                                );
                            });         
                            this.loaderService.hideLoading(
                                SectionLoading.PARTICIPANT_MODAL
                            )             
                            return of(null);
                        }else{

                            this.toastr.error(
                                this.i18nService.translate(
                                    'CONFERENCE.WIZARD.PARTICIPANTS.TOASTR.ERROR_USER_SAVE.TEXT'
                                ),
                                this.i18nService.translate(
                                    'CONFERENCE.WIZARD.PARTICIPANTS.TOASTR.ERROR_USER_SAVE.TITLE'
                                )
                            );
                            this.loaderService.hideLoading(
                                SectionLoading.PARTICIPANT_MODAL
                            )
                            return of(null);
                        }               
                        
                    }),
                    map((res: WrapperPostPutData) => {
                        if(res == null)
                            return;
                        user.id = res.id;
                        this.tmpUsers.push(new User(user));
                        // this.tmpParticipant.users = this.tmpUsers;
                        this.toastr.info(
                            this.i18nService.translate(
                                'CONFERENCE.WIZARD.PARTICIPANTS.TOASTR.SUCCESS_USER_SAVE.TEXT'
                            ),
                            this.i18nService.translate(
                                'CONFERENCE.WIZARD.PARTICIPANTS.TOASTR.SUCCESS_USER_SAVE.TITLE'
                            )
                        );
                    }),
                    tap(_ =>
                        this.loaderService.hideLoading(
                            SectionLoading.PARTICIPANT_MODAL
                        )
                    )
                );
        }
        return obs;
    }

    // apertura modale in modalità scrittura
    addParticipant(): void {
        this.loaderService.showLoading(SectionLoading.PARTICIPANT_MODAL);
        this.loggerService.log('addParticipant');
        this.actionParticipant = ActionForm.CREATE;
        this.footerButtons = true;
        this.footerTextSubmitBtn = 'BUTTON.ADD';
        this._createGroupsFields();
        this._initAuthorityAutomplete();
        this.openModal();
    }

    // apertura modale in modalità lettura
    viewParticipant(participant: Participant): void {
        this.loggerService.log('viewParticipant', participant);
        this.actionParticipant = ActionForm.READONLY;
        // this.footerButtons = !participant.readonly || this.isReadonly();
        this.footerButtons = false;
        this._prepareAndOpenModal(participant);
    }

    // apertura modale in modalità edit
    editParticipant(participant: Participant): void {
        this.loggerService.log('editParticipant', participant);
        this.actionParticipant = ActionForm.EDIT;
        this.footerButtons = !participant.readonly;
        this.footerTextSubmitBtn = 'BUTTON.UPDATE';
        this.tmpParticipant = participant;
        this._prepareAndOpenModal(participant);
    }

    onNext(): void {
        return this.conferenceStoreService.submitParticipant();
    }

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

    getFooterTextButton(): string {
        return this.action === ActionForm.CREATE
            ? 'BUTTON.SAVE_NEXT'
            : 'BUTTON.SAVE';
    }

    openModalForDelete(participant: Participant): void {
        this.idToDelete = participant.id;
        this.participantToDelete = participant;
        this.confirmModal.open();
    }

    deleteParticipant(): void {
        this.loggerService.log('deleteParticipant', this.participantToDelete);
        this.participantsService
            .deleteParticipant(this.idToDelete)
            .pipe(
                takeUntil(this.destroy$),
                catchError(() => {
                    this.confirmModal.close();
                    return of();
                })
            )
            .subscribe((res: WrapperDeleteData) => {
                this.conference.deleteParticipant(this.idToDelete);
                this.confirmModal.close();

                this.toastr.info(
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.PARTICIPANTS.TOASTR.SUCCESS_DELETE.TEXT'
                    ),
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.PARTICIPANTS.TOASTR.SUCCESS_DELETE.TITLE'
                    )
                );
            });
    }

    openModal(): void {
        this.modal.open();
    }

    resetContext(): void {
        this.tmpParticipant = null;
        this.tmpUsers = [];
    }

    resetForm(): void {
        this.groupFields
            .get('actor')
            .fields.get('description')
            .control.reset();

        this.groupFields
            .get('actor')
            .fields.get('pec')
            .control.reset();

        this.groupFields
            .get('actor')
            .fields.get('fiscalCode')
            .control.reset();
    }

    close(): void {
        this.resetContext();
        this.modal.close();
    }

    defineUserTableStructure(readonly: boolean = false): AbstractTableField[] {
        const userTableStructure: AbstractTableField[] = [];

        userTableStructure.push(
            new TableField(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.PARTICIPANTS.MODAL.USER.NAME'
                ),
                'NORMAL',
                'name',
                true
            )
        );
        userTableStructure.push(
            new TableField(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.PARTICIPANTS.MODAL.USER.SURNAME'
                ),
                'NORMAL',
                'surname',
                true
            )
        );

        userTableStructure.push(
            new TemplateField(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.PARTICIPANTS.MODAL.USER.PROFILE'
                ),
                this.personRolesTemplate
            )
        );

        userTableStructure.push(
            new TableField(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.PARTICIPANTS.MODAL.USER.EMAIL'
                ),
                'NORMAL',
                'email',
                true
            )
        );

        userTableStructure.push(
            new TableField(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.PARTICIPANTS.MODAL.USER.CF'
                ),
                'NORMAL',
                'fiscalCode',
                true
            )
        );

        // non possiamo aggiungere il pulsante cancella finchè
        // il partecipante non è creato
        // non avremmo un id utente per eseguire la chiamata utente verso il server
        // visto che finche non viene creato il partecipante
        // tutti gli utenti sono solo sul modello locale
        if (!readonly && this.actionParticipant !== ActionForm.CREATE) {
            userTableStructure.push(
                new TemplateField(
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.PARTICIPANTS.MODAL.USER.ACTIONS'
                    ),
                    this.actionTemplate
                )
            );
        }

        return userTableStructure;
    }

    showFooterBtn(): boolean {
        return this.action === ActionForm.CREATE;
    }

    isReadonly(): boolean {
        return this.action === ActionForm.READONLY;
    }

    confirmClicked(model: User): void {
        this.deleteUser(model);
    }

    private _initPermission(): void {
        this.conferencePermissionsService.apply(
            this,
            this.appSections.CONFERENCE_STEP,
            this.conferenceStoreService
        );
    }

    private _resolveUsers(participantId: string): Observable<User[]> {
        return this.participantsService.getUsers(
            this.conference.id,
            participantId,
            true
        );
    }

    private _prepareAndOpenModal(participant: Participant): void {
        this.loaderService.showLoading(SectionLoading.PARTICIPANT_MODAL);
        this.openModal();
        this._resolveUsers(participant.id)
            .pipe(takeUntil(this.destroy$))
            .subscribe((users: User[]) => {
                participant.setUsers(users);
                this._createGroupsFields(participant);
                this._initAuthorityAutomplete(participant);
            });
    }
    private _checkDetermination(role: ComboBox<ParticipantRole>): void {
        const field = this.groupFields.get('actor').fields.get('determination');

        if (field) {
            if (role.key === ParticipantRole.COMPETENT_ADMINISTRATION) {
                field.control.setValue(true);
                field.control.disable();
            } else {
                field.control.enable();
            }
        }
    }

    private _initCallbacks(): void {
        this.saveFn = this._saveParticipant.bind(this);
        this.saveCompleteFn = this._saveParticipantCompleteFn.bind(this);
        this.saveErrorFn = this._saveParticipantErroFn.bind(this);
        this.extractDataToSubmit = this._extractDataToSubmit.bind(this);
    }

    private _initAuthorityAutomplete(
        participant: Participant = new Participant()
    ): void {
        const authorityField: FormField = this.groupFields
            .get('actor')
            .fields.get('authority');
        this._initFieldFormAutocomplete(
            authorityField,
            this.utilityService.getCompanyAutocomplete.bind(this.utilityService)
        );
        authorityField.options.pipe(
            takeUntil(this.destroy$),
            map((combo: ComboBox[]) => {
                // controlla la presenza di partecipanti
                // elimina dalla tendina quelli già presenti
                const parts: string[] = this.conference.getParticipantsAuthority();
                const filteredCombo: ComboBox[] = [];

                combo.forEach((c: ComboBox) => {
                    if (
                        parts.indexOf(c.key) < 0 ||
                        participant.authority.key === c.key
                    ) {
                        filteredCombo.push(c);
                    }
                });

                return filteredCombo;
            })
        );
    }

    private _createGroupsFields(
        participant: Participant = new Participant()
    ): void {
        this._initContext(participant);
        // work arround for angular change detection
        this.groupFields = new Map();
        this.groupFields
            .set('actor', {
                panel: true,
                panelHead: 'CONFERENCE.WIZARD.PARTICIPANTS.MODAL.ACTOR.TITLE',
                accordion: true,
                fields: new Map()
                    .set('authority', {
                        placeholder:
                            'CONFERENCE.WIZARD.PARTICIPANTS.MODAL.ACTOR.AUTHORITY_PLACEHOLDER',
                        type: 'autocomplete',
                        label:
                            'CONFERENCE.WIZARD.PARTICIPANTS.MODAL.ACTOR.AUTHORITY',
                        control: new FormControl(
                            {
                                value: participant.authority,
                                disabled:
                                    participant.readonly || this.isReadonly()
                            },
                            [Validators.required]
                        ),
                        disabled: participant.readonly || this.isReadonly(),
                        required: true,
                        size: '12|12|12',
                        valueChange: (authority: ComboBox): void => {
                            if (authority) {
                                this.utilityService
                                    .getCompanyById(authority.key)
                                    .pipe(takeUntil(this.destroy$))
                                    .subscribe(
                                        (_authority: {
                                            description;
                                            pec;
                                            fiscalCode;
                                        }) => {
                                            this.groupFields
                                                .get('actor')
                                                .fields.get('description')
                                                .control.setValue(
                                                    _authority.description
                                                );

                                            this.groupFields
                                                .get('actor')
                                                .fields.get('pec')
                                                .control.setValue(
                                                    _authority.pec
                                                );

                                            this.groupFields
                                                .get('actor')
                                                .fields.get('fiscalCode')
                                                .control.setValue(
                                                    _authority.fiscalCode
                                                );
                                        }
                                    );
                            }
                        },
                        onClear: (): void => {
                            this.groupFields
                                .get('actor')
                                .fields.get('description')
                                .control.reset();

                            this.groupFields
                                .get('actor')
                                .fields.get('pec')
                                .control.reset();

                            this.groupFields
                                .get('actor')
                                .fields.get('fiscalCode')
                                .control.reset();
                        },
                        errorLabels: [ErrorLabelConstants.REQUIRED],
                        notFoundText:
                            'CONFERENCE.AUTOCOMPLETE.INSERT_MIN_CHARACTERS',
                        loading: false
                    })
                    .set('description', {
                        type: 'text-area',
                        label: customConfigurationConf.participantLabel
                            ? 'CONFERENCE.WIZARD.PARTICIPANTS.MODAL.ACTOR.AUTHORITY_DESCRIPTION'
                            : 'CONFERENCE.WIZARD.PARTICIPANTS.MODAL.ACTOR.AUTHORITY_DESCRIPTION_SUAP',
                        control: new FormControl(
                            {
                                value: participant.description,
                                disabled:
                                   // participant.readonly || this.isReadonly()
                                   true
                            },
                            [Validators.required]
                        ),
                        rows: 4,
                        maxLength: 1000,
                        errorLabels: [ErrorLabelConstants.REQUIRED],
                        size: '12|12|12'
                    })
                    .set('role', {
                        type: 'select',
                        label:
                            'CONFERENCE.WIZARD.PARTICIPANTS.MODAL.ACTOR.ROLE',
                        control: new FormControl(
                            {
                                value: participant.role,
                                disabled:
                                    participant.readonly || this.isReadonly()
                            },
                            [Validators.required]
                        ),
                        valueChange: (
                            role: ComboBox<ParticipantRole>
                        ): void => {
                            this.loggerService.log('valueChange', role);

                            if (role && role.key) {
                                this.groupFields
                                    .get('user')
                                    .fields.get(
                                        'profile'
                                    ).options = this.utilityService.getPersonRoles(
                                    role.key
                                );

                                this._checkDetermination(role);
                            }
                        },
                        size: '12|6|6',
                        options: this.utilityService.getParticipantRoles(
                            participant.role
                        ),
                        errorLabels: [ErrorLabelConstants.REQUIRED]
                    })
                    .set('pec', {
                        type: 'text',
                        pattern: '^[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,4}$',
                        label: customConfigurationConf.participantLabel
                            ? 'CONFERENCE.WIZARD.PARTICIPANTS.MODAL.ACTOR.PEC'
                            : 'CONFERENCE.WIZARD.PARTICIPANTS.MODAL.ACTOR.PEC_SUAP',
                        control: new FormControl(
                            {
                                value: participant.pec,
                                disabled:
                                   // participant.readonly || this.isReadonly()
                                   true
                            },
                            [Validators.required]
                        ),
                        errorLabels: [
                            ErrorLabelConstants.REQUIRED,
                            ErrorLabelConstants.REGEX('ERROR.EMAIL.INVALID')
                        ],
                        size: '12|6|6'
                    })
                    .set('fiscalCode', {
                        type: 'text',
                        label: customConfigurationConf.participantLabel
                            ? 'CONFERENCE.WIZARD.PARTICIPANTS.MODAL.ACTOR.FISCALCODE'
                            : 'CONFERENCE.WIZARD.PARTICIPANTS.MODAL.ACTOR.FISCALCODE_SUAP',
                        pattern: '^[0-9]{11}$',
                        control: new FormControl(
                            {
                                value: participant.fiscalCode,
                                disabled:
                                    //participant.readonly || this.isReadonly()
                                    true
                            },
                            [Validators.required]
                        ),
                        errorLabels: [
                            ErrorLabelConstants.REQUIRED,
                            ErrorLabelConstants.REGEX('ERROR.REGEX')
                        ],
                        size: '12|6|6'
                    })
                    .set('competence', {
                        type: 'text',
                        label:
                            'CONFERENCE.WIZARD.PARTICIPANTS.MODAL.ACTOR.COMPETENCE',
                        control: new FormControl({
                            value: participant.competence,
                            disabled: participant.readonly || this.isReadonly()
                        }),
                        size: '12|6|6'
                        // options: this.utilityService.getActorCompetence()
                    })
                    .set('emails', {
                        type: 'tag-input',
                        label:
                            'CONFERENCE.WIZARD.PARTICIPANTS.MODAL.EMAIL.LABEL',
                        pattern: '^[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,4}$',
                        control: new FormControl(
                            {
                                value: participant.emails,
                                disabled:
                                    participant.readonly || this.isReadonly()
                            },
                            []
                        ),
                        errorLabels: [
                            ErrorLabelConstants.REGEX('ERROR.EMAIL.INVALID')
                        ],
                        size: '12|6|6'
                    })
                    .set('determination', {
                        type: 'switch',
                        label:
                            'CONFERENCE.WIZARD.PARTICIPANTS.MODAL.ACTOR.DETERMINATION',
                        control: new FormControl({
                            value: participant.determination,
                            disabled: participant.readonly || this.isReadonly()
                        }),
                        size: '12|6|6'
                    })
            })
            .set('user', {
                panel: true,
                panelHead:
                    participant.readonly || this.isReadonly()
                        ? 'CONFERENCE.WIZARD.PARTICIPANTS.MODAL.USER.TITLE_DISABLED'
                        : 'CONFERENCE.WIZARD.PARTICIPANTS.MODAL.USER.TITLE',
                tooltip:
                    !this.isReadonly() &&
                    new TooltipModel(
                        'CONFERENCE.WIZARD.PARTICIPANTS.MODAL.USER.TOOLTIP.CONTENT'
                    ),
                oneToMany: true,
                accordion: true,
                model: User,
                saveFn: this.saveUser.bind(this),
                listStructure: this.defineUserTableStructure(
                    participant.readonly || this.isReadonly()
                ),
                listTitle:
                    'CONFERENCE.WIZARD.PARTICIPANTS.MODAL.USER.LIST_TITLE',
                emptyTextList:
                    'CONFERENCE.WIZARD.PARTICIPANTS.MODAL.USER.EMPTY_TEXT_LIST',
                listMany: participant.users,
                readonly: participant.readonly || this.isReadonly(),
                fields: new Map()
                    .set('name', {
                        type: 'text',
                        label: 'CONFERENCE.WIZARD.PARTICIPANTS.MODAL.USER.NAME',
                        validators: [Validators.required],
                        errorLabels: [ErrorLabelConstants.REQUIRED],
                        size: '12|6|6'
                    })
                    .set('surname', {
                        type: 'text',
                        label:
                            'CONFERENCE.WIZARD.PARTICIPANTS.MODAL.USER.SURNAME',
                        validators: [Validators.required],
                        errorLabels: [ErrorLabelConstants.REQUIRED],
                        size: '12|6|6'
                    })
                    .set('fiscalCode', {
                        type: 'text',
                        label: 'CONFERENCE.WIZARD.PARTICIPANTS.MODAL.USER.CF',
                        pattern:
                            '^[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]$',
                        validators: [Validators.required],
                        errorLabels: [
                            ErrorLabelConstants.REQUIRED,
                            ErrorLabelConstants.REGEX('ERROR.REGEX')
                        ],
                        size: '12|6|6'
                    })
                    .set('profile', {
                        type: 'select',
                        label:
                            'CONFERENCE.WIZARD.PARTICIPANTS.MODAL.USER.PROFILE',
                        validators: [Validators.required],
                        size: '12|6|6',
                        options: this.utilityService.getPersonRoles(
                            participant.role.key
                        ),
                        errorLabels: [ErrorLabelConstants.REQUIRED]
                    })
                    .set('email', {
                        type: 'text',
                        pattern: '^[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,4}$',
                        label:
                            'CONFERENCE.WIZARD.PARTICIPANTS.MODAL.USER.EMAIL',
                        validators: [],
                        errorLabels: [
                            ErrorLabelConstants.REGEX('ERROR.EMAIL.INVALID')
                        ],
                        size: '12|6|6'
                    })
                    /*
                    .set('addressPec', {
                        type: 'switch',
                        label:
                        'CONFERENCE.WIZARD.PARTICIPANTS.MODAL.USER.ADDRESSPEC',
                        size: '12|6|6'
                    })
                    */
            })
            .set('notes', {
                panel: true,
                panelHead: 'CONFERENCE.WIZARD.PARTICIPANTS.MODAL.NOTES.TITLE',
                accordion: true,
                fields: new Map().set('notes', {
                    type: 'text-area',
                    // label: 'CONFERENCE.WIZARD.PARTICIPANTS.MODAL.NOTES.TITLE',
                    control: new FormControl({
                        value: participant.note,
                        disabled: participant.readonly || this.isReadonly()
                    }),
                    size: '12|12|12',
                    maxLength: 300
                })
            });

        if (participant.isApplicant()) {
            this._setApplicantCustomizzations(participant);
        }
        if (this.actionParticipant === ActionForm.EDIT) {
            this.groupFields
                .get('actor')
                .fields.get('fiscalCode')
                .control.markAsTouched();
        }

        this._setCompetenceFields(participant);

        if (!environment.customConfigurationConf.determinationView) {
            this._deleteDetermination();
        }
    }

    private _setApplicantCustomizzations(participant: Participant): void {
        const principalApplicant =
            participant.getPrincipalApplicant() || participant.getApplicant();

        if (principalApplicant) {
            const fields = this.groupFields.get('actor').fields;
            this.groupFields.get('actor').panelHead =
                'CONFERENCE.WIZARD.PARTICIPANTS.MODAL.ACTOR.APPLICANT_TITLE';
            fields.delete('competence');
            fields.delete('determination');
            fields.get('authority').label =
                'CONFERENCE.WIZARD.PARTICIPANTS.MODAL.ACTOR.APPLICANT_AUTHORITY';
            fields.get('description').label =
                'CONFERENCE.WIZARD.PARTICIPANTS.MODAL.ACTOR.APPLICANT_AUTHORITY_DESCRIPTION';
            fields.get('pec').label =
                'CONFERENCE.WIZARD.PARTICIPANTS.MODAL.ACTOR.APPLICANT_PEC';
            fields.get('fiscalCode').label =
                'CONFERENCE.WIZARD.PARTICIPANTS.MODAL.ACTOR.APPLICANT_CF';
            fields
                .get('authority')
                .control.setValue(
                    `${principalApplicant.name} ${principalApplicant.surname}`
                );
            fields
                .get('description')
                .control.setValue(
                    `${this.conference.procedure.company.getFormattedData()}`
                );
        }
    }

    private _deleteDetermination(): void {
        const fields = this.groupFields.get('actor').fields;

        fields.delete('determination');
    }

    private _saveParticipantCompleteFn(response: WrapperPostPutData): void {
        if (this.actionParticipant === ActionForm.CREATE) {
            this.tmpParticipant.id = response.id;
            this.conference.participants.push(this.tmpParticipant);
        } else {
            this.conference.editParticipant(this.tmpParticipant);
        }

        this.loaderService.hideLoading(SectionLoading.PARTICIPANT_MODAL);
        this.toastr.info(
            this.i18nService.translate(
                'CONFERENCE.WIZARD.PARTICIPANTS.TOASTR.SUCCESS_SAVE.TEXT'
            ),
            this.i18nService.translate(
                'CONFERENCE.WIZARD.PARTICIPANTS.TOASTR.SUCCESS_SAVE.TITLE'
            )
        );

        this.tmpUsers = [];
        this.tmpParticipant = null;
        this.modal.close();
    }

    private _saveParticipantErroFn(error: ErrorMessage): void {
        this.toastr.error(
            this.i18nService.translate(
                'CONFERENCE.WIZARD.PARTICIPANTS.TOASTR.ERROR_SAVE.TEXT'
            ),
            this.i18nService.translate(
                'CONFERENCE.WIZARD.PARTICIPANTS.TOASTR.ERROR_SAVE.TITLE'
            )
        );
    }

    private _setCompetenceFields(participant: Participant): void {
        if (!participant.isApplicant()) {
            if (this.template.competence.length > 0) {
                const fields: Map<string, FormField> = this.groupFields.get(
                    'actor'
                ).fields;
                const competence: FormField = fields.get('competence');
                const emails: FormField = fields.get('emails');
                const determination: FormField = fields.get('determination');
                const role: FormField = fields.get('role');
                const pec: FormField = fields.get('pec');
                const fiscalCode: FormField = fields.get('fiscalCode');

                role.size = '12|4|4';
                pec.size = '12|4|4';
                fiscalCode.size = '12|4|4';

                fields.delete('emails');
                fields.delete('determination');
                fields.delete('competence');

                fields.set('competenceAuthorization', competence);
                fields
                    .set('emails', emails)
                    .set('determination', determination);

                competence.type = 'select-two';
                competence.options = of(this.template.competence);
                competence.control = new FormControl({
                    value: participant.competenceAuthorization,
                    disabled: participant.readonly || this.isReadonly()
                });
                this._checkDetermination(role.control.value);
            }
            this.groupFields = new Map(this.groupFields);

            this.loaderService.hideLoading(
                SectionLoading.PARTICIPANT_MODAL,
                500
            );
        } else {
            this.loaderService.hideLoading(
                SectionLoading.PARTICIPANT_MODAL,
                500
            );
        }
    }

    private _saveParticipant(
        participant: Participant
    ): Observable<WrapperPostPutData> {
        this.loaderService.showLoading(SectionLoading.PARTICIPANT_MODAL);
        this.loggerService.log('_saveParticipant', participant);
        this.loggerService.log('mode', this.actionParticipant);
        this.tmpParticipant = new Participant(
            Object.assign(this.tmpParticipant || {}, participant)
        );

        if (this.actionParticipant === ActionForm.CREATE) {
            return this.participantsService
                .createParticipant(this.conference.id, this.tmpParticipant)
                .pipe(takeUntil(this.destroy$));
        } else {
            const users: User[] = this.tmpParticipant.users;
            delete this.tmpParticipant.users;

            return this.participantsService
                .editParticipant(this.tmpParticipant)
                .pipe(
                    takeUntil(this.destroy$),
                    tap((resPart: WrapperPostPutData) => {
                        this.tmpParticipant.users = users;
                    })
                );
        }
    }

    // trasform form value in participants
    private _extractDataToSubmit(form: FormGroup): Participant {
        return {
            ...form.getRawValue().actor,
            users: Object.assign([], this.tmpUsers),
            note: form.value.notes.notes
        };
    }

    private _initContext(participant: Participant): void {
        if (this.tmpUsers.length && !participant.users.length) {
            participant.users = this.tmpUsers;
        } else if (!this.tmpUsers.length) {
            this.tmpUsers = participant.users;
        }
    }

    deleteParticipantList(participantList: Participant[]){
        
        if(participantList.length == 0){
            this.toastr.warning(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.PARTICIPANTS.TOASTR.WARNING_DELETE_LIST_FILE.TEXT'
                ),
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.PARTICIPANTS.TOASTR.WARNING_DELETE_LIST_FILE.TITLE'
                )
            );
            return;                
        }
        this.participantsListToDelete = participantList;
        this.deleteListParticipantsConfirmModal.open()
    }

    deleteListParticipantsModalButtons: ActionItem[] = [
        new ActionItem(
            'SEARCH.CONFIRM_DELETE_MODAL.CANCEL_BUTTON',
            (action: ActionItem) => {
                this.deleteListParticipantsConfirmModal.close();
            },
            'close'
        ),
        new ActionItem(
            'SEARCH.CONFIRM_DELETE_MODAL.OK_BUTTON',
            (action: ActionItem) => {
                this._deleteParticipantsList();
            },
            'trash'
        )
    ];

    private _deleteParticipantsList(){
        
        let idList : Number[] = [];
        this.participantsListToDelete.forEach( participant => {
            idList.push(participant.id)
        })  

        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
        this.deleteListParticipantsConfirmModal.close();
        this.participantsService.deletePartecipantList(idList)
        .pipe( 
            catchError( res => {
                if(res.status != '200'){

                    this.toastr.error(
                        this.i18nService.translate(
                            'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.ERROR_DELETE_LIST_PARTICPANT.TEXT'
                        ),
                        this.i18nService.translate(
                            'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.ERROR_DELETE_LIST_PARTICPANT.TITLE'
                        )
                    );
                    
                    return of(null)
                }else{
                    this.toastr.error(
                        this.i18nService.translate(
                            'CONFERENCE.WIZARD.PARTICIPANTS.TOASTR.SUCCESS_DELETE_LIST_PARTICPANT.TEXT'
                        ),
                        this.i18nService.translate(
                            'CONFERENCE.WIZARD.PARTICIPANTS.TOASTR.SUCCESS_DELETE_LIST_PARTICPANT.TITLE'
                        )
                    );

                    return of()
                }
            })
        )
        .subscribe( res =>{
            if(res !== null){
                

                this.participantsListToDelete.forEach( participant => {
                    //this.model.delete(file);                  
                    const participantIndex = this._findPartecipantById(participant.id);

                    if (participantIndex !== null) {
                        this.deleteParticipantFromList(participant);
                    }            
                })  

                this.participantsListToDelete = [];
            }

            this.loaderService.hideLoading(
                SectionLoading.ALL_CONTENT,
                300
            );
        })             
        
    }

    private _findPartecipantById(
        participantId: string
    ): number {
        for (let i = 0; i <  this.conference.participants.length; i++) {
            if ( this.findParticipantForListToDelete(participantId)) {
                return i;
            }
        }
        return null;
    }

    private findParticipantForListToDelete(participantsId: string): Participant {
        return this.conference.participants.find((p: Participant) => p.id === participantsId);
    }

    private deleteParticipantFromList (participant: Participant): void {
        const i = this.conference.participants.findIndex((p: Participant) => p.id === participant.id);
        this.conference.participants.splice(i, 1);
    }

    toggleSelection(){
        let partecipants : any = this.conference.participants;
        partecipants.forEach( participant => {  
            if (participant.readonly == false) {
                participant.selected = !participant.selected;
            }
        });
    }

    deleteParticipantsSelected(){
        let partecipantsList = []

        let partecipants : any = this.conference.participants;
        partecipants.forEach( participant => {  
            if(participant.selected){
                partecipantsList.push(participant)
            }
        });

        this.deleteParticipantList(partecipantsList)
    }
}
