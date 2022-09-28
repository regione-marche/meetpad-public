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
import { Observable } from 'rxjs';
import { ErrorMessage, ErrorLabelConstants } from '@eng-ds/ng-toolkit';
import { ActivatedRoute, Router } from '@angular/router';
import { UtilityService } from '../../../../../../core/services/utility.service';
import { takeUntil, catchError } from 'rxjs/operators';
import { FormControl, Validators } from '@angular/forms';
import { Applicant } from '../../models/applicant.model';
import { ApplicantService } from '../../services/applicant.service';
import { HttpResponse } from '@angular/common/http';

@Component({
    // tslint:disable-next-line: component-selector
    selector: 'admin-applicant',
    templateUrl: './applicant.component.html',
    styleUrls: ['./applicant.component.scss']
})
export class ApplicantComponent extends AutoUnsubscribe implements OnInit {
    createMode: boolean = true;
    conferenceType: string;
    onNext: (data: Applicant) => Observable<WrapperPostPutData>;
    saveComplete: (response: WrapperPostPutData) => void;
    saveError: (error: ErrorMessage) => void;
    groupFields: Map<string, FormFieldGroup> = new Map();
    model: Applicant = new Applicant();
    competenceAuthorization: ComboBox[];
    constructor(
        private route: ActivatedRoute,
        private loaderService: LoaderService,
        private applicantService: ApplicantService,
        private utilityService: UtilityService,
        private messageToastr: MessageToastrService,
        private router: Router
    ) {
        super();
    }
    ngOnInit() {
        if (this.route.snapshot.data.applicant) {
            const a = this.route.snapshot.data.applicant;
            this.createMode = false;
            this.model = new Applicant(Applicant.fromDto(a));
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
        this.groupFields.set('applicant', {
            panel: true,
            panelHead: 'PRELOADING.APPLICANT.EDIT.TITLE',
            accordion: false,
            fields: new Map()
                .set('applicant-preload', {
                    type: 'autocomplete',
                    label: 'PRELOADING.APPLICANT.EDIT.SEARCH',
                    size: '12|12|12',
                    control: new FormControl(),
                    notFoundText:
                        'CONFERENCE.AUTOCOMPLETE.INSERT_MIN_CHARACTERS',
                    loading: false,
                    valueChange: (applicant: ComboBox) => {
                        if (applicant && applicant.key) {
                            return this.applicantService
                                .getApplicantPrecomplete(applicant.key)
                                .pipe(takeUntil(this.destroy$))
                                .subscribe((value: Applicant) => {
                                    const fields = this.groupFields.get(
                                        'applicant'
                                    ).fields;
                                    const _applicant = Applicant.fromDto(value);
                                    fields
                                        .get('name')
                                        .control.setValue(_applicant.name);

                                    fields
                                        .get('fiscalCode')
                                        .control.setValue(
                                            _applicant.fiscalCode
                                        );

                                    fields
                                        .get('surname')
                                        .control.setValue(_applicant.surname);

                                    fields
                                        .get('email')
                                        .control.setValue(_applicant.email);
                                });
                        }
                    }
                })
                .set('name', {
                    type: 'text',
                    label: 'PRELOADING.APPLICANT.EDIT.NAME',
                    control: new FormControl(this.model.name, [
                        Validators.required
                    ]),
                    size: '12|6|6'
                })
                .set('surname', {
                    type: 'text',
                    label: 'PRELOADING.APPLICANT.EDIT.SURNAME',

                    control: new FormControl(this.model.surname, [
                        Validators.required
                    ]),
                    size: '12|6|6'
                })
                .set('email', {
                    type: 'text',
                    label: 'PRELOADING.APPLICANT.EDIT.EMAIL',
                    control: new FormControl(this.model.email, [
                        Validators.required
                    ]),
                    size: '12|6|6'
                })
                .set('fiscalCode', {
                    type: 'text',
                    label: 'PRELOADING.APPLICANT.EDIT.FISCAL_CODE',
                    pattern:
                        '(^[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]$)|(^[0-9]{11}$)',
                    control: new FormControl(this.model.fiscalCode, [
                        Validators.required
                    ]),
                    errorLabels: [ErrorLabelConstants.REGEX('ERROR.REGEX')],
                    size: '12|6|6'
                })
                .set('company', {
                    type: 'autocomplete',
                    label: 'PRELOADING.APPLICANT.EDIT.COMPANY',
                    size: '12|12|12',
                    control: new FormControl(this.model.company),
                    notFoundText:
                        'CONFERENCE.AUTOCOMPLETE.INSERT_MIN_CHARACTERS',
                    loading: false,
                    valueChange: (company: ComboBox) => {
                        if (company && company.key) {
                            this.model.company = company;
                        }
                    }
                })
        });

        this._initCompanyAutocomplete();

        if (this.createMode) {
            this._initApplicantAutocomplete();
        } else {
            this.groupFields
                .get('applicant')
                .fields.delete('applicant-preload');
        }

        this.groupFields = new Map(this.groupFields);
    }

    private _initCallbacks(): void {
        this.onNext = this._onNext.bind(this);
        this.saveComplete = this._saveComplete.bind(this);
        this.saveError = () => {};
        this.loaderService.hideLoading(SectionLoading.ALL_CONTENT, 500);
    }

    private _initApplicantAutocomplete(): void {
        this._initFieldFormAutocomplete(
            this.groupFields.get('applicant').fields.get('applicant-preload'),
            this.utilityService.getApplicantAutocomplete.bind(
                this.utilityService
            )
        );
    }

    private _initCompanyAutocomplete(): void {
        this.utilityService.setConferenceType(this.conferenceType);
        this._initFieldFormAutocomplete(
            this.groupFields.get('applicant').fields.get('company'),
            this.utilityService.getCompanyAutocompleteForApplicant.bind(
                this.utilityService
            )
        );
    }

    private _saveComplete(response: WrapperResponse<any>): void {
        if (this.createMode) {
            this.router.navigate(['../', response['id']], {
                relativeTo: this.route
            });
            this.messageToastr.showMessage(
                response,
                'PRELOADING.APPLICANT.EDIT',
                'NEW'
            );
        } else {
            this.messageToastr.showMessage(
                response,
                'PRELOADING.APPLICANT.EDIT',
                'EDIT'
            );
        }
    }

    private _saveError(error: HttpResponse<WrapperResponse>): Observable<null> {
        if (this.createMode) {
            return this.messageToastr.showErrorMessage(
                error,
                'PRELOADING.APPLICANT.EDIT',
                'NEW'
            );
        } else {
            return this.messageToastr.showErrorMessage(
                error,
                'PRELOADING.APPLICANT.EDIT',
                'EDIT'
            );
        }
    }

    private _onNext(data: Applicant): Observable<WrapperPostPutData> {
        data.conferenceType = this.model.conferenceType;
        if (this.createMode) {
            return this.applicantService
                .createApplicant(data, this.conferenceType)
                .pipe(
                    catchError((response: HttpResponse<WrapperResponse>) => {
                        return this._saveError(response);
                    })
                );
        } else {
            return this.applicantService.edit(data, this.model.id).pipe(
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
