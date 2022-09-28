import { TooltipModel } from '@eng-ds/ng-toolkit';

export interface FormButton {
    color?: string;
    tooltip?: TooltipModel;
    onClick?: (event: any) => any;
    title?: string;
}
