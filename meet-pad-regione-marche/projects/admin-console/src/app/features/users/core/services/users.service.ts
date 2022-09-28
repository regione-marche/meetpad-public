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
import { SearchUser } from '../models/searchUser.model';
import { User } from '../models/user.model';

@Injectable()
export class UsersService extends BaseService {
    constructor(protected api: ApiService) {
        super(api);
    }

    private _getUsers(
        params: HttpParams
    ): Observable<WrapperGetData<SearchUser>> {
        return this.api
            .request<WrapperGetData<SearchUser>>('getUsers', null, params)
            .pipe(
                map((result: WrapperGetData<SearchUser>) => {
                    const arr = [];
                    // tslint:disable-next-line:forin
                    for (const k in result.list) {
                        arr.push(SearchUser.fromDto(result.list[k]));
                    }

                    return {
                        list: arr,
                        totalNumber: result.totalNumber
                    } as WrapperGetData<SearchUser>;
                })
            );
    }

    getUsers(
        criteriaDto: any,
        searchQuery?: { value: string; prosecutingAdministration: string }
    ): Observable<WrapperGetData<SearchUser>> {
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
                'amministrazioneProcedente',
                searchQuery.prosecutingAdministration
            );
        }
        return this._getUsers(params);
    }

    createUser(data: User): Observable<any> {
        return this.api.request<WrapperPostPutData>(
            'newUser',
            User.toDto(data['user'])
        );
    }

    deleteUser(id: string) {
        return this.api.request<WrapperDeleteData>('deleteUser', null, null, {
            id
        });
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

    viewAll(criteriaDto: any): Observable<WrapperGetData<SearchUser>> {
        const params = this.getPaginationParamsFromCriteria(criteriaDto);
        return this._getUsers(params);
    }

    // getAuthorityDetail(id: string): Observable<Authority> {
    //     return this.api.request('getAuthorityDetail', null, null, { id });
    // }

    getUserDetail(id: string): Observable<User> {
        return this.api.request('getUserDetail', null, null, { id });
    }

    edit(data: User, id: string): Observable<WrapperPostPutData> {
        return this.api.request('editUser', User.toDto(data['user']), null, {
            id: id
        });
    }
}
