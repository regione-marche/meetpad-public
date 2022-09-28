import { Component, OnInit } from '@angular/core';
import { Authority } from '../../../../core/models/authority.model';
import { AuthorityService } from '../../../../core/services/authority.service';
import {
    AutoUnsubscribe,
    WrapperPostPutData,
    FormFieldGroup,
    ComboBox,
    LoaderService,
    SectionLoading,
    WrapperResponse,
    MessageToastrService
} from '@common';
import { Observable, of } from 'rxjs';
import { ErrorMessage, ErrorLabelConstants } from '@eng-ds/ng-toolkit';
import { ActivatedRoute, Router } from '@angular/router';
import { takeUntil, catchError } from 'rxjs/operators';
import { FormControl, Validators } from '@angular/forms';
import { UtilityService } from '../../../../../../core/services/utility.service';
import { HttpResponse } from '@angular/common/http';

@Component({
    // tslint:disable-next-line: component-selector
    selector: 'admin-edit-authority',
    templateUrl: './authority.component.html',
    styleUrls: ['./authority.component.scss']
})
export class AuthorityComponent extends AutoUnsubscribe implements OnInit {
    createMode: boolean = true;
    conferenceType: number;
    onNext: (data: Authority) => Observable<WrapperPostPutData>;
    saveComplete: (response: WrapperPostPutData) => void;
    saveError: (error: ErrorMessage) => void;

    groupFields: Map<string, FormFieldGroup> = new Map();
    model: Authority = new Authority();
    competenceAuthorization: ComboBox[];
    istatType: ComboBox[];
    administrativeType: ComboBox[];

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
        if (this.route.snapshot.data.authority) {
            const a = this.route.snapshot.data.authority;
            this.createMode = false;
            this.model = new Authority(Authority.fromDto(a));
        }

        this.route.parent.params
            .pipe(takeUntil(this.destroy$))
            .subscribe(value => {
                this.conferenceType = value.conferenceType;
            });
        this._createForm();
        this._initCallbacks();
    }

    private _createForm(): void {
        this.groupFields.set('authority', {
            panel: true,
            panelHead: 'AUTHORITIES.EDIT.TITLE',
            accordion: false,
            fields: new Map()
                .set('name', {
                    type: 'text',
                    label: 'AUTHORITIES.EDIT.FORM.NAME',
                    control: new FormControl(this.model.name, [
                        Validators.required
                    ]),
                    size: '12|6|6'
                })
                .set('fiscalCode', {
                    type: 'text',
                    label: 'AUTHORITIES.EDIT.FORM.FISCAL_CODE',
                    pattern:
                        '(^[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]$)|(^[0-9]{11}$)',
                    control: new FormControl(this.model.fiscalCode, [
                        Validators.required
                    ]),
                    errorLabels: [ErrorLabelConstants.REGEX('ERROR.REGEX')],
                    size: '12|6|6'
                })
                .set('area', {
                    type: 'autocomplete',
                    label: 'AUTHORITIES.EDIT.FORM.AREA',
                    control: new FormControl(this.model.area, [
                        Validators.required
                    ]),
                    size: '12|6|6',
                    notFoundText:
                        'CONFERENCE.AUTOCOMPLETE.INSERT_MIN_CHARACTERS',
                    loading: false,
                    valueChange: (area: ComboBox) => {
                        if (area && area.key) {
                            this.groupFields
                                .get('authority')
                                .fields.get('province')
                                .control.reset();

                            this.groupFields
                                .get('authority')
                                .fields.get('city')
                                .control.reset();

                            this._initFieldFormAutocomplete(
                                this.groupFields
                                    .get('authority')
                                    .fields.get('province'),
                                this.utilityService.getProvinciesAutocomplete.bind(
                                    this.utilityService,
                                    area.key
                                )
                            );
                        }
                    },
                    onClear: () => {
                        this.groupFields
                            .get('authority')
                            .fields.get('province').options = of([]);

                        this.groupFields
                            .get('authority')
                            .fields.get('province')
                            .control.reset();

                        this.groupFields
                            .get('authority')
                            .fields.get('city').options = of([]);

                        this.groupFields
                            .get('authority')
                            .fields.get('city')
                            .control.reset();
                    }
                })
                .set('province', {
                    type: 'autocomplete',
                    label: 'AUTHORITIES.EDIT.FORM.PROVINCE',
                    control: new FormControl(this.model.province, [
                        Validators.required
                    ]),
                    size: '12|6|6',
                    notFoundText:
                        'CONFERENCE.AUTOCOMPLETE.INSERT_MIN_CHARACTERS',
                    loading: false,
                    valueChange: (province: ComboBox) => {
                        if (province && province.key) {
                            this.groupFields
                                .get('authority')
                                .fields.get('city')
                                .control.reset();

                            this._initFieldFormAutocomplete(
                                this.groupFields
                                    .get('authority')
                                    .fields.get('city'),
                                this.utilityService.getCitiesAutocomplete.bind(
                                    this.utilityService,
                                    province.key
                                )
                            );
                        }
                    },
                    onClear: () => {
                        this.groupFields
                            .get('authority')
                            .fields.get('city').options = of([]);

                        this.groupFields
                            .get('authority')
                            .fields.get('city')
                            .control.reset();
                    }
                })
                .set('city', {
                    type: 'autocomplete',
                    label: 'AUTHORITIES.EDIT.FORM.CITY',
                    control: new FormControl(this.model.city, [
                        Validators.required
                    ]),
                    size: '12|6|6',
                    notFoundText:
                        'CONFERENCE.AUTOCOMPLETE.INSERT_MIN_CHARACTERS',
                    loading: false
                })
                .set('pec', {
                    type: 'text',
                    pattern: '^[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,4}$',
                    label: 'AUTHORITIES.EDIT.FORM.PEC',
                    control: new FormControl(this.model.pec, [
                        Validators.required
                    ]),
                    size: '12|6|6',
                    errorLabels: [
                        ErrorLabelConstants.REQUIRED,
                        ErrorLabelConstants.REGEX('ERROR.EMAIL.INVALID')
                    ]
                })
                .set('officeCode', {
                    type: 'text',
                    label: 'AUTHORITIES.EDIT.FORM.OFFICE_CODE',
                    control: new FormControl(this.model.officeCode),
                    size: '12|6|6'
                })
                .set('istatType', {
                    type: 'select',
                    label: 'AUTHORITIES.EDIT.FORM.ISTAT_TYPE',
                    control: new FormControl(this.model.istatType),
                    size: '12|6|6',
                    options: this.utilityService.getIstatType()
                })
                .set('administrativeType', {
                    type: 'select',
                    label: 'AUTHORITIES.EDIT.FORM.ADMINISTRATIVE_TYPE',
                    control: new FormControl(this.model.administrativeType),
                    size: '12|6|6',
                    options: this.utilityService.getAdministrativeType()
                })
                .set('flagProsecutingAdministration', {
                    type: 'switch',
                    label: 'AUTHORITIES.EDIT.FORM.PROCEDING',
                    control: new FormControl(
                        this.model.flagProsecutingAdministration
                    ),
                    size: '12|12|12'
                })
        });
        this._initLocalizationAutocomplete();
    }

    private _initCallbacks(): void {
        this.onNext = this._onNext.bind(this);
        this.saveComplete = this._saveComplete.bind(this);
        this.saveError = () => {};
        this.loaderService.hideLoading(SectionLoading.ALL_CONTENT, 500);
    }

    private _initLocalizationAutocomplete(): void {
        this._initFieldFormAutocomplete(
            this.groupFields.get('authority').fields.get('area'),
            this.utilityService.getAreasAutocomplete.bind(this.utilityService)
        );

        if (this.model.area && this.model.area.key) {
            this._initFieldFormAutocomplete(
                this.groupFields.get('authority').fields.get('province'),
                this.utilityService.getProvinciesAutocomplete.bind(
                    this.utilityService,
                    this.model.area.key
                )
            );
        }

        if (this.model.province && this.model.province.key) {
            this._initFieldFormAutocomplete(
                this.groupFields.get('authority').fields.get('city'),
                this.utilityService.getCitiesAutocomplete.bind(
                    this.utilityService,
                    this.model.province.key
                )
            );
        }
    }

    private _saveComplete(response: WrapperResponse<any>): void {
        if (this.createMode) {
            this.router.navigate(['../', response['id']], {
                relativeTo: this.activatedRoute
            });
            this.messageToastr.showMessage(response, 'AUTHORITIES.EDIT', 'NEW');
        } else {
            this.messageToastr.showMessage(
                response,
                'AUTHORITIES.EDIT',
                'EDIT'
            );
        }
    }

    private _saveError(error: HttpResponse<WrapperResponse>): Observable<null> {
        if (this.createMode) {
            return this.messageToastr.showErrorMessage(
                error,
                'AUTHORITIES.EDIT',
                'NEW'
            );
        } else {
            return this.messageToastr.showErrorMessage(
                error,
                'AUTHORITIES.EDIT',
                'EDIT'
            );
        }
    }

    private _onNext(data: Authority): Observable<WrapperPostPutData> {
        if (this.createMode) {
            return this.authorityService.createAuthority(data).pipe(
                catchError((response: HttpResponse<WrapperResponse>) => {
                    return this._saveError(response);
                })
            );
        } else {
            return this.authorityService
                .editAuthority(data, this.model.id)
                .pipe(
                    catchError((response: HttpResponse<WrapperResponse>) => {
                        return this._saveError(response);
                    })
                );
        }
    }

    isReadonly(): boolean {
        return !this.createMode;
    }
}
