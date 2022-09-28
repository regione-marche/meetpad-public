import { Component } from '@angular/core';
import { AutoUnsubscribe } from '@common';

@Component({
    selector: 'app-alert-info',
    templateUrl: './alert-info.component.html',
    styleUrls: ['./alert-info.component.scss']
})
export class AlertInfoComponent extends AutoUnsubscribe {
    show: boolean;
    title: string;
    text: string;
    constructor() {
        super();
    }
}
