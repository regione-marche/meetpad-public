/* tslint:disable:no-inferrable-types */
import { Event } from './event.model';

import { environment } from '@env/environment';

export class IntegrationClosed extends Event {
    administration?: string = '';
    constructor(event?: Partial<IntegrationClosed>) {
        super(event);

        this.type = Object.assign(
            {},
            environment.defaultComboBox.conference.events.integrationClosed.type
        );
        this.subject = Object.assign(
            {},
            environment.defaultComboBox.conference.events.integrationClosed
                .subject
        );
    }
}
