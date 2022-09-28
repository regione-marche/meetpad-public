import { ComboBox } from '@common';

import { environment } from '@env/environment';

export class SearchAdvancedCriteria {
    private _requestReference: string;
    private _fiscalCode: string;

    private _conferenceType: ComboBox =
        environment.defaultComboBox.advancedSearch.conferenceType;
    private _state: ComboBox = environment.defaultComboBox.advancedSearch.state;

    private _province: ComboBox = environment.defaultComboBox.province;
    private _city: ComboBox = environment.defaultComboBox.city;

    constructor(criteria?: Partial<SearchAdvancedCriteria>) {
        if (criteria) {
            this.requestReference = criteria.requestReference;
            this.fiscalCode = criteria.fiscalCode;
            this.conferenceType = criteria.conferenceType;
            this.state = criteria.state;
            this.province = criteria.province;
            this.city = criteria.city;
        }
    }

    set requestReference(value: string) {
        this._requestReference = value || '';
    }

    set fiscalCode(value: string) {
        this._fiscalCode = value || '';
    }

    set conferenceType(value: ComboBox) {
        if (value && value.key && value.key === 'all') {
            this._conferenceType = { key: '' };
        } else {
            this._conferenceType = value;
        }
    }

    set state(value: ComboBox) {
        if (value && value.key && value.key === 'all') {
            this._state = { key: '' };
        } else {
            this._state = value;
        }
    }

    set province(value: ComboBox) {
        this._province = value;
    }

    set city(value: ComboBox) {
        this._city = value;
    }

    get requestReference() {
        return this._requestReference;
    }
    get fiscalCode() {
        return this._fiscalCode;
    }
    get conferenceType() {
        return this._conferenceType;
    }
    get state() {
        return this._state;
    }
    get province() {
        return this._province;
    }
    get city() {
        return this._city;
    }
}
