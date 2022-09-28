import {
    Component,
    OnInit,
    Input,
    Output,
    EventEmitter,
    ViewChild
} from '@angular/core';
import { Subscription, of, Observable } from 'rxjs';
import { mergeMap,  takeUntil, catchError, tap } from 'rxjs/operators';

import {
    AbstractTableField,
    ActionItem,
    TableField,
    TemplateField,
    ActionsField
} from '@eng-ds/ng-toolkit';

import { I18nService } from '@eng-ds/ng-core';

import {
    ConferenceState,
    appRoleAuthMappingConfig,
    UserPortalService
} from '@app/core';

import {
    ApplicationRole,
    ConferenceRole,
    AppSections,
    LoaderService,
    User,
    WrapperGetData,
    SectionLoading,
    AutoUnsubscribe
} from '@common';

import { ActionForm } from '@app/core';

import { ProtocollingService } from '@app/features/private/conference/core/services/protocol/protocol.service';
import {
    conferenceRoleAuthMappingConfig,
    ConferenceStoreService
} from '@app/features/private/conference/core';
import { Protocol } from '@app/features/private/conference/core/models/protocol/protocolling.model';
import { ProtocolState } from '@app/core/enums/protocol-state.enum';


@Component({
    selector: 'app-protocolling-table',
    templateUrl: './protocolling-table.component.html',
    styleUrls: ['./protocolling-table.component.scss']
})
export class ProtocolTableComponent extends AutoUnsubscribe
    implements OnInit {
    @Input('list') list = [];
    @Input('action') action: ActionForm;
    @Output('view') view = new EventEmitter<Protocol>();

    @ViewChild('actionTemplate') actionTemplate;
    @ViewChild('templateProtocolState') templateProtocolState;


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
     * Actual page of result loaded
     */
    public pageNumber: number = 1;
    public pageSizeNumber: number = 1000;
    public totalPages = 0;

    /**
     * This will contain the subscription to search operation
     */
    protected searchSubscription: Subscription;

    constructor(
        private i18nService: I18nService,
        private loaderService: LoaderService,
        private protocolService: ProtocollingService,
        private userService: UserPortalService,
        private conferenceStoreService: ConferenceStoreService
    ) {
        super();
    }

    ngOnInit(): void {
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
        this.tableStructure = this.defineTableStructure(this.tableStructure);
        this.sortFieldActive = this.getDefaultSortField();
        this.runSearch();
    }

    get conferenceId(): string {
        return this.conferenceStoreService.conference.id;
    }

    defineTableStructure(
        structure: AbstractTableField[]
    ): AbstractTableField[] {
        const actions = [
            new ActionItem(
                'BUTTON.UPDATESTATEPROTOCOL',

                (a, item: Protocol) => {
                    this.view.emit(item);
                },
                'pencil'
            )
              
        ];

        structure.push(
            new TableField(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.PROTOCOL.TABLE.EVENT'
                ),
                'NORMAL',
                'event',
                true
            )
        );

        structure.push(
            new TableField(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.PROTOCOL.TABLE.DOCUMENTNAME'
                ),
                'NORMAL',
                'documentName',
                true
            )
        );

        structure.push(
            new TableField(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.PROTOCOL.TABLE.ERROR'
                ),
                'NORMAL',
                'error',
                true
            )
        );

        structure.push(
            new TableField(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.PROTOCOL.TABLE.PROTOCOLNUMBER'
                ),
                'NORMAL',
                'protocolNumber',
                true
            )
        );
        structure.push(
            new TableField(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.PROTOCOL.TABLE.PROTOCOLDATE'
                ),
                'NORMAL',
                'protocolDate',
                false
            )
        );
     

        structure.push(
            new TemplateField(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.PROTOCOL.TABLE.STATE.TITLE'
                ),
                this.templateProtocolState
            )
        );
        structure.push(
            new ActionsField(
                'CONFERENCE.WIZARD.PROTOCOL.TABLE.ACTIONS',
                actions,
                this._canActionShowed.bind(this)
            )
        );   
                
        
          

        
        return structure;
    }

    getDefaultSortField(): string {
        return 'surname';
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
    private runSearch() {
        // 3. manage old search don't complete
        if (this.searchSubscription) {
            this.searchSubscription.unsubscribe();
            // this.stopPageLoading();
        }
        // 4. run search
        this.startPageLoading();

        this.searchSubscription = this.search()
            .pipe(takeUntil(this.destroy$))
            .subscribe(() => {
                this.stopPageLoading();
            });
    }

    public search(): Observable<WrapperGetData<Protocol>> {
        return this.protocolService
            .getProtocollings(this.buildCriteriaDto(), this.conferenceId)
            .pipe(
                // TODO: ridonante
                tap((results: WrapperGetData<Protocol>) => {
                    this.list = results.list;
                    // tslint:disable-next-line:radix
                    this.totalPages = Math.ceil(
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
        this.conferenceStoreService.hidePageLoader();
    }

    isReadonly(): boolean {
        return this.action === ActionForm.READONLY;
    }

    private _canActionShowed(
        model: Protocol,
        action: ActionItem
        ): boolean {
        
        let result: boolean = false;

        this.userService.getConferenceProfile(this.conferenceStoreService.conference.id)
                            .pipe(
                                // xmf todo: handle user rights in orer to allow external users
                                mergeMap((user: User) => {
                                    if (user) {
                                        return this._handlePermissionApply(
                                            appRoleAuthMappingConfig,
                                            user.profile.key,
                                            this.conferenceStoreService
                                                .conference.state
                                                .key as ConferenceState
                                        );
                                    } else {
                                        return this.userService
                                            .getRole()
                                            .pipe(
                                                mergeMap(
                                                    (
                                                        userRole: ApplicationRole
                                                    ) =>
                                                        this._handlePermissionApply(
                                                            appRoleAuthMappingConfig,
                                                            userRole,
                                                            this
                                                                .conferenceStoreService
                                                                .conference
                                                                .state
                                                                .key as ConferenceState
                                                        )
                                                )
                                            );
                                    }
                                })
                                /*
                                ,
                                tap((canActivate: boolean) => {
                                    if (canActivate) {
                                        if (model.protocolState == ProtocolState.ERRORE) {
                                            result = true;
                                        }
                                    }
                                })
                                */
                            )
                            .subscribe(response => {
                                if (response && !this.isReadonly()) {
                                    if (model.protocolState == ProtocolState.ERRORE) {
                                        result = true;
                                    }
                                }
                            });

        return result;
    }

    private _handlePermissionApply(
        _mappingRole: Map<any, any>,
        role: ConferenceRole | ApplicationRole,
        conferenceState: ConferenceState
    ): Observable<boolean> {
        const section = _mappingRole.get(AppSections.CONFERENCE_PROTOCOL);
        if (!section) { 
            return of(false);
        }
        const permission = section.get(role);
        if (permission) {
            const result = permission.apply(this, {
                conferenceState
            });
            if (result instanceof Observable) {
                return result;
            } else if (typeof result === 'boolean') {
                return of(result);
            }
            return of(true);
        } else {
            return of(false);
        }
    }

    canView(conferenceState: ConferenceState): boolean {
        return (
            conferenceState === ConferenceState.COMPILING ||
            conferenceState === ConferenceState.DRAFT ||
            conferenceState === ConferenceState.CLOSED ||
            conferenceState === ConferenceState.JUDGMENT
        );
    }
}
