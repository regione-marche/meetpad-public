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
    WrapperGetData,
    WrapperResponse,
    HttpInternalErrorResponse,
    MessageToastrService
} from '@common';
import {
    AbstractTableField,
    ActionItem,
    TemplateField,
    ModalComponent
} from '@eng-ds/ng-toolkit';
import { Subscription, Observable, of } from 'rxjs';
import { I18nService } from '@eng-ds/ng-core';
import { ToastrService } from 'ngx-toastr';
import { Router, ActivatedRoute } from '@angular/router';
import { take, takeUntil, tap, catchError } from 'rxjs/operators';
import { SearchCompany } from '../../models/searchCompany.model';
import { CompanyService } from '../../services/company.service';

@Component({
    // tslint:disable-next-line: component-selector
    selector: 'admin-company-list',
    templateUrl: './company-list.component.html',
    styleUrls: ['./company-list.component.scss']
})
export class CompanyListComponent extends AutoUnsubscribe
    implements OnInit, OnChanges {
    @Input('searchQuery') searchQuery: string;
    @Input('preloading') preloading: boolean;

    @ViewChild('nameTemplate') nameTemplate;
    @ViewChild('fiscalCodeTemplate') fiscalCodeTemplate;
    @ViewChild('vatTemplate') vatTemplate;
    @ViewChild('actionsTemplate') actionsTemplate;
    @ViewChild('confirmModal') confirmModal: ModalComponent;

    list = [];
    tableStructure: AbstractTableField[] = [];
    loading = true;
    conferenceType: string;
    hasCriteria = false;
    currentIdToDelete: string = '-1';

    modalText: string = this.i18nService.translate(
        'PRELOADING.COMPANY.CONFIRM_DELETE_MODAL.MESSAGE'
    );
    modalButtons: ActionItem[] = [
        new ActionItem(
            'USERS.CONFIRM_DELETE_MODAL.CANCEL_BUTTON',
            (action: ActionItem): void => {
                this.currentIdToDelete = '-1';
                this.confirmModal.close();
            },
            'close'
        ),
        new ActionItem(
            'USERS.CONFIRM_DELETE_MODAL.OK_BUTTON',
            (action: ActionItem): void => {
                this.deleteCompany();
            },
            'trash'
        )
    ];

    actionEdit = new ActionItem(
        'BUTTON.EDIT',

        (a, item: SearchCompany) => {
            this.router.navigate(['./', item.idPreemptiveCompany], {
                relativeTo: this.activatedRoute
            });
        },
        'edit'
    );
    actionDelete = new ActionItem(
        'BUTTON.DELETE',

        (a, item: SearchCompany) => {
            this.currentIdToDelete = item.idPreemptiveCompany;
            this.confirmModal.open();
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
        private companyService: CompanyService,
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
        if (this.preloading) {
            this.activatedRoute.parent.params
                .pipe(takeUntil(this.destroy$))
                .subscribe(value => {
                    this.conferenceType = value.conferenceType;
                    this.runSearch();
                });
        } else {
            this.runSearch();
        }
    }

    ngOnChanges(changes: SimpleChanges): void {
        if (!changes.searchQuery.firstChange) {
            this.pageNumber = 1;
            this.runSearch(changes.searchQuery.currentValue);
        }
    }

    defineTableStructure(
        structure: AbstractTableField[]
    ): AbstractTableField[] {
        structure.push(
            new TemplateField(
                this.i18nService.translate('REGISTRY.COMPANY.TABLE.NAME'),
                this.nameTemplate
            )
        );
        structure.push(
            new TemplateField(
                this.i18nService.translate('REGISTRY.COMPANY.TABLE.VAT'),
                this.vatTemplate
            )
        );
        structure.push(
            new TemplateField(
                this.i18nService.translate(
                    'REGISTRY.COMPANY.TABLE.FISCAL_CODE'
                ),
                this.fiscalCodeTemplate
            )
        );

        structure.push(
            new TemplateField(
                this.i18nService.translate('REGISTRY.COMPANY.TABLE.ACTIONS'),
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
            .subscribe(_ => {
                this.stopPageLoading();
            });
    }

    public search(query?: string): Observable<WrapperGetData<SearchCompany>> {
        return this.companyService
            .getCompanies(
                this.buildCriteriaDto(),
                query,
                this.preloading,
                this.conferenceType
            )
            .pipe(
                tap((results: WrapperGetData<SearchCompany>) => {
                    this.list = results.list;
                    if (this.list.length === 0) {
                        this.toastr.warning(
                            this.i18nService.translate(
                                'REGISTRY.COMPANY.TABLE.TOASTR.EMPTY_MESSAGE'
                            ),
                            this.i18nService.translate(
                                'REGISTRY.COMPANY.TABLE.TOASTR.TITLE'
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

    deleteCompany(): void {
        this.companyService
            .deleteCompany(this.currentIdToDelete)
            .pipe(
                take(1),
                catchError((error: HttpInternalErrorResponse) => {
                    this.messageToastr.showErrorMessage(
                        error,
                        'PRELOADING.COMPANY.EDIT',
                        'DELETE'
                    );
                    this.confirmModal.close();

                    return of(null);
                })
            )
            .subscribe(response => {
                const r = response as WrapperResponse<number>;
                this.messageToastr.showMessage(
                    r,
                    'PRELOADING.COMPANY.EDIT',
                    'DELETE'
                );
                if (r.code === '200') {
                    this._deleteFromList();
                }
                this.currentIdToDelete = '-1';
                this.confirmModal.close();
            });
    }
    private _deleteFromList() {
        const iu = this.list.findIndex(
            (_u: SearchCompany) =>
                _u.idPreemptiveCompany === this.currentIdToDelete
        );

        if (iu < 0) {
            return;
        }

        this.list.splice(iu, 1);
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
