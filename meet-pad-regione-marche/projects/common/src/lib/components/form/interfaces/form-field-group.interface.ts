import { Observable } from 'rxjs';
import { AbstractTableField, TooltipModel } from '@eng-ds/ng-toolkit';
import { FormField } from './form-field.interface';

export interface Alert {
    component: any;
    type: 'danger' | 'info' | 'warning' | 'success';
    textBeforeLink: string;
    textAfterLink?: string;
    textLink?: string;
 //   textTooLarge?: string;
    linkClick?: () => void;
}

export interface FormFieldGroup {
    openAccordion?: () => void;
    panel: boolean;
    panelHead: string | boolean;
    accordion?: boolean;
    accordionName?: string;
    menu?: boolean;
    fields?: Map<string, FormField>;
    tooltip?: TooltipModel;
    oneToMany?: boolean;
    model?: Object;
    saveFn?: (data: any) => Observable<any>;
    listMany?: any[];
    listStructure?: AbstractTableField[];
    listTitle?: string;
    emptyTextList?: string;
    readonly?: boolean;
    alert?: Alert;
    detailLookup?: any;
}
