import { BaseModel } from '@eng-ds/ng-toolkit';
import { BaseFile, ComboBox } from '@common';

export class Delegate extends BaseModel {
    id: any;
    name: string;
    surname: string;
    email: string;
    fiscalCode: string;
    conferenceType: ComboBox<string>;
    documentName: string;
    url: string;
    file: File;

    constructor(delegate?: Partial<Delegate>) {
        super();
        if (delegate) {
            this.id = delegate.id;
            this.surname = delegate.surname;
            this.name = delegate.name;
            this.email = delegate.email;
            this.fiscalCode = delegate.fiscalCode;
            this.documentName = delegate.documentName;
            this.url = delegate.url;
            this.file = delegate.file;
        }
    }

    static fromDto(data: any) {
        return new Delegate({
            id: data.idDelegate,
            surname: data.surname,
            name: data.name,
            fiscalCode: data.fiscalCode,
            email: data.email,
            documentName: data.documentName,
            url: data.url,
            file: data.file
        });
    }

    static toDto(data: Delegate, conferenceType?: string) {
        return {
            idDelegate: data.id,
            conferenceType: conferenceType
                ? { key: conferenceType, value: '' }
                : null,
            name: data.name,
            surname: data.surname,
            email: data.email,
            fiscalCode: data.fiscalCode,
            documentName: data.file ? data.file.name : null,
            file: data.file
        };
    }
}
