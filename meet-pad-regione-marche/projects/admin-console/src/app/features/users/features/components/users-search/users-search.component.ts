import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { SectionLoading, LoaderService } from '@common';

@Component({
    // tslint:disable-next-line: component-selector
    selector: 'admin-users-search',
    templateUrl: './users-search.component.html',
    styleUrls: ['./users-search.component.scss']
})
export class UsersSearchComponent implements OnInit {
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
            value: [],
            prosecutingAdministration: []
        });
    }

    search() {
        this.onNewSearch.emit({
            value: this.form.get('value').value,
            prosecutingAdministration: this.form.get(
                'prosecutingAdministration'
            ).value
        });
    }
}
