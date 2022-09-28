import {
    Component,
    OnInit,
    OnChanges,
    Input,
    ViewChild,
    SimpleChanges,
    Output,
    EventEmitter
} from '@angular/core';
import {
    SectionLoading,
    AutoUnsubscribe,
    LoaderService,
    WrapperGetData,
    HttpInternalErrorResponse,
    WrapperResponse,
    UserService,
    MessageToastrService,
    FormFieldGroup,
    WrapperPostPutData
} from '@common';
import {
    ModalComponent,
    AbstractTableField,
    ActionItem,
    TableField,
    TemplateField,
    ActionsField,
    DateModel
} from '@eng-ds/ng-toolkit';
import { Subscription, Observable, of } from 'rxjs';
import { I18nService } from '@eng-ds/ng-core';
import { ToastrService } from 'ngx-toastr';
import { Router, ActivatedRoute } from '@angular/router';
import { takeUntil, tap, catchError, take } from 'rxjs/operators';
import { Voting } from '@app/features/private/conference/core/models/voting/voting.model';
import {
    ConferenceStoreService,
    VotingService
} from '@app/features/private/conference/core';
import { VotingState } from '@app/core/enums/voting-state.enum';
import { Vote } from '@app/features/private/conference/core/models/voting/vote.model';
import { FormControl, Validators } from '@angular/forms';
import { VotingType } from 'projects/common/src/lib/enums/voting-type.enum';
import { defaultComboBox } from '@config';
import { formatDate } from '@angular/common';

@Component({
    selector: 'app-voting-table',
    templateUrl: './voting-table.component.html',
    styleUrls: ['./voting-table.component.scss']
})
export class VotingTableComponent extends AutoUnsubscribe
    implements OnInit, OnChanges {
    @Input('searchQuery') searchQuery = false;
    @Output('edit') edit: EventEmitter<Voting> = new EventEmitter();

    @ViewChild('confirmModal') confirmModal: ModalComponent;
    @ViewChild('voteModal') voteModal: ModalComponent;
    @ViewChild('votingType') votingType: ModalComponent;
    @ViewChild('votingState') votingState: ModalComponent;

    groupFieldsForVote: Map<string, FormFieldGroup> = new Map();

    actions: ActionItem[] = [];

    selectedId: string = '-1';
    selectedVoting: Voting = new Voting();
    list = [];
    tableStructure: AbstractTableField[] = [];
    loading = true;
    hasCriteria = false;

    modalTitle: string = '';
    modalText: string = '';

    votingDate: DateModel;
    
    modalButtons: ActionItem[] = [];
    disabledBtn: boolean = true;
    
    voteModalButtons: ActionItem[] = [];
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
        private toastr: ToastrService,
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private votingService: VotingService,
        private conferenceStoreService: ConferenceStoreService,
        private messageToastr: MessageToastrService
    ) {
        super();
    }

    get conferenceId(): string {
        return this.conferenceStoreService.conference.id;
    }

    ngOnInit(): void {
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
        this.tableStructure = this.defineTableStructure(this.tableStructure);
        this.sortFieldActive = this.getDefaultSortField();
        // gestire logica visualizzazione pulsanti action
        this._createGroupFieldsForVote();
        this.initActions();
        this.runSearch();
    }

    ngOnChanges(changes: SimpleChanges): void {
        if (!changes.searchQuery.firstChange) {
            this.pageNumber = 1;
            this.runSearch(changes.searchQuery.currentValue);
        }
    }

    initActions(): void {
        this.actions.push(
            new ActionItem(
                'BUTTON.VOTING.EDIT',

                (a, item) => {
                    this.selectedId = item.id;
                    this.edit.emit(item);
                },
                'edit'
            ),
            new ActionItem(
                'BUTTON.VOTING.DELETE',

                (a, item) => {
                    this.selectedId = item.id;
                    this.changeTextForAction('DELETE');

                    this.modalButtons[1].action = () => {
                        this.deleteVoting();
                    };

                    this.confirmModal.open();
                },
                'trash'
            ),
            new ActionItem(
                'BUTTON.VOTING.VOTE',

                (a, item) => {
                    this.selectedId = item.id;
                    this.selectedVoting = item;

                    switch (item.votingType.key){
                        case VotingType.VOTAZIONE: 
                            this.voteModal.title = 'CONFERENCE.WIZARD.VOTING.VOTE.MODAL.TITLE';
                            this._createGroupFieldsForVote();
                            this.voteModalButtons = [
                                new ActionItem(
                                    'CONFERENCE.WIZARD.VOTING.VOTE.MODAL.NO_BUTTON',
                                    (action: ActionItem) => {
                                        const detail: string = this.groupFieldsForVote
                                            .get('vote')
                                            .fields.get('detail').control.value;
                                        this.vote(detail, false);
                                    },
                                    null,
                                    null,
                                    null,
                                    this.disabledBtn
                                ),
                                new ActionItem(
                                    'CONFERENCE.WIZARD.VOTING.VOTE.MODAL.YES_BUTTON',
                                    (action: ActionItem) => {
                                        const detail: string = this.groupFieldsForVote
                                            .get('vote')
                                            .fields.get('detail').control.value;
                                        this.vote(detail, true);
                                    },
                                    null,
                                    null,
                                    null,
                                    this.disabledBtn
                                )
                            ];

                            this.voteModal.open();
                        break;
                        case VotingType.RILEVAZIONE_PRESENZE:

                            this.selectedId = item.id;
                            this.modalTitle = 'CONFERENCE.WIZARD.VOTING.VOTE.MODAL.RILEVAZIONE_PRESENZE_TITLE';                            
                            this.modalText = 'CONFERENCE.WIZARD.VOTING.VOTE.MODAL.CONFIRM_PRESENCE';

                            this.modalButtons = [
                                new ActionItem(
                                    'BUTTON.VOTING.CONFIRM',
                                    () => {
                                        this.selectedId = item.id;
                                        this.vote(VotingType.RILEVAZIONE_PRESENZE, true);
                                    },
                                    'confirm'
                                )
                            ];                           

                            this.confirmModal.open();

                        break;
                        case VotingType.CALENDARIZZAZIONE:
                            
                            this._createGroupFieldsForScheduling();
                            this.voteModal.title = 'CONFERENCE.WIZARD.VOTING.VOTE.MODAL.SCHEDULING_TITLE';
                            this.voteModalButtons = [
                                new ActionItem(
                                    'BUTTON.VOTING.CONFIRM',
                                    () => {
                                        this.selectedId = item.id;
                                        this.vote(this.votingDate.toDate().toISOString(), true);
                                    },
                                    'confirm'
                                )
                            ];            
                            this.voteModal.open();

                        break;

                    }
                    
                },
                'check-square'
            ),
            new ActionItem(
                'BUTTON.VOTING.START',

                (a, item) => {
                    this.selectedId = item.id;
                    this.changeTextForAction('START');
                    this.modalButtons[1].action = () => {
                        this.startVoting(item);
                    };
                    this.modalButtons[1].icon = 'play-circle';

                    this.confirmModal.open();
                },
                'play-circle'
            ),
            new ActionItem(
                'BUTTON.VOTING.CLOSE',

                (a, item) => {
                    this.selectedId = item.id;
                    this.changeTextForAction('CLOSE');
                    this.modalButtons[1].action = () => {
                        this.closeVoting(item);
                    };
                    this.modalButtons[1].icon = 'ban';
                    this.confirmModal.open();
                },
                'ban'
            ),
            new ActionItem(
                'BUTTON.VOTING.EVALUATE',

                (a, item) => {
                    this.selectedId = item.id;
                    this.edit.emit(item);
                },
                'edit'
            ),
        );
    }

    changeTextForAction(action: string): void {
        this.modalText = this.i18nService.translate(
            'CONFERENCE.WIZARD.VOTING.TABLE.MODAL.'.concat(action, '.TEXT')
        );
        this.modalTitle = this.i18nService.translate(
            'CONFERENCE.WIZARD.VOTING.TABLE.MODAL.'.concat(action, '.TITLE')
        );
        this.modalButtons = [
            new ActionItem(
                'CONFERENCE.WIZARD.VOTING.TABLE.MODAL.'.concat(
                    action,
                    '.CANCEL_BUTTON'
                ),
                () => {
                    this.selectedId = '-1';
                    this.confirmModal.close();
                },
                'close'
            ),
            new ActionItem(
                'CONFERENCE.WIZARD.VOTING.TABLE.MODAL.'.concat(
                    action,
                    '.OK_BUTTON'
                ),
                null,
                'trash'
            )
        ];
    }

    defineTableStructure(
        structure: AbstractTableField[]
    ): AbstractTableField[] {
        structure.push(
            new TableField(
                this.i18nService.translate('CONFERENCE.WIZARD.VOTING.TABLE.ID'),
                'NORMAL',
                'id',
                true
            )
        );
        structure.push(
            new TableField(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.VOTING.TABLE.SUBJECT'
                ),
                'NORMAL',
                'subject',
                false
            )
        );
        structure.push(
            new TemplateField(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.VOTING.TABLE.VOTING_TYPE'
                ),
               this.votingType
            )
        );

        structure.push(
            new TemplateField(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.VOTING.TABLE.VOTING_STATE'
                ),
               this.votingState
            )
        );

        structure.push(
            new ActionsField(
                'CONFERENCE.WIZARD.VOTING.TABLE.ACTIONS.TITLE',
                this.actions,
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
    private runSearch(query?: boolean) {
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

    public search(query?: boolean): Observable<WrapperGetData<Voting>> {
        return this.votingService
            .getVotings(this.buildCriteriaDto(), this.conferenceId, query)
            .pipe(
                tap((results: WrapperGetData<Voting>) => {
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

    deleteVoting(): void {
        this.votingService
            .deleteVoting(this.conferenceId, this.selectedId)
            .pipe(
                take(1),
                catchError((error: HttpInternalErrorResponse) => {
                    this.messageToastr.showErrorMessage(
                        error,
                        'CONFERENCE.WIZARD.VOTING.TABLE.MODAL',
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
                        'CONFERENCE.WIZARD.VOTING.TABLE.MODAL',
                        'DELETE'
                    );
                    if (r.code === '200') {
                        this._deleteFromList();
                    }
                }
                this.selectedId = '-1';
                this.confirmModal.close();
            });
    }

    private _deleteFromList() {
        const iu = this.list.findIndex(
            (_u: Voting) => _u.id === this.selectedId
        );
        if (iu < 0) {
            return;
        }
        this.list.splice(iu, 1);
    }

    startVoting(voting: Voting) {
        voting.startVoting();
        this.votingService
            .changeVotingState(this.conferenceId, this.selectedId, voting)
            .pipe(
                take(1),
                catchError((error: HttpInternalErrorResponse) => {
                    this.messageToastr.showErrorMessage(
                        error,
                        'CONFERENCE.WIZARD.VOTING.TABLE.MODAL',
                        'START'
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
                        'CONFERENCE.WIZARD.VOTING.TABLE.MODAL',
                        'START'
                    );
                    this.editVoting(r.data)
                }
                this.selectedId = '-1';
                this.confirmModal.close();
            });
    }

    closeVoting(voting: Voting) {
        voting.closeVoting();
        this.votingService
            .changeVotingState(this.conferenceId, this.selectedId, voting)
            .pipe(
                take(1),
                catchError((error: HttpInternalErrorResponse) => {
                    this.messageToastr.showErrorMessage(
                        error,
                        'CONFERENCE.WIZARD.VOTING.TABLE.MODAL',
                        'CLOSE'
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
                        'CONFERENCE.WIZARD.VOTING.TABLE.MODAL',
                        'CLOSE'
                    );
                    this.editVoting(r.data)  
                }
                this.selectedId = '-1';
                this.confirmModal.close();
            });
    }

    vote(detail: string, opinion: boolean) {
        // IMPORTANTE! capire se passare CF o Combo partecipante?
        const vote: Vote = new Vote({
            vote: opinion,
            detail: detail
        });

        this.votingService
            .postVote(this.conferenceId, this.selectedId, vote)
            .pipe(
                take(1),
                catchError((error: HttpInternalErrorResponse) => {
                    this.messageToastr.showErrorMessage(
                        error,
                        'CONFERENCE.WIZARD.VOTING.TABLE.MODAL',
                        'VOTE'
                    );
                    this.voteModal.close();
                    return of(null);
                })
            )
            .subscribe(response => {
                const r = response as WrapperResponse<any>;
                if (response) {
                    this.messageToastr.showMessage(
                        r,
                        'CONFERENCE.WIZARD.VOTING.TABLE.MODAL',
                        'VOTE'
                    );
                     this.editVoting(r.data);
                }
                
                this.selectedId = '-1';
                this.selectedVoting = new Voting();
                this.confirmModal.close();
                this.voteModal.close();
            });
    }

    private _createGroupFieldsForScheduling(): void {
        this.groupFieldsForVote = new Map();
        this.groupFieldsForVote.set('vote', {
            panel: false,
            accordion: false,
            panelHead: null,
            fields: new Map().set('voteDate', {
                type: 'date',
                label: 'CONFERENCE.WIZARD.VOTING.MODAL.FORM.VOTING_DATE',
                control: new FormControl(
                    this.selectedVoting.votingDate,
                    [Validators.required]
                ),
                size: '12|12|12',
                valueChange: (value: DateModel) => {
                    
                    this.votingDate = value;
                    
                    if (this.votingDate != null) {
                        this.voteModalButtons.forEach((action: ActionItem) => {
                            action.disabled = false;
                        });
                    } else {
                        this.voteModalButtons.forEach((action: ActionItem) => {
                            action.disabled = true;
                        });
                    }
                }
            })
            
        });
    }

    private _createGroupFieldsForVote(): void {
        this.groupFieldsForVote = new Map();
        this.groupFieldsForVote.set('vote', {
            panel: false,
            accordion: false,
            panelHead: null,
            fields: new Map().set('detail', {
                type: 'text',
                label: 'CONFERENCE.WIZARD.VOTING.VOTE.MODAL.DETAIL',
                control: new FormControl(null, [Validators.required]),
                size: '12|12|12',
                valueChange: (value: string) => {
                    if (value.length > 0) {
                        this.voteModalButtons.forEach((action: ActionItem) => {
                            action.disabled = false;
                        });
                    } else {
                        this.voteModalButtons.forEach((action: ActionItem) => {
                            action.disabled = true;
                        });
                    }
                }
            })
        });
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

    addVoting(voting: Voting){
        this.list.push(voting);
        this.refreshSearch();
    }

    editVoting(voting: Voting){
        this._deleteFromList();
        this.list.push(voting);
        this.refreshSearch();
    }

    private _canActionShowed(
        model: Voting,
        action: ActionItem
    ): Observable<boolean> {        
        return model.permissions[
            action.name
                .substring(action.name.lastIndexOf('.') + 1)
                .toLowerCase()
        ];
    }
}
