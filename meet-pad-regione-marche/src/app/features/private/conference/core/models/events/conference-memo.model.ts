/* tslint:disable:no-inferrable-types */
import { DateModel } from '@eng-ds/ng-toolkit';

import { ComboBox } from '@common';
import { Event } from './event.model';

import { environment } from '@env/environment';
import { EventDocument } from './event-attachment.model';

export class ConferenceMemo extends Event {
    administration: string = '';
    notes: string = '';
    document: EventDocument = EventDocument.fromDto('','','',new DateModel(new Date()),
    Object.assign(
        {},
        environment.defaultComboBox.conference.events.conferenceMemo
                .document.category
    ));

    constructor(event?: Partial<ConferenceMemo>) {
        super(event);

        this.type = Object.assign(
            {},
            environment.defaultComboBox.conference.events.conferenceMemo.type
        );
        this.subject = Object.assign(
            {},
            environment.defaultComboBox.conference.events.conferenceMemo.subject
        );

        if (event) {
            if (event.document) {
                this.document.protocolNumber =
                    event.document.protocolNumber || '';
                this.document.protocolDate =
                    event.document.protocolDate || new DateModel(new Date());

                this.document.name = event.document.name || '';
                this.document.url = event.document.url || '';

                if (event.document.category) {
                    this.document.category = event.document.category;
                }
            }

            this.notes = event.notes || '';
            this.administration = event.administration || '';
        }
    }
}
