/* tslint:disable:no-inferrable-types */
import { DateModel } from '@eng-ds/ng-toolkit';

import { ComboBox } from '@common';
import { Event } from './event.model';

import { environment } from '@env/environment';

export class ConferenceIndiction extends Event {
    administration: string = '';
    body: string = '';
    document: {
        protocolNumber: string;
        protocolDate: DateModel;
        name: string;
        url: string;
        category: ComboBox;
        file?: File;
    } = {
        protocolNumber: '',
        protocolDate: new DateModel(new Date()),
        name: '',
        url: '',
        category: Object.assign(
            {},
            environment.defaultComboBox.conference.events.conferenceIndiction
                .document.category
        )
    };

    constructor(event?: Partial<ConferenceIndiction>) {
        super(event);

        this.type = Object.assign(
            {},
            environment.defaultComboBox.conference.events.conferenceIndiction
                .type
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
        }
    }
}
