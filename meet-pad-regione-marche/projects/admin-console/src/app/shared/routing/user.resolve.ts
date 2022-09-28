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
import { User } from '../../features/users/core/models/user.model';
import { UsersService } from '../../features/users/core/services/users.service';

@Injectable()
export class UserResolver implements Resolve<User> {
    constructor(
        private router: Router,
        private usersService: UsersService,
        private i18nService: I18nService,
        private toastr: ToastrService,
        private loaderService: LoaderService
    ) {}
    resolve(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<any> | Promise<any> | any {
        if (route.params.id && route.params.id !== 'new') {
            return this.usersService.getUserDetail(route.params.id).pipe(
                catchError((err: HttpInternalErrorResponse) => {
                    if (err.status === 404) {
                        this.toastr.warning(
                            this.i18nService.translate(
                                'USERS.TOASTR.NOT_FOUND.TEXT'
                            ),
                            this.i18nService.translate(
                                'USERS.TOASTR.NOT_FOUND.TITLE'
                            )
                        );
                    }
                    this.router.navigate(['/', 'admin', 'users']);
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
