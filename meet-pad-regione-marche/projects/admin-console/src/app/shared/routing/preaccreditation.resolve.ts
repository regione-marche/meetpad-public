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
import { Preaccreditation } from '../../features/preloading/features/preaccreditation/models/preaccreditation.model';
import { PreaccreditationService } from '../../features/preloading/features/preaccreditation/services/preaccreditation.service';


@Injectable()
export class PreaccreditationResolver implements Resolve<Preaccreditation> {
    constructor(
        private router: Router,
        private preaccreditationService: PreaccreditationService,
        private i18nService: I18nService,
        private toastr: ToastrService,
        private loaderService: LoaderService
    ) {}

    resolve(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<any> | Promise<any> | any {
        if (route.params.id) {
            return this.preaccreditationService
                .getPreaccreditationDetail(route.params.id)
                .pipe(
                    catchError((err: HttpInternalErrorResponse) => {
                        if (err.status === 404) {
                            this.toastr.warning(
                                this.i18nService.translate(
                                    'PRELOADING.PREACCREDITATION.TOASTR.NOT_FOUND.TEXT'
                                ),
                                this.i18nService.translate(
                                    'PRELOADING.PREACCREDITATION.TOASTR.NOT_FOUND.TITLE'
                                )
                            );
                        }
                        this.router.navigate([
                            '/',
                            'admin',
                            'preloading',
                            route.parent.params.conferenceType,
                            'preaccreditation'
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
