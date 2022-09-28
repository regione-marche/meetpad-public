import { Injectable } from '@angular/core';

import { ToastrService } from 'ngx-toastr';
import { I18nService } from '@eng-ds/ng-core';
import { HttpResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import {
    WrapperResponse,
    HttpInternalErrorResponse
} from '../../interfaces/wrapers-http-response.interface';

@Injectable()
export class MessageToastrService {
    constructor(
        private toastr: ToastrService,
        private i18nService: I18nService
    ) {}

    showMessage(
        response: WrapperResponse<any>,
        entity: string,
        action: string
    ): void {
        const addResponse = response as any;
        entity = entity.concat('.TOASTR.', action.toUpperCase());

        if (addResponse.id) {
            entity = entity.concat('.SUCCESS.');
            this.toastr.info(
                this.i18nService.translate(entity.concat('TEXT')),
                this.i18nService.translate(entity.concat('TITLE'))
            );
        }
        if (response.code) {
            if (response.code === '200') {
                entity = entity.concat('.SUCCESS.');
                this.toastr.info(
                    this.i18nService.translate(entity.concat('TEXT')),
                    this.i18nService.translate(entity.concat('TITLE'))
                );
            } else {
                if (response.code === '403.3') {
                    entity = entity.concat('.FAIL.');
                    this.toastr.error(
                        this.i18nService.translate(entity.concat('TEXT')),
                        this.i18nService.translate(entity.concat('TITLE'))
                    );
                } else {
                    this.toastr.error(
                        this.i18nService.translate('TOASTR.MAINTENANCE.TEXT'),
                        this.i18nService.translate('TOASTR.MAINTENANCE.TITLE')
                    );
                }
            }
        }
    }

    showErrorMessage(
        error: HttpInternalErrorResponse | HttpResponse<any>,
        entity: string,
        action: string
    ): Observable<null> {
        entity = entity.concat('.TOASTR.', action.toUpperCase(), '.FAIL.');

        if (error.body && error.body.code === '403.3') {
            this.toastr.error(
                this.i18nService.translate(entity.concat('TEXT')),
                this.i18nService.translate(entity.concat('TITLE'))
            );
        } else {
            this.toastr.error(
                this.i18nService.translate('TOASTR.MAINTENANCE.TEXT'),
                this.i18nService.translate('TOASTR.MAINTENANCE.TITLE')
            );
        }
        return throwError(error);
    }
}
