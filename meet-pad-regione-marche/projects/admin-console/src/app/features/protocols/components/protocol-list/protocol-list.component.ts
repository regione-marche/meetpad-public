import {
    Component,
    OnInit,
    OnChanges,
    Input,
    ViewChild,
    SimpleChanges,
    Output,
    EventEmitter,
} from '@angular/core';
import {
    AutoUnsubscribe,
    LoaderService,
    SectionLoading,
    WrapperGetData
} from '@common';
import {
    AbstractTableField,
    ActionItem,
    TableField,
    TemplateField,
    ActionsField
} from '@eng-ds/ng-toolkit';
import { Subscription, Observable, of } from 'rxjs';
import { I18nService } from '@eng-ds/ng-core';
import { ToastrService } from 'ngx-toastr';
import { takeUntil, tap, catchError } from 'rxjs/operators';
import { ProtocolService } from '../../core/services/protocol.services';
import { SearchProtocol } from '../../core/models/searchProtocol.model';
import { ProtocolState } from '../../enum/protocol-state.enum';

@Component({
    // tslint:disable-next-line: component-selector
    selector: 'admin-protocol-list',
    templateUrl: './protocol-list.component.html',
    styleUrls: ['./protocol-list.component.scss']
})
export class ProtocolListComponent extends AutoUnsubscribe
    implements OnInit, OnChanges {
    @Input('searchQuery') searchQuery: string;
    @Input('actions') actions: ActionItem[];

    @Output('view') view = new EventEmitter<SearchProtocol>();

  //  @ViewChild('typeTemplate') typeTemplate;
  //  @ViewChild('actionsTemplate') actionsTemplate;
    @ViewChild('templateProtocolState') templateProtocolState;


    list = [];
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
        private protocolService: ProtocolService,
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
            this.pageNumber = 1;
            this.runSearch(changes.searchQuery.currentValue);
        }
    }

    defineTableStructure(
        structure: AbstractTableField[]
    ): AbstractTableField[] {
        const actions = [
            new ActionItem(
                'BUTTON.UPDATESTATEPROTOCOL',

                (a, item: SearchProtocol) => {
                    this.view.emit(item);
                },
                'pencil'
            )
              
        ];

        structure.push(
            new TableField(
                this.i18nService.translate(
                   'PROTOCOLS.LIST.ID'
                ),
                'NUMBER',
                'idConference',
                true
            )
        );
        structure.push(
            new TableField(
                this.i18nService.translate(
                    'PROTOCOLS.LIST.EVENT'
                ),
                'NORMAL',
                'event',
                false
            )
        );

        structure.push(
            new TableField(
                this.i18nService.translate(
                    'PROTOCOLS.LIST.DOCUMENTNAME'
                ),
                'NORMAL',
                'documentName',
                false
            )
        );

        structure.push(
            new TableField(
                this.i18nService.translate(
                    'PROTOCOLS.LIST.ERROR'
                ),
                'NORMAL',
                'error',
                false
            )
        );

        structure.push(
            new TableField(
                this.i18nService.translate(
                    'PROTOCOLS.LIST.PROTOCOLNUMBER'
                ),
                'NORMAL',
                'protocolNumber',
                false
            )
        );

        structure.push(
            new TableField(
                this.i18nService.translate(
                    'PROTOCOLS.LIST.PROTOCOLDATE'
                ),
                'NORMAL',
                'protocolDate',
                false
            )
        );

        structure.push(
            new TemplateField(
                this.i18nService.translate(
                    'PROTOCOLS.LIST.PROTOCOLSTATE'
                ),
                this.templateProtocolState
            )
        );

        structure.push(
            new ActionsField(
                'PROTOCOLS.LIST.ACTIONS.TITLE',
                actions,
                this._canActionShowed.bind(this)
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

    public search(
        query?: string
    ): Observable<WrapperGetData<SearchProtocol>> {
        return this.protocolService
            .getProtocols(this.buildCriteriaDto(), query)
            .pipe(
                tap((results: WrapperGetData<SearchProtocol>) => {
                    this.list = results.list;
                    if (this.list.length === 0) {
                        this.toastr.warning(
                            this.i18nService.translate(
                                'PROTOCOLS.SEARCH.TOASTR.EMPTY_SEARCH.TEXT'
                            ),
                            this.i18nService.translate(
                                'PROTOCOLS.SEARCH.TOASTR.EMPTY_SEARCH.TITLE'
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

    private _canActionShowed(
        model: SearchProtocol,
        action: ActionItem
        ): boolean {
        
        let result: boolean;
        result = false;

        if (model.protocolState == ProtocolState.ERRORE) {
            result = true;
        }
        
        return result;
    }
}
