import { Component, OnInit } from '@angular/core';
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
import { Participant } from '../../../participant/models/participant.model';
import { Observable, of } from 'rxjs';
import { ErrorMessage, ErrorLabelConstants } from '@eng-ds/ng-toolkit';
import { ActivatedRoute, Router } from '@angular/router';
import { UtilityService } from '../../../../../../core/services/utility.service';
import { takeUntil, catchError } from 'rxjs/operators';
import { FormControl, Validators } from '@angular/forms';
import { CompanyService } from '../../services/company.service';
import { Company } from '../../models/company.model';
import { HttpResponse } from '@angular/common/http';

@Component({
    // tslint:disable-next-line: component-selector
    selector: 'admin-company',
    templateUrl: './company.component.html',
    styleUrls: ['./company.component.scss']
})
export class CompanyComponent extends AutoUnsubscribe implements OnInit {
    createMode: boolean = true;
    conferenceType: number;
    onNext: (data: Participant) => Observable<WrapperPostPutData>;
    saveComplete: (response: WrapperPostPutData) => void;
    saveError: (error: ErrorMessage) => void;

    groupFields: Map<string, FormFieldGroup> = new Map();
    model: Company = new Company();
    competenceAuthorization: ComboBox[];

    constructor(
        private route: ActivatedRoute,
        private loaderService: LoaderService,
        private companyService: CompanyService,
        private utilityService: UtilityService,
        private messageToastr: MessageToastrService,
        private router: Router
    ) {
        super();
    }

    ngOnInit() {
        if (this.route.snapshot.data.company) {
            const c = this.route.snapshot.data.company;
            this.createMode = false;
            this.model = new Company(Company.fromDto(c));
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
        this.groupFields.set('company', {
            panel: true,
            panelHead: 'PRELOADING.COMPANY.EDIT.TITLE',
            accordion: false,
            fields: new Map()
                .set('company-preload', {
                    type: 'autocomplete',
                    label: 'PRELOADING.COMPANY.EDIT.SEARCH',
                    size: '12|12|12',
                    control: new FormControl(),

                    notFoundText:
                        'CONFERENCE.AUTOCOMPLETE.INSERT_MIN_CHARACTERS',
                    loading: false,
                    valueChange: (company: ComboBox) => {
                        if (company && company.key) {
                            return this.companyService
                                .getNewCompanyDetail(company.key)
                                .pipe(takeUntil(this.destroy$))
                                .subscribe((value: Company) => {
                                    const fields = this.groupFields.get(
                                        'company'
                                    ).fields;
                                    const _company = Company.fromDtoForNewCompany(
                                        value
                                    );
                                    fields
                                        .get('name')
                                        .control.setValue(_company.name);

                                    fields
                                        .get('fiscalCode')
                                        .control.setValue(_company.fiscalCode);

                                    fields
                                        .get('vatNumber')
                                        .control.setValue(_company.vatNumber);

                                    fields

                                        .get('legalForm')
                                        .control.setValue(_company.legalForm);

                                    fields
                                        .get('area')
                                        .control.setValue(_company.area);

                                    fields
                                        .get('province')
                                        .control.setValue(_company.province);

                                    fields
                                        .get('city')
                                        .control.setValue(_company.city);

                                    fields
                                        .get('address')
                                        .control.setValue(_company.address);
                                });
                        }
                    },
                    onClear: () => {}
                })
                .set('name', {
                    type: 'text',
                    label: 'PRELOADING.COMPANY.EDIT.DENOMINATION',
                    control: new FormControl(this.model.name, [
                        Validators.required
                    ]),
                    size: '12|6|6'
                })
                .set('fiscalCode', {
                    type: 'text',
                    label: 'PRELOADING.COMPANY.EDIT.FISCAL_CODE',
                    pattern:
                        '(^[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]$)|(^[0-9]{11}$)',
                    control: new FormControl(this.model.fiscalCode, []),
                    errorLabels: [ErrorLabelConstants.REGEX('ERROR.REGEX')],
                    size: '12|6|6'
                })
                .set('vatNumber', {
                    type: 'text',
                    label: 'PRELOADING.COMPANY.EDIT.VAT_NUMBER',
                    pattern: '^(IT)?[0-9]{11}$',
                    control: new FormControl(this.model.vatNumber, [
                        Validators.required
                    ]),
                    errorLabels: [ErrorLabelConstants.REGEX('ERROR.REGEX')],
                    size: '12|6|6'
                })
                .set('legalForm', {
                    type: 'select',
                    label: 'PRELOADING.COMPANY.EDIT.LEGAL_FORM',

                    control: new FormControl(this.model.legalForm),
                    size: '12|6|6',
                    options: this.utilityService.getLegalForms()
                })
                .set('area', {
                    type: 'autocomplete',
                    label: 'PRELOADING.COMPANY.EDIT.AREA',
                    control: new FormControl(
                        this.model.area
                        //    disabled: this.isReadonly()
                    ),
                    size: '12|6|6',
                    notFoundText:
                        'CONFERENCE.AUTOCOMPLETE.INSERT_MIN_CHARACTERS',
                    loading: false,
                    valueChange: (area: ComboBox) => {
                        if (area && area.key) {
                            this.groupFields
                                .get('company')
                                .fields.get('province')
                                .control.reset();

                            this.groupFields
                                .get('company')
                                .fields.get('city')
                                .control.reset();

                            this._initFieldFormAutocomplete(
                                this.groupFields
                                    .get('company')
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
                            .get('company')
                            .fields.get('province').options = of([]);

                        this.groupFields
                            .get('company')
                            .fields.get('province')
                            .control.reset();

                        this.groupFields
                            .get('company')
                            .fields.get('city').options = of([]);

                        this.groupFields
                            .get('company')
                            .fields.get('city')
                            .control.reset();
                    }
                })
                .set('province', {
                    type: 'autocomplete',
                    label: 'PRELOADING.COMPANY.EDIT.PROVINCE',
                    control: new FormControl(
                        this.model.province
                        // disabled: this.isReadonly()
                    ),
                    size: '12|6|6',
                    notFoundText:
                        'CONFERENCE.AUTOCOMPLETE.INSERT_MIN_CHARACTERS',
                    loading: false,
                    valueChange: (province: ComboBox) => {
                        if (province && province.key) {
                            this.groupFields
                                .get('company')
                                .fields.get('city')
                                .control.reset();

                            this._initFieldFormAutocomplete(
                                this.groupFields
                                    .get('company')
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
                            .get('company')
                            .fields.get('city').options = of([]);

                        this.groupFields
                            .get('company')
                            .fields.get('city')
                            .control.reset();
                    }
                })
                .set('city', {
                    type: 'autocomplete',
                    label: 'PRELOADING.COMPANY.EDIT.CITY',
                    control: new FormControl(
                        this.model.city
                        // disabled: this.isReadonly()
                    ),
                    size: '12|6|6',
                    notFoundText:
                        'CONFERENCE.AUTOCOMPLETE.INSERT_MIN_CHARACTERS',
                    loading: false
                })
                .set('address', {
                    type: 'text',
                    label: 'PRELOADING.COMPANY.EDIT.ADDRESS',
                    control: new FormControl(this.model.address),
                    size: '12|6|6'
                })
                .set('stepList', {
                    type: 'select-two',
                    label: 'PRELOADING.COMPANY.EDIT.STEP',
                    control: new FormControl(this.model.stepList),
                    options: of([
                        { key: '0', value: 'Pratica' },
                        { key: '1', value: 'Definizione' },
                        { key: '2', value: 'Partecipanti' },
                        { key: '3', value: 'Documentazione' }
                    ]),
                    size: '12|6|6'
                })
                .set('editableConferenceStep', {
                    type: 'switch',
                    label: 'PRELOADING.COMPANY.EDIT.EDITABLE',
                    control: new FormControl(this.model.editableConferenceStep),
                    size: '12|6|6'
                })
        });
        this._initLocalizationAutocomplete();
        if (this.createMode) {
            this._initCompanyAutocomplete();
        } else {
            this.groupFields.get('company').fields.delete('company-preload');
        }

        // workaround per eliminare ExpressionChangedAfterItHasBeenCheckedError
        setTimeout(() => {
            this.groupFields = new Map(this.groupFields);
        }, 0);
    }

    private _initCallbacks(): void {
        this.onNext = this._onNext.bind(this);
        this.saveComplete = this._saveComplete.bind(this);
        this.saveError = () => {};
        this.loaderService.hideLoading(SectionLoading.ALL_CONTENT, 500);
    }

    private _initCompanyAutocomplete(): void {
        this._initFieldFormAutocomplete(
            this.groupFields.get('company').fields.get('company-preload'),
            this.utilityService.getNewCompanyAutocomplete.bind(
                this.utilityService
            )
        );
    }

    private _initLocalizationAutocomplete(): void {
        this._initFieldFormAutocomplete(
            this.groupFields.get('company').fields.get('area'),
            this.utilityService.getAreasAutocomplete.bind(this.utilityService)
        );

        if (this.model.area && this.model.area.key) {
            this._initFieldFormAutocomplete(
                this.groupFields.get('company').fields.get('province'),
                this.utilityService.getProvinciesAutocomplete.bind(
                    this.utilityService,
                    this.model.area.key
                )
            );
        }

        if (this.model.province && this.model.province.key) {
            this._initFieldFormAutocomplete(
                this.groupFields.get('company').fields.get('city'),
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
                relativeTo: this.route
            });
            this.messageToastr.showMessage(
                response,
                'PRELOADING.COMPANY.EDIT',
                'NEW'
            );
        } else {
            this.messageToastr.showMessage(
                response,
                'PRELOADING.COMPANY.EDIT',
                'EDIT'
            );
        }
    }

    private _saveError(error: HttpResponse<WrapperResponse>): Observable<null> {
        if (this.createMode) {
            return this.messageToastr.showErrorMessage(
                error,
                'PRELOADING.COMPANY.EDIT',
                'NEW'
            );
        } else {
            return this.messageToastr.showErrorMessage(
                error,
                'PRELOADING.COMPANY.EDIT',
                'EDIT'
            );
        }
    }

    private _onNext(data: Company): Observable<WrapperPostPutData> {
        data.conferenceType = this.model.conferenceType;
        if (this.createMode) {
            return this.companyService
                .createCompany(data, this.conferenceType)
                .pipe(
                    catchError((response: HttpResponse<WrapperResponse>) => {
                        return this._saveError(response);
                    })
                );
        } else {
            return this.companyService.edit(data, this.model.id).pipe(
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
