import {
    Component,
    OnInit,
    Input,
    Output,
    EventEmitter,
    ViewChild
} from '@angular/core';

import { takeUntil } from 'rxjs/operators';

import {
    AbstractTableField,
    ActionItem,
    TableField,
    TemplateField
} from '@eng-ds/ng-toolkit';

import { I18nService, LoggerService } from '@eng-ds/ng-core';

import { ConferenceRole, AutoUnsubscribe } from '@common';

import { ActionForm, UtilityService } from '@app/core';

import {
    Participant,
    ConferenceStoreService
} from '@features/private/conference/core';

@Component({
    selector: 'app-participants-table',
    templateUrl: './participants-table.component.html',
    styleUrls: ['./participants-table.component.scss']
})
export class ParticipantsTableComponent extends AutoUnsubscribe
    implements OnInit {
    @Input('listParticipants') listParticipants :  Participant[] = [];
    @Input('action') action: ActionForm;

    @Output('view') view = new EventEmitter<Participant>();
    @Output('edit') edit = new EventEmitter<Participant>();
    @Output('openModalForDelete') openModalForDelete = new EventEmitter<
        Participant
    >();

    @ViewChild('actionTemplate') actionTemplate;
    @ViewChild('authorityTemplate') authorityTemplate;
    @ViewChild('roleTemplate') roleTemplate;
    @ViewChild('determinationExpressedTemplate') determinationExpressedTemplate;
    @ViewChild('integrationRequiredTemplate') integrationRequiredTemplate;

    tableStructure: AbstractTableField[] = [];

    viewParticipant: ActionItem;
    editParticipant: ActionItem;
    deleteParticipant: ActionItem;

    selectAction = new ActionItem(
        'BUTTON.SELECT',
        (a, item: Participant) => {
            item.toggleSelection();
        },
        'square-o'
    )

    unselectAction = new ActionItem(
        'BUTTON.UNSELECT',
        (a, item: Participant) => {
            item.toggleSelection();
        },
        'check'
    )

    constructor(
        private i18nService: I18nService,
        public utilityService: UtilityService,
        private loggerService: LoggerService,
        private conferenceStoreService: ConferenceStoreService
    ) {
        super();
    }

    ngOnInit(): void {
        this.tableStructure = this.defineTableStructure(this.tableStructure);
    }

    defineTableStructure(
        structure: AbstractTableField[]
    ): AbstractTableField[] {
        this.viewParticipant = new ActionItem(
            'BUTTON.VIEW',

            (a, item: Participant) => {
                this.view.emit(item);
            },
            'eye'
        );
        this.editParticipant = new ActionItem(
            'BUTTON.UPDATE',

            (a, item: Participant) => {
                this.edit.emit(item);
            },
            'pencil'
        );
        this.deleteParticipant = new ActionItem(
            'BUTTON.DELETE',

            (a, item: Participant) => {
                this.openModalForDelete.emit(item);
            },
            'trash'
        );
        structure.push(
            new TemplateField(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.PARTICIPANTS.TABLE.ACTOR'
                ),
                this.authorityTemplate
            )
        );
        structure.push(
            new TemplateField(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.PARTICIPANTS.TABLE.ROLE'
                ),
                this.roleTemplate
            )
        );
        // structure.push(
        //     new TableField(
        //         this.i18nService.translate(
        //             'CONFERENCE.WIZARD.PARTICIPANTS.TABLE.PEC'
        //         ),
        //         'NORMAL',
        //         'pec',
        //         false
        //     )
        // );
        // structure.push(
        //     new TableField(
        //         this.i18nService.translate(
        //             'CONFERENCE.WIZARD.PARTICIPANTS.TABLE.FISCALCODE'
        //         ),
        //         'NORMAL',
        //         'fiscalCode',
        //         false
        //     )
        // );
        structure.push(
            new TableField(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.PARTICIPANTS.TABLE.DESCRIPTION'
                ),
                'NORMAL',
                'description',
                false
            )
        );
        if (this.conferenceStoreService.conference.isIndictionState()) {
            this.conferenceStoreService.userProfile$
                .pipe(takeUntil(this.destroy$))
                .subscribe(user => {
                    if (
                        !user ||
                        user.profile.key === ConferenceRole.CONFERENCE_MANAGER
                    ) {
                        structure.push(
                            new TemplateField(
                                this.i18nService.translate(
                                    'CONFERENCE.WIZARD.PARTICIPANTS.TABLE.DETERMINATION_EXPRESSED'
                                ),
                                this.determinationExpressedTemplate
                            )
                        );
                        structure.push(
                            new TemplateField(
                                this.i18nService.translate(
                                    'CONFERENCE.WIZARD.PARTICIPANTS.TABLE.INTEGRATION_REQUIRED'
                                ),
                                this.integrationRequiredTemplate
                            )
                        );
                    }
                });
        }

        structure.push(
            new TemplateField(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.PARTICIPANTS.TABLE.ACTIONS'
                ),
                this.actionTemplate
            )
        );

        return structure;
    }

    isReadonly(): boolean {
        return this.action === ActionForm.READONLY;
    }
}
