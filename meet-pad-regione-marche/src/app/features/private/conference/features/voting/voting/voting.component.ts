import { Component, OnInit, ViewChild } from '@angular/core';
import {
    AutoUnsubscribe,
    WrapperPostPutData,
    LoaderService,
    FormFieldGroup,
    SectionLoading,
    ComboBox,
    FormField,
    UserService,
    HttpInternalErrorResponse,
    WrapperResponse,
    MessageToastrService
} from '@common';
import {
    ModalComponent,
    ActionItem,
    ErrorMessage,
    AbstractTableField,
    TableField,
    TemplateField,
    ActionsField
} from '@eng-ds/ng-toolkit';
import { Observable, of } from 'rxjs';
import { ActionForm, ConferenceState } from '@app/core';
import { QuerySearchEvents } from '../../events/models/query-search-events.model';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { I18nService } from '@eng-ds/ng-core';
import { ConferenceStoreService, VotingService, User } from '../../../core';
import { Voting } from '../../../core/models/voting/voting.model';
import { Vote } from '../../../core/models/voting/vote.model';
import { VotingState } from '@app/core/enums/voting-state.enum';
import { takeUntil, catchError, take } from 'rxjs/operators';
import { ToastrService } from 'ngx-toastr';
import { HttpResponse } from '@angular/common/http';
import { VotingType } from 'projects/common/src/lib/enums/voting-type.enum';
import { VotingRule } from 'projects/common/src/lib/enums/voting-rule.enum';
import { VotingResult } from '@app/core/enums/voting-result.enum';

@Component({
    selector: 'app-voting',
    templateUrl: './voting.component.html',
    styleUrls: ['./voting.component.scss']
})
export class VotingComponent extends AutoUnsubscribe implements OnInit {
    @ViewChild('addModal') addModal: ModalComponent;

    @ViewChild('dateTemplate') dateTemplate;
    @ViewChild('voteTemplateVoting') voteTemplateVoting;
    @ViewChild('voteTemplateCalendar') voteTemplateCalendar;
    @ViewChild('detailLookup') detailLookup;
    
    @ViewChild('votingTable') votingTable;

    modalTitle: string = this.i18nService.translate(
        'CONFERENCE.WIZARD.VOTING.MODAL.TITLE'
    );

    confirmModalTitle: string = '';
    confirmModalText: string = '';
    groupFieldsForSwitch: Map<string, FormFieldGroup> = new Map();
    groupFields: Map<string, FormFieldGroup> = new Map();
    groupFieldsForVote: Map<string, FormFieldGroup> = new Map();

    votingTypeOptions: Observable<ComboBox[]> = of([
        { key: VotingType.VOTAZIONE, value: this.i18nService.translate(
            'CONFERENCE.WIZARD.VOTING.OPTIONS_ENUM.'+VotingType.VOTAZIONE)  
        },
        { key: VotingType.CALENDARIZZAZIONE , value: this.i18nService.translate(
            'CONFERENCE.WIZARD.VOTING.OPTIONS_ENUM.'+VotingType.CALENDARIZZAZIONE)  
        },
        { key: VotingType.RILEVAZIONE_PRESENZE, value: this.i18nService.translate(
            'CONFERENCE.WIZARD.VOTING.OPTIONS_ENUM.'+VotingType.RILEVAZIONE_PRESENZE)  
        },
    ]);

    votingStateOptions: Observable<ComboBox[]> = of([
        { key: VotingState.PREPARAZIONE, value: this.i18nService.translate(
            'CONFERENCE.WIZARD.VOTING.OPTIONS_ENUM.'+VotingState.PREPARAZIONE)  
        },
        { key: VotingState.IN_CORSO, value: this.i18nService.translate(
            'CONFERENCE.WIZARD.VOTING.OPTIONS_ENUM.'+VotingState.IN_CORSO)  
        },
        { key: VotingState.ESITO_IMPOSTATO, value: this.i18nService.translate(
            'CONFERENCE.WIZARD.VOTING.OPTIONS_ENUM.'+VotingState.ESITO_IMPOSTATO)  
        },
        { key: VotingState.TERMINATA, value: this.i18nService.translate(
            'CONFERENCE.WIZARD.VOTING.OPTIONS_ENUM.'+VotingState.TERMINATA)  
        },
    ]);

    votingRuleOptions: Observable<ComboBox[]> = of([
        { key: VotingRule.EVENTO, value: this.i18nService.translate(
            'CONFERENCE.WIZARD.VOTING.OPTIONS_ENUM.'+VotingRule.EVENTO)  
        },
        { key: VotingRule.VALUTAZIONE, value: this.i18nService.translate(
            'CONFERENCE.WIZARD.VOTING.OPTIONS_ENUM.'+VotingRule.VALUTAZIONE)  
        }
    ]);

    votingResultOptions: Observable<ComboBox[]> = of([
        { key: VotingResult.APPROVATA, value: this.i18nService.translate(
            'CONFERENCE.WIZARD.VOTING.OPTIONS_ENUM.'+VotingResult.APPROVATA)  
        },
        { key: VotingResult.NON_APPROVATA, value: this.i18nService.translate(
            'CONFERENCE.WIZARD.VOTING.OPTIONS_ENUM.'+VotingResult.NON_APPROVATA)  
        },
    ]);

    addModalButtons: ActionItem[] = [
        new ActionItem(
            'CONFERENCE.WIZARD.VOTING.MODAL.CANCEL_BUTTON',
            (action: ActionItem) => {
                this.addModal.close();
            }
        ),
        new ActionItem(
            'CONFERENCE.WIZARD.VOTING.MODAL.OK_BUTTON',
            (action: ActionItem) => {}
        )
    ];

    detail: string = this.i18nService.translate(
        'CONFERENCE.WIZARD.VOTING.MODAL.FORM.VOTE.LIST.DETAIL'
    );

    footerTextSubmitBtn = 'CONFERENCE.WIZARD.VOTING.MODAL.SAVE';
    footerButtons = true;

    readonlyModal = false;

    tmpVotes: Vote[] = [];
    tmpVoting: Voting;
    searchQuery: boolean;

    createMode: boolean = true;
 
    saveFn: (voting: Voting) => Observable<WrapperPostPutData>;
    saveCompleteFn: (response: WrapperPostPutData) => void;
    saveErrorFn: (error: ErrorMessage) => void;
    extractDataToSubmit: (form: FormGroup) => Voting;

    constructor(
        private conferenceStoreService: ConferenceStoreService,
        private i18nService: I18nService,
        private loaderService: LoaderService,
        private votingService: VotingService,
        private messageToastr: MessageToastrService
    ) {
        super();
        this._createSwitchFilterInput();
    }

    ngOnInit() {}

    get conferenceId(): string {
        return this.conferenceStoreService.conference.id;
    }

    private _createSwitchFilterInput(): void {
        this.groupFieldsForSwitch = new Map();
        this.groupFieldsForSwitch.set('switchPanel', {
            panel: false,
            panelHead: null,
            fields: new Map().set('switchVotingVisibility', {
                type: 'switch',
                label: 'CONFERENCE.WIZARD.VOTING.SWITCH_LABEL',
                control: new FormControl({
                    value: false,
                    disabled: false
                }),
                valueChange: (val: boolean): void => {
                    this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
                    this.searchQuery = val;
                },
                size: '12|6|6'
            })
        });
    }

    private _updateVotingFields(voting: Voting){

        let isEvaluating = false;
        //set default rule based on voting type
        switch (voting.votingType.key){
            case  VotingType.VOTAZIONE : 
                voting.votingRule.key = VotingRule.VALUTAZIONE;
                this.votingRuleOptions = of([
                    { key: VotingRule.VALUTAZIONE, value: this.i18nService.translate(
                        'CONFERENCE.WIZARD.VOTING.OPTIONS_ENUM.'+VotingRule.VALUTAZIONE)  
                    }
                ])
            break;
            case  VotingType.CALENDARIZZAZIONE : 
                voting.votingRule.key = VotingRule.VALUTAZIONE;
                this.votingRuleOptions = of([
                    { key: VotingRule.VALUTAZIONE, value: this.i18nService.translate(
                        'CONFERENCE.WIZARD.VOTING.OPTIONS_ENUM.'+VotingRule.VALUTAZIONE)  
                    }
                ])
            break;
            case  VotingType.RILEVAZIONE_PRESENZE : 
                voting.votingRule.key = VotingRule.EVENTO;
                this.votingRuleOptions = of([
                    { key: VotingRule.EVENTO, value: this.i18nService.translate(
                        'CONFERENCE.WIZARD.VOTING.OPTIONS_ENUM.'+VotingRule.EVENTO)  
                    }
                ])
            break;
        }

        //on evaluating
        if(voting.votingState.key === VotingState.TERMINATA){
            isEvaluating = true;
            //voting.votingState.key = VotingState.ESITO_IMPOSTATO;
            this.votingStateOptions = of([
                { key: VotingState.ESITO_IMPOSTATO, value: this.i18nService.translate(
                    'CONFERENCE.WIZARD.VOTING.OPTIONS_ENUM.'+VotingState.ESITO_IMPOSTATO)  
                }
            ])
        }

        let fields = new Map();
                
        fields.set('votingType', {
                        type: 'select',
                        label:
                            'CONFERENCE.WIZARD.VOTING.MODAL.FORM.VOTING_TYPE',
                        control: new FormControl(
                            {
                                value: voting.votingType,
                                disabled: this.readonlyModal || voting.votingState.key !== VotingState.PREPARAZIONE
                            },
                            [Validators.required]
                        ),
                        valueChange: (val: ComboBox<string>): void => {
                            voting.votingType = val;
                            this._updateVotingFields(voting);
                        },
                        options: this.votingTypeOptions,
                        size: '12|6|6'
        });

        fields.set('votingRule', {
                        type: 'select',
                        label:
                            'CONFERENCE.WIZARD.VOTING.MODAL.FORM.VOTING_RULE',
                        control: new FormControl(
                            {
                                value: voting.votingRule,
                                disabled: this.readonlyModal || voting.votingState.key !== VotingState.PREPARAZIONE
                            },
                            [Validators.required]
                        ),
                        options: this.votingRuleOptions,
                        size: '12|6|6'
                    });

                    fields.set('subject', {
                        type: 'text',
                        label: 'CONFERENCE.WIZARD.VOTING.MODAL.FORM.SUBJECT',
                        control: new FormControl(
                            {
                                value: voting.subject,
                                disabled: voting.votingState.key !== VotingState.PREPARAZIONE,
                            },
                            [Validators.required]
                        ),
                        size: '12|12|12'
                    });

        fields.set('endVotingDate', {
                        type: 'date',
                        label:
                            'CONFERENCE.WIZARD.VOTING.MODAL.FORM.END_VOTING_DATE',
                        control: new FormControl(
                            {
                                value: voting.endVotingDate,
                                disabled: this.readonlyModal || voting.votingState.key !== VotingState.PREPARAZIONE
                            },
                            [Validators.required]
                        ),
                        options: of(),
                        size: '12|6|6'
                    });

        if(isEvaluating){
            fields.set('votingState', {
                type: 'select',
                label:
                    'CONFERENCE.WIZARD.VOTING.MODAL.FORM.VOTING_STATE',
                control: new FormControl(
                    {
                        value: { key: VotingState.ESITO_IMPOSTATO},
                        disabled: this.readonlyModal
                    },
                    [Validators.required]
                ),
                options: this.votingStateOptions,
                size: '12|6|6'
            });
        }
        
        

                    if(voting.votingType.key === VotingType.CALENDARIZZAZIONE){
                        fields.set('votingDate', {
                            type: 'date',
                            label:
                                'CONFERENCE.WIZARD.VOTING.MODAL.FORM.VOTING_DATE',
                            control: new FormControl(
                                {
                                    value: voting.votingDate,
                                    disabled: this.readonlyModal || ( voting.votingState.key !== VotingState.PREPARAZIONE && !isEvaluating)
                                },
                                [Validators.required]
                            ),
                            options: of(),
                            size: '12|6|6'
                        });
                    }

                    if(isEvaluating){
                        fields.set('votingResult',
                        {
                            type: 'select',
                            label: 'CONFERENCE.WIZARD.VOTING.MODAL.FORM.VOTING_RESULT',
                            control: new FormControl(
                                {
                                    value: voting.votingResult,
                                    disabled: this.readonlyModal,
                                },
                                [Validators.required]
                            ),
                            size: '12|6|6',
                            options: this.votingResultOptions
                        });
                    }
                    

        this.groupFields.set('voting', {
            panel: true,
            accordion: true,
            panelHead: 'CONFERENCE.WIZARD.VOTING.MODAL.FORM.TITLE',
            fields: fields
        })
        
        this.groupFields = new Map(this.groupFields);
    }

    private _createGroupFields(voting: Voting = new Voting()): void {
        this._initContext(voting);
        this._initCallback();
        
        this.groupFields = new Map();
        
        this.groupFields.set('voting', {
            panel: true,
            accordion: true,
            panelHead: 'CONFERENCE.WIZARD.VOTING.MODAL.FORM.TITLE',
            //fields: fields
        })

        let title = (voting.votingType.key === VotingType.VOTAZIONE)? 'CONFERENCE.WIZARD.VOTING.MODAL.FORM.VOTE.TITLE' : (voting.votingType.key === VotingType.CALENDARIZZAZIONE)? 'CONFERENCE.WIZARD.VOTING.MODAL.FORM.VOTE.TITLE_SCHEDULING':'CONFERENCE.WIZARD.VOTING.MODAL.FORM.VOTE.TITLE_PRESENCE';
        if(voting.votingState.key !== VotingState.PREPARAZIONE)    
            this.groupFields.set('vote', {
                panel: true,
                panelHead: title,
                oneToMany: true,
                accordion: true,
                detailLookup: this.detailLookup,
                model: Vote,
                readonly: true,
                listStructure: this.defineVoteTableStructure(voting.votingType.key),
                listTitle: title,
                emptyTextList:
                    'CONFERENCE.WIZARD.VOTING.MODAL.FORM.VOTE.EMPTY_TEXT_LIST',
                listMany: voting.vote,
                fields: new Map()
            });

            this._updateVotingFields(voting);

    }

    private _initContext(voting: Voting): void {

        if(!voting.vote.length){
            this.tmpVotes = voting.vote;
        }else{
            this.tmpVotes = [];
        }
       
    }

    defineVoteTableStructure(votingType: string): AbstractTableField[] {
        const voteTableStructure: AbstractTableField[] = [];

        voteTableStructure.push(
            new TableField(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.VOTING.MODAL.FORM.VOTE.LIST.PARTICIPANT'
                ),
                'NORMAL',
                'participant.value',
                true
            )
        );
        
        if(votingType === VotingType.VOTAZIONE){
            voteTableStructure.push(
                new TemplateField(
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.VOTING.MODAL.FORM.VOTE.LIST.VOTE'
                    ),
                    this.voteTemplateVoting
                )
            );
        }else if(votingType === VotingType.CALENDARIZZAZIONE){
            voteTableStructure.push(
                new TemplateField(
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.VOTING.MODAL.FORM.VOTE.LIST.VOTE'
                    ),
                    this.voteTemplateCalendar
                )
            );
        }
        
        voteTableStructure.push(
            new TemplateField(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.VOTING.MODAL.FORM.VOTE.LIST.DATE'
                ),
                this.dateTemplate
            )
        );

        if(votingType === VotingType.VOTAZIONE){
            voteTableStructure.push(
                new TemplateField(
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.VOTING.MODAL.FORM.VOTE.LIST.DETAIL'
                    ),
                    this.detailLookup
                )
            );
        }
        

        voteTableStructure.push(
            new ActionsField('SEARCH.RESULT_TABLE.ACTIONS.TITLE', null, () => {
                return true;
            })
        );

        return voteTableStructure;
    }

    private _initCallback(): void {
        this.saveFn = this._saveFn.bind(this);
        this.saveCompleteFn = this._saveCompleteFn.bind(this);
        this.saveErrorFn = this._saveErrorFn.bind(this);
    }

    private _saveFn(data: Voting): Observable<WrapperPostPutData> {
        if (this.createMode) {
            return this.votingService.addVoting(this.conferenceId, data).pipe(
                catchError((response: HttpResponse<WrapperResponse>) => {
                    return this._saveErrorFn(response);
                })
            )

        } else {
            return this.votingService
                .editVoting(this.conferenceId, this.tmpVoting.id, data)
                .pipe(
                    catchError((response: HttpResponse<WrapperResponse>) => {
                        return this._saveErrorFn(response);
                    })
                );
        }
    }

    private _saveCompleteFn(response: WrapperResponse): void {
        if (this.createMode) {
            this.votingTable.addVoting(response);   
 
            this.messageToastr.showMessage(
                response,
                'CONFERENCE.WIZARD.VOTING.TABLE.MODAL',
                'ADD'
            );
        } else {

            this.votingTable.editVoting(response); 

            this.messageToastr.showMessage(
                response,
                'CONFERENCE.WIZARD.VOTING.TABLE.MODAL',
                'EDIT'
            );
        }
        this.addModal.close();
    }

    private _saveErrorFn(
        error: HttpResponse<WrapperResponse>
    ): Observable<null> {
        this.addModal.close();
        if (this.createMode) {
            return this.messageToastr.showErrorMessage(
                error,
                'CONFERENCE.WIZARD.VOTING.TABLE.MODAL',
                'ADD'
            );
        } else {
            return this.messageToastr.showErrorMessage(
                error,
                'CONFERENCE.WIZARD.VOTING.TABLE.MODAL',
                'EDIT'
            );
        }
    }

    openVotingModal(voting: Voting): void {
        this.createMode = false;
           
        let modalTitle = (voting.votingState.key === VotingState.TERMINATA)? 'CONFERENCE.WIZARD.VOTING.MODAL.EVALUATE_TITLE': 'CONFERENCE.WIZARD.VOTING.MODAL.EDIT_TITLE'
        this.readonlyModal = false;
        if(voting.votingState.key === VotingState.IN_CORSO || voting.votingState.key === VotingState.ESITO_IMPOSTATO){
            this.readonlyModal = true;
            modalTitle = 'CONFERENCE.WIZARD.VOTING.MODAL.SHOW_TITLE';
        }

        this.modalTitle = this.i18nService.translate(
            `${this.i18nService.translate( modalTitle
                 )}: ${voting.id}`
        );

        this._createGroupFields(voting);
         
        this.tmpVoting = new Voting(voting);
        this.addModal.open();
    }

    addNewVoting(): void {
        this.createMode = true;
        this.modalTitle = this.i18nService.translate(
            'CONFERENCE.WIZARD.VOTING.MODAL.TITLE'
        );

        this._createGroupFields();
        this.addModal.open();
    }

    startResearch(query: boolean) {
        this.searchQuery = query;
    }

    isReadonly(): boolean {
        return false;
    }

    readonlyMode(): boolean {
        return this.conferenceStoreService.conference.state.key === ConferenceState.CLOSED;
    }
}
