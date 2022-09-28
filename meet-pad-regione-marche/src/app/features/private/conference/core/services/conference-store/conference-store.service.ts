import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpResponse } from '@angular/common/http';

import {
    Observable,
    forkJoin,
    Subject,
    BehaviorSubject,
    Subscription,
    throwError,
    of
} from 'rxjs';
import { mergeMap, map, tap, switchMap, catchError } from 'rxjs/operators';

import { ToastrService } from 'ngx-toastr';

import { DateModel, ErrorMessage, Step } from '@eng-ds/ng-toolkit';
import { LoggerService, I18nService, ConfigService } from '@eng-ds/ng-core';

import {
    WrapperPostPutData,
    LoaderService,
    ComboBox,
    SectionLoading,
    ApplicationRole,
    ConferenceRole,
    FooterButtons,
    WrapperError,
    WrapperResponse,
    FormField,
    FormFieldGroup
} from '@common';

import {
    ActionForm,
    UtilityService,
    StepName,
    ConferenceState,
    ConferenceService,
    ConferenceType,
    UserPortalService
} from '@app/core';

import * as Common from '@common';

import { ParticipantsService } from '../participants/participants.service';
import { DocumentsService } from '../documents/documents.service';

import { SupportContactService } from '../support-contact/support-contact.service';
import {
    Event,
    Conference,
    Participant,
    ConferencePermissions,
    Procedure,
    Definition,
    Documentation,
    SupportContact,
    User,
    ConferenceStep,
    ConferenceTemplateToApply,
    ConferenceTemplate
} from '../../models';
import { Voting } from '../../models/voting/voting.model';
import {AccreditationPartecipants} from "@features/private/conference/core/models/accreditation/accreditations-participants.model";
import { debug } from 'util';

@Injectable()
export class ConferenceStoreService {
    // a Subject of conference for share reactive change
    conference$: Subject<Conference>;

    confirmationModal$: Subject<FooterButtons> = new Subject<FooterButtons>();

    conference: Conference;
    initLoading: boolean;

    conferenceAction: ActionForm;
    procedingAdministration: Participant;
    stepNumber: StepName;

    lastStepActivated: StepName;

    activeStep: ConferenceStep;
    steps: ConferenceStep[];
    userProfile$: BehaviorSubject<Common.User>;
    isDelegate: boolean;

    private _submitParticipantSubscription: Subscription;
    private _conferencePersmissionSubscription: Subscription;
    private _updateConferenceStepSubscription: Subscription;
    private _updateSateSubscription: Subscription;

    conferencePermissions: ConferencePermissions;
    userConferencePermissions$: Subject<ConferencePermissions>;
    template: ConferenceTemplateToApply;

    constructor(
        private router: Router,
        private loggerService: LoggerService,
        private i18nService: I18nService,
        private loaderService: LoaderService,
        private configService: ConfigService,
        private conferenceService: ConferenceService,
        private participantsService: ParticipantsService,
        private documentsService: DocumentsService,
        private userService: UserPortalService,
        private utilityService: UtilityService,
        public toastr: ToastrService,
        private supportContactService: SupportContactService
    ) {}

    submitProcedure(data: Procedure): Observable<WrapperPostPutData> {
        const today = new DateModel(new Date());

        this.loggerService.log('_submitProcedure', data);

        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);

        // aggiorna il modello interno
        this.conference.procedure.update(data);
        // prepopola la definition.instance.creationDate con la data di start se quest'ultima non è posteriore alla data odierna,
        // altrimenti prepopola la definition.instance.creationDate con la data di oggi
        if (this.conference.procedure.applicant.startDate > today) {
            this.conference.definition.instance.creationDate = today;
        } else {
            this.conference.definition.instance.creationDate = this.conference.procedure.applicant.startDate;
        }

        if (this.isCreateMode()) {
            return this._createConference();
        } else {
            return this._editConference();
        }
    }

    saveCompleteProcedure(response: WrapperPostPutData): any {
        if (
            this._submitParticipantSubscription &&
            this._submitParticipantSubscription instanceof Subscription
        ) {
            this._submitParticipantSubscription.unsubscribe();
        }
        if (this.isCreateMode()) {
            this._submitParticipantSubscription = forkJoin([
                // salva il partecipante di default: richiedente
                this.participantsService.createParticipant(
                    response.id,
                    this.conference.createApplicantParticipantFromApplicant(
                        this.conference.procedure.applicant
                    )
                ),
                // salva il partecipante di default: amministrazione procedente
                this._getProcedingAdministrationParticipant().pipe(
                    mergeMap(participant =>
                        this.participantsService.createParticipant(
                            response.id,
                            participant
                        )
                    )
                )
            ]).subscribe((res: WrapperPostPutData[]) => {
                this.navigateToRoute({
                    path: 'definition',
                    id: response.id
                });
                this.toastr.info(
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.PROCEDURE.TOASTR.SUCCESS_SAVE.TEXT'
                    ),
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.PROCEDURE.TOASTR.SUCCESS_SAVE.TITLE'
                    )
                );
            });
            this.lastStepActivated++;
        } else {
            this._submitParticipantSubscription = this._editApplicantParticipant().subscribe(
                (res: WrapperPostPutData) => {
                    this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);

                    this.toastr.info(
                        this.i18nService.translate(
                            'CONFERENCE.WIZARD.PROCEDURE.TOASTR.SUCCESS_SAVE.TEXT'
                        ),
                        this.i18nService.translate(
                            'CONFERENCE.WIZARD.PROCEDURE.TOASTR.SUCCESS_SAVE.TITLE'
                        )
                    );
                }
            );
        }
    }

    saveErrorProcedure(error: ErrorMessage): void {
        // TODO: gestire l'errore validazione campi
        this.toastr.error(
            this.i18nService.translate(
                'CONFERENCE.WIZARD.PROCEDURE.TOASTR.ERROR_SAVE.TEXT'
            ),
            this.i18nService.translate(
                'CONFERENCE.WIZARD.PROCEDURE.TOASTR.ERROR_SAVE.TITLE'
            )
        );
    }

    submitDefinition(data: Definition): Observable<WrapperPostPutData> {
        this.loggerService.log('_submitDefinition', data);
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
        // aggiorna il modello interno
        this.conference.definition.update(data);
        if (this.getStepActionForm(StepName.DEFINITION) === ActionForm.CREATE) {
            this.conference.step = this.activeStep.id;
        }
        return this._editConference();
    }

    saveCompleteDefinition(response: any): void {
        if (this.getStepActionForm(StepName.DEFINITION) === ActionForm.CREATE) {
            this.navigateToRoute({ path: 'participant' });
            this.lastStepActivated++;
        } else {
            this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
        }
        this.toastr.info(
            this.i18nService.translate(
                'CONFERENCE.WIZARD.DEFINITION.TOASTR.SUCCESS_SAVE.TEXT'
            ),
            this.i18nService.translate(
                'CONFERENCE.WIZARD.DEFINITION.TOASTR.SUCCESS_SAVE.TITLE'
            )
        );
    }

    saveErrorDefinition(
        error: HttpResponse<WrapperResponse>,
        groupFields: Map<string, FormFieldGroup>
    ): Observable<null> {
        this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
        this._parseFieldsErrors(error, groupFields, 'definition');
        return throwError(error);
    }

    private _parseFieldsErrors(
        err: HttpResponse<WrapperResponse>,
        groupFields: Map<string, FormFieldGroup>,
        section: 'procedure' | 'definition'
    ) {
        if (err.status === 422 && err.body && err.body.errors) {
            const errors = err.body.errors;
            for (let i = 0; i < errors.length; i++) {
                this._parseErrorField(errors[i], groupFields, section);
            }
        }
    }

    private _parseErrorField(
        error: WrapperError,
        groupFields: Map<string, FormFieldGroup>,
        section: 'procedure' | 'definition'
    ) {
        const errorFieldName = error.fields[0];
        const errorMsgField = error.msg;

        // setta l'errore nella form
        if (errorFieldName) {
            const formField = this._getFormFieldsByErrorFieldName(
                errorFieldName,
                groupFields,
                section
            );
            if (formField) {
                formField.control.setErrors({ required: true });
            }
        }

        if (errorMsgField) {
            this.toastr.error(
                errorMsgField,
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.VALIDATIONS_ERROR.TITLE'
                )
            );
        }
    }

    private _getFormFieldsByErrorFieldName(
        errorFieldName: string,
        groupFields: Map<string, FormFieldGroup>,
        section: 'procedure' | 'definition'
    ): null | FormField {
        // errorFieldName sarà di questo tipo definition.instance.firstSessionDate
        const groupFieldsName = errorFieldName
            .replace(`${section}.`, '')
            .split('.');
        if (groupFields.has(groupFieldsName[0])) {
            const _groupFields = groupFields.get(groupFieldsName[0]);
            _groupFields.openAccordion();
            return groupFields
                .get(groupFieldsName[0])
                .fields.get(groupFieldsName[1]);
        }
        return null;
    }

    submitParticipant(): void {
        this.loggerService.log('_submitParticipant');
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);

        if (
            this._updateConferenceStepSubscription &&
            this._updateConferenceStepSubscription instanceof Subscription
        ) {
            this._updateConferenceStepSubscription.unsubscribe();
        }

        this._updateConferenceStepSubscription = this._updateConferenceStep().subscribe(
            () => {
                this.navigateToRoute({ path: 'documentation' });
                this.conference.step = this.activeStep.id as StepName;
                this.lastStepActivated++;

                this.toastr.info(
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.PARTICIPANTS.TOASTR.SUCCESS_SUBMIT.TEXT'
                    ),
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.PARTICIPANTS.TOASTR.SUCCESS_SUBMIT.TITLE'
                    )
                );
            }
        );
    }

    submitDocumentation(): void {
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
        if (
            this._updateConferenceStepSubscription &&
            this._updateConferenceStepSubscription instanceof Subscription
        ) {
            this._updateConferenceStepSubscription.unsubscribe();
        }

        this._updateConferenceStepSubscription = this._updateConferenceStep().subscribe(
            () => {
                this.conference.step = this.activeStep.id as StepName;

                this.navigateToRoute({ path: 'summary' });
                this.lastStepActivated++;

                this.toastr.info(
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.SUCCESS_SAVE.TEXT'
                    ),
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.SUCCESS_SAVE.TITLE'
                    )
                );
            }
        );
    }

    submitSummary(): void {
        this.loggerService.log('submitSummary');
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);

        if (
            this._updateSateSubscription &&
            this._updateSateSubscription instanceof Subscription
        ) {
            this._updateSateSubscription.unsubscribe();
        }

        this._updateSateSubscription = this.conferenceService
            .updateState(
                this.conference.id,
                ConferenceState.DRAFT,
                StepName.INITIAL
            )
            .subscribe(() => {
                this.conference.updateState({
                    key: ConferenceState.DRAFT,
                    value: this.i18nService.translate('CONFERENCE.STATES.DRAFT')
                });
                // naviga sulla pagina della documentazione
                // sulla sezione relativa al caricamento della nota di invio
                this.navigateToRoute({
                    path: 'documentation',
                    id: this.conference.id,
                    fragment: 'panelGroupDocumentation'
                });
                if (this.conference.isPreliminary()) {
                    this.toastr.info(
                        this.i18nService.translate(
                            'CONFERENCE.WIZARD.SUMMARY.TOASTR.SUCCESS.PRELIMINARY_TEXT'
                        ),
                        this.i18nService.translate(
                            'CONFERENCE.WIZARD.SUMMARY.TOASTR.SUCCESS.TITLE'
                        )
                    );
                } else {
                    this.toastr.info(
                        this.i18nService.translate(
                            'CONFERENCE.WIZARD.SUMMARY.TOASTR.SUCCESS.TEXT'
                        ),
                        this.i18nService.translate(
                            'CONFERENCE.WIZARD.SUMMARY.TOASTR.SUCCESS.TITLE'
                        )
                    );
                }
                // next dello stesso conferencePermissions in modo da eseguire i controlli sul permissions
                this.userConferencePermissions$.next(
                    this.conferencePermissions
                );
                this.lastStepActivated = -1;
            });
    }

    onIndiction(): void {
        this.steps = this._getInitialStep();
        this.conference.step = StepName.INITIAL;
        this.stepNumber = StepName.PROCEDURE;
        this.lastStepActivated = StepName.PROCEDURE;

        this.setActiveStep(this.stepNumber);

        this.conference.updateState({
            key: ConferenceState.JUDGMENT,
            value: this.i18nService.translate('CONFERENCE.STATES.JUDGMENT')
        });

        this.navigateToDetail();
    }

    editManager(
        manager: ComboBox & any,
        administrationKey?: string
    ): Observable<any> {
        return this.utilityService.getAuthority(administrationKey).pipe(
            // takeUntil(this.destroy$),
            tap((authority: any) => {
                this.procedingAdministration = new Participant({
                    // tslint:disable-next-line:max-line-length
                    // authority: environment.defaultComboBox.conference.defaultParticipant.administrationProceding.participantAuthority,
                    // role: environment.defaultComboBox.conference.defaultParticipant.administrationProceding.participantRole,
                    // authority: authority.name,
                    // authority: environment.defaultComboBox.conference.defaultParticipant.administrationProceding.participantAuthority,
                    // tslint:disable-next-line:max-line-length
                    authority: {key: authority.id, value: authority.description},
                    role: authority.role,
                    pec: authority.pec,
                    fiscalCode: authority.fiscalCode,
                    description: authority.description,
                    users: [
                        new User({
                            surname: manager.surname,
                            name: manager.name,
                            fiscalCode: manager.fiscalCode,
                            email: manager.email,
                            // profile: environment.defaultComboBox.conference.defaultParticipant.administrationProceding.userRole
                            // or
                            profile: manager.profilo
                        })
                    ],
                    readonly: true
                });
            })
        );
    }

    /**
     *
     * @param conferenceId
     */

    initByConferenceId(
        conferenceId: string
    ): Observable<[Conference, Participant[]]> {
        return forkJoin([
            this.conferenceService.getDetail(conferenceId),
            this.resolveParticipants(conferenceId)
        ]).pipe(
            tap(([conference, participants]: [Conference, Participant[]]) => {
                if (conference) {                    
                    this.init(conference, participants);
                }
            }),
            switchMap(
                ([conference, participants]: [Conference, Participant[]]) => {
                    return this.setTemplate(conference.definition.instance
                        .conferenceType.key as ConferenceType).pipe(
                        map(
                            () =>
                                [conference, participants] as [
                                    Conference,
                                    Participant[]
                                ]
                        )
                    );
                }
            )
        );
    }

    /**
     * @param conference
     * @param participants
     */
    init(
        conference: Conference = null,
        participants: Participant[] = null,
        accreditationPartecipants: AccreditationPartecipants[] = null
    ): void {
        this._initStore(conference, participants);
        this._initConferenceUserProfile();
        this._initConferenceDelegate();

        setTimeout(_ => {
            this.initLoading = false;
        }, 500);
    }

    isCreateMode(): boolean {
        return this.conferenceAction === ActionForm.CREATE;
    }

    changeStepToChild(e: ConferenceStep): void {
        this.navigateToRoute({ path: e.stepPath });
    }

    _initConferencePermissions(): void {
        if (typeof this.conference.id !== 'undefined') {
            if (
                this._conferencePersmissionSubscription &&
                this._conferencePersmissionSubscription instanceof Subscription
            ) {
                this._conferencePersmissionSubscription.unsubscribe();
            }
            this._conferencePersmissionSubscription = this.getConferencePermissions().subscribe(
                value => {
                    this.conferencePermissions = new ConferencePermissions(
                        value['applicantEditor'].conferenceEditable,
                        value['applicantEditor'].stepList,
                        value['applicantEditor'].enabled
                    );
                    this.userConferencePermissions$.next(
                        this.conferencePermissions
                    );
                }
            );
        }
    }

    resetAll(): void {
        this.initLoading = true;
        this.conference = undefined;
        this.conferenceAction = undefined;
        this.procedingAdministration = undefined;
        this.stepNumber = StepName.PROCEDURE;
        this.activeStep = undefined;
        this.userProfile$ = new BehaviorSubject(null);
        this.conference$ = new Subject();

        this.userConferencePermissions$ = new Subject();
        this.conferencePermissions = undefined;
        this.isDelegate = false;

        this.steps = this._getInitialStep();
    }

    getConferencePermissions(): Observable<any> {
        return this.conferenceService.getApplicantEditorStep(
            this.conference.id
        );
    }

    showFooterButtonLoading(): void {
        this.loaderService.showLoading(SectionLoading.CONFERENCE_FOOTER_BUTTON);
    }

    cancelModalBeforeCreate(): void {
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
        this.router.navigate(['']);
    }

    closeModalBeforeCreate(): void {}

    getStepActionAfterIndiction(): ActionForm {
        return this._isReadonly() ? ActionForm.READONLY : ActionForm.EDIT;
    }

    getStepActionForm(step: StepName): ActionForm {
        return this._checkReadonlyStep(step)
            ? ActionForm.READONLY
            : this.conference.isDraft()
            ? ActionForm.EDIT
            : this.lastStepActivated <= step
            ? ActionForm.CREATE
            : ActionForm.EDIT;
    }

    isStepActive(stepName: StepName): boolean {
        return this.steps[stepName].id === this.activeStep.id;
    }

    setTemplate(
        conferenceType: ConferenceType,
        companyId: string = null
    ): Observable<ConferenceTemplateToApply> {
        return this._extractTemplate(conferenceType, companyId).pipe(
            tap((templateToApply: ConferenceTemplateToApply) => {
                this._setTemplate(templateToApply);
            })
        );
    }

    resolveTitle(conferenceId: string): Observable<any> | Promise<any> | any {
        if (conferenceId) {
            return `${this.i18nService.translate(
                'CONFERENCE.TITLE'
            )} ${this.conference.id.toString()}`;
        } else {
            return 'CONFERENCE.ADD.TITLE';
        }
    }

    resolveParticipants(conferenceId: string): Observable<Participant[]> {
        // ottiene i partecipanti
        return this.participantsService.getParticipants(conferenceId).pipe(
            mergeMap((participants: Participant[]) => {
                // per il partecipante richiedente ottiene
                // i rispettivi utenti
                let req: Observable<User[]> = null;
                let partApplicantIndex = null;
                // tslint:disable-next-line:forin
                for (const k in participants) {

                    if (
                        participants[k].role.key ===
                            this.configService.get(
                                'defaultComboBox.conference.defaultParticipant.applicant.participantRole'
                            ).key &&
                        participants[k].authority.key ===
                            this.configService.get(
                                'defaultComboBox.conference.defaultParticipant.applicant.participantAuthority'
                            ).key
                    ) {
                        req = this.participantsService.getUsers(
                            conferenceId,
                            participants[k].id
                        );
                        partApplicantIndex = k;
                        break;
                    }
                }
                return req.pipe(
                    map((res: User[]) => {
                        // mappa gli utenti ritornati nel rispettivo partecipante
                        // tslint:disable-next-line:forin
                        participants[partApplicantIndex].users = res;
                        // ritorna i parteicipanti nel primo return
                        return participants;
                    })
                );
            })
        );
    }

    getCurrentParticipantRole(): ApplicationRole | any {
        this.userService.getRole().subscribe(value => {
            return value;
        });
    }

    updateDocuments(
        conferenceId: string,
        allVisibility: boolean
    ): Observable<Documentation> {
        return this.documentsService
            .getDocuments(conferenceId, allVisibility)
            .pipe(
                tap((documents: Documentation) => {
                    this.conference.updateDocumentation(documents);
                })
            );
    }

    syncronizeDocuments(
        conferenceId: string,
        allVisibility: boolean
    ): Observable<Documentation> {
        return this.documentsService
            .syncronizeDocuments(conferenceId, allVisibility)
            .pipe(
                catchError( res => {                    
                    if(res.status !== "200"){
                        this.toastr.error(
                            this.i18nService.translate(
                                'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.ERROR_SYNC_FILE.TEXT'
                            ),
                            this.i18nService.translate(
                                'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.ERROR_SYNC_FILE.TITLE'
                            )
                        );

                        return of(null)
                    }else{

                        this.toastr.error(
                            this.i18nService.translate(
                                'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.SUCCESS_SYNC_FILE.TEXT'
                            ),
                            this.i18nService.translate(
                                'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.SUCCESS_SYNC_FILE.TITLE'
                            )
                        );
                        
                        return of()
                    }
                   
                }),
                tap((documents: Documentation) => {
                    if(documents != null){
                        this.conference.updateDocumentation(documents);
                    }
                })
            );
    }

    resolveDocuments(
        conferenceId: string,
        allVisibility: boolean
    ): Observable<any> | Promise<any> | any {
        return this.updateDocuments(conferenceId, allVisibility);
    }

    resolveContacts(conferenceId: string): any {
        return this.supportContactService.getContacts(conferenceId).pipe(
            tap((contacts: SupportContact[]) => {
                this.conference.addContacts(contacts);
            })
        );
    }

    navigateToRoute({
        path,
        id,
        fragment,
        replaceUrl
    }: {
        path: string;
        id?: string;
        fragment?: string;
        replaceUrl?: boolean;
    }): void {
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
        setTimeout(() => {
            this.router.navigate(
                ['conference', this.conference.id || id, path],
                {
                    fragment,
                    replaceUrl
                }
            );
            this.setActiveStep(StepName[path.toUpperCase()]);
        }, this.configService.get('loading.conference.startNavDelay'));
    }

    navigateToDetail(): void {
        this.router.navigate(['/conference', this.conference.id]);
    }

    selectCorrectRoute(replaceUrl: boolean = false): void {
        switch (this.conference.step) {
            case -1: {
                if (this.conference.state.key === ConferenceState.COMPILING) {
                    this.navigateToRoute({ path: 'summary', replaceUrl });
                } else {
                    if (this.conference.isDraft()) {
                        this.navigateToRoute({
                            path: 'documentation',
                            id: this.conference.id,
                            fragment: 'panelGroupDocumentation',
                            replaceUrl
                        });
                    } else {
                        this.navigateToRoute({ path: 'procedure', replaceUrl });
                    }
                }
                break;
            }
            case StepName.PROCEDURE: {
                this.navigateToRoute({ path: 'definition', replaceUrl });
                break;
            }
            case StepName.DEFINITION: {
                this.navigateToRoute({ path: 'participant', replaceUrl });
                break;
            }
            case StepName.PARTICIPANT: {
                this.navigateToRoute({ path: 'documentation', replaceUrl });
                break;
            }
            case StepName.DOCUMENTATION: {
                this.navigateToRoute({ path: 'summary', replaceUrl });
                break;
            }
        }
    }

    private _initConferenceUserProfile(): void {
        if (this.conference.id) {
            this._updateConferenceStepSubscription = this.userService
                .getConferenceProfile(this.conference.id)
                .subscribe((user: Common.User) => {
                    this.userProfile$.next(user);
                });
        } else {
            this.userProfile$.next(null);
        }
    }

    
    private _initConferenceDelegate(): void {
        if (this.conference.id) {
            this.userService
                .isConferenceDelegate(this.conference.id).pipe(                
                    catchError(_ => {
                        this.isDelegate = false;
                        return of(null);
                    })
                )
                .subscribe(res => {
                    this.isDelegate = res;
                });
        } else {
            this.isDelegate = false;
        }
    }

    private _initStore(
        conference: Conference,
        participants: Participant[]
    ): void {
        this.resetAll();
        if (conference) {
            this._setupMode(conference, participants);
        } else {
            // add
            this._setupAddMode();
        }
        this.lastStepActivated = this.stepNumber;
        this._applyActiveStep(this.stepNumber, true);
        this._setupStepState();
    }

    private _isSummaryStep(): boolean {
        return this.activeStep.id === StepName.SUMMARY;
    }

    private _isInSummaryStep(step: StepName): boolean {
        return step === StepName.SUMMARY;
    }

    private _isReadonly(): boolean {
        return this.conferenceAction === ActionForm.READONLY;
    }

    private _checkReadonlyStep(step: StepName): boolean {
        let profile: string;

        if (typeof this.conference.id !== 'undefined') {
            this.userService
                .getConferenceProfile(this.conference.id)
                .subscribe((value: Common.User) => {
                    value ? (profile = value.profile.key) : (profile = '-1');
                });
        }

        return (
            this.conference.isIndictionState() ||
            this._isReadonly() ||
            (this._isSummaryStep() && !this._isInSummaryStep(step)) ||
            (profile === ConferenceRole.APPLICANT
                ? this.checkStepPermissionsForApplicant(step)
                : profile === '-1' ||
                  profile === ConferenceRole.CONFERENCE_MANAGER
                ? !this.checkStepPermissionsForCreator(step)
                : false)
        );
    }

    checkStepPermissionsForApplicant(step: StepName): boolean {
        if (this.conferencePermissions && !this.conferencePermissions.enabled) {
            return true;
        }

        return (
            this.conferencePermissions &&
            this.conferencePermissions.enabled &&
            typeof this.conferencePermissions.stepList.find(
                _step => _step === step
            ) === 'undefined'
        );
    }

    checkStepPermissionsForCreator(step: StepName): boolean {
        if (!this.conference.enableApplicantEdit) {
            return true;
        } else {
            return (
                this.conferencePermissions &&
                typeof this.conferencePermissions.stepList.find(
                    _step => _step === step
                ) === 'undefined'
            );
        }
    }

    private _getInitialStep(): ConferenceStep[] {
        return [
            new ConferenceStep(
                new Step(
                    StepName.PROCEDURE,
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.PROCEDURE.TITLE'
                    ),
                    false,
                    false
                ),
                'procedure'
            ),
            new ConferenceStep(
                new Step(
                    StepName.DEFINITION,
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.DEFINITION.TITLE'
                    ),
                    false,
                    true
                ),
                'definition'
            ),
            new ConferenceStep(
                new Step(
                    StepName.PARTICIPANT,
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.PARTICIPANTS.TITLE'
                    ),
                    false,
                    true
                ),
                'participant'
            ),
            new ConferenceStep(
                new Step(
                    StepName.DOCUMENTATION,
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.DOCUMENTATION.TITLE'
                    ),
                    false,
                    true
                ),
                'documentation'
            ),
            new ConferenceStep(
                new Step(
                    StepName.SUMMARY,
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.SUMMARY.TITLE'
                    ),
                    false,
                    true
                ),
                'summary'
            ),
            new ConferenceStep(
                new Step(
                    StepName.ACCREDITATION,
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.ACCREDITATION.TITLE'
                    ),
                    false,
                    true
                ),
                'accreditation'
            ),
            new ConferenceStep(
                new Step(
                    StepName.EVENT,
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.EVENTS.TITLE'
                    ),
                    false,
                    true
                ),
                'event'
            ),
            new ConferenceStep(
                new Step(
                    StepName.PEC,
                    this.i18nService.translate('CONFERENCE.WIZARD.PEC.TITLE'),
                    false,
                    true
                ),
                'pec'
            ),
            new ConferenceStep(
                new Step(
                    StepName.VOTINGS,
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.VOTING.TITLE'
                    ),
                    false,
                    true
                ),
                'votings'
            ),
            new ConferenceStep(
                new Step(
                    StepName.PROTOCOL,
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.PROTOCOL.TITLE'
                    ),
                    false,
                    true
                ),
                'protocol'
            )
        ];
    }

    private _setupMode(
        conference: Conference,
        participants: Participant[]
    ): void {
        // console.log('-xmf---------------------conference.state.key' + conference.state.key)
        if(conference && conference.state && conference.state.key === ConferenceState.CLOSED)
            this.conferenceAction = ActionForm.READONLY;
        else
            this.conferenceAction = ActionForm.EDIT;

        this.conference = new Conference(conference);
        this.conference.addParticipants(participants);
        this.stepNumber = this.conference.step;

        if (this.conference.step !== StepName.SUMMARY) {
            this.stepNumber += 1;
        }
    }

    private _setupAddMode(): void {
        this.conferenceAction = ActionForm.CREATE;
        this.conference = new Conference();
    }

    private _setupStepState(): void {
        let endStepIndex = this.stepNumber;
        if (
            this.conference.isDraft() ||
            this.conference.isIndictionState() ||
            this.conference.isClosed()
        ) {
            endStepIndex = this.steps.length;
        }

        if (this.stepNumber < this.steps.length) {
            for (let i = 0; i <= endStepIndex; i++) {
                if (this.steps[i]) {
                    this.steps[i].visited = true;
                    this.steps[i].disable = false;
                }
            }
        }
    }

    private _applyActiveStep(step: StepName, init = false): void {
        this.stepNumber = step;
        this.activeStep = this.steps[step];
        // aggiorna lo step sulla conference solo in init o in fase di salvataggio
        if (!init && this.lastStepActivated < step) {
            this.conference.step = step - 1;
        }
    }

    /**
     * Aggiorna lo step attivo sulla UI e sul modello
     * @param step step da impostare
     */
    setActiveStep(step: StepName): void {
        this._applyActiveStep(step);
        this._setupStepState();
    }

    private _createConference(): Observable<WrapperPostPutData> {
        return this.conferenceService.create(this.conference);
    }

    private _editConference(
        dataToSend: { [key: string]: boolean } = {
            procedure: true,
            definition: true,
            documentation: false
        }
    ): Observable<WrapperPostPutData> {
        return this.conferenceService.edit(this.conference, dataToSend);
    }

    private _updateConferenceStep(): Observable<any> {
        return this.conferenceService.updateStep(
            this.conference.id,
            this.activeStep.id
        );
    }

    private _editApplicantParticipant(): Observable<WrapperPostPutData> {
        // clona il partecipante richiedente e aggiorna i dati a partire dai dati dell'applicant
        const tmpPart = this.conference.getApplicantParticipantFromApplicant(
            this.conference.procedure.applicant
        );

        if (tmpPart) {

            // aggiorna il partecipante sul BE
            return this.participantsService.editParticipant(tmpPart).pipe(
                mergeMap((resPart: WrapperPostPutData) => {
                    return of(resPart);
                })
            );
        } else {
            this.toastr.warning(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.PROCEDURE.TOASTR.SUCCESS_SAVE_NO_APPLICANT.TEXT'
                ),
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.PROCEDURE.TOASTR.SUCCESS_SAVE_NO_APPLICANT.TITLE'
                )
            );

            return new Observable(observer => {
                observer.next();
                observer.complete();
            });
        }
    }

    private _getProcedingAdministrationParticipant(): Observable<Participant> {
        if (!this.procedingAdministration) {
            return forkJoin([
                this.userService.getInfo(),
                this.utilityService.getAuthority()
            ]).pipe(
                // takeUntil(this.destroy$),
                map((res: any) => {
                    // const [userInfo, authority]: [User, Partial<Participant>] = res;
                    const [userInfo, authority]: [any, any] = res;

                    const users = [
                        new User({
                            surname: userInfo.lastname,
                            name: userInfo.name,
                            fiscalCode: userInfo.fiscalCode,
                            email: userInfo.email,
                            // profile: environment.defaultComboBox.conference.defaultParticipant.administrationProceding.userRole
                            // or
                            profile: userInfo.profile
                        })
                    ];
                    // TODO: rivedere questa sezione
                    return new Participant({
                        // tslint:disable-next-line:max-line-length
                        // authority: environment.defaultComboBox.conference.defaultParticipant.administrationProceding.participantAuthority,
                        // role: environment.defaultComboBox.conference.defaultParticipant.administrationProceding.participantRole,
                        // authority: authority.name,
                        // tslint:disable-next-line:max-line-length
                        // authority: environment.defaultComboBox.conference.defaultParticipant.administrationProceding.participantAuthority,
                        // tslint:disable-next-line:max-line-length
                        authority: this.configService.get(
                            'defaultComboBox.conference.defaultParticipant.administrationProceding.participantAuthority'
                        ),
                        role: authority.role,
                        pec: authority.pec,
                        fiscalCode: authority.fiscalCode,
                        description: authority.description,
                        users,
                        readonly: true
                    });
                })
            );
        } else {
            return new Observable(observer => {
                observer.next(this.procedingAdministration);
                observer.complete();
            });
        }
    }

    private _setTemplate(template: ConferenceTemplateToApply): void {
        if (this.isCreateMode()) {
            this.conference.setType(template.type);
            this.conference.setApplicant(template.procedure.applicant);
            this.conference.setCompany(template.procedure.company);
        }
        this.template = template;
        this.conference$.next(this.conference);
    }

    private _extractTemplate(
        conferenceType: ConferenceType,
        companyId: string = null
    ): Observable<ConferenceTemplateToApply> {
        return this.utilityService.getConferenceTemplate(conferenceType).pipe(
            map((result: ConferenceTemplate) => {
                const conferenceTemplate = new ConferenceTemplate(result);
                    //console.log('-xmf------------------conferenceAction' + this.conferenceAction);
                const templateToApply = conferenceTemplate.extract({
                    conferenceAction: this.conferenceAction,
                    companyId
                });
                templateToApply.type = conferenceType;
                return templateToApply;
            })
        );
    }

    // metodo di supporto per disabilitare loader
    disableButtonsLoader() {
        this.loaderService.hideLoading(SectionLoading.CONFERENCE_FOOTER_BUTTON);
    }

    authorizeChange(): void {
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
        this.conferenceService
            // cambiare a true, solo per test a false
            .patchApplicantEditorEnabled(this.conference.id, true)
            .subscribe(response => {
                if (response) {
                    this.toastr.info(
                        this.i18nService.translate(
                            'CONFERENCE.WIZARD.CHANGE.TOASTR.AUTHORIZATION.TEXT'
                        ),
                        this.i18nService.translate(
                            'CONFERENCE.WIZARD.CHANGE.TOASTR.AUTHORIZATION.TITLE'
                        )
                    );
                    if (
                        this._conferencePersmissionSubscription &&
                        this._conferencePersmissionSubscription instanceof
                            Subscription
                    ) {
                        this._conferencePersmissionSubscription.unsubscribe();
                    }
                    this._conferencePersmissionSubscription = this.getConferencePermissions().subscribe(
                        (value: ConferencePermissions) => {
                            this.conferencePermissions = new ConferencePermissions(
                                value['applicantEditor'].conferenceEditable,
                                value['applicantEditor'].stepList,
                                value['applicantEditor'].enabled
                            );
                            this.userConferencePermissions$.next(
                                this.conferencePermissions
                            );
                        }
                    );
                    this.conference.setEnableApplicantEdit(true);
                    this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
                }
            });
    }

    // gestire errori, valutare
    unauthorizeChange(): void {
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
        this.conferenceService
            .patchApplicantEditorEnabled(this.conference.id, false)
            .subscribe(response => {
                if (response) {
                    this.toastr.info(
                        this.i18nService.translate(
                            'CONFERENCE.WIZARD.CHANGE.TOASTR.UNAUTHORIZATION.TEXT'
                        ),
                        this.i18nService.translate(
                            'CONFERENCE.WIZARD.CHANGE.TOASTR.UNAUTHORIZATION.TITLE'
                        )
                    );
                    if (
                        this._conferencePersmissionSubscription &&
                        this._conferencePersmissionSubscription instanceof
                            Subscription
                    ) {
                        this._conferencePersmissionSubscription.unsubscribe();
                    }
                    this._conferencePersmissionSubscription = this.getConferencePermissions().subscribe(
                        (value: ConferencePermissions) => {
                            this.conferencePermissions = new ConferencePermissions(
                                value['applicantEditor'].conferenceEditable,
                                value['applicantEditor'].stepList,
                                value['applicantEditor'].enabled
                            );
                            this.userConferencePermissions$.next(
                                this.conferencePermissions
                            );
                        }
                    );
                    this.conference.setEnableApplicantEdit(false);
                    this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
                }
            });
    }

    completeChange() {
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
        this.conferenceService
            .patchApplicantEditorEnabled(this.conference.id, false)
            .subscribe(response => {
                if (response) {
                    this.toastr.info(
                        this.i18nService.translate(
                            'CONFERENCE.WIZARD.CHANGE.TOASTR.COMPLETE.TEXT'
                        ),
                        this.i18nService.translate(
                            'CONFERENCE.WIZARD.CHANGE.TOASTR.COMPLETE.TITLE'
                        )
                    );
                    if (
                        this._conferencePersmissionSubscription &&
                        this._conferencePersmissionSubscription instanceof
                            Subscription
                    ) {
                        this._conferencePersmissionSubscription.unsubscribe();
                    }
                    this._conferencePersmissionSubscription = this.getConferencePermissions().subscribe(
                        (value: ConferencePermissions) => {
                            this.conferencePermissions = new ConferencePermissions(
                                value['applicantEditor'].conferenceEditable,
                                value['applicantEditor'].stepList,
                                value['applicantEditor'].enabled
                            );
                            this.userConferencePermissions$.next(
                                this.conferencePermissions
                            );
                        }
                    );
                    this.conference.setEnableApplicantEdit(false);
                    this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
                }
            });
    }

    openConfirmationModal(action: FooterButtons): void {
        this.confirmationModal$.next(action);
    }

    hidePageLoader(): void {
        this.loaderService.hideLoading(
            SectionLoading.ALL_CONTENT,
            this.configService.get('loading.conference.endNavDelay')
        );
    }
}
