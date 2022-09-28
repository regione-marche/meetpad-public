import { Injectable } from '@angular/core';
import {
    CanActivate,
    ActivatedRouteSnapshot,
    RouterStateSnapshot
} from '@angular/router';

import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ApplicationRole } from '@common';
import { UserPortalService } from '@app/core';
import { ConferenceStoreService } from '../../../core';

@Injectable()
export class ConferenceCreateGuard implements CanActivate {
    constructor(
        private userService: UserPortalService,
        public conferenceStoreService: ConferenceStoreService
    ) {}

    canActivate(
        next: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<boolean> | Promise<boolean> | boolean {
        return this.userService.getRole().pipe(
            map((role: ApplicationRole) => {
                // autorizzazioni mancanti
                // ATTENZIONE! Testare con i ruoli dopo commento per aprire creazione domus ad amministratore
                /*if (
                    role !== ApplicationRole.CONFERENCE_CREATOR 
                    &&  
                    role !== ApplicationRole.CONFERENCE_MANAGER
                ) {
                    return false;
                }*/
                if (next.data.initStore) {
                    this.conferenceStoreService.init();
                }
                return true;
            })
        );
    }
}
