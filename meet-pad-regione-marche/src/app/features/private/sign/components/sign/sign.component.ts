import { Component, OnInit, Input, QueryList, ViewChild } from '@angular/core';
import { LoaderService, SectionLoading, BaseComponent, WrapperPostPutData, FileService, BaseFile } from '@common';
import { SignTableComponent } from '../sign-table/sign-table.component';
import { SignPanelComponent } from '../sign-panel/sign-panel.component';
import { ActivatedRoute } from '@angular/router';
import { filter, takeUntil, catchError, map } from 'rxjs/operators';
import { SearchSign } from '../../models/search-sign.models';
import { ActionItem, ModalComponent } from '@eng-ds/ng-toolkit';
import { SignService } from '../../services/sign.service';
import { ToastrService } from 'ngx-toastr';
import { I18nService } from '@eng-ds/ng-core';
import { Observable, of } from 'rxjs';
import { HttpParams } from '@angular/common/http';
import { SignatureFile } from '@app/features/private/conference/core/models/documentation/signature-file.model';

declare var $: any;
declare function togglePwdView(): any;

@Component({
  selector: 'app-sign',
  templateUrl: './sign.component.html',
  styleUrls: ['./sign.component.scss']
})
export class SignComponent {//extends BaseComponent implements OnInit {
    @ViewChild('documentToSign') documentToSign: SignPanelComponent;
    @ViewChild('documentSigned') documentSigned: SignPanelComponent;
    @ViewChild('documentRejected') documentRejected: SignPanelComponent;
    @ViewChild('rejectListFileConfirmModal') rejectListFileConfirmModal: ModalComponent;
    @ViewChild('signSelectedModal') signSelectedModal: ModalComponent;

    @Input('title') title: string;
    @Input('summary') summary: boolean = false;
    @Input('selectionEnabled') selectionEnabled: boolean = false;
    loading: boolean = false;
    openPanel: string;
    active: false;
    fileListToReject: SearchSign[];
    fileListToSign: SearchSign[];
 
    constructor( 
        private route: ActivatedRoute,
        private signService: SignService,
        private toastr: ToastrService,
        private i18nService: I18nService,
        private loaderService: LoaderService,
        private fileService: FileService
        ) {
       // super();
      
       //this.startPageLoading();
       
    }

    get menuItems(): string[] {
      return [
          'SIGN.ACTIONS_BUTTONS.SEND',
          'SIGN.ACTIONS_BUTTONS.REJECTED',
          'SIGN.ACTIONS_BUTTONS.SIGNED'
      ];
    }

    rejectListFileModalButtons: ActionItem[] = [
        new ActionItem(
            'SIGN.CONFIRM_REJECT_LIST_MODAL.CANCEL_BUTTON',
            (action: ActionItem) => {
                this.rejectListFileConfirmModal.close();
            },
            'close'
        ),
        new ActionItem(
            'SIGN.CONFIRM_REJECT_LIST_MODAL.OK_BUTTON',
            (action: ActionItem) => {
                this._rejectFileList();
            },
            'trash'
        )
    ];
    modalFirstUploadRemoteSignatureButtons: ActionItem[] =
    [
        new ActionItem(
            'SIGN.CONFIRM_SIGN_LIST_MODAL.CANCEL_BUTTON',
            (action: ActionItem) => {
                this.signSelectedModal.close();
            },
            'close'
        ),
        new ActionItem(
            'SIGN.CONFIRM_SIGN_LIST_MODAL.SIGN_REMOTE',
            (action: ActionItem) => {
                this._signFileList(); 
                this.signSelectedModal.close();
            },
            'upload'
        )
    ];
    
    ngOnInit(): void {
  
    }

	ngAfterViewInit(): void {
        this._setAccordionListener();
        this._checkFragment();
    }

    private _setAccordionListener(): void {
        this._listenShowAccordion();
        this._listenShownAccordion();
        this._listenHiddenAccordion();
    }

    private _checkFragment(): void {
        this.route.fragment
        .pipe(
            filter(
                fragment =>
                    !!fragment && fragment === 'panelGroupDocumentation'
            ),
            // takeUntil(this.destroy$)
        )
        .subscribe(() => {
            this._openAccordion('collapseDocumentation-4');
        });
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
    private _existOpenAccordion(): boolean {
        return $('#panelGroupDocumentation').find('.panel-collapse.in').length;
    }
    private _blurAllMenuItem(): void {
        $('.formNav a').blur();
    }

    private _listenShownAccordion(): void {
        $('#panelGroupDocumentation').on('shown.bs.collapse', e => {
            this._setActiveAccordion(e.target.id);
        });
    }

    private _setActiveAccordion(id: string): void {
       this.openPanel = id;
    // this.changeDetectorRef.detectChanges();
    }

    // TODO
    isReadonly(): boolean {
        return true;
    }
    // recupero dati
    get toSign() {
        return 'getDocumentsInDraft'
    }

    get rejected() {
        return 'getRejectedDocuments'
    }
    
    get signed() {
        return 'getSignedDocuments'
    }

    get loadingForSignature(): Observable<boolean> {
        return this.fileService.loadingForSignature;
    }

    // gestione eventi
    public signSectionFiles(event: any) {
        this.signFileList(event);
    }

    public rejectSectionFiles(event: any) {
        this.rejectFileList(event);
    }

    // RECUPERO DOCUMENTI E APERTURA DELLE FINESTRE MODALI ADATTE
    public signFileList(fileList: any[]){
        if(fileList.length == 0){
            this.toastr.warning(
                this.i18nService.translate(
                    'SIGN.CONFIRM_SIGN_LIST_MODAL.WARNING_SIGN_LIST_FILE.TEXT'
                ),
                this.i18nService.translate(
                    'SIGN.CONFIRM_SIGN_LIST_MODAL.WARNING_SIGN_LIST_FILE.TITLE'
                )
            );
            return;                
        }
        this.fileListToSign = fileList;
        this.signSelectedModal.open();
    }

    public rejectFileList(fileList: any[]){
        if(fileList.length == 0){
            this.toastr.warning(
                this.i18nService.translate(
                    'SIGN.CONFIRM_REJECT_LIST_MODAL.WARNING_REJECT_LIST_FILE.TEXT'
                ),
                this.i18nService.translate(
                    'SIGN.CONFIRM_REJECT_LIST_MODAL.WARNING_REJECT_LIST_FILE.TITLE'
                )
            );
            return;                
        }

        this.fileListToReject = fileList;
        this.rejectListFileConfirmModal.open()
    }

    // CHIAMATE AL SERVIZIO PER LA GESTIONE DELLE OPERAZIONI
    private _rejectFileList(){
    
        let idList : Number[] = [];
        this.fileListToReject.forEach( file => {
            idList.push(file.id)
        })  

        this.loaderService.showLoading(SectionLoading.SIGNATURE_FILE_MODAL);
        this.signService.rejectDocumentList(idList)
        .pipe( 
            catchError( res => {
                if(res.status != '200'){
                    this.toastr.error(
                        this.i18nService.translate(
                            'SIGN.CONFIRM_REJECT_LIST_MODAL.ERROR_REJECT_LIST_FILE.TEXT'
                        ),
                        this.i18nService.translate(
                            'SIGN.CONFIRM_REJECT_LIST_MODAL.ERROR_REJECT_LIST_FILE.TITLE'
                        )
                    );
                    this.rejectListFileConfirmModal.close();
                    this.loaderService.hideLoading(
                        SectionLoading.ALL_CONTENT,
                        300
                    );
                    return of()
                }
            }),
            map((res: WrapperPostPutData) => {
                this.toastr.success(
                    this.i18nService.translate(
                        'SIGN.CONFIRM_REJECT_LIST_MODAL.SUCCESS_REJECT_LIST_FILE.TEXT'
                    ),
                    this.i18nService.translate(
                        'SIGN.CONFIRM_REJECT_LIST_MODAL.SUCCESS_REJECT_LIST_FILE.TITLE'
                    )
                );
                this.rejectListFileConfirmModal.close();
            }),
        )
        .subscribe( res =>{
            if(res !== null){
                this.rejectListFileConfirmModal.close();
                this.documentRejected.doRefreshPanel();
                this.documentToSign.doRefreshPanel();
                this.fileListToReject = [];
            }

            this.loaderService.hideLoading(
                SectionLoading.ALL_CONTENT,
                300
            );
        })                   
    }
    private _signFileList(){
    
        let idList : Number[] = [];
        this.fileListToSign.forEach( file => {
            idList.push(file.id)
        })  
        const calamaioParams = new SignatureFile({
            name: ''
        });
        calamaioParams.remoteSignature = true;
        calamaioParams.calamaioRemoteUsername = $('#dlgRemoteCalamaioUID').val();
        calamaioParams.calamaioRemotePassword = $('#dlgRemoteCalamaioPwd').val();
        calamaioParams.calamaioRemoteOTP = $('#dlgRemoteCalamaioOTP').val();
        calamaioParams.calamaioRemoteDomain = $('#dlgRemoteCalamaioDomain').val();
        calamaioParams.padesCades = $('#dlgCadesPades').is(':checked');

        if(!calamaioParams.calamaioRemoteUsername || !calamaioParams.calamaioRemotePassword || !calamaioParams.calamaioRemoteOTP) {
            this.toastr.info(
                this.i18nService.translate('CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.TOOLTIP.CALAMAIO_REMOTE.ERROR'),
                this.i18nService.translate('CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.TOOLTIP.CALAMAIO_REMOTE.TITLE')
            );
            this.signSelectedModal.close();
            this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
            return;
        }

        
        
        this.loaderService.showLoading(SectionLoading.SIGNATURE_FILE_MODAL);
        this.signService.signDocumentList(idList, calamaioParams)
        .pipe( 
            catchError( res => {
                if(res.status != '200'){
                          
                    res.body.errors.forEach( err => {
                        this.toastr.error(
                            err.msg,
                            this.i18nService.translate(
                                'SIGN.CONFIRM_SIGN_LIST_MODAL.ERROR_SIGN_LIST_FILE.TEXT'
                            )
                        );
                    })
                    this.loaderService.hideLoading(SectionLoading.SIGNATURE_FILE_MODAL, 300);
                    return of()
                }
            }),           
            map((res: WrapperPostPutData) => {
                this.toastr.success(
                    this.i18nService.translate(
                        'SIGN.CONFIRM_SIGN_LIST_MODAL.SUCCESS_SIGN_LIST_FILE.TEXT'
                    ),
                    this.i18nService.translate(
                        'SIGN.CONFIRM_SIGN_LIST_MODAL.SUCCESS_SIGN_LIST_FILE.TITLE'
                    )
                );
                this.loaderService.hideLoading(SectionLoading.SIGNATURE_FILE_MODAL,300);
            }),
            )
            .subscribe( res =>{
                if(res !== null ){
                    this.documentSigned.doRefreshPanel();
                    this.documentToSign.doRefreshPanel();
                    this.fileListToSign = [];
            }
           this.loaderService.hideLoading(SectionLoading.ALL_CONTENT,300);
        })               
        
    }
    
}
