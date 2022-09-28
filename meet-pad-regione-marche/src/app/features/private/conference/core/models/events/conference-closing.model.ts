/* tslint:disable:no-inferrable-types */
import { ComboBox } from '@common';
import { Event } from './event.model';

import { environment } from '@env/environment';

export class ConferenceClosing extends Event {
    administration: string = '';
    notes: string = '';
    result: ComboBox;

    constructor(event?: Partial<ConferenceClosing>) {
        super(event);

        this.type = Object.assign(
            {},
            environment.defaultComboBox.conference.events.conferenceClosing.type
        );
        this.subject = Object.assign(
            {},
            environment.defaultComboBox.conference.events.conferenceClosing
                .subject
        );

        this.result = Object.assign(
            {},
            environment.defaultComboBox.conference.events.conferenceClosing
                .result
        );

        if (event) {
            this.notes = event.notes || '';
            this.administration = event.administration || '';
            this.result = event.result;
        }
    }
}
