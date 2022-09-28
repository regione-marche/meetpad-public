import { Component } from '@angular/core';

import { catchError, takeUntil } from 'rxjs/operators';
import { of, Observable } from 'rxjs';

import { AbstractTableField, TableField } from '@eng-ds/ng-toolkit';
import { I18nService } from '@eng-ds/ng-core';

import {
    AutoUnsubscribe,
    LoaderService,
    HeaderService,
    SectionLoading,
    HttpInternalErrorResponse
} from '@common';

import { UserPortalService } from '@app/core';
import { AccreditamentPreview } from '@app/features/private/conference/core';

@Component({
    templateUrl: './pending-list-page.component.html',
    styleUrls: ['./pending-list-page.component.scss']
})
export class PendingListPageComponent extends AutoUnsubscribe {
    tableStructure: AbstractTableField[] = [];

    constructor(
        private loaderService: LoaderService,
        private headerService: HeaderService,
        private userService: UserPortalService,
        private i18nService: I18nService
    ) {
        super();
        this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
        this._init();
    }

    private _init(): void {
        this._initTableStructure();
        this._initHeaderVisibility();
    }

    private _initTableStructure(): void {
        this.tableStructure.push(
            new TableField(
                this.i18nService.translate(
                    'CONFERENCE.JOIN.PENDING_LIST_PAGE.TABLE.ID'
                ),
                'NUMBER',
                'id',
                false
            )
        );

        this.tableStructure.push(
            new TableField(
                this.i18nService.translate(
                    'CONFERENCE.JOIN.PENDING_LIST_PAGE.TABLE.CONFERENCE_ID'
                ),
                'NORMAL',
                'idConference',
                false
            )
        );

        this.tableStructure.push(
            new TableField(
                this.i18nService.translate(
                    'CONFERENCE.JOIN.PENDING_LIST_PAGE.TABLE.CREATION_DATE'
                ),
                'NORMAL',
                'creationDate',
                false
            )
        );
    }

    private _initHeaderVisibility(): void {
        this.userService
            .getInfo()
            .pipe(
                takeUntil(this.destroy$),
                catchError((response: HttpInternalErrorResponse) => {
                    if (response.status === 403) {
                        this.headerService.hideMenu();
                        this.headerService.hideUser();
                        this.headerService.hideLogin();
                    }
                    return of(null);
                })
            )
            .subscribe();
    }

    get showMenu$(): Observable<boolean> {
        return this.headerService.showMenu$;
    }

    get penndingAccreditations$(): Observable<AccreditamentPreview[]> {
        return this.userService.getPendingAccreditations();
    }
}
