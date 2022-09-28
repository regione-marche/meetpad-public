/* tslint:disable:no-inferrable-types */
import { ComboBox } from '@common';

import { DateModel } from '@eng-ds/ng-toolkit';

import { environment } from '@env/environment';

import { Event } from './event.model';

import { EventDocument } from './event-attachment.model';

export class IntegrationRegistered extends Event {
    administration: string = '';
    body: string = '';
    recipients: ComboBox[] = [];
    document: EventDocument = EventDocument.fromDto('','','',new DateModel(new Date()),
    Object.assign(
        {},
        environment.defaultComboBox.conference.events.integrationRegistered
                .document.category
    ));
    
    constructor(event?: Partial<IntegrationRegistered>) {
        super(event);

        this.type = Object.assign(
            {},
            environment.defaultComboBox.conference.events.integrationRegistered
                .type
        );
        this.subject = Object.assign(
            {},
            environment.defaultComboBox.conference.events.integrationRegistered
                .subject
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
            this.administration = event.administration || '';
            this.body = event.body || '';
            this.recipients = event.recipients;
        }
    }
}
