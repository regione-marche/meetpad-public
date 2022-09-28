import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { of } from 'rxjs';
import { catchError } from 'rxjs/internal/operators/catchError';
import { ToastrService } from 'ngx-toastr';

import { I18nService } from '@eng-ds/ng-core';

import {
    LoaderService,
    SectionLoading,
    AutoUnsubscribe,
    FileService,
    BaseFile
} from '@common';
import { MediaLibrary } from '../model/media-library.model';

@Component({
    templateUrl: './media-main.component.html',
    styleUrls: ['./media-main.component.scss']
})
export class MediaMainComponent extends AutoUnsubscribe implements OnInit {
    searchValue: string;

    constructor(
        private router: Router,
        private fileService: FileService,
        private loaderService: LoaderService,
        private i18nService: I18nService,
        private toastr: ToastrService
    ) {
        super();
    }

    ngOnInit() {
        this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
    }

    onNewSearch(value: string): any {
        this.searchValue = value || '';
    }

    downloadFile(media: MediaLibrary): void {
        // window.open(item.url, '_blank').focus();
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
        this.fileService
            .download(new BaseFile({ url: media.path }))
            .pipe(
                catchError(_ => {
                    this.toastr.error(
                        this.i18nService.translate(
                            'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.FILE_DOWNLOAD_ERROR.TEXT'
                        ),
                        this.i18nService.translate(
                            'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.FILE_DOWNLOAD_ERROR.TITLE'
                        )
                    );
                    return of(null);
                })
            )
            .subscribe(_ => {
                this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
            });
    }

    navigateToConference(conferenceId: string): void {
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
        this.router.navigate(['/conference', conferenceId]);
    }
}
