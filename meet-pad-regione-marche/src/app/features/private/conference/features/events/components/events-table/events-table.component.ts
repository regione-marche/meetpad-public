import {
    Component,
    OnInit,
    Input,
    Output,
    EventEmitter,
    ViewChild,
    OnChanges,
    SimpleChanges
} from '@angular/core';

import { Subscription, Observable, of } from 'rxjs';
import { takeUntil, catchError, tap, filter } from 'rxjs/operators';

import {
    AbstractTableField,
    TableField,
    ActionItem,
    TemplateField
} from '@eng-ds/ng-toolkit';
import { I18nService } from '@eng-ds/ng-core';

import {
    LoaderService,
    SectionLoading,
    WrapperGetData,
    AutoUnsubscribe
} from '@common';

import { ActionForm, UtilityService } from '@app/core';

import {
    ConferenceStoreService,
    EventsService,
    Event
} from '@features/private/conference/core';

import { EventStoreService } from '../../services';
import { QuerySearchEvents } from '../../models/query-search-events.model';

import * as $ from 'jquery';

@Component({
    selector: 'app-events-table',
    templateUrl: './events-table.component.html',
    styleUrls: ['./events-table.component.scss']
})
export class EventsTableComponent extends AutoUnsubscribe
    implements OnInit, OnChanges {
    @Input('action') action: ActionForm;
    @Output('view') view = new EventEmitter<Event>();
    @Input('searchQuery') searchQuery: QuerySearchEvents;

    @ViewChild('typeTemplate') typeTemplate;
    @ViewChild('subjectTemplate') subjectTemplate;
    @ViewChild('actionsTemplate') actionsTemplate;
    @ViewChild('statusTemplate') statusTemplate;

    list = [];
    tableStructure: AbstractTableField[] = [];
    loading = true;
    hasCriteria = false;
    actionView = new ActionItem(
        'BUTTON.VIEW',

        (a, item: Event) => {
            this.view.emit(item);
        },
        'eye'
    );

    /**
     * Witch filed use for sort the result
     */
    public sortFieldActive: string;
    /**
     * Kind of sort
     */
    public sortType: 'asc' | 'desc' = 'asc';

    /**
     * This will contain the subscription to search operation
     */
    protected searchSubscription: Subscription;

    /**
     * Actual page of result loaded
     */
    public pageNumber: number = 1;
    public pageSizeNumber: number = 10;
    public totalPages = 0;

    constructor(
        private i18nService: I18nService,
        public utilityService: UtilityService,
        private loaderService: LoaderService,
        private eventsService: EventsService,
        private eventStoreService: EventStoreService,
        private conferenceStoreService: ConferenceStoreService
    ) {
        super();
    }

    ngOnInit(): void {
        this.eventStoreService.eventsRefresh$
            .pipe(
                takeUntil(this.destroy$),
                filter((flag: boolean) => flag)
            )
            .subscribe(_ => {
                this.runSearch();
            });
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
        this.tableStructure = this.defineTableStructure(this.tableStructure);
        this.sortFieldActive = this.getDefaultSortField();
        this.runSearch();
    }

    ngOnChanges(changes: SimpleChanges): void {
        if (!changes.searchQuery.firstChange) {
            this.runSearch(changes.searchQuery.currentValue);
        }
    }

    get conferenceId(): string {
        return this.conferenceStoreService.conference.id;
    }

    get store(): EventStoreService {
        return this.eventStoreService;
    }

    getType(model: Event): string {
        if (model.type && model.type.value) {
            return model.type.value;
        }
        return '---';
    }

    getSubject(model: Event): string {
        if (model.subject && model.subject.value) {
            return model.subject.value;
        }
        return '---';
    }

    getStatus(model: Event): string {
        let status = '---';
        
        if (model.statusDocument) {
            status = 'CONFERENCE.WIZARD.EVENTS.TABLE.STATUS.'.concat(model.statusDocument);
        }
        return status;
    }

    defineTableStructure(
        structure: AbstractTableField[]
    ): AbstractTableField[] {
        structure.push(
            new TableField(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.EVENTS.TABLE.DATE'
                ),
                'DATE',
                'date',
                false
            )
        );
        structure.push(
            new TemplateField(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.EVENTS.TABLE.TYPE'
                ),
                this.typeTemplate
            )
        );

        structure.push(
            new TableField(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.EVENTS.TABLE.SENDER'
                ),
                'NORMAL',
                'sender',
                false
            )
        );

        structure.push(
            new TemplateField(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.EVENTS.TABLE.SUBJECT'
                ),
                this.subjectTemplate
            )
        );

        structure.push(
            new TemplateField(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.EVENTS.TABLE.STATUS_DOCUMENT'
                ),
                this.statusTemplate
            )
        );

        structure.push(
            new TemplateField(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.EVENTS.TABLE.ACTIONS'
                ),
                this.actionsTemplate
            )
        );

        return structure;
    }

    getDefaultSortField(): string {
        return 'id';
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
    private runSearch(query?: QuerySearchEvents) {
        // 3. manage old search don't complete
        if (this.searchSubscription) {
            this.searchSubscription.unsubscribe();
            // this.stopPageLoading();
        }
        // 4. run search
        this.startPageLoading();

        this.searchSubscription = this.search(query)
            .pipe(takeUntil(this.destroy$))
            .subscribe(() => {
                this.stopPageLoading();
            });
    }

    public search(
        query?: QuerySearchEvents
    ): Observable<WrapperGetData<Event>> {
        return this.eventsService
            .get(this.conferenceId, this.buildCriteriaDto(), query)
            .pipe(
                // TODO: ridonante
                tap((results: WrapperGetData<Event>) => {
                    this.list = results.list;
                    // tslint:disable-next-line:radix
                    this.totalPages = Math.ceil(
                        parseInt(results.totalNumber, 10) / this.pageSizeNumber
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
