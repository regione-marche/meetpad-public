import { ComboBox } from '@common';
import { environment } from '@env/environment';

export class MeetAddress {
    type: ComboBox = Object.assign(
        {},
        environment.defaultComboBox.conference.definition.instance.address.type
    );

    address: string = '';
    cap: string = '';
    city: ComboBox = Object.assign({}, environment.defaultComboBox.city);
    province: ComboBox = Object.assign(
        {},
        environment.defaultComboBox.province
    );

    constructor(ma: Partial<MeetAddress>) {
        if (ma) {
            this.type = ma.type
                ? ma.type
                : Object.assign(
                      {},
                      environment.defaultComboBox.conference.definition.instance
                          .address.type
                  );
            this.address = ma.address;
            this.cap = ma.cap;
            this.city = ma.city;
            this.province = ma.province;
        }
    }

    update(ma: MeetAddress): void {
        if (ma) {
            this.type = ma.type
                ? ma.type
                : Object.assign(
                      {},
                      environment.defaultComboBox.conference.definition.instance
                          .address.type
                  );
            this.address = ma.address;
            this.cap = ma.cap;
            this.city = ma.city;
            this.province = ma.province;
        }
    }
}
