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
import { SearchAuthority } from '../models/searchAuthority.model';
import { map, catchError } from 'rxjs/operators';
import { Manager } from '../models/manager.model';
import { Authority } from '../models/authority.model';

@Injectable()
export class AuthorityService extends BaseService {
    constructor(protected api: ApiService) {
        super(api);
    }

    private _getAuthorities(
        params: HttpParams
    ): Observable<WrapperGetData<SearchAuthority>> {
        return this.api
            .request<WrapperGetData<SearchAuthority>>(
                'getAuthorities',
                null,
                params
            )
            .pipe(
                map((result: WrapperGetData<SearchAuthority>) => {
                    const arr = [];
                    // tslint:disable-next-line:forin
                    for (const k in result.list) {
                        arr.push(SearchAuthority.fromDto(result.list[k]));
                    }

                    return {
                        list: arr,
                        totalNumber: result.totalNumber
                    } as WrapperGetData<SearchAuthority>;
                })
            );
    }
    private _getManagers(
        params: HttpParams
    ): Observable<WrapperGetData<Manager>> {
        return this.api
            .request<WrapperGetData<Manager>>('getManagers', null, params)
            .pipe(
                map((result: WrapperGetData<Manager>) => {
                    const arr = [];
                    // tslint:disable-next-line:forin
                    for (const k in result.list) {
                        arr.push(Manager.fromDto(result.list[k]));
                    }

                    return {
                        list: arr,
                        totalNumber: result.totalNumber
                    } as WrapperGetData<Manager>;
                })
            );
    }

    getAuthorities(
        criteriaDto: any,
        value: string = ''
    ): Observable<WrapperGetData<SearchAuthority>> {
        let params = this.getPaginationParamsFromCriteria(criteriaDto);
        if (value !== '') {
            params = params.set('nome', value);
        }
        return this._getAuthorities(params);
    }

    getManagers(
        criteriaDto: any,
        searchQuery?: { value: string; prosecutingAdministration: string }
    ) {
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
            searchQuery.prosecutingAdministration !== '' &&
            searchQuery.prosecutingAdministration !== null
        ) {
            params = params.set(
                'prosecutingAdministration',
                searchQuery.prosecutingAdministration
            );
        }
        return this._getManagers(params);
    }

    createManager(data: Manager): Observable<any> {
        return this.api.request<WrapperPostPutData>(
            'newManager',
            Manager.toDto(data['manager'])
        );
    }

    createAuthority(data: Authority): Observable<any> {
        return this.api.request<WrapperPostPutData>(
            'newAuthority',
            data['authority']
        );
    }

    deleteManager(id: string) {
        return this.api
            .request<WrapperDeleteData>('deleteManager', null, null, { id })
            .pipe(
                map((result: WrapperDeleteData) => {
                    return result;
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

    viewAll(criteriaDto: any): Observable<WrapperGetData<SearchAuthority>> {
        const params = this.getPaginationParamsFromCriteria(criteriaDto);
        return this._getAuthorities(params);
    }

    getAuthorityDetail(id: string): Observable<SearchAuthority> {
        return this.api.request('getAuthorityDetail', null, null, { id });
    }

    getManagerDetail(id: string): Observable<Manager> {
        return this.api.request('getManagerDetail', null, null, { id });
    }

    setProceding(
        data: SearchAuthority,
        proceding: boolean
    ): Observable<WrapperPostPutData> {
        return this.api.request(
            'editProceedingAuthority',
            { flagProsecutingAdministration: proceding },
            null,
            {
                id: data.id
            }
        );
    }

    editAuthority(
        data: SearchAuthority,
        id: number
    ): Observable<WrapperPostPutData> {
        return this.api.request('editAuthority', data['authority'], null, {
            id: id
        });
    }

    edit(data: SearchAuthority, id: number): Observable<WrapperPostPutData> {
        return this.api.request(
            'editProceedingAuthority',
            {
                pec: data['authority'].pec,
                flagProsecutingAdministration: data['authority'].proceding
            },
            null,
            {
                id: id
            }
        );
    }
}
