import {
    Resolve,
    ActivatedRouteSnapshot,
    RouterStateSnapshot
} from '@angular/router';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

import { SectionLoading, LoaderService } from '@common';

import { CalendarService } from '../../calendar/services/calendar.service';
import { Calendar } from '../../calendar/model/calendar.model';

@Injectable()
export class CalendarResolver implements Resolve<Calendar> {
    constructor(
        private loaderService: LoaderService,
        private calendarService: CalendarService
    ) {}

    resolve(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<any> | Promise<any> | any {
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
        return this.calendarService.simpleSearch();
    }
}
