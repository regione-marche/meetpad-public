import { BaseModel } from '@eng-ds/ng-toolkit';
import { BaseFile, ComboBox } from '@common';
//import { environment } from '@env/environment';

export class Preaccreditation extends BaseModel {

    id: any;
    name: string;
    surname: string;
    email: string;
    fiscalCode: string;
    pec: boolean;
    conferenceType: ComboBox<string>;
    authority: ComboBox;
    profileType: ComboBox<string> | string;
    documentName: string;
    url: string;
    file: File;

    constructor(preaccreditation?: Partial<Preaccreditation>) {
        super();
        if (preaccreditation) {
            this.id = preaccreditation.id;
            this.surname = preaccreditation.surname;
            this.name = preaccreditation.name;
            this.email = preaccreditation.email;
            this.pec = preaccreditation.pec;
            this.fiscalCode = preaccreditation.fiscalCode;
            this.authority = preaccreditation.authority;
            this.profileType = preaccreditation.profileType;
            this.documentName = preaccreditation.documentName;
            this.url = preaccreditation.url;
            this.file = preaccreditation.file;
        }
    }

    static fromDto(data: any) {
        return new Preaccreditation({
            id: data.idPreaccreditation,
            surname: data.surname,
            name: data.name,
            fiscalCode: data.fiscalCode,
            email: data.email,
            pec:data.pec,
            authority: data.authority,
            profileType: data.profileType,
            documentName: data.documentName,
            url: data.url,
            file: data.file
        });
    }

    static toDto(data: Preaccreditation, conferenceType?: string) {
        return {
            idPreaccreditation: data.id,
            conferenceType: conferenceType
                ? { key: conferenceType, value: '' }
                : null,
            name: data.name,
            surname: data.surname,
            email: data.email,
            pec:data.pec,
            fiscalCode: data.fiscalCode,
            authority: data.authority,
            profileType: data.profileType,
            documentName: data.file ? data.file.name : null,
            file: data.file
        };
    }
}
