import { Component, OnInit } from '@angular/core';
import { LoaderService } from '../../services/loader/loader.service';
import { SectionLoading } from '../../enums/section-loading.enum';
import { FileService } from '../../services/file/file.service';
import { takeUntil } from 'rxjs/operators';
import { AutoUnsubscribe } from '../auto-unsubscribe/auto-unsubscribe.class';
import { Subject } from 'rxjs';
import { I18nService } from '@eng-ds/ng-core';

@Component({
    selector: 'app-upload-status',
    templateUrl: './upload-status.component.html',
    styleUrls: ['./upload-status.component.scss']
})
export class UploadStatusComponent extends AutoUnsubscribe implements OnInit {
    tmpUploads: Subject<{ name: string; progress: number }>[] = [];
    noFileUploading: string = this.i18nService.translate(
        'COMMON.NO_FILE_UPLOADING'
    );
    constructor(
        private loaderService: LoaderService,
        private fileService: FileService,
        private i18nService: I18nService
    ) {
        super();
    }

    ngOnInit() {
        this._initFileServiceObserver();
        this.fileService.getUploads();
        this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
    }

    private _initFileServiceObserver(): void {
        this.fileService.uploads$
            .pipe(takeUntil(this.destroy$))
            .subscribe(
                (
                    upload: Map<
                        string,
                        Subject<{ name: string; progress: number }>
                    >
                ) => {
                    upload.forEach(
                        (
                            value: Subject<{ name: string; progress: number }>
                        ) => {
                            this.tmpUploads.push(value);
                        }
                    );
                }
            );
    }
}
