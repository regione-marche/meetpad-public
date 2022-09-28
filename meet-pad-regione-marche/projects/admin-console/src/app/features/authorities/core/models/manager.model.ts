import { BaseModel } from '@eng-ds/ng-toolkit';
import { ComboBox } from '@common';

export class Manager extends BaseModel {
    id: any;
    fiscalCode: string;
    name: string;
    pec: string = '';
    prosecutingAdministration: ComboBox<string> | string;
    surname: string;

    constructor(manager?: Partial<Manager>) {
        super();
        if (manager) {
            this.id = manager.id;
            this.name = manager.name;
            this.surname = manager.surname;
            this.prosecutingAdministration = manager.prosecutingAdministration;
            this.fiscalCode = manager.fiscalCode;
        }
    }

    static fromDto(data: any) {
        return new Manager({
            id: data.idPerson,
            name: data.name,
            surname: data.surname,
            prosecutingAdministration: data.prosecutingAdministration,
            fiscalCode: data.fiscalCode
        });
    }

    static toDto(data: any) {
        return {
            name: data.name,
            surname: data.surname,
            prosecutingAdministration: data.prosecutingAdministration,
            fiscalCode: data.fiscalCode,
            email: data.pec
        };
    }
}
