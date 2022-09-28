import { Component, OnInit } from '@angular/core';
import {
    AutoUnsubscribe,
    WrapperPostPutData,
    FormFieldGroup,
    LoaderService,
    SectionLoading,
    FormField,
    ComboBox,
    WrapperResponse,
    HttpInternalErrorResponse,
    MessageToastrService
} from '@common';
import { Manager } from '../../../../core/models/manager.model';
import { Observable, of } from 'rxjs';
import { ErrorMessage, ErrorLabelConstants } from '@eng-ds/ng-toolkit';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthorityService } from '../../../../core/services/authority.service';
import { FormControl, Validators } from '@angular/forms';
import { UtilityService } from '../../../../../../core/services/utility.service';
import { takeUntil, map, catchError } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';

@Component({
    // tslint:disable-next-line: component-selector
    selector: 'admin-manager',
    templateUrl: './manager.component.html',
    styleUrls: ['./manager.component.scss']
})
export class ManagerComponent extends AutoUnsubscribe implements OnInit {
    createMode: boolean = true;

    onNext: (data: Manager) => Observable<WrapperPostPutData>;
    saveComplete: (response: WrapperPostPutData) => void;
    saveError: (error: ErrorMessage) => void;
    groupFields: Map<string, FormFieldGroup> = new Map();
    model: Manager = new Manager();
    constructor(
        private route: ActivatedRoute,
        private loaderService: LoaderService,
        private authorityService: AuthorityService,
        private utilityService: UtilityService,
        private messageToastr: MessageToastrService,
        private router: Router,
        private activatedRoute: ActivatedRoute
    ) {
        super();
    }

    ngOnInit() {
        this._createForm();
        if (this.route.snapshot.data.manager) {
            this.createMode = false;
            this.model = new Manager(this.route.snapshot.data.manager);
        }
        this._initCallbacks();
        this.loaderService.hideLoading(SectionLoading.ALL_CONTENT, 500);
    }

    private _createForm(): void {
        this.groupFields.set('manager', {
            panel: true,
            panelHead: 'AUTHORITIES.PROCEDING.MANAGER.EDIT.TITLE',
            accordion: false,
            fields: new Map()
                .set('name', {
                    type: 'text',
                    label: 'AUTHORITIES.PROCEDING.MANAGER.EDIT.NAME',
                    control: new FormControl(
                        {
                            value: this.model.name,
                            disabled: this.isReadonly()
                        },
                        [Validators.required]
                    ),
                    size: '12|12|12'
                })
                .set('surname', {
                    type: 'text',
                    label: 'AUTHORITIES.PROCEDING.MANAGER.EDIT.SURNAME',
                    control: new FormControl(
                        {
                            value: this.model.name,
                            disabled: this.isReadonly()
                        },
                        [Validators.required]
                    ),
                    size: '12|12|12'
                })
                .set('pec', {
                    type: 'text',
                    pattern: '^[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,4}$',
                    label: 'AUTHORITIES.PROCEDING.MANAGER.EDIT.PEC',
                    control: new FormControl(
                        {
                            value: this.model.pec,
                            disabled: false
                        },
                        [Validators.required]
                    ),
                    size: '12|12|12',
                    errorLabels: [
                        ErrorLabelConstants.REQUIRED,
                        ErrorLabelConstants.REGEX('ERROR.EMAIL.INVALID')
                    ]
                })
                .set('fiscalCode', {
                    type: 'text',
                    pattern:
                        '^[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]$',
                    label: 'AUTHORITIES.PROCEDING.MANAGER.EDIT.FISCAL_CODE',
                    control: new FormControl(
                        {
                            value: this.model.fiscalCode,
                            disabled: this.isReadonly()
                        },
                        [Validators.required]
                    ),
                    errorLabels: [ErrorLabelConstants.REGEX('ERROR.REGEX')],
                    options: of([]),
                    size: '12|12|12'
                })
                .set('prosecutingAdministration', {
                    placeholder: 'AUTHORITIES.PROCEDING.MANAGER.EDIT.AUTHORITY',
                    type: 'autocomplete',
                    label: 'AUTHORITIES.PROCEDING.MANAGER.EDIT.AUTHORITY',
                    control: new FormControl(
                        {
                            value: this.model.prosecutingAdministration,
                            disabled: this.isReadonly()
                        },
                        [Validators.required]
                    ),
                    disabled: this.isReadonly(),
                    required: true,
                    size: '12|12|12',
                    errorLabels: [ErrorLabelConstants.REQUIRED],
                    notFoundText:
                        'AUTHORITIES.PROCEDING.MANAGER.EDIT.AUTHORITY',
                    loading: false
                })
        });
        this._initAuthorityAutomplete();
    }

    private _initCallbacks(): void {
        this.onNext = this._onNext.bind(this);
        this.saveComplete = this._saveComplete.bind(this);
        this.saveError = () => {};
    }

    private _initAuthorityAutomplete(
        participant: Manager = new Manager()
    ): void {
        const authorityField: FormField = this.groupFields
            .get('manager')
            .fields.get('prosecutingAdministration');
        this._initFieldFormAutocomplete(
            authorityField,
            this.utilityService.getCompanyAutocomplete.bind(this.utilityService)
        );
        authorityField.options.pipe(
            takeUntil(this.destroy$),
            map((combo: ComboBox[]) => {
                // controlla la presenza di partecipanti
                // elimina dalla tendina quelli già presenti
                return combo;
            })
        );
    }

    private _saveComplete(response: WrapperResponse<any>): void {
        this.router.navigate(['../'], {
            relativeTo: this.activatedRoute
        });
        this.messageToastr.showMessage(
            response,
            'AUTHORITIES.PROCEDING.MANAGER.EDIT',
            'NEW'
        );
    }

    private _saveError(error: HttpResponse<WrapperResponse>): Observable<null> {
        return this.messageToastr.showErrorMessage(
            error,
            'AUTHORITIES.PROCEDING.MANAGER.EDIT',
            'NEW'
        );
    }

    private _onNext(data: Manager): Observable<WrapperPostPutData> {
        return this.authorityService.createManager(data).pipe(
            catchError((response: HttpResponse<WrapperResponse>) => {
                return this._saveError(response);
            })
        );
    }

    isReadonly(): boolean {
        return !this.createMode;
    }
}
