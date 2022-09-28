import { Directive, ViewContainerRef, Input } from '@angular/core';
import { Alert } from '../../interfaces';

@Directive({
    // tslint:disable-next-line:directive-selector
    selector: '[alert-host]'
})
export class AlertDirective {
    @Input('alert') alert: Alert;
    constructor(public viewContainerRef: ViewContainerRef) {}
}
