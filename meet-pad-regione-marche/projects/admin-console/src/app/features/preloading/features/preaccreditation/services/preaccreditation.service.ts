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
import { Preaccreditation } from '../models/preaccreditation.model';
import { SearchPreaccreditation } from '../models/search-preaccreditation.model';
import { QueryPreaccreditation } from '../models/query-preaccreditation.model';

@Injectable()
export class PreaccreditationService extends BaseService {
    constructor(
        protected http: HttpClient,
        private configService: ConfigService,
        protected api: ApiService) {
        super(api);
    }

    private _getPreaccreditation(
        params: HttpParams
    ): Observable<WrapperGetData<SearchPreaccreditation>> {
        return this.api
            .request<WrapperGetData<SearchPreaccreditation>>(
                'preloading/getPreaccreditation',
                null,
                params
            )
            .pipe(
                map((result: WrapperGetData<SearchPreaccreditation>) => {
                    const arr = [];
                    for (const k in result.list) {
                        arr.push(SearchPreaccreditation.fromDto(result.list[k]));
                    }

                    return {
                        list: arr,
                        totalNumber: result.totalNumber
                    } as WrapperGetData<SearchPreaccreditation>;
                })
            );
    }

    getPreaccreditation(
        criteriaDto: any,
        conferenceType: string,
        searchQuery?: QueryPreaccreditation
    ): Observable<WrapperGetData<SearchPreaccreditation>> {
        let params = this.getPaginationParamsFromCriteria(criteriaDto);
        if (
            searchQuery &&
            searchQuery.value !== '' &&
            searchQuery.value !== null
        ) {
            params = params.set('value', searchQuery.value);
        }
           
        params = params.set('codiceTipoConf', conferenceType);
        return this._getPreaccreditation(params);
       
    }

    deletePreaccreditation(id: string) {
        return this.api.request<WrapperDeleteData>(
            'preloading/deletePreaccreditation',
            null,
            null,
            {
                id
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

    viewAll(criteriaDto: any): Observable<WrapperGetData<SearchPreaccreditation>> {
        const params = this.getPaginationParamsFromCriteria(criteriaDto);
        return this._getPreaccreditation(params);
    }

    getPreaccreditationDetail(id: string): Observable<Preaccreditation> {
        return this.api.request('preloading/getPreaccreditationDetail', null, null, {
            id
        });
    }
    getPreaccreditationPrecomplete(id: string): Observable<Preaccreditation> {
        return this.api.request('preloading/getPreaccreditationPrecomplete', null, null, {
            id
        });
    }
    
    createPreaccreditation(
        data: Preaccreditation,
        conferenceType: string
    ): Observable<any> {
        return this.api.request<WrapperPostPutData>(
            'preloading/newPreaccreditation',
            Preaccreditation.toDto(data['preaccreditation'], conferenceType)
        );
    }

    edit(
        data: Preaccreditation,
        conferenceType: string,
        id: string
    ): Observable<WrapperPostPutData> {
        return this.api.request(
            'preloading/editPreaccreditation',
            Preaccreditation.toDto(data['preaccreditation'], conferenceType),
            null,
            {
                id: id
            }
        );
    }
}
