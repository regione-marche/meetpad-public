import { Injectable } from '@angular/core';
import { Voting } from '../../models/voting/voting.model';
import { Observable } from 'rxjs';
import { HttpParams, HttpRequest, HttpClient } from '@angular/common/http';
import { BaseService, WrapperGetData, WrapperPostPutData } from '@common';
import { ApiService, ConfigService } from '@eng-ds/ng-core';
import { map } from 'rxjs/operators';
import { Vote } from '../../models/voting/vote.model';

@Injectable({
    providedIn: 'root'
})
export class VotingService extends BaseService {
    constructor(
        protected api: ApiService,
        private configService: ConfigService,
        private http: HttpClient
    ) {
        super(api);
    }

    _getVotings(
        conferenceId: string,
        params: HttpParams
    ): Observable<WrapperGetData<Voting>> {
        return this.api
            .request<WrapperGetData<Voting>>('getVotings', null, params, {
                conferenceId
            })
            .pipe(
                map((result: WrapperGetData<Voting>) => {
                    const arr = [];
                    // tslint:disable-next-line:forin
                    for (const k in result.list) {
                        arr.push(Voting.fromDto(result.list[k]));
                    }
                    return {
                        list: arr,
                        totalNumber: result.totalNumber
                    } as WrapperGetData<Voting>;
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

    getVotings(
        criteriaDto: any,
        conferenceId: string,
        searchQuery: boolean = false
    ): Observable<WrapperGetData<Voting>> {
        let params = this.getPaginationParamsFromCriteria(criteriaDto);
        // passare searchQuery
        params = params.set('key', searchQuery.toString());
        return this._getVotings(conferenceId, params);
    }

    postVote(
        conferenceId: string,
        votingId: string,
        vote: Vote
    ): Observable<WrapperPostPutData> {
        return this.api.request<WrapperPostPutData>('postVote', vote, null, {
            conferenceId: conferenceId,
            votingId: votingId
        });
    }

    deleteVoting(
        conferenceId: string,
        votingId: string
    ): Observable<WrapperPostPutData> {
        return this.api.request<WrapperPostPutData>(
            'deleteVoting',
            null,
            null,
            {
                contactId: conferenceId,
                conferenceId: conferenceId,
                votingId: votingId
            }
        );
    }

    changeVotingState(
        conferenceId: string,
        votingId: string,
        voting: Voting
    ): Observable<WrapperPostPutData> {
        return this.api.request<WrapperPostPutData>(
            'editVoting',
            voting,
            null,
            {
                conferenceId: conferenceId,
                votingId: votingId
            }
        );
    }

    editVoting(
        conferenceId: string,
        votingId: string,
        voting: Voting
    ): Observable<WrapperPostPutData> {
        return this.api.request<WrapperPostPutData>(
            'editVoting',
            voting['voting'],
            null,
            {
                conferenceId: conferenceId,
                votingId: votingId
            }
        );
    }

    addVoting(
        conferenceId: string,
        voting: Voting
    ): Observable<WrapperPostPutData> {
        return this.api.request<WrapperPostPutData>(
            'postVoting',
            voting['voting'],
            null,
            {
                conferenceId: conferenceId
            }
        );
    }
}
