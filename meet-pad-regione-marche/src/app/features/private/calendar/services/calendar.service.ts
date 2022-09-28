import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ApiService } from '@eng-ds/ng-core';

import { BaseService, WrapperGetData } from '@common';

import { Calendar } from '../model/calendar.model';

@Injectable()
export class CalendarService extends BaseService {
    constructor(protected api: ApiService) {
        super(api);
    }

    simpleSearch(): Observable<Calendar[]> {
        return this.api
            .request<WrapperGetData<Calendar>>('calendarEvents')
            .pipe(
                map((result: WrapperGetData<Calendar>) => {
                    const arr = [];
                    if (result && result.list) {
                        for (const k in result.list) {
                            if (result.list[k]) {
                                arr.push(Calendar.fromDto(result.list[k]));
                            }
                        }
                    }
                    // tslint:disable-next-line:forin
                    return arr;
                })
            );
    }
}
