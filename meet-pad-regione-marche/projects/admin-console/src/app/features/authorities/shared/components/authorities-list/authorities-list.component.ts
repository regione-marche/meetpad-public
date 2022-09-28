import {
    Component,
    OnInit,
    SimpleChanges,
    OnChanges,
    Input,
    ViewChild
} from '@angular/core';
import { Subscription, Observable, of } from 'rxjs';
import { I18nService } from '@eng-ds/ng-core';
import {
    LoaderService,
    SectionLoading,
    WrapperGetData,
    AutoUnsubscribe,
    WrapperPostPutData,
    WrapperResponse,
    HttpInternalErrorResponse,
    MessageToastrService
} from '@common';
import { takeUntil, tap, catchError, take } from 'rxjs/operators';
import {
    AbstractTableField,
    TableField,
    TemplateField,
    ActionItem
} from '@eng-ds/ng-toolkit';
import { AuthorityService } from '../../../core/services/authority.service';
import { SearchAuthority } from '../../../core/models/searchAuthority.model';
import { ToastrService } from 'ngx-toastr';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
    // tslint:disable-next-line: component-selector
    selector: 'admin-authorities-list',
    templateUrl: './authorities-list.component.html',
    styleUrls: ['./authorities-list.component.scss']
})
export class AuthoritiesListComponent extends AutoUnsubscribe
    implements OnInit, OnChanges {
    @Input('searchQuery') searchQuery: string;
    @Input('procedingPage') procedingPage: boolean = true;

    @ViewChild('typeTemplate') typeTemplate;
    @ViewChild('actionsTemplate') actionsTemplate;

    list = [];
    tableStructure: AbstractTableField[] = [];
    loading = true;
    hasCriteria = false;
    actionEdit = new ActionItem(
        'BUTTON.EDIT',

        (a, item: SearchAuthority) => {
            this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
            this.router.navigate(['../', item.id], {
                relativeTo: this.activatedRoute
            });
        },
        'edit'
    );
    actionSetProceding = new ActionItem(
        'BUTTON.SET_PROCEDING',

        (a, item: SearchAuthority) => {
            this._setProceding(item, true);
        },
        'check-circle'
    );
    actionSetNotProceding = new ActionItem(
        'BUTTON.SET_NOT_PROCEDING',

        (a, item: SearchAuthority) => {
            this._setProceding(item, false);
        },
        'minus-circle'
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
        private toastr: ToastrService,
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private messageToastr: MessageToastrService
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
            this.pageNumber = 1;
            this.runSearch(changes.searchQuery.currentValue);
        }
    }

    private _setProceding(authority: SearchAuthority, proceding: boolean) {
        this.authorityService
            .setProceding(authority, proceding)
            .pipe(
                take(1),
                catchError((error: HttpInternalErrorResponse) => {
                    if (proceding) {
                        this.messageToastr.showErrorMessage(
                            error,
                            'AUTHORITIES.PROCEDING.EDIT.ACTIONS',
                            'SET'
                        );
                    } else {
                        this.messageToastr.showErrorMessage(
                            error,
                            'AUTHORITIES.PROCEDING.EDIT.ACTIONS',
                            'UNSET'
                        );
                    }

                    return of(null);
                })
            )
            .subscribe((response: WrapperPostPutData) => {
                if (response) {
                    let r = response as any;
                    r = r as WrapperResponse<any>;
                    if (proceding) {
                        this.messageToastr.showMessage(
                            r,
                            'AUTHORITIES.PROCEDING.EDIT.ACTIONS',
                            'SET'
                        );
                    } else {
                        this.messageToastr.showMessage(
                            r,
                            'AUTHORITIES.PROCEDING.EDIT.ACTIONS',
                            'UNSET'
                        );
                    }
                    if (response.id) {
                        authority.flagProsecutingAdministration = proceding;
                    }
                }
            });
    }

    defineTableStructure(
        structure: AbstractTableField[]
    ): AbstractTableField[] {
        structure.push(
            new TableField(
                this.i18nService.translate(
                    'AUTHORITIES.PROCEDING.EDIT.TABLE.NAME'
                ),
                'NORMAL',
                'name',
                true
            )
        );
        structure.push(
            new TableField(
                this.i18nService.translate(
                    'AUTHORITIES.PROCEDING.EDIT.TABLE.PEC'
                ),
                'NORMAL',
                'pec',
                false
            )
        );
        structure.push(
            new TemplateField(
                this.i18nService.translate(
                    'AUTHORITIES.PROCEDING.EDIT.TABLE.PROCEDING'
                ),
                this.typeTemplate
            )
        );

        structure.push(
            new TemplateField(
                this.i18nService.translate(
                    'AUTHORITIES.PROCEDING.EDIT.TABLE.ACTIONS'
                ),
                this.actionsTemplate
            )
        );
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
    private runSearch(query?: string) {
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

    public search(query?: string): Observable<WrapperGetData<SearchAuthority>> {
        return this.authorityService
            .getAuthorities(this.buildCriteriaDto(), query)
            .pipe(
                tap((results: WrapperGetData<SearchAuthority>) => {
                    this.list = results.list;
                    if (this.list.length === 0) {
                        this.toastr.warning(
                            this.i18nService.translate(
                                'AUTHORITIES.PROCEDING.EDIT.LIST.TOASTR.EMPTY_MESSAGE'
                            ),
                            this.i18nService.translate(
                                'AUTHORITIES.PROCEDING.EDIT.LIST.TOASTR.TITLE'
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
        this.loaderService.hideLoading(SectionLoading.ALL_CONTENT, 500);
    }
}
