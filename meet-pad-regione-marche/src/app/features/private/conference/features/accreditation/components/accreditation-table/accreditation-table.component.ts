import {
    Component,
    OnInit,
    Input,
    Output,
    EventEmitter,
    ViewChild
} from '@angular/core';
import { Subscription, of, Observable } from 'rxjs';
import { takeUntil, catchError, tap } from 'rxjs/operators';

import {
    AbstractTableField,
    ActionItem,
    TableField,
    TemplateField,
    ActionsField
} from '@eng-ds/ng-toolkit';

import { I18nService } from '@eng-ds/ng-core';

import {
    LoaderService,
    WrapperGetData,
    SectionLoading,
    AutoUnsubscribe
} from '@common';

import { ActionForm } from '@app/core';

import { AccreditationService } from '@features/private/core/services/accreditation/accreditation.service';
import {
    AccreditamentPreview,
    ConferenceStoreService
} from '@app/features/private/conference/core';

@Component({
    selector: 'app-accreditation-table',
    templateUrl: './accreditation-table.component.html',
    styleUrls: ['./accreditation-table.component.scss']
})
export class AccreditationTableComponent extends AutoUnsubscribe
    implements OnInit {
    @Input('list') list = [];
    @Input('action') action: ActionForm;
    @Output('view') view = new EventEmitter<AccreditamentPreview>();

    @ViewChild('actionTemplate') actionTemplate;
    @ViewChild('templateAccreditationState') templateAccreditationState;

    viewAccreditation: ActionItem;

    tableStructure: AbstractTableField[] = [];

    loading = true;
    hasCriteria = false;
    /**
     * Witch filed use for sort the result
     */
    public sortFieldActive: string;

    /**
     * Kind of sort
     */
    public sortType: 'asc' | 'desc' = 'asc';

    /**
     * Actual page of result loaded
     */
    public pageNumber: number = 1;
    public pageSizeNumber: number = 1000;
    public totalPages = 0;

    /**
     * This will contain the subscription to search operation
     */
    protected searchSubscription: Subscription;

    constructor(
        private i18nService: I18nService,
        private loaderService: LoaderService,
        private accreditationService: AccreditationService,
        private conferenceStoreService: ConferenceStoreService
    ) {
        super();
    }

    ngOnInit(): void {
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
        this.tableStructure = this.defineTableStructure(this.tableStructure);
        this.sortFieldActive = this.getDefaultSortField();
        this.runSearch();
    }

    get conferenceId(): string {
        return this.conferenceStoreService.conference.id;
    }

    defineTableStructure(
        structure: AbstractTableField[]
    ): AbstractTableField[] {
        const actions = [
            new ActionItem(
                'BUTTON.VIEW',

                (a, item: AccreditamentPreview) => {
                    this.view.emit(item);
                },
                'eye'
            )
        ];

        structure.push(
            new TableField(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.ACCREDITATION.TABLE.USER'
                ),
                'NORMAL',
                'user',
                true
            )
        );
        structure.push(
            new TableField(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.ACCREDITATION.TABLE.EMAIL'
                ),
                'NORMAL',
                'email',
                true
            )
        );
        structure.push(
            new TableField(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.ACCREDITATION.TABLE.PROFILE'
                ),
                'NORMAL',
                'profile.value',
                true
            )
        );
        structure.push(
            new TableField(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.ACCREDITATION.TABLE.PARTICIPANT_DESCRIPTION'
                ),
                'NORMAL',
                'participantDescription',
                false
            )
        );
        structure.push(
            new TemplateField(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.ACCREDITATION.TABLE.STATE.TITLE'
                ),
                this.templateAccreditationState
            )
        );

        structure.push(
            new ActionsField(
                'CONFERENCE.WIZARD.ACCREDITATION.TABLE.ACTIONS',
                actions
            )
        );

        return structure;
    }

    getDefaultSortField(): string {
        return 'surname';
    }

    /**
     * Change the sort field to input one. If the field it's the same change the sort type.
     * @param field
     */
    public changeSort(field: string) {
        if (field === this.sortFieldActive) {
            // switch sort type
            this.sortType = this.sortType === 'asc' ? 'desc' : 'asc';
            this.pageNumber = 0;
            this.pageSizeNumber = 10;
        } else {
            this.sortType = 'asc';
            this.sortFieldActive = field;
            this.pageNumber = 0;
            this.pageSizeNumber = 10;
        }
        this.hasCriteria = true;
        // after change refresh the search
        this.refreshSearch();
    }

    /**
     * Change the page to load
     */
    public changePage(pageNumber: number) {
        if (pageNumber > 0) {
            this.pageNumber = pageNumber--;
        } else {
            this.pageNumber = pageNumber;
        }

        this.hasCriteria = true;
        this.runSearch();
    }

    public changePageElementsShowed(pageSizeNumber: number) {
        this.hasCriteria = true;
        this.pageSizeNumber = pageSizeNumber;
        this.refreshSearch();
    }

    /**
     * Refresh the search and managing also che reset of page cache
     */
    public refreshSearch(newSearch = false) {
        if (newSearch) {
            this.pageNumber = 1;
        }
        this.runSearch();
    }

    /**
     * This internal method build the new criteriaDto and run a search managing also the page number and result
     */
    private runSearch() {
        // 3. manage old search don't complete
        if (this.searchSubscription) {
            this.searchSubscription.unsubscribe();
            // this.stopPageLoading();
        }
        // 4. run search
        this.startPageLoading();

        this.searchSubscription = this.search()
            .pipe(takeUntil(this.destroy$))
            .subscribe(() => {
                this.stopPageLoading();
            });
    }

    public search(): Observable<WrapperGetData<AccreditamentPreview>> {
        return this.accreditationService
            .getList(this.buildCriteriaDto(), this.conferenceId)
            .pipe(
                // TODO: ridonante
                tap((results: WrapperGetData<AccreditamentPreview>) => {
                    this.list = results.list;
                    // tslint:disable-next-line:radix
                    this.totalPages = Math.ceil(
                        parseInt(results.totalNumber) / this.pageSizeNumber
                    );
                }),
                catchError(() => {
                    this.stopPageLoading();
                    return of({ list: [], totalNumber: '' });
                })
            );
    }

    /**
     * Build the dto for backend from criteria
     */
    private buildCriteriaDto() {
        const criteria = {};
        criteria['sort'] = this.sortFieldActive + ',' + this.sortType;
        criteria['page'] = this.pageNumber;
        criteria['size'] = this.pageSizeNumber || 10;
        return criteria;
    }

    public resetSearch() {
        this.sortType = 'asc';
        this.sortFieldActive = this.getDefaultSortField();
        this.pageNumber = 0;
        this.pageSizeNumber = 10;
    }

    public startPageLoading() {
        this.loading = true;
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
    }

    public stopPageLoading() {
        this.loading = false;
        this.conferenceStoreService.hidePageLoader();
    }

    isReadonly(): boolean {
        return this.action === ActionForm.READONLY;
    }
}
