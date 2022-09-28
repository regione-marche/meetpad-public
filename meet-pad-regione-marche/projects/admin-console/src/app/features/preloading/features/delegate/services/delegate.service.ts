import { Injectable } from '@angular/core';
import {
    BaseService,
    WrapperGetData,
    WrapperPostPutData,
    WrapperDeleteData,
    BaseFile
} from '@common';
import { ApiService, ConfigService } from '@eng-ds/ng-core';
import { HttpClient, HttpParams, HttpRequest, HttpResponse } from '@angular/common/http';
import { Observable, of, Subject } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { Delegate } from '../models/delegate.model';
import { SearchDelegate } from '../models/search-delegates.model';
import { QueryDelegate } from '../models/query-delegate.model';

@Injectable()
export class DelegateService extends BaseService {
    constructor(
        protected http: HttpClient,
        private configService: ConfigService,
        protected api: ApiService) {
        super(api);
    }

    private _getDelegates(
        params: HttpParams
    ): Observable<WrapperGetData<SearchDelegate>> {
        return this.api
            .request<WrapperGetData<SearchDelegate>>(
                'preloading/getDelegates',
                null,
                params
            )
            .pipe(
                map((result: WrapperGetData<SearchDelegate>) => {
                    const arr = [];
                    for (const k in result.list) {
                        arr.push(SearchDelegate.fromDto(result.list[k]));
                    }

                    return {
                        list: arr,
                        totalNumber: result.totalNumber
                    } as WrapperGetData<SearchDelegate>;
                })
            );
    }

    getDelegates(
        criteriaDto: any,
        conferenceType: string,
        searchQuery?: QueryDelegate
    ): Observable<WrapperGetData<SearchDelegate>> {
        let params = this.getPaginationParamsFromCriteria(criteriaDto);
        if (
            searchQuery &&
            searchQuery.value !== '' &&
            searchQuery.value !== null
        ) {
            params = params.set('value', searchQuery.value);
        }
           
        params = params.set('codiceTipoConf', conferenceType);
        return this._getDelegates(params);
       
    }

    deleteDelegate(id: string) {
        return this.api.request<WrapperDeleteData>(
            'preloading/deleteDelegate',
            null,
            null,
            {
                id
            }
        );
    }

    deleteDelegateDocument(id: string) {
        return this.api.request<WrapperPostPutData>(
            'preloading/deleteDelegateFile',
            null,
            null,
            {
                id: id
            }
        );
    }

    getPaginationParamsFromCriteria(criteria: any): HttpParams {
        const query = {};
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

        const params = new HttpParams({
            fromObject: query
        });
        return params;
    }

    viewAll(criteriaDto: any): Observable<WrapperGetData<SearchDelegate>> {
        const params = this.getPaginationParamsFromCriteria(criteriaDto);
        return this._getDelegates(params);
    }

    getDelegateDetail(id: string): Observable<Delegate> {
        return this.api.request('preloading/getDelegateDetail', null, null, {
            id
        });
    }
    getDelegatePrecomplete(id: string): Observable<Delegate> {
        return this.api.request('getDelegatePrecomplete', null, null, {
            id
        });
    }

    saveFile(
        data: Delegate,
        conferenceType: string,
        file: File
    ): Observable<BaseFile> {
        const url = this.configService
            .getApiConfig('preloading/saveDelegateFile').url;

        const formData: FormData = new FormData();

        formData.append('conferenceType', conferenceType );
        formData.append('name', data['delegate'].name );
        formData.append('surname', data['delegate'].surname );
        formData.append('email', data['delegate'].email );
        formData.append('fiscalCode', data['delegate'].fiscalCode );
        if(file){
            formData.append('file', file, file.name);
            formData.append('documentName ', file.name);
        }

        // create a http-post request and pass the form
        // tell it to report the upload progress
        const req = new HttpRequest('POST', url, formData, {
            reportProgress: false
        });

        const data$ = new Subject<BaseFile>();

        const _p$ = new Subject<{ name: string; progress: number }>();

        // send the http-request and subscribe for progress-updates
        this.http
            .request(req)
            .pipe(
                catchError(err => {
                    _p$.error(err);

                    // this.uploads$.next(this.filesUpload$);
                    data$.error(err);
                    data$.complete();
                    return of(err);
                })
            )
            .subscribe(event => {
              if (event instanceof HttpResponse) {
                    // Close the progress-stream if we get an answer form the API
                    // The upload is complete
                    // this.progress$.complete();
                    _p$.complete();
                   
                    // this.uploads$.next(this.filesUpload$);
                    data$.next(event.body as BaseFile);
                    data$.complete();
                }
            });

        return data$.asObservable();
    }

    updateDelegate(
        data: Delegate,
        conferenceType: string,
        id: string,
        file: File
    ): Observable<BaseFile> {
        const url = this.configService
            .getApiConfig('preloading/editDelegate')
            .url.replace('{id}', id);

        const formData: FormData = new FormData();

        formData.append('conferenceType', conferenceType );
        formData.append('name', data['delegate'].name );
        formData.append('surname', data['delegate'].surname );
        formData.append('email', data['delegate'].email );
        formData.append('fiscalCode', data['delegate'].fiscalCode );
        if(file){
            formData.append('file', file, file.name);
            formData.append('documentName ', file.name);
        }

        // create a http-post request and pass the form
        // tell it to report the upload progress
        const req = new HttpRequest('PATCH', url, formData, {
            reportProgress: false
        });

        const data$ = new Subject<BaseFile>();

        const _p$ = new Subject<{ name: string; progress: number }>();

        // send the http-request and subscribe for progress-updates
        this.http
            .request(req)
            .pipe(
                catchError(err => {
                    _p$.error(err);

                    // this.uploads$.next(this.filesUpload$);
                    data$.error(err);
                    data$.complete();
                    return of(err);
                })
            )
            .subscribe(event => {
              if (event instanceof HttpResponse) {
                    // Close the progress-stream if we get an answer form the API
                    // The upload is complete
                    // this.progress$.complete();
                    _p$.complete();
                   
                    // this.uploads$.next(this.filesUpload$);
                    data$.next(event.body as BaseFile);
                    data$.complete();
                }
            });

        return data$.asObservable();
    }
}
