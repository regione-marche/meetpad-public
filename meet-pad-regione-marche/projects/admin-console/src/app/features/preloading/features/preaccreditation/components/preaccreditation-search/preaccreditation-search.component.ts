import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { SectionLoading, LoaderService } from '@common';
import { I18nService } from '@eng-ds/ng-core';
import { QueryPreaccreditation } from '../../models/query-preaccreditation.model';

@Component({
    selector: 'admin-preaccreditation-search',
    templateUrl: './preaccreditation-search.component.html',
    styleUrls: ['./preaccreditation-search.component.scss']
})
export class PreaccreditationSearchComponent implements OnInit {
    @Output() onNewSearch = new EventEmitter<QueryPreaccreditation>();
    form: FormGroup;

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
            value: this.form.get('value').value
        });
    }
}
