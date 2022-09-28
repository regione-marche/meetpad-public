import { FormControl } from '@angular/forms';
import { Observable } from 'rxjs/internal/Observable';
import { ErrorLabel, TooltipModel } from '@eng-ds/ng-toolkit';
import { BaseFile } from '../../../models/base-file.model';

// TODO: raggruppare le vari proprietà per contestualizzare
export interface FormField {
    control?: FormControl;
    model?: string;
    validators?: Array<any>;
    errorLabels?: ErrorLabel[];
    tooltip?: TooltipModel;
    required?: boolean;
    type:
        | 'text'
        | 'switch'
        | 'select'
        | 'date'
        | 'radio'
        | 'text-area'
        | 'single-textbox'
        | 'tag-input'
        | 'select-two'
        | 'autocomplete'
        | 'file'
        | 'drop-file'
        | 'nop';
    size?: string;
    offset?: string;
    label?: string;
    options?: Observable<any>;
    radioOptions?: any;
    valueChange?: any;
    change?: any;
    loading?: boolean;
    maxLength?: number;
    rows?: number;
    disabled?: boolean;
    placeholder?: string;
    onUpload?: (file: File) => void;
    onSelect?: (file: File) => void;
    onDownload?: (url: BaseFile) => void;
    onClear?: () => void;
    onMultipleUpload?: (files: File[]) => void;
    file?: BaseFile;
    accept?: string;
    hideSubmitBtn?: boolean;
    readonly?: boolean;
    typeahead?: any;
    notFoundText?: string;
    pattern?: string;
}
