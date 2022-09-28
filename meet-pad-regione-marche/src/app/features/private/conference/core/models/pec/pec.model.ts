import { BaseModel, DateModel } from '@eng-ds/ng-toolkit';

import { ComboBox } from '@common';
import { environment } from '@env/environment';

export class Pec extends BaseModel {
    id: string = '';
    sender: string = '';
    recipient: string = '';
    status: ComboBox = Object.assign(
        {},
        environment.defaultComboBox.conference.pec.search.status
    );
    event: ComboBox = Object.assign(
        {},
        environment.defaultComboBox.conference.pec.search.event
    );
    sentDate: DateModel | string = new DateModel('');
    recipientPec: string = '';
    statusMessage: string = '';

    constructor(pec?: Partial<Pec>) {
        super();
        if (pec) {
            this.sender = pec.sender;
            this.recipient = pec.recipient;
            this.status = pec.status;
            this.event = pec.event;
            this.recipientPec = pec.recipientPec;
            this.statusMessage = pec.statusMessage;
            this.sentDate = new DateModel(pec.sentDate as string);
        }
    }

    static fromDto(data: any) {
        return new Pec({
            sender: data.sender,
            recipient: data.recipient,
            status: data.status,
            event: data.event,
            sentDate: data.sentDate,
            recipientPec: data.recipientPec,
            statusMessage: data.statusMessage
        });
    }
}
