import { Component, OnInit } from '@angular/core';
import { User } from '../../../core/models/user.model';
import { ToastrService } from 'ngx-toastr';
import {
    AutoUnsubscribe,
    WrapperPostPutData,
    FormFieldGroup,
    LoaderService,
    SectionLoading,
    FormField,
    ComboBox,
    WrapperResponse,
    SelectValueValidator,
    MessageToastrService
} from '@common';
import { Observable, of } from 'rxjs';
import { ErrorMessage, ErrorLabelConstants } from '@eng-ds/ng-toolkit';
import { ActivatedRoute, Router } from '@angular/router';
import { UtilityService } from '../../../../../core/services/utility.service';
import { FormControl, Validators } from '@angular/forms';
import { takeUntil, map, catchError } from 'rxjs/operators';
import { UsersService } from '../../../core/services/users.service';
import { HttpResponse } from '@angular/common/http';
import { getBodyNode } from '@angular/animations/browser/src/render/shared';

@Component({
    // tslint:disable-next-line: component-selector
    selector: 'admin-users',
    templateUrl: './users.component.html',
    styleUrls: ['./users.component.scss']
})
export class UsersComponent extends AutoUnsubscribe implements OnInit {
    createMode: boolean = true;

    onNext: (data: User) => Observable<WrapperPostPutData>;
    saveComplete: (response: WrapperPostPutData) => void;
    saveError: (error: ErrorMessage) => void;

    groupFields: Map<string, FormFieldGroup> = new Map();
    model: User = new User();
    profile: ComboBox[] = [{ key: '-1', value: 'Seleziona profilo' }];
    
    managersToImpersonateData: Observable<ComboBox[] & any> = this.utilityService.getAllConferenzaCreatorUsers();

    constructor(
        private route: ActivatedRoute,
        private loaderService: LoaderService,
        private usersService: UsersService,
        private utilityService: UtilityService,
        private messageToastr: MessageToastrService,
        private router: Router,
        private toastr: ToastrService,
        private activatedRoute: ActivatedRoute
    ) {
        super();
    }

    ngOnInit() {
        if (this.route.snapshot.data.user) {
            this.createMode = false;
            this.model = new User(User.fromDto(this.route.snapshot.data.user));
        }
        this._createForm();

        this._initCallbacks();
        this.loaderService.hideLoading(SectionLoading.ALL_CONTENT, 500);
    }

    private _createForm(): void {
        this.groupFields.set('user', {
            panel: true,
            panelHead: 'USERS.EDIT.TITLE',
            accordion: false,
            fields: new Map()
                .set('name', {
                    type: 'text',
                    label: 'USERS.EDIT.NAME',
                    control: new FormControl(this.model.name, [
                        Validators.required
                    ]),
                    size: '12|12|12'
                })
                .set('surname', {
                    type: 'text',
                    label: 'USERS.EDIT.SURNAME',
                    control: new FormControl(this.model.surname, [
                        Validators.required
                    ]),
                    size: '12|12|12'
                })
                .set('email', {
                    type: 'text',
                    pattern: '^[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,4}$',
                    label: 'USERS.EDIT.PEC',
                    control: new FormControl(this.model.email, [
                        Validators.required
                    ]),
                    errorLabels: [
                        ErrorLabelConstants.REQUIRED,
                        ErrorLabelConstants.REGEX('ERROR.EMAIL.INVALID')
                    ],
                    size: '12|12|12'
                })
                .set('fiscalCode', {
                    type: 'text',
                    pattern:
                        '^[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]$',
                    label: 'USERS.EDIT.FISCAL_CODE',
                    errorLabels: [ErrorLabelConstants.REGEX('ERROR.REGEX')],
                    control: new FormControl(this.model.fiscalCode, [
                        Validators.required
                    ]),
                    size: '12|12|12'
                })
                .set('prosecutingAdministration', {
                    placeholder: 'USERS.EDIT.AUTHORITY',
                    type: 'autocomplete',
                    label: 'USERS.EDIT.AUTHORITY',
                    control: new FormControl(
                        this.model.prosecutingAdministration,
                        [Validators.required]
                    ),
                    size: '12|12|12',
                    errorLabels: [ErrorLabelConstants.REQUIRED],
                    notFoundText: 'USERS.EDIT.AUTHORITY',
                    valueChange: (value: ComboBox) => {
                        if (value) {
                            this.groupFields
                                .get('user')
                                .fields.get(
                                    'profile'
                                ).options = this.utilityService.getProfiles(
                                value.key
                            );
                        }
                    }
                })
                .set('profile', {
                    placeholder: 'USERS.EDIT.PROFILE',
                    type: 'select',
                    label: 'USERS.EDIT.PROFILE',
                    control: new FormControl(
                        this.model.profile,
                        Validators.compose([
                            Validators.required,
                            SelectValueValidator
                        ])
                    ),
                    size: '12|12|12',
                    options: of(this.profile)
                })
                .set('managersToImpersonate', {
                    type: 'select-two',
                    label:
                        'USERS.EDIT.MANAGERS',
                    control: new FormControl(this.model.managersToImpersonate),
                    placeholder: 'COMMON.ALL',
                    size: '12|6|6',
                    options: this.managersToImpersonateData,
                }).set('flagSignatory', {
                    type: 'switch',
                    label: 'USERS.EDIT.FLAG_SIGNATORY',
                    control: new FormControl(
                        this.model.flagSignatory
                    ),
                    size: '12|12|12'
                })
        });
        this._initProfile();
        this._initAuthorityAutomplete();
    }

    private _initCallbacks(): void {
        this.onNext = this._onNext.bind(this);
        this.saveComplete = this._saveComplete.bind(this);
        this.saveError = () => {};
    }

    private _initProfile() {
        const profileField: FormField = this.groupFields
            .get('user')
            .fields.get('profile');
        const authorityField: FormField = this.groupFields
            .get('user')
            .fields.get('prosecutingAdministration');
        if (
            authorityField.control.value &&
            authorityField.control.value.key !== '-1'
        ) {
            profileField.options = this.utilityService.getProfiles(
                authorityField.control.value.key
            );
        }
    }

    private _initAuthorityAutomplete(participant: User = new User()): void {
        const authorityField: FormField = this.groupFields
            .get('user')
            .fields.get('prosecutingAdministration');
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
                relativeTo: this.activatedRoute
            });
            this.messageToastr.showMessage(response, 'USERS.EDIT', 'NEW');
        } else {
            this.messageToastr.showMessage(response, 'USERS.EDIT', 'EDIT');
        }
    }

    private _saveError(error: HttpResponse<WrapperResponse>): Observable<null> {
        try {
            this.toastr.error(error.body.errors[0].msg, 'Impossibile registrare le modifiche');
        }
        catch(e) {}

        if (this.createMode) {
            return this.messageToastr.showErrorMessage(
                error,
                'USERS.EDIT',
                'NEW'
            );
        } else {
            return this.messageToastr.showErrorMessage(
                error,
                'USERS.EDIT',
                'EDIT'
            );
        }
    }
    private _onNext(data: User): Observable<WrapperPostPutData> {
        const formValue = this.groupFields.get('user').fields;

        if (this.createMode) {
            return this.usersService.createUser(data).pipe(
                catchError((response: HttpResponse<WrapperResponse>) => {
                    return this._saveError(response);
                })
            );
        } else {
            return this.usersService
                .edit(data, this.route.snapshot.params.id)
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
