import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { UtilityService } from '@app/core';
import { ComboBox, LoaderService, SectionLoading } from '@common';
import { I18nService } from '@eng-ds/ng-core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { SearchUnifyCriteria } from '../../models/search-unifymodel';

@Component({
    // tslint:disable-next-line:component-selector
    selector: 'search',
    templateUrl: './search.component.html',
    styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit {
    // tslint:disable-next-line:no-output-on-prefix
    @Output() onNewSearch = new EventEmitter<SearchUnifyCriteria>();
    form: FormGroup;
    @Input('stateOptions') stateOptions: Observable<ComboBox[]>;
    @Input('showStatus') showStatus: boolean = false;
    @Input('requiredSearch') requiredSearch: boolean = true;

    searchLoading$ = this.loaderService.getLoading$(SectionLoading.SEARCH);

    constructor(
        private fb: FormBuilder,
        private loaderService: LoaderService
    ) {}

    ngOnInit() {
        this.form = this.fb.group({
            value: [],
            state: []
        });
    }

    search() {
        this.onNewSearch.emit({
            value: this.form.get('value').value, 
            state: this.form.get('state').value
        });
    }
}
