import {
    Component,
    Input,
    ViewChild,
    Output,
    EventEmitter,
    AfterViewInit
} from '@angular/core';
import { UploadFile, UploadEvent } from 'ngx-file-drop';
import { ModalComponent, ActionItem } from '@eng-ds/ng-toolkit';

@Component({
    selector: 'eng-file-drop',
    templateUrl: './eng-file-drop.component.html',
    styleUrls: ['./eng-file-drop.component.scss']
})
export class EngFileDropComponent implements AfterViewInit {
    @Input('accept') accept: string;
    @Input('disabled') disabled: boolean;

    @Output('onMultipleUpload') onMultipleUpload = new EventEmitter<any>();

    @ViewChild('confirmUploadModal') confirmUploadModal: ModalComponent;

    modalUploadButtons: ActionItem[] = [
        new ActionItem(
            'CONFERENCE.WIZARD.DOCUMENTATION.DROP_FILE.MODAL.CANCEL_BUTTON',
            (action: ActionItem) => {
                this.confirmUploadModal.close();
            },
            'close'
        ),
        new ActionItem(
            'CONFERENCE.WIZARD.DOCUMENTATION.DROP_FILE.MODAL.OK_BUTTON',
            (action: ActionItem) => {
                this.uploadFiles();
            },
            'check'
        )
    ];

    files: UploadFile[] = [];

    ngAfterViewInit() {
        document
            .querySelector('.ngx-file-drop__content')
            .addEventListener('click', () => {
                document
                    .querySelector('.ngx-file-drop__file-input')
                    .dispatchEvent(new MouseEvent('click'));
            });
    }
    dropped(event: UploadEvent) {
        this.files = event.files;
        this.confirmUploadModal.open();
    }

    fileOver(event) {
        // console.log(event);
    }

    fileLeave(event) {
        // console.log(event);
    }

    uploadFiles() {
        this.onMultipleUpload.emit(this.files);
        this.files = [];
        this.confirmUploadModal.close();
    }
}
