import { BaseModel } from '@eng-ds/ng-toolkit';

export class SearchCompany extends BaseModel {
    id: any;
    idPreemptiveCompany: any;
    vatNumber: string;
    name: string;
    fiscalCode: string;

    constructor(company?: Partial<SearchCompany>) {
        super();
        if (company) {
            this.id = company.id;
            this.idPreemptiveCompany = company.idPreemptiveCompany;
            this.name = company.name;
            this.fiscalCode = company.fiscalCode;
            this.vatNumber = company.vatNumber;
        }
    }

    static fromDto(data: any) {
        return new SearchCompany({
            id: data.idCompany,
            idPreemptiveCompany: data.idPreemptiveCompany,
            name: data.name,
            fiscalCode: data.fiscalCode,
            vatNumber: data.vatNumber
        });
    }
}
