import {
    Component,
    OnInit,
    OnChanges,
    Input,
    ViewChild,
    SimpleChanges
} from '@angular/core';
import {
    AutoUnsubscribe,
    LoaderService,
    SectionLoading,
    WrapperGetData
} from '@common';
import { AbstractTableField, ActionItem, TableField } from '@eng-ds/ng-toolkit';
import { takeUntil, tap, catchError } from 'rxjs/operators';
import { Subscription, Observable, of } from 'rxjs';
import { I18nService } from '@eng-ds/ng-core';
import { AuthorityService } from '../../../../core/services/authority.service';
import { ToastrService } from 'ngx-toastr';
import { Manager } from '../../../../core/models/manager.model';

@Component({
    // tslint:disable-next-line: component-selector
    selector: 'admin-manager-list',
    templateUrl: './manager-list.component.html',
    styleUrls: ['./manager-list.component.scss']
})
export class ManagerListComponent extends AutoUnsubscribe
    implements OnInit, OnChanges {
    @Input('searchQuery') searchQuery: string;

    @ViewChild('typeTemplate') typeTemplate;
    @ViewChild('actionsTemplate') actionsTemplate;

    list = [];
    tableStructure: AbstractTableField[] = [];
    loading = true;
    hasCriteria = false;

    actionDelete = new ActionItem(
        'BUTTON.DELETE',

        (a, item: Manager) => {
            this.authorityService.deleteManager(item.id);
        },
        'trash'
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
        private loaderService: LoaderService,
        private authorityService: AuthorityService,
        private toastr: ToastrService
    ) {
        super();
    }

    ngOnInit(): void {
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

    defineTableStructure(
        structure: AbstractTableField[]
    ): AbstractTableField[] {
        structure.push(
            new TableField(
                this.i18nService.translate(
                    'AUTHORITIES.PROCEDING.MANAGER.TABLE.NAME'
                ),
                'NORMAL',
                'name',
                true
            )
        );
        structure.push(
            new TableField(
                this.i18nService.translate(
                    'AUTHORITIES.PROCEDING.MANAGER.TABLE.SURNAME'
                ),
                'NORMAL',
                'surname',
                false
            )
        );
        structure.push(
            new TableField(
                this.i18nService.translate(
                    'AUTHORITIES.PROCEDING.MANAGER.TABLE.FISCAL_CODE'
                ),
                'NORMAL',
                'fiscalCode',
                false
            )
        );
        structure.push(
            new TableField(
                this.i18nService.translate(
                    'AUTHORITIES.PROCEDING.MANAGER.TABLE.AUTHORITY'
                ),
                'NORMAL',
                'prosecutingAdministration',
                false
            )
        );

        // structure.push(
        //     new TemplateField(
        //         this.i18nService.translate('AUTHORITIES.MANAGER.TABLE.ACTIONS'),
        //         this.actionsTemplate
        //     )
        // );
        return structure;
    }

    getDefaultSortField(): string {
        return 'name';
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
    private runSearch(query?: {
        value: string;
        prosecutingAdministration: string;
    }) {
        // 3. manage old search don't complete
        if (this.searchSubscription) {
            this.searchSubscription.unsubscribe();
            // this.stopPageLoading();
        }
        // 4. run search
        this.startPageLoading();

        this.searchSubscription = this.search(query)
            .pipe(takeUntil(this.destroy$))
            .subscribe(list => {
                this.stopPageLoading();
            });
    }

    public search(query?: {
        value: string;
        prosecutingAdministration: string;
    }): Observable<WrapperGetData<Manager>> {
        return this.authorityService
            .getManagers(this.buildCriteriaDto(), query)
            .pipe(
                tap((results: WrapperGetData<Manager>) => {
                    this.list = results.list;
                    if (this.list.length === 0) {
                        this.toastr.warning(
                            this.i18nService.translate(
                                'AUTHORITIES.PROCEDING.MANAGER.LIST.TOASTR.EMPTY_MESSAGE'
                            ),
                            this.i18nService.translate(
                                'AUTHORITIES.PROCEDING.MANAGER.LIST.TOASTR.TITLE'
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
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
    }

    public stopPageLoading() {
        this.loading = false;
        this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
    }
}
