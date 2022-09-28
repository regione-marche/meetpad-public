import { Component, OnInit, Input } from '@angular/core';
import { Subject } from 'rxjs';
import { AutoUnsubscribe } from '../../../auto-unsubscribe/auto-unsubscribe.class';

@Component({
    selector: 'app-upload',
    templateUrl: './upload.component.html',
    styleUrls: ['./upload.component.scss']
})
export class UploadComponent extends AutoUnsubscribe implements OnInit {
    @Input('progress') progress: Subject<{ name: string; progress: number }>;
    @Input('size') size: string;

    color: string;
    status: number = 0;
    name: string;

    _progress: number;

    constructor() {
        super();
    }

    ngOnInit() {
        this.progress.subscribe(
            (value: { name: string; progress: number }) => {
                this.name = value.name;
                this._progress = value.progress;
            },
            error => {
                this.status = 1;
                this.color = '#BD362F';
            },
            () => {
                this.status = 2;
                this.color = '#28a745';
            }
        );
    }
}
