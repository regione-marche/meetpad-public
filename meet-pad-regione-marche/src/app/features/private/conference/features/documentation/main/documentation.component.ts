/* tslint:disable:no-inferrable-types */
import {
    Component,
    ViewChild,
    OnInit,
    Output,
    EventEmitter,
    Input,
    AfterViewInit,
    ChangeDetectorRef
} from '@angular/core';

import { HttpParams } from '@angular/common/http';

import { FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { Observable, of, forkJoin, Subscriber, Subject } from 'rxjs';
import { takeUntil, catchError, mergeMap, filter, map, take } from 'rxjs/operators';

import { ToastrService } from 'ngx-toastr';
import { UploadFile, FileSystemFileEntry } from 'ngx-file-drop';

import {
    ErrorLabelConstants,
    ErrorMessage,
    ActionItem,
    ModalComponent,
    TooltipModel,
    DateModel,
    AbstractTableField,
} from '@eng-ds/ng-toolkit';

import { LoggerService, I18nService, ConfigService } from '@eng-ds/ng-core';

import {
    BaseFile,
    ComboBox,
    LoaderService,
    SectionLoading,
    AutoUnsubscribe,
    FormFieldGroup,
    FormButton,
    FormField,
    FileType,
    FooterButtons,
    WrapperPostPutData,
    WrapperResponse,
    SelectValueValidator,
    FileService,
    OnlyOffice,
    AlertWithLinkComponent,
    UploadStatusComponent
} from '@common';

import { DocumentsService } from '../../../core/services/documents/documents.service';
import { environment } from '@env/environment';

import {
    UtilityService,
    ActionForm,
    ConferenceState,
    StepName,
    Mixin,
    DocumentCategories,
    SignatureStatus,
    UserPortalService,
    EventType,
    ConferenceType

} from '@app/core';

import {
    Documentation,
    AdditionalFile,
    InterectionFile,
    IndictionFile,
    PreliminaryFile,
    ConferenceStoreService,
    ConferencePermissionsService,
    Instance,
    SharedFile,
    FieldDateName, ParticipantsService, Owner
} from '@features/private/conference/core';

import { customConfigurationConf } from '@config';
import { EditModalFileComponent, FilePanelComponent } from '../components';
import { FormStep } from '../../../core/mixins';
import { SignatureFile } from '../../../core/models/documentation/signature-file.model';
import { strictEqual } from 'assert';
import { User } from 'projects/admin-console/src/app/features/users/core/models/user.model';
import { DISABLED } from '@angular/forms/src/model';
import { DebugContext } from '@angular/core/src/view';
import { debug } from 'util';
import { ConditionalExpr } from '@angular/compiler';

declare var $: any;
declare function togglePwdView(): any;

interface GetFileReturn {
    file: File;
    model: PreliminaryFile | AdditionalFile | InterectionFile;
}

@Component({
    selector: 'app-conference-documentation',
    templateUrl: './documentation.component.html',
    styleUrls: ['./documentation.component.scss']
})
@Mixin([FormStep])
export class DocumentationComponent extends AutoUnsubscribe
    implements OnInit, AfterViewInit {
    @ViewChild('fileModal') fileModal: EditModalFileComponent;
    @ViewChild('confirmModal') confirmModal: ModalComponent;
    @ViewChild('deleteListFileConfirmModal') deleteListFileConfirmModal: ModalComponent;
    @ViewChild('confirmDeleteSignatureFileModal') confirmDeleteSignatureFileModal: ModalComponent;
    @ViewChild('downloadSignatureFileModal') downloadSignatureFileModal: ModalComponent;
    @ViewChild('uploadSignatureFileModal') uploadSignatureFileModal: EditModalFileComponent;
    @ViewChild('uploadRemoteSignatureFileModal') uploadRemoteSignatureFileModal: ModalComponent;
    @ViewChild('uploadCalamaioFileModal') uploadCalamaioFileModal: ModalComponent;
    @ViewChild('choiseSignModeModal') choiseSignModeModal: ModalComponent;


    @ViewChild('confirmSharedModal') confirmSharedModal: ModalComponent;
    @ViewChild('confirmSignatureModal') confirmSignatureModal: ModalComponent;
    @ViewChild('indictionModal') indictionModal: ModalComponent;
    @ViewChild('indictionModalToSign') indictionModalToSign: ModalComponent;
    @ViewChild('uploadStatusModal') uploadStatusModal: ModalComponent;
    @ViewChild('downloadStatusModal') downloadStatusModal: ModalComponent;

    @ViewChild('additionalFiles') additionalFiles: FilePanelComponent;
    @ViewChild('interectionFiles') interectionFiles: FilePanelComponent;
    @ViewChild('preliminaryFiles') preliminaryFiles: FilePanelComponent;
    @ViewChild('sharedFiles') sharedFiles: FilePanelComponent;
    @ViewChild('signatureFiles') signatureFiles: FilePanelComponent;

    @Input('summary') summary: boolean = false;
    @Output('openConfirmModal') openConfirmModal = new EventEmitter<
        FooterButtons
    >();

    switchControl: boolean = false;
    switchAllDocumentsVisibility: boolean = false;
    fileName: string = '';
    calamaioStatus : Observable<boolean> = of(false);
    uploadSignatureFileWithCalamaio : boolean = false;
    managerOptions: Observable<ComboBox[]>;
    multipleSignEnable: boolean  = false;
    managerCST: string='';
    flagInstallCalamaio : boolean = false;
    padesCades : boolean =  false;

    tmpUploads: Subject<{ name: string; progress: number }>[] = [];
    tmpDownloads: Subject<{ name: string; progress: number }>[] = [];
    progress: Subject<{ name: string; progress: number }> = new Subject<{ name: string; progress: number }>();
    noFileUploading: string = this.i18nService.translate(
        'COMMON.NO_FILE_UPLOADING'
    );

    saveSharedButtonText: string =
        'CONFERENCE.WIZARD.DOCUMENTATION.SHARED.BUTTON.SAVE';

    saveSignButtonText: string =
        'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.BUTTON.SAVE';

    modalButtons: ActionItem[] = [
        new ActionItem(
            'SEARCH.CONFIRM_DELETE_MODAL.CANCEL_BUTTON',
            (action: ActionItem) => {
                this.confirmModal.close();
            },
            'close'
        ),
        new ActionItem(
            'SEARCH.CONFIRM_DELETE_MODAL.OK_BUTTON',
            (action: ActionItem) => {
                this._deleteFile();
            },
            'trash'
        )
    ];

    deleteListFileModalButtons: ActionItem[] = [
        new ActionItem(
            'SEARCH.CONFIRM_DELETE_MODAL.CANCEL_BUTTON',
            (action: ActionItem) => {
                this.deleteListFileConfirmModal.close();
            },
            'close'
        ),
        new ActionItem(
            'SEARCH.CONFIRM_DELETE_MODAL.OK_BUTTON',
            (action: ActionItem) => {
                this._deleteFileList();
            },
            'trash'
        )
    ];
    
    modalUploadButtons: ActionItem[] = [
        new ActionItem(
            'CONFERENCE.WIZARD.DOCUMENTATION.SHARED.MODAL.CANCEL',
            (action: ActionItem) => {
                this.confirmSharedModal.close();
            },
            'close'
        ),
        new ActionItem(
            'CONFERENCE.WIZARD.DOCUMENTATION.SHARED.MODAL.UPLOAD',
            (action: ActionItem) => {
                this.loaderService.showLoading(
                    SectionLoading.SHARED_FILE_MODAL
                );
                this.openUploadStatusModal();
                this.uploadSharedFile(this.sharedFile, true);
                
            },
            'upload'
        )
    ];

    modalFirstUploadSignatureButtons: ActionItem[] =
    [
        new ActionItem(
            'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.MODAL.CANCEL',
            (action: ActionItem) => {
                this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
                this.confirmSignatureModal.close();
            },
            'close'
        ),
        new ActionItem(
            'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.MODAL.UPLOAD',
            (action: ActionItem) => {
                this.loaderService.showLoading(SectionLoading.SIGNATURE_FILE_MODAL);
                let otp = null;
                if($('#otpForCalamaio').length)
                    otp = $('#otpForCalamaio').val();

                this.uploadSignatureFile(this.signatureFile, otp);
            },
            'upload'
        )
    ];

    modalFirstUploadRemoteSignatureButtons: ActionItem[] =
    [
        new ActionItem(
            'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.MODAL.CANCEL',
            (action: ActionItem) => {
                this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
                this.uploadRemoteSignatureFileModal.close();
            },
            'close'
        ),
        new ActionItem(
            'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.MODAL.SIGN_REMOTE',
            (action: ActionItem) => {
                this.loaderService.showLoading(SectionLoading.SIGNATURE_FILE_MODAL);
                this.uploadRemoteSignatureFile();
            },
            'upload'
        )
    ];

    modalFirstUploadCalamaioSignatureButtons: ActionItem[] =
    [
        new ActionItem(
            'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.MODAL.CANCEL',
            (action: ActionItem) => {
                this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
                this.uploadCalamaioFileModal.close();
            },
            'close'
        ),
        new ActionItem(
            'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.MODAL.CALAMAIO_SIGN_BUTTON',
            (action: ActionItem) => {
                this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
                this.uploadCalamaioSignatureFile($('#dlgCalamaioCadesPades').is(':checked'));
                /*
                
                this.performCalamaioFileSign($('#dlgCalamaioCadesPades').is(':checked')).subscribe( res => {

                    if(res){

                        this.toastr.info(
                            this.i18nService.translate(
                                'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.SUCCESS_SIGN_FILE.TEXT'
                            ),
                            this.i18nService.translate(
                                'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.SUCCESS_SIGN_FILE.TITLE'
                            )
                        );

                    }else{
                        this.toastr.error(
                            this.i18nService.translate(
                                'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.FILE_SIGN_ERROR.TEXT'
                            ),
                            this.i18nService.translate(
                                'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.FILE_SIGN_ERROR.TITLE'
                            )
                        );
                    }
                    
                    this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);

                });

                this.choiseSignModeModal.close();
                */
            },
            'upload'
        )
    ];

    intervalUpdateCalamaio: any;

    get choiceSignModeModalButtons() : ActionItem[] {        

        let result : ActionItem[] =  [
            new ActionItem(
                'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.MODAL.UPLOAD',
                (action: ActionItem) => {
                    this.loaderService.showLoading(
                        SectionLoading.SIGNATURE_FILE_MODAL
                    );
                    this.choiseSignModeModal.close();                  
                    this.uploadSignatureFileModal.openModal();
                    
                },
                'upload'
            ),
            new ActionItem(
                'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.MODAL.SIGN_REMOTE',
                (action: ActionItem) => {
                    //this.loaderService.showLoading(SectionLoading.SIGNATURE_FILE_MODAL);
                    this.choiseSignModeModal.close();
                    this.uploadRemoteSignatureFileModal.open();
                    if (this.fileToSign != null && 
                        this.fileToSign.name != null &&
                        this.fileToSign.name.endsWith("p7m")) {
                        $("#dlgCadesPades").attr("disabled", true);
                        
                    }else{
                       $("#dlgCadesPades").removeAttr("disabled");
                    }
                    
                },
                'pencil'
            )
        ];

        let calamaioSignButton = new ActionItem(
            'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.MODAL.CALAMAIO_SIGN_BUTTON',
            (action: ActionItem) => {
                this.choiseSignModeModal.close();
                //this.uploadCalamaioFileModal.open();
                this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
                this.performCalamaioFileSign("calamaio").subscribe( res => {

                    if(res){

                        this.toastr.info(
                            this.i18nService.translate(
                                'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.SUCCESS_SIGN_FILE.TEXT'
                            ),
                            this.i18nService.translate(
                                'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.SUCCESS_SIGN_FILE.TITLE'
                            )
                        );

                    }else{
                        this.toastr.error(
                            this.i18nService.translate(
                                'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.FILE_SIGN_ERROR.TEXT'
                            ),
                            this.i18nService.translate(
                                'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.FILE_SIGN_ERROR.TITLE'
                            )
                        );
                    }
                    
                    this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);

                });

                this.choiseSignModeModal.close();
            },
            'pencil',
        );
        calamaioSignButton.disabled = !this.calamaioStatus;
        result.push(calamaioSignButton);
        return result;
        
    }

    downloadCalamaioApplication(){

        this.loaderService.showLoading(
            SectionLoading.ALL_CONTENT
        );
        
        this.fileService
        .downloadByApiName(
            'downloadCalamaioApplication'
        )
        //.pipe(takeUntil(this.destroy$))
        .subscribe(() => {
            this.loaderService.hideLoading(
                SectionLoading.ALL_CONTENT
            );
        });

    }

    modalIndictionButtons: ActionItem[] = [
        new ActionItem(
            'CONFERENCE.WIZARD.DOCUMENTATION.INDICTION.INDICTION_MODAL.CANCEL_BUTTON',
            (action: ActionItem) => {
                this.indictionModal.close();
            },
            'close'
        ),
        new ActionItem(
            'CONFERENCE.WIZARD.DOCUMENTATION.INDICTION.INDICTION_MODAL.OK_BUTTON',
            (action: ActionItem) => {
                this.loaderService.showLoading(
                    SectionLoading.INDICTION_FILE_MODAL
                );
                this.uploadIndictionFile(this.indictionFile);
            },
            'check'
        )
    ];

    //-------------new button -------------//
     modalIndictionToSignButtons: ActionItem[] = [
        new ActionItem(
            'SIGN.MODAL_SIGN.CANCEL_BUTTON',
            (action: ActionItem) => {
                this.indictionModalToSign.close();
            },
            'close'
        ),
        new ActionItem(
            'SIGN.MODAL_SIGN.OK_BUTTON',
            (action: ActionItem) => {
                this.loaderService.showLoading(
                    SectionLoading.MODAL_SIGN
                );
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
                }else{

                    this.markToSignFile(this.indictionFile);
                    this.indictionModalToSign.close();
                }
            },
            'check'
        )
    ];
    //-------------------------------------//

    modalDeleteSignatureFileButtons: ActionItem[] = [
        new ActionItem(
            'SEARCH.CONFIRM_DELETE_MODAL.CANCEL_BUTTON',
            (action: ActionItem) => {
                this.confirmDeleteSignatureFileModal.close();
            },
            'close'
        ),
        new ActionItem(
            'SEARCH.CONFIRM_DELETE_MODAL.OK_BUTTON',
            (action: ActionItem) => {
                this._deleteSignatureFile();
            },
            'trash'
        )
    ];

    modalDownloadSignatureButtons : ActionItem[] = [
        new ActionItem(
            'CONFERENCE.WIZARD.DOCUMENTATION.DOWNLOAD_SIGNATURE_FILE_MODAL.CANCEL_BUTTON',
            (action: ActionItem) => {
                this.downloadSignatureFileModal.close();
            },
            'close'
        ),
        new ActionItem(
            'CONFERENCE.WIZARD.DOCUMENTATION.DOWNLOAD_SIGNATURE_FILE_MODAL.DOWNLOAD_BUTTON',
            (action: ActionItem) => {
                this.downloadSignatureFileAndLock();
            },
            'download'
        )
    ]

    idToDelete: string = '';
    fileToDelete: BaseFile;
    fileToSign: SignatureFile;
    fileListToDelete: BaseFile[] = [];

    // loading: false;
    tmpAdditionalFile: AdditionalFile = new AdditionalFile();
    tmpPreliminaryFile: PreliminaryFile = new PreliminaryFile();
    modalFile: BaseFile = new BaseFile();
    modalAdditionalFile: AdditionalFile = new AdditionalFile();
    modalInterectionFile: InterectionFile = new InterectionFile();
    modalIndictionFile: IndictionFile = new IndictionFile();
    modalSharedFile: SharedFile = new SharedFile();
    modalSignatureFile: SignatureFile = new SignatureFile();
    modalPreliminaryFile: PreliminaryFile = new PreliminaryFile();

    optionsCategory: Observable<
        ComboBox[]
    > = this.utilityService.getCategoriesFile(this.conferenceType);
    files: Array<File> = [];

    optionsCategoryForSharedDoc: Observable<
        ComboBox[]
    > = this.utilityService.getCategoriesFileForDocumentType(
        this.conferenceType,
        FileType.SHARED
    );

    groupFieldsAdditional: Map<string, FormFieldGroup> = new Map();
    groupFieldsIndiction: Map<string, FormFieldGroup> = new Map();
    groupFieldsShared: Map<string, FormFieldGroup> = new Map();
    groupFieldsPreliminary: Map<string, FormFieldGroup> = new Map();
    groupFieldsForSwitch: Map<string, FormFieldGroup> = new Map();
    groupFieldsForSign: Map<string, FormFieldGroup> = new Map();

    indictionFile: File;
    sharedFile: File;
    signatureFile: File;
    saveShared: (data: SharedFile) => Observable<WrapperPostPutData>;
    saveSignature: (data: SignatureFile) => Observable<WrapperPostPutData>;
    saveSharedComplete: (response: WrapperPostPutData) => void;
    saveSignatureComplete: (response: WrapperPostPutData) => void;
    saveSharedError: (error: ErrorMessage) => void;
    saveSignatureError: (error: ErrorMessage) => void;
    indictionBaseFile: IndictionFile = new IndictionFile();

    actionClicked: (action: ActionForm) => Observable<ActionForm>;
    editFile: (data: { file: BaseFile }) => Observable<BaseFile>;
    editFileComplete: (response: BaseFile) => void;
    editFileError: (error: ErrorMessage) => void;

    saveAdditionalUrl: (data: AdditionalFile) => Observable<WrapperPostPutData>;
    saveAdditionalUrlComplete: (response: WrapperPostPutData) => void;
    saveAdditionalUrlError: (error: ErrorMessage) => void;
    saveAdditionalUrlButtonText: string =
        'CONFERENCE.WIZARD.DOCUMENTATION.ADDITIONAL.BUTTON.SAVE';

    groupFields: Map<string, FormFieldGroup> = new Map();
    signatureUploadGroupFields: Map<string, FormFieldGroup> = new Map();
    remoteSignatureUploadGroupFields: Map<string, FormFieldGroup> = new Map();
    groupButtons: Map<FooterButtons, FormButton> = new Map();

    inventoryNumberLabel: string = '';
    openPanel: string;

    constructor(
        private loggerService: LoggerService,
        private route: ActivatedRoute,
        private i18nService: I18nService,
        private utilityService: UtilityService,
        private fileService: FileService,
        private documentsService: DocumentsService,
        private loaderService: LoaderService,
        private toastr: ToastrService,
        private conferenceStoreService: ConferenceStoreService,
        private participantsService: ParticipantsService,
        private changeDetectorRef: ChangeDetectorRef,
        private conferencePermissionsService: ConferencePermissionsService,
        private configService: ConfigService,
        private userPortalService: UserPortalService
    ) {
        super();

        if (
            environment.customConfigurationConf
                .documentationInventoryNumberMpTitle
        ) {
            this.inventoryNumberLabel = this.i18nService.translate(
                'CONFERENCE.WIZARD.DOCUMENTATION.INDICTION.FILE.INVENTORY_NUMBER'
            );
        } else {
            this.inventoryNumberLabel = this.i18nService.translate(
                'CONFERENCE.WIZARD.DOCUMENTATION.INDICTION.FILE.INVENTORY_SUAP_NUMBER'
            );
        }
    }

    ngOnInit(): void {
        this._initPermission();
        this._createAdditionalInputs();
        this._createIndictionInputs();
        this._createSharedInputs();
        this._createPreliminaryInputs();
        this._createSwitchFilterInput();
        this._initCallbacks();
        this._createSignInputs();
        this._createUploadSignInputs();
        this._createUploadRemoteSignInputs();
        if (!this.summary) {
            this.conferenceStoreService.hidePageLoader();
        }

       this._initFileServiceObserver();
    }

    
     getPreliminaryTitle() {
        if (customConfigurationConf.documentationPreliminaryTitle) {
            return 'CONFERENCE.WIZARD.DOCUMENTATION.PRELIMINARY.TITLE';
        } else {
            return 'CONFERENCE.WIZARD.DOCUMENTATION.PRELIMINARY.TITLE_SUAP';
        }
    }

    ngAfterViewInit(): void {
        this._setAccordionListener();
        this._checkFragment();
    }

    get menuItems(): string[] {
        return [
            'CONFERENCE.WIZARD.DOCUMENTATION.ADDITIONAL.TITLE',
            'CONFERENCE.WIZARD.DOCUMENTATION.INTERACTIONS.TITLE',
            'CONFERENCE.WIZARD.DOCUMENTATION.SHARED.TITLE',
            'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.TITLE',
            'CONFERENCE.WIZARD.DOCUMENTATION.PRELIMINARY.TITLE',
            'CONFERENCE.WIZARD.DOCUMENTATION.INDICTION.TITLE'
        ];
    }

    // TODO REMOVE
    get loadingFooterButtons(): Observable<boolean> {
        return this.loaderService.getLoading$(
            SectionLoading.CONFERENCE_FOOTER_BUTTON
        );
    }

    get instance(): Instance {
        return this.conferenceStoreService.conference.definition.instance;
    }

    get model(): Documentation {
        return this.conferenceStoreService.conference.documentation;
    }

    get conferenceId(): string {
        return this.conferenceStoreService.conference.id;
    }

    get conferenceType(): string {
        return this.conferenceStoreService.conference.definition.instance
            .conferenceType.key;
    }

    get conferenceState(): ConferenceState {
        return this.conferenceStoreService.conference.state
            .key as ConferenceState;
    }

    get participants(): ComboBox[] {
        return this.conferenceStoreService.conference.getParticipantsComboBox();
    }

    allAccreditedForPartecipants : ComboBox[] = [];
    get accreditedForPartecipants(): ComboBox[] {

        this.allAccreditedForPartecipants = this.participantsService.getAccreditedPerPartecipantsComboBox(this.conferenceId);
        return this.allAccreditedForPartecipants;

    }

    get action(): ActionForm {
        return this.conferenceStoreService.getStepActionForm(
            StepName.DOCUMENTATION
        );
    }

    get loading(): Observable<boolean> {
        return this.fileService.loading;
    }

    get loadingForShared(): Observable<boolean> {
        return this.fileService.loadingForShared;
    }

    get loadingForSignature(): Observable<boolean> {
        return this.fileService.loadingForSignature;
    }

    get endIntegrationDate(): string {
        return this._getDate(FieldDateName.END_INTEGRATION_DATE);
    }

    get endOpinionDate(): string {
        return this._getDate(FieldDateName.END_OPINION_DATE);
    }

    get firstSessionDate(): string {
        return this._getDate(FieldDateName.FIRST_SESSION_DATE);
    }

    get expirationDate(): string {
        return this._getDate(FieldDateName.EXPIRATION_DATE);
    }

    get additional() {
        if (this.model) {
            return this.model.additional;
        }
        return [];
    }

    get interection() {
        if (this.model) {
            return this.model.interection;
        }
        return [];
    }

    get shared() {
        if (this.model) {
            return this.model.shared;
        }
        return [];
    }

    get signature() {
        if (this.model) {
            return this.model.signature;
        }
        return [];
    }

    get preliminary() {
        if (this.model) {
            return this.model.preliminary;
        }
        return [];
    }

    get indiction() {
        if (this.model) {
            return this.model.indiction;
        }
        return [];
    }

    get showConformityAlert(): boolean {
        return (
            this.conferenceStoreService.conference.isBroadband() &&
            this.conferenceStoreService.conference.isIndictionState()
        );
    }

    private _getDate(fieldDateName: FieldDateName): string {
        let returnDate = (this.inventoryNumberLabel = this.i18nService.translate(
            'CONFERENCE.WIZARD.DOCUMENTATION.INDICTION.EMPTY_DATE'
        ));
        if (
            this.instance[fieldDateName] &&
            this.instance[fieldDateName].toString() !== ''
        ) {
            returnDate = this.instance[fieldDateName].toString();
        }
        return returnDate;
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

    onNext(): void {
        return this.conferenceStoreService.submitDocumentation();
    }

    onIndiction(): void {
        this.conferenceStoreService.onIndiction();
    }

    showIndictionPanel(): boolean {
        return this.conferenceState !== ConferenceState.COMPILING;
    }

    isJudgement(): boolean {
        return this.conferenceState == ConferenceState.JUDGMENT;
    }
    showFooterBtn(): boolean {
        return this.action === ActionForm.CREATE;
    }

    isReadonly(): boolean {
        return this.action === ActionForm.READONLY;
    }

    isProtocolHidden() {
        return this.conferenceType == ConferenceType.OPERATIONAL_MEETING
            || this.conferenceType == ConferenceType.DOMUS
            || this.conferenceType == ConferenceType.ENVIRONMENT_INVESTIGATION_VIA
            || this.conferenceType == ConferenceType.ENVIRONMENT_DEFINITION_VIA
            || this.conferenceType == ConferenceType.ENVIRONMENT_INVESTIGATION_AIA
            || this.conferenceType == ConferenceType.ENVIRONMENT_DEFINITION_AIA;
    }

    uploadIndictionFile(file: File): void {
        let newFile = null;
        if(this.isProtocolHidden()) {
            newFile = new IndictionFile({
                name: file.name,
                visibility: [],
                protocolDate: new DateModel(new Date(0)), // "01-01-1970", // woarkaround to skip protocol validation
                inventoryNumber: '',
                fileComplient: this.groupFieldsIndiction.get('indictionPanel').fields.get('fileComplient')? 
                    this.groupFieldsIndiction.get('indictionPanel').fields.get('fileComplient').control.value : false
            });
        }
        else {
            newFile = new IndictionFile({
                name: file.name,
                visibility: [],
                // tmp workaround
                protocolNumber: this.groupFieldsIndiction
                    .get('indictionPanel')
                    .fields.get('protocolNumber').control.value,
                protocolDate: this.groupFieldsIndiction
                    .get('indictionPanel')
                    .fields.get('protocolDate').control.value,
                inventoryNumber: this.groupFieldsIndiction
                    .get('indictionPanel')
                    .fields.get('inventoryNumber').control.value,
                fileComplient: this.groupFieldsIndiction.get('indictionPanel').fields.get('fileComplient')? 
                    this.groupFieldsIndiction.get('indictionPanel').fields.get('fileComplient').control.value : false
            });
        }

        this.fileService.upload(this.conferenceId, newFile, file, newFile.type)
            .pipe(
                takeUntil(this.destroy$),
                catchError(res => {
                    this.loaderService.hideLoading(
                        SectionLoading.INDICTION_FILE_MODAL
                    );
                    this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
                    return of(this._manageToaaserOnUploadError(res));
                })
            )
            .subscribe((res: BaseFile) => {
                if(res == null)
                    return;

                this.model.indiction.push(new IndictionFile(res));
                this.loaderService.hideLoading(
                    SectionLoading.INDICTION_FILE_MODAL
                );
                this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
                this.toastr.info(
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.SUCCESS_SAVE_FILE.TEXT'
                    ),
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.SUCCESS_SAVE_FILE.TITLE'
                    )
                );
                this.onIndiction();
            });
    }

    uploadSharedFile(file: File, enableProgressBar: boolean = false): void {

        const newFile = new SharedFile({
            name: file.name
        });
        const formValue = this.groupFieldsShared.get('sharedPanel').fields;

        newFile.fileComplient = formValue.get('fileComplient')? formValue.get('fileComplient').control.value : false;

        if (formValue.get('visibility').control.value.value) {
            newFile.setVisibility([]);
        } else {
            newFile.setVisibility(formValue.get('visibility').control.value);
        }

        this.fileService.upload(this.conferenceId, newFile, file, newFile.type, enableProgressBar)
            .pipe(
                takeUntil(this.destroy$),
                catchError((res) => {
                    this.loaderService.hideLoading(
                        SectionLoading.SHARED_FILE_MODAL
                    );
                    this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
                    this.confirmSharedModal.close();
                    return of(this._manageToaaserOnUploadError(res));

                })
            )
            .subscribe((res: BaseFile) => {
                if(res == null)
                    return;
                    
                this.model.push(new SharedFile(res));
                this.closeUploadStatusModal();
                this.confirmSharedModal.close();
                this.loaderService.hideLoading(
                    SectionLoading.SHARED_FILE_MODAL
                );
                this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);

                this.toastr.info(
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.SUCCESS_SAVE_FILE.TEXT'
                    ),
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.SUCCESS_SAVE_FILE.TITLE'
                    )
                );

            });
    }

    isCalamaioRemote(): boolean {
        if(!this.groupFieldsForSign || !this.groupFieldsForSign.get('signPanel')) return;

        const formValue = this.groupFieldsForSign.get('signPanel').fields;
        return formValue.get('calamaio_remote') && formValue.get('calamaio_remote').control.value === true? formValue.get('calamaio_remote').control.value:false;
    }

    uploadSignatureFile(file: File, otp: string): void {
        const newFile = new SignatureFile({
            name: file.name
        });
        const formValue = this.groupFieldsForSign.get('signPanel').fields;

        if (formValue.get('visibility').control.value.value) {
            newFile.setVisibility(this.allAccreditedForPartecipants);
        } else {
            newFile.setVisibility(formValue.get('visibility').control.value);
        }

        newFile.fileComplient = formValue.get('fileComplient')? formValue.get('fileComplient').control.value:false;

        if(this.uploadSignatureFileWithCalamaio){
            newFile.signed = false;
            newFile.calamaioSignature = false;
        }

        newFile.remoteSignature = formValue.get('calamaio_remote') && formValue.get('calamaio_remote').control.value === true? formValue.get('calamaio_remote').control.value:false;
        newFile.padesCades = formValue.get('calamaio_pades_cades') && formValue.get('calamaio_pades_cades').control.value === true? formValue.get('calamaio_pades_cades').control.value:false;
        if(newFile.remoteSignature) {
            newFile.calamaioRemoteUsername = formValue.get('calamaio_remote_uid')? formValue.get('calamaio_remote_uid').control.value:'';
            newFile.calamaioRemotePassword = formValue.get('calamaio_remote_pwd')? formValue.get('calamaio_remote_pwd').control.value:'';
            newFile.calamaioRemoteOTP = otp; // formValue.get('calamaio_remote_otp')? formValue.get('calamaio_remote_otp').control.value:'';
            newFile.calamaioRemoteDomain = formValue.get('calamaio_remote_domain')? formValue.get('calamaio_remote_domain').control.value:'';

            if(!newFile.calamaioRemoteUsername || !newFile.calamaioRemotePassword || !newFile.calamaioRemoteOTP) {
                this.toastr.info(
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.TOOLTIP.CALAMAIO_REMOTE.ERROR'
                    ),
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.TOOLTIP.CALAMAIO_REMOTE.TITLE'
                    )
                );
                this.confirmSignatureModal.close();
                this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
                return;
            }
        }        

        this.fileService.upload(this.conferenceId, newFile, file, newFile.type)
            .pipe(
                takeUntil(this.destroy$),
                catchError(res => {
                    this.loaderService.hideLoading(
                        SectionLoading.SIGNATURE_FILE_MODAL
                    );
                    this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
                    this.confirmSignatureModal.close();
                   
                    if(res.body.code === "422"){

                        res.body.errors.forEach( err => {
                            this.toastr.error(
                                err.msg,
                                this.i18nService.translate(
                                    'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.ERROR_SAVE_FILE.TITLE'
                                )
                            );
                        })
                        
                    }else{
                        this.toastr.error(
                            this.i18nService.translate(
                                'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.ERROR_SAVE_FILE.TEXT'
                            ),
                            this.i18nService.translate(
                                'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.ERROR_SAVE_FILE.TITLE'
                            )
                        );
                    }

                    
                    this.confirmSignatureModal.close();
                    return of();
                })
            )
            .subscribe((res: any) => {

                if(!res || res.errors) {
                    this.loaderService.hideLoading(SectionLoading.SIGNATURE_FILE_MODAL);
                    this.confirmSignatureModal.close();
                    this.toastr.info(
                        !res.errors || !res.errors.length?this.i18nService.translate('CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.ERROR_REMOTE_SIGNING_FILE.TEXT'):res.errors[0].msg,
                        this.i18nService.translate('CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.ERROR_REMOTE_SIGNING_FILE.TITLE')
                    );
                }
                else {
                    this.fileToSign = new SignatureFile(res);
                    if(this.uploadSignatureFileWithCalamaio){
                        this.model.push(this.fileToSign);
                        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
                        this.confirmSignatureModal.close();
                        
                        this.performCalamaioSign().subscribe( res => {
    
                            if(res){
        
                                this.toastr.info(
                                    this.i18nService.translate(
                                        'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.SUCCESS_SIGN_FILE.TEXT'
                                    ),
                                    this.i18nService.translate(
                                        'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.SUCCESS_SIGN_FILE.TITLE'
                                    )
                                );
    
                                this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
        
                            }else{
                                this.toastr.error(
                                    this.i18nService.translate(
                                        'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.FILE_SIGN_ERROR.TEXT'
                                    ),
                                    this.i18nService.translate(
                                        'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.FILE_SIGN_ERROR.TITLE'
                                    )
                                );
                                this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
                            }
                        });                    
    
                    }else{
                        //try{this.model.delete(this.fileToSign); } catch(e) {}
                        this.model.push(this.fileToSign); //// xmf TODO: refresh list instead of add
                        this.confirmSignatureModal.close();
                        this.loaderService.hideLoading(SectionLoading.SIGNATURE_FILE_MODAL);
    
                        this.toastr.info(
                            this.i18nService.translate(
                                'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.SUCCESS_SAVE_FILE.TEXT'
                            ),
                            this.i18nService.translate(
                                'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.SUCCESS_SAVE_FILE.TITLE'
                            )
                        );
                    }
                }

            });
    }

    uploadRemoteSignatureFile(): void {
        const newFile = new SignatureFile({
            name: ''
        });
        newFile.remoteSignature = true;
        newFile.calamaioRemoteUsername = $('#dlgRemoteCalamaioUID').val();
        newFile.calamaioRemotePassword = $('#dlgRemoteCalamaioPwd').val();
        newFile.calamaioRemoteOTP = $('#dlgRemoteCalamaioOTP').val();
        newFile.calamaioRemoteDomain = $('#dlgRemoteCalamaioDomain').val();
        newFile.calamaioRemoteDocumentId = ''+this.fileToSign.id;
        newFile.padesCades = $('#dlgCadesPades').is(':checked');

        if(!newFile.calamaioRemoteUsername || !newFile.calamaioRemotePassword || !newFile.calamaioRemoteOTP) {
            this.toastr.info(
                this.i18nService.translate('CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.TOOLTIP.CALAMAIO_REMOTE.ERROR'),
                this.i18nService.translate('CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.TOOLTIP.CALAMAIO_REMOTE.TITLE')
            );
            this.uploadRemoteSignatureFileModal.close();
            this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
            return;
        }

        const formValue = this.groupFieldsForSign.get('signPanel').fields;
        if (formValue.get('visibility').control.value.value)
            newFile.setVisibility(this.allAccreditedForPartecipants);
        else
            newFile.setVisibility(formValue.get('visibility').control.value);

        newFile.fileComplient = formValue.get('fileComplient')? formValue.get('fileComplient').control.value:false;
        if(this.uploadSignatureFileWithCalamaio) {
            newFile.signed = false;
            newFile.calamaioSignature = true;
        }
            

        this.fileService.upload(this.conferenceId, newFile, null, newFile.type)
            .pipe(
                takeUntil(this.destroy$),
                catchError(res => {
                    this.loaderService.hideLoading(SectionLoading.SIGNATURE_FILE_MODAL);
                    this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
                    this.uploadRemoteSignatureFileModal.close();
                   
                    if(res.body.code === "422"){

                        res.body.errors.forEach( err => {
                            this.toastr.error(
                                err.msg,
                                this.i18nService.translate(
                                    'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.ERROR_SAVE_FILE.TITLE'
                                )
                            );
                        })
                        
                    }else{
                        this.toastr.error(
                            this.i18nService.translate(
                                'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.ERROR_SAVE_FILE.TEXT'
                            ),
                            this.i18nService.translate(
                                'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.ERROR_SAVE_FILE.TITLE'
                            )
                        );
                    }

                    
                    this.uploadRemoteSignatureFileModal.close();
                    return of();
                })
            )
            .subscribe((res: any) => {

                if(!res || res.errors) {
                    this.loaderService.hideLoading(SectionLoading.SIGNATURE_FILE_MODAL);
                    this.uploadRemoteSignatureFileModal.close();
                    this.toastr.info(
                        !res.errors || !res.errors.length?this.i18nService.translate('CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.ERROR_REMOTE_SIGNING_FILE.TEXT'):res.errors[0].msg,
                        this.i18nService.translate('CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.ERROR_REMOTE_SIGNING_FILE.TITLE')
                    );
                }
                else {
                    this.fileToSign = new SignatureFile(res);
                    if(this.uploadSignatureFileWithCalamaio){
                        this.model.push(this.fileToSign);
                        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
                        this.uploadRemoteSignatureFileModal.close();
                        
                        this.performCalamaioSign().subscribe( res => {
    
                            if(res){
        
                                this.toastr.info(
                                    this.i18nService.translate(
                                        'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.SUCCESS_SIGN_FILE.TEXT'
                                    ),
                                    this.i18nService.translate(
                                        'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.SUCCESS_SIGN_FILE.TITLE'
                                    )
                                );
    
                                this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
        
                            }else{
                                this.toastr.error(
                                    this.i18nService.translate(
                                        'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.FILE_SIGN_ERROR.TEXT'
                                    ),
                                    this.i18nService.translate(
                                        'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.FILE_SIGN_ERROR.TITLE'
                                    )
                                );
                                this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
                            }
                        });                    
    
                    }else{
                        try{this.model.delete(this.fileToSign); } catch(e) {}
                        this.model.push(this.fileToSign); //// xmf TODO: refresh list instead of add
                        this.uploadRemoteSignatureFileModal.close();
                        this.loaderService.hideLoading(SectionLoading.SIGNATURE_FILE_MODAL);
    
                        this.toastr.info(
                            this.i18nService.translate(
                                'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.SUCCESS_SAVE_FILE.TEXT'
                            ),
                            this.i18nService.translate(
                                'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.SUCCESS_SAVE_FILE.TITLE'
                            )
                        );
                    }
                }

            });
    }

    uploadCalamaioSignatureFile(padesCades: boolean): void {
        
        this.performCalamaioFileSign(null).subscribe( res => {

            if(res){

                this.toastr.info(
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.SUCCESS_SIGN_FILE.TEXT'
                    ),
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.SUCCESS_SIGN_FILE.TITLE'
                    )
                );

            }else{
                this.toastr.error(
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.FILE_SIGN_ERROR.TEXT'
                    ),
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.FILE_SIGN_ERROR.TITLE'
                    )
                );
            }
            
            this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);

        });

        this.uploadCalamaioFileModal.close();
       
    }




    // evento emesso dalla tabella
    // crea un clone e passa il corrispondente file
    // della riga selezionata
    openEditFile(
        file:
            | PreliminaryFile
            | AdditionalFile
            | InterectionFile
            | IndictionFile
            | SharedFile
    ): void {
        this.modalFile = file;
        this.fileName = file.name;
        switch (this.modalFile.type) {
            case FileType.ADDITIONAL:
                this.modalAdditionalFile.visibility = (file as AdditionalFile).visibility;
                this.modalAdditionalFile.category = (file as AdditionalFile).category;
                this.modalAdditionalFile.cityReference = file.cityReference;
                this._createAdditionalModalGroupsFields();
                break;
            case FileType.PRELIMINARY:
                this.modalPreliminaryFile.visibility = file.visibility;
                this.modalPreliminaryFile.category = (file as PreliminaryFile).category;
                this.modalPreliminaryFile.cityReference = file.cityReference;
                this._createPreliminaryModalGroupsFields();
                break;
            case FileType.INTERETION:
                this.modalInterectionFile.visibility = file.visibility;
                this._createInterationModalGroupsFields();
                break;
            case FileType.INDICTION:
                if(!this.isProtocolHidden()) {
                    this.modalIndictionFile.protocolDate = (file as IndictionFile).protocolDate;
                    this.modalIndictionFile.protocolNumber = (file as IndictionFile).protocolNumber;
                    this.modalIndictionFile.inventoryNumber = (file as IndictionFile).inventoryNumber;
                }
                this.modalIndictionFile.visibility = (file as IndictionFile).visibility;
                this.modalIndictionFile.statusDocument = (file as IndictionFile).statusDocument;
                this._createIndictionModalGroupsFields();
                break;
            case FileType.SHARED:
                this.modalSharedFile.name = (file as SharedFile).name;
                this.modalSharedFile.visibility = (file as SharedFile).visibility;
                this.modalSharedFile.url = (file as SharedFile).url;
                this._createSharedModalGroupsFields();
                break;
        }
        this.fileModal.openModal();
    }

    // open confirm modal
    deleteSignatureFile(file: BaseFile): void {
        this.idToDelete = file.id;
        this.fileToDelete = file;
        this.confirmDeleteSignatureFileModal.open();
    }

    unlockSignatureFile(file: SignatureFile): void {
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
        this.fileToSign = file;
        this.documentsService
        .unlockSignatureFile(file.id)
        .pipe(                
            catchError(res => {

                    if(res.body.code === "422"){

                        res.body.errors.forEach( err => {
                            this.toastr.error(
                                err.msg,
                                this.i18nService.translate(
                                    'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.FILE_UNLOCK_ERROR.TITLE'
                                )
                            );
                        })
                        
                    }else{
                        this.toastr.error(
                            this.i18nService.translate(
                                'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.FILE_UNLOCK_ERROR.TEXT'
                            ),
                            this.i18nService.translate(
                                'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.FILE_UNLOCK_ERROR.TITLE'
                            )
                        );
                    }

                    return of(null);
                })
            )
            .subscribe(signatureFile => {
                this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
                if(signatureFile != null){
                    this.fileToSign.status = signatureFile.status;
                    this.fileToSign.owner = signatureFile.owner;
                }                
                return of(null)
            });
    }

    checkCalamaioStatus(): Observable<any> {
        // hide calamaio download when firma panel opens
        setTimeout(function() {

            $("eng-input[data-test-id^='CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.FILE.CALAMAIO_']").hide();

            $("input[id^='CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.FILE.CALAMAIO_DOMAIN.']").val("frRegioneMarche");
            var pwd = $("input[id^='CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.FILE.CALAMAIO_PWD.']");
            pwd.attr("type", "password");
            pwd.val("");

            pwd.parent().append('<span id="passwordtooglefield" class="fa fa-fw fa-eye field-icon toggle-password" style="float: right;margin-left: -25px;margin-top: -25px;position: relative;z-index: 2;" onclick="togglePwdView()"></span>')
        }, 100);

        const data$ = new Subject<any>();
        this.documentsService.getCalamaioStatus().pipe(                
            catchError(res => {
                if(res.status === 200){
                    return of(true);
                }else{
                    return of(false);
                }
                
            })
        ).subscribe(res => {
            this.calamaioStatus = of(res);
            data$.next(res);
            data$.complete();  
        }) 

        return data$.asObservable();
    }
    
    // open confirm modal
    openSignatureFileModal(file: SignatureFile): void {
        this.fileToSign = file;
        if(file.status === SignatureStatus.UNLOCKED){
            this.downloadSignatureFileModal.open();
        }else{
            this.checkCalamaioStatus().subscribe(res => {
                this.choiseSignModeModal.open();
            })   
        }        
    }

    performCalamaioSign(): Observable<any> {

        const data$ = new Subject<any>();
        //1. getCalamaioFillxml
        const formValue = this.groupFieldsForSign.get('signPanel').fields;
        this.padesCades = formValue.get('calamaio_pades_cades') && formValue.get('calamaio_pades_cades').control.value === true? formValue.get('calamaio_pades_cades').control.value:false;
        this.documentsService.getCalamaioFillxml(this.fileToSign.id, this.padesCades, null, this.fileToSign.name)
        .pipe(                
            catchError(_ => {
                return of(null);
            })
        )
        .subscribe(xmlFillResponse => {
            
            //2. postCalamaioSign
            if(xmlFillResponse != null){

                this.documentsService.postCalamaioSign(xmlFillResponse.callbackbody).pipe(                
                    catchError(_ => {
                        return of(null);
                    })
                )
                .subscribe(signResult => {
    
                    //3. unlockSignatureFileWithCallabck
                    if(signResult != null){
                        this.documentsService.unlockSignatureFileWithCallabck(this.fileToSign.id, signResult,this.padesCades,"calamaio").pipe(                
                            catchError(_ => {
                                return of(null);
                            })
                        )
                        .subscribe(signatureFile => {
                            
                            if(signatureFile != null){
                                this.fileToSign.status = signatureFile.status;
                                this.fileToSign.owner = signatureFile.owner;
    
                                data$.next(true);
                                data$.complete(); 
                            }
                                
                            data$.next(false);
                            data$.complete(); 
                                        
                        });
    
                    }else{
                        data$.next(false);
                        data$.complete(); 
                    }
                                        
                });

            }else{
                data$.next(false);
                data$.complete();  
            }
            
        });
        
        return data$.asObservable();
    }

    performCalamaioFileSign(calamaioRemota : string): Observable<any> {

        const data$ = new Subject<any>();
        //1. getCalamaioFillxml
        this.documentsService.getCalamaioFillxml(this.fileToSign.id, null, calamaioRemota, this.fileToSign.name)
        .pipe(                
            catchError(_ => {           
                return of(null);
            })
        )
        .subscribe(xmlFillResponse => {
            
            //2. postCalamaioSign
            if(xmlFillResponse != null){   
                this.documentsService.postCalamaioSign(xmlFillResponse.callbackbody).pipe(                
                    catchError(_ => {                      
                        return of(null);
                    })
                    
                )
                .subscribe(signResult => {
    
                    //3. unlockSignatureFileWithCallabck
                    if(signResult != null){
                        this.documentsService.unlockSignatureFileWithCallabck(this.fileToSign.id, signResult,null,calamaioRemota).pipe(                
                            catchError(_ => {                 
                                return of(null);
                            })
                        )
                        .subscribe(signatureFile => {
                            if(signatureFile != null){
                                this.fileToSign.status = signatureFile.status;
                                this.fileToSign.owner = signatureFile.owner;
    
                                data$.next(true);
                                data$.complete(); 
                            } 
                                
                            data$.next(false);
                            data$.complete(); 
                                        
                        });
    
                    }else{
                        data$.next(false);
                        data$.complete(); 
                    }
                                        
                });

            }else{
                data$.next(false);
                data$.complete();  
            }
            
        });
        
        return data$.asObservable();
    }

    getTokenByUrl(url: string){
        let regEx = '.*downloadFile\/(.*)';
        return url.match(regEx)[1];
    }

    downloadSignatureFileAndLock(): void {
        
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
        this.fileService.downloadByApiName('lockAndDownload',{token: this.getTokenByUrl(this.fileToSign.url)},
                                        null,false,true
            ).pipe(                
                catchError(_ => {
                    this.toastr.error(
                        this.i18nService.translate(
                            'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.FILE_DOWNLOAD_ERROR.TEXT'
                        ),
                        this.i18nService.translate(
                            'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.FILE_DOWNLOAD_ERROR.TITLE'
                        )
                    );
                    return of(null);
                })
            )
            .subscribe(res => {
                if(res != null && res.body != null && res.body.type === "application/json"){
                    this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
                    this.downloadSignatureFileModal.close();
                    return of(null);
                }else{
                    this.fileToSign.status = SignatureStatus.SIGNING;

                    this.userPortalService.getUser(true)
                    .subscribe( user => {
                        let owner = new Owner({"name": user.name, "surname":user.lastname});
                        this.fileToSign.owner = owner;

                        this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
                        this.downloadSignatureFileModal.close();
                        return of(null);

                    })                 
                }                

            });
    }

    // open confirm modal
    deleteFile(file: BaseFile): void {
        this.idToDelete = file.id;
        this.fileToDelete = file;
        this.confirmModal.open();
    }

    downloadFile(file: BaseFile): void {
        // window.open(item.url, '_blank').focus();
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
        this.openDownloadStatusModal();
        this.fileService
            .download(file, null, true)
            .pipe(
                catchError(_ => {
                    this.toastr.error(
                        this.i18nService.translate(
                            'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.FILE_DOWNLOAD_ERROR.TEXT'
                        ),
                        this.i18nService.translate(
                            'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.FILE_DOWNLOAD_ERROR.TITLE'
                        )
                    );
                    this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
                    this.closeDownloadStatusModal(); 
                    return of(null);
                })
            )
            .subscribe(res => {
                
                if((res && this.fileService.isUploadCompleted )){
                    this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
                    this.closeDownloadStatusModal();
                }          
            });
    }

    openEditCloud(file: BaseFile): void {
        const token = file.url.substring(file.url.lastIndexOf('/') + 1);
        this.fileService
            .getOOData(token)
            .pipe(
                takeUntil(this.destroy$),
                catchError(() => {
                    this.toastr.error(
                        this.i18nService.translate(
                            'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.OO_ERROR.TEXT'
                        ),
                        this.i18nService.translate(
                            'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.OO_ERROR.TITLE'
                        )
                    );
                    return of();
                })
            )
            .subscribe((data: OnlyOffice) => {
                // const _data = {
                //     document: {
                //         fileType: 'docx',
                //         title: '1.docx',
                //         url:
                //             'http://10.101.11.135:8080/scritturacondivisa/1.docx'
                //     },
                //     permissions: {
                //         edit: 'true',
                //         download: 'true',
                //         review: 'edit'
                //     },
                //     documentType: 'text',
                //     editorConfig: {
                //         callbackUrl: 'https://example.com/url-to-callback.ashx'
                //     }
                // };
                if (data) {
                    window.open(
                        './oo.html?data=' + btoa(JSON.stringify(data)),
                        '_blank'
                    );
                }
            });
    }

    openIndictionModal(file: File) {
        if(this.groupFieldsIndiction.get('indictionPanel').fields.get('multipleSign') !== undefined && this.groupFieldsIndiction.get('indictionPanel').fields.get('multipleSign').control.value === true)
            this.openIndictionModalToSign(file);
        else{
            this.indictionFile = file;
            this.indictionModal.open();
        }
    }

    openSharedModal(file: File) {
        this.sharedFile = file;
        this.confirmSharedModal.open();
    }

    openSignatureModal(file: File) {
        this.signatureFile = file;
        this.confirmSignatureModal.open();
    }

    openUploadStatusModal() {
        this.tmpUploads = [];
        this.uploadStatusModal.open();
    }

    closeUploadStatusModal() {
        this.resetUploadStatus();
        this.uploadStatusModal.close();
    }

    openDownloadStatusModal() {
        this.tmpDownloads = [];
        this.downloadStatusModal.open();
    }

    closeDownloadStatusModal() {
        this.resetDownloadStatus();
        this.downloadStatusModal.close();
    }

    closeIndictionModal() {
        this.indictionModal.close();
    }

    //--------- new button -------//
    openIndictionModalToSign(file: File) {
        if(file.name === undefined ){
            this.toastr.warning(
                this.i18nService.translate(
                    'SIGN.CONFIRM_REJECT_LIST_MODAL.WARNING_REJECT_LIST_FILE.TEXT'
                ),
                this.i18nService.translate(
                    'SIGN.CONFIRM_REJECT_LIST_MODAL.WARNING_REJECT_LIST_FILE.TITLE'
                )
            );
            return;                
        }else{
            this.managerOptions = of(this.managerSignaturesOptions); 
            this.indictionFile = file;
            this.indictionModalToSign.open();
        }
    }

    closeIndictionModalToSign() {
        this.indictionModalToSign.close();
    }

    get managerSignaturesOptions(): ComboBox[] {
        return this.documentsService.getManagerSignaturesComboBox(this.conferenceId);
    }

    //----------------------------//
    private _checkFragment(): void {
        this.route.fragment
            .pipe(
                filter(
                    fragment =>
                        !!fragment && fragment === 'panelGroupDocumentation'
                ),
                takeUntil(this.destroy$)
            )
            .subscribe(() => {
                this._openAccordion('collapseDocumentation-4');
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

    private _onNext(): void {
        return this.onNext();
    }

    postSharedFile(): Observable<any> {
        const formValue = this.groupFieldsShared.get('sharedPanel').fields;
        const dataFile: any = {
            model: formValue.get('model')
                ? formValue.get('model').control.value
                : null,

            category:
                formValue.get('mode').control.value.key === 'dashboard'
                    ? environment.defaultComboBox.conference.documentation
                          .sharedDashboardCategory
                    : undefined
        };
        if (formValue.get('mode').control.value.key === 'dashboard') {
            dataFile.name = formValue.get('mode').control.value.value;
        } else {
            dataFile.name = formValue.get('model')
                ? formValue.get('model').control.value.value
                : formValue.get('name').control.value;
        }
        const newFile = new SharedFile(dataFile);

        if (formValue.get('visibility').control.value.value) {
            newFile.setVisibility([]);
        } else {
            newFile.setVisibility(formValue.get('visibility').control.value);
        }

        return this.fileService.postModelUrlFile(
            this.conferenceId,
            newFile,
            newFile.type
        );
    }

    private _postSharedCompleted(response: WrapperResponse<any>): void {
        const addResponse = response as any;

        if (addResponse.id) {
            this.model.push(new SharedFile(addResponse));
            this.sharedFiles.hideForm();
            this.toastr.info(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.SUCCESS_SAVE_FILE.TEXT'
                ),
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.SUCCESS_SAVE_FILE.TITLE'
                )
            );
        }
        if (response.code) {
            if (response.code === '404') {
                this.toastr.error(
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.ERROR_SAVE_FILE.TEXT'
                    ),
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.ERROR_SAVE_FILE.TITLE'
                    )
                );
            }
        }
    }

    private _postSharedError(error: ErrorMessage): void {
        this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
        this.toastr.error(
            this.i18nService.translate(
                'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.ERROR_SAVE_FILE.TEXT'
            ),
            this.i18nService.translate(
                'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.ERROR_SAVE_FILE.TITLE'
            )
        );
    }

    postAdditionalUrlFile(): Observable<any> {
        const formValue = this.groupFieldsAdditional.get('additionalPanel')
            .fields;
        const newFile = new AdditionalFile({
            url: formValue.get('url')? formValue.get('url').control.value : null,
            name: formValue.get('name').control.value,
            cityReference: formValue.get('cityReference')
                ? formValue.get('cityReference').control.value
                : null,
            fileComplient: formValue.get('fileComplient')? formValue.get('fileComplient').control.value : false
        });

        newFile.setCategory(formValue.get('category').control.value);

        return this.fileService.postModelUrlFile(
            this.conferenceId,
            newFile,
            newFile.type
        );
    }

    private _postAdditionalUrlCompleted(response: WrapperResponse<any>): void {
        const addResponse = response as any;

        if (addResponse.id) {
            this.model.push(new AdditionalFile(addResponse));
            this.toastr.info(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.SUCCESS_SAVE_FILE.TEXT'
                ),
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.SUCCESS_SAVE_FILE.TITLE'
                )
            );
        }
        if (response.code) {
            if (response.code === '404') {
                this.toastr.error(
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.ERROR_SAVE_FILE.TEXT'
                    ),
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.ERROR_SAVE_FILE.TITLE'
                    )
                );
            }
        }
    }

    private _postAdditionalUrlError(error: ErrorMessage): void {
        this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
        this.toastr.error(
            this.i18nService.translate(
                'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.ERROR_SAVE_FILE.TEXT'
            ),
            this.i18nService.translate(
                'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.ERROR_SAVE_FILE.TITLE'
            )
        );
    }

    private _initCallbacks(): void {
        this.editFile = this._editFile.bind(this);
        this.editFileComplete = this._editFileComplete.bind(this);
        this.editFileError = this._editFileError.bind(this);

        this.saveSharedComplete = this._postSharedCompleted.bind(this);
        this.saveSharedError = this._postSharedError.bind(this);

        this.saveAdditionalUrlComplete = this._postAdditionalUrlCompleted.bind(
            this
        );
        this.saveAdditionalUrlError = this._postAdditionalUrlError.bind(this);
    }

    private _editFileComplete(response: BaseFile): void {
        this.fileModal.close();
        this._editFileCompleted(response);
    }

    private _editFile(data: { file: BaseFile }): Observable<BaseFile> {
        const { file } = data;

        this.modalFile = Object.assign(this.modalFile, file);

        this.loggerService.log('_saveFile', this.modalFile);

        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
        return this.fileService
            .edit(this.modalFile, this.modalFile.type)
            .pipe(takeUntil(this.destroy$));
    }

    private _editFileCompleted(file: BaseFile): void {
        this.model.update(this.modalFile);
        this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
        this.toastr.info(
            this.i18nService.translate(
                'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.SUCCESS_SAVE_FILE.TEXT'
            ),
            this.i18nService.translate(
                'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.SUCCESS_SAVE_FILE.TITLE'
            )
        );
    }

    private _editFileError(error: ErrorMessage): void {
        this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
        this.toastr.error(
            this.i18nService.translate(
                'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.ERROR_SAVE_FILE.TEXT'
            ),
            this.i18nService.translate(
                'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.ERROR_SAVE_FILE.TITLE'
            )
        );
    }

    private _createSwitchFilterInput(): void {
        this.groupFieldsForSwitch = new Map();
        this.groupFieldsForSwitch.set('switchPanel', {
            panel: false,
            panelHead: null,
            fields: new Map().set('switchDocumentVisibility', {
                type: 'switch',
                label: 'CONFERENCE.WIZARD.DOCUMENTATION.SWITCH_LABEL',
                control: new FormControl({
                    value: false,
                    disabled: false
                }),
                valueChange: (val: boolean): void => {
                    this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
                    this.switchAllDocumentsVisibility = val;
                    this.conferenceStoreService
                        .updateDocuments(this.conferenceId, val)
                        .subscribe(() => {
                            this.loaderService.hideLoading(
                                SectionLoading.ALL_CONTENT,
                                300
                            );
                        });
                },
                size: '12|6|6'
            })
        });
    }

    private _createAdditionalModalGroupsFields(): void {
        // work arround for angular change detection
        this.groupFields = new Map();
        this.groupFields.set('file', {
            panel: false,
            panelHead: null,
            fields: new Map()
                .set('category', {
                    type: 'select',
                    label:
                        'CONFERENCE.WIZARD.DOCUMENTATION.ADDITIONAL.FILE.CATEGORY',
                    control: new FormControl(
                        {
                            value: this.modalAdditionalFile.category,
                            disabled: this.fileModal.disabled
                        },
                        [Validators.required]
                    ),
                    size: '12|12|12',
                    options: this.optionsCategory,
                    errorLabels: [ErrorLabelConstants.REQUIRED],
                    valueChange: (value: ComboBox) => {
                        this.groupFields = this._setCityReferenceFieldsOnModal(
                            this.groupFields,
                            value,
                            'file',
                            '_setCityReferenceFieldsOnAdditionalModal'
                        );
                    }
                })
                .set('visibility', {
                    type: 'select-two',
                    label:
                        'CONFERENCE.WIZARD.DOCUMENTATION.ADDITIONAL.FILE.PARTICIPANT',
                    control: new FormControl({
                        value: this.modalAdditionalFile.visibility,
                        disabled: this.fileModal.disabled
                    }),
                    tooltip:
                        !this.isReadonly() &&
                        new TooltipModel(
                            'CONFERENCE.WIZARD.DOCUMENTATION.ADDITIONAL.MODAL.PARTICIPANT.TOOLTIP.CONTENT',
                            undefined,
                            'CONFERENCE.WIZARD.DOCUMENTATION.ADDITIONAL.MODAL.PARTICIPANT.TOOLTIP.TITLE'
                        ),
                    placeholder: 'COMMON.ALL',
                    size: '12|12|12',
                    options: of(this.participants)
                })
        });
        this.groupFields = this._setCityReferenceFieldsOnModal(
            this.groupFields,
            this.groupFields.get('file').fields.get('category').control.value,
            'file',
            '_setCityReferenceFieldsOnAdditionalModal'
        );
    }

    private _createInterationModalGroupsFields(): void {
        // work arround for angular change detection
        this.groupFields = new Map();
        this.groupFields.set('file', {
            panel: false,
            panelHead: null,
            fields: new Map().set('visibility', {
                type: 'select-two',
                label:
                    'CONFERENCE.WIZARD.DOCUMENTATION.ADDITIONAL.FILE.PARTICIPANT',
                control: new FormControl({
                    value: this.modalInterectionFile.visibility,
                    disabled: true
                }),
                tooltip:
                    !this.isReadonly() &&
                    new TooltipModel(
                        'CONFERENCE.WIZARD.DOCUMENTATION.ADDITIONAL.MODAL.PARTICIPANT.TOOLTIP.CONTENT',
                        undefined,
                        'CONFERENCE.WIZARD.DOCUMENTATION.ADDITIONAL.MODAL.PARTICIPANT.TOOLTIP.TITLE'
                    ),
                placeholder: 'COMMON.ALL',
                size: '12|12|12'
            })
        });
    }

    private _createIndictionModalGroupsFields(): void {
        let inventoryNumber = '';
        if (this.modalIndictionFile.inventoryNumber !== 'undefined') {
            inventoryNumber = this.modalIndictionFile.inventoryNumber;
        }
        this.groupFields = new Map();

        let fieldMap = new Map();
        if(!this.isProtocolHidden()) {
            fieldMap
                .set('protocolNumber', {
                    type: 'text',
                    label:
                        'CONFERENCE.WIZARD.DOCUMENTATION.INDICTION.FILE.PROTOCOL_NUMBER',
                    control: new FormControl({
                        value: this.modalIndictionFile.protocolNumber,
                        disabled: this.fileModal.disabled
                    }),
                    size: '12|6|6',
                    errorLabels: [ErrorLabelConstants.REQUIRED]
                })
                .set('protocolDate', {
                    type: 'date',
                    label:
                        'CONFERENCE.WIZARD.DOCUMENTATION.INDICTION.FILE.PROTOCOL_DATE',
                    control: new FormControl({
                        value: this.modalIndictionFile.protocolDate,
                        disabled: this.fileModal.disabled
                    }),
                    size: '12|6|6',
                    errorLabels: [ErrorLabelConstants.REQUIRED]
                })
                .set('inventoryNumber', {
                    type: 'text',
                    label: this.inventoryNumberLabel,
                    control: new FormControl({
                        value: inventoryNumber,
                        disabled: this.fileModal.disabled
                    }),
                    size: '12|6|6'
                });
        }
        
        fieldMap
                .set('visibility', {
                    type: 'select-two',
                    label:
                        'CONFERENCE.WIZARD.DOCUMENTATION.ADDITIONAL.FILE.PARTICIPANT',
                    control: new FormControl({
                        value: this.modalIndictionFile.visibility,
                        disabled: this.fileModal.disabled
                    }),
                    tooltip:
                        !this.isReadonly() &&
                        new TooltipModel(
                            'CONFERENCE.WIZARD.DOCUMENTATION.ADDITIONAL.MODAL.PARTICIPANT.TOOLTIP.CONTENT',
                            undefined,
                            'CONFERENCE.WIZARD.DOCUMENTATION.ADDITIONAL.MODAL.PARTICIPANT.TOOLTIP.TITLE'
                        ),
                    placeholder: 'COMMON.ALL',
                    size: '12|12|12',
                    options: of(this.participants)
                });


        this.groupFields.set('file', {
            panel: false,
            panelHead: null,
            fields: fieldMap
        });
    }

    private _createPreliminaryModalGroupsFields(): void {
        // work arround for angular change detection
        this.groupFields = new Map();
        this.groupFields.set('file', {
            panel: false,
            panelHead: null,
            fields: new Map()
                .set('category', {
                    type: 'select',
                    label:
                        'CONFERENCE.WIZARD.DOCUMENTATION.PRELIMINARY.FILE.CATEGORY',
                    control: new FormControl(
                        {
                            value: this.modalPreliminaryFile.category,
                            disabled: this.fileModal.disabled
                        },
                        [Validators.required]
                    ),
                    size: '12|12|12',
                    options: this.optionsCategory,
                    errorLabels: [ErrorLabelConstants.REQUIRED],
                    valueChange: (value: ComboBox) => {
                        this.groupFields = this._setCityReferenceFieldsOnModal(
                            this.groupFields,
                            value,
                            'file',
                            '_setCityReferenceFieldsOnPreliminaryModal'
                        );
                    }
                })

                .set('visibility', {
                    type: 'select-two',
                    label:
                        'CONFERENCE.WIZARD.DOCUMENTATION.PRELIMINARY.FILE.PARTICIPANT',
                    control: new FormControl({
                        value: this.modalPreliminaryFile.visibility,
                        disabled: this.fileModal.disabled
                    }),
                    tooltip:
                        !this.isReadonly() &&
                        new TooltipModel(
                            'CONFERENCE.WIZARD.DOCUMENTATION.PRELIMINARY.MODAL.PARTICIPANT.TOOLTIP.CONTENT',
                            undefined,
                            'CONFERENCE.WIZARD.DOCUMENTATION.PRELIMINARY.MODAL.PARTICIPANT.TOOLTIP.TITLE'
                        ),
                    placeholder: 'COMMON.ALL',
                    size: '12|12|12',
                    options: of(this.participants)
                })
        });

        this.groupFields = this._setCityReferenceFieldsOnModal(
            this.groupFields,
            this.groupFields.get('file').fields.get('category').control.value,
            'file',
            '_setCityReferenceFieldsOnPreliminaryModal'
        );
    }

    private _createAdditionalInputs(): void {
        const mode: ComboBox<string>[] = [
            { key: 'file', value: 'File' },
            { key: 'url', value: 'URL' }
        ];

        const uploadMode = new Map()
            .set('file', {
                type: 'drop-file',
                onMultipleUpload: this._uploadMultipleFiles.bind(
                    this,
                    AdditionalFile,
                    this.additionalFiles,
                    this.model
                )
            })
            .set('url', {
                type: 'text',
                label: 'CONFERENCE.WIZARD.DOCUMENTATION.ADDITIONAL.FILE.URL',
                control: new FormControl(this.modalSharedFile.url, [
                    Validators.required,
                    Validators.pattern(
                        '^(http://www.|https://www.|http://|https://)?[a-z0-9]+([-.]{1}[a-z0-9]+)*.[a-z]{2,5}(:[0-9]{1,5})?(/.*)?$'
                    )
                ]),
                errorLabels: [
                    ErrorLabelConstants.REQUIRED,
                    ErrorLabelConstants.REGEX('ERROR.REGEX')
                ],
                size: '12|6|6'
            })
            .set('name', {
                type: 'text',
                label: 'CONFERENCE.WIZARD.DOCUMENTATION.ADDITIONAL.FILE.NAME',
                control: new FormControl(this.modalSharedFile.name, [
                    Validators.required
                ]),
                size: '12|6|6'
            });

        this.groupFieldsAdditional.set('additionalPanel', {
            panel: false,
            panelHead: null,
            fields: new Map()
                .set('mode', {
                    type: 'select',
                    label:
                        'CONFERENCE.WIZARD.DOCUMENTATION.ADDITIONAL.FILE.MODE',
                    size: '12|6|6',
                    options: of(mode),
                    tooltip: new TooltipModel(
                        'CONFERENCE.WIZARD.DOCUMENTATION.ADDITIONAL.TOOLTIP.MODE.TEXT',
                        undefined,
                        'CONFERENCE.WIZARD.DOCUMENTATION.ADDITIONAL.TOOLTIP.MODE.TITLE'
                    ),
                    control: new FormControl(),
                    valueChange: (value: ComboBox) => {
                        if (value.key === 'url') {
                            this.groupFieldsAdditional
                                .get('additionalPanel')
                                .fields.set('name', uploadMode.get('name'));
                        } else {
                            this.groupFieldsAdditional
                                .get('additionalPanel')
                                .fields.delete('name');
                        }
                        mode.forEach((_value: ComboBox) => {
                            if (value.key !== _value.key) {
                                this.groupFieldsAdditional
                                    .get('additionalPanel')
                                    .fields.delete(_value.key);
                            }
                        });
                        if (value.key !== 'file') {
                            this.saveAdditionalUrl = this.postAdditionalUrlFile.bind(
                                this
                            );
                        } else {
                            this.saveAdditionalUrl = null;
                        }

                        this.groupFieldsAdditional
                            .get('additionalPanel')
                            .fields.set(value.key, uploadMode.get(value.key));
                        this.groupFieldsAdditional = new Map(
                            this.groupFieldsAdditional
                        );
                    }
                })
                .set('category', {
                    type: 'select',
                    label:
                        'CONFERENCE.WIZARD.DOCUMENTATION.ADDITIONAL.FILE.CATEGORY',
                    control: new FormControl(this.tmpAdditionalFile.category, [
                        Validators.required
                    ]),
                    options: this.optionsCategory,
                    errorLabels: [ErrorLabelConstants.REQUIRED],
                    size: '12|6|6',
                    valueChange: (value: ComboBox<DocumentCategories>) => {
                        this.groupFieldsAdditional = this._setCityReferenceFields(
                            this.groupFieldsAdditional,
                            value,
                            'additionalPanel'
                        );
                    }
                })
                .set('fileComplient', {
                    type: 'switch',
                    label: 'CONFERENCE.WIZARD.DOCUMENTATION.FILE_COMPLIENT',
                    control: new FormControl(this.tmpAdditionalFile.fileComplient),
                    size: '12|12|12'
                })
                .set('file', {
                    type: 'drop-file',
                    onMultipleUpload: this._uploadMultipleFiles.bind(
                        this,
                        AdditionalFile,
                        this.additionalFiles,
                        this.model
                    )
                })
        });
        this.groupFieldsAdditional = this._setCityReferenceFields(
            this.groupFieldsAdditional,
            this.groupFieldsAdditional
                .get('additionalPanel')
                .fields.get('category').control.value,
            'additionalPanel'
        );

        if (this.conferenceStoreService.conference.isBroadband()) {
            const gf = this.groupFieldsAdditional.get('additionalPanel');
            gf.alert = {
                component: AlertWithLinkComponent,
                type: 'danger',
                textBeforeLink:
                    'CONFERENCE.WIZARD.DOCUMENTATION.ADDITIONAL.SIGN_ALERT.TEXT'
            };
            this.groupFieldsAdditional.set('additionalPanel', gf);
        }
    }

    private _createIndictionInputs(): void {
        let fieldMap = new Map();
        if(!this.isProtocolHidden()) {
            fieldMap
                .set('protocolNumber', {
                    type: 'text',
                    label:
                        'CONFERENCE.WIZARD.DOCUMENTATION.INDICTION.FILE.PROTOCOL_NUMBER',
                    control: new FormControl(
                        this.indictionBaseFile.protocolNumber,
                        [Validators.required]
                    ),
                    size: '12|6|6',
                    errorLabels: [ErrorLabelConstants.REQUIRED]
                })
                .set('protocolDate', {
                    type: 'date',
                    label:
                        'CONFERENCE.WIZARD.DOCUMENTATION.INDICTION.FILE.PROTOCOL_DATE',
                    control: new FormControl(
                        this.indictionBaseFile.protocolDate,
                        [Validators.required]
                    ),
                    size: '12|6|6',
                    errorLabels: [ErrorLabelConstants.REQUIRED]
                })
                .set('inventoryNumber', {
                    type: 'text',
                    label: this.inventoryNumberLabel,
                    control: new FormControl(
                        this.indictionBaseFile.protocolNumber,
                        []
                    ),
                    size: '12|6|6'
                });

        }
        
        fieldMap
            .set('fileComplient', {
                type: 'switch',
                label: 'CONFERENCE.WIZARD.DOCUMENTATION.FILE_COMPLIENT',
                size: '12|12|12',
                control: new FormControl(this.indictionBaseFile.fileComplient)
            });
        if(this.isMultipleSignEnable()) {
                fieldMap.set('multipleSign', {
                type: 'switch',
                label: 'BUTTON.SEND_TO_SIGN',
                size: '12|12|12',
                control: new FormControl(this.indictionBaseFile.multipleSign)
        })}
        fieldMap.set('file', {
                type: 'file',
                onUpload: this.openIndictionModal.bind(this),
                disabled: true
            })
            ;


        this.groupFieldsIndiction.set('indictionPanel', {
            alert: {
                component: AlertWithLinkComponent,
                type: 'danger',
                textAfterLink:
                    'CONFERENCE.WIZARD.DOCUMENTATION.INDICTION.FILE_ALERT.AFTER_LINK',
                textBeforeLink:
                    'CONFERENCE.WIZARD.DOCUMENTATION.INDICTION.FILE_ALERT.BEFORE_LINK',
                textLink:
                    'CONFERENCE.WIZARD.DOCUMENTATION.INDICTION.FILE_ALERT.LINK',
                linkClick: ((): void => {
                    this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
                    this.fileService
                        .downloadByApiName(
                            'downloadMemoConferenceTemplate',
                            {
                                conferenceId: this.conferenceId
                            },
                            {
                                eventType: EventType.CONFERENCE_INDICTION
                            }
                        )
                        .pipe(takeUntil(this.destroy$))
                        .subscribe(() => {
                            this.loaderService.hideLoading(
                                SectionLoading.ALL_CONTENT
                            );
                            this.groupFieldsIndiction
                                .get('indictionPanel')
                                .fields.get('file').disabled = false;
                        });
                }).bind(this)
            },
            panel: false,
            panelHead: null,
            fields: fieldMap
        });
    }

    private _createSignInputs(): void{

        //check calamaio status
        this.checkCalamaioStatus().subscribe(calamaioStatus => {
            this.groupFieldsForSign.set('signPanel', {
                panel: false,
                panelHead: null,
                fields: new Map()
                    .set('calamaio', {
                        type: 'switch',
                        label:
                            'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.FILE.CALAMAIO',
                        tooltip: new TooltipModel(
                                'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.TOOLTIP.CALAMAIO.TEXT',
                                undefined,
                                'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.TOOLTIP.CALAMAIO.TITLE'
                        ),
                        control: new FormControl({
                            value: false, 
                            disabled: !calamaioStatus,
                        }),
                        valueChange: (val) => {
                            $("input[id^='input-switch-CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.FILE.CALAMAIO_REMOTE.']").parent().parent().parent().toggle(!val);
                            $("#CALAMAIO_DOWNLOAD_MESSAGE").toggle(val);
                            $("eng-input[data-test-id^='CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.FILE.CALAMAIO_']").hide();
                            
                            this.uploadSignatureFileWithCalamaio = val;
                        },
                        placeholder: 'COMMON.ALL',
                        size: '12|12|12',
                    })

                    .set('calamaio_remote', {
                        type: 'switch',
                        label:
                            'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.FILE.CALAMAIO_REMOTE',
                        tooltip: new TooltipModel(
                                'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.TOOLTIP.CALAMAIO_REMOTE.TEXT',
                                undefined,
                                'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.TOOLTIP.CALAMAIO_REMOTE.TITLE'
                        ),
                        control: new FormControl({
                            value: false, 
                        }),
                        valueChange: (val) => {
                            $("input[id^='input-switch-CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.FILE.CALAMAIO.']").parent().parent().parent().toggle(!val);
                            $("eng-input[data-test-id^='CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.FILE.CALAMAIO_']").toggle(val);
                            $("input[id^='CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.FILE.CALAMAIO_PWD.']").val("");
                        },
                        placeholder: 'COMMON.ALL',
                        size: '12|12|12',
                    })
                    .set('calamaio_remote_uid', {
                        type: 'text',
                        label: 'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.FILE.CALAMAIO_UID',
                        tooltip: new TooltipModel(
                                'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.TOOLTIP.CALAMAIO_REMOTE.UID',
                                undefined,
                                'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.TOOLTIP.CALAMAIO_REMOTE.TITLE'
                        ),
                        pattern:
                            '^.*$',
                        control: new FormControl(false),
                        errorLabels: [ErrorLabelConstants.REGEX('ERROR.REGEX')],
                        size: '12|6|6'
                    })
                    .set('calamaio_remote_pwd', {
                        type: 'text',
                        label: 'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.FILE.CALAMAIO_PWD',
                        tooltip: new TooltipModel(
                                'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.TOOLTIP.CALAMAIO_REMOTE.PWD',
                                undefined,
                                'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.TOOLTIP.CALAMAIO_REMOTE.TITLE'
                        ),
                        pattern:
                            '^.*$',
                        control: new FormControl(false),
                        errorLabels: [ErrorLabelConstants.REGEX('ERROR.REGEX')],
                        size: '12|6|6'
                    })
                    /*
                    .set('calamaio_remote_otp', {
                        type: 'text',
                        label: 'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.FILE.CALAMAIO_OTP',
                        tooltip: new TooltipModel(
                                'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.TOOLTIP.CALAMAIO_REMOTE.OTP',
                                undefined,
                                'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.TOOLTIP.CALAMAIO_REMOTE.TITLE'
                        ),
                        pattern:
                            '^.*$',
                        control: new FormControl(false),
                        errorLabels: [ErrorLabelConstants.REGEX('ERROR.REGEX')],
                        size: '12|6|6'
                    })
                    */
                    .set('calamaio_remote_domain', {
                        type: 'text',
                        label: 'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.FILE.CALAMAIO_DOMAIN',
                        tooltip: new TooltipModel(
                                'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.TOOLTIP.CALAMAIO_REMOTE.DOMAIN',
                                undefined,
                                'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.TOOLTIP.CALAMAIO_REMOTE.TITLE'
                        ),
                        pattern:
                            '^.*$',
                        control: new FormControl(false),
                        errorLabels: [ErrorLabelConstants.REGEX('ERROR.REGEX')],
                        size: '12|6|6'
                    })

                    .set('calamaio_pades_cades', {
                        type: 'single-textbox',
                        label:
                            'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.FILE.LABEL_PADES_CADES',
                        control: new FormControl({
                            value: false, 
                        }),
                        /*
                        valueChange: (val) => {
                            $("input[id^='input-switch-CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.FILE.CALAMAIO.']").parent().parent().parent().toggle(!val);
                            $("eng-input[data-test-id^='CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.FILE.CALAMAIO_']").toggle(val);
                            $("input[id^='CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.FILE.CALAMAIO_PWD.']").val("");
                        },
                        */
                        placeholder: 'COMMON.ALL',
                        size: '12|12|12',
                    })


                    .set('fileComplient', {
                        type: 'switch',
                        label: 'CONFERENCE.WIZARD.DOCUMENTATION.FILE_COMPLIENT',
                        size: '12|12|12',
                        control: new FormControl(false)
                    })
                    .set('visibility', {
                        type: 'select-two',
                        label:
                            'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.FILE.PARTICIPANT',
                        control: new FormControl({
                            value:  this.allAccreditedForPartecipants
                        }),
    
                        placeholder: 'COMMON.ALL',
                        size: '12|6|6',
                        options: of(this.accreditedForPartecipants)
                    })
                    .set('file', {
                        type: 'file',
                        onUpload: this.openSignatureModal.bind(this)
                    })

                    
            });

            this.intervalUpdateCalamaio = setInterval(() => {
                this._uploadCalamaioStatus(); 
                if (this.flagInstallCalamaio === true) {      
                    clearInterval(this.intervalUpdateCalamaio);
                }
            }, 3000);
        }) 

    }
/*
    uplaodRemoteSignatureFile(file: any){
        //alert('OK');
        //remoteSignatureUploadGroupFields
        //CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.REMOTESIGN.CALAMAIO_UID.64671

        return new Observable(
            (observer: Subscriber<void>): void => {
                this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
                this.uploadRemoteSignatureFileModal.close();

                observer.next();
                observer.complete();
            }
        );

    }
*/

    uplaodSignatureFile(file: any){
        this.uploadSignatureFileModal.close();
        this.fileService.edit(this.fileToSign, FileType.SIGNATURE , file).pipe(
            takeUntil(this.destroy$),
            catchError(() => {
                this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
                this.toastr.info(
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.ERROR_SAVE_FILE.TEXT'
                    ),
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.ERROR_SAVE_FILE.TITLE'
                    )
                );
                return of();
            })
        )
        .subscribe(signatureFile => {
            this.fileToSign.status = signatureFile.status;
            this.fileToSign.owner = signatureFile.owner;
            this.fileToSign.remoteSignature = signatureFile.remoteSignature;
            this.fileToSign.calamaioRemoteUsername = signatureFile.calamaioRemoteUsername;
            this.fileToSign.calamaioRemotePassword = signatureFile.calamaioRemotePassword;
            this.fileToSign.calamaioRemoteOTP = signatureFile.calamaioRemoteOTP;
            this.fileToSign.calamaioRemoteDomain = signatureFile.calamaioRemoteDomain;
                                
            this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
                        
            this.toastr.info(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.SUCCESS_SAVE_FILE.TEXT'
                ),
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.SUCCESS_SAVE_FILE.TITLE'
                )
            );
        });        
    }

    private _createUploadSignInputs(): void {
        
        this.signatureUploadGroupFields.set('file', {
            panel: false,
            panelHead: null,
            fields: new Map()
                .set('file', {
                    type: 'file',
                    tooltip:
                        new TooltipModel(
                            'CONFERENCE.WIZARD.DOCUMENTATION.ADDITIONAL.MODAL.PARTICIPANT.TOOLTIP.CONTENT',
                            undefined,
                            'CONFERENCE.WIZARD.DOCUMENTATION.ADDITIONAL.MODAL.PARTICIPANT.TOOLTIP.TITLE'
                        ),
                    onUpload: this.uplaodSignatureFile.bind(this)
                })
        });
    }

    private _createUploadRemoteSignInputs(): void {
        
        this.remoteSignatureUploadGroupFields.set('file', {
            panel: false,
            panelHead: null,
            fields: new Map()
                    .set('dlg_calamaio_remote_uid', {
                        type: 'text',
                        label: 'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.REMOTESIGN.CALAMAIO_UID',
                        tooltip: new TooltipModel(
                                'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.TOOLTIP.CALAMAIO_REMOTE.UID',
                                undefined,
                                'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.TOOLTIP.CALAMAIO_REMOTE.TITLE'
                        ),
                        pattern:
                            '^.*$',
                        control: new FormControl(false),
                        errorLabels: [ErrorLabelConstants.REGEX('ERROR.REGEX')],
                        size: '12|6|6'
                    })
                    .set('dlg_calamaio_remote_pwd', {
                        type: 'text',
                        label: 'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.REMOTESIGN.CALAMAIO_PWD',
                        tooltip: new TooltipModel(
                                'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.TOOLTIP.CALAMAIO_REMOTE.PWD',
                                undefined,
                                'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.TOOLTIP.CALAMAIO_REMOTE.TITLE'
                        ),
                        pattern:
                            '^.*$',
                        control: new FormControl(false),
                        errorLabels: [ErrorLabelConstants.REGEX('ERROR.REGEX')],
                        size: '12|6|6'
                    })
                    .set('dlg_calamaio_remote_otp', {
                        type: 'text',
                        label: 'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.REMOTESIGN.CALAMAIO_OTP',
                        tooltip: new TooltipModel(
                                'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.TOOLTIP.CALAMAIO_REMOTE.OTP',
                                undefined,
                                'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.TOOLTIP.CALAMAIO_REMOTE.TITLE'
                        ),
                        pattern:
                            '^.*$',
                        control: new FormControl(false),
                        errorLabels: [ErrorLabelConstants.REGEX('ERROR.REGEX')],
                        size: '12|6|6'
                    })
                    .set('dlg_calamaio_remote_domain', {
                        type: 'text',
                        label: 'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.REMOTESIGN.CALAMAIO_DOMAIN',
                        tooltip: new TooltipModel(
                                'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.TOOLTIP.CALAMAIO_REMOTE.DOMAIN',
                                undefined,
                                'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.TOOLTIP.CALAMAIO_REMOTE.TITLE'
                        ),
                        pattern:
                            '^.*$',
                        control: new FormControl(false),
                        errorLabels: [ErrorLabelConstants.REGEX('ERROR.REGEX')],
                        size: '12|6|6'
                    })
        });
    }

    private _createSharedInputs(): void {
        const mode: ComboBox<string>[] = [
            {
                key: 'file',
                value: this.i18nService.translate(
                    'CONFERENCE.WIZARD.DOCUMENTATION.SHARED.FILE.FILE'
                )
            },
            {
                key: 'model',
                value: this.i18nService.translate(
                    'CONFERENCE.WIZARD.DOCUMENTATION.SHARED.FILE.MODEL'
                )
            },
            {
                key: 'dashboard',
                value: this.i18nService.translate(
                    'CONFERENCE.WIZARD.DOCUMENTATION.SHARED.FILE.DASHBOARD'
                )
            }
        ];

        const uploadMode = new Map()
            .set('model', {
                type: 'select',
                label: 'CONFERENCE.WIZARD.DOCUMENTATION.SHARED.FILE.MODEL',
                control: new FormControl(this.modalSharedFile.model, [
                    Validators.required,
                    SelectValueValidator
                ]),
                size: '12|12|12',
                options: this.utilityService.getDocumentModels(
                    this.conferenceType
                )
            })
            .set('file', {
                type: 'file',
                onUpload: this.openSharedModal.bind(this)
            });

        this.groupFieldsShared.set('sharedPanel', {
            panel: false,
            panelHead: null,
            fields: new Map()
                .set('mode', {
                    type: 'select',
                    label: 'CONFERENCE.WIZARD.DOCUMENTATION.SHARED.FILE.MODE',
                    size: '12|12|12',
                    options: of(mode),
                    tooltip: new TooltipModel(
                        'CONFERENCE.WIZARD.DOCUMENTATION.SHARED.TOOLTIP.MODE.TEXT',
                        undefined,
                        'CONFERENCE.WIZARD.DOCUMENTATION.SHARED.TOOLTIP.MODE.TITLE'
                    ),
                    control: new FormControl(),
                    valueChange: (value: ComboBox) => {
                        mode.forEach((_value: ComboBox) => {
                            if (value.key !== _value.key) {
                                this.groupFieldsShared
                                    .get('sharedPanel')
                                    .fields.delete(_value.key);
                            }
                        });

                        if (value.key !== 'file') {
                            this.saveShared = this.postSharedFile.bind(this);
                        } else {
                            this.saveShared = null;
                        }

                        if (uploadMode.has(value.key)) {
                            this.groupFieldsShared
                                .get('sharedPanel')
                                .fields.set(
                                    value.key,
                                    uploadMode.get(value.key)
                                );
                        }

                        this.groupFieldsShared = new Map(
                            this.groupFieldsShared
                        );
                    }
                })
                .set('visibility', {
                    type: 'select-two',
                    label:
                        'CONFERENCE.WIZARD.DOCUMENTATION.SHARED.FILE.PARTICIPANT',
                    control: new FormControl({
                        value: this.modalSharedFile.visibility
                    }),

                    placeholder: 'COMMON.ALL',
                    size: '12|6|6',
                    options: of(this.participants)
                })
                .set('fileComplient', {
                    type: 'switch',
                    label: 'CONFERENCE.WIZARD.DOCUMENTATION.FILE_COMPLIENT',
                    size: '12|12|12',
                    control: new FormControl(this.modalSharedFile.fileComplient)
                })
                .set('file', {
                    type: 'file',
                    onUpload: this.openSharedModal.bind(this)
                })                
        });
    }

    private _createSharedModalGroupsFields(): void {
        this.groupFields.set('file', {
            panel: false,
            panelHead: null,
            fields: new Map()
                .set('name', {
                    type: 'text',
                    label: 'CONFERENCE.WIZARD.DOCUMENTATION.SHARED.FILE.NAME',
                    control: new FormControl(this.modalSharedFile.name, [
                        Validators.required
                    ]),
                    size: '12|12|12',
                    errorLabels: [ErrorLabelConstants.REQUIRED]
                })
                .set('visibility', {
                    type: 'select-two',
                    label:
                        'CONFERENCE.WIZARD.DOCUMENTATION.SHARED.FILE.PARTICIPANT',
                    control: new FormControl({
                        value: this.modalSharedFile.visibility,
                        disabled: this.fileModal.disabled
                    }),

                    placeholder: 'COMMON.ALL',
                    size: '12|6|6',
                    options: of(this.participants)
                })
        });
        this.groupFields = new Map(this.groupFields);
    }

    isLink(model: any): boolean {
        return model.url.indexOf(this.configService.get('backend.baseUrl'));
    }

    private _createPreliminaryInputs(): void {
        this.groupFieldsPreliminary.set('preliminaryPanel', {
            panel: false,
            panelHead: null,
            fields: new Map()
                .set('category', {
                    type: 'select',
                    label:
                        'CONFERENCE.WIZARD.DOCUMENTATION.PRELIMINARY.FILE.CATEGORY',
                    control: new FormControl(this.tmpPreliminaryFile.category, [
                        Validators.required
                    ]),
                    options: this.optionsCategory,
                    errorLabels: [ErrorLabelConstants.REQUIRED],
                    size: '12|12|12',
                    valueChange: (value: ComboBox<DocumentCategories>) => {
                        this.groupFieldsPreliminary = this._setCityReferenceFields(
                            this.groupFieldsPreliminary,
                            value,
                            'preliminaryPanel'
                        );
                    }
                })
                .set('fileComplient', {
                    type: 'switch',
                    label: 'CONFERENCE.WIZARD.DOCUMENTATION.FILE_COMPLIENT',
                    size: '12|12|12',
                    control: new FormControl(this.tmpPreliminaryFile.fileComplient)
                })
                .set('file', {
                    type: 'drop-file',
                    onMultipleUpload: this._uploadMultipleFiles.bind(
                        this,
                        PreliminaryFile,
                        this.preliminaryFiles,
                        this.model
                    )
                })
        });
        this.groupFieldsPreliminary = this._setCityReferenceFields(
            this.groupFieldsPreliminary,
            this.groupFieldsPreliminary
                .get('preliminaryPanel')
                .fields.get('category').control.value,
            'preliminaryPanel'
        );
    }

    private _setAccordionListener(): void {
        this._listenShowAccordion();
        this._listenShownAccordion();
        this._listenHiddenAccordion();
    }

    private _listenShowAccordion(): void {
        $('#panelGroupDocumentation').on('show.bs.collapse', e => {
            if (e.target.nodeName !== 'INPUT') {
                this._closeAllAccordion();
            }
        });
    }

    private _closeAllAccordion(): void {
        $('#panelGroupDocumentation')
            .find('.panel-collapse.in')
            .collapse('hide');
    }

    private _openAccordion(id: string): void {
        $('#panelGroupDocumentation')
            .find(`#${id}`)
            .collapse('show');
        this._setActiveAccordion(id);
    }

    private _listenHiddenAccordion(): void {
        $('#panelGroupDocumentation').on('hidden.bs.collapse', e => {
            if (!this._existOpenAccordion()) {
                this._setActiveAccordion(null);
                this._blurAllMenuItem();
            }
        });
    }

    private _blurAllMenuItem(): void {
        $('.formNav a').blur();
    }

    private _listenShownAccordion(): void {
        $('#panelGroupDocumentation').on('shown.bs.collapse', e => {
            this._setActiveAccordion(e.target.id);
        });
    }

    private _existOpenAccordion(): boolean {
        return $('#panelGroupDocumentation').find('.panel-collapse.in').length;
    }

    private _setActiveAccordion(id: string): void {
        this.openPanel = id;
        this.changeDetectorRef.detectChanges();
    }

    private _setCityReferenceFields(
        groupFields: Map<string, FormFieldGroup>,
        value: ComboBox<DocumentCategories>,
        panel: 'preliminaryPanel' | 'additionalPanel'
    ): Map<string, FormFieldGroup> {
        const category: FormField = groupFields
            .get(panel)
            .fields.get('category');
        if (
            value &&
            value.key === DocumentCategories.MUNICIPALITY_DOCUMENTATION
        ) {
            const file: FormField = groupFields.get(panel).fields.get('file');
            groupFields.get(panel).fields.delete('file');
            groupFields.get(panel).fields.set('cityReference', {
                type: 'text',
                label:
                    'CONFERENCE.WIZARD.DOCUMENTATION.ADDITIONAL.FILE.CITY_REFERENCE',
                control: null,
                size: '12|12|12'
            });
            if (file) {
                groupFields.get(panel).fields.set('file', file);
            }
            if (panel === 'preliminaryPanel') {
                groupFields
                    .get(panel)
                    .fields.get('cityReference').control = new FormControl(
                    this.tmpPreliminaryFile.cityReference
                );
            }
            if (panel === 'additionalPanel') {
                groupFields
                    .get(panel)
                    .fields.get('cityReference').control = new FormControl(
                    this.tmpAdditionalFile.cityReference
                );
            }
            category.size = '12|6|6';
        } else {
            groupFields.get(panel).fields.delete('cityReference');
            category.size = '12|6|6';
        }
        return new Map(groupFields);
    }

    private _setCityReferenceFieldsOnModal(
        groupFields: Map<string, FormFieldGroup>,
        value: ComboBox,
        panel: 'file',
        supportMethod:
            | '_setCityReferenceFieldsOnAdditionalModal'
            | '_setCityReferenceFieldsOnPreliminaryModal'
    ): Map<string, FormFieldGroup> {
        const category: FormField = groupFields
            .get(panel)
            .fields.get('category');

        if (
            value &&
            value.key === DocumentCategories.MUNICIPALITY_DOCUMENTATION
        ) {
            const visibility: FormField = groupFields
                .get(panel)
                .fields.get('visibility');
            groupFields.get(panel).fields.delete('visibility');
            groupFields.get(panel).fields.get('category').size = '12|6|6';

            this[supportMethod](groupFields, panel);

            groupFields.get(panel).fields.set('visibility', visibility);
        } else {
            delete this.modalFile.cityReference;
            groupFields.get(panel).fields.delete('cityReference');
            category.size = '12|12|12';
        }
        return new Map(groupFields);
    }

    private _setCityReferenceFieldsOnAdditionalModal(
        groupFields: Map<string, FormFieldGroup>,
        panel: string
    ): void {
        groupFields.get(panel).fields.set('cityReference', {
            type: 'text',
            label:
                'CONFERENCE.WIZARD.DOCUMENTATION.ADDITIONAL.FILE.CITY_REFERENCE',
            control: new FormControl({
                value: this.modalAdditionalFile.cityReference,
                disabled: this.fileModal.disabled
            }),
            size: '12|6|6'
        });
    }

    private _setCityReferenceFieldsOnPreliminaryModal(
        groupFields: Map<string, FormFieldGroup>,
        panel: string
    ): void {
        groupFields.get(panel).fields.set('cityReference', {
            type: 'text',
            label:
                'CONFERENCE.WIZARD.DOCUMENTATION.PRELIMINARY.FILE.CITY_REFERENCE',
            control: new FormControl({
                value: this.modalPreliminaryFile.cityReference,
                disabled: this.fileModal.disabled
            }),
            size: '12|6|6'
        });
    }

    private _getFile(
        modelClass:
            | typeof PreliminaryFile
            | typeof AdditionalFile
            | typeof InterectionFile,
        fileEntry: FileSystemFileEntry
    ): Observable<GetFileReturn> {
        return new Observable((observer: Subscriber<GetFileReturn>) => {
            fileEntry.file((file: File) => {
                let modelData: Partial<
                    PreliminaryFile | AdditionalFile | InterectionFile
                >;
                switch (modelClass.name) {
                    case 'AdditionalFile':
                        modelData = {
                            name: file.name,
                            visibility: [],
                            category: this.groupFieldsAdditional
                                .get('additionalPanel')
                                .fields.get('category').control.value,
                            cityReference: this.groupFieldsAdditional
                                .get('additionalPanel')
                                .fields.get('cityReference')
                                ? this.groupFieldsAdditional
                                      .get('additionalPanel')
                                      .fields.get('cityReference').control.value
                                : null,
                            fileComplient: this.groupFieldsAdditional.get('additionalPanel').fields.get('fileComplient')?
                                this.groupFieldsAdditional.get('additionalPanel').fields.get('fileComplient').control.value : false
                        };
                        break;
                    case 'InterectionFile':
                        modelData = {
                            name: file.name,
                            visibility: [],
                            category: null,
                            cityReference: null,
                            fileComplient: false
                        };
                        break;
                    case 'PreliminaryFile':
                        modelData = {
                            name: file.name,
                            visibility: [],
                            category: this.groupFieldsPreliminary
                                .get('preliminaryPanel')
                                .fields.get('category').control.value,
                            cityReference: this.groupFieldsPreliminary
                                .get('preliminaryPanel')
                                .fields.get('cityReference')
                                ? this.groupFieldsPreliminary
                                      .get('preliminaryPanel')
                                      .fields.get('cityReference').control.value
                                : null,
                            fileComplient: this.groupFieldsPreliminary.get('preliminaryPanel').fields.get('fileComplient')?
                                this.groupFieldsPreliminary.get('preliminaryPanel').fields.get('fileComplient').control.value : false
                        };
                        break;
                }
                observer.next({
                    file,
                    model: new modelClass(modelData)
                });
                observer.complete();
            });
        });
    }

    private _manageToaaserOnUploadError(res){

        if(res.status != 200){

            if(res.body && res.body.status != "200"){
                res.body.errors.forEach(error => {
                    this.toastr.error(
                        error.msg,
                        this.i18nService.translate(
                            'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.ERROR_SAVE_FILE.TITLE'
                        )
                    );
                });
            }else if(res.errors && res.errors.length > 0){
                res.errors.forEach(error => {
                    this.toastr.error(
                        error.msg,
                        this.i18nService.translate(
                            'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.ERROR_SAVE_FILE.TITLE'
                        )
                    );
                });
            }else{

                this.toastr.error(
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.ERROR_SAVE_FILE.TEXT'
                    ),
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.ERROR_SAVE_FILE.TITLE'
                    )
                );                
            }            
            return null
        }else{
            return res
        }

    }

    private _uploadMultipleFiles(
        modelClass:
            | typeof PreliminaryFile
            | typeof AdditionalFile
            | typeof InterectionFile,
        filePanelCmp: FilePanelComponent,
        model: Documentation,
        files: UploadFile[]
    ): void {
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
        this.openUploadStatusModal();
        const obsFile: Observable<GetFileReturn>[] = [];
        // tslint:disable-next-line:forin
        for (const k in files) {
            const fileEntry = files[k].fileEntry as FileSystemFileEntry;
            obsFile.push(this._getFile(modelClass, fileEntry));
        }
        forkJoin(obsFile)
            .pipe(
                takeUntil(this.destroy$),
                mergeMap((data: GetFileReturn[]) => {
                    const obs = [];

                    // tslint:disable-next-line:forin
                    for (const k in files) {
                        obs.push(
                            this.fileService.upload(
                                this.conferenceId,
                                data[k].model,
                                data[k].file,
                                data[k].model.type,
                                true
                            )
                        );
                    }

                    return forkJoin(obs);
                }),
                catchError((res) => {
                    this.closeUploadStatusModal();
                    this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
                    filePanelCmp.hideForm();
                    return of(this._manageToaaserOnUploadError(res))
                })
            )
            .subscribe((res: BaseFile[]) => {
                if(res == null)
                    return;
                // tslint:disable-next-line:forin
                for (const k in res) {
                    model.push(new modelClass(res[k]));
                }
                if(this.fileService.isUploadCompleted){
                    this.closeUploadStatusModal();
                }
                this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);

                filePanelCmp.hideForm();

                this.toastr.info(
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.SUCCESS_SAVE_FILE.TEXT'
                    ),
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.SUCCESS_SAVE_FILE.TITLE'
                    )
                );
            });
    }

    private _deleteFile(): void {
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
        this.fileService
            .deleteFile(this.idToDelete)
            .pipe(
                takeUntil(this.destroy$),
                catchError(() => {
                    this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
                    this.confirmModal.close();
                    this.toastr.info(
                        this.i18nService.translate(
                            'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.ERROR_DELETE_FILE.TEXT'
                        ),
                        this.i18nService.translate(
                            'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.ERROR_DELETE_FILE.TITLE'
                        )
                    );
                    return of();
                })
            )
            .subscribe(res => {
                this.model.delete(this.fileToDelete);
                this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
                this.confirmModal.close();
                this.toastr.info(
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.SUCCESS_DELETE_FILE.TEXT'
                    ),
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.SUCCESS_DELETE_FILE.TITLE'
                    )
                );
            });
    }

    private _deleteSignatureFile(): void {
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
        this.fileService.deleteFile(this.idToDelete)
            .pipe(
                takeUntil(this.destroy$),
                catchError(() => {
                    this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
                    this.confirmDeleteSignatureFileModal.close();
                    this.toastr.info(
                        this.i18nService.translate(
                            'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.ERROR_DELETE_FILE.TEXT'
                        ),
                        this.i18nService.translate(
                            'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.ERROR_DELETE_FILE.TITLE'
                        )
                    );
                    return of();
                })
            )
            .subscribe(res => {
                this.model.delete(this.fileToDelete);
                this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
                this.confirmDeleteSignatureFileModal.close();
                
                this.toastr.info(
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.SUCCESS_DELETE_FILE.TEXT'
                    ),
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.SUCCESS_DELETE_FILE.TITLE'
                    )
                );
            });
    }

    public deleteFileList(fileList: BaseFile[]){
        
        if(fileList.length == 0){
            this.toastr.warning(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.WARNING_DELETE_LIST_FILE.TEXT'
                ),
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.WARNING_DELETE_LIST_FILE.TITLE'
                )
            );
            return;                
        }
        this.fileListToDelete = fileList;
        this.deleteListFileConfirmModal.open()
    }

    private _deleteFileList(){
        
        let idList : Number[] = [];
        this.fileListToDelete.forEach( file => {
            idList.push(file.id)
        })  

        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
        this.deleteListFileConfirmModal.close();
        this.documentsService.deleteDocumentList(idList)
        .pipe( 
            catchError( res => {
                if(res.status != '200'){

                    this.toastr.error(
                        this.i18nService.translate(
                            'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.ERROR_DELETE_LIST_FILE.TEXT'
                        ),
                        this.i18nService.translate(
                            'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.ERROR_DELETE_LIST_FILE.TITLE'
                        )
                    );
                    
                    return of(null)
                }else{
                    this.toastr.error(
                        this.i18nService.translate(
                            'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.SUCCESS_DELETE_LIST_FILE.TEXT'
                        ),
                        this.i18nService.translate(
                            'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.SUCCESS_DELETE_LIST_FILE.TITLE'
                        )
                    );

                    return of()
                }
            })
        )
        .subscribe( res =>{
            if(res !== null){
                this.fileListToDelete.forEach( file => {
                    this.model.delete(file);
                })  

                this.fileListToDelete = [];
            }

            this.loaderService.hideLoading(
                SectionLoading.ALL_CONTENT,
                300
            );
        })             
        
    }
     
    public syncronizeFiles(event: any){
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
        this.conferenceStoreService.syncronizeDocuments(this.conferenceId, this.switchAllDocumentsVisibility)
        .subscribe(() => {
            this.loaderService.hideLoading(
                SectionLoading.ALL_CONTENT,
                300
            );
        }); 
    }

    public isOperationalUSR(): boolean {
        return this.conferenceStoreService.conference.definition.instance.conferenceType.key === ConferenceType.DOMUS;
    }    

    public isPaleoDomus(): boolean {
        return this.conferenceStoreService.conference.definition.instance.conferenceType.key === ConferenceType.DOMUS
            || this.conferenceStoreService.conference.definition.instance.conferenceType.key === ConferenceType.ENVIRONMENT_DEFINITION_AIA
            || this.conferenceStoreService.conference.definition.instance.conferenceType.key === ConferenceType.ENVIRONMENT_DEFINITION_VIA
            || this.conferenceStoreService.conference.definition.instance.conferenceType.key === ConferenceType.ENVIRONMENT_INVESTIGATION_AIA
            || this.conferenceStoreService.conference.definition.instance.conferenceType.key === ConferenceType.ENVIRONMENT_INVESTIGATION_VIA
    }    

    public deleteSectionFiles(event: any) {
        this.deleteFileList(event);
    }

    downloadPDFFile(file: BaseFile): void {
        console.log('--------------downloadPDFFile: ' + file.id);

        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
        this.openDownloadStatusModal();

        let params = new HttpParams();
        params = params.set('pdffile', '1');

        this.fileService
            .download(file, params, true)
            .pipe(
                catchError(_ => {
                    this.toastr.error(this.i18nService.translate('CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.FILE_DOWNLOAD_ERROR.TEXT'), this.i18nService.translate('CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.FILE_DOWNLOAD_ERROR.TITLE'));
                    this.closeDownloadStatusModal();
                    return of(null);
                })
            )
            .subscribe(_ => {
                if((_ && this.fileService.isUploadCompleted )){
                    this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
                    this.closeDownloadStatusModal();
                }
            });
    }

    public downloadSectionFiles(fileList: any[]){
        console.log('--------------downloadSectionFiles: ' + fileList.length);

        if(fileList.length > 0){
            this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
            this.openDownloadStatusModal();

            let protocol: string = '';
            let docIds: string = '';
            fileList.forEach( (item, index) => {
                if(index == 0)
                    protocol = item.name;
                else
                    docIds += (index==1?'':',')+item.id;
            });

            let params = new HttpParams();
            params = params
                .set('protocol', protocol)
                .set('docIDs', docIds);
            this.fileService.downloadByApiName('downloadZipFile', null, params, true)
                /*
                .pipe(
                    catchError(_ => {
                        this.toastr.error(
                            this.i18nService.translate(
                                'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.ERROR_ZIP_FILE.TEXT'
                            ),
                            this.i18nService.translate(
                                'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.ERROR_ZIP_FILE.TITLE'
                            )
                        );
                        return of(null);
                    })
                )
                */
                .subscribe(_ => {
                    if((_ && this.fileService.isUploadCompleted )){
                        this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
                        this.closeDownloadStatusModal();
                    }
                });
        }
    }

    isClosedMode(): boolean {
        return this.conferenceStoreService.conference.state.key === ConferenceState.CLOSED;
    }

    isUserDelegate(): boolean {
        return this.conferenceStoreService.isDelegate;
    }

    isImpersonatedAdmin(): boolean {
        return this.conferenceStoreService.conference.isImpersonatedAdmin;
    }
     
    isReadonlyMode(): boolean {
        return this.action === ActionForm.READONLY;
    }
      
    isMultipleSignEnable(): boolean {

        this.multipleSignEnable = this.conferenceType == ConferenceType.DOMUS
                || this.conferenceType == ConferenceType.ENVIRONMENT_INVESTIGATION_VIA
                || this.conferenceType == ConferenceType.ENVIRONMENT_DEFINITION_VIA
                || this.conferenceType == ConferenceType.ENVIRONMENT_INVESTIGATION_AIA
                || this.conferenceType == ConferenceType.ENVIRONMENT_DEFINITION_AIA;

        return this.multipleSignEnable;
    }

    markToSignFile(file: File): void {
        let newFile = null;

        if(this.isProtocolHidden()) {
            newFile = new IndictionFile({
                name: file.name,
                visibility: [],
                protocolDate: new DateModel(new Date(0)), // "01-01-1970", // woarkaround to skip protocol validation
                inventoryNumber: '',
                fileComplient: this.groupFieldsIndiction.get('indictionPanel').fields.get('fileComplient')? 
                    this.groupFieldsIndiction.get('indictionPanel').fields.get('fileComplient').control.value : false
            });
        }
        else {
            newFile = new IndictionFile({
                name: file.name,
                visibility: [],
                // tmp workaround
                protocolNumber: this.groupFieldsIndiction
                    .get('indictionPanel')
                    .fields.get('protocolNumber').control.value,
                protocolDate: this.groupFieldsIndiction
                    .get('indictionPanel')
                    .fields.get('protocolDate').control.value,
                inventoryNumber: this.groupFieldsIndiction
                    .get('indictionPanel')
                    .fields.get('inventoryNumber').control.value,
                fileComplient: this.groupFieldsIndiction.get('indictionPanel').fields.get('fileComplient')? 
                    this.groupFieldsIndiction.get('indictionPanel').fields.get('fileComplient').control.value : false
            });
        }

        this.documentsService.uplaoadAndMarkDocumentToSign(this.conferenceId, newFile, file, newFile.type, this.managerCST)
           
        .pipe(
                takeUntil(this.destroy$),
                catchError(res => {
                    this.loaderService.hideLoading(
                        SectionLoading.MODAL_SIGN
                    );
                    this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
                    return of(this._manageToaaserOnUploadError(res));
                })
            )
            .subscribe((res: BaseFile) => {
                if(res == null)
                    return;

                this.model.indiction.push(new IndictionFile(res));
                this.loaderService.hideLoading(
                    SectionLoading.MODAL_SIGN
                );
                this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
                this.toastr.info(
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.SUCCESS_SAVE_FILE.TEXT'
                    ),
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.SUCCESS_SAVE_FILE.TITLE'
                    )
                );
             //   this.indictionModalToSign.close();
            //    this.onIndiction();
            });
    }
    selectChangeHandler (event: any) {
        //update the ui
        this.managerCST = event.target.value;
      }

    private _initFileServiceObserver(): void {
        this.fileService.uploads$
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
            this.fileService.downloads$
            .pipe(takeUntil(this.destroy$))
            .subscribe(
                (
                    download: Map<
                        string,
                        Subject<{ name: string; progress: number }>
                    >
                ) => {
                    download.forEach(
                        (
                            value: Subject<{ name: string; progress: number }>
                        ) => {
                            if(this.tmpDownloads.indexOf(value) < 0)
                                this.tmpDownloads.push(value);
                        }
                    );
                }
            );
    }

    resetUploadStatus() {
        this.tmpUploads.pop();
    }

    resetDownloadStatus() {
        this.tmpDownloads.pop();
    }

    private _uploadCalamaioStatus() {
       if (!this.flagInstallCalamaio) {
        this.documentsService.getCalamaioStatus().pipe(                
            catchError(res => {
                let result : Observable<boolean>;
                    if(res.status === 200){
                        result = of(true);
                    }else{
                        result=  of(false);
                    }
                return result; 
            })
        ).subscribe(res => {
            this.calamaioStatus = of(res);
            if (res == true)   {
                this.flagInstallCalamaio = true;
                this.groupFieldsForSign.get('signPanel')
                .fields.get('calamaio')
                .control.enable();
            } 
        }) 
       }
    }
}


