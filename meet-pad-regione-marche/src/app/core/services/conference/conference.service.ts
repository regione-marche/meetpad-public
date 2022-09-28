import { HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';

import { ApiService } from '@eng-ds/ng-core';

import { BaseService, WrapperGetData, WrapperPostPutData } from '@common';

import { StepName, ConferenceState } from '@app/core';

import { SearchAdvancedCriteria } from '@features/private/search/models/search-advanced-criteria.model';
import { SearchConference } from '@features/private/search/models/search-conference.model';
import {AccreditationPartecipants} from "@features/private/conference/core/models/accreditation/accreditations-participants.model";
import {
    Conference,
    PaleoConference,
    DomusConference
} from '@features/private/conference/core';


@Injectable()
export class ConferenceService extends BaseService {
    constructor(protected api: ApiService) {
        super(api);
    }


    private _unifyResearch(
        params: HttpParams
    ): Observable<WrapperGetData<SearchConference>> {
        return this.api
            .request<WrapperGetData<SearchConference>>(
                'unifyResearch',
                null,
                params
            )
            .pipe(
                map((result: WrapperGetData<SearchConference>) => {
                    const arr = [];
                    if (result && result.list) {
                        for (const k in result.list) {
                            if (result.list[k]) {
                                arr.push(
                                    SearchConference.fromDto(result.list[k])
                                );
                            }
                        }
                    }
                    return {
                        list: arr,
                        totalNumber: result.totalNumber
                    } as WrapperGetData<SearchConference>;
                })
            );
    }

    private _advancedSearch(
        params: HttpParams
    ): Observable<WrapperGetData<SearchConference>> {
        return this.api
            .request<WrapperGetData<SearchConference>>(
                'advancedSearch',
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

    unifyResearch(
        criteriaDto: any,
        value: string = '',
        stateValue: string = ''
    ): Observable<WrapperGetData<SearchConference>> {
        let params = this.getPaginationParamsFromCriteria(criteriaDto);
        params = params.set('value', value);
        params = params.set('state', stateValue);
        return this._unifyResearch(params);
    }

    advancedSearch(
        criteriaDto: any,
        searchCriteria: SearchAdvancedCriteria
    ): Observable<WrapperGetData<SearchConference>> {
        let params = this.getPaginationParamsFromCriteria(criteriaDto);

        const conferenceType =
            searchCriteria.conferenceType &&
            (searchCriteria.conferenceType.key || '');
        const state = searchCriteria.state && (searchCriteria.state.key || '');

        params = params.set('conferenceType', conferenceType);
        params = params.set('state', state);
        params = params.set('fiscalCode', searchCriteria.fiscalCode);
        params = params.set(
            'requestReference',
            searchCriteria.requestReference
        );

        params = params.set('province', searchCriteria.province.key || '');
        params = params.set('city', searchCriteria.city.key || '');

        return this._advancedSearch(params);
    }

    simpleSearch(id: string): Observable<any> {
        let params = new HttpParams();
        params = params.set('id', id);

        return this.api
            .request<WrapperGetData<SearchConference>>(
                'simpleSearch',
                null,
                params
            )
            .pipe(
                map((result: WrapperGetData<SearchConference>) => {
                    const arr = [];
                    if (result && result.list) {
                        for (const k in result.list) {
                            if (result.list[k]) {
                                arr.push(
                                    SearchConference.fromDto(result.list[k])
                                );
                            }
                        }
                    }
                    // tslint:disable-next-line:forin
                    return arr;
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

    desktopSearch(
        criteriaDto: any
    ): Observable<WrapperGetData<SearchConference>> {
        let params = this.getPaginationParamsFromCriteria(criteriaDto);

        params = params.set('conferenceType', '');
        params = params.set('state', '');
        params = params.set('fiscalCode', '');
        params = params.set('requestReference', '');

        params = params.set('province', '');
        params = params.set('city', '');

        return this._advancedSearch(params);
    }

    getDetail(id: string): Observable<Conference> {
        return this.api.request('getConferenceDetail', null, null, { id });
    }

    getEnableApplicantEdit(id: string): Observable<boolean> {
        this.getDetail(id).subscribe(value => {
            return of(value['enableApplicantEdit']);
        });
        return of(null);
    }

    create(data: Conference): Observable<WrapperPostPutData> {
        const dataToSend = {
            definition: true,
            documentation: false
        };
        return this.api.request('createConference', data.toDto(dataToSend));
    }

    edit(
        data: Conference,
        dataToSend: { [key: string]: boolean }
    ): Observable<WrapperPostPutData> {
        return this.api.request(
            'editConference',
            data.toDto(dataToSend),
            null,
            { id: data.id }
        );
    }

    delete(id: string): Observable<Conference> {
        return this.api.request('deleteConference', null, null, { id: id });
    }

    updateStep(
        conferenceId: string,
        step: StepName
    ): Observable<WrapperPostPutData> {
        return this.api.request('updateStep', { step }, null, {
            id: conferenceId
        });
    }

    getApplicantEditorStep(id: string): Observable<any> {
        return this.api.request('getConferencePermission', null, null, {
            id: id
        });
    }

    patchApplicantEditorEnabled(id: string, enabled: boolean): Observable<any> {
        return this.api.request(
            'patchEnabled',
            { enableApplicantEdit: enabled },
            null,
            { id: id }
        );
    }

    clone(id: string): Observable<string> {
        return this.api.request('cloneConference', null, null, { id: id });
    }

    paleoCreate(data: PaleoConference): Observable<{ id: string }> {
        return this.api.request('createConferenceFromPaleo', data.toDto());
    }

    updateState(
        conferenceId: string,
        state: ConferenceState,
        step: StepName
    ): Observable<string> {
        return this.api.request(
            'updateState',
            {
                state: { key: state },
                step
            },
            null,
            { id: conferenceId }
        );
    }

    domusCreateWithManager(comune: string, conferenceManagersDomus: string): Observable<{ id: string, ids: string }> {
        return this.api.request('createConferenceFromDomusAndAccountable', 
       // comune,
       {
        comune,
        conferenceManagersDomus
       },
        null, 
        { comune: comune, conferenceManagersDomus: conferenceManagersDomus });
    }

    /* domusCreate(comune: string): Observable<{ id: string }> {
        return this.api.request('createConferenceFromDomus', comune, null, { comune: comune });
    }*/
}
