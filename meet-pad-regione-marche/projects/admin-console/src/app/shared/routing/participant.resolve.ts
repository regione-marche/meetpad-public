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
import { Participant } from '../../features/preloading/features/participant/models/participant.model';
import { ParticipantsService } from '../../features/preloading/features/participant/services/participant.service';

@Injectable()
export class ParticipantResolver implements Resolve<Participant> {
    constructor(
        private router: Router,
        private participantsService: ParticipantsService,
        private i18nService: I18nService,
        private toastr: ToastrService,
        private loaderService: LoaderService
    ) {}
    resolve(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<any> | Promise<any> | any {
        if (route.params.id && route.params.id !== 'new') {
            return this.participantsService
                .getParticipantDetail(route.params.id)
                .pipe(
                    catchError((err: HttpInternalErrorResponse) => {
                        if (err.status === 404) {
                            this.toastr.warning(
                                this.i18nService.translate(
                                    'PRELOADING.PARTICIPANT.TOASTR.NOT_FOUND.TEXT'
                                ),
                                this.i18nService.translate(
                                    'PRELOADING.PARTICIPANT.TOASTR.NOT_FOUND.TITLE'
                                )
                            );
                        }
                        this.router.navigate([
                            '/',
                            'admin',
                            'preloading',
                            route.parent.params.conferenceType,
                            'participants'
                        ]);
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
