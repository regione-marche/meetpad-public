import { BaseModel } from '@eng-ds/ng-toolkit';
import { ComboBox } from '@common';

export class SearchPreaccreditation extends BaseModel {
    id: any;
    name: string;
    surname: string;
    fiscalCode: string;
    conferenceType: ComboBox<string> | string;
    documentName: string;
    authority: ComboBox<string> | string;
    profileType: ComboBox<string> | string;

    constructor(preaccreditation?: Partial<SearchPreaccreditation>) {
        super();
        if (preaccreditation) {
            this.id = preaccreditation.id;
            this.name = preaccreditation.name;
            this.surname = preaccreditation.surname;
            this.fiscalCode = preaccreditation.fiscalCode;
            this.conferenceType = preaccreditation.conferenceType;
            this.documentName = preaccreditation.documentName;
            this.authority = preaccreditation.authority;
            this.profileType = preaccreditation.profileType;
        }
    }

    static fromDto(data: any) {
        return new SearchPreaccreditation({
            id: data.idAccreditation,
            name: data.name,
            surname: data.surname,
            fiscalCode: data.fiscalCode,
            conferenceType: data.conferenceType,
            documentName: data.documentName,
            authority: data.authority,
            profileType: data.profileType
        });
    }
}
