import { BaseModel } from '@eng-ds/ng-toolkit';
import { ComboBox } from '@common';

export class SearchUser extends BaseModel {
    id: any;
    fiscalCode: string;
    name: string;
    prosecutingAdministration: ComboBox<string> | string;
    surname: string;

    constructor(user?: Partial<SearchUser>) {
        super();
        if (user) {
            this.id = user.id;
            this.name = user.name;
            this.surname = user.surname;
            this.prosecutingAdministration = user.prosecutingAdministration;
            this.fiscalCode = user.fiscalCode;
        }
    }

    static fromDto(data: any) {
        return new SearchUser({
            id: data.idUser,
            name: data.name,
            surname: data.surname,
            prosecutingAdministration: data.prosecutingAdministration,
            fiscalCode: data.fiscalCode
        });
    }
}
