import { BaseModel } from '@eng-ds/ng-toolkit';

// const id = (): string => {
//     return (
//         '_' +
//         Math.random()
//             .toString(36)
//             .substr(2, 9)
//     );
// };

export class SupportContact extends BaseModel {
    id: any;
    name: string = '';
    surname: string = '';
    email: string = '';
    phone: string = '';

    constructor(supportContact?: Partial<SupportContact>) {
        super();
        if (supportContact) {
            this.id = supportContact.id;
            this.name = supportContact.name;
            this.surname = supportContact.surname;
            this.email = supportContact.email;
            this.phone = supportContact.phone;
        }
    }

    update(sc: SupportContact): void {
        if (sc) {
            this.id = sc.id;
            this.name = sc.name;
            this.surname = sc.surname;
            this.email = sc.email;
            this.phone = sc.phone;
        }
    }
}
