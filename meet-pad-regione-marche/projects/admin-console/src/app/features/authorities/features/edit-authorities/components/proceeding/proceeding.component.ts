import { Component, OnInit } from '@angular/core';
import {
    AutoUnsubscribe,
    LoaderService,
    SectionLoading,
    FormFieldGroup,
    WrapperPostPutData,
    WrapperResponse,
    HttpInternalErrorResponse,
    MessageToastrService
} from '@common';
import { ActivatedRoute } from '@angular/router';
import { FormControl } from '@angular/forms';
import { SearchAuthority } from '../../../../core/models/searchAuthority.model';
import { ErrorMessage, ErrorLabelConstants } from '@eng-ds/ng-toolkit';
import { Observable } from 'rxjs';
import { AuthorityService } from '../../../../core/services/authority.service';
import { HttpResponse } from '@angular/common/http';
import { catchError } from 'rxjs/operators';

@Component({
    // tslint:disable-next-line: component-selector
    selector: 'admin-proceeding-authorities',
    templateUrl: './proceeding.component.html',
    styleUrls: ['./proceeding.component.scss']
})
export class AuthoritiesProceedingComponent extends AutoUnsubscribe
    implements OnInit {
    onNext: (data: SearchAuthority) => Observable<WrapperPostPutData>;
    saveComplete: (response: WrapperPostPutData) => void;
    saveError: (error: ErrorMessage) => void;
    groupFields: Map<string, FormFieldGroup> = new Map();
    model: SearchAuthority = new SearchAuthority();
    constructor(
        private route: ActivatedRoute,
        private loaderService: LoaderService,
        private authorityService: AuthorityService,
        private messageToastr: MessageToastrService
    ) {
        super();
    }

    ngOnInit() {
        if (this.route.snapshot.data.authority) {
            this.model = new SearchAuthority(
                this.route.snapshot.data.authority
            );
        }
        this._createForm();

        this._initCallbacks();
        this.loaderService.hideLoading(SectionLoading.ALL_CONTENT, 500);
    }

    private _createForm(): void {
        this.groupFields.set('authority', {
            panel: true,
            panelHead: 'AUTHORITIES.PROCEDING.EDIT.PAGE.TITLE',
            accordion: false,
            fields: new Map()
                .set('name', {
                    type: 'text',
                    label: 'AUTHORITIES.PROCEDING.EDIT.PAGE.NAME',
                    control: new FormControl({
                        value: this.model.name,
                        disabled: true
                    }),
                    size: '12|12|12'
                })
                .set('pec', {
                    type: 'text',
                    pattern: '^[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,4}$',
                    label: 'AUTHORITIES.PROCEDING.EDIT.PAGE.PEC',
                    control: new FormControl({
                        value: this.model.pec,
                        disabled: false
                    }),
                    size: '12|12|12',
                    errorLabels: [
                        ErrorLabelConstants.REQUIRED,
                        ErrorLabelConstants.REGEX('ERROR.EMAIL.INVALID')
                    ]
                })
                .set('proceding', {
                    type: 'switch',
                    label: 'AUTHORITIES.PROCEDING.EDIT.PAGE.PROCEDING',
                    control: new FormControl(
                        this.model.flagProsecutingAdministration
                    ),
                    size: '12|12|12'
                })
        });
    }
    private _initCallbacks(): void {
        this.onNext = this._onNext.bind(this);
        this.saveComplete = this._saveComplete.bind(this);
        this.saveError = () => {};
    }

    private _onNext(data: SearchAuthority): Observable<WrapperPostPutData> {
        return this.authorityService
            .edit(data, this.route.snapshot.params.id)
            .pipe(
                catchError((response: HttpResponse<WrapperResponse>) => {
                    return this._saveError(response);
                })
            );
    }

    private _saveComplete(response: WrapperResponse<any>): void {
        this.messageToastr.showMessage(
            response,
            'AUTHORITIES.PROCEDING.EDIT',
            'EDIT'
        );
    }

    private _saveError(error: HttpResponse<WrapperResponse>): Observable<null> {
        return this.messageToastr.showErrorMessage(
            error,
            'AUTHORITIES.PROCEDING.EDIT',
            'EDIT'
        );
    }
}
