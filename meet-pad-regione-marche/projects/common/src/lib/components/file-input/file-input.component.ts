import {
    Component,
    EventEmitter,
    AfterViewInit,
    Output,
    Input,
    OnChanges,
    SimpleChanges
} from '@angular/core';
import $ from 'jquery';

import { BootstrapSize, TooltipModel } from '@eng-ds/ng-toolkit';
import { BaseFile } from '../../models/base-file.model';
import { FormStoreService } from '../form/services/form-store.service';

// type BaseFile = any;
// type FormStoreService = any;

@Component({
    selector: 'app-file-input',
    templateUrl: './file-input.component.html',
    styleUrls: ['./file-input.component.scss']
})
export class FileInputComponent implements AfterViewInit, OnChanges {
    // tslint:disable-next-line:no-output-on-prefix
    @Output('onSelect') onSelect = new EventEmitter<any>();
    @Output('onUpload') onUpload = new EventEmitter<any>();
    @Output('onDownload') onDownload = new EventEmitter<BaseFile>();

    @Input('required') required: boolean = false;
    @Input('disabled') disabled: boolean = false;
    @Input('submitDisabled') submitDisabled: boolean;
    @Input('hideSubmitBtn') hideSubmitBtn: boolean;
    @Input('label') label: string;
    @Input('tooltip') tooltip: TooltipModel;

    @Input('readonly') readonly: boolean;
    // file type sbagliato cambiare
    @Input('file') file: File | BaseFile | any;

    chooseFileBtn: boolean = true;
    id: string = 'file_' + Math.round(Math.random() * 1000);
    idFile: string='addFile_' + Math.round(Math.random() * 1000);

    /**
     * Bootstrap size of input
     */
    _size: BootstrapSize = new BootstrapSize(12, 12, 12);

    /**
     * Define the bootstrap size of input
     */
    @Input()
    set size(pipes: string | BootstrapSize) {
        if (pipes instanceof BootstrapSize) {
            this._size = pipes;
        } else if (pipes === undefined) {
            this._size = new BootstrapSize(12, 12, 12);
        } else {
            this._size = BootstrapSize.fromPipe(pipes);
        }
    }

    constructor(public formStore: FormStoreService) {}

    private _checkValid(): void {
        this.formStore.valid = !this.required || (this.required && !!this.file);
    }

    ngOnChanges(changes: SimpleChanges): void {
        if (this.submitDisabled) {
            $('.add-file')
                .addClass('disabled')
                .attr('disabled', 'disabled');
        } else {
            if (!this.chooseFileBtn) {
                $('.add-file')
                    .removeClass('disabled')
                    .removeAttr('disabled');
            }
        }
    }

    ngAfterViewInit() {
        this.formStore.valid = !this.required;

        // /* Carica File */
        const _this = this;
        const uploadFiles = $(`#${this.id} .uploadFile`);
        if (uploadFiles.length > 0) {
            for (let i = 0; i < uploadFiles.length; i++) {
                const uploadFile = uploadFiles.eq(i);
                uploadFile.find('.fileUpload :file').on('change', function() {
                    _this.chooseFileBtn = false;

                    if (!_this.submitDisabled) {
                        $(this)
                            .parent()
                            .parent()
                            .next()
                            .find('button')
                            .removeClass('disabled')
                            .removeAttr('disabled');
                    }

                    $(this)
                        .parent()
                        .addClass('disabled');

                    const input = $(this),
                        label = input
                            .val()
                            .replace(/\\/g, '/')
                            .replace(/.*\//, '');
                    // tslint:disable-next-line:no-shadowed-variable
                    const uploadFile = input.closest('.uploadFile');
                    uploadFile
                        .find('.upload-file-button-container input:text')
                        .val(label);
                    // uploadFile.find(".fileUpload").hide();
                    // uploadFile.find(".fileSubmit").show();

                    uploadFile.find('.innerx').click(function(e) {
                        $(this)
                            .parent()
                            .parent()
                            .find('.upload-container')
                            .removeClass('disabled');
                        $(this)
                            .parent()
                            .parent()
                            .find('.add-file')
                            .addClass('disabled')
                            .attr('disabled', 'disabled');
                        // tslint:disable-next-line:no-shadowed-variable
                        const uploadFile = $(this).closest('.uploadFile');
                        e.stopImmediatePropagation();
                        e.preventDefault();
                        uploadFile.find('.fileUpload').show();
                        // uploadFile.find(".fileSubmit").hide();
                        uploadFile
                            .find('.upload-file-button-container input:text')
                            .val('');
                        uploadFile.find('.fileUpload input:file').val('');

                        _this.file = null;
                        _this._checkValid();
                    });

                    const cnt: HTMLInputElement = document.querySelector(
                        `#${this.id}`
                    );

                    _this.file = cnt.files[0];
                    if (_this.onSelect) {
                        _this.onSelect.emit(_this.file);
                        _this._checkValid();
                    }
                });
            }
        }

        /*INPUT FILE FUNCTION*/
        $(`#${this.id} .btn-file :file`).on('fileselect', function(
            event,
            numFiles,
            label
        ) {
            const input = $(this)
                    .parents('.upload-file-container')
                    .find(':text'),
                log = numFiles > 1 ? numFiles + ' files selezionati' : label;

            if (input.length) {
                input.val(log);
            } else {
                if (log) {
                    alert(log);
                }
            }
        });

        $(document).on('change', `#${this.id} .btn-file :file`, function() {
            const input = $(this),
                numFiles = input.get(0).files ? input.get(0).files.length : 1,
                label = input
                    .val()
                    .replace(/\\/g, '/')
                    .replace(/.*\//, '');
            input.trigger('fileselect', [numFiles, label]);
        });
    }

    uploadFile() {
        const cnt: HTMLElement = document.querySelector(`#${this.id}`);
        //const input: HTMLInputElement = cnt.querySelector('#addfile');
        const input: HTMLInputElement = cnt.querySelector(`#${this.idFile}`);

        if (!input) {
            // alert("Um, couldn't find the fileinput element.");
        } else if (!input.files) {
            // alert("This browser doesn't seem to support the `files` property of file inputs.");
        } else if (!input.files[0]) {
            // alert("Please select a file before clicking 'Load'");
        } else {
            // const fileReader = new FileReader();
            // fileReader.readAsDataURL(input.files[0]);
            // console.log(input.files[0]);
            // const fileName: string = $('.fileUpload input:file')
            //     .val()
            //     .substring(12, $('.fileUpload input:file').val().length);
            // this.onNewUpload.emit({ fileReader, fileName });

            this.file = input.files[0];
            if (this.onUpload) {
                this.onUpload.emit(this.file);
                this._checkValid();
            }
            $(`#${this.idFile}`)
                .parent()
                .removeClass('disabled');
            $('.add-file')
                .addClass('disabled')
                .attr('disabled', 'disabled');
            $('.upload-file-button-container input:text').val('');
            $('.fileUpload input:file').val('');
        }
    }

    downloadFile(): void {
        this.onDownload.emit(this.file as BaseFile);
    }
}
