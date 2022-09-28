import { Component } from '@angular/core';

import { catchError, takeUntil } from 'rxjs/operators';
import { of, Observable } from 'rxjs';

import {
    AutoUnsubscribe,
    LoaderService,
    HeaderService,
    SectionLoading,
    HttpInternalErrorResponse
} from '@common';
import { UserPortalService } from '@app/core';

@Component({
    selector: 'app-pending-accreditation-page',
    templateUrl: './pending-accreditation-page.component.html',
    styleUrls: ['./pending-accreditation-page.component.scss']
})
export class PendingAccreditationPageComponent extends AutoUnsubscribe {
    constructor(
        private loaderService: LoaderService,
        private headerService: HeaderService,
        private userService: UserPortalService
    ) {
        super();
        this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
        this._init();
    }

    private _init(): void {
        this.userService
            .getInfo()
            .pipe(
                takeUntil(this.destroy$),
                catchError((response: HttpInternalErrorResponse) => {
                    if (response.status === 403) {
                        this._notExist();
                    }
                    return of(null);
                })
            )
            .subscribe();
    }

    private _notExist(): void {
        this.headerService.hideMenu();
        this.headerService.hideUser();
        this.headerService.hideLogin();
    }

    get showMenu$(): Observable<boolean> {
        return this.headerService.showMenu$;
    }
}
