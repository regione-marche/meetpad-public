import { Component, Output, EventEmitter } from '@angular/core';
import { FormControl } from '@angular/forms';

import { of } from 'rxjs';
import { takeUntil, map } from 'rxjs/operators';

import { I18nService } from '@eng-ds/ng-core';

import { ComboBox, AutoUnsubscribe, FormFieldGroup, FormField } from '@common';
import { UtilityService } from '@app/core';

import { ConferenceStoreService } from '@app/features/private/conference/core';

import { EventStoreService } from '../../services';
import { QuerySearchEvents } from '../../models/query-search-events.model';

@Component({
    selector: 'app-events-search',
    templateUrl: './events-search.component.html',
    styleUrls: ['./events-search.component.scss']
})
export class EventsSearchComponent extends AutoUnsubscribe {
    groupFields: Map<string, FormFieldGroup> = new Map<
        string,
        FormFieldGroup
    >();

    @Output('searchQuery') searchQuery: EventEmitter<
        QuerySearchEvents
    > = new EventEmitter();

    all: string = this.i18nService.translate('COMMON.ALL');
    reset: string = this.i18nService.translate(
        'CONFERENCE.WIZARD.EVENTS.SEARCH_PANEL.RESET_BUTTON'
    );

    constructor(
        private eventsStoreService: EventStoreService,
        private utilityService: UtilityService,
        private conferenceStoreService: ConferenceStoreService,
        private i18nService: I18nService
    ) {
        super();
        this._createSearchForm();
    }

    private _createSearchForm(): void {
        const participantComboBox: ComboBox<
            string
        >[] = this.conferenceStoreService.conference.getAuthorityComboBox();
        const registered: ComboBox<string>[] = [];
        registered.push({ key: '-1', value: this.all });
        registered.push(...participantComboBox);

        this.groupFields.set('search', {
            panel: false,
            panelHead: null,
            fields: new Map<string, FormField>()
                .set('eventType', {
                    type: 'select',
                    label: 'CONFERENCE.WIZARD.EVENTS.SEARCH_PANEL.EVENT_TYPE',
                    control: new FormControl(),
                    size: '12|4|4',
                    options: this.eventsStoreService.utilityService
                        .getEventTypes()
                        .pipe(
                            takeUntil(this.destroy$),
                            map(value => {
                                const newValue: ComboBox<string>[] = [];
                                newValue.push({
                                    key: '-1',
                                    value: this.all
                                });
                                newValue.push(...value);
                                return newValue;
                            })
                        )
                })
                .set('administrationType', {
                    type: 'select',
                    label:
                        'CONFERENCE.WIZARD.EVENTS.SEARCH_PANEL.ADMINISTRATION_TYPE',
                    control: new FormControl(),
                    size: '12|4|4',
                    options: this.utilityService.getAllParticipantRoles().pipe(
                        takeUntil(this.destroy$),
                        map(value => {
                            const newValue: ComboBox<string>[] = [];
                            newValue.push({ key: '-1', value: this.all });
                            newValue.push(...value);
                            return newValue;
                        })
                    )
                })
                .set('authority', {
                    type: 'select',
                    label: 'CONFERENCE.WIZARD.EVENTS.SEARCH_PANEL.AUTHORITY',
                    control: new FormControl(),
                    size: '12|4|4',
                    options: of(registered)
                })
        });
    }

    onReset(): void {
        this.groupFields.get('search').fields.forEach((field: FormField) => {
            if (field.control.value) {
                field.control.setValue({ key: '-1', value: this.all });
                setTimeout(() => {
                    field.control.reset();
                }, 500);
            }
        });
    }

    search(): void {
        const fields = this.groupFields.get('search').fields;
        this.searchQuery.emit({
            eventType: fields.get('eventType').control.value
                ? fields.get('eventType').control.value.key
                : null,
            administrationType: fields.get('administrationType').control.value
                ? fields.get('administrationType').control.value.key
                : null,
            authority: fields.get('authority').control.value
                ? fields.get('authority').control.value.key
                : null
        });
    }
}
