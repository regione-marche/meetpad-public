import {
    Component,
    OnInit,
    OnChanges,
    ViewChild,
    SimpleChanges
} from '@angular/core';
import { PreloadingService } from '../core/services/preloading.service';
import {
    AutoUnsubscribe,
    LoaderService,
    SectionLoading,
    WrapperGetData
} from '@common';
import { takeUntil, tap, catchError } from 'rxjs/operators';
import { I18nService } from '@eng-ds/ng-core';
import {
    ModalComponent,
    AbstractTableField,
    ActionItem,
    TableField,
    ActionsField
} from '@eng-ds/ng-toolkit';
import { Subscription, Observable, of } from 'rxjs';
import { ToastrService } from 'ngx-toastr';
import { Router, ActivatedRoute } from '@angular/router';
import { ConferenceType } from '../core/models/conferenceType.model';

@Component({
    // tslint:disable-next-line: component-selector
    selector: 'admin-preloading-main',
    templateUrl: './main.component.html',
    styleUrls: ['./main.component.scss']
})
export class MainPreloadingComponent extends AutoUnsubscribe
    implements OnInit, OnChanges {
    @ViewChild('typeTemplate') typeTemplate;
    @ViewChild('actionsTemplate') actionsTemplate;
    @ViewChild('confirmModal') confirmModal: ModalComponent;

    currentIdToDelete: string = '-1';
    conferenceType: string;
    list = [];
    tableStructure: AbstractTableField[] = [];
    loading = true;
    hasCriteria = false;
    descriptionText: string = this.i18nService.translate(
        'PRELOADING.MAIN.DESCRIPTION'
    );
    modalText: string = this.i18nService.translate(
        'PRELOADING.PARTICIPANT.CONFIRM_DELETE_MODAL.MESSAGE'
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
    actions: ActionItem[] = [];

    constructor(
        private i18nService: I18nService,
        private loaderService: LoaderService,
        private preloadingService: PreloadingService,
        private toastr: ToastrService,
        private router: Router,
        private activatedRoute: ActivatedRoute
    ) {
        super();
    }

    ngOnInit(): void {
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
        this.defineActions();

        this.tableStructure = this.defineTableStructure(this.tableStructure);
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

    defineActions(): void {
        this.actions.push(
            new ActionItem(
                'PRELOADING.MAIN.ACTIONS.APPLICANT',
                (a, item) => {
                    this.router.navigate([
                        'admin',
                        'preloading',
                        item.id,
                        'applicants'
                    ]);
                },
                'users'
            ),
            new ActionItem(
                'PRELOADING.MAIN.ACTIONS.PARTICIPANT',
                (a, item) => {
                    this.router.navigate([
                        'admin',
                        'preloading',
                        item.id,
                        'participants'
                    ]);
                },
                'shield'
            ),
            new ActionItem(
                'PRELOADING.MAIN.ACTIONS.COMPANY',
                (a, item) => {
                    this.router.navigate([
                        'admin',
                        'preloading',
                        item.id,
                        'companies'
                    ]);
                },
                'building'
            ),
            new ActionItem(
                'PRELOADING.MAIN.ACTIONS.DELEGATE',
                (a, item) => {
                    this.router.navigate([
                        'admin',
                        'preloading',
                        item.id,
                        'delegates'
                    ]);
                },
                'users'
            ),
            new ActionItem(
                'PRELOADING.MAIN.ACTIONS.PREACCREDITATION',
                (a, item) => {
                    this.router.navigate([
                        'admin',
                        'preloading',
                        item.id,
                        'preaccreditation'
                    ]);
                },
                'users'
            )
        );
    }
    defineTableStructure(
        structure: AbstractTableField[]
    ): AbstractTableField[] {
        structure.push(
            new TableField(
                this.i18nService.translate('PRELOADING.MAIN.TITLE'),
                'NORMAL',
                'name',
                false
            )
        );

        structure.push(
            new ActionsField(
                'PRELOADING.MAIN.ACTIONS.TITLE',
                this.actions,
                () => true
            )
        );

        return structure;
    }
    private _canActionShowed(): Observable<boolean> {
        return this._handlePermissionApply();
    }

    private _handlePermissionApply(): Observable<boolean> {
        return of(true);
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

        this.searchSubscription = this.search(this.conferenceType, query)
            .pipe(takeUntil(this.destroy$))
            .subscribe(list => {
                this.stopPageLoading();
            });
    }

    public search(
        conferenceType: string,
        query?: string
    ): Observable<WrapperGetData<ConferenceType>> {
        return this.preloadingService.getConferenceTypes().pipe(
            tap((results: WrapperGetData<ConferenceType>) => {
                this.list = results.list;
                if (this.list.length === 0) {
                    this.toastr.warning(
                        this.i18nService.translate(
                            'PRELOADING.PARTICIPANT.LIST.TOASTR.EMPTY_MESSAGE'
                        ),
                        this.i18nService.translate(
                            'PRELOADING.PARTICIPANT.LIST.TOASTR.TITLE'
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

    public startPageLoading() {
        this.loading = true;
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
    }

    public stopPageLoading() {
        this.loading = false;
        this.loaderService.hideLoading(SectionLoading.ALL_CONTENT, 500);
    }
}
