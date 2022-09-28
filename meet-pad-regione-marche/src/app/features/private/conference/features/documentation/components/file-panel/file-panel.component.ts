/* tslint:disable:no-inferrable-types */
import { Component, Input, EventEmitter, Output } from '@angular/core';
import { FormFieldGroup, BaseFile, WrapperPostPutData, Alert } from '@common';
import { Folder, SharedFile } from '@app/features/private/conference/core';
import { Observable } from 'rxjs';
import { ErrorMessage } from '@eng-ds/ng-toolkit';

declare var $: any;

@Component({
    selector: 'app-file-panel',
    templateUrl: './file-panel.component.html',
    styleUrls: ['./file-panel.component.scss']
})
export class FilePanelComponent {
    @Input('title') title: string;
    @Input('accordionIndex') accordionIndex: number;
    @Input('readonly') readonly: boolean;
    @Input('showSyncronize') showSyncronize: boolean = false;
    @Input('showDeleteList') showDeleteList: boolean = false;
    @Input('showSectionDeleteList') showSectionDeleteList: boolean = false;
    @Input('showSectionDownload') showSectionDownload: boolean = false;
    @Input('selectionEnabled') selectionEnabled: boolean = false;
    @Input('plusSignIsVisble') plusSignIsVisble: boolean;
    @Input('files') files: BaseFile[] | Folder<BaseFile>[];
    @Input('multiple') multiple: boolean = true;
    @Input('groupFields') groupFields: Map<string, FormFieldGroup>;
    @Input('sharedDocumentation') sharedDocumentation: boolean = false;
    @Input('additionalDocumentation') additionalDocumentation: boolean = false;
    @Input('isImpersonatedAdmin') isImpersonatedAdmin: boolean = false;
    @Input('statusDocumentation') statusDocumentation: boolean = false;

    @Input('saveFn') saveFn: (
        data: SharedFile
    ) => Observable<WrapperPostPutData>;
    @Input('saveButtonText') saveButtonText: string;
    @Input('saveComplete') saveComplete: (response: WrapperPostPutData) => void;
    @Input('saveError') saveError: (error: ErrorMessage) => void;

    @Input('textAlert') textAlert: string;
    @Input('typeAlert') typeAlert: Alert;
    @Input('showAlert') showAlert: boolean;

    @Output('openEditFile')
    openEditFile: EventEmitter<any> = new EventEmitter();
    @Output('deleteFile') deleteFile: EventEmitter<any> = new EventEmitter();
    // tslint:disable-next-line: no-output-rename
    @Output('openEditCloud') openEditCloud: EventEmitter<
        any
    > = new EventEmitter();
    @Output('downloadFile') downloadFile: EventEmitter<
        any
    > = new EventEmitter();
    @Output('downloadPDFFile') downloadPDFFile: EventEmitter<any> = new EventEmitter();
    @Output('downloadSectionFiles') downloadSectionFiles: EventEmitter<any> = new EventEmitter();
    @Output('deleteSectionFiles') deleteSectionFiles: EventEmitter<any> = new EventEmitter();

    @Output('openInputForm') openInputForm = new EventEmitter<any>();
    @Output('syncronizeFiles') syncronizeFiles = new EventEmitter<any>();
    @Output('deleteFileList') deleteFileList = new EventEmitter<any>();

    private _showForm = false;
    private forceHide = false;
    plusSignIsVisbleForRole = true;

    get showForm(): boolean {
        return (
            ((!this.readonly && (!this.multiple && !this.files.length)) ||
                this._showForm) &&
            !this.forceHide
        );
    }

    get hasFolders(): boolean {
        if (this.files[0]) {
            return this.files[0] instanceof Folder;
        }
        return false;
    }

    get folders(): Folder<BaseFile>[] {
        return this.files as Folder<BaseFile>[];
    }

    private _openPanel(): void {
        setTimeout(() => {
            $(`#collapseDocumentation-${this.accordionIndex}`).collapse('show');
        }, 40);
    }

    private _closePanel(): void {
        $(`#collapseDocumentation-${this.accordionIndex}`).collapse('hide');
    }

    private _isPanelOpen(): boolean {
        return $(
            `#collapseDocumentation-${this.accordionIndex}.panel-collapse.in`
        ).length;
    }

    toogleForm() {
        if (!this.readonly && this.multiple) {
            if (this.showForm) {
                // il form cè
                if (!this._isPanelOpen()) {
                    // il pannello è chiuso
                    this._openPanel(); // va aperto
                    this.openInputForm.emit();
                } else {
                    this._showForm = !this._showForm;
                    if (!this.files.length) {
                        // il pannello va chiuso se non ci sono utenti in tabella
                        this._closePanel();
                    }
                }
            } else {
                this._showForm = !this._showForm;
                this._openPanel();
                this.openInputForm.emit();
            }
        }
    }

    hideForm(force: boolean = false): void {
        this.forceHide = force;
        this._showForm = false;
    }

    showPlusIcon()
    {
        return this.plusSignIsVisble && this.multiple && this.plusSignIsVisbleForRole
    }

    deleteFilesSelected(){
        let fileList = []

        let folders : any = this.files;
        folders.forEach( folder => {  
            folder.files.forEach( file =>{ 
                if(file.selected){
                    fileList.push(file)
                }
            }) 
        });

        this.deleteFileList.emit(fileList)
    }

    doDownloadSectionFiles(event: any[], folder: any) {
        event.splice(0, 0, folder);
        this.downloadSectionFiles.emit(event);
    }

}
