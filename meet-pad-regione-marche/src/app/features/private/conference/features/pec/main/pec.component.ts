import {
    Component,
    OnInit,
    Output,
    EventEmitter,
    Input,
    ViewChild
} from '@angular/core';
import { FormControl } from '@angular/forms';

import { Subscription, Observable, of } from 'rxjs';
import { takeUntil, tap, catchError } from 'rxjs/operators';

import { ToastrService } from 'ngx-toastr';

import { I18nService } from '@eng-ds/ng-core';

import {
    AbstractTableField,
    TableField,
    ActionItem,
    ActionsField,
    ModalComponent
} from '@eng-ds/ng-toolkit';

import {
    SectionLoading,
    LoaderService,
    WrapperGetData,
    WrapperPostPutData,
    AutoUnsubscribe,
    FormFieldGroup
} from '@common';

import { ActionForm } from '@app/core';

import {
    Pec,
    ConferenceStoreService,
    ConferencePermissionsService,
    PecService
} from '../../../core';

@Component({
    selector: 'app-conference-pec',
    templateUrl: './pec.component.html',
    styleUrls: ['./pec.component.scss']
})
export class PecComponent extends AutoUnsubscribe implements OnInit {
    searchCriteria: Pec;

    titleSearch: string = this.i18nService.translate(
        'CONFERENCE.WIZARD.PEC.SEARCH.TITLE'
    );

    searchOpen: boolean = false;
    searchInitialOpen: boolean = false;

    initialSearch: boolean = true;

    @Output('onNext') onNext: EventEmitter<void> = new EventEmitter();

    @Input('list') list = [];

    @ViewChild('pecModal') modal: ModalComponent;

    action: ActionForm = this.conferenceStoreService.getStepActionAfterIndiction();

    emptyMessage: string = this.i18nService.translate(
        'CONFERENCE.WIZARD.PEC.TABLE.EMPTY_MESSAGE'
    );

    tableStructure: AbstractTableField[] = [];

    loading$ = this.loaderService.getLoading$(SectionLoading.PEC_TAB);

    loading = true;

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
    public pageSizeNumber: number = 10;
    public totalPages = 0;

    /**
     * This will contain the subscription to search operation
     */
    protected searchSubscription: Subscription;

    saveFn: (pec: Pec) => Observable<WrapperPostPutData>;
    saveCompleteFn: (response: WrapperPostPutData) => void;

    groupFields: Map<string, FormFieldGroup> = new Map();

    constructor(
        private conferenceStoreService: ConferenceStoreService,
        private i18nService: I18nService,
        private loaderService: LoaderService,
        private pecService: PecService,
        private toastr: ToastrService,
        private conferencePermissionsService: ConferencePermissionsService
    ) {
        super();
    }

    ngOnInit() {
        this._initPermission();
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
        this.tableStructure = this.defineTableStructure(this.tableStructure);
        this.sortFieldActive = this.getDefaultSortField();
        this.runSearch();
    }

    get conferenceId(): string {
        return this.conferenceStoreService.conference.id;
    }

    private _createGroupsFields(pec: Pec = new Pec()): void {
        // work arround for angular change detection
        this.groupFields = new Map();
        this.groupFields.set('pec', {
            panel: true,
            // panelHead: 'CONFERENCE.WIZARD.PARTICIPANTS.MODAL.ACTOR.TITLE',
            panelHead: null,
            fields: new Map()
                .set('sender', {
                    type: 'text',
                    label: 'CONFERENCE.WIZARD.PEC.FORM.SENDER',
                    control: new FormControl(
                        {
                            value: pec.sender,
                            disabled: true
                        },
                        []
                    ),
                    size: '12|6|6'
                })
                .set('recipient', {
                    type: 'text',
                    label: 'CONFERENCE.WIZARD.PEC.FORM.RECIPIENT',
                    control: new FormControl(
                        {
                            value: pec.recipient,
                            disabled: true
                        },
                        []
                    ),
                    size: '12|6|6'
                })
                .set('recipientPec', {
                    type: 'text',
                    label: 'CONFERENCE.WIZARD.PEC.FORM.RECIPIENT_PEC',
                    control: new FormControl(
                        {
                            value: pec.recipientPec,
                            disabled: true
                        },
                        []
                    ),
                    size: '12|6|6'
                })
                .set('sentDate', {
                    type: 'date',
                    label: 'CONFERENCE.WIZARD.PEC.FORM.DATE',
                    control: new FormControl(
                        {
                            value: pec.sentDate,
                            disabled: true
                        },
                        []
                    ),
                    size: '12|6|6'
                })
                .set('status', {
                    type: 'text',
                    label: 'CONFERENCE.WIZARD.PEC.FORM.STATE',
                    control: new FormControl(
                        {
                            value: pec.status.value,
                            disabled: true
                        },
                        []
                    ),
                    size: '12|6|6'
                })
                .set('statusMessage', {
                    type: 'text',
                    label: 'CONFERENCE.WIZARD.PEC.FORM.STATUS_MESSAGE',
                    control: new FormControl(
                        {
                            value: pec.statusMessage,
                            disabled: true
                        },
                        []
                    ),

                    size: '12|6|6'
                })
                .set('event', {
                    type: 'text',
                    label: 'CONFERENCE.WIZARD.PEC.FORM.EVENT',
                    control: new FormControl(
                        {
                            value: pec.event.value,
                            disabled: true
                        },
                        []
                    ),

                    size: '12|6|6'
                })
        });
    }

    private _initPermission(): void {
        this.conferencePermissionsService.apply(
            this,
            this.appSections.CONFERENCE_PEC,
            this.conferenceStoreService
        );
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

        if (this.initialSearch) {
            this.searchSubscription = this.search()
                .pipe(takeUntil(this.destroy$))
                .subscribe(() => {
                    this.stopPageLoading();
                });
        } else {
            this.searchSubscription = this.advancedSarch()
                .pipe(takeUntil(this.destroy$))
                .subscribe(() => {
                    this.stopPageLoading();
                });
        }
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

    onNewPecForm(searchData: Pec): any {
        this.searchCriteria = searchData;
        this.initialSearch = false;
        this.runSearch();
    }

    getDefaultSortField(): string {
        return 'sender';
    }

    /**
     * Change the sort field to input one. If the field it's the same change the sort type.
     * @param field
     */
    changeSort(field: string) {
        if (field === this.sortFieldActive) {
            // switch sort type
            this.sortType = this.sortType === 'asc' ? 'desc' : 'asc';
            this.pageNumber = 1;
            this.pageSizeNumber = 10;
        } else {
            this.sortType = 'asc';
            this.sortFieldActive = field;
            this.pageNumber = 1;
            this.pageSizeNumber = 10;
        }
        // after change refresh the search
        this.refreshSearch();
    }

    /**
     * Change the page to load
     */
    changePage(pageNumber: number) {
        if (pageNumber > 0) {
            this.pageNumber = pageNumber--;
        } else {
            this.pageNumber = pageNumber;
        }

        this.runSearch();
    }

    changePageElementsShowed(pageSizeNumber: number) {
        this.pageSizeNumber = pageSizeNumber;
        this.refreshSearch();
    }

    /**
     * Refresh the search and managing also che reset of page cache
     */
    refreshSearch(newSearch = false) {
        if (newSearch) {
            this.pageNumber = 1;
        }
        this.runSearch();
    }

    search(): Observable<WrapperGetData<Pec>> {
        return this.pecService
            .getList(this.buildCriteriaDto(), this.conferenceId)
            .pipe(
                // TODO: ridonante
                tap((results: WrapperGetData<Pec>) => {
                    this.list = results.list;
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

    advancedSarch(): Observable<WrapperGetData<Pec>> {
        return this.pecService
            .advancedSearch(
                this.buildCriteriaDto(),
                this.conferenceId,
                this.searchCriteria
            )
            .pipe(
                // TODO: ridonante
                tap((results: WrapperGetData<Pec>) => {
                    this.list = results.list;
                    if (this.list.length === 0) {
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

    resetSearch() {
        this.sortType = 'asc';
        this.sortFieldActive = this.getDefaultSortField();
        this.pageNumber = 0;
        this.pageSizeNumber = 10;
    }

    startPageLoading() {
        this.loading = true;
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
    }

    stopPageLoading() {
        this.loading = false;
        this.conferenceStoreService.hidePageLoader();
    }

    isReadonly(): boolean {
        return this.action === ActionForm.READONLY;
    }

    showFooterBtn(): boolean {
        return this.action === ActionForm.CREATE;
    }

    // TODO modello pec
    defineTableStructure(
        structure: AbstractTableField[]
    ): AbstractTableField[] {
        const actions = [
            new ActionItem(
                'BUTTON.VIEW',

                (a, item: Pec) => {
                    this.view(item);
                },
                'eye'
            )
        ];

        structure.push(
            new TableField(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.PEC.TABLE.SENDER'
                ),
                'NORMAL',
                'sender',
                true
            )
        );
        structure.push(
            new TableField(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.PEC.TABLE.RECIPIENT'
                ),
                'NORMAL',
                'recipient',
                true
            )
        );
        structure.push(
            new TableField(
                this.i18nService.translate('CONFERENCE.WIZARD.PEC.TABLE.STATE'),
                'NORMAL',
                'status.value',
                false
            )
        );
        structure.push(
            new TableField(
                this.i18nService.translate('CONFERENCE.WIZARD.PEC.TABLE.EVENT'),
                'NORMAL',
                'event.value',
                false
            )
        );
        structure.push(
            new TableField(
                this.i18nService.translate('CONFERENCE.WIZARD.PEC.TABLE.DATE'),
                'NORMAL',
                'sentDate',
                true
            )
        );
        structure.push(
            new ActionsField('CONFERENCE.WIZARD.PEC.TABLE.ACTIONS', actions)
        );

        return structure;
    }

    view(pec: Pec): void {
        this._createGroupsFields(pec);
        this.modal.open();
    }
}
