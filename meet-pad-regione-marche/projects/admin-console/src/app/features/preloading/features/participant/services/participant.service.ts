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
import { SearchParticipant } from '../models/search-participants.model';
import { Participant } from '../models/participant.model';

@Injectable()
export class ParticipantsService extends BaseService {
    constructor(protected api: ApiService) {
        super(api);
    }

    private _getParticipants(
        params: HttpParams
    ): Observable<WrapperGetData<SearchParticipant>> {
        return this.api
            .request<WrapperGetData<SearchParticipant>>(
                'preloading/getParticipants',
                null,
                params
            )
            .pipe(
                map((result: WrapperGetData<SearchParticipant>) => {
                    const arr = [];
                    // tslint:disable-next-line:forin
                    for (const k in result.list) {
                        arr.push(SearchParticipant.fromDto(result.list[k]));
                    }

                    return {
                        list: arr,
                        totalNumber: result.totalNumber
                    } as WrapperGetData<SearchParticipant>;
                })
            );
    }

    getParticipants(
        criteriaDto: any,
        conferenceType: string,
        searchQuery?: string
    ): Observable<WrapperGetData<SearchParticipant>> {
        let params = this.getPaginationParamsFromCriteria(criteriaDto);
        if (searchQuery && searchQuery !== '' && searchQuery !== null) {
            params = params.set('nome', searchQuery);
        }
        params = params.set('codiceTipoConf', conferenceType);
        return this._getParticipants(params);
    }

    createParticipant(
        data: Participant,
        conferenceType: string
    ): Observable<any> {
        return this.api.request<WrapperPostPutData>(
            'preloading/newParticipant',
            Participant.toDto(data['participant'], conferenceType)
        );
    }

    deleteParticipant(id: string) {
        return this.api.request<WrapperDeleteData>(
            'preloading/deleteParticipant',
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

    viewAll(criteriaDto: any): Observable<WrapperGetData<SearchParticipant>> {
        const params = this.getPaginationParamsFromCriteria(criteriaDto);
        return this._getParticipants(params);
    }

    // getAuthorityDetail(id: string): Observable<Authority> {
    //     return this.api.request('getAuthorityDetail', null, null, { id });
    // }

    getParticipantDetail(id: string): Observable<Participant> {
        return this.api.request('preloading/getParticipantDetail', null, null, {
            id
        });
    }

    edit(
        data: Participant,
        conferenceType: string,
        id: string
    ): Observable<WrapperPostPutData> {
        return this.api.request(
            'preloading/editParticipant',
            Participant.toDto(data['participant'], conferenceType),
            null,
            {
                id: id
            }
        );
    }
}
