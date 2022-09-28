import { Component, OnInit } from '@angular/core';

import { LoaderService, SectionLoading, BaseComponent, ComboBox } from '@common';

import { ConferenceState, ResultTableMode, UtilityService } from '@app/core';
import { Observable } from 'rxjs';
import { I18nService } from '@eng-ds/ng-core';
import { map } from 'rxjs/operators';
import { SearchUnifyCriteria } from '../models/search-unifymodel';

@Component({
    templateUrl: './search.component.html',
    styleUrls: ['./search.component.scss']
})
export class SearchComponent extends BaseComponent implements OnInit {
    searchedValue: string;
    stateValue: string;
    resultTableMode: typeof ResultTableMode = ResultTableMode;
    stateOptions: Observable<ComboBox[]>;
    searchQuery: SearchUnifyCriteria;

    constructor(private loaderService: LoaderService,
        private utilityService: UtilityService,
        private i18nService: I18nService) {
        super();
    }

    ngOnInit() {
        setTimeout(() => {
            this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
        });
        this._initStateOption();        
    }

    onNewSearch(query: SearchUnifyCriteria): any {
        this.searchedValue = query.value || '';
        if((query.state && query.state.key))
            this.stateValue = query.state.key;
        else
            this.stateValue = '';
    }

    private _initStateOption(): void{
        this.stateOptions = this.utilityService.getConferenceStatus().pipe(
            map((v: ComboBox[]) => {
                for(var i=0; i< v.length; i++){
                    if(v[i].key === ConferenceState.CLOSED){
                        v[i].value = this.i18nService.translate(
                            'SEARCH.SEARCH.OPTION_STATE.CLOSED'
                        );
                    }
                    if(v[i].key === ConferenceState.ARCHIVIED){
                        v[i].value = this.i18nService.translate(
                            'SEARCH.SEARCH.OPTION_STATE.FILED'
                        );
                    }
                }

                return [
                    {
                        key: '',
                        value: this.i18nService.translate(
                            'SEARCH.SEARCH.OPTION_STATE.ALL'
                        )
                    } as ComboBox
                ].concat([
                    {
                        key: '999',
                        value: this.i18nService.translate(
                            'SEARCH.SEARCH.OPTION_STATE.OPEN'
                        )
                    } as ComboBox
                ]).concat(v);
            })
        );
    }
}
