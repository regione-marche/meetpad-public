import { BaseModel } from '@eng-ds/ng-toolkit';
import { ComboBox } from '@common';

export class SearchApplicant extends BaseModel {
    id: any;
    name: string;
    surname: string;
    fiscalCode: string;
    company: ComboBox<string>;
    conferenceType: ComboBox<string> | string;

    ì;
    constructor(participant?: Partial<SearchApplicant>) {
        super();
        if (participant) {
            this.id = participant.id;
            this.name = participant.name;
            this.surname = participant.surname;
            this.fiscalCode = participant.fiscalCode;
            this.conferenceType = participant.conferenceType;
            this.company = participant.company;
        }
    }

    static fromDto(data: any) {
        return new SearchApplicant({
            id: data.idApplicant,
            name: data.name,
            surname: data.surname,
            fiscalCode: data.fiscalCode,
            conferenceType: data.conferenceType,
            company: data.company
        });
    }
}
