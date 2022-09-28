import {
    Resolve,
    ActivatedRouteSnapshot,
    RouterStateSnapshot,
    Router
} from '@angular/router';
import { Injectable } from '@angular/core';

import { ToastrService } from 'ngx-toastr';

import { Observable, of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

import { HttpInternalErrorResponse } from '@common';

import { I18nService } from '@eng-ds/ng-core';

import { ConferenceService } from '@app/core';
import { ConferenceStoreService, Conference } from '../../core';

@Injectable()
export class ConferenceResolver implements Resolve<Conference> {
    constructor(
        private router: Router,
        private conferenceService: ConferenceService,
        private conferenceStoreService: ConferenceStoreService,
        private i18nService: I18nService,
        private toastr: ToastrService
    ) {}

    resolve(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<any> | Promise<any> | any {
        if (route.parent.params.id) {
            return this.conferenceService
                .getDetail(route.parent.params.id)
                .pipe(
                    catchError((err: HttpInternalErrorResponse) => {
                        if (err.status === 404) {
                            this.toastr.warning(
                                this.i18nService.translate(
                                    'CONFERENCE.TOASTR.NOT_FOUND.TEXT'
                                ),
                                this.i18nService.translate(
                                    'CONFERENCE.TOASTR.NOT_FOUND.TITLE'
                                )
                            );
                        }
                        this.router.navigate(['/']);
                        return of(null);
                    }),
                    map((conference: Conference) => {
                        if (conference) {
                            this.conferenceStoreService.selectCorrectRoute(
                                true
                            );
                            return of(conference);
                        }
                    })
                );
        } else {
            return of(null);
        }
    }
}
