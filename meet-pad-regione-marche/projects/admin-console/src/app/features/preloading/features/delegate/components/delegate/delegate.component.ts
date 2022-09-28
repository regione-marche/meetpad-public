import { Component, OnInit, ViewChild } from '@angular/core';
import {
    AutoUnsubscribe,
    WrapperPostPutData,
    FormFieldGroup,
    ComboBox,
    LoaderService,
    SectionLoading,
    FormField,
    WrapperResponse,
    SelectValueValidator,
    MessageToastrService,
    FileService,
    BaseFile,
    FormButton,
    FooterButtons
} from '@common';
import { Observable, of, Subscription } from 'rxjs';
import { ErrorMessage, ErrorLabelConstants, ModalComponent, ActionItem } from '@eng-ds/ng-toolkit';
import { ActivatedRoute, Router } from '@angular/router';
import { FormControl, Validators } from '@angular/forms';
import { takeUntil, map, catchError } from 'rxjs/operators';
import { Delegate } from '../../models/delegate.model';
import { UtilityService } from '../../../../../../core/services/utility.service';
import { HttpResponse } from '@angular/common/http';
import { DelegateService } from '../../services/delegate.service';
import { SearchDelegate } from '../../models/search-delegates.model';


@Component({
  selector: 'admin-delegate',
  templateUrl: './delegate.component.html',
  styleUrls: ['./delegate.component.scss']
})
export class DelegateComponent extends AutoUnsubscribe implements OnInit {

    @ViewChild('confirmUploadModal') confirmUploadModal: ModalComponent;
    @ViewChild('confirmModal') confirmModal: ModalComponent;

    createMode: boolean = true;
    conferenceType: string;
    onNext: (data: Delegate) => Observable<WrapperPostPutData>;
    saveComplete: (response: WrapperPostPutData) => void;
    saveError: (error: ErrorMessage) => void;

    groupFields: Map<string, FormFieldGroup> = new Map();
    model: Delegate = new Delegate();
    competenceAuthorization: ComboBox[];
    _documentFile: File;
    _attachmentFlag: boolean = false;
    fileDownloadSubscription: Subscription;

    constructor(
        private route: ActivatedRoute,
        private loaderService: LoaderService,
        private delegatesService: DelegateService,
        private utilityService: UtilityService,
        private messageToastr: MessageToastrService,
        public fileService: FileService,
        private router: Router
    ) {
        super();
    }

    ngOnInit() {
        if (this.route.snapshot.data.delegate) {
            const a = this.route.snapshot.data.delegate;
            this.createMode = false;
            this.model = new Delegate(Delegate.fromDto(a));
        }
        this.route.parent.params
            .pipe(takeUntil(this.destroy$))
            .subscribe(value => {
                this.conferenceType = value.conferenceType;
            });
        this._createForm();
        this._initCallbacks();
    }

    private _createForm(): void {
        this.groupFields.set('delegate', {
            panel: true,
            panelHead: 'PRELOADING.DELEGATE.EDIT.TITLE',
            accordion: false,
            fields: new Map()
            .set('delegate-preload', {
                type: 'autocomplete',
                label: 'PRELOADING.DELEGATE.EDIT.SEARCH',
                size: '12|12|12',
                control: new FormControl(),
                notFoundText:
                    'CONFERENCE.AUTOCOMPLETE.INSERT_MIN_CHARACTERS',
                loading: false,
                valueChange: (delegate: ComboBox) => {
                    if (delegate && delegate.key) {
                        return this.delegatesService.getDelegatePrecomplete(delegate.key)
                            .pipe(takeUntil(this.destroy$))
                            .subscribe((value: Delegate) => {
                                const fields = this.groupFields.get(
                                    'delegate'
                                ).fields;
                                const _delegate = Delegate.fromDto(value);
                                fields
                                    .get('name')
                                    .control.setValue(_delegate.name);

                                fields
                                    .get('fiscalCode')
                                    .control.setValue(
                                        _delegate.fiscalCode
                                    );

                                fields
                                    .get('surname')
                                    .control.setValue(_delegate.surname);

                                fields
                                    .get('email')
                                    .control.setValue(_delegate.email);
                                
                                if(_delegate.documentName != null){
                                    fields
                                        .get('documentName')
                                        .control.setValue(_delegate.documentName);
                                }
                            });
                    }
                }
            })
            .set('name', {
                type: 'text',
                label: 'PRELOADING.DELEGATE.EDIT.NAME',
                control: new FormControl(this.model.name, [
                    Validators.required
                ]),
                size: '12|6|6'
            })
            .set('surname', {
                type: 'text',
                label: 'PRELOADING.DELEGATE.EDIT.SURNAME',

                control: new FormControl(this.model.surname, [
                    Validators.required
                ]),
                size: '12|6|6'
            })
            .set('email', {
                type: 'text',
                label: 'PRELOADING.DELEGATE.EDIT.EMAIL',
                control: new FormControl(this.model.email, [
                    Validators.required
                ]),
                size: '12|6|6'
            })
            .set('fiscalCode', {
                type: 'text',
                label: 'PRELOADING.DELEGATE.EDIT.FISCAL_CODE',
                pattern:
                    '(^[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]$)|(^[0-9]{11}$)',
                control: new FormControl(this.model.fiscalCode, [
                    Validators.required
                ]),
                errorLabels: [ErrorLabelConstants.REGEX('ERROR.REGEX')],
                size: '12|6|6'
            })
        });

        if(this.model.documentName != null){      
            this.groupFields.get('delegate').fields.set('file', {
                type: 'file',
                label: 'PRELOADING.DELEGATE.EDIT.DOCUMENT_NAME' || ' ',
                pattern:
                '(^[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]$)|(^[0-9]{11}$)',
                control: new FormControl(this.model.documentName),
                readonly: true,
                file: new BaseFile({
                    url: this.model.id,
                    name: this.model.documentName
                }),
                onDownload: (file: BaseFile) => {
                   this.downloadFile(file);
                },
                size: '12|6|6',
            })
        }else{
            this.groupFields.get('delegate').fields.set('file', {
                type: 'file',
                hideSubmitBtn: true,
                control: new FormControl(this.model.file),
                onSelect: this.onSelectDocument.bind(this)
            })
        }
        
        if (this.createMode) {
            this._initDelegateAutocomplete();
        } else {
            this.groupFields
                .get('delegate')
                .fields.delete('delegate-preload');
        }

        this.groupFields = new Map(this.groupFields);
    }

    onSelectDocument(file: File) {
        
        this._attachmentFlag = true;
        this._documentFile = file;
    }

    saveFile(data: Delegate): Observable<WrapperPostPutData> {
        return this.delegatesService.saveFile(this.model, this.conferenceType, this._documentFile)
                .pipe(
                    catchError((response: HttpResponse<WrapperResponse>) => {
                        return this._saveError(response);
                    })
                );
    }
 
    downloadFile(file: BaseFile): void {
        if (
            this.fileDownloadSubscription instanceof
            Subscription
        ) {
            this.fileDownloadSubscription.unsubscribe();
        }
        this.fileDownloadSubscription = this.fileService
            .downloadByApiName(
                'preloading/downloadDelegateFile',
                {
                    id: this.model.id
                }
            )
            .subscribe(_ => {
                this.loaderService.hideLoading(
                    SectionLoading.EVENTS_MODAL
                );
            });
    }

    private _initCallbacks(): void {
        this.onNext = this._onNext.bind(this);
        this.saveComplete = this._saveComplete.bind(this);
        this.saveError = () => {};
        this.loaderService.hideLoading(SectionLoading.ALL_CONTENT, 500);
    }

    private _initDelegateAutocomplete(): void {
        this._initFieldFormAutocomplete(
            this.groupFields.get('delegate').fields.get('delegate-preload'),
            this.utilityService.getDelegateAutocomplete.bind(
                this.utilityService
            )
        );
    }

    private _saveComplete(response: WrapperResponse<any>): void {
        if (this.createMode) {
            this.router.navigate(['../', response['id']], {
                relativeTo: this.route
            });

            this.messageToastr.showMessage(
                response,
                'PRELOADING.DELEGATE.EDIT',
                'NEW'
            );
        } else {
            this.messageToastr.showMessage(
                response,
                'PRELOADING.DELEGATE.EDIT',
                'EDIT'
            );
        }
    }

    private _saveError(error: HttpResponse<WrapperResponse>): Observable<null> {
        if (this.createMode) {
            return this.messageToastr.showErrorMessage(
                error,
                'PRELOADING.DELEGATE.EDIT',
                'NEW'
            );
        } else {
            return this.messageToastr.showErrorMessage(
                error,
                'PRELOADING.DELEGATE.EDIT',
                'EDIT'
            );
        }
    }
    private _onNext(data: Delegate): Observable<WrapperPostPutData> {
        data.file = this._documentFile;
        if (this.createMode) {
            return this.delegatesService
                .saveFile(data, this.conferenceType, this._documentFile)
                .pipe(
                    catchError((response: HttpResponse<WrapperResponse>) => {
                        return this._saveError(response);
                    })
                );
        } else {
            data.conferenceType = this.model.conferenceType;
            return this.delegatesService
                //.edit(data, this.conferenceType, this.model.id)
                .updateDelegate(data,this.conferenceType,this.model.id, this._documentFile)
                .pipe(
                    catchError((response: HttpResponse<WrapperResponse>) => {
                        return this._saveError(response);
                    })
                );
        }
    }

    isReadonly(): boolean {
        return !this.createMode;
    }
}
