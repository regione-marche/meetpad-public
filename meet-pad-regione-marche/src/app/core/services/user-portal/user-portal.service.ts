import { Injectable } from '@angular/core';
import { HttpParams } from '@angular/common/http';

import { Observable, of, Subject } from 'rxjs';
import { map } from 'rxjs/internal/operators/map';

import { ApiService } from '@eng-ds/ng-core';

import { UserService, WrapperGetData, ComboBox, User } from '@common';

import { AccreditamentPreview } from '@app/features/private/conference/core/models/accreditation/accreditament-preview.model';
import { catchError } from 'rxjs/operators';

@Injectable()
export class UserPortalService extends UserService {
    constructor(protected api: ApiService) {
        super(api);
        console.log();
    }

    
    getUser(cache: boolean = true): Observable<User> {
        return this.get<User>(
            this.api.request('getUserInfo'),
            'userInfo',
            !cache
        );
    }
    
    getPendingAccreditations(): Observable<AccreditamentPreview[]> {
        return this.get<WrapperGetData<AccreditamentPreview>>(
            this.api.request<WrapperGetData<AccreditamentPreview>>(
                'getPendingAccreditations'
            ),
            'pendingAccreditations'
        ).pipe(
            map((accInfo: WrapperGetData<AccreditamentPreview>) => {
                const res = [];
                for (let i = 0; i < accInfo.list.length; i++) {
                    res.push(new AccreditamentPreview(accInfo.list[i]));
                }
                return res;
            })
        );
    }

    getConferenceParticipant(conferenceId: string): Observable<ComboBox> {
        let params = new HttpParams();

        params = params.set('idConference', conferenceId);
        return this.api.request('getConferenceParticipant', null, params);
    }

    getConferenceProfile(conferenceId: string): Observable<User> {
        let params = new HttpParams();

        params = params.set('idConference', conferenceId);
        return this.get<User>(
            this.api.request('getConferenceProfile', null, params),
            `${conferenceId}.conferenceProfile`
        );
    }

    isConferenceDelegate(conferenceId: string): Observable<boolean> {
        let params = new HttpParams();

        params = params.set('idConference', conferenceId);

        const data$ = new Subject<boolean>();
        this.api.request<boolean>('getConferenceDelegate', null, params).
        pipe(     
            catchError(err => {
            data$.error(err);
            data$.complete();
            return of(err);
        })
        ).subscribe(res => {
            data$.next(res as boolean);
            data$.complete();
        });
        return data$.asObservable();
    }
}
