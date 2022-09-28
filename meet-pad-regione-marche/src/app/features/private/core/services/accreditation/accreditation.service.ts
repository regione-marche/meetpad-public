import { Injectable } from '@angular/core';

import {
    HttpRequest,
    HttpClient,
    HttpEventType,
    HttpResponse,
    HttpParams
} from '@angular/common/http';

import { Observable, Subject, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { ApiService, ConfigService } from '@eng-ds/ng-core';
import {
    WrapperPostPutData,
    WrapperGetData,
    WrapperPostPutAccreditation
} from '@common';

import {
    AccreditamentPreview,
    AccreditamentInfo
} from '@features/private/conference/core/models/accreditation';

@Injectable()
export class AccreditationService {
    progress$ = new Subject<number>();

    constructor(
        private api: ApiService,
        private http: HttpClient,
        private configService: ConfigService
    ) {}

    get(token1: string, token2: string): Observable<AccreditamentInfo> {
        return this.api.request('getAccreditations', null, {
            token1: token1,
            token2: token2
        });
    }

    post(data: AccreditamentPreview): Observable<WrapperPostPutAccreditation> {
        const url = this.configService.getApiConfig('postAccreditations').url;
        const formData: FormData = new FormData();

        formData.append('name ', data.name);
        formData.append('email ', data.email);
        if (data.fiscalCode) {
            formData.append('fiscalCode', data.fiscalCode.toUpperCase());
        }
        formData.append('surname', data.surname);
        formData.append('role', data.profile.key);
        if (data.file) {
            // formData.append('file', data.file);
            formData.append('file', data.file, data.file.name);
        }
        formData.append('token1', data.token1);
        formData.append('token2', data.token2);

        // create a http-put request and pass the form
        // tell it to report the upload progress
        const req = new HttpRequest('POST', url, formData, {
            reportProgress: true
        });

        const data$ = new Subject<WrapperPostPutAccreditation>();

        // send the http-request and subscribe for progress-updates
        this.http
            .request(req)
            .pipe(
                catchError(err => {
                    data$.error(err);
                    data$.complete();
                    return of(err);
                })
            )
            .subscribe(event => {
                if (event.type === HttpEventType.UploadProgress) {
                    // calculate the progress percentage
                    const percentDone = Math.round(
                        (100 * event.loaded) / event.total
                    );

                    // pass the percentage into the progress-stream
                    this.progress$.next(percentDone);
                } else if (event instanceof HttpResponse) {
                    // Close the progress-stream if we get an answer form the API
                    // The upload is complete
                    // this.progress$.complete();
                    data$.next(event.body as WrapperPostPutAccreditation);
                    data$.complete();
                }
            });

        // return the progress.observables
        return data$.asObservable();
    }

    put(data: AccreditamentPreview): Observable<WrapperPostPutAccreditation> {
        const url = this.configService
            .getApiConfig('putAccreditations')
            .url.replace('{id}', data.id);
        const formData: FormData = new FormData();

        formData.append('name ', data.name);
        formData.append('email ', data.email);
        if (data.fiscalCode) {
            formData.append('fiscalCode', data.fiscalCode.toUpperCase());
        }
        formData.append('id ', data.id);
        formData.append('surname', data.surname);
        formData.append('role', data.profile.key);
        if (data.file) {
            formData.append('file', data.file);
            formData.append('file', data.file, data.file.name);
        }
        // create a http-put request and pass the form
        // tell it to report the upload progress
        const req = new HttpRequest('PUT', url, formData, {
            reportProgress: true
        });

        const data$ = new Subject<WrapperPostPutAccreditation>();

        // send the http-request and subscribe for progress-updates
        this.http
            .request(req)
            .pipe(
                catchError(err => {
                    data$.error(err);
                    data$.complete();
                    return of(err);
                })
            )
            .subscribe(event => {
                if (event.type === HttpEventType.UploadProgress) {
                    // calculate the progress percentage
                    const percentDone = Math.round(
                        (100 * event.loaded) / event.total
                    );

                    // pass the percentage into the progress-stream
                    this.progress$.next(percentDone);
                } else if (event instanceof HttpResponse) {
                    // Close the progress-stream if we get an answer form the API
                    // The upload is complete
                    // this.progress$.complete();
                    data$.next(event.body as WrapperPostPutAccreditation);
                    data$.complete();
                }
            });

        // return the progress.observables
        return data$.asObservable();
    }

    getList(
        criteriaDto: any,
        conferenceId: string
    ): Observable<WrapperGetData<AccreditamentPreview>> {
        let params = this.getPaginationParamsFromCriteria(criteriaDto);

        return this.api
            .request<WrapperGetData<AccreditamentPreview>>(
                'getListAccreditations',
                null,
                params,
                { conferenceId }
            )
            .pipe(
                map((result: WrapperGetData<AccreditamentPreview>) => {
                    const arr = [];
                    // tslint:disable-next-line:forin
                    for (const k in result.list) {
                        arr.push(AccreditamentPreview.fromDto(result.list[k]));
                    }
                    return {
                        list: arr,
                        totalNumber: result.totalNumber
                    } as WrapperGetData<AccreditamentPreview>;
                })
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

    confirmOrRejectAccreditation(
        accreditation: AccreditamentPreview
    ): Observable<WrapperPostPutData> {
        const conferenceId = accreditation.idConference;
        const accreditationId = accreditation.id;
        return this.api.request('confirmAccreditation', accreditation, null, {
            conferenceId,
            accreditationId
        });
    }
}
