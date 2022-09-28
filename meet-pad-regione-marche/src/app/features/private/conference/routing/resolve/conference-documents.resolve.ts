import {
    Resolve,
    ActivatedRouteSnapshot,
    RouterStateSnapshot
} from '@angular/router';
import { Injectable } from '@angular/core';

import { Observable, of } from 'rxjs';

import { ConferenceStoreService, Documentation } from '../../core';

@Injectable()
export class ConferenceDocumentsResolver implements Resolve<Documentation> {
    constructor(private conferenceStoreService: ConferenceStoreService) {}

    resolve(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<any> | Promise<any> | any {
        if (route.parent.params.id) {
            return this.conferenceStoreService.resolveDocuments(
                route.parent.params.id,
                false
            );
        } else {
            return of(null);
        }
    }
}
