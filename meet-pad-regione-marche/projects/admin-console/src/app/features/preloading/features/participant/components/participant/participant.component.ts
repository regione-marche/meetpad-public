import { Component, OnInit } from '@angular/core';
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
    MessageToastrService
} from '@common';
import { Observable, of } from 'rxjs';
import { ErrorMessage, ErrorLabelConstants } from '@eng-ds/ng-toolkit';
import { ActivatedRoute, Router } from '@angular/router';
import { FormControl, Validators } from '@angular/forms';
import { takeUntil, map, catchError } from 'rxjs/operators';
import { Participant } from '../../models/participant.model';
import { ParticipantsService } from '../../services/participant.service';
import { UtilityService } from '../../../../../../core/services/utility.service';
import { HttpResponse } from '@angular/common/http';

@Component({
    // tslint:disable-next-line: component-selector
    selector: 'admin-participant',
    templateUrl: './participant.component.html',
    styleUrls: ['./participant.component.scss']
})
export class ParticipantComponent extends AutoUnsubscribe implements OnInit {
    createMode: boolean = true;
    conferenceType: string;
    onNext: (data: Participant) => Observable<WrapperPostPutData>;
    saveComplete: (response: WrapperPostPutData) => void;
    saveError: (error: ErrorMessage) => void;

    groupFields: Map<string, FormFieldGroup> = new Map();
    model: Participant = new Participant();
    competenceAuthorization: ComboBox[];

    constructor(
        private route: ActivatedRoute,
        private loaderService: LoaderService,
        private participantsService: ParticipantsService,
        private utilityService: UtilityService,
        private messageToastr: MessageToastrService,
        private router: Router
    ) {
        super();
    }

    ngOnInit() {
        if (this.route.snapshot.data.participant) {
            const p = this.route.snapshot.data.participant;

            this.createMode = false;
            this.model = new Participant(Participant.fromDto(p));
            this.model.authority = {
                key: p.idAuthority,
                value: p.name
            };
        }

        this.route.parent.params
            .pipe(takeUntil(this.destroy$))
            .subscribe(value => {
                this.conferenceType = value.conferenceType;
            });

        this.utilityService
            .getCompetenceAuthorizationForConferenceTypes(this.conferenceType)
            .pipe(takeUntil(this.destroy$))
            .subscribe((competenceAuthorization: ComboBox[]) => {
                this.competenceAuthorization = competenceAuthorization;
                this._createForm();
                this.loaderService.hideLoading(SectionLoading.ALL_CONTENT, 500);
                if (competenceAuthorization.length === 0) {
                    this.groupFields
                        .get('participant')
                        .fields.delete('competenceAuthorization');
                }
            });
        this._initCallbacks();
    }

    private _createForm(): void {
        this.groupFields.set('participant', {
            panel: true,
            panelHead: 'PRELOADING.PARTICIPANT.EDIT.TITLE',
            accordion: false,
            fields: new Map()
                .set('authority', {
                    placeholder:
                        'PRELOADING.PARTICIPANT.EDIT.AUTHORITY_PLACEHOLDER',
                    type: 'autocomplete',
                    label: 'PRELOADING.PARTICIPANT.EDIT.AUTHORITY',
                    control: new FormControl(
                        {
                            value: this.model.authority,
                            disabled: !this.createMode
                        },
                        [Validators.required]
                    ),
                    required: true,
                    size: '12|12|12',
                    valueChange: (value: ComboBox): void => {
                        if (value) {
                            this.groupFields
                                .get('participant')
                                .fields.get(
                                    'authority'
                                ).options = this.utilityService.getProfiles(
                                value.key
                            );
                        }
                    },
                    errorLabels: [ErrorLabelConstants.REQUIRED],
                    notFoundText:
                        'CONFERENCE.AUTOCOMPLETE.INSERT_MIN_CHARACTERS',
                    loading: false
                })
                .set('participantRole', {
                    type: 'select',
                    label: 'PRELOADING.PARTICIPANT.EDIT.ROLE',
                    control: new FormControl(this.model.participantRole, [
                        Validators.required,
                        SelectValueValidator
                    ]),

                    size: '12|12|12',
                    options: this.utilityService.getParticipantRoles(),
                    errorLabels: [ErrorLabelConstants.REQUIRED]
                })
                .set('competenceAuthorization', {
                    type: 'select-two',
                    label: 'PRELOADING.PARTICIPANT.EDIT.COMPETENCE',
                    control: new FormControl(
                        this.model.competenceAuthorization,
                        [Validators.required]
                    ),
                    size: '12|12|12',
                    options: of(this.competenceAuthorization)
                })
        });
        // this._initProfile();
        this._initAuthorityAutomplete();
        this.groupFields = new Map(this.groupFields);
    }

    private _initCallbacks(): void {
        this.onNext = this._onNext.bind(this);
        this.saveComplete = this._saveComplete.bind(this);
        this.saveError = () => {};
    }

    private _initAuthorityAutomplete(
        participant: Participant = new Participant()
    ): void {
        const authorityField: FormField = this.groupFields
            .get('participant')
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
                'PRELOADING.PARTICIPANT.EDIT',
                'NEW'
            );
        } else {
            this.messageToastr.showMessage(
                response,
                'PRELOADING.PARTICIPANT.EDIT',
                'EDIT'
            );
        }
    }

    private _saveError(error: HttpResponse<WrapperResponse>): Observable<null> {
        if (this.createMode) {
            return this.messageToastr.showErrorMessage(
                error,
                'PRELOADING.PARTICIPANT.EDIT',
                'NEW'
            );
        } else {
            return this.messageToastr.showErrorMessage(
                error,
                'PRELOADING.PARTICIPANT.EDIT',
                'EDIT'
            );
        }
    }
    private _onNext(data: Participant): Observable<WrapperPostPutData> {
        if (this.createMode) {
            return this.participantsService
                .createParticipant(data, this.conferenceType)
                .pipe(
                    catchError((response: HttpResponse<WrapperResponse>) => {
                        return this._saveError(response);
                    })
                );
        } else {
            data.conferenceType = this.model.conferenceType;
            return this.participantsService
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
