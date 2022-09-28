import { BaseModel } from '@eng-ds/ng-toolkit';
import { ComboBox } from '@common';

export class Applicant extends BaseModel {
    id: any;
    name: string;
    surname: string;
    email: string;
    fiscalCode: string;
    company: ComboBox<string>;
    conferenceType: ComboBox<string>;

    constructor(applicant?: Partial<Applicant>) {
        super();
        if (applicant) {
            this.id = applicant.id;
            this.surname = applicant.surname;
            this.name = applicant.name;
            this.email = applicant.email;
            this.fiscalCode = applicant.fiscalCode;
            this.company = applicant.company;
        }
    }

    static fromDto(data: any) {
        return new Applicant({
            id: data.idApplicant,
            surname: data.surname,
            name: data.name,
            fiscalCode: data.fiscalCode,
            email: data.email,
            company: data.company
        });
    }

    static toDto(data: Applicant, conferenceType?: string) {
        return {
            idApplicant: data.id,
            conferenceType: conferenceType
                ? { key: conferenceType, value: '' }
                : null,
            name: data.name,
            surname: data.surname,
            email: data.email,
            fiscalCode: data.fiscalCode,
            company: data.company
        };
    }
}
