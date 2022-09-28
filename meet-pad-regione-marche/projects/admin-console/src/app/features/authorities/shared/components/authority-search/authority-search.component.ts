import { Component, EventEmitter, Output, OnInit } from '@angular/core';
import { LoaderService, SectionLoading } from '@common';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
    // tslint:disable-next-line: component-selector
    selector: 'admin-authority-search',
    templateUrl: './authority-search.component.html',
    styleUrls: ['./authority-search.component.scss']
})
export class AuthoritySearchComponent implements OnInit {
    // tslint:disable-next-line:no-output-on-prefix
    @Output() onNewSearch = new EventEmitter<any>();
    form: FormGroup;

    searchLoading$ = this.loaderService.getLoading$(SectionLoading.SEARCH);

    constructor(
        private fb: FormBuilder,
        private loaderService: LoaderService
    ) {}

    ngOnInit() {
        this.form = this.fb.group({
            value: []
        });
    }

    search() {
        this.onNewSearch.emit(this.form.get('value').value);
    }
}
