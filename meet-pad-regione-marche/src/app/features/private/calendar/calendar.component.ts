import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import * as moment from 'moment';

import { isSameMonth, isSameDay } from 'date-fns';

import { CalendarView, CalendarEvent } from 'angular-calendar';

import { I18nService } from '@eng-ds/ng-core';

import { LoaderService, SectionLoading, AutoUnsubscribe } from '@common';
import { CalendarEventType } from '@app/core/enums/calendar-event-type.enum';
import { Calendar } from './model/calendar.model';

const colors: any = {
    red: {
        primary: '#ad2121',
        secondary: '#FAE3E3'
    },
    blue: {
        primary: '#1e90ff',
        secondary: '#D1E8FF'
    },
    yellow: {
        primary: '#e3bc08',
        secondary: '#FDF1BA'
    },
    green: {
        primary: '#00e600',
        secondary: '#e6ffe6'
    },
    black: {
        primary: '#000000',
        secondary: '#e6e6e6'
    }
};

@Component({
    selector: 'app-calendar',
    templateUrl: './calendar.component.html',
    styleUrls: ['./calendar.component.scss']
})
export class CalendarComponent extends AutoUnsubscribe implements OnInit {
    view: CalendarView = CalendarView.Month;
    viewDate: Date = new Date();

    previous: string = this.i18nService.translate('CALENDAR.PREVIOUS');
    today: string = this.i18nService.translate('CALENDAR.TODAY');
    next: string = this.i18nService.translate('CALENDAR.NEXT');

    events: CalendarEvent<Calendar>[];

    expirationDate: string = this.i18nService.translate(
        'CONFERENCE.WIZARD.DEFINITION.INSTANCE.EXPIRATION_DATE'
    );
    endOpinionDate: string = this.i18nService.translate(
        'CONFERENCE.WIZARD.DEFINITION.INSTANCE.END_OPINION_DATE'
    );
    endIntegrationDate: string = this.i18nService.translate(
        'CONFERENCE.WIZARD.DEFINITION.INSTANCE.END_INTEGRATION_DATE'
    );
    firstSessionDate: string = this.i18nService.translate(
        'CONFERENCE.WIZARD.DEFINITION.INSTANCE.FIRST_SESSION_DATE'
    );
    creationDate: string = this.i18nService.translate(
        'CONFERENCE.WIZARD.DEFINITION.INSTANCE.CREATION_DATE'
    );

    activeDayIsOpen: boolean = false;

    constructor(
        private router: Router,
        private route: ActivatedRoute,
        private loaderService: LoaderService,
        private i18nService: I18nService
    ) {
        super();
    }

    ngOnInit() {
        this.events = this.route.snapshot.data.events.map((event: Calendar) => {
            const eventColor = this.setColor(event.eventType.key);

            return {
                title:
                    'CONFERENZA N. ' +
                    event.conferenceId +
                    ', ' +
                    event.conferenceName +
                    ': ' +
                    event.eventType.value,
                start: moment(event.eventDate, 'DD/MM/YYYY').toDate(),
                end: moment(event.eventDate, 'DD/MM/YYYY').toDate(),
                color: eventColor,
                allDay: true,
                meta: {
                    ...event
                }
            } as CalendarEvent<Calendar>;
        });
        this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
    }

    setColor(eventTypeKey: string): any {
        let color: string = '';
        switch (eventTypeKey) {
            case CalendarEventType.EXPIRATION_DATE:
                color = colors.red;
                break;
            case CalendarEventType.END_OPINION_DATE:
                color = colors.blue;
                break;
            case CalendarEventType.END_INTEGRATION_DATE:
                color = colors.yellow;
                break;
            case CalendarEventType.FIRST_SESSION_DATE:
                color = colors.green;
                break;
            case CalendarEventType.CREATION_DATE:
                color = colors.black;
                break;
        }
        return color;
    }

    dayClicked({
        date,
        events
    }: {
        date: Date;
        events: CalendarEvent[];
    }): void {
        if (isSameMonth(date, this.viewDate)) {
            if (
                (isSameDay(this.viewDate, date) &&
                    this.activeDayIsOpen === true) ||
                events.length === 0
            ) {
                this.activeDayIsOpen = false;
            } else {
                this.activeDayIsOpen = true;
                this.viewDate = date;
            }
        }
    }

    navigateOnConference(event: CalendarEvent<Calendar>): void {
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
        this.router.navigate(['/conference', event.meta.conferenceId]);
    }
}
