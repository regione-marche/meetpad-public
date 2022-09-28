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
import { Authority } from '../../features/authorities/core/models/authority.model';
import { AuthorityService } from '../../features/authorities/core/services/authority.service';

@Injectable()
export class AuthorityResolver implements Resolve<Authority> {
    constructor(
        private router: Router,
        private authorityService: AuthorityService,
        private i18nService: I18nService,
        private toastr: ToastrService,
        private loaderService: LoaderService
    ) {}
    resolve(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<any> | Promise<any> | any {
        if (route.params.id) {
            return this.authorityService
                .getAuthorityDetail(route.params.id)
                .pipe(
                    catchError((err: HttpInternalErrorResponse) => {
                        if (err.status === 404) {
                            this.toastr.warning(
                                this.i18nService.translate(
                                    'AUTHORITIES.EDIT.TOASTR.NOT_FOUND.TEXT'
                                ),
                                this.i18nService.translate(
                                    'AUTHORITIES.EDIT.TOASTR.NOT_FOUND.TITLE'
                                )
                            );
                        }
                        this.router.navigate(['/admin/authorities']);

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
