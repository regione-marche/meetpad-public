import { HttpParams } from '@angular/common/http';

import { Observable, Subject, of, concat } from 'rxjs';
import {
    shareReplay,
    map,
    debounceTime,
    distinctUntilChanged,
    tap,
    switchMap
} from 'rxjs/operators';

import { ApiService, PathParams } from '@eng-ds/ng-core';
import { RadioOption } from '@eng-ds/ng-toolkit';

// import { environment } from '@env/environment';
// import { FormField } from '@app/features/private/shared';

import { ComboBox } from '../interfaces/combo-box.interface';
import { WrapperGetData } from '../interfaces/wrapers-http-response.interface';

const CACHE_SIZE = 1;

export class BaseService {
    private cache$: Map<string, Observable<any>> = new Map();

    constructor(protected api: ApiService) {}

    protected get<T>(
        fn: Observable<any>,
        key: string,
        refresh: boolean = false
    ): Observable<T> {
        if (refresh) {
            this.delete(key);
        }
        if (!this.cache$.has(key)) {
            this.cache$.set(key, fn.pipe(shareReplay(CACHE_SIZE)));
        }

        // if (environment.logger.cache && environment.logger.cache === true) {
        //     console.log('BaseService.get -------------------------');
        //     console.log('key', key);
        //     console.log('cache maps', this.cache$);
        //     console.log('------------------------------------------');
        // }

        return this.cache$.get(key);
    }

    protected delete(key: string): void {
        if (this.cache$.has(key)) {
            this.cache$.delete(key);
        }
    }

    protected comboBoxRequest(
        apiName: string,
        queryParams: HttpParams = null,
        pathParams: PathParams = null
    ): Observable<ComboBox[]> {
        return this.api
            .request<WrapperGetData<ComboBox>>(
                apiName,
                null,
                queryParams,
                pathParams
            )
            .pipe(map(response => response.list));
    }

    protected mapComboBoxToRadioOption(list: ComboBox[]): RadioOption[] {
        const ro: RadioOption[] = [];
        list.forEach((cb: ComboBox) => {
            ro.push(new RadioOption(cb.value, cb.value));
        });
        return ro;
    }

    protected getAutocomplete(
        field: /* FormField */ any,
        fn: (search: string) => Observable<any>
    ): { options$: Observable<ComboBox[]>; typeahead$: Subject<string> } {
        const typeahead$ = new Subject<string>();

        const options$: Observable<ComboBox[]> = concat(
            of([]),
            typeahead$.pipe(
                debounceTime(500),
                distinctUntilChanged(),
                tap(() => (field.loading = true)),
                switchMap((search: string) => {
                    if (search && search.length > 2) {
                        field.notFoundText =
                            'CONFERENCE.AUTOCOMPLETE.ITEMS_NOT_FOUND';

                        field.loading = false;
                        return fn(search);
                    }

                    field.notFoundText =
                        'CONFERENCE.AUTOCOMPLETE.INSERT_MIN_CHARACTERS';

                    field.loading = false;
                    return of([]);
                })
            )
        );

        return { options$, typeahead$ };
    }
}
