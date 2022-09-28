import { BaseModel } from '@eng-ds/ng-toolkit';
import { ComboBox } from '@common';

export class Authority extends BaseModel {
    id: any;
    flagProsecutingAdministration: boolean;
    name: string;
    pec: string;
    fiscalCode: string;

    area: ComboBox;
    province: ComboBox;
    city: ComboBox;

    officeCode: string;
    istatType: ComboBox;
    administrativeType: ComboBox;

    constructor(authority?: Partial<Authority>) {
        super();
        if (authority) {
            this.id = authority.id;
            this.name = authority.name;
            this.pec = authority.pec;
            this.flagProsecutingAdministration =
                authority.flagProsecutingAdministration;
            this.fiscalCode = authority.fiscalCode;
            this.area = authority.area;
            this.province = authority.province;
            this.city = authority.city;
            this.officeCode = authority.officeCode;
            this.istatType = authority.istatType;
            this.administrativeType = authority.administrativeType;
        }
    }

    static fromDto(data: any) {
        return new Authority({
            id: data.id,
            name: data.name,
            pec: data.pec,
            fiscalCode: data.fiscalCode,
            flagProsecutingAdministration: data.flagProsecutingAdministration,
            area: data.area,
            administrativeType: data.administrativeType,
            city: data.city,
            istatType: data.istatType,
            officeCode: data.officeCode,
            province: data.province
        });
    }
}
