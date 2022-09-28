/* tslint:disable:no-inferrable-types */
import { DateModel } from '@eng-ds/ng-toolkit';

import { ComboBox } from '@common';
import { Event } from './event.model';

import { environment } from '@env/environment';
import { EventDocument } from './event-attachment.model';

export class OpinionExpress extends Event {
    administration: string = '';
    body: string = '';
   
    determinationType?: ComboBox =
        environment.defaultComboBox.conference.events.opinionExpress
            .determinationType;
    
    document: EventDocument = EventDocument.fromDto('','','',new DateModel(new Date()),
            Object.assign(
                {},
                environment.defaultComboBox.conference.events.opinionExpress
                .document.category
    ), null,null, 
    Object.assign(
        {},
        environment.defaultComboBox.conference.events.opinionExpress
    .determinationType
    ));
    
    constructor(event?: Partial<OpinionExpress>) {
        super(event);

        this.type = Object.assign(
            {},
            environment.defaultComboBox.conference.events.opinionExpress.type
        );
        this.subject = Object.assign(
            {},
            environment.defaultComboBox.conference.events.opinionExpress.subject
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

            if (event.determinationType) {
                this.determinationType = event.determinationType;
            }

            this.administration = event.administration || '';
            this.body = event.body || '';
        }
    }
}
