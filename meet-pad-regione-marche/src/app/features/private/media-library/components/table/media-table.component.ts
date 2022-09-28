import {
    Component,
    OnInit,
    Input,
    OnChanges,
    SimpleChanges,
    ViewChild,
    Output,
    EventEmitter
} from '@angular/core';

import { Subscription, Observable, of } from 'rxjs';
import { tap, catchError, takeUntil } from 'rxjs/operators';

import { ToastrService } from 'ngx-toastr';

import {
    AbstractTableField,
    TableField,
    ActionsField,
    ActionItem,
    TableResultComponent,
    TemplateField
} from '@eng-ds/ng-toolkit';

import { I18nService } from '@eng-ds/ng-core';

import {
    LoaderService,
    WrapperGetData,
    SectionLoading,
    AutoUnsubscribe
} from '@common';

import { MediaLibrary } from '../../model/media-library.model';
import { MediaLibraryService } from '../../services/media-library.service';

@Component({
    selector: 'app-media-table',
    templateUrl: './media-table.component.html',
    styleUrls: ['./media-table.component.scss']
})
export class MediaTableComponent extends AutoUnsubscribe
    implements OnChanges, OnInit {
    @ViewChild(TableResultComponent) tableResult: TableResultComponent;
    @ViewChild('conferenceSubject') conferenceSubject;

    @Input() title: string;
    @Input() searchedValue: string;
    @Output('downloadFile') downloadFile = new EventEmitter<MediaLibrary>();
    @Output('navigateToConference') navigateToConference = new EventEmitter<
        string
    >();
    results: MediaLibrary[] = [];

    loading = false;
    /**
     * Witch filed use for sort the result
     */
    public sortFieldActive: string;

    /**
     * Kind of sort
     */
    public sortType: 'asc' | 'desc' = 'desc';

    /**
     * Actual page of result loaded
     */
    public pageNumber: number = 1;
    public pageSizeNumber: number = 10;
    public totalPages = 0;

    /**
     * This will contain the subscription to search operation
     */
    protected searchSubscription: Subscription;

    /**
     * Contain the table structure for result
     */
    tableStructure: AbstractTableField[] = [];

    constructor(
        private i18nService: I18nService,
        private loaderService: LoaderService,
        private toastr: ToastrService,
        private mediaLibraryService: MediaLibraryService
    ) {
        super();
    }

    get loading$(): Observable<boolean> {
        return this.loaderService.getLoading$(SectionLoading.CLONE_MODAL);
    }

    ngOnInit(): void {
        this.tableStructure = this.defineTableStructure(this.tableStructure);
        this.sortFieldActive = this.getDefaultSortField();
    }

    ngOnChanges(changes: SimpleChanges): void {
        if (
            changes.searchedValue &&
            changes.searchedValue.currentValue !== null &&
            changes.searchedValue.currentValue !== undefined &&
            !changes.searchedValue.firstChange
        ) {
            this.refreshSearch();
        }
    }

    defineTableStructure(
        structure: AbstractTableField[]
    ): AbstractTableField[] {
        structure.push(
            new TableField(
                this.i18nService.translate('MEDIA_LIBRARY.TABLE.ID'),
                'NUMBER',
                'id',
                true
            )
        );
        structure.push(
            new TableField(
                this.i18nService.translate('MEDIA_LIBRARY.TABLE.DOCUMENT_NAME'),
                'NORMAL',
                'name',
                true
            )
        );

        structure.push(
            new TemplateField(
                this.i18nService.translate(
                    'MEDIA_LIBRARY.TABLE.CONFERENCE_SUBJECT'
                ),
                this.conferenceSubject
            )
        );

        structure.push(
            new TableField(
                this.i18nService.translate('MEDIA_LIBRARY.TABLE.DOCUMENT_MD5'),
                'NORMAL',
                'md5',
                true
            )
        );

        structure.push(
            new ActionsField('MEDIA_LIBRARY.TABLE.ACTIONS', [
                new ActionItem(
                    'BUTTON.DOWNLOAD',
                    (a, item: MediaLibrary) => {
                        this.downloadFile.emit(item);
                    },
                    'download'
                )
            ])
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
        // after change refresh the search
        this.refreshSearch(true);
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

        this.startPageLoading();
        this.runSearch();
    }

    public changePageElementsShowed(pageSizeNumber: number) {
        this.pageSizeNumber = pageSizeNumber;
        this.refreshSearch();
    }

    /**
     * Refresh the search and managing also che reset of page cache
     */
    public refreshSearch(newSearch = false) {
        this.startPageLoading();
        if (newSearch) {
            this.pageNumber = 1;
            this.tableResult.pageActive = 1;
            this.tableResult.calculatePageNumberToShow(
                this.pageNumber,
                this.tableResult.selectedOption
            );
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
        this.searchSubscription = this.search()
            .pipe(takeUntil(this.destroy$))
            .subscribe(() => {
                this.stopPageLoading();
            });
    }

    public search(): Observable<WrapperGetData<MediaLibrary>> {
        return this.mediaLibraryService
            .list(this.buildCriteriaDto(), this.searchedValue)
            .pipe(
                // TODO: ridonante
                tap((results: WrapperGetData<MediaLibrary>) => {
                    this.results = results.list;
                    if (this.results.length === 0) {
                        this.toastr.warning(
                            this.i18nService.translate(
                                'SEARCH.TOASTR.EMPTY_SEARCH.TEXT'
                            ),
                            this.i18nService.translate(
                                'SEARCH.TOASTR.EMPTY_SEARCH.TITLE'
                            )
                        );
                    }
                    this.totalPages = Math.ceil(
                        // tslint:disable-next-line:radix
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
        this.loaderService.showLoading(SectionLoading.SEARCH);
    }

    public stopPageLoading() {
        setTimeout(() => {
            this.loading = false;
            this.loaderService.hideLoading(SectionLoading.SEARCH);
            this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
        }, 500);
    }
}
