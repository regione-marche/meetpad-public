import { BaseModel } from '@eng-ds/ng-toolkit';

import { ComboBox } from '@common';

export class Calendar extends BaseModel {
    id: any;
    conferenceId: string = '';
    conferenceName: string = '';
    eventDate: string = '';
    eventType: ComboBox;

    constructor(calendar?: Partial<Calendar>) {
        super();
        if (calendar) {
            this.conferenceId = calendar.conferenceId;
            this.conferenceName = calendar.conferenceName;
            this.eventDate = calendar.eventDate;
            this.eventType = calendar.eventType;
        }
    }

    update(calendar: Calendar): void {
        if (calendar) {
            this.conferenceId = calendar.conferenceId;
            this.conferenceName = calendar.conferenceName;
            this.eventDate = calendar.eventDate;
            this.eventType = calendar.eventType;
        }
    }

    static fromDto(data: any) {
        return new Calendar({
            id: data.id,
            conferenceId: data.conferenceId,
            conferenceName: data.conferenceName,
            eventDate: data.eventDate,
            eventType: data.eventType
        });
    }
}
