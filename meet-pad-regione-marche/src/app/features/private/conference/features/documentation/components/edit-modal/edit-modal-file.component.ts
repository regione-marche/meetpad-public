/* tslint:disable:no-inferrable-types */

import { Component, ViewChild, Input } from '@angular/core';

import { Observable } from 'rxjs';

import { ModalComponent, ErrorMessage } from '@eng-ds/ng-toolkit';

import {
    ComboBox,
    AutoUnsubscribe,
    FormFieldGroup,
    FormComponent
} from '@common';

import { BaseFile } from 'projects/common/src/lib/models/base-file.model';

@Component({
    selector: 'app-edit-modal-file',
    templateUrl: './edit-modal-file.component.html',
    styleUrls: ['./edit-modal-file.component.scss']
})
export class EditModalFileComponent extends AutoUnsubscribe {
    @Input('file') file: BaseFile;

    @Input('disabledTitle') disabledTitle: string = 'CONFERENCE.WIZARD.DOCUMENTATION.ADDITIONAL.MODAL.TITLE_DISABLED';
    @Input('enabledTitle') enabledTitle: string = 'CONFERENCE.WIZARD.DOCUMENTATION.ADDITIONAL.MODAL.TITLE';

    @Input('disabled') disabled: boolean = false;
    @Input('participants') participants: ComboBox[];

    @Input('groupFields') groupFields: Map<string, FormFieldGroup> = new Map();

    @Input('save') save: (data: { file: BaseFile }) => Observable<BaseFile>;
    @Input('saveComplete') saveComplete: (response: BaseFile) => void;
    @Input('saveError') saveError: (error: ErrorMessage) => void;
    @Input('fileName') fileName: string;

    @ViewChild('docModal') modal: ModalComponent;
    @ViewChild('formCmp') formCmp: FormComponent;

    constructor() {
        super();
    }

    openModal(): void {
        this.modal.open();
    }

    get title(): string {
        if (this.disabled) {
            return this.disabledTitle;
        } else {
            return this.enabledTitle;
        }
    }

    close(): void {
        this.modal.close();
    }
}
