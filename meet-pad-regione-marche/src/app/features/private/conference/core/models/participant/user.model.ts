/* tslint:disable:no-inferrable-types */

import { BaseModel } from '@eng-ds/ng-toolkit';
import { ComboBox } from '@common';

import { environment } from '@env/environment';

export class User extends BaseModel {
    id: any;
    name: string = '';
    surname: string = '';
    profile: ComboBox =
        environment.defaultComboBox.conference.participants.users.profile;
    fiscalCode: string = '';
    email: string = '';
    //addressPec: boolean = false;
    principalApplicant?: boolean;

    constructor(user?: Partial<User>) {
        super();
        if (user) {
            this.id = user.id;
            this.name = user.name;
            this.surname = user.surname;
            this.profile = user.profile;
            this.fiscalCode = user.fiscalCode;
            this.email = user.email;
            //this.addressPec = user.addressPec;
            this.principalApplicant = user.principalApplicant;
        }
    }

    update(user: User): void {
        if (user) {
            this.id = user.id;
            this.name = user.name;
            this.surname = user.surname;
            this.profile = user.profile;
            this.fiscalCode = user.fiscalCode;
            this.email = user.email;
            //this.addressPec = user.addressPec;
            this.principalApplicant = user.principalApplicant;
        }
    }
}
