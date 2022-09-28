import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { SectionLoading, LoaderService } from '@common';

@Component({
    // tslint:disable-next-line: component-selector
    selector: 'admin-participants-search',
    templateUrl: './participants-search.component.html',
    styleUrls: ['./participants-search.component.scss']
})
export class ParticipantsSearchComponent implements OnInit {
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
