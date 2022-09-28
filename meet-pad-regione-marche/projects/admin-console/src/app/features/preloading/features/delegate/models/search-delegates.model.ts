import { BaseModel } from '@eng-ds/ng-toolkit';
import { ComboBox } from '@common';

export class SearchDelegate extends BaseModel {
    id: any;
    name: string;
    surname: string;
    fiscalCode: string;
    conferenceType: ComboBox<string> | string;
    documentName: string;

    constructor(delegate?: Partial<SearchDelegate>) {
        super();
        if (delegate) {
            this.id = delegate.id;
            this.name = delegate.name;
            this.surname = delegate.surname;
            this.fiscalCode = delegate.fiscalCode;
            this.conferenceType = delegate.conferenceType;
            this.documentName = delegate.documentName;
        }
    }

    static fromDto(data: any) {
        return new SearchDelegate({
            id: data.idDelegate,
            name: data.name,
            surname: data.surname,
            fiscalCode: data.fiscalCode,
            conferenceType: data.conferenceType,
            documentName: data.documentName
        });
    }
}
