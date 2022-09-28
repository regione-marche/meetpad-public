import { Component, Input } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { LoggerService } from '@eng-ds/ng-core';

import { BootstrapSize } from '@eng-ds/ng-toolkit';
import { FormField } from '../../interfaces';

@Component({
    selector: 'app-inputs-form',
    templateUrl: './inputs-form.component.html',
    styleUrls: ['./inputs-form.component.scss']
})
export class InputsFormComponent {
    @Input('groupName') groupName: string;
    // type di fields sbagliato RIVEDERE
    @Input('fields') fields: any;
    @Input('form') form: FormGroup;
    @Input('errorLabels') errorLabels: any;

    @Input('submitDisabled') submitDisabled: boolean;

    constructor(private loggerService: LoggerService) {}

    getBootstrapSizeClass(size: string | BootstrapSize): string {
        if (size instanceof BootstrapSize) {
            return size.toBootstrapClass();
        } else if (size === undefined) {
            return new BootstrapSize(12, 12, 12).toBootstrapClass();
        } else {
            return BootstrapSize.fromPipe(size).toBootstrapClass();
        }
    }

    isSelect(field: FormField): boolean {
        return field.type === 'select';
    }

    isSwitch(field: FormField): boolean {
        return field.type === 'switch';
    }

    isText(field: FormField): boolean {
        return field.type === 'text';
    }

    isDate(field: FormField): boolean {
        return field.type === 'date';
    }

    isRadio(field: FormField): boolean {
        return field.type === 'radio';
    }

    isTextArea(field: FormField): boolean {
        return field.type === 'text-area';
    }

    isSingleTextbox(field: FormField): boolean {
        return field.type === 'single-textbox';
    }

    isTagInput(field: FormField): boolean {
        return field.type === 'tag-input';
    }

    isSelectMultiple(field: FormField): boolean {
        return field.type === 'select-two';
    }

    isAutocomplete(field: FormField): boolean {
        return field.type === 'autocomplete';
    }

    isFile(field: FormField): boolean {
        return field.type === 'file';
    }

    isDropFile(field: FormField): boolean {
        return field.type === 'drop-file';
    }

    isRequired(field: FormField): boolean {
        let required = false;

        if (field.required === true) {
            required = true;
        }

        if (field.control && field.control.validator) {
            const validationResult = field.control.validator(new FormControl());
            required =
                validationResult !== null && validationResult.required === true;
        }
        return required;
    }

    isNop(field: FormField): boolean {
        return field.type === 'nop';
    }

    valueChanges(evt) {
        this.loggerService.log('valueChange', evt);
    }

    getFieldName(fieldName: string) {
        return this.groupName ? `${this.groupName}.${fieldName}` : fieldName;
    }

    getErrorLabel(fieldName: string) {
        return this.groupName
            ? this.errorLabels[this.groupName][fieldName]
            : this.errorLabels[fieldName];
    }

    onFileSelect(field: FormField, file: File): void {
        if (field.onSelect && typeof field.onSelect === 'function') {
            field.onSelect(file);
        }
    }

    onFileUpload(field: FormField, file: File): void {
        if (field.onUpload && typeof field.onUpload === 'function') {
            field.onUpload(file);
        }
    }

    onFilesUpload(field: FormField, files: File[]): void {
        if (
            field.onMultipleUpload &&
            typeof field.onMultipleUpload === 'function'
        ) {
            field.onMultipleUpload(files);
        }
    }

    onClearAutocomplete(field: FormField): void {
        if (field.onClear && typeof field.onClear === 'function') {
            field.onClear();
        }
    }
}
