/* tslint:disable:no-inferrable-types */
import { ComboBox } from '@common';

import { environment } from '@env/environment';

export class Localization {
    province: ComboBox = Object.assign(
        {},
        environment.defaultComboBox.province
    );
    city: ComboBox = Object.assign({}, environment.defaultComboBox.city);

    address: string = '';

    constructor(localization: Partial<Localization>) {
        if (localization) {
            this.province = localization.province;
            this.city = localization.city;
            this.address = localization.address;
        }
    }

    update(localization: Partial<Localization>) {
        if (localization) {
            this.province = localization.province;
            this.city = localization.city;
            this.address = localization.address;
        }
    }
}
