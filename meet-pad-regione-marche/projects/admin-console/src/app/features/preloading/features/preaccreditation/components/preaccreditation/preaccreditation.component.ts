import { Component, OnInit, ViewChild } from '@angular/core';
import {
    AutoUnsubscribe,
    WrapperPostPutData,
    FormFieldGroup,
    ComboBox,
    LoaderService,
    SectionLoading,
    FormField,
    WrapperResponse,
    SelectValueValidator,
    MessageToastrService,
    FileService,
    BaseFile,
    FormButton,
    FooterButtons
} from '@common';
import { Observable, of, Subscription } from 'rxjs';
import { ErrorMessage, ErrorLabelConstants, ModalComponent, ActionItem } from '@eng-ds/ng-toolkit';
import { ActivatedRoute, Router } from '@angular/router';
import { FormControl, Validators } from '@angular/forms';
import { takeUntil, map, catchError, tap } from 'rxjs/operators';
import { Preaccreditation } from '../../models/preaccreditation.model';
import { UtilityService } from '../../../../../../core/services/utility.service';
import { HttpResponse } from '@angular/common/http';
import { PreaccreditationService } from '../../services/preaccreditation.service';


@Component({
  selector: 'admin-preaccreditation',
  templateUrl: './preaccreditation.component.html',
  styleUrls: ['./preaccreditation.component.scss']
})
export class PreaccreditationComponent extends AutoUnsubscribe implements OnInit {

    @ViewChild('confirmUploadModal') confirmUploadModal: ModalComponent;
    @ViewChild('confirmModal') confirmModal: ModalComponent;

    createMode: boolean = true;
    conferenceType: string;
    onNext: (data: Preaccreditation) => Observable<WrapperPostPutData>;
    saveComplete: (response: WrapperPostPutData) => void;
    saveError: (error: ErrorMessage) => void;

    groupFields: Map<string, FormFieldGroup> = new Map();
    model: Preaccreditation = new Preaccreditation();
    competenceAuthorization: ComboBox[];
    profileAccreditation: Observable<ComboBox[] & any> = of([]);
    private _isFirstLoad = true;
    fileDownloadSubscription: Subscription;

    constructor(
        private route: ActivatedRoute,
        private loaderService: LoaderService,
        private preaccreditationService: PreaccreditationService,
        private utilityService: UtilityService,
        private messageToastr: MessageToastrService,
        public fileService: FileService,
        private router: Router
    ) {
        super();
    }

    ngOnInit() {
        if (this.route.snapshot.data.preaccreditation) {
            const a = this.route.snapshot.data.preaccreditation;
            this.createMode = false;
            this.model = new Preaccreditation(Preaccreditation.fromDto(a));
        }
        this.route.parent.params
            .pipe(takeUntil(this.destroy$))
            .subscribe(value => {
                this.conferenceType = value.conferenceType;
            });
        this.profileAccreditation = this.utilityService.getPreaccreditationPersonRoles();
        this._createForm();
        this._initCallbacks();
    }

    private _createForm(): void {
        const selectOptions = this.utilityService.getPreaccreditationPersonRoles();
        this.groupFields.set('preaccreditation', {
            panel: true,
            panelHead: 'PRELOADING.PREACCREDITATION.EDIT.TITLE',
            accordion: false,
            fields: new Map()
            .set('preaccreditation-preload', {
                type: 'autocomplete',
                label: 'PRELOADING.PREACCREDITATION.EDIT.SEARCH',
                size: '12|12|12',
                control: new FormControl(),
                notFoundText:
                    'CONFERENCE.AUTOCOMPLETE.INSERT_MIN_CHARACTERS',
                loading: false,
                valueChange: (preaccreditation: ComboBox) => {
                    if (preaccreditation && preaccreditation.key) {
                        return this.preaccreditationService.getPreaccreditationPrecomplete(preaccreditation.key)
                            .pipe(takeUntil(this.destroy$))
                            .subscribe((value: Preaccreditation) => {
                                const fields = this.groupFields.get(
                                    'preaccreditation'
                                ).fields;
                                const _preaccreditation = Preaccreditation.fromDto(value);
                                fields
                                    .get('name')
                                    .control.setValue(_preaccreditation.name);

                                fields
                                    .get('fiscalCode')
                                    .control.setValue(
                                        _preaccreditation.fiscalCode
                                    );

                                fields
                                    .get('surname')
                                    .control.setValue(_preaccreditation.surname);

                                fields
                                    .get('email')
                                    .control.setValue(_preaccreditation.email);
                                fields
                                    .get('pec')
                                    .control.setValue(_preaccreditation.pec);
                                
                            });
                    }
                }
            })
            .set('name', {
                type: 'text',
                label: 'PRELOADING.PREACCREDITATION.EDIT.NAME',
                control: new FormControl(this.model.name, [
                    Validators.required
                ]),
                size: '12|6|6'
            })
            .set('surname', {
                type: 'text',
                label: 'PRELOADING.PREACCREDITATION.EDIT.SURNAME',

                control: new FormControl(this.model.surname, [
                    Validators.required
                ]),
                size: '12|6|6'
            })
            .set('email', {
                type: 'text',
                label: 'PRELOADING.PREACCREDITATION.EDIT.EMAIL',
                control: new FormControl(this.model.email, [
                    Validators.required
                ]),
                size: '12|6|6'
            })
            .set('pec', {
                type: 'switch',
                label:
                'PRELOADING.PREACCREDITATION.EDIT.PEC',
                control: new FormControl({
                    value: this.model.pec,
                    disabled: false,
                }),
                size: '12|6|6'
            })
            .set('fiscalCode', {
                type: 'text',
                label: 'PRELOADING.PREACCREDITATION.EDIT.FISCAL_CODE',
                pattern:
                    '(^[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]$)|(^[0-9]{11}$)',
                control: new FormControl(this.model.fiscalCode, [
                    Validators.required
                ]),
                errorLabels: [ErrorLabelConstants.REGEX('ERROR.REGEX')],
                size: '12|6|6'
            })
           .set('authority', {
                placeholder:
                    'PRELOADING.PREACCREDITATION.EDIT.AUTHORITY_PLACEHOLDER',
                type: 'autocomplete',
                label: 'PRELOADING.PREACCREDITATION.EDIT.AUTHORITY',
                control: new FormControl(
                    {
                        value: this.model.authority,
                        disabled: !this.createMode
                    },
                    [Validators.required]
                ),
                required: true,
                size: '12|12|12',
                errorLabels: [ErrorLabelConstants.REQUIRED],
                notFoundText:
                    'CONFERENCE.AUTOCOMPLETE.INSERT_MIN_CHARACTERS',
                loading: false
            })
            .set('profileType', {
                type: 'select',
                label:'PRELOADING.PREACCREDITATION.EDIT.PROFILE',
                control: new FormControl(
                    {
                        value: this.model.profileType,
                        disabled: false
                    }
                ),
                required: true,
                size: '12|6|6',
                options: this.utilityService.getPreaccreditationPersonRoles(),
                errorLabels: [ErrorLabelConstants.REQUIRED]
            })
            .set('profileType', {
                type: 'select',
                label:'PRELOADING.PREACCREDITATION.EDIT.PROFILE',
                control: new FormControl(
                    {
                        value: this.model.profileType,
                        disabled: false
                    },
                     Validators.required
                ),
                size: '12|6|6',
                
                options: this.profileAccreditation.pipe(
                    
                    tap((c: ComboBox[]) => {
                        const field = this.groupFields
                            .get('preaccreditation')
                            .fields.get('profileType');
                        if(this._isFirstLoad && !field.control.value) {
                            this._isFirstLoad = false;
                            this.groupFields
                            .get('preaccreditation')
                            .fields.get('profileType').control.setValue(c[0]);
                        }
                    })),
            })
         });

               
        if (this.createMode) {
            this._initPreaccreditationAutocomplete();
        } else {
            this.groupFields
                .get('preaccreditation')
                .fields.delete('preaccreditation-preload');
        }
        this._initAuthorityAutomplete();
        this.groupFields = new Map(this.groupFields);
    }

    private _initCallbacks(): void {
        this.onNext = this._onNext.bind(this);
        this.saveComplete = this._saveComplete.bind(this);
        this.saveError = () => {};
        this.loaderService.hideLoading(SectionLoading.ALL_CONTENT, 500);
    }

    private _initPreaccreditationAutocomplete(): void {
        this._initFieldFormAutocomplete(
            this.groupFields.get('preaccreditation').fields.get('preaccreditation-preload'),
            this.utilityService.getPreaccreditationAutocomplete.bind(
                this.utilityService
            )
        );
    }

    private _initAuthorityAutomplete(
        participant: Preaccreditation = new Preaccreditation()
    ): void {
        const authorityField: FormField = this.groupFields
            .get('preaccreditation')
            .fields.get('authority');
        this._initFieldFormAutocomplete(
            authorityField,
            this.utilityService.getCompanyAutocomplete.bind(this.utilityService)
        );
        authorityField.options.pipe(
            takeUntil(this.destroy$),
            map((combo: ComboBox[]) => {
                return combo;
            })
        );
    }

    private _saveComplete(response: WrapperResponse<any>): void {
        if (this.createMode) {
            this.router.navigate(['../', response['id']], {
                relativeTo: this.route
            });

            this.messageToastr.showMessage(
                response,
                'PRELOADING.PREACCREDITATION.EDIT',
                'NEW'
            );
        } else {
            this.messageToastr.showMessage(
                response,
                'PRELOADING.PREACCREDITATION.EDIT',
                'EDIT'
            );
        }
    }

    private _saveError(error: HttpResponse<WrapperResponse>): Observable<null> {
        if (this.createMode) {
            return this.messageToastr.showErrorMessage(
                error,
                'PRELOADING.PREACCREDITATION.EDIT',
                'NEW'
            );
        } else {
            return this.messageToastr.showErrorMessage(
                error,
                'PRELOADING.PREACCREDITATION.EDIT',
                'EDIT'
            );
        }
    }

    private _onNext(data: Preaccreditation): Observable<WrapperPostPutData> {
        if (this.createMode) {
            return this.preaccreditationService
                .createPreaccreditation(data, this.conferenceType)
                .pipe(
                    catchError((response: HttpResponse<WrapperResponse>) => {
                        return this._saveError(response);
                    })
                );
        } else {
            data.conferenceType = this.model.conferenceType;
            return this.preaccreditationService
                .edit(data, this.conferenceType, this.model.id)
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
