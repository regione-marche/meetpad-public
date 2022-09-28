import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { SectionLoading, LoaderService } from '@common';

@Component({
    selector: 'lib-eng-search',
    templateUrl: './eng-search.component.html',
    styleUrls: ['./eng-search.component.scss']
})
export class EngSearchComponent implements OnInit {
    @Input('accordionTitle') accordionTitle: string;
    @Input('inputLabel') inputLabel: string;
    @Input('searchButtonTitle') searchButtonTitle: string;
    @Input('singleInput') singleInput: boolean;
    @Output() onNewSearch = new EventEmitter<any>();
    @Input('form') form: FormGroup;

    searchLoading$ = this.loaderService.getLoading$(SectionLoading.SEARCH);

    constructor(
        private fb: FormBuilder,
        private loaderService: LoaderService
    ) {}

    ngOnInit() {}

    search() {
        this.onNewSearch.emit(this.form.get('value').value);
    }
}
