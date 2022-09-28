import { Injectable } from '@angular/core';
import {
    BaseService,
    WrapperGetData,
    WrapperPostPutData,
    WrapperDeleteData
} from '@common';
import { ApiService } from '@eng-ds/ng-core';
import { HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { SearchApplicant } from '../models/search-applicant.model';
import { Applicant } from '../models/applicant.model';
import { QueryApplicant } from '../models/query-applicant.model';

@Injectable()
export class ApplicantService extends BaseService {
    constructor(protected api: ApiService) {
        super(api);
    }

    private _getApplicants(
        params: HttpParams
    ): Observable<WrapperGetData<SearchApplicant>> {
        return this.api
            .request<WrapperGetData<SearchApplicant>>(
                'preloading/getApplicants',
                null,
                params
            )
            .pipe(
                map((result: WrapperGetData<SearchApplicant>) => {
                    const arr = [];
                    // tslint:disable-next-line:forin
                    for (const k in result.list) {
                        arr.push(SearchApplicant.fromDto(result.list[k]));
                    }

                    return {
                        list: arr,
                        totalNumber: result.totalNumber
                    } as WrapperGetData<SearchApplicant>;
                })
            );
    }

    getApplicants(
        criteriaDto: any,
        conferenceType: string,
        searchQuery?: QueryApplicant
    ): Observable<WrapperGetData<SearchApplicant>> {
        let params = this.getPaginationParamsFromCriteria(criteriaDto);
        if (
            searchQuery &&
            searchQuery.value !== '' &&
            searchQuery.value !== null
        ) {
            params = params.set('value', searchQuery.value);
        }
        if (
            searchQuery &&
            searchQuery.company !== '' &&
            searchQuery.company !== null
        ) {
            params = params.set('impresa', searchQuery.company);
        }

        if (
            searchQuery &&
            searchQuery !== null &&
            searchQuery.linked !== null
        ) {
            params = params.set(
                'collegamentoAImpresa',
                (!searchQuery.linked).toString()
            );
        }

        params = params.set('codiceTipoConf', conferenceType);
        return this._getApplicants(params);
    }

    createApplicant(data: Applicant, conferenceType: string): Observable<any> {
        return this.api.request<WrapperPostPutData>(
            'preloading/newApplicant',
            Applicant.toDto(data['applicant'], conferenceType)
        );
    }

    deleteApplicant(id: string) {
        return this.api.request<WrapperDeleteData>(
            'preloading/deleteApplicant',
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

    viewAll(criteriaDto: any): Observable<WrapperGetData<SearchApplicant>> {
        const params = this.getPaginationParamsFromCriteria(criteriaDto);
        return this._getApplicants(params);
    }

    // getAuthorityDetail(id: string): Observable<Authority> {
    //     return this.api.request('getAuthorityDetail', null, null, { id });
    // }

    getApplicantDetail(id: string): Observable<Applicant> {
        return this.api.request('preloading/getApplicantDetail', null, null, {
            id
        });
    }

    getApplicantPrecomplete(id: string): Observable<Applicant> {
        return this.api.request('getApplicantPrecomplete', null, null, {
            id
        });
    }

    edit(data: Applicant, id: number): Observable<WrapperPostPutData> {
        data['applicant'].id = id;
        return this.api.request(
            'preloading/editApplicant',
            Applicant.toDto(data['applicant']),
            null,
            {
                id: id
            }
        );
    }
}
