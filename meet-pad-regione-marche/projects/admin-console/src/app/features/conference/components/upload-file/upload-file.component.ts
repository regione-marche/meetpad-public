import { Component, OnInit, ViewChild } from '@angular/core';
import {
    FormFieldGroup,
    ComboBox,
    LoaderService,
    SectionLoading,
    FileService,
    AutoUnsubscribe,
    BaseFile,
    SelectValueValidator,
    FormField
} from '@common';
import { FormControl, Validators } from '@angular/forms';
import { of, Observable, Subject } from 'rxjs';
import { ConsoleFile } from '../../core/models/console-file.model';
import { UtilityService } from '../../../../core/services/utility.service';
import { ActivatedRoute } from '@angular/router';
import {
    ErrorLabelConstants,
    ModalComponent,
    ActionItem
} from '@eng-ds/ng-toolkit';
import { Conference } from '../../../authorities/core/models/conference.model';
import { takeUntil, catchError } from 'rxjs/operators';
import { ToastrService } from 'ngx-toastr';
import { I18nService } from '@eng-ds/ng-core';

@Component({
    // tslint:disable-next-line: component-selector
    selector: 'admin-upload-file',
    templateUrl: './upload-file.component.html',
    styleUrls: ['./upload-file.component.scss']
})
export class UploadFileComponent extends AutoUnsubscribe implements OnInit {
    @ViewChild('confirmModal') confirmModal: ModalComponent;

    groupFields: Map<string, FormFieldGroup> = new Map();
    model: ConsoleFile = new ConsoleFile();
    modelFile: File;
    conference: Conference = new Conference();
    conferenceAdministations: Observable<ComboBox[] & any> = of([]);
    optionsType: Observable<
        ComboBox[] & any
    > = this.utilityService.getDocumentType();
    optionsCategory: Observable<ComboBox[] & any> = of([]);

    modalButtons: ActionItem[] = [
        new ActionItem(
            'CONFERENCE.UPLOAD.MODAL.CANCEL_BUTTON',
            (action: ActionItem) => {
                this.confirmModal.close();
            },
            'close'
        ),
        new ActionItem(
            'CONFERENCE.UPLOAD.MODAL.OK_BUTTON',
            (action: ActionItem) => {
                this.uploadFile(this.modelFile);
                this.confirmModal.close();
            },
            'upload'
        )
    ];

    constructor(
        private utilityService: UtilityService,
        private route: ActivatedRoute,
        private loaderService: LoaderService,
        private fileService: FileService,
        private toastr: ToastrService,
        private i18nService: I18nService
    ) {
        super();
    }

    ngOnInit() {
        if (this.route.snapshot.parent.params.id) {
            this.conference = Conference.fromDto(
                this.route.snapshot.data.conference
            );
            this.conference.id = this.route.snapshot.parent.params.id;

            this.conferenceAdministations = this.utilityService.getParticipantForConference(
                this.conference.id
            );
        }

        this._createForm();
        this.loaderService.hideLoading(SectionLoading.ALL_CONTENT, 500);
    }

    openUploadModal(file: File) {
        this.modelFile = file;
        this.confirmModal.open();
    }

    uploadFile(file: File): void {
        const formValue = this.groupFields.get('filePanel').fields;

        const newFile = new ConsoleFile({
            name: file.name,
            type: formValue.get('type').control.value.key,
            meetingDate: formValue.get('meetingDate')
                ? formValue.get('meetingDate').control.value
                : null
        });

        if (formValue.get('visibility').control.value.value) {
            newFile.setVisibility([]);
        } else {
            newFile.setVisibility(formValue.get('visibility').control.value);
        }
        newFile.setCategory(formValue.get('category').control.value);

        this.fileService.uploadConsoleFile(this.conference.id, newFile, file);
    }

    private _createForm(): void {
        const tmpFields = new Map()
            .set('meetingDate', {
                type: 'date',
                label: 'CONFERENCE.UPLOAD.FILE.MEETING_DATE',
                control: new FormControl(this.model.meetingDate, [
                    Validators.required
                ]),
                size: '12|4|4'
            })
            .set('visibility', {
                type: 'select-two',
                label: 'CONFERENCE.UPLOAD.FILE.VISIBILITY',
                control: new FormControl(this.model.visibility),

                placeholder: 'COMMON.ALL',
                size: '12|6|6',
                options: this.conferenceAdministations
            })
            .set('file', {
                type: 'file',
                onUpload: this.openUploadModal.bind(this)
            });

        this.groupFields.set('filePanel', {
            panel: false,
            panelHead: null,
            fields: new Map()
                .set('type', {
                    type: 'select',
                    label: 'CONFERENCE.UPLOAD.FILE.TYPE',
                    control: new FormControl(this.model.type, [
                        Validators.required,
                        SelectValueValidator
                    ]),
                    size: '12|6|6',
                    options: this.optionsType,
                    errorLabels: [ErrorLabelConstants.REQUIRED],
                    valueChange: (value: ComboBox) => {
                        this.groupFields
                            .get('filePanel')
                            .fields.get(
                                'category'
                            ).options = this.utilityService.getCategoriesFileForDocumentType(
                            this.conference.conferenceType.key,
                            value.key
                        );
                    }
                })
                .set('category', {
                    type: 'select',
                    label: 'CONFERENCE.UPLOAD.FILE.CATEGORY',
                    control: new FormControl(this.model.category, [
                        Validators.required,
                        SelectValueValidator
                    ]),
                    size: '12|6|6',
                    options: this.optionsCategory,
                    errorLabels: [ErrorLabelConstants.REQUIRED],
                    valueChange: (value: ComboBox) => {
                        if (value.key === '16') {
                            this.groupFields
                                .get('filePanel')
                                .fields.delete('visibility');
                            this.groupFields
                                .get('filePanel')
                                .fields.delete('file');
                            this.groupFields
                                .get('filePanel')
                                .fields.get('type').size = '12|4|4';
                            this.groupFields
                                .get('filePanel')
                                .fields.get('category').size = '12|4|4';

                            tmpFields.forEach(
                                (value: FormField, key: string) => {
                                    this.groupFields
                                        .get('filePanel')
                                        .fields.set(key, value);
                                }
                            );
                        } else {
                            this.groupFields
                                .get('filePanel')
                                .fields.delete('date');
                        }
                        this.groupFields = new Map(this.groupFields);
                    }
                })
                .set('visibility', {
                    type: 'select-two',
                    label: 'CONFERENCE.UPLOAD.FILE.VISIBILITY',
                    control: new FormControl(this.model.visibility),

                    placeholder: 'COMMON.ALL',
                    size: '12|6|6',
                    options: this.conferenceAdministations
                })
                .set('file', {
                    type: 'file',
                    onUpload: this.openUploadModal.bind(this)
                })
        });
        // modificare e settare da env
        this.groupFields
            .get('filePanel')
            .fields.get(
                'category'
            ).options = this.utilityService.getCategoriesFileForDocumentType(
            this.conference.conferenceType.key,
            ''
        );
        this.groupFields = new Map(this.groupFields);
    }
}
