import { Injectable } from '@angular/core';
import { Validators, FormControl } from '@angular/forms';
import { AbstractTableField, ActionItem, TableField } from '@eng-ds/ng-toolkit';

import { forkJoin, Subscription, Observable, Subscriber } from 'rxjs';

import { ErrorLabelConstants } from '@eng-ds/ng-toolkit';
import { LoggerService } from '@eng-ds/ng-core';
import { EventAttach } from '@features/private/conference/core';
import { of } from 'rxjs';


import {
    ComboBox,
    SectionLoading,
    LoaderService,
    FormFieldGroup,
    FormField,
    BaseFile,
    FileService,
    WrapperPostPutData,
    AlertWithLinkComponent
} from '@common';

import {
    EventType,
    UtilityService,
    ActionForm,
    UserPortalService,
    ConferenceType 
} from '@app/core';

import {
    Event,
    IntegrationRequest,
    IntegrationClosed,
    IntegrationOnlyOneRequest,
    IntegrationSend,
    ConferenceMemo,
    ConferenceMemoInternal,
    ConferenceClosing,
    OpinionExpress,
    FinalResolution,
    GenericCommunication,
    IntegrationRegistered,
    EditDate
} from '@features/private/conference/core';

import { ConferenceStoreService } from '@app/features/private/conference/core';

import {
    integrationRequest,
    genericCommunication,
    conferenceCreated,
    conferenceIndiction,
    integrationClosed,
    integrationOnlyOneRequest,
    integrationSendRequest,
    opinionExpress,
    finalResolution,
    conferenceMemo,
    conferenceMemoInternal,
    conferenceClosing,
    integrationRegistered,
    editDate
} from './events-fields';
import { map } from 'rxjs/operators';
import { element } from '@angular/core/src/render3/instructions';
import { DateUpdate } from '@app/features/private/conference/core/models/definition/update-date.model';
import { DateModel } from '@eng-ds/ng-toolkit';
import { DateType } from '@app/core/enums/date-type.enum';
import { controlNameBinding } from '@angular/forms/src/directives/reactive_directives/form_control_name';

@Injectable()
export class EventGroupFieldsService {
    private groupsFields: Map<EventType, Function> = new Map();
    formGroupsFields: Map<EventType, Map<string, FormFieldGroup>> = new Map();

    subscription: Subscription;
    fileDownloadSubscription: Subscription;

    _attachmentFlag: boolean = false;
    _tmpArrayFiles: File;
    _currentEvent: IntegrationRequest
                    | IntegrationClosed
                    | IntegrationOnlyOneRequest
                    | IntegrationSend
                    | ConferenceMemo
                    | ConferenceMemoInternal
                    | ConferenceClosing
                    | OpinionExpress
                    | FinalResolution
                    | GenericCommunication
                    | IntegrationRegistered
                    | EditDate;
    saveAttachFile: (data: EventAttach) => Observable<void | WrapperPostPutData>;

    constructor(
        public loggerService: LoggerService,
        public utilityService: UtilityService,
        private userService: UserPortalService,
        public conferenceStore: ConferenceStoreService,
        public fileService: FileService,
        public loaderService: LoaderService
    ) {
        this._initEventsMap();

        this.saveAttachFile = this.savingAttach.bind(this);
    }

    get conferenceType(): string {
        return this.conferenceStore.conference.definition.instance
            .conferenceType.key;
    }

    get participants(): ComboBox[] {
        return this.conferenceStore.conference.getParticipantsComboBox();
    }

    get getDateTypes(): Observable<ComboBox[]> {
        return this.utilityService.getDateTypes();
    }

    getDateValueByType(dateTypeCode: DateType): DateModel | string{
  
        let dateValue = null;
        switch (dateTypeCode) {
            case DateType.DATA_TERMINE: 
                dateValue = this.conferenceStore.conference.definition.instance.expirationDate; 
                break;        
            case DateType.DATA_TERMINE_ESPRESSIONE_PARERI: 
                dateValue = this.conferenceStore.conference.definition.instance.endOpinionDate; 
                break;    
            case DateType.DATA_TERMINE_RICHIESTA_INTEGRAZIONI_CONFERENZA: 
                dateValue = this.conferenceStore.conference.definition.instance.endIntegrationDate;  
                break; 
            case DateType.DATA_PRIMA_SESSIONE_SIMULATA: 
                dateValue = this.conferenceStore.conference.definition.instance.firstSessionDate;
                break;
        }

        this.conferenceStore.conference.definition.instance.dateUpdateList.forEach( dateUpdate => {
            if(dateUpdate.code === dateTypeCode)
                dateValue = dateUpdate.dateNew
        })

        return dateValue;
    }

    private _initEventsMap(): void {
        this.groupsFields.set(
            EventType.CONFERENCE_CREATED,
            conferenceCreated.bind(this)
        );

        this.groupsFields.set(
            EventType.CONFERENCE_INDICTION,
            conferenceIndiction.bind(this)
        );

        this.groupsFields.set(
            EventType.INTEGRATION_REQUEST,
            integrationRequest.bind(this)
        );

        this.groupsFields.set(
            EventType.INTEGRATION_CLOSED,
            integrationClosed.bind(this)
        );

        this.groupsFields.set(
            EventType.INTEGRATION_ONLY_ONE_REQUEST,
            integrationOnlyOneRequest.bind(this)
        );

        this.groupsFields.set(
            EventType.INTEGRATION_SEND,
            integrationSendRequest.bind(this)
        );

        this.groupsFields.set(
            EventType.CONFERENCE_MEMO,
            conferenceMemo.bind(this)
        );

        this.groupsFields.set(
            EventType.CONFERENCE_MEMO_INTERNAL,
            conferenceMemoInternal.bind(this)
        );

        this.groupsFields.set(
            EventType.CONFERENCE_CLOSING,
            conferenceClosing.bind(this)
        );

        this.groupsFields.set(
            EventType.OPINION_EXPRESS,
            opinionExpress.bind(this)
        );

        this.groupsFields.set(
            EventType.FINAL_RESOLUTION,
            finalResolution.bind(this)
        );

        this.groupsFields.set(
            EventType.GENERIC_COMUNICATION,
            genericCommunication.bind(this)
        );

        this.groupsFields.set(
            EventType.INTEGRATION_REGISTERED,
            integrationRegistered.bind(this)
        );

        this.groupsFields.set(
            EventType.EDIT_DATE,
            editDate.bind(this)
        );

    }

    asyncPopulateDefaultIntegration(
        gf: Map<string, FormFieldGroup>,
        eventType: EventType
    ): void {
        this.subscription = forkJoin([
            this.utilityService.getSubjectsEvent(eventType),
            this.userService.getConferenceParticipant(
                this.conferenceStore.conference.id
            )
        ]).subscribe((result: ComboBox[]) => {
            const subjectControl = gf.get('head').fields.get('subject').control;
            subjectControl.setValue(result[0].value);
            if (subjectControl.disabled) {
                subjectControl.disable();
            }

            const administrationField = gf
                .get('head')
                .fields.get('administration');

            if (administrationField) {
                administrationField.control.setValue(result[1].value);
                if (administrationField.control.disabled) {
                    administrationField.control.disable();
                }
            }
        });
    }

    fileDownload(file: BaseFile): void {
        if (this.fileDownloadSubscription instanceof Subscription) {
            this.fileDownloadSubscription.unsubscribe();
        }

        this.loaderService.showLoading(SectionLoading.EVENTS_MODAL);
        this.fileDownloadSubscription = this.fileService
            .download(file)
            .subscribe(_ => {
                this.loaderService.hideLoading(SectionLoading.EVENTS_MODAL);
            });
    }

    get<T>(
        eventType: EventType,
        mode: ActionForm,
        model?: T
    ): Map<string, FormFieldGroup> {
        return this.groupsFields.get(eventType)(mode, model);
    }

    has(eventType: EventType): boolean {
        return this.groupsFields.has(eventType);
    }

    resetContext(): void {
        if (this.subscription) {
            this.subscription.unsubscribe();
        }
    }

    fnStruct(): AbstractTableField[] {
        const attachTableStructure: AbstractTableField[] = [];

        attachTableStructure.push(
            new TableField('Nome file allegato',
                'NORMAL',
                'attachment.name',
                false
            ));

        return attachTableStructure;
    }

    savingAttach(attach: EventAttach): Observable<void | WrapperPostPutData> {
        let obs: Observable<void | WrapperPostPutData> = null;
        attach.attachment = this._tmpArrayFiles;
        if (this._attachmentFlag) {
            this._attachmentFlag = false;
        }
        this._currentEvent.attaches.push(new EventAttach(attach));
		obs = new Observable(
			(observer: Subscriber<void | WrapperPostPutData>): void => {
				observer.next();
				observer.complete();
			}
		);
        return obs;
    }

    createPanelDocument(gf: Map<string, FormFieldGroup>, model: Partial<| IntegrationRequest
        | IntegrationOnlyOneRequest
        | IntegrationSend
        | ConferenceMemo
        | ConferenceMemoInternal
        | OpinionExpress
        | FinalResolution
        | GenericCommunication
        | IntegrationRegistered
        | EditDate>,
        eventType: EventType) :void {

            gf.set('docPrinc', {
                panel: true,
                panelHead: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.MAIN_DOCUMENT',
                fields: new Map<string, FormField>()
                .set('file', {
                    type: 'file',
                    required: false,
                    control: new FormControl(),
                    onSelect: file => {
                        let fileSize: number = file.size;
                        this.loggerService.log('onSelect', file);
                        if(fileSize  > 20000 * 1000){
                            let element:HTMLElement = document.getElementsByClassName('innerx')[0] as HTMLElement;
                            element.click();
                            alert('File troppo grande! Inserire file di massimo 20 mb');
                            gf.get('docPrinc')
                            .fields.get('file')
                            .control.setValue(null);
                        }else {
                            gf.get('docPrinc')
                            .fields.get('file')
                            .control.setValue(file);
                        }                          
                    },
                    disabled: true,
                    hideSubmitBtn: true,
                    size: '12|6|6'
                })
            });
             // xmfAddedAttaches
             gf.set('attach', {
                panel: true,
                panelHead: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.ATTACHMENTS_LIST',
                oneToMany: true,
                accordion: false,
                model: EventAttach,
                saveFn: this.saveAttachFile,
                listStructure: this.fnStruct(),
                listTitle: 'CONFERENCE.WIZARD.PARTICIPANTS.MODAL.USER.LIST_TITLE',
                emptyTextList: '',
                listMany: model.attaches,
                readonly: false,
                fields: new Map<string, FormField>()
                    .set('attachment', {
                        type: 'file',
                        required: true,
                        control: new FormControl(),
                        onSelect: file => {
                            this._attachmentFlag = true;
                            this._tmpArrayFiles = file;                            
                        }   ,
                    hideSubmitBtn: true,
                    size: '12|6|6'
                })
            });
        
        switch (eventType) {
            case EventType.GENERIC_COMUNICATION:
            case EventType.EDIT_DATE:

                gf.set('document', {
                    panel: true,
                    panelHead: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.PROTOCOL_NUMBER_DATE',
                    fields: new Map<string, FormField>()
                        .set('protocolNumber', {
                            type: 'text',
                            label: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.PROTOCOL_NUMBER',
                            control: new FormControl(
                                {
                                    value: model.document.protocolNumber,
                                    disabled: !(this.conferenceType == ConferenceType.BROADBAND || this.conferenceType == ConferenceType.PRELIMINARY)
                                },
                                []
                            ),
                            size: '12|6|6'
                        })
                        .set('protocolDate', {
                            type: 'date',
                            label: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.PROTOCOL_DATE',
                            control: new FormControl(
                                {
                                    value: model.document.protocolDate,
                                    disabled: !(this.conferenceType == ConferenceType.BROADBAND || this.conferenceType == ConferenceType.PRELIMINARY)
                                },
                                []
                            ),
                            valueChange: evt => {
                                gf.get('document')
                                    .fields.get('protocolDate')
                                    .control.setErrors(null);
                            },
                            size: '12|6|6'
                        })
                });
                break;
            case EventType.FINAL_RESOLUTION:
                gf.set('document', {
                    panel: true,
                    panelHead: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.PROTOCOL_NUMBER_DATE',
                    fields: new Map<string, FormField>()
                    .set('category', {
                        type: 'select',
                        label:
                            'CONFERENCE.WIZARD.DOCUMENTATION.ADDITIONAL.FILE.CATEGORY',
                        control: new FormControl({
                            value: model.document.category,
                            disabled: false
                        }),
                        size: '12|6|4',
                        options: this.utilityService.getCategoriesFile(
                            this.conferenceType,
                            eventType
                        )
                    })
                    .set('protocolNumber', {
                        type: 'text',
                        label:
                            'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.RESOLUTION_NUMBER',
                        control: new FormControl(
                            {
                                value: model.document.protocolNumber,
                                disabled: !(this.conferenceType == ConferenceType.BROADBAND || this.conferenceType == ConferenceType.PRELIMINARY)
                            },
                          //  [Validators.required]
                        ),
                        errorLabels: [ErrorLabelConstants.REQUIRED],
                        size: '12|6|4'
                    })
                    .set('protocolDate', {
                        type: 'date',
                        label:
                            'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.RESOLUTION_DATE',
                        control: new FormControl(
                            {
                                value: model.document.protocolDate,
                                disabled: !(this.conferenceType == ConferenceType.BROADBAND || this.conferenceType == ConferenceType.PRELIMINARY)
                            },
                            //[Validators.required]
                        ),
                        errorLabels: [ErrorLabelConstants.REQUIRED],
                        size: '12|6|4'
                    })
                });
                break;
            case EventType.INTEGRATION_REQUEST:
            case EventType.INTEGRATION_ONLY_ONE_REQUEST:

                gf.set('document', {
                    panel: true,
                    panelHead: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.PROTOCOL_NUMBER_DATE',
                    fields: new Map<string, FormField>()
                    .set('category', {
                        type: 'select',
                        label:
                            'CONFERENCE.WIZARD.DOCUMENTATION.ADDITIONAL.FILE.CATEGORY',
                        control: new FormControl({
                            value: model.document.category,
                            disabled: false
                        }),
                        size: '12|6|4',
                        options: this.utilityService.getCategoriesFile(
                            this.conferenceType,
                            eventType
                        )
                    })
                    .set('protocolNumber', {
                        type: 'text',
                        label:
                            'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.PROTOCOL_NUMBER',
                        control: new FormControl(
                            {
                                value: model.document.protocolNumber,
                                disabled: !(this.conferenceType == ConferenceType.BROADBAND || this.conferenceType == ConferenceType.PRELIMINARY)
                            },
                            [Validators.required]
                        ),
                        errorLabels: [ErrorLabelConstants.REQUIRED],
                        size: '12|6|4'
                    })
                    .set('protocolDate', {
                        type: 'date',
                        label:
                            'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.PROTOCOL_DATE',
                        control: new FormControl(
                            {
                                value: model.document.protocolDate,
                                disabled: !(this.conferenceType == ConferenceType.BROADBAND || this.conferenceType == ConferenceType.PRELIMINARY)
                            },
                            [Validators.required]
                        ),
                        errorLabels: [ErrorLabelConstants.REQUIRED],
                        size: '12|6|4'
                    })
                });
                break;
            case EventType.INTEGRATION_SEND:
                
                gf.set('document', {
                panel: true,
                panelHead: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.PROTOCOL_NUMBER_DATE',
                fields: new Map<string, FormField>()
                    .set('category', {
                        type: 'select',
                        label:
                            'CONFERENCE.WIZARD.DOCUMENTATION.ADDITIONAL.FILE.CATEGORY',
                        control: new FormControl({
                            value: model.document.category,
                            disabled: false
                        }),
                        size: '12|6|4',
                        options: this.utilityService.getCategoriesFile(
                            this.conferenceType,
                            EventType.INTEGRATION_SEND
                        )
                    })
                    .set('protocolNumber', {
                        type: 'text',
                        label:
                            'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.PROTOCOL_NUMBER',
                        control: new FormControl(
                            {
                                value: model.document.protocolNumber,
                                disabled: !(this.conferenceType == ConferenceType.BROADBAND || this.conferenceType == ConferenceType.PRELIMINARY)
                            },
                            [Validators.required]
                        ),
                        errorLabels: [ErrorLabelConstants.REQUIRED],
                        size: '12|6|4'
                    })
                    .set('protocolDate', {
                        type: 'date',
                        label:
                            'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.PROTOCOL_DATE',
                        control: new FormControl(
                            {
                                value: model.document.protocolDate,
                                disabled: !(this.conferenceType == ConferenceType.BROADBAND || this.conferenceType == ConferenceType.PRELIMINARY)
                            },
                            [Validators.required]
                        ),
                        errorLabels: [ErrorLabelConstants.REQUIRED],
                        size: '12|6|4'
                    })
                    .set('visibility', {
                        type: 'select-two',
                        label:
                            'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.VISIBILITY',
                        control: new FormControl({
                            value: model.document.visibility,
                            disabled: false
                        }),
                        placeholder: 'COMMON.ALL',
                        size: '12|12|12',
                        options: of(this.participants)
                    })
                });
                break;                
            case EventType.CONFERENCE_MEMO_INTERNAL:
            case EventType.CONFERENCE_MEMO:

                gf.set('document', {
                    alert: {
                        component: AlertWithLinkComponent,
                        type: 'danger',
                        textAfterLink:
                            'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.ATTACHED_FILE_ALERT.AFTER_LINK',
                        textBeforeLink:
                            'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.ATTACHED_FILE_ALERT.BEFORE_LINK',
                        textLink:
                            'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.ATTACHED_FILE_ALERT.LINK',
                        //textTooLarge:
                         //   'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.ATTACHED_FILE_ALERT.SIZETOOLARGE_LINK',
                        linkClick: (() => {
                            gf.get('docPrinc').fields.get('file').disabled = false;

                            this.loaderService.showLoading(
                                SectionLoading.EVENTS_MODAL
                            );
                            if (
                                this.fileDownloadSubscription instanceof
                                Subscription
                            ) {
                                this.fileDownloadSubscription.unsubscribe();
                            }
                            this.fileDownloadSubscription = this.fileService
                                .downloadByApiName(
                                    'downloadMemoConferenceTemplate',
                                    {
                                        conferenceId: this.conferenceStore
                                            .conference.id
                                    },
                                    {
                                        eventType: model.type.key
                                    }
                                )
                                .subscribe(_ => {
                                    this.loaderService.hideLoading(
                                        SectionLoading.EVENTS_MODAL
                                    );
                                });
                        }).bind(this)
                    },
                    panel: true,
                    panelHead:
                        'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.ATTACHED_FILE',
                    fields: new Map<string, FormField>()
                        .set('category', {
                            type: 'select',
                            label:
                                'CONFERENCE.WIZARD.DOCUMENTATION.ADDITIONAL.FILE.CATEGORY',
                            control: new FormControl({
                                value: model.document.category,
                                disabled: false
                            }),
                            size: '12|6|6',
                            options: this.utilityService.getCategoriesFile(
                                this.conferenceType,
                                eventType
                            )
                        })
                        .set('protocolNumber', {
                            type: 'text',
                            label:
                                'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.PROTOCOL_NUMBER',
                            control: new FormControl({
                                value: model.document.protocolNumber,
                                disabled: !(this.conferenceType == ConferenceType.BROADBAND || this.conferenceType == ConferenceType.PRELIMINARY)
                            }),
                            size: '12|6|6'
                        })
                        .set('protocolDate', {
                            type: 'date',
                            label:
                                'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.PROTOCOL_DATE',
                            control: new FormControl({
                                value: model.document.protocolDate,
                                disabled: !(this.conferenceType == ConferenceType.BROADBAND || this.conferenceType == ConferenceType.PRELIMINARY)
                            }),
                            size: '12|6|6'
                        })
                });
                break;
            case EventType.OPINION_EXPRESS:
                gf.set('document', {
                    panel: true,
                    panelHead: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.PROTOCOL_NUMBER_DATE',
                    fields: new Map<string, FormField>()
                        .set('determinationType', {
                            type: 'select',
                            label:
                                'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.DETERMINATION_TYPE',
                            control: new FormControl({
                                value: model.document.determinationType,
                                disabled: false
                            }),
                            size: '12|6|6',
                            options: this.utilityService.getDeterminationType()
                        })
                        .set('category', {
                            type: 'select',
                            label:
                                'CONFERENCE.WIZARD.DOCUMENTATION.ADDITIONAL.FILE.CATEGORY',
                            control: new FormControl({
                                value: model.document.category,
                                disabled: false
                            }),
                            size: '12|6|6',
                            options: this.utilityService.getCategoriesFile(
                                this.conferenceType,
                                eventType
                            )
                        })
                        .set('protocolNumber', {
                            type: 'text',
                            label:
                                'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.PROTOCOL_NUMBER',
                            control: new FormControl(
                                {
                                    value: model.document.protocolNumber,
                                    disabled: !(this.conferenceType == ConferenceType.BROADBAND || this.conferenceType == ConferenceType.PRELIMINARY)
                                },
                                [Validators.required]
                            ),
                            errorLabels: [ErrorLabelConstants.REQUIRED],
                            size: '12|6|6'
                        })
                        .set('protocolDate', {
                            type: 'date',
                            label:
                                'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.PROTOCOL_DATE',
                            control: new FormControl(
                                {
                                    value: model.document.protocolDate,
                                    disabled: !(this.conferenceType == ConferenceType.BROADBAND || this.conferenceType == ConferenceType.PRELIMINARY)
                                },
                                [Validators.required]
                            ),
                            errorLabels: [ErrorLabelConstants.REQUIRED],
                            size: '12|6|6'
                        })
                });
                break;
            case EventType.INTEGRATION_REGISTERED:
                gf.set('document', {
                    panel: true,
                    panelHead: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.PROTOCOL_NUMBER_DATE',
                    fields: new Map<string, FormField>()
                        .set('protocolNumber', {
                            type: 'text',
                            label:
                                'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.PROTOCOL_NUMBER',
                            control: new FormControl(
                                {
                                    value: model.document.protocolNumber,
                                    disabled: !(this.conferenceType == ConferenceType.BROADBAND || this.conferenceType == ConferenceType.PRELIMINARY)
                                },
                                [Validators.required]
                            ),
                            errorLabels: [ErrorLabelConstants.REQUIRED],
                            size: '12|6|6'
                        })
                        .set('protocolDate', {
                            type: 'date',
                            label:
                                'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.PROTOCOL_DATE',
                            control: new FormControl(
                                {
                                    value: model.document.protocolDate,
                                    disabled: !(this.conferenceType == ConferenceType.BROADBAND || this.conferenceType == ConferenceType.PRELIMINARY)
                                },
                                [Validators.required]
                            ),
                            errorLabels: [ErrorLabelConstants.REQUIRED],
                            size: '12|6|6'
                        })
                });
                break;
            default:
                break;
        }
        
    
    }


}
