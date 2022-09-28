/* tslint:disable:no-inferrable-types */
import { Event } from './event.model';

import { environment } from '@env/environment';

export class ConferenceCreated extends Event {
    administration: string = '';
    body: string = '';

    constructor(event?: Partial<ConferenceCreated>) {
        super(event);

        this.type = Object.assign(
            {},
            environment.defaultComboBox.conference.events.conferenceCreated.type
        );

        if (event) {
            this.administration = event.administration || '';
            this.body = event.body || '';
        }
    }
}
