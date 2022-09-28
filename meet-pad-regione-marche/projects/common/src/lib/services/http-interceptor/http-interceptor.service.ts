import { Injectable } from '@angular/core';
import {
    HttpInterceptor,
    HttpRequest,
    HttpHandler,
    HttpResponse,
    HttpEvent,
    HttpErrorResponse
} from '@angular/common/http';

import { Observable, throwError, of } from 'rxjs';
import { switchMap, catchError } from 'rxjs/operators';

import { UserService } from '../user/user.service';
import { ToastrService } from 'ngx-toastr';
import { I18nService } from '@eng-ds/ng-core';

@Injectable()
export class HttpInterceptorService implements HttpInterceptor {
    constructor(private userService: UserService, private toastr: ToastrService, private i18nService: I18nService,) {}

    intercept(
        req: HttpRequest<any>,
        next: HttpHandler
    ): Observable<HttpEvent<any>> {
        return next.handle(req).pipe(
            switchMap((event: HttpEvent<any>) => this._mapResponse(event)),
            catchError((event: HttpEvent<any>) => this._handleError(event))
        );
    }

    private _mapResponse(event: HttpEvent<any>): Observable<HttpEvent<any>> {
        if (event instanceof HttpResponse) {
            if (!(event.body instanceof Blob) && event.body.code != 200) {
                return throwError(
                    event.clone({ status: parseInt(event.body.code, 10) })
                );
            }
            event = event.clone({ body: event.body.data });
        }
        return of(event);
    }

    private _handleError(event: HttpEvent<any>) {
        if (event instanceof HttpErrorResponse) {
            switch (event.status) {
                case 401:
                    this.userService.unauthenticated$.next();
                    break;
                case 500:
                    this.toastr.error(
                        event.error.msg,
                        this.i18nService.translate('TOASTR.MAINTENANCE.ID'),
                        {
                            disableTimeOut: true,
                            closeButton: true,
                            timeOut: 0,
                            extendedTimeOut: 0,
                            tapToDismiss: false
                        }
                    );
                    this.userService.systemMaintenance$.next();
                    break;
                case 404:
                    this.userService.systemMaintenance$.next();
                    break;
            }
        }
        return throwError(event);
    }
}
