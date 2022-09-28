import { Resolve, ActivatedRouteSnapshot } from '@angular/router';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import { ConferenceStoreService } from '../../core';

@Injectable()
export class ConferenceBreadcrumbResolver implements Resolve<any> {
    constructor(private conferenceStoreService: ConferenceStoreService) {}

    resolve(
        route: ActivatedRouteSnapshot
    ): Observable<any> | Promise<any> | any {
        return this.conferenceStoreService.resolveTitle(route.params.id);
    }
}
