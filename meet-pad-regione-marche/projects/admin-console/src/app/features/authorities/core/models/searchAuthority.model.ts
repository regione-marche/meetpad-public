import { BaseModel } from '@eng-ds/ng-toolkit';

export class SearchAuthority extends BaseModel {
    id: any;
    flagProsecutingAdministration: boolean;
    name: string;
    pec: string;

    constructor(authority?: Partial<SearchAuthority>) {
        super();
        if (authority) {
            this.id = authority.id;
            this.name = authority.name;
            this.pec = authority.pec;
            this.flagProsecutingAdministration =
                authority.flagProsecutingAdministration;
        }
    }

    static fromDto(data: any) {
        return new SearchAuthority({
            id: data.idAuthority,
            name: data.name,
            pec: data.pec,
            flagProsecutingAdministration: data.flagProsecutingAdministration
        });
    }
}
