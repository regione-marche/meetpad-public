import { Injectable } from '@angular/core';
import {
    CanActivate,
    ActivatedRouteSnapshot,
    RouterStateSnapshot,
    Router
} from '@angular/router';

import { Observable, of } from 'rxjs';
import { mergeMap, tap, catchError } from 'rxjs/operators';

import { ToastrService } from 'ngx-toastr';

import { I18nService } from '@eng-ds/ng-core';

import {
    ApplicationRole,
    User,
    ConferenceRole,
    AppSections,
    HttpInternalErrorResponse
} from '@common';

import {
    ConferenceState,
    appRoleAuthMappingConfig,
    UserPortalService
} from '@app/core';

import {
    ConferenceStoreService,
    conferenceRoleAuthMappingConfig
} from '../../../core';

@Injectable()
export class ConferenceDetailGuard implements CanActivate {
    redirectPath = ['/not-found'];

    constructor(
        public router: Router,
        private userService: UserPortalService,
        public conferenceStoreService: ConferenceStoreService,
        private toastr: ToastrService,
        private i18nService: I18nService
    ) {}

    canActivate(
        next: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<boolean> | Promise<boolean> | boolean {
        if (!next.params.id) {
            this.router.navigate(this.redirectPath);
            return false;
        }
        return this.conferenceStoreService
            .initByConferenceId(next.params.id)
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
                    return of(false);
                }),
                mergeMap((succ: boolean) => {
                    if (succ) {
                        this.conferenceStoreService._initConferencePermissions();
                        return this.userService
                            .getConferenceProfile(next.params.id)
                            .pipe(
                                // xmf todo: handle user rights in orer to allow external users
                                mergeMap((user: User) => {
                                    if (user) {
                                        return this._handlePermissionApply(
                                            conferenceRoleAuthMappingConfig,
                                            user.profile.key,
                                            this.conferenceStoreService
                                                .conference.state
                                                .key as ConferenceState
                                        );
                                    } else {
                                        return this.userService
                                            .getRole()
                                            .pipe(
                                                mergeMap(
                                                    (
                                                        userRole: ApplicationRole
                                                    ) =>
                                                        this._handlePermissionApply(
                                                            appRoleAuthMappingConfig,
                                                            userRole,
                                                            this
                                                                .conferenceStoreService
                                                                .conference
                                                                .state
                                                                .key as ConferenceState
                                                        )
                                                )
                                            );
                                    }
                                }),
                                tap((canActivate: boolean) => {
                                    if (!canActivate) {
                                        this.router.navigate(this.redirectPath);
                                    }
                                })
                            );
                    } else {
                        return of(false);
                    }
                })
            );
    }

    private _handlePermissionApply(
        _mappingRole: Map<any, any>,
        role: ConferenceRole | ApplicationRole,
        conferenceState: ConferenceState
    ): Observable<boolean> {
        const section = _mappingRole.get(AppSections.CONFERENCE_DETAIL_ROUTE);
        if (!section) { 
            return of(false);
        }
        const permission = section.get(role);
        if (permission) {
            const result = permission.apply(this, {
                conferenceState
            });
            if (result instanceof Observable) {
                return result;
            } else if (typeof result === 'boolean') {
                return of(result);
            }
            return of(true);
        } else {
            return of(false);
        }
    }

    canView(conferenceState: ConferenceState): boolean {
        return (
            conferenceState === ConferenceState.COMPILING ||
            conferenceState === ConferenceState.DRAFT ||
            conferenceState === ConferenceState.CLOSED ||
            conferenceState === ConferenceState.JUDGMENT
        );
    }
}
