import {
    Resolve,
    ActivatedRouteSnapshot,
    RouterStateSnapshot,
    Router
} from '@angular/router';
import { Injectable } from '@angular/core';

import { ToastrService } from 'ngx-toastr';

import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

import {
    LoaderService,
    SectionLoading,
    HttpInternalErrorResponse
} from '@common';

import { I18nService } from '@eng-ds/ng-core';
import { ConferenceService } from '../../features/authorities/core/services/conference.service';
import { Conference } from '../../features/authorities/core/models/conference.model';

@Injectable()
export class ConferenceResolver implements Resolve<Conference> {
    constructor(
        private router: Router,
        private conferenceService: ConferenceService,
        private loaderService: LoaderService,
        private i18nService: I18nService,
        private toastr: ToastrService
    ) {}

    resolve(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<any> | Promise<any> | any {
        if (route.params.id && route.params.id !== 'new') {
            return this.conferenceService
                .getConferenceDetail(route.params.id)
                .pipe(
                    catchError((err: HttpInternalErrorResponse) => {
                        if (err.status === 404) {
                            this.toastr.warning(
                                this.i18nService.translate(
                                    'AUTHORITIES.PROCEDING.CONFERENCE.TOASTR.NOT_FOUND.TEXT'
                                ),
                                this.i18nService.translate(
                                    'AUTHORITIES.PROCEDING.CONFERENCE.TOASTR.NOT_FOUND.TITLE'
                                )
                            );
                        }
                        this.router.navigate(['/admin']);

                        this.loaderService.hideLoading(
                            SectionLoading.ALL_CONTENT,
                            500
                        );
                        return of(null);
                    })
                );
        } else {
            return of(null);
        }
    }
}
