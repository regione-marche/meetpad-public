import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { LoaderService, SectionLoading } from '@common';

@Component({
    // tslint:disable-next-line:component-selector
    selector: 'search',
    templateUrl: './media-search.component.html',
    styleUrls: ['./media-search.component.scss']
})
export class MediaSearchComponent implements OnInit {
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
