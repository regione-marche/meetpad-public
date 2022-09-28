import { BaseModel } from '@eng-ds/ng-toolkit';
import { ComboBox } from '@common';

export class User extends BaseModel {
    id: any;
    fiscalCode: string;
    name: string;
    surname: string;
    prosecutingAdministration: ComboBox<string>;
    email: string;
    flagSignatory: boolean;
    profile: ComboBox<string> = Object.assign(
        {},
        { key: '-1', value: 'Seleziona profilo' }
    );

    managersToImpersonate: ComboBox[] = [];

    constructor(user?: Partial<User>) {
        super();
        if (user) {
            this.id = user.id;
            this.name = user.name;
            this.surname = user.surname;
            this.prosecutingAdministration = user.prosecutingAdministration;
            this.fiscalCode = user.fiscalCode;
            this.email = user.email;
            this.profile = user.profile;
            this.managersToImpersonate = user.managersToImpersonate;
            this.flagSignatory = user.flagSignatory;
        }
    }

    static fromDto(data: any) {
        return new User({
            id: data.idUser,
            name: data.name,
            surname: data.surname,
            prosecutingAdministration: data.prosecutingAdministration,
            fiscalCode: data.fiscalCode,
            email: data.email,
            profile: data.profile,
            managersToImpersonate: data.managersToImpersonate,
            flagSignatory: data.flagSignatory
        });
    }
    static toDto(data: User) {
        return {
            email: data.email,
            fiscalCode: data.fiscalCode,
            lastname: data.surname,
            name: data.name,
            profile: {
                key: data.profile.key,
                value: data.profile.value
            },
            managersToImpersonate: data.managersToImpersonate,
            flagSignatory: data.flagSignatory
        };
    }

    setManagersToImpersonate(_impersonate: Array<ComboBox>) {
        this.managersToImpersonate = _impersonate;
    }

}
