import { Component, OnInit } from '@angular/core';
import { AutoUnsubscribe, MenuGuard, FileService } from '@common';
import { ToastrService } from 'ngx-toastr';
import { I18nService } from '@eng-ds/ng-core';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})
export class AppComponent extends AutoUnsubscribe implements OnInit {
    constructor(
        public menuGuard: MenuGuard
    ) // private fileService: FileService,
    // private toastr: ToastrService,
    // private i18nService: I18nService
    {
        super();
    }
    ngOnInit() {
        // this.fileService.uploads$.pipe(takeUntil(this.destroy$)).subscribe(
        //     (upload: Map<string, Subject<number>>) => {
        //         this.toastr.info(
        //             this.i18nService.translate(
        //                 'CONFERENCE.UPLOAD.TOASTR.NEW_FILE_UPLOAD.TEXT'
        //             ),
        //             this.i18nService.translate(
        //                 'CONFERENCE.UPLOAD.TOASTR.NEW_FILE_UPLOAD.TITLE'
        //             )
        //         );
        //     },
        //     error => {
        //         this.toastr.error(
        //             this.i18nService.translate(
        //                 'CONFERENCE.UPLOAD.TOASTR.ERROR_FILE_UPLOAD.TEXT'
        //             ),
        //             this.i18nService.translate(
        //                 'CONFERENCE.UPLOAD.TOASTR.ERROR_FILE_UPLOAD.TITLE'
        //             )
        //         );
        //     },
        //     () => {
        //         this.toastr.info(
        //             this.i18nService.translate(
        //                 'CONFERENCE.UPLOAD.TOASTR.FINISH_FILE_UPLOAD.TEXT'
        //             ),
        //             this.i18nService.translate(
        //                 'CONFERENCE.UPLOAD.TOASTR.FINISH_FILE_UPLOAD.TITLE'
        //             )
        //         );
        //     }
        // );
    }
}
