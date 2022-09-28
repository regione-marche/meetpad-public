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
import { Delegate } from '../../features/preloading/features/delegate/models/delegate.model';
import { DelegateService } from '../../features/preloading/features/delegate/services/delegate.service';

@Injectable()
export class DelegateResolver implements Resolve<Delegate> {
    constructor(
        private router: Router,
        private delegateService: DelegateService,
        private i18nService: I18nService,
        private toastr: ToastrService,
        private loaderService: LoaderService
    ) {}

    resolve(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<any> | Promise<any> | any {
        if (route.params.id) {
            return this.delegateService
                .getDelegateDetail(route.params.id)
                .pipe(
                    catchError((err: HttpInternalErrorResponse) => {
                        if (err.status === 404) {
                            this.toastr.warning(
                                this.i18nService.translate(
                                    'PRELOADING.DELEGATE.TOASTR.NOT_FOUND.TEXT'
                                ),
                                this.i18nService.translate(
                                    'PRELOADING.DELEGATE.TOASTR.NOT_FOUND.TITLE'
                                )
                            );
                        }
                        this.router.navigate([
                            '/',
                            'admin',
                            'preloading',
                            route.parent.params.conferenceType,
                            'delegates'
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
