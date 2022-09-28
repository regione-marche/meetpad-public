import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { I18nService } from '@eng-ds/ng-core';
import {
    SectionLoading,
    LoaderService,
    AutoUnsubscribe,
    WrapperPostPutData,
    FormFieldGroup,
    ComboBox,
    FormField,
    WrapperResponse,
    MessageToastrService,
    SelectValueValidator,
} from '@common';
import { ToastrService } from 'ngx-toastr';
import { ConferenceService } from '../../../authorities/core/services/conference.service';
import { ActivatedRoute, Router } from '@angular/router';

import { Observable, of } from 'rxjs';
import { tap } from 'rxjs/operators';
import { ErrorMessage, ErrorLabelConstants } from '@eng-ds/ng-toolkit';
import { UtilityService } from '../../../../core/services/utility.service';
import { ChangeState } from '../../../authorities/core/models/changeState';
import { HttpResponse } from '@angular/common/http';


@Component({
    selector: 'admin-change-state',
    templateUrl: './change-state.component.html',
    styleUrls: ['./change-state.component.scss']
})
export class ChangeStateComponent extends AutoUnsubscribe implements OnInit {
    onNext: (data: ChangeState) => Observable<WrapperPostPutData>;
    saveComplete: (response: WrapperPostPutData) => void;
    saveError: (error: ErrorMessage) => void;
    groupFields: Map<string, FormFieldGroup> = new Map();
    model: ChangeState = new ChangeState();

    conferenceAdministations: Observable<ComboBox[] & any> = of([]);
    private _isFirstLoad = true;

    constructor(
        private router: Router,
        private i18nService: I18nService,
        private toastr: ToastrService,
        private route: ActivatedRoute,
        private loaderService: LoaderService,
        private conferenceService: ConferenceService,
        private utilityService: UtilityService,
        private messageToastr: MessageToastrService,
        private activatedRoute: ActivatedRoute
    ) {
        super();
    }

    ngOnInit() {
        if (this.route.snapshot.parent.params.id) {
            this.model.idTipologia = this.route.snapshot.parent.params.id;
            this.conferenceAdministations = this.utilityService.getStateForConference(
                this.model.idTipologia
            );   
        }

        this._createForm(); 
        this._initCallbacks();
        this.loaderService.hideLoading(SectionLoading.ALL_CONTENT, 500);
    }

    private _createForm(): void {
        this.groupFields = new Map<string, FormFieldGroup>().set(
            'getStateForConference',
            {
                panel: true,
                panelHead: 'CONFERENCE.CHANGE_STATE.TITLE',
                fields: new Map<string, FormField>()
                    .set('state', {
                        type: 'select',
                        label: 'CONFERENCE.CHANGE_STATE.STATE',
                        control: new FormControl(this.model.state, [
                            Validators.required,
                            SelectValueValidator
                        ]),
                        size: '12|12|12',
                        options: this.conferenceAdministations.pipe(
                            tap((c: ComboBox[]) => {
                                if(this._isFirstLoad) {
                                    this._isFirstLoad = false;
                                    this.groupFields
                                    .get('getStateForConference')
                                    .fields.get('state').control.setValue(c[0]);                        
                                }
                                if(c.length == 0) {
                                    alert( this.i18nService.translate(
                                        'PRELOADING.APPLICANT.LIST.TOASTR2.EMPTY_MESSAGE'
                                    ));
                                    window.history.back();
                                }
                            })),
                        errorLabels: [ErrorLabelConstants.REQUIRED]
                    })
            }
        );
    }

    private _initCallbacks(): void {
        this.onNext = this._onNext.bind(this);
        this.saveComplete = this._saveComplete.bind(this);
        this.saveError = this._saveError.bind(this);
    }

     private _saveComplete(response: WrapperResponse<any>): void {
        this.router.navigate(['../../'], {
            relativeTo: this.activatedRoute
        });
        this.messageToastr.showMessage(
            response,
            'CONFERENCE.CHANGE_STATE',
            'NEW'
        );
        
    }

    private _saveError(error: ErrorMessage): void {}

   private _onNext(data: ChangeState): Observable<WrapperPostPutData> {
        return this.conferenceService.getState(     
            data,      
            this.route.snapshot.parent.params.id
        );
    }
}
