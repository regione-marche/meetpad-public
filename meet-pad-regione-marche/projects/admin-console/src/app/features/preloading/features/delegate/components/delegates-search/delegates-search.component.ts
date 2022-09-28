import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { SectionLoading, LoaderService } from '@common';
import { I18nService } from '@eng-ds/ng-core';
import { QueryDelegate } from '../../models/query-delegate.model';

@Component({
    selector: 'admin-delegates-search',
    templateUrl: './delegates-search.component.html',
    styleUrls: ['./delegates-search.component.scss']
})
export class DelegatesSearchComponent implements OnInit {
    @Output() onNewSearch = new EventEmitter<QueryDelegate>();
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
