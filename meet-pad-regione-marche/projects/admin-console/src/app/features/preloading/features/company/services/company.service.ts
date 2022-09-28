import { Injectable } from '@angular/core';
import {
    BaseService,
    WrapperGetData,
    WrapperPostPutData,
    WrapperDeleteData
} from '@common';
import { ApiService } from '@eng-ds/ng-core';
import { HttpParams } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { SearchCompany } from '../models/searchCompany.model';
import { Company } from '../models/company.model';

@Injectable()
export class CompanyService extends BaseService {
    constructor(protected api: ApiService) {
        super(api);
    }

    private _getCompanies(
        params: HttpParams
    ): Observable<WrapperGetData<SearchCompany>> {
        return this.api
            .request<WrapperGetData<SearchCompany>>(
                'getCompanies',
                null,
                params
            )
            .pipe(
                map((result: WrapperGetData<SearchCompany>) => {
                    const arr = [];
                    // tslint:disable-next-line:forin
                    for (const k in result.list) {
                        arr.push(SearchCompany.fromDto(result.list[k]));
                    }

                    return {
                        list: arr,
                        totalNumber: result.totalNumber
                    } as WrapperGetData<SearchCompany>;
                })
            );
    }

    private _getPreloadingCompanies(
        params: HttpParams,
        conferenceType: string
    ): Observable<WrapperGetData<SearchCompany>> {
        params = params.append('tipologiaConferenza', conferenceType);
        return this.api
            .request<WrapperGetData<SearchCompany>>(
                'preloading/getCompanies',
                null,
                params
            )
            .pipe(
                map((result: WrapperGetData<SearchCompany>) => {
                    const arr = [];
                    // tslint:disable-next-line:forin
                    for (const k in result.list) {
                        arr.push(SearchCompany.fromDto(result.list[k]));
                    }

                    return {
                        list: arr,
                        totalNumber: result.totalNumber
                    } as WrapperGetData<SearchCompany>;
                })
            );
    }

    getCompanies(
        criteriaDto: any,
        value: string = '',
        preloading: boolean = false,
        conferenceType?: string
    ): Observable<WrapperGetData<SearchCompany>> {
        let params = this.getPaginationParamsFromCriteria(criteriaDto);
        if (value !== '' && value !== null) {
            params = params.set('value', value);
        }
        if (preloading) {
            return this._getPreloadingCompanies(params, conferenceType);
        } else {
            return this._getCompanies(params);
        }
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

    deleteCompany(id: string) {
        return this.api.request<WrapperDeleteData>(
            'preloading/deleteCompany',
            null,
            null,
            {
                id
            }
        );
    }

    getCompanyDetail(id: string): Observable<Company> {
        return this.api.request('preloading/getCompanyDetail', null, null, {
            id
        });
    }

    getNewCompanyDetail(id: string): Observable<Company> {
        return this.api.request('preloading/getNewCompanyDetail', null, null, {
            id
        });
    }

    createCompany(data: Company, conferenceType: number): Observable<any> {
        return this.api.request<WrapperPostPutData>(
            'preloading/newCompany',
            Company.toDto(data['company'], conferenceType)
        );
    }

    edit(data: Company, id: number): Observable<WrapperPostPutData> {
        return this.api.request(
            'preloading/editCompany',
            Company.toDto(data['company']),
            null,
            {
                id: id
            }
        );
    }
}
