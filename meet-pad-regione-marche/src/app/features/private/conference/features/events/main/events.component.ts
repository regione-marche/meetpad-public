import { Component, ViewChild, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';

import { Observable, Subscription, of, Subject } from 'rxjs';
import { takeUntil, filter, catchError } from 'rxjs/operators';

import { LoggerService, I18nService } from '@eng-ds/ng-core';
import { ModalComponent, ErrorMessage, ActionItem } from '@eng-ds/ng-toolkit';

import {
    WrapperPostPutData,
    ComboBox,
    AutoUnsubscribe,
    FormFieldGroup,FooterButtons, FormButton, LoaderService, SectionLoading
} from '@common';

import { ActionForm, EventType, ConferenceType } from '@app/core';

import {
    Event,
    Conference,
    ConferenceStoreService
} from '@features/private/conference/core';

import { EventStoreService } from '../services';
import { QuerySearchEvents } from '../models/query-search-events.model';
import { DocumentsService } from '../../../core/services/documents/documents.service';
import { ToastrService } from 'ngx-toastr';

declare var $: any;

@Component({
    selector: 'app-conference-events',
    templateUrl: './events.component.html',
    styleUrls: ['./events.component.scss']
})
export class EventsComponent extends AutoUnsubscribe implements OnInit {
    @ViewChild('detailModal') detailModal: ModalComponent;
    @ViewChild('attentionModal') attentionModal: ModalComponent;
    @ViewChild('eventModalToSign') eventModalToSign: ModalComponent;
    @ViewChild('uploadStatusModal') uploadStatusModal: ModalComponent;
    
    attentionModalBtn: ActionItem[] = [
        new ActionItem(
            'CONFERENCE.WIZARD.EVENTS.ATTENTION_MODAL.OK_BUTTON',
            _ => {
                this.conferenceStoreService.navigateToDetail();
            },
            'check'
        )
    ];

    @ViewChild('confirmModal') confirmModal: ModalComponent;

    confirmModalTitle: string = '';
    confirmModalText: string = '';

    tmpUploads: Subject<{ name: string; progress: number }>[] = [];
    progress: Subject<{ name: string; progress: number }> = new Subject<{ name: string; progress: number }>();
    noFileUploading: string = this.i18nService.translate(
        'COMMON.NO_FILE_UPLOADING'
    );

    confirmModalButtons: ActionItem[] = [
        new ActionItem(
            'CONFERENCE.AUTHORIZATION_MODAL.CANCEL_BUTTON',
            (action: ActionItem) => {
                this.confirmModal.close();
                this.detailModal.close();
            }
        ),
        new ActionItem(
            'CONFERENCE.AUTHORIZATION_MODAL.OK_BUTTON',
            (action: ActionItem) => {}
        )
    ];

    @ViewChild('errorModal') errorModal: ModalComponent;

    errorModalTitle: string = '';
    errorModalText: string = '';

    errorModalButtons: ActionItem[] = [
        new ActionItem(
            'CONFERENCE.AUTHORIZATION_MODAL.CANCEL_BUTTON',
            (action: ActionItem) => {
                this.confirmModal.close();
                this.detailModal.close();
            }
        ),
    ];

    groupFields: Map<string, FormFieldGroup>;
    eventTypes: ComboBox[];
    eventTypesSubscription: Subscription;

    footerTextSubmitBtn = 'CONFERENCE.WIZARD.EVENTS.MODAL.SAVE';
    footerButtons = true;
    currentEventType: EventType;
    action: ActionForm = this.conferenceStoreService.getStepActionAfterIndiction();

    searchQuery: QuerySearchEvents;
    searchOpen: boolean = false;
    searchInitialOpen: boolean = false;
    searchTitle: string = this.i18nService.translate(
        'CONFERENCE.WIZARD.EVENTS.SEARCH_PANEL.TITLE'
    );
    // usata per istanziare/distruggere il componente del modale di dettaglio
    // in modo che vengano distrutti tutti componenti figli, nello specifico il form
    // è necessario per il corretto funzionamento del form
    // e l'utilizzo del componente dinamico di alert per l'evento caricamento verbale
    detailModalInstance: boolean = true;
   
    // varabili usate per la gestione della firma multipla
    //comboBox per il recupero dei responsabili alla firma multipla
    managerOptions: Observable<ComboBox[]>;
    multipleSignEnable: boolean  = false;
    permissionToSign: boolean;
    multipleSign: boolean;
    managerCST: string='';

    saveFn: (event: Event) => Observable<WrapperPostPutData>;
    saveCompleteFn: (response: WrapperPostPutData) => void;
    saveErrorFn: (error: ErrorMessage) => void;
    extractDataToSubmit: (form: FormGroup) => Event;
    saveSendToSign: (event: Event) => Observable<WrapperPostPutData>;

    constructor(
        private eventsStoreService: EventStoreService,
        private loggerService: LoggerService,
        private documentsService: DocumentsService,
        private toastr: ToastrService,
        private loaderService: LoaderService,
    ) {
        super();
    }

    ngOnInit() {
        this._init();
    }

    get i18nService(): I18nService {
        return this.eventsStoreService.i18nService;
    }

    get conferenceStoreService(): ConferenceStoreService {
        return this.eventsStoreService.conferenceStoreService;
    }

    get conference(): Conference {
        return this.conferenceStoreService.conference;
    }

    get conferenceId(): string {
        return this.conferenceStoreService.conference.id;
    }

    get requestReference(): string {
        return this.conferenceStoreService.conference.procedure.applicant
            .requestReference;
    }

    get title(): string {
        switch (this.currentEventType) {
            case EventType.CONFERENCE_CREATED:
                return `${this.i18nService.translate(
                    'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.TITLE_CONFERENCE_CREATED'
                )}: ${this.requestReference}`;
            case EventType.CONFERENCE_INDICTION:
                return `${this.i18nService.translate(
                    'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.TITLE_CONFERENCE_INDICTION'
                )}: ${this.requestReference}`;
            case EventType.INTEGRATION_REQUEST:
            case EventType.INTEGRATION_CLOSED:
            case EventType.INTEGRATION_ONLY_ONE_REQUEST:
            case EventType.INTEGRATION_SEND:
                return `${this.i18nService.translate(
                    'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.TITLE_REQUEST_INTEGRATIONS'
                )}: ${this.requestReference}`;
            case EventType.CONFERENCE_MEMO:
                return `${this.i18nService.translate(
                    'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.TITLE_CONFERENCE_MEMO'
                )}: ${this.requestReference}`;
            case EventType.CONFERENCE_MEMO_INTERNAL:
                return `${this.i18nService.translate(
                    'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.TITLE_CONFERENCE_MEMO_INTERNAL'
                )}: ${this.requestReference}`;
            case EventType.CONFERENCE_CLOSING:
                return `${this.i18nService.translate(
                    'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.TITLE_CONFERENCE_CLOSING'
                )}: ${this.requestReference}`;
            case EventType.OPINION_EXPRESS:
                return `${this.i18nService.translate(
                    'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.TITLE_OPINION_EXPRESS'
                )}: ${this.requestReference}`;
            case EventType.FINAL_RESOLUTION:
                return `${this.i18nService.translate(
                    'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.TITLE_FINAL_RESOLUTION'
                )}: ${this.requestReference}`;
            case EventType.GENERIC_COMUNICATION:
                return `${this.i18nService.translate(
                    'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.TITLE_GENERIC_COMMUNICATION'
                )}: ${this.requestReference}`;
            case EventType.INTEGRATION_REGISTERED:
                return `${this.i18nService.translate(
                    'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.TITLE_INTEGRATION_REGISTERED'
                )}: ${this.requestReference}`;        
        }
    }

    get modalTitle(): string {
        switch (this.currentEventType) {
            case EventType.CONFERENCE_CREATED:
                return 'CONFERENCE.WIZARD.EVENTS.MODAL.TITLE_CONFERENCE_CREATED';
            case EventType.CONFERENCE_INDICTION:
                return 'CONFERENCE.WIZARD.EVENTS.MODAL.TITLE_CONFERENCE_INDICTION';
            case EventType.INTEGRATION_REQUEST:
                return 'CONFERENCE.WIZARD.EVENTS.MODAL.TITLE_REQUEST_INTEGRATIONS';
            case EventType.INTEGRATION_CLOSED:
                return 'CONFERENCE.WIZARD.EVENTS.MODAL.TITLE_INTEGRATION_CLOSED';
            case EventType.INTEGRATION_ONLY_ONE_REQUEST:
                return 'CONFERENCE.WIZARD.EVENTS.MODAL.TITLE_INTEGRATION_ONLY_ONE_REQUEST';
            case EventType.INTEGRATION_SEND:
                return 'CONFERENCE.WIZARD.EVENTS.MODAL.TITLE_INTEGRATION_SEND';
            case EventType.CONFERENCE_MEMO:
                return 'CONFERENCE.WIZARD.EVENTS.MODAL.TITLE_CONFERENCE_MEMO';
            case EventType.CONFERENCE_MEMO_INTERNAL:
                return 'CONFERENCE.WIZARD.EVENTS.MODAL.TITLE_CONFERENCE_MEMO_INTERNAL';
            case EventType.CONFERENCE_CLOSING:
                return 'CONFERENCE.WIZARD.EVENTS.MODAL.TITLE_CONFERENCE_CLOSING';
            case EventType.OPINION_EXPRESS:
                return 'CONFERENCE.WIZARD.EVENTS.MODAL.TITLE_OPINION_EXPRESS';
            case EventType.FINAL_RESOLUTION:
                return 'CONFERENCE.WIZARD.EVENTS.MODAL.TITLE_FINAL_RESOLUTION';
            case EventType.GENERIC_COMUNICATION:
                return 'CONFERENCE.WIZARD.EVENTS.MODAL.TITLE_GENERIC_COMMUNICATION';
            case EventType.INTEGRATION_REGISTERED:
                return 'CONFERENCE.WIZARD.EVENTS.MODAL.TITLE_INTEGRATION_REGISTERED';
                case EventType.EDIT_DATE:
                    return 'CONFERENCE.WIZARD.EVENTS.MODAL.TITLE_EDIT_DATA';
        }
    }

       //-------------new button -------------//
       modalIndictionToSignButtons: ActionItem[] = [
        new ActionItem(
            'SIGN.MODAL_SIGN.CANCEL_BUTTON',
            (action: ActionItem) => {
                this.eventModalToSign.close();
                this.detailModal.close();
            },
            'close'
        ),
        new ActionItem(
            'SIGN.MODAL_SIGN.OK_BUTTON',
            (action: ActionItem) => {},
            'check'
        )
    ];

    //-------------------------------------//
    
    get loading(): Observable<boolean> {
        return this.eventsStoreService.loading;
    }

    get groupButtons(): Map<FooterButtons, FormButton>{
        let groupButtonsToSet: Map<FooterButtons, FormButton> = new Map();
        switch (this.conference.definition.instance.conferenceType.key) {
            case ConferenceType.ENVIRONMENT_DEFINITION_AIA:
            case ConferenceType.ENVIRONMENT_DEFINITION_VIA:
            case ConferenceType.DOMUS:
                groupButtonsToSet.set(FooterButtons.SIGN, {
                    color: 'blue',
                    title: 'BUTTON.SEND_TO_SIGN',
                    onClick: this._logButton.bind(this)
                });
                break;
            default:
                groupButtonsToSet = new Map();
                break;
                
        }
        return groupButtonsToSet;     
    }

    _logButton(event: Event){
        console.log(" evento processato event", event);//this.conference.definition.instance.conferenceType 
        
    }
    get eventTypes$() {
        return this.eventsStoreService.utilityService
            .getEventTypesForConference(this.conferenceId)
            .pipe(takeUntil(this.destroy$));
    }

    openUploadStatusModal() {
        this.tmpUploads = [];
        this.uploadStatusModal.open();
    }

    closeUploadStatusModal() {
        this.resetUploadStatus();
        this.uploadStatusModal.close();
    }

    private _init(): void {
        this._initTypes();
        this._initCallback();
        this._onRefresh();
        this._initFileServiceObserver();
        this.permissionsToSign();
    }

    private _onRefresh() {
        this.eventsStoreService.eventsRefresh$
            .pipe(
                takeUntil(this.destroy$),
                filter((flag: boolean) => flag)
            )
            .subscribe(_ => {
                this._initTypes();
            });
    }

    private _initTypes(): void {
        if (
            this.eventTypesSubscription &&
            this.eventTypesSubscription instanceof Subscription
        ) {
            this.eventTypesSubscription.unsubscribe();
        }
        this.eventTypesSubscription = this.eventsStoreService.utilityService
            .getEventTypesForConference(this.conferenceId)
            .pipe(takeUntil(this.destroy$))
            .subscribe(
                (types: ComboBox[]): void => {
                    this.eventTypes = types;
                }
            );
    }

    private _initCallback(): void {

        this.saveFn = function(event: Event, checkEventType: boolean = true){            
            // caso firma multipla
            if (event.type.key == EventType.EDIT_DATE) {
                event.newDateTermine = this.groupFields.get('dates').fields.get('newDateTermine').control.value;
                event.newDateTermineEsprPareri = this.groupFields.get('dates').fields.get('newDateTermineEsprPareri').control.value;
                event.newDateTermineRichInteg = this.groupFields.get('dates').fields.get('newDateTermineRichInteg').control.value;
                event.newDatePrimaSessioneSimultanea = this.groupFields.get('dates').fields.get('newDatePrimaSessioneSimultanea').control.value;
            }

            if (event.attachmentFlag) {
                this._openModalError('CONFERENCE.WIZARD.EVENTS.ERROR_MODAL.TITLE_ERROR_ATTACH','CONFERENCE.WIZARD.EVENTS.ERROR_MODAL.TEXT_ERROR_ATTACH_CONTROL' )
                return of( null );
            } else {
                if (event.type.key == EventType.EDIT_DATE &&
                    this.groupFields.get('dates').fields.get('newDateTermine').control.value.date._d.toString() === this.groupFields.get('dates').fields.get('oldDateTermine').control.value.date._d.toString() &&
                    this.groupFields.get('dates').fields.get('newDateTermineEsprPareri').control.value.date._d.toString() === this.groupFields.get('dates').fields.get('oldDateTermineEsprPareri').control.value.date._d.toString() &&
                    this.groupFields.get('dates').fields.get('newDateTermineRichInteg').control.value.date._d.toString() === this.groupFields.get('dates').fields.get('oldDateTermineRichInteg').control.value.date._d.toString() &&
                    this.groupFields.get('dates').fields.get('newDatePrimaSessioneSimultanea').control.value.date._d.toString() === this.groupFields.get('dates').fields.get('oldDatePrimaSessioneSimultanea').control.value.date._d.toString()) {
                    this._openModalError('CONFERENCE.WIZARD.EVENTS.ERROR_MODAL.TITLE_ERROR_DATE','CONFERENCE.WIZARD.EVENTS.ERROR_MODAL.TEXT_ERROR_DATE_CONTROL' )
                    return of( null );
                } else {
                    if(this.isMultipleSignEnable() && this.groupFields.get('document').fields.get('multipleSign') !== undefined &&  this.groupFields.get('document').fields.get('multipleSign').control.value === true){
                        event.multipleSign = this.multipleSign;
                        this.openModalEventToSign(event);
                        this._logButton(event);
                        return of( null );
                    }else{
                        if(checkEventType && this.eventsStoreService.needConfirmBeforeSave(event.type.key)){
                            this._openConfirmModal(event.type.key, event)
                            return of( null );
                        }else{  
                            this.openUploadStatusModal();
                            return this.eventsStoreService.save(
                            this.conferenceId,
                            event,
                            true)   
                        }
                    } 
                }
            }

            

        }.bind(this)

        this.extractDataToSubmit = this.eventsStoreService.extractDataToSubmit.bind(
            this.eventsStoreService
        );
        this.saveCompleteFn = this._saveCompleteFn.bind(this);
        this.saveErrorFn = this._saveErrorFn.bind(this);
    }

    private _saveCompleteFn(response: WrapperPostPutData): void {
        if(response === null){
            return;
        }else if (
            this.eventsStoreService.currentEvent.type.key ===
            EventType.CONFERENCE_CLOSING
        ) {
            this.attentionModal.open();
        } else {
            this.eventsStoreService.saveComplete(response);
        }
        this.resetContext();
        this.detailModal.close();
        this.closeUploadStatusModal();
    }

    private _saveErrorFn(error: ErrorMessage): void {
        // TODO: gestire errori
        this.eventsStoreService.saveError(error);
        this.closeUploadStatusModal();
    }

    startResearch(query: QuerySearchEvents) {
        this.searchQuery = query;
    }

    showEventsAction(): boolean {
        return !this.isReadonly();
    }

    isReadonly(): boolean {
        return this.action === ActionForm.READONLY;
    }

    createEvent(type: EventType): void {
        this.detailModalInstance = true;
        this.loggerService.log('createEvent', type);
        this.resetContext();

        if (this.eventsStoreService.needModal(type)) {
            this.currentEventType = type;
            this.footerButtons = true;
            this.groupFields = this.eventsStoreService.getGroupFields(
                this.currentEventType,
                ActionForm.CREATE
            );
            if(this.isMultipleSignEnable()) {    
                this.groupFields.get('document').fields.set('multipleSign', {
                    type: 'switch',
                    label: 'BUTTON.SEND_TO_SIGN',
                    size: '12|12|12',
                    control: new FormControl(this.multipleSign)
                });
            }
            setTimeout(() => {
                this.detailModal.open();
            });
        } else {
            this._openConfirmModal(type);
        }
    }

    _openConfirmModal(type: EventType, event: Event = null): void {

        switch(type){            
            case EventType.EDIT_DATE:
                this.confirmModalTitle =
                    'CONFERENCE.WIZARD.EVENTS.CONFIRM_MODAL.TITLE_EDIT_DATE';
                this.confirmModalText =
                    'CONFERENCE.WIZARD.EVENTS.CONFIRM_MODAL.TEXT_EDIT_DATE';

                this.confirmModalButtons[1].action = (action: ActionItem) => 
                {                    
                    this.confirmModal.close();
                    
                    this.eventsStoreService.save(
                        this.conferenceId,
                        event
                    ).subscribe(
                        (response: WrapperPostPutData) => {
                            this.eventsStoreService.saveComplete(response);
                            this.detailModal.close();
                        },
                        (error: any) => {
                            const errorMex = new ErrorMessage();
                            errorMex.title = 'ERRORE.GENERICO_SERVER';
                            errorMex.message = error.message;
                            this.eventsStoreService.saveError(errorMex);
                            this.detailModal.close()
                        }                        
                    );
                };
            break;

            default:
                this.confirmModalTitle =
                    'CONFERENCE.WIZARD.EVENTS.CONFIRM_MODAL.TITLE_INTEGRATION_CLOSED';
                this.confirmModalText =
                    'CONFERENCE.WIZARD.EVENTS.CONFIRM_MODAL.TEXT_INTEGRATION_CLOSED';
                this.confirmModalButtons[1].action = (action: ActionItem) => {
                    this.eventsStoreService.processCreationEvent(
                        type,
                        this.conferenceId
                    );
                    this.confirmModal.close();
                };
            break;
        }
        
        this.confirmModal.open();
    }

    _openModalError(title: string, message: string): void {
        this.errorModalTitle = title;
        this.errorModalText = message;

        this.errorModalButtons[0].action = (action: ActionItem) => 
        {                    
             this.errorModal.close();   
                this.detailModal.close();      
        };
                
        this.errorModal.open();
    }

    viewEvent(event: Event): void {
        this.detailModalInstance = true;
        this.loggerService.log('viewEvent', event);
        this.resetContext();
        this.currentEventType = event.type.key as EventType;
        setTimeout(() => {
            this.detailModal.open();
        });
        this.eventsStoreService
            .getEventById(this.conferenceId, event.id)
            .pipe(takeUntil(this.destroy$))
            .subscribe((_event: Event) => {
                this.footerButtons = false;
                this.groupFields = this.eventsStoreService.getGroupFields(
                    this.currentEventType,
                    ActionForm.READONLY,
                    _event
                );
            });
    }

    resetContext(): void {
        this.groupFields = undefined;
        this.currentEventType = undefined;
        this.eventsStoreService.resetContext();
    }

    modalOnClose(): void {
        this.detailModalInstance = false;
    }

    checkFormStatus(groupname : string) {
       //setTimeout(() => $("button[id^='Aggiungi.']").closest('div').hide())
       setTimeout(() => $("button[id^='Annulla.']").hide())
    }

    //------new button-------//
    isMultipleSignEnable(): boolean {
        const conferenceType = this.conference.definition.instance.conferenceType.key;
        this.multipleSignEnable = conferenceType == ConferenceType.DOMUS
                || conferenceType == ConferenceType.ENVIRONMENT_INVESTIGATION_VIA
                || conferenceType == ConferenceType.ENVIRONMENT_DEFINITION_VIA
                || conferenceType == ConferenceType.ENVIRONMENT_INVESTIGATION_AIA
                || conferenceType == ConferenceType.ENVIRONMENT_DEFINITION_AIA;

        const eventNoToSign = this.currentEventType == EventType.INTEGRATION_REQUEST 
                || this.currentEventType == EventType.OPINION_EXPRESS
                || this.currentEventType == EventType.CONFERENCE_CLOSING;

        if(eventNoToSign === true)
            this.multipleSignEnable = false;
        if(this.permissionToSign === false)
            this.multipleSignEnable = false;

        return this.multipleSignEnable;
    }

    openModalEventToSign(event: Event) {
      
        this.modalIndictionToSignButtons[1].action = (action: ActionItem) => 
        {   
            if(this.managerCST === undefined || this.managerCST===''){
                this.toastr.warning(
                    this.i18nService.translate(
                        'SIGN.MODAL_SIGN.WARNING_SEND_TO_SIGN.TEXT'
                    ),
                    this.i18nService.translate(
                        'SIGN.MODAL_SIGN.WARNING_SEND_TO_SIGN.TITLE'
                    )
                );
                return;                
            } else{ 
                this.loaderService.showLoading(
                    SectionLoading.MODAL_SIGN
                );
                this.openUploadStatusModal();
                this.eventsStoreService.sendToSignEvent(
                    this.conferenceId,
                    this.managerCST,
                    event,
                    true
                ).subscribe(
                    (response: WrapperPostPutData) => {
                        this.eventsStoreService.saveComplete(response);
                        this.managerCST = '';
                        this.detailModal.close();
                        this.closeUploadStatusModal();
                    },
                    (error: any) => {
                        const errorMex = new ErrorMessage();
                        errorMex.title = 'ERRORE.GENERICO_SERVER';
                        errorMex.message = error.message;
                        this.eventsStoreService.saveError(errorMex);
                        this.loaderService.hideLoading(SectionLoading.EVENTS_MODAL);
                        this.managerCST = '';
                        this.detailModal.close();
                        this.closeUploadStatusModal();
                    }                        
                );
            };

            this.eventModalToSign.close();

        };
        this.managerOptions = of(this.managerSignaturesOptions); 
        //   this.indictionFile = file;
        this.eventModalToSign.open();
        
    }

    closeModalEventToSign() {
        this.eventModalToSign.close();
    }

    get managerSignaturesOptions(): ComboBox[] {
        return this.documentsService.getManagerSignaturesComboBox(this.conferenceId);
    }

    permissionsToSign(){           
         this.documentsService.getPermissionsToSign(this.conferenceId).pipe(                
            catchError(_ => {
                this.permissionToSign = false;
                return of(null);
            })
        )
        .subscribe(res => {
            this.permissionToSign = res;
        });
    }

    selectChangeHandler (event: any) {
        //update the ui
        this.managerCST = event.target.value;
    }

    private _initFileServiceObserver(): void {
        this.eventsStoreService.getUpload()
            .pipe(takeUntil(this.destroy$))
            .subscribe(
                (
                    upload: Map<
                        string,
                        Subject<{ name: string; progress: number }>
                    >
                ) => {
                    upload.forEach(
                        (
                            value: Subject<{ name: string; progress: number }>
                        ) => {
                            if(this.tmpUploads.indexOf(value) < 0)
                                this.tmpUploads.push(value);
                        }
                    );
                }
            );

    }

    resetUploadStatus() {
        this.tmpUploads.pop();
    }
}
