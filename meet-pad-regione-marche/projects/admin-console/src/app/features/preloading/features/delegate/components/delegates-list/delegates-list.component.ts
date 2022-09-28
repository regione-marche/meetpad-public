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
  SectionLoading,
  LoaderService,
  WrapperGetData,
  WrapperResponse,
  HttpInternalErrorResponse,
  MessageToastrService,
  BaseFile,
  FileService
} from '@common';
import {
  ModalComponent,
  AbstractTableField,
  ActionItem,
  TableField,
  TemplateField,
  ActionsField
} from '@eng-ds/ng-toolkit';
import { Subscription, Observable, of } from 'rxjs';
import { I18nService } from '@eng-ds/ng-core';
import { ToastrService } from 'ngx-toastr';
import { Router, ActivatedRoute } from '@angular/router';
import { takeUntil, tap, catchError, take } from 'rxjs/operators';
import { SearchDelegate } from '../../models/search-delegates.model';
import { DelegateService } from '../../services/delegate.service';
import { QueryDelegate } from '../../models/query-delegate.model';
import { HttpResponse } from '@angular/common/http';


@Component({
  selector: 'admin-delegates-list',
  templateUrl: './delegates-list.component.html',
  styleUrls: ['./delegates-list.component.scss']
})
export class DelegatesListComponent extends AutoUnsubscribe
implements OnInit, OnChanges {
    @Input('searchQuery') searchQuery: QueryDelegate;

    @ViewChild('typeTemplate') typeTemplate;
    @ViewChild('actionsTemplate') actionsTemplate;
    @ViewChild('confirmModal') confirmModal: ModalComponent;
    @ViewChild('confirmDeleteDocumentModal') confirmDeleteDocumentModal: ModalComponent;

    currentIdToDelete: string = '-1';
    currentIdDocumentToDelete: string = '-1';
    
    conferenceType: string;
    list = [];
    tableStructure: AbstractTableField[] = [];
    loading = true;
    hasCriteria = false;
    fileDownloadSubscription: Subscription;

    modalText: string = this.i18nService.translate(
        'PRELOADING.DELEGATE.CONFIRM_DELETE_MODAL.MESSAGE'
    );
    modalButtons: ActionItem[] = [
        new ActionItem(
            'PRELOADING.DELEGATE.CONFIRM_DELETE_MODAL.CANCEL_BUTTON',
            (action: ActionItem): void => {
                this.currentIdToDelete = '-1';
                this.confirmModal.close();
            },
            'close'
        ),
        new ActionItem(
            'PRELOADING.DELEGATE.CONFIRM_DELETE_MODAL.OK_BUTTON',
            (action: ActionItem): void => {
                this.deleteDelegate();
            },
            'trash'
        )
    ];

    modalDeleteFileButtons: ActionItem[] = [
        new ActionItem(
            'PRELOADING.DELEGATE.CONFIRM_DELETE_FILE_MODAL.CANCEL_BUTTON',
            (action: ActionItem) => {
                this.currentIdDocumentToDelete = '-1';
                this.confirmDeleteDocumentModal.close();
            },
            'close'
        ),
        new ActionItem(
            'PRELOADING.DELEGATE.CONFIRM_DELETE_FILE_MODAL.OK_BUTTON',
            (action: ActionItem) => {
                this.deleteDelegateDocument();                
            },
            'trash'
        )
    ];

    actionEdit = new ActionItem(
        'BUTTON.EDIT',

        (a, item: SearchDelegate) => {
            this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
            this.router.navigate(['../', item.id], {
                relativeTo: this.activatedRoute
            });
        },
        'edit'
    );
    actionDelete = new ActionItem(
        'BUTTON.DELETE',

        (a, item: SearchDelegate) => {
            this.currentIdToDelete = item.id;
            this.confirmModal.open();
        },
        'trash'
    );

    actionDownload = new ActionItem(
        'BUTTON.DOWNLOAD',
        (a, item: SearchDelegate) => {
            this.downloadFile(item);
        },
        'download'       
    );
    
    actionDeleteDocument = new ActionItem(
        'BUTTON.DELETE_FILE',

        (a, item: SearchDelegate) => {
            this.currentIdDocumentToDelete = item.id;
            this.confirmDeleteDocumentModal.open();
        },
        'remove'
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
        private delegateService: DelegateService,
        private toastr: ToastrService,
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private messageToastr: MessageToastrService,
        public fileService: FileService
    ) {
        super();
    }

    ngOnInit(): void {
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
        this.tableStructure = this.defineTableStructure(this.tableStructure);
        this.sortFieldActive = this.getDefaultSortField();
        this.activatedRoute.parent.params
            .pipe(takeUntil(this.destroy$))
            .subscribe(value => {
                this.conferenceType = value.conferenceType;
                this.runSearch();
            });
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
            new TableField(
                this.i18nService.translate('PRELOADING.DELEGATE.TABLE.NAME'),
                'NORMAL',
                'name',
                true
            )
        );
        structure.push(
            new TableField(
                this.i18nService.translate(
                    'PRELOADING.DELEGATE.TABLE.SURNAME'
                ),
                'NORMAL',
                'surname',
                false
            )
        );
        structure.push(
            new TableField(
                this.i18nService.translate(
                    'PRELOADING.DELEGATE.TABLE.FISCAL_CODE'
                ),
                'NORMAL',
                'fiscalCode',
                false
            )
        );
        structure.push(
            new TableField(
                this.i18nService.translate(
                    'PRELOADING.DELEGATE.TABLE.DOCUMENT_NAME'
                ),
                'NORMAL',
                'documentName',
                false
            )
        );

        structure.push(
            new TemplateField(
                this.i18nService.translate(
                    'PRELOADING.DELEGATE.TABLE.ACTIONS'
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
        this.refreshSearch(true);
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
    private runSearch(query?: QueryDelegate) {
        // 3. manage old search don't complete
        if (this.searchSubscription) {
            this.searchSubscription.unsubscribe();
            // this.stopPageLoading();
        }
        // 4. run search
        this.startPageLoading();

        this.searchSubscription = this.search(this.conferenceType, query)
            .pipe(takeUntil(this.destroy$))
            .subscribe(list => {
                this.stopPageLoading();
            });
    }

    public search(
        conferenceType: string,
        query?: QueryDelegate
    ): Observable<WrapperGetData<SearchDelegate>> {
        return this.delegateService.getDelegates(this.buildCriteriaDto(), conferenceType, query)
            .pipe(
                tap((results: WrapperGetData<SearchDelegate>) => {
                    this.list = results.list;
                    if (this.list.length === 0) {
                        this.toastr.warning(
                            this.i18nService.translate(
                                'PRELOADING.DELEGATE.LIST.TOASTR.EMPTY_MESSAGE'
                            ),
                            this.i18nService.translate(
                                'PRELOADING.DELEGATE.LIST.TOASTR.TITLE'
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
    
    /* -------------- functions ----------------- */
    downloadFile(delegate: SearchDelegate): void {
        if (
            this.fileDownloadSubscription instanceof
            Subscription
        ) {
            this.fileDownloadSubscription.unsubscribe();
        }
        this.fileDownloadSubscription = this.fileService
            .downloadByApiName(
                'preloading/downloadDelegateFile',
                {
                    id: delegate.id
                }
            )
            .subscribe(_ => {
                this.loaderService.hideLoading(
                    SectionLoading.EVENTS_MODAL
                );
            });
    }

    deleteDelegate(): void {
        this.delegateService.deleteDelegate(this.currentIdToDelete)
            .pipe(
                take(1),
                catchError((error: HttpInternalErrorResponse) => {
                    this.messageToastr.showErrorMessage(
                        error,
                        'PRELOADING.DELEGATE.EDIT',
                        'DELETE'
                    );
                    this.confirmModal.close();

                    return of(null);
                })
            )
            .subscribe(response => {
                const r = response as WrapperResponse<any>;

                if (response) {
                    this.messageToastr.showMessage(
                        r,
                        'PRELOADING.DELEGATE.EDIT',
                        'DELETE'
                    );
                    if (r.code === '200') {
                        this._deleteFromList();
                    }
                }

                this.currentIdToDelete = '-1';
                this.confirmModal.close();
            });
    }

    private _deleteFromList() {
        const iu = this.list.findIndex(
            (_u: SearchDelegate) => _u.id === this.currentIdToDelete
        );

        if (iu < 0) {
            return;
        }

        this.list.splice(iu, 1);
    }

    deleteDelegateDocument(): void {
        this.delegateService.deleteDelegateDocument(this.currentIdDocumentToDelete)
            .pipe(
                take(1),
                catchError((error: HttpInternalErrorResponse) => {
                    this.messageToastr.showErrorMessage(
                        error,
                        'PRELOADING.DELEGATE.EDIT',
                        'DELETE_FILE'
                    );
                    this.confirmDeleteDocumentModal.close();

                    return of(null);
                })
            )
            .subscribe((response: any) => {
                const r = response as WrapperResponse<any>;
                if (response) {
                    this.messageToastr.showMessage(
                        r,
                        'PRELOADING.DELEGATE.EDIT',
                        'DELETE_FILE'
                    );
                    if (response.id) {
                        this.refreshSearch();
                    }

                }

                this.currentIdDocumentToDelete = '-1';
                this.confirmDeleteDocumentModal.close();
            });
    }

}
