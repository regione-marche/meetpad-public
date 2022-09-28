import { Injectable } from '@angular/core';
import {
    HttpParams,
    HttpRequest,
    HttpClient,
    HttpEventType,
    HttpResponse,
    HttpEvent
} from '@angular/common/http';

import { Observable, Subject, of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

import { ApiService, ConfigService } from '@eng-ds/ng-core';
import { WrapperGetData, WrapperPostPutData } from '@common';
import { EventType } from '@app/core';

import {
    Event,
    IntegrationRequest,
    IntegrationClosed,
    IntegrationOnlyOneRequest,
    ConferenceMemo,
    ConferenceMemoInternal,
    ConferenceClosing,
    OpinionExpress,
    FinalResolution,
    GenericCommunication,
    IntegrationRegistered
} from '../../models';
import { QuerySearchEvents } from '../../../features/events/models/query-search-events.model';
import { debug } from 'util';
import { EditDate } from '../../models/events/edit-date.model';
import { FormRowBreakComponent } from '@eng-ds/ng-toolkit/lib/components/form/form-row-break/form-row-break.component';

@Injectable()
export class EventsService {
    progress$ = new Subject<number>();
    // Subject a cui iscriversi per vedere tutti gli upload
    uploads$: Subject<
        Map<string, Subject<{ name: string; progress: number }>>
    > = new Subject<Map<string, Subject<{ name: string; progress: number }>>>();
    // Mappa di uploadProgress
    filesUpload$: Map<
        string,
        Subject<{ name: string; progress: number }>
    > = new Map();
    
    isUploadCompleted: boolean = false;
    isEnableProgressBar: boolean = false;

    constructor(
        protected http: HttpClient,
        protected api: ApiService,
        private configService: ConfigService
    ) {}

    enableProgressBar(){
        this.isEnableProgressBar = true;
    }

    disableProgressBar(){
        this.isEnableProgressBar = false;
    }
    
    get(
        idConference: string,
        criteria?: any,
        query?: QuerySearchEvents
    ): Observable<WrapperGetData<Event>> {
        let params = this.getPaginationParamsFromCriteria(criteria);

        if (query && query.eventType && query.eventType !== '-1') {
            params = params.set('eventType', query.eventType.toString());
        }
        if (query && query.authority && query.authority !== '-1') {
            params = params.set('idAuthority', query.authority.toString());
        }
        if (
            query &&
            query.administrationType &&
            query.administrationType !== '-1'
        ) {
            params = params.set(
                'participantRole',
                query.administrationType.toString()
            );
        }
        return this.api
            .request<WrapperGetData<Event>>('searchEvents', null, params, {
                idConference
            })
            .pipe(
                map((result: WrapperGetData<Event>) => {
                    const arr = [];
                    // tslint:disable-next-line:forin
                    for (const k in result.list) {
                        arr.push(Event.fromDto(result.list[k]));
                    }
                    return {
                        list: arr,
                        totalNumber: result.totalNumber
                    } as WrapperGetData<Event>;
                })
            );
    }

    getById(conferenceId: string, eventId: string): Observable<Event> {
        return this.api.request<Event>('getEventById', null, null, {
            conferenceId,
            eventId
        });
    }

    getPaginationParamsFromCriteria(criteria?: any): HttpParams {
        const query = {
            recordForPage: '100',
            pageNumber: '1',
            orderColumn: 'id',
            orderDirection: 'ASC'
        };

        if (criteria) {
            if (criteria['sort']) {
                const [col, direction] = criteria['sort'].split(',');
                query['orderColumn'] = col;
                query['orderDirection'] = direction;
            }
            if (criteria['size']) {
                query['recordForPage'] = criteria['size'];
            }
            if (criteria['page'] === 0 || criteria['page']) {
                query['pageNumber'] = criteria['page'];
            }

            delete criteria['sort'];
            delete criteria['page'];
            delete criteria['size'];
        }

        const params = new HttpParams({
            fromObject: query
        });
        return params;
    }

    create(
        event:
            | Event
            | IntegrationRequest
            | IntegrationClosed
            | IntegrationOnlyOneRequest
            | ConferenceMemo
            | ConferenceMemoInternal
            | ConferenceClosing
            | OpinionExpress
            | FinalResolution
            | GenericCommunication
            | IntegrationRegistered
            | EditDate,
        conferenceId: string,
        enableProgressBar: boolean = false
    ): Observable<WrapperPostPutData> {

        if(enableProgressBar === true)
            this.enableProgressBar();
        else
            this.disableProgressBar();
        this.isUploadCompleted= false;

        const _event = event as any;
        const url = this.configService
            .getApiConfig('createEvent')
            .url.replace('{conferenceId}', conferenceId);

        const formData: FormData = new FormData();
        formData.append('idConference', conferenceId);
        formData.append('type', _event.type.key);

        switch (event.type.key) {
            case EventType.FINAL_RESOLUTION:
            case EventType.INTEGRATION_REQUEST:
            case EventType.INTEGRATION_ONLY_ONE_REQUEST:
            case EventType.INTEGRATION_SEND:
                formData.append(
                    'file',
                    _event.document.file,
                    _event.document.file.name
                );
                formData.append('name', _event.document.file.name);
                formData.append('body', _event.body);
                formData.append(
                    'protocolNumber',
                    _event.document.protocolNumber
                );
                formData.append(
                    'protocolDate',
                    _event.document.protocolDate.date.format('DD-MM-YYYY')
                );
                formData.append('category', _event.document.category.key);
                formData.append(
                    'recipients',
                    JSON.stringify(_event.document.visibility)
                );
                break;
            case EventType.OPINION_EXPRESS:
                formData.append(
                    'file',
                    _event.document.file,
                    _event.document.file.name
                );
                formData.append('name', _event.document.file.name);
                formData.append('body', _event.body);
                formData.append(
                    'protocolNumber',
                    _event.document.protocolNumber
                );
                formData.append(
                    'protocolDate',
                    _event.document.protocolDate.date.format('DD-MM-YYYY')
                );
                formData.append('category', _event.document.category.key);
                formData.append(
                    'recipients',
                    JSON.stringify(_event.document.visibility)
                );
                if (_event.determinationType) {
                    formData.append(
                        'determinationType',
                        _event.determinationType.key
                    );
                }
                break;
            case EventType.CONFERENCE_MEMO:
            case EventType.CONFERENCE_MEMO_INTERNAL:
                        formData.append(
                    'file',
                    _event.document.file,
                    _event.document.file.name
                );
                formData.append('name', _event.document.file.name);
                formData.append(
                    'protocolNumber',
                    _event.document.protocolNumber
                );
                formData.append(
                    'protocolDate',
                    _event.document.protocolDate.date.format('DD-MM-YYYY')
                );
                formData.append('category', _event.document.category.key);
                formData.append('notes', _event.notes);
                break;
            case EventType.CONFERENCE_CLOSING:
                formData.append('notes', _event.notes);
                formData.append('result', _event.result.key);
                break;
            case EventType.INTEGRATION_CLOSED:
                break;
            case EventType.GENERIC_COMUNICATION:
            case EventType.INTEGRATION_REGISTERED:
                formData.append(
                    'file',
                    _event.document.file,
                    _event.document.file.name
                );
                formData.append('name', _event.document.file.name);
                formData.append('body', _event.body);
                formData.append(
                    'protocolNumber',
                    _event.document.protocolNumber
                );
                if (_event.document.protocolDate) {
                    formData.append(
                        'protocolDate',
                        _event.document.protocolDate.date.format('DD-MM-YYYY')
                    );
                }
                formData.append(
                    'recipients',
                    JSON.stringify(_event.recipients)
                );
                break;
            case EventType.EDIT_DATE:
                formData.append('body', _event.body);
                /*
                formData.append('dateType', _event.dateType);
                formData.append(
                        'newDate',
                        _event.newDate.date.format('DD-MM-YYYY')
                    );
                */
                    formData.append(
                        'newDateTermine',
                        _event.newDateTermine.date.format('DD-MM-YYYY')
                    );
                    formData.append(
                        'newDateTermineEsprPareri',
                        _event.newDateTermineEsprPareri.date.format('DD-MM-YYYY')
                    );
                    formData.append(
                        'newDateTermineRichInteg',
                        _event.newDateTermineRichInteg.date.format('DD-MM-YYYY')
                    );
                    formData.append(
                        'newDatePrimaSessioneSimultanea',
                        _event.newDatePrimaSessioneSimultanea.date.format('DD-MM-YYYY')
                    );
                    
                    if(_event.document.file){
                        formData.append(
                            'file',
                            _event.document.file,
                            _event.document.file.name
                        );
                        formData.append('name', _event.document.file.name);
                        formData.append('protocolNumber', _event.document.protocolNumber);
                    }
                    
                break;
        }

        // xmfAddedAttaches
        for(var j=0;event.attachedFiles && j < event.attachedFiles.length; j++)
            formData.append("attachments", event.attachedFiles[j], event.attachedFiles[j]['name']);

        // create a http-post request and pass the form
        // tell it to report the upload progress
        const req = new HttpRequest('POST', url, formData, {
            reportProgress: true
        });

        const data$ = new Subject<WrapperPostPutData>();
        const _p$ = new Subject<{ name: string; progress: number }>();
    
        var fileNameProgress = '';
        if(_event.document && _event.document.file!=null && _event.document.file.name)
            fileNameProgress = _event.document.file.name;

        const name = fileNameProgress.concat(Math.random().toString());

        if(this.isEnableProgressBar){
            this.filesUpload$.set(name, _p$);
            this.uploads$.next(this.filesUpload$);
        }
        // send the http-request and subscribe for progress-updates
        this.http
            .request(req)
            .pipe(
                catchError(err => {
                    if(this.isEnableProgressBar){
                        _p$.error(err);
                        this.filesUpload$.delete(name);
                    }
                    data$.error(err);
                    data$.complete();
                    return of(err);
                })
            )
            .subscribe((__event: HttpEvent<WrapperPostPutData>) => {
                if (__event.type === HttpEventType.UploadProgress) {
                    // calculate the progress percentage
                    const percentDone = Math.round(
                        (100 * __event.loaded) / __event.total
                    );
                    if(this.isEnableProgressBar){
                        if (fileNameProgress.length > 35) { 
                            _p$.next({ name: fileNameProgress.substring(0, 35) + '...', progress: percentDone });
                        }else{
                            _p$.next({ name: fileNameProgress, progress: percentDone });
                        }
                        // pass the percentage into the progress-stream
                        this.progress$.next(percentDone);
                    }
                } else if (__event instanceof HttpResponse) {
                    // Close the progress-stream if we get an answer form the API
                    // The upload is complete
                    // this.progress$.complete();
                    if(this.isEnableProgressBar){
                        _p$.complete();
                        this.filesUpload$.delete(name);
                    }
                    data$.next(__event.body);
                    data$.complete();
                    this.isUploadCompleted= true;
                }
            });

        // return the progress.observables
        return data$.asObservable();
    }

    createSendToSign(
        event:
            | Event
            | IntegrationRequest
            | IntegrationClosed
            | IntegrationOnlyOneRequest
            | ConferenceMemo
            | ConferenceMemoInternal
            | ConferenceClosing
            | OpinionExpress
            | FinalResolution
            | GenericCommunication
            | IntegrationRegistered
            | EditDate,
        conferenceId: string,
        managerCST: string,
        enableProgressBar: boolean = false
    ): Observable<WrapperPostPutData> {
        
        if(enableProgressBar === true)
            this.enableProgressBar();
        else
            this.disableProgressBar();

        this.isUploadCompleted= false;
        const _event = event as any;
        const url = this.configService
            .getApiConfig('createEventSendToSign')
            .url.replace('{idConference}', conferenceId);

        const formData: FormData = new FormData();
        formData.append('idConference', conferenceId);
        formData.append('managerCST', managerCST);
        formData.append('type', _event.type.key);

        switch (event.type.key) {

            case EventType.FINAL_RESOLUTION:
            case EventType.INTEGRATION_REQUEST:
            case EventType.INTEGRATION_ONLY_ONE_REQUEST:
            case EventType.INTEGRATION_SEND:
                formData.append(
                    'file',
                    _event.document.file,
                    _event.document.file.name
                );
                formData.append('name', _event.document.file.name);
                formData.append('body', _event.body);
                formData.append(
                    'protocolNumber',
                    _event.document.protocolNumber
                );
                formData.append(
                    'protocolDate',
                    _event.document.protocolDate.date.format('DD-MM-YYYY')
                );
                formData.append('category', _event.document.category.key);
                formData.append(
                    'recipients',
                    JSON.stringify(_event.document.visibility)
                );
                break;
            case EventType.OPINION_EXPRESS:
                formData.append(
                    'file',
                    _event.document.file,
                    _event.document.file.name
                );
                formData.append('name', _event.document.file.name);
                formData.append('body', _event.body);
                formData.append(
                    'protocolNumber',
                    _event.document.protocolNumber
                );
                formData.append(
                    'protocolDate',
                    _event.document.protocolDate.date.format('DD-MM-YYYY')
                );
                formData.append('category', _event.document.category.key);
                formData.append(
                    'recipients',
                    JSON.stringify(_event.document.visibility)
                );
                if (_event.determinationType) {
                    formData.append(
                        'determinationType',
                        _event.determinationType.key
                    );
                }
                break;
            case EventType.CONFERENCE_MEMO:
            case EventType.CONFERENCE_MEMO_INTERNAL:
                        formData.append(
                    'file',
                    _event.document.file,
                    _event.document.file.name
                );
                formData.append('name', _event.document.file.name);
                formData.append(
                    'protocolNumber',
                    _event.document.protocolNumber
                );
                formData.append(
                    'protocolDate',
                    _event.document.protocolDate.date.format('DD-MM-YYYY')
                );
                formData.append('category', _event.document.category.key);
                formData.append('notes', _event.notes);
                break;
            case EventType.CONFERENCE_CLOSING:
                formData.append('notes', _event.notes);
                formData.append('result', _event.result.key);
                break;
            case EventType.INTEGRATION_CLOSED:
                break;
            case EventType.GENERIC_COMUNICATION:
            case EventType.INTEGRATION_REGISTERED:
                formData.append(
                    'file',
                    _event.document.file,
                    _event.document.file.name
                );
                formData.append('name', _event.document.file.name);
                formData.append('body', _event.body);
                formData.append(
                    'protocolNumber',
                    _event.document.protocolNumber
                );
                if (_event.document.protocolDate) {
                    formData.append(
                        'protocolDate',
                        _event.document.protocolDate.date.format('DD-MM-YYYY')
                    );
                }
                formData.append(
                    'recipients',
                    JSON.stringify(_event.recipients)
                );
                break;
            case EventType.EDIT_DATE:
                formData.append('body', _event.body);
                    /*
                formData.append('dateType', _event.dateType);
                formData.append(
                        'newDate',
                        _event.newDate.date.format('DD-MM-YYYY')
                    );
                */
               
                    formData.append(
                        'newDateTermine',
                        _event.newDateTermine.date.format('DD-MM-YYYY')
                    );
                    formData.append(
                        'newDateTermineEsprPareri',
                        _event.newDateTermineEsprPareri.date.format('DD-MM-YYYY')
                    );
                    formData.append(
                        'newDateTermineRichInteg',
                        _event.newDateTermineRichInteg.date.format('DD-MM-YYYY')
                    );
                    formData.append(
                        'newDatePrimaSessioneSimultanea',
                        _event.newDatePrimaSessioneSimultanea.date.format('DD-MM-YYYY')
                    );
                    
                    if(_event.document.file){
                        formData.append(
                            'file',
                            _event.document.file,
                            _event.document.file.name
                        );
                        formData.append('name', _event.document.file.name);
                        formData.append('protocolNumber', _event.document.protocolNumber);
                    }
                    
                break;
                
        }

        // xmfAddedAttaches
        for(var j=0;event.attachedFiles && j < event.attachedFiles.length; j++)
            formData.append("attachments", event.attachedFiles[j], event.attachedFiles[j]['name']);

        // create a http-post request and pass the form
        // tell it to report the upload progress
        const req = new HttpRequest('POST', url, formData, {
            reportProgress: true
        });

        const data$ = new Subject<WrapperPostPutData>();
        const _p$ = new Subject<{ name: string; progress: number }>();
    
        var fileNameProgress = '';
        if(_event.document && _event.document.file!=null && _event.document.file.name)
            fileNameProgress = _event.document.file.name;

        const name = fileNameProgress.concat(Math.random().toString());

        if(this.isEnableProgressBar){
            this.filesUpload$.set(name, _p$);
            this.uploads$.next(this.filesUpload$);
        }

        // send the http-request and subscribe for progress-updates
        this.http
            .request(req)
            .pipe(
                catchError(err => {
                    if(this.isEnableProgressBar){
                        _p$.error(err);
                        this.filesUpload$.delete(name);
                    }
                    data$.error(err);
                    data$.complete();
                    return of(err);
                })
            )
            .subscribe((__event: HttpEvent<WrapperPostPutData>) => {
                if (__event.type === HttpEventType.UploadProgress) {
                    // calculate the progress percentage
                    const percentDone = Math.round(
                        (100 * __event.loaded) / __event.total
                    );

                    if(this.isEnableProgressBar){
                        _p$.next({ name: '', progress: percentDone });
                        // pass the percentage into the progress-stream
                        this.progress$.next(percentDone);
                    }
                } else if (__event instanceof HttpResponse) {
                    // Close the progress-stream if we get an answer form the API
                    // The upload is complete
                    // this.progress$.complete();
                    if(this.isEnableProgressBar){
                        _p$.complete();
                        this.filesUpload$.delete(name);
                    }
                    data$.next(__event.body);
                    data$.complete();
                    this.isUploadCompleted= true;
                }
            });

        // return the progress.observables
        return data$.asObservable();
    }
}
