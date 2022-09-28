import { Injectable } from '@angular/core';
import { HttpParams } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Observable } from 'rxjs';

import { ApiService } from '@eng-ds/ng-core';

import { WrapperGetData } from '@common';

import { Pec } from '../../models/pec/pec.model';

@Injectable({
    providedIn: 'root'
})
export class PecService {
    constructor(private api: ApiService) {}

    getList(
        criteriaDto: any,
        conferenceId: string
    ): Observable<WrapperGetData<Pec>> {
        let params = this.getPaginationParamsFromCriteria(criteriaDto);
        return this.api
            .request<WrapperGetData<Pec>>('getPec', null, params, {
                conferenceId
            })
            .pipe(
                map((result: WrapperGetData<Pec>) => {
                    const arr = [];
                    // tslint:disable-next-line:forin
                    for (const k in result.list) {
                        arr.push(Pec.fromDto(result.list[k]));
                    }
                    return {
                        list: arr,
                        totalNumber: result.totalNumber
                    } as WrapperGetData<Pec>;
                })
            );
    }

    advancedSearch(
        criteriaDto: any,
        conferenceId: string,
        searchCriteria: Pec
    ): Observable<WrapperGetData<Pec>> {
        let params = this.getPaginationParamsFromCriteria(criteriaDto);

        if (searchCriteria.status) {
            if (
                searchCriteria.status.key != '' &&
                searchCriteria.status.key != undefined
            ) {
                params = params.set('status', searchCriteria.status.key);
            }
        }
        if (searchCriteria.event) {
            if (
                searchCriteria.event.key != '' &&
                searchCriteria.event.key != undefined
            ) {
                params = params.set('event', searchCriteria.event.key);
            }
        }

        if (searchCriteria.sender) {
            params = params.set('sender', searchCriteria.sender);
        }
        if (searchCriteria.recipient) {
            params = params.set('recipient', searchCriteria.recipient);
        }
        return this._advancedSearch(params, conferenceId);
    }

    private _advancedSearch(
        params: HttpParams,
        conferenceId: string
    ): Observable<WrapperGetData<Pec>> {
        return this.api
            .request<WrapperGetData<Pec>>('getPec', null, params, {
                conferenceId
            })
            .pipe(
                map((result: WrapperGetData<Pec>) => {
                    const arr = [];
                    // tslint:disable-next-line:forin
                    for (const k in result.list) {
                        arr.push(Pec.fromDto(result.list[k]));
                    }
                    return {
                        list: arr,
                        totalNumber: result.totalNumber
                    } as WrapperGetData<Pec>;
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
}
