import { Component, forwardRef, Input } from '@angular/core';
import { NG_VALIDATORS, NG_VALUE_ACCESSOR } from '@angular/forms';
import { BaseInputComponent } from '@eng-ds/ng-toolkit';

@Component({
    selector: 'app-bs3-switch',
    templateUrl: './bs3-switch.component.html',
    styleUrls: ['./bs3-switch.component.scss'],
    providers: [
        {
            provide: NG_VALUE_ACCESSOR,
            useExisting: forwardRef(() => Bs3SwitchComponent),
            multi: true
        },
        {
            provide: NG_VALIDATORS,
            useExisting: forwardRef(() => Bs3SwitchComponent),
            multi: true
        }
    ]
})
export class Bs3SwitchComponent extends BaseInputComponent {
    @Input('round') round = true;
    @Input('checked-value') checkedValue: any = true;
    @Input('unchecked-value') uncheckedValue: any = false;

    @Input('i18n-extra') i18nExtra: any;
    @Input('offset') offset: any;
    @Input('label') label: any;
    @Input('size') size: any;
    @Input('tooltip') tooltip: any;

    writeValue(value: any): void {
        if (
            !value ||
            (value !== this.checkedValue && value !== this.uncheckedValue)
        ) {
            this.value = this.uncheckedValue;
        } else {
            this.value = value;
        }
    }
    inputTypeName(): string {
        return 'SingleCheckboxInputComponent';
    }

    ngOnInitForChildren() {}
    extractInformationFromInternalInput($event: any) {
        return 'e non doveva fare questo giro';
    }
    onClick() {
        if (this.disabled) {
            return;
        }
        if (this.value === this.checkedValue) {
            this.value = this.uncheckedValue;
        } else {
            this.value = this.checkedValue;
        }
        this.onChangeCallBack(this.value);
    }
}
