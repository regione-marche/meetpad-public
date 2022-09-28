import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { SectionLoading, LoaderService } from '@common';
import { QueryApplicant } from '../../models/query-applicant.model';
import { I18nService } from '@eng-ds/ng-core';

@Component({
    // tslint:disable-next-line: component-selector
    selector: 'admin-applicant-search',
    templateUrl: './applicant-search.component.html',
    styleUrls: ['./applicant-search.component.scss']
})
export class ApplicantSearchComponent implements OnInit {
    @Output() onNewSearch = new EventEmitter<QueryApplicant>();
    form: FormGroup;
    linkedLabel: string = this.i18nService.translate(
        'PRELOADING.APPLICANT.SEARCH.LINKED_LABEL'
    );

    searchLoading$ = this.loaderService.getLoading$(SectionLoading.SEARCH);

    constructor(
        private fb: FormBuilder,
        private loaderService: LoaderService,
        private i18nService: I18nService
    ) {}

    ngOnInit() {
        this.form = this.fb.group({
            value: [],
            company: [],
            linked: false
        });
    }

    search() {
        this.onNewSearch.emit({
            value: this.form.get('value').value,
            company: this.form.get('company').value,
            linked: this.form.get('linked').value
        });
    }
}
