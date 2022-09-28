import { BaseModel } from '@eng-ds/ng-toolkit';
import { ComboBox } from '@common';

export class Conference extends BaseModel {
    id: any;
    administration: ComboBox = {
        key: '-1',
        value: 'Selezione amministrazione'
    };
    state: ComboBox;
    conferenceType: ComboBox;
    requestReference: string;
    manager: ComboBox = { key: '-1', value: 'Selezione responsabile' };

    constructor(conference?: Partial<Conference>) {
        super();
        if (conference) {
            this.id = conference.id;
            this.state = conference.state;
            this.conferenceType = conference.conferenceType;
            this.requestReference = conference.requestReference;
            this.administration = conference.administration;
            this.manager = conference.manager;
        }
    }

    static fromDto(data: any) {
        return new Conference({
            id: data.id,
            state: data.state,
            conferenceType: data.definition.instance.conferenceType,
            requestReference: data.definition.instance.requestReference,
            administration: data.proceedingCompany,
            manager: data.conferenceManager
        });
    }
}
