/* tslint:disable:no-inferrable-types */
import { ComboBox } from '@common';

import { DateModel } from '@eng-ds/ng-toolkit';

import { environment } from '@env/environment';

import { Event } from './event.model';
import { DateUpdate } from '../definition/update-date.model';
import { EventDocument } from './event-attachment.model';

export class EditDate extends Event {
    administration: string = '';
    body: string = '';
    editDate?: DateUpdate = new DateUpdate();
    oldDate?: DateModel | string = new DateModel('');
    newDate?: DateModel | string = new DateModel('');
    dateType?: string = '';
    newDateTermine?: DateModel | string = new DateModel('');
    newDateTermineEsprPareri?: DateModel | string = new DateModel('');
    newDateTermineRichInteg?: DateModel | string = new DateModel('');
    newDatePrimaSessioneSimultanea?: DateModel | string = new DateModel('');
    document: EventDocument = EventDocument.fromDto('','','',new DateModel(new Date()),
    Object.assign(
        {},
        environment.defaultComboBox.conference.events.editDate
            .document.category
    ));
    
    constructor(event?: Partial<EditDate>) {
        super(event);

        this.type = Object.assign(
            {},
            environment.defaultComboBox.conference.events.editDate
                .type
        );
        this.subject = Object.assign(
            {},
            environment.defaultComboBox.conference.events.editDate
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
            //this.subject = Object.assign( { key: event.subject.key, value: event.subject.value} )

            if(event.editDate){
                this.editDate = new DateUpdate(event.editDate)
            }
            this.oldDate = event.oldDate;
            
        }
    }
}
