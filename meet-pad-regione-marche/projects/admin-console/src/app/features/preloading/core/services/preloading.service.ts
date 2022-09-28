import { Injectable } from '@angular/core';
import { BaseService, WrapperGetData } from '@common';
import { ApiService } from '@eng-ds/ng-core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ConferenceType } from '../models/conferenceType.model';

@Injectable()
export class PreloadingService extends BaseService {
    constructor(protected api: ApiService) {
        super(api);
    }

    getConferenceTypes(): Observable<WrapperGetData<ConferenceType>> {
        return this.api
            .request<WrapperGetData<ConferenceType>>('conferenceTypes')
            .pipe(
                map((result: WrapperGetData<ConferenceType>) => {
                    const arr = [];
                    // tslint:disable-next-line:forin
                    for (const k in result.list) {
                        arr.push(ConferenceType.fromDto(result.list[k]));
                    }
                    return {
                        list: arr,
                        totalNumber: result.totalNumber
                    } as WrapperGetData<ConferenceType>;
                })
            );
    }

    // private _getConference(
    //     params: HttpParams
    // ): Observable<WrapperGetData<SearchConference>> {
    //     return this.api
    //         .request<WrapperGetData<SearchConference>>(
    //             'getConference',
    //             null,
    //             params
    //         )
    //         .pipe(
    //             map((result: WrapperGetData<SearchConference>) => {
    //                 const arr = [];
    //                 // tslint:disable-next-line:forin
    //                 for (const k in result.list) {
    //                     arr.push(
    //                         SearchConference.fromDto(result.list[k])
    //                     );
    //                 }

    //                 return {
    //                     list: arr,
    //                     totalNumber: result.totalNumber
    //                 } as WrapperGetData<SearchConference>;
    //             })
    //         );
    // }

    // getConferences(
    //     criteriaDto: any,
    //     value: string = ''
    // ): Observable<WrapperGetData<SearchConference>> {
    //     let params = this.getPaginationParamsFromCriteria(criteriaDto);
    //     if (value !== '' && value !== null) {
    //         params = params.set('value', value);
    //     }
    //     return this._getConference(params);
    // }

    // getPaginationParamsFromCriteria(criteria: any): HttpParams {
    //     const query = {};
    //     if (criteria['sort']) {
    //         const [col, direction] = criteria['sort'].split(',');
    //         query['orderColumn'] = col;
    //         query['orderDirection'] = direction;
    //     }
    //     if (criteria['size']) {
    //         query['recordForPage'] = criteria['size'];
    //     }
    //     if (criteria['page'] === 0 || criteria['page']) {
    //         query['pageNumber'] = criteria['page'];
    //     }

    //     delete criteria['sort'];
    //     delete criteria['page'];
    //     delete criteria['size'];

    //     const params = new HttpParams({
    //         fromObject: query
    //     });
    //     return params;
    // }

    // getConferenceDetail(id: string): Observable<Conference> {
    //     return this.api.request('getConferenceDetail', null, null, { id });
    // }

    // editConference(
    //     data: Conference,
    //     id: number
    // ): Observable<WrapperPostPutData> {
    //     return this.api.request(
    //         'editConference',
    //         null,
    //         {
    //             idManager: +data['conference'].manager.key
    //         },

    //         {
    //             id: id
    //         }
    //     );
    // }
}
