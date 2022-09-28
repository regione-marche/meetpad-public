import {
    Component,
    OnInit,
    Output,
    EventEmitter,
    ViewChild
} from '@angular/core';

import { FormControl } from '@angular/forms';

import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { ToastrService } from 'ngx-toastr';

import { I18nService } from '@eng-ds/ng-core';
import { ModalComponent, ActionItem, ErrorMessage } from '@eng-ds/ng-toolkit';

import { FormFieldGroup, BaseFile, FileService } from '@common';

import { ActionForm } from '@app/core/enums/action-form.enum';

import { environment } from '@env/environment';

import { AccreditationService } from '@features/private/core/services/accreditation/accreditation.service';

import { ConferenceStoreService, AccreditamentPreview } from '../../../core';

@Component({
    selector: 'app-conference-accreditation',
    templateUrl: './accreditation-tab.component.html',
    styleUrls: ['./accreditation-tab.component.scss']
})
export class AccreditationTabComponent implements OnInit {
    @ViewChild('docModal') modal: ModalComponent;

    @Output('onNext') onNext: EventEmitter<void> = new EventEmitter();

    groupFields: Map<string, FormFieldGroup> = new Map();
    rejectGroupFields: Map<string, FormFieldGroup> = new Map();

    accreditationView: AccreditamentPreview;

    fileUrl: File;

    accreditationRejected: boolean = false;
    confirmAccreditation: boolean = false;

    action: ActionForm = this.conferenceStoreService.getStepActionAfterIndiction();

    rejectSaveFn: (data: any) => Observable<any>;
    rejectSaveComplete: () => void;

    buttons: ActionItem[] = [];

    constructor(
        private accreditationService: AccreditationService,
        private toastr: ToastrService,
        private i18nService: I18nService,
        private conferenceStoreService: ConferenceStoreService,
        private fileService: FileService
    ) {
        this.rejectSaveFn = this._rejectSaveFn.bind(this);
        this.rejectSaveComplete = this._rejectSaveComplete.bind(this);
    }

    ngOnInit() {}

    viewModalButtons(): void {
        if (
            !this.isReadonly() &&
            !this.accreditationView.accreditamentFlag &&
            !this.accreditationView.rejectedFlag
        ) {
            this.buttons = [
                new ActionItem(
                    'CONFERENCE.WIZARD.ACCREDITATION.MODAL.CONFIRM_BUTTON',
                    (action: ActionItem) => {
                        this.confirmAccreditation = true;
                        this.buttons = [];
                    }
                ),
                new ActionItem(
                    'CONFERENCE.WIZARD.ACCREDITATION.MODAL.CANCEL_BUTTON',
                    (action: ActionItem) => {
                        this.accreditationRejected = true;
                        this.buttons = [];
                        // this.modal.close();
                    }
                )
            ];
        } else {
            this.buttons = [];
        }
    }

    showFooterBtn(): boolean {
        return this.action === ActionForm.CREATE;
    }

    isReadonly(): boolean {
        return this.action === ActionForm.READONLY;
    }

    // apertura modale in modalità lettura
    viewAccreditation(accreditation: AccreditamentPreview): void {
        this.accreditationRejected = false;
        this.confirmAccreditation = false;
        this.accreditationView = accreditation;
        this.viewModalButtons();
        this.fileUrl = accreditation.file;
        this._createGroupsFields(accreditation);
        this.openModal();
    }

    openModal(): void {
        this.modal.open();
    }

    okConfirmAccreditation(): void {
        this.accreditationView.accreditamentFlag = true;
        this.accreditationView.rejectedFlag = false;
        this.accreditationView.rejectedDescription = '';
        this.accreditationService
            .confirmOrRejectAccreditation(
                AccreditamentPreview.fromDto(this.accreditationView)
            )
            .subscribe(() => {
                this.toastr.info(
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.ACCREDITATION.TOASTR.SUCCESS_CONFIRM.TEXT'
                    ),
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.ACCREDITATION.TOASTR.SUCCESS_CONFIRM.TITLE'
                    )
                );
            });
        this.modal.close();
    }

    rejectSaveError(error: ErrorMessage): void {
        // TODO gestire gli errori
    }

    private _createGroupsFields(accreditation: AccreditamentPreview): void {
        // work arround for angular change detection
        this.groupFields = new Map();
        this.groupFields.set('user', {
            panel: false,
            panelHead: null,
            fields: new Map()
                .set('name', {
                    type: 'text',
                    label: 'CONFERENCE.JOIN.MODAL.FORM.NAME',
                    control: new FormControl({
                        value: accreditation.name,
                        disabled: true
                    }),
                    size: '12|6|6'
                })
                .set('surname', {
                    type: 'text',
                    label: 'CONFERENCE.JOIN.MODAL.FORM.SURNAME',
                    control: new FormControl({
                        value: accreditation.surname,
                        disabled: true
                    }),
                    size: '12|6|6'
                })
                .set('fiscalCode', {
                    type: 'text',
                    label: 'CONFERENCE.JOIN.MODAL.FORM.FISCAL_CODE',
                    control: new FormControl({
                        value: accreditation.fiscalCode,
                        disabled: true
                    }),
                    size: '12|6|6'
                })
                .set('profile', {
                    type: 'text',
                    label: 'CONFERENCE.JOIN.MODAL.FORM.PROFILE',
                    size: '12|6|6',
                    control: new FormControl({
                        value:
                            environment.defaultComboBox.conference.participants
                                .users.profile.value,
                        disabled: true
                    })
                })
                .set('email', {
                    type: 'text',
                    label: 'CONFERENCE.JOIN.MODAL.FORM.EMAIL',
                    control: new FormControl({
                        value: accreditation.email,
                        disabled: true
                    }),
                    size: '12|6|6'
                })
              /*  .set('file', {
                    type: 'file',
                    label: 'CONFERENCE.JOIN.MODAL.FORM.FILE',
                    size: '12|6|6',
                    readonly: true,
                    file: new BaseFile({
                        url: accreditation.url,
                        name: this.i18nService.translate(
                            'CONFERENCE.WIZARD.ACCREDITATION.MODAL.DOWNLOAD'
                        )
                    }),
                    onDownload: (file: BaseFile) => {
                        //this.fileService.downloadByApiName('downloadFile',{id: file.url.substring(file.url.lastIndexOf('/'))}).pipe(   
                        this.fileService.download(new BaseFile({ url: file.url})).pipe(                                            
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
                                return of(null);
                            });
                    }
                })*/
        });

        this.rejectGroupFields = new Map();
        this.rejectGroupFields.set('rejected', {
            panel: false,
            panelHead: null,
            fields: new Map().set('rejectedDescription', {
                type: 'text',
                label: 'CONFERENCE.JOIN.MODAL.FORM.NOTE',
                control: new FormControl(accreditation.rejectedDescription),
                size: '12|12|12',
                disabled: false
            })
        });
    }

    private _rejectSaveFn(data: any): Observable<any> {
        this.accreditationView.accreditamentFlag = false;
        this.accreditationView.rejectedFlag = true;
        this.accreditationView.rejectedDescription =
            data.rejected.rejectedDescription;
        return this.accreditationService.confirmOrRejectAccreditation(
            AccreditamentPreview.fromDto(this.accreditationView)
        );
    }

    private _rejectSaveComplete(): void {
        this.toastr.info(
            this.i18nService.translate(
                'CONFERENCE.WIZARD.ACCREDITATION.TOASTR.SUCCESS_REJECT.TEXT'
            ),
            this.i18nService.translate(
                'CONFERENCE.WIZARD.ACCREDITATION.TOASTR.SUCCESS_REJECT.TITLE'
            )
        );
        this.modal.close();
        this.accreditationRejected = false;
    }
}
