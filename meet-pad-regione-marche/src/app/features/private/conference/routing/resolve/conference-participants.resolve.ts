import {
    Resolve,
    ActivatedRouteSnapshot,
    RouterStateSnapshot
} from '@angular/router';
import { Injectable } from '@angular/core';

import { Observable, of } from 'rxjs';

import { Participant, ConferenceStoreService } from '../../core';
import { tap } from 'rxjs/operators';

@Injectable()
export class ConferenceParticipantsResolver implements Resolve<Participant[]> {
    constructor(private conferenceStoreService: ConferenceStoreService) {}

    resolve(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<any> | Promise<any> | any {
        if (route.parent.params.id) {
            return this.conferenceStoreService
                .resolveParticipants(route.parent.params.id)
                .pipe(
                    tap((participants: Participant[]) => {
                        this.conferenceStoreService.conference.addParticipants(
                            participants
                        );
                    })
                );
        } else {
            return of(null);
        }
    }
}
