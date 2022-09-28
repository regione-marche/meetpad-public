/* tslint:disable:no-inferrable-types */
import { Component, Input, EventEmitter, Output } from '@angular/core';
import { FilePanelComponent } from '../file-panel/file-panel.component';
import { SignatureFile } from '@app/features/private/conference/core/models/documentation/signature-file.model';
import { BaseFile } from '@common/common';

declare var $: any;

@Component({
    selector: 'app-file-panel-sign',
    templateUrl: './file-panel-sign.component.html',
    styleUrls: ['./file-panel-sign.component.scss']
})
export class FilePanelSignComponent extends FilePanelComponent {
    @Output('signFile') signFile = new EventEmitter<BaseFile>();
    @Output('deleteFile') deleteFile = new EventEmitter<BaseFile>();
    @Output('unlockFile') unlockFile = new EventEmitter<SignatureFile>();
    @Output('downloadFile') downloadFile = new EventEmitter<SignatureFile>();
    @Output('downloadCalamaioApplication') downloadCalamaioApplication = new EventEmitter<any>();
    @Input('isDelegate') isDelegate: boolean = false;

    public isAdmin = false;

    showPlusIcon()
    {
        return this.plusSignIsVisble && this.multiple && (this.plusSignIsVisbleForRole || this.isDelegate)
    }

}
