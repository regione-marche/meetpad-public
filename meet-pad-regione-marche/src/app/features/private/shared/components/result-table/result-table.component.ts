/* tslint:disable:no-inferrable-types */

import {
    Component,
    OnInit,
    Input,
    OnChanges,
    SimpleChanges,
    ViewChild
} from '@angular/core';
import { Router } from '@angular/router';

import { Subscription, Observable, of } from 'rxjs';
import { tap, catchError, takeUntil } from 'rxjs/operators';

import { ToastrService } from 'ngx-toastr';

import {
    AbstractTableField,
    TableField,
    ActionsField,
    ActionItem,
    TemplateField,
    ModalComponent,
    TableResultComponent
} from '@eng-ds/ng-toolkit';

import { I18nService, ConfigService, LoggerService } from '@eng-ds/ng-core';

import {
    LoaderService,
    WrapperGetData,
    SectionLoading,
    AutoUnsubscribe
} from '@common';

import { ConferenceService, ResultTableMode } from '@app/core';
import { SearchConference } from '@features/private/search/models/search-conference.model';

@Component({
    selector: 'app-result-table',
    templateUrl: './result-table.component.html',
    styleUrls: ['./result-table.component.scss']
})
export class ResultTableComponent extends AutoUnsubscribe
    implements OnChanges, OnInit {
    @Input() title: string;
    @Input() mode: ResultTableMode;
    @Input() searchedValue: string;
    @Input() stateValue: string;
    results: SearchConference[] = [];

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

    public dateFormat = this.configService.get('dateFormat.toUI');

    /**
     * This will contain the subscription to search operation
     */
    protected searchSubscription: Subscription;

    /**
     * Contain the table structure for result
     */
    tableStructure: AbstractTableField[] = [];

    @ViewChild('conferenceTypeTemplate') conferenceTypeTemplate;
    @ViewChild('conferenceEndProcedureTemplate') conferenceEndProcedureTemplate;
    @ViewChild('conferenceStateTemplate') conferenceStateTemplate;
    @ViewChild('conferencepPoceedingAdministrationTemplate')
    conferencepPoceedingAdministrationTemplate;
    @ViewChild('conferenceApplicant') conferenceApplicant;
    @ViewChild('conferenceDenomination') conferenceDenomination;

    @ViewChild('confirmModal') confirmModal: ModalComponent;
    @ViewChild('confirmModalForClone') confirmModalForClone: ModalComponent;
    @ViewChild(TableResultComponent) tableResult: TableResultComponent;

    modalText: string = this.i18nService.translate(
        'SEARCH.CONFIRM_DELETE_MODAL.MESSAGE'
    );

    modalTextForClone: string = this.i18nService.translate(
        'SEARCH.CONFIRM_CLONE_MODAL.MESSAGE'
    );

    idToDelete: string;
    idToClone: string;

    modalButtons: ActionItem[] = [
        new ActionItem(
            'SEARCH.CONFIRM_DELETE_MODAL.CANCEL_BUTTON',
            (action: ActionItem) => {
                this.confirmModal.close();
            },
            'close'
        ),
        new ActionItem(
            'SEARCH.CONFIRM_DELETE_MODAL.OK_BUTTON',
            (action: ActionItem) => {
                this.deleteConference();
            },
            'trash'
        )
    ];

    modalButtonsForClone: ActionItem[] = [
        new ActionItem(
            'SEARCH.CONFIRM_CLONE_MODAL.CANCEL_BUTTON',
            (action: ActionItem) => {
                this.confirmModal.close();
            },
            'close'
        ),
        new ActionItem(
            'SEARCH.CONFIRM_CLONE_MODAL.OK_BUTTON',
            (action: ActionItem) => {
                this.cloneConference();
            },
            'copy'
        )
    ];

    actions: ActionItem[] = [];

    ///////////////////////////////////////////

    constructor(
        private i18nService: I18nService,
        private conferenceService: ConferenceService,
        private configService: ConfigService,
        private router: Router,
        private loggerService: LoggerService,
        private loaderService: LoaderService,
        private toastr: ToastrService
    ) {
        super();
    }

    get loading$(): Observable<boolean> {
        return this.loaderService.getLoading$(SectionLoading.CLONE_MODAL);
    }

    ngOnInit(): void {
        
        this.tableStructure = this.defineTableStructure(this.tableStructure);
        this._initAction();
        this.sortFieldActive = this.getDefaultSortField();
        if (this.mode === ResultTableMode.DESKTOP) {
            this.runSearch();
        }

        
    }

    ngOnChanges(changes: SimpleChanges): void {
        if (
            (changes.searchedValue &&
            changes.searchedValue.currentValue !== null &&
            changes.searchedValue.currentValue !== undefined &&
            !changes.searchedValue.firstChange) ||
            (changes.stateValue &&
                changes.stateValue.currentValue !== null &&
                changes.stateValue.currentValue !== undefined &&
                !changes.stateValue.firstChange)
        ) {
            this.refreshSearch();
        }
    }

    defineTableStructure(
        structure: AbstractTableField[]
    ): AbstractTableField[] {
        structure.push(
            new TableField(
                this.i18nService.translate('SEARCH.RESULT_TABLE.ID'),
                'NUMBER',
                'id',
                true
            )
        );
        structure.push(
            new TableField(
                this.i18nService.translate(
                    'SEARCH.RESULT_TABLE.REQUEST_REFERENCE'
                ),
                'NORMAL',
                'requestReference',
                true
            )
        );

        structure.push(
            this._getTemplateField(
                'SEARCH.RESULT_TABLE.TYPE',
                'conferenceType',
                this.conferenceTypeTemplate
            )
        );
        structure.push(
            this._getTemplateField(
                'SEARCH.RESULT_TABLE.END_PROCEDURE',
                'endProcedureDate',
                this.conferenceEndProcedureTemplate
            )
        );
        structure.push(
            this._getTemplateField(
                'SEARCH.RESULT_TABLE.STATE',
                'status',
                this.conferenceStateTemplate
            )
        );
        structure.push(
            this._getTemplateField(
                'SEARCH.RESULT_TABLE.PROCEEDING_ADMINISTRATION',
                'proceedingCompany',
                this.conferencepPoceedingAdministrationTemplate
            )
        );
        structure.push(
            this._getTemplateField(
                'SEARCH.RESULT_TABLE.APPLICANT',
                'applicant',
                this.conferenceApplicant
            )
        );
        structure.push(
            this._getTemplateField(
                'SEARCH.RESULT_TABLE.OWNER',
                'denomination',
                this.conferenceDenomination
            )
        );
        structure.push(
            new TableField(
                this.i18nService.translate('SEARCH.RESULT_TABLE.IDCLONE'),
                'NORMAL',
                'idParent',
                true
            )
        );

        structure.push(
            new ActionsField(
                'SEARCH.RESULT_TABLE.ACTIONS.TITLE',
                this.actions,
                this._canActionShowed.bind(this)
            )
        );

        return structure;
    }

    private _getTemplateField(label: string, fieldName: string, template: any) {
        const field = new TemplateField(
            this.i18nService.translate(label),
            template
        );
        field.sortable = true;
        field.propertyNameToUse = fieldName;

        return field;
    }

    private _canActionShowed(
        model: SearchConference,
        action: ActionItem
    ): boolean {
        return model.permissions[
            action.name
                .substring(action.name.lastIndexOf('.') + 1)
                .toLowerCase()
        ];
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
        if (this.mode === ResultTableMode.DESKTOP) {
            this.searchSubscription = this.desktopSearch()
                .pipe(takeUntil(this.destroy$))
                .subscribe(() => {
                    this.stopPageLoading();
                });
        } else {
            this.startPageLoading();
            this.searchSubscription = this.unifyResearch()
                .pipe(takeUntil(this.destroy$))
                .subscribe(() => {
                    this.stopPageLoading();
                });
        }
    }

    public unifyResearch(): Observable<WrapperGetData<SearchConference>> {
        return this.conferenceService
            .unifyResearch(this.buildCriteriaDto(), this.searchedValue, this.stateValue)
            .pipe(
                // TODO: ridonante
                tap((results: WrapperGetData<SearchConference>) => {
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

    public desktopSearch(): Observable<WrapperGetData<SearchConference>> {
        return this.conferenceService
            .desktopSearch(this.buildCriteriaDto())
            .pipe(
                // TODO: ridonante
                tap((results: WrapperGetData<SearchConference>) => {
                    this.results = results.list;
                    if (this.results.length === 0) {
                        this.toastr.info(
                            this.i18nService.translate(
                                'SEARCH.TOASTR.EMPTY_DESKTOP.TEXT'
                            ),
                            this.i18nService.translate(
                                'SEARCH.TOASTR.EMPTY_DESKTOP.TITLE'
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

    openModalForDelete(): void {
        this.confirmModal.open();
    }

    openModalForClone(): void {
        this.confirmModalForClone.open();
    }

    cloneConference(): void {
        this.loaderService.showLoading(SectionLoading.CLONE_MODAL);
        this.conferenceService.clone(this.idToClone).subscribe((data: any) => {
            this.router.navigate(['/conference', data.id]);
        });
    }

    deleteConference(): void {
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
        this.conferenceService
            .delete(this.idToDelete)
            .pipe(takeUntil(this.destroy$))
            .subscribe(res => {
                this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
                this.confirmModal.close();
                this._deleteFromResults();
                this.toastr.info(
                    this.i18nService.translate(
                        'SEARCH.TOASTR.SUCCESS_DELETE.TEXT'
                    ),
                    this.i18nService.translate(
                        'SEARCH.TOASTR.SUCCESS_DELETE.TITLE'
                    )
                );
            });
    }

    private _deleteFromResults(): void {
        const i = this.results.findIndex(
            (conf: SearchConference) => conf.id === this.idToDelete
        );
        this.results.splice(i, 1);
    }

    private _initAction(): void {
        // -+ Aggiungere if per ogni pulsante, sulla base di quanto ritornato dal BE
        this.actions.push(
            new ActionItem(
                'SEARCH.RESULT_TABLE.ACTIONS.BUTTONS.ENTER',
                (a, item: SearchConference) => {
                    this.loggerService.log('edit', item);
                    this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
                    setTimeout(() => {
                        this.router.navigate(['/conference', item.id]);
                    });
                },
                'sign-in'
            ),
            new ActionItem(
                'SEARCH.RESULT_TABLE.ACTIONS.BUTTONS.DELETE',
                (a, item) => {
                    this.openModalForDelete();
                    this.idToDelete = item.id;
                },
                'trash'
            ),
            new ActionItem(
                'SEARCH.RESULT_TABLE.ACTIONS.BUTTONS.SKYPE',
                (a, item) => {
                    window.open(item.address, '_blank').focus();
                },
                'skype'
            ),
            new ActionItem(
                'SEARCH.RESULT_TABLE.ACTIONS.BUTTONS.CLONE',
                (a, item) => {
                    this.openModalForClone();
                    this.idToClone = item.id;
                },
                'copy'
            )
        );
    }
}
