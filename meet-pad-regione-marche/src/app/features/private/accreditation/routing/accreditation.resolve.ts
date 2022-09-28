import { Injectable } from '@angular/core';
import {
    Resolve,
    ActivatedRouteSnapshot,
    RouterStateSnapshot,
    Router
} from '@angular/router';

import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { ToastrService } from 'ngx-toastr';

import { I18nService } from '@eng-ds/ng-core';

import {
    LoaderService,
    SectionLoading,
    AuthService,
    HttpInternalErrorResponse
} from '@common';

import { AccreditationService } from '@features/private/core/services/accreditation/accreditation.service';
import { AccreditamentInfo } from '@features/private/conference/core';

@Injectable()
export class AccreditationResolver implements Resolve<AccreditamentInfo> {
    constructor(
        private accreditationService: AccreditationService,
        private loaderService: LoaderService,
        private router: Router,
        private authService: AuthService,
        private toastrService: ToastrService,
        private i18nService: I18nService
    ) {}

    resolve(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<any> | Promise<any> | any {
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
        return this.accreditationService
            .get(route.params.token_1, route.params.token_2)
            .pipe(
                catchError((err: HttpInternalErrorResponse) => {
                    // link scaduto (termine prima sessione simultanea scaduto)
                    if (err.status === 410) {
                        this.toastrService.warning(
                            this.i18nService.translate(
                                'CONFERENCE.JOIN.TOASTR.EXPIRED.TEXT'
                            ),
                            this.i18nService.translate(
                                'CONFERENCE.JOIN.TOASTR.EXPIRED.TITLE'
                            )
                        );
                        this.router.navigate(['/']);
                        return of(false);
                    }
                    this.authService.logout();
                    return of(false);
                })
            );
    }
}
