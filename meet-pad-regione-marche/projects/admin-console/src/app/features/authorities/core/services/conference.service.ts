import { Injectable } from '@angular/core';
import { BaseService, WrapperGetData, WrapperPostPutData } from '@common';
import { ApiService } from '@eng-ds/ng-core';
import { HttpParams } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { SearchConference } from '../models/searchConference.model';
import { Conference } from '../models/conference.model';
import { SendMail } from '../models/sendMail.model';
import { ChangeState } from '../models/changeState';

@Injectable()
export class ConferenceService extends BaseService {
    constructor(protected api: ApiService) {
        super(api);
    }

    nopec : boolean;

    private _getConference(
        params: HttpParams
    ): Observable<WrapperGetData<SearchConference>> {
        return this.api
            .request<WrapperGetData<SearchConference>>(
                'getConference',
                null,
                params
            )
            .pipe(
                map((result: WrapperGetData<SearchConference>) => {
                    const arr = [];
                    // tslint:disable-next-line:forin
                    for (const k in result.list) {
                        arr.push(SearchConference.fromDto(result.list[k]));
                    }

                    return {
                        list: arr,
                        totalNumber: result.totalNumber
                    } as WrapperGetData<SearchConference>;
                })
            );
    }

    getConferences(
        criteriaDto: any,
        value: string = ''
    ): Observable<WrapperGetData<SearchConference>> {
        let params = this.getPaginationParamsFromCriteria(criteriaDto);
        if (value !== '' && value !== null) {
            params = params.set('value', value);
        }
        return this._getConference(params);
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

    getConferenceDetail(id: string): Observable<Conference> {
        return this.api.request('getConferenceDetail', null, null, { id });
    }

    editConference(
        data: Conference,
        id: number
    ): Observable<WrapperPostPutData> {
        return this.api.request(
            'editConference',
            null,
            {
                idManager: +data['conference'].manager.key
            },

            {
                id: id
            }
        );
    }

    sendIndictionMail(
        data: SendMail,
        id: number,
        forcenonpec: boolean
    ): Observable<WrapperPostPutData> {
      
        if(forcenonpec == true) {
           this.nopec =  true;
        } else {
            this.nopec = false;
        }
       
        return this.api.request(
            'sendIndictionMail',
            {
                idAuthority: data['sendIndictionMail'].participant.key,
                email: data['sendIndictionMail'].newMail
            },
            {
                sendnonpec: this.nopec
            },
            {
                idConference: id
            }
        );
    }

    getState(
        data: ChangeState,
        idConference: number
    ): Observable<WrapperPostPutData> {   
        return this.api.request(
            'changeStateConference',
            null,
            null,
            {
                id: idConference,
                idState: data['getStateForConference'].state.key
            }
           
        );
    }
}
