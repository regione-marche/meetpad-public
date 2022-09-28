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
import { Company } from '../../features/preloading/features/company/models/company.model';
import { CompanyService } from '../../features/preloading/features/company/services/company.service';

@Injectable()
export class CompanyResolver implements Resolve<Company> {
    constructor(
        private router: Router,
        private companyService: CompanyService,
        private i18nService: I18nService,
        private toastr: ToastrService,
        private loaderService: LoaderService
    ) {}
    resolve(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<any> | Promise<any> | any {
        if (route.params.id && route.params.id !== 'new') {
            return this.companyService.getCompanyDetail(route.params.id).pipe(
                catchError((err: HttpInternalErrorResponse) => {
                    if (err.status === 404) {
                        this.toastr.warning(
                            this.i18nService.translate(
                                'PRELOADING.COMPANY.TOASTR.NOT_FOUND.TEXT'
                            ),
                            this.i18nService.translate(
                                'PRELOADING.COMPANY.TOASTR.NOT_FOUND.TITLE'
                            )
                        );
                    }
                    this.router.navigate([
                        '/',
                        'admin',
                        'preloading',
                        route.parent.params.conferenceType,
                        'companies'
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
