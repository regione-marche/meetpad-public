import {
    Component,
    Input,
    ViewChild,
    ElementRef,
    AfterViewInit
} from '@angular/core';
import { Alert } from '../form/interfaces';

@Component({
    selector: 'app-form-alert',
    templateUrl: './alert-with-link.component.html'
})
export class AlertWithLinkComponent implements AfterViewInit {
    @ViewChild('ahref') ahref: ElementRef;

    @Input('type') type: Alert['type'];
    @Input('textBeforeLink') textBeforeLink: string;
    @Input('textAfterLink') textAfterLink: string;
    @Input('textLink') textLink: string;
   // @Input('textTooLarge') textTooLarge: string;
    @Input('linkClick') linkClick: () => void;

    // click event on html not work
    ngAfterViewInit() {
        if (this.link) {
            this.ahref.nativeElement.addEventListener('click', this.linkClick);
        }
    }

    get link(): boolean {
        return !!this.textLink && !!this.linkClick;
    }
}
