/* tslint:disable:no-inferrable-types */
import { ComboBox } from '@common';
import { environment } from '@env/environment';

export class Company {
    denomination: string = '';
    fiscalCode: string = '';
    vatNumber: string = '';
    id: string = '';

    legalForm: ComboBox = Object.assign(
        {},
        environment.defaultComboBox.conference.procedure.legalForm
    );
    area: ComboBox = Object.assign({}, environment.defaultComboBox.area);
    province: ComboBox = Object.assign(
        {},
        environment.defaultComboBox.province
    );
    city: ComboBox = Object.assign({}, environment.defaultComboBox.city);

    address: string = '';

    constructor(company: Partial<Company>) {
        if (company) {
            this.id = company.id;
            this.denomination = company.denomination;
            this.fiscalCode = company.fiscalCode;
            this.vatNumber = company.vatNumber;
            this.legalForm = company.legalForm;
            this.area = company.area;
            this.province = company.province;
            this.city = company.city;
            this.address = company.address;
        }
    }

    getFormattedData(): string {
        let _r = '';
        if (
            this.denomination &&
            (this.vatNumber || this.fiscalCode) &&
            this.address &&
            (this.city && this.city.value) &&
            (this.province && this.province.value)
        ) {
            _r = this.denomination;

            if (this.vatNumber) {
                _r += `\nP.Iva: ${this.vatNumber}`;
            }

            if (this.fiscalCode) {
                _r += `\nCF: ${this.fiscalCode}`;
            }

            if (this.address) {
                _r += `\n${this.address}`;
            }

            if (this.city && this.city.value) {
                _r += ` - ${this.city.value}`;
            }

            if (this.province && this.province.value) {
                if (
                    (this.city &&
                        this.city.value &&
                        this.city.value !== this.province.value) ||
                    !(this.city && this.city.value)
                ) {
                    _r += ` - ${this.province.value}`;
                }
            }
        }

        return _r;
    }

    update(company: Partial<Company>) {
        if (company) {
            this.denomination = company.denomination;
            this.fiscalCode = company.fiscalCode;
            this.vatNumber = company.vatNumber;
            this.legalForm = company.legalForm;
            this.area = company.area;
            this.province = company.province;
            this.city = company.city;
            this.address = company.address;
        }
    }
}
