import { Component, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';

import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { I18nService } from '@eng-ds/ng-core';
import { ErrorMessage } from '@eng-ds/ng-toolkit';

import {
    ComboBox,
    SectionLoading,
    LoaderService,
    AutoUnsubscribe
} from '@common';

import { UtilityService } from '@app/core';
import { Pec } from '../../../core';

@Component({
    selector: 'app-pec-search',
    templateUrl: './pec-search.component.html',
    styleUrls: ['./pec-search.component.scss']
})
export class PecSearchComponent extends AutoUnsubscribe {
    @Output() onNewSearch = new EventEmitter<any>();

    form: FormGroup;
    searchCriteria: Pec;

    stateOptions: Observable<ComboBox[]>;
    eventOptions: Observable<ComboBox[]>;

    searchLoading$ = this.loaderService.getLoading$(SectionLoading.PEC_TAB);

    constructor(
        private fb: FormBuilder,
        private i18nService: I18nService,
        private utilityService: UtilityService,
        private loaderService: LoaderService
    ) {
        super();

        this._init();
    }

    private _init(): void {
        this.searchCriteria = new Pec();

        this.eventOptions = this.utilityService.getEventTypes().pipe(
            map((v: ComboBox[]) => {
                return [
                    {
                        key: '',
                        value: this.i18nService.translate(
                            'CONFERENCE.WIZARD.PEC.SEARCH.ALL'
                        )
                    } as ComboBox
                ].concat(v);
            })
        );

        this.stateOptions = this.utilityService.getPecStatus().pipe(
            map((v: ComboBox[]) => {
                return [
                    {
                        key: '',
                        value: this.i18nService.translate(
                            'CONFERENCE.WIZARD.PEC.SEARCH.ALL'
                        )
                    } as ComboBox
                ].concat(v);
            })
        );

        // stateRadioOptions
        this.form = this.fb.group({
            sender: [this.searchCriteria.sender, []],
            recipient: [this.searchCriteria.recipient, []],
            event: [this.searchCriteria.event, []],
            status: [this.searchCriteria.status, []]
        });
    }

    search(): void {
        this.onNewSearch.emit(new Pec(this.form.value));
    }

    reset(): void {
        this.form.reset();
    }

    fillForm(form: FormGroup, model: any) {
        return null;
    }

    extractDataToSubmit(form: FormGroup) {
        return form.value;
    }

    submitComplete(model: any) {
        alert('Tutto okay');
    }

    submitError(error: ErrorMessage) {
        alert('Errore');
    }

    pageName(): string {
        return 'Pec research';
    }
}
