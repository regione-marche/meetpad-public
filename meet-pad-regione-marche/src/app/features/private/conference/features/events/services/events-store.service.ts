import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';

import { ToastrService } from 'ngx-toastr';

import { Observable, Subscription, Subject,of } from 'rxjs';
import { tap } from 'rxjs/operators';

import { ErrorMessage } from '@eng-ds/ng-toolkit';
import { I18nService } from '@eng-ds/ng-core';

import { EventAttach } from '@features/private/conference/core';
import { EventDocument } from '@features/private/conference/core';
import { environment } from '@env/environment';

import {
    LoaderService,
    WrapperPostPutData,
    SectionLoading,
    WrapperGetData,
    FormFieldGroup
} from '@common';

import { EventType, ActionForm, UtilityService } from '@app/core';

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
    EventsService,
    ConferenceStoreService
} from '@features/private/conference/core';

import { EventGroupFieldsService } from './group-fields/event-group-fields.service';
import { EditDate } from '../../../core/models/events/edit-date.model';
import { DateUpdate } from '../../../core/models/definition/update-date.model';
import { modelGroupProvider } from '@angular/forms/src/directives/ng_model_group';

@Injectable()
export class EventStoreService {
    currentEvent: Event;
    currentEventType: EventType;
    subscription: Subscription;
    eventsRefresh$: Subject<boolean>;

    constructor(
        private eventGroupFieldsService: EventGroupFieldsService,
        private eventsService: EventsService,
        private loaderService: LoaderService,
        private toastrService: ToastrService,
        public conferenceStoreService: ConferenceStoreService,
        public utilityService: UtilityService,
        public i18nService: I18nService,
    ) {
        this.eventsRefresh$ = new Subject<boolean>();
    }

    get loading(): Observable<boolean> {
        return this.loaderService.getLoading$(SectionLoading.EVENTS_MODAL);
    }

    getGroupFields(
        eventType: EventType,
        mode: ActionForm,
        model?: any
    ): Map<string, FormFieldGroup> {
        this.currentEventType = eventType;
        return this.eventGroupFieldsService.get(eventType, mode, model);
    }

    hasGroupFields(eventType: EventType): boolean {
        return this.eventGroupFieldsService.has(eventType);
    }

    getEventById(conferenceId: string, eventId: string): Observable<Event> {
        this.loaderService.showLoading(SectionLoading.EVENTS_MODAL);
        return this.eventsService
            .getById(conferenceId, eventId)
            .pipe(
                tap(_ =>
                    this.loaderService.hideLoading(
                        SectionLoading.EVENTS_MODAL,
                        800
                    )
                )
            );
    }

    fetchAllEvents() {
        const criteria = {};
        criteria['sort'] = 'id,asc';
        criteria['page'] = 1;
        criteria['size'] = 10000;

        return this.eventsService
            .get(this.conferenceStoreService.conference.id, criteria)
            .pipe(
                tap((results: WrapperGetData<Event>) => {
                    this.conferenceStoreService.conference.addEvents(
                        results.list
                    );
                })
            );
    }

    extractDataToSubmit(
        form: FormGroup
    ): Partial<
        | IntegrationRequest
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
        | EditDate
    > {
        const formRawValue = form.getRawValue();
        let ed: EventDocument = new EventDocument();
        const groupFields = this.eventGroupFieldsService.formGroupsFields.get(this.currentEventType);
        const attachmentFlag = this.eventGroupFieldsService._attachmentFlag;

        let attachments: Array<File> = [];
        if(groupFields) {
            //attachments = groupFields.get('attach').listMany;
            const listMany: EventAttach[] = groupFields.get('attach').listMany;
            for(var j=0;listMany && j < listMany.length; j++) {
                if(listMany[j].attachment) {
                    attachments.push(listMany[j].attachment);
                } 
            }

            ed.file = groupFields.get('docPrinc').fields.get("file").control.value;
            ed.protocolNumber = groupFields.get('document').fields.get("protocolNumber").control.value;
            ed.protocolDate = groupFields.get('document').fields.get("protocolDate").control.value;

            switch(this.currentEventType) {
                case EventType.INTEGRATION_REGISTERED:
                    ed.category = Object.assign(
                        {},
                            environment.defaultComboBox.conference.events.integrationRegistered
                            .document.category
                        );  
                case EventType.GENERIC_COMUNICATION:
                    ed.category = Object.assign(
                        {},
                            environment.defaultComboBox.conference.events.genericCommunication
                            .document.category
                        );  
                    break;
                case EventType.EDIT_DATE:
                    ed.category = Object.assign(
                        {},
                            environment.defaultComboBox.conference.events.editDate
                            .document.category
                        );  
                    break;
                case EventType.CONFERENCE_MEMO_INTERNAL:
                case EventType.CONFERENCE_MEMO:
                case EventType.FINAL_RESOLUTION:
                case EventType.INTEGRATION_REQUEST:
                case EventType.INTEGRATION_ONLY_ONE_REQUEST:
                    ed.category = groupFields.get('document').fields.get("category").control.value;
                    break;
                case EventType.INTEGRATION_SEND:
                        ed.category = groupFields.get('document').fields.get("category").control.value;
                        ed.visibility = groupFields.get('document').fields.get("visibility").control.value;                           
                        break;
                case EventType.OPINION_EXPRESS:
                    ed.category = groupFields.get('document').fields.get("category").control.value;
                    if (environment.customConfigurationConf.determinationTypeEventView) {
                        ed.determinationType = groupFields.get('document').fields.get("determinationType").control.value;   
                    }
                                            
                    break;
                default: 
                    break;
            }
                
        }


        return {
            type: { key: this.currentEventType, value: '' },
            notes:
                (formRawValue.additionalFields &&
                    formRawValue.additionalFields.notes) ||
                null,
            result:
                (formRawValue.additionalFields &&
                    formRawValue.additionalFields.result) ||
                null,
            body: formRawValue.head.body || null,
            document:  ed,
            determinationType: formRawValue.document && formRawValue.document.determinationType || null,
            attachedFiles: attachments,
            recipients: formRawValue.head.recipients || null,
            dateType: (formRawValue.head.dateType)? formRawValue.head.dateType.key : null,
            newDate: formRawValue.head.newDate || null,
            newDateTermine: formRawValue.head.newDateTermine  || null,
            newDateTermineEsprPareri: formRawValue.head.newDateTermineEsprPareri  || null,
            newDateTermineRichInteg: formRawValue.head.newDateTermineRichInteg  || null,
            newDatePrimaSessioneSimultanea: formRawValue.head.newDatePrimaSessioneSimultanea  || null,
            attachmentFlag: attachmentFlag,
        };
    }

    save(
        conferenceId: string,
        event:
            | IntegrationRequest
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
            | EditDate,
        enableProgressBar: boolean = false
    ): Observable<WrapperPostPutData> {
        this.currentEvent = event;
        this.loaderService.showLoading(SectionLoading.EVENTS_MODAL);
        return this.eventsService.create(event, conferenceId, enableProgressBar);
                
    }

    saveComplete(response: WrapperPostPutData): void {
        this.eventsRefresh$.next(true);
        this.loaderService.hideLoading(SectionLoading.EVENTS_MODAL);
        this.toastrService.info(
            this.i18nService.translate(
                'CONFERENCE.WIZARD.EVENTS.TOASTR.SUCCESS.TEXT'
            ),
            this.i18nService.translate(
                'CONFERENCE.WIZARD.EVENTS.TOASTR.SUCCESS.TITLE'
            )
        );
    }

    saveError(error: ErrorMessage): void {
        this.loaderService.showLoading(SectionLoading.EVENTS_MODAL);
        this.toastrService.error(
            this.i18nService.translate(
                'CONFERENCE.WIZARD.EVENTS.TOASTR.ERROR.TEXT'
            ),
            this.i18nService.translate(
                'CONFERENCE.WIZARD.EVENTS.TOASTR.ERROR.TITLE'
            )
        );
    }

    resetContext(): void {
        this.eventGroupFieldsService.resetContext();
        this._resetContext();
    }

    _resetContext(): void {
        this.currentEvent = undefined;
        if (this.subscription) {
            this.subscription.unsubscribe();
        }
    }

    needConfirmBeforeSave(eventType: string): boolean {
        return eventType === EventType.EDIT_DATE;
    }

    needModal(eventType: EventType): boolean {
        return eventType !== EventType.INTEGRATION_CLOSED;
    }

    processCreationEvent(eventType: EventType, conferenceId: string): void {
        if (eventType === EventType.INTEGRATION_CLOSED) {
            this.subscription = this.save(
                conferenceId,
                new IntegrationClosed()
            ).subscribe(
                (response: WrapperPostPutData) => {
                    this.saveComplete(response);
                },
                (error: any) => {
                    const errorMex = new ErrorMessage();
                    errorMex.title = 'ERRORE.GENERICO_SERVER';
                    errorMex.message = error.message;
                    this.saveError(errorMex);
                }
            );
        }
    }
    sendToSignEvent(
        conferenceId: string,
        managerCST: string,
        event:
            | IntegrationRequest
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
            | EditDate,
        enableProgressBar: boolean = false
    ): Observable<WrapperPostPutData> {
        this.currentEvent = event;
        this.loaderService.showLoading(SectionLoading.EVENTS_MODAL);
        return this.eventsService.createSendToSign(event, conferenceId, managerCST, enableProgressBar);
                
    }

    getUpload(): Subject<Map<string, Subject<{ name: string; progress: number }>>>{
        return this.eventsService.uploads$;
    }

    getUploadCompleted(): boolean{
        return this.eventsService.isUploadCompleted;
    }
}
