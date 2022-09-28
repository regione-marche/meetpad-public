import {
    Resolve,
    ActivatedRouteSnapshot,
    RouterStateSnapshot
} from '@angular/router';
import { Injectable } from '@angular/core';

import { Observable, of } from 'rxjs';

import { ConferenceStoreService, SupportContact } from '../../core';

@Injectable()
export class ConferenceContactsResolver implements Resolve<SupportContact> {
    constructor(private conferenceStoreService: ConferenceStoreService) {}

    resolve(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<any> | Promise<any> | any {
        if (route.parent.params.id) {
            return this.conferenceStoreService.resolveContacts(
                route.parent.params.id
            );
        } else {
            return of(null);
        }
    }
}
