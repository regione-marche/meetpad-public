/* tslint:disable:no-inferrable-types */
import { ComboBox } from '@common';
import { BaseModel } from '@eng-ds/ng-toolkit';

export class Company extends BaseModel {
    name: string;
    fiscalCode: string;
    vatNumber: string;
    id: any;

    legalForm: ComboBox;

    area: ComboBox;
    province: ComboBox;
    city: ComboBox;
    conferenceType: number;
    editableConferenceStep: boolean = false;
    stepList: ComboBox[] = [];
    address: string;

    constructor(company?: Partial<Company>) {
        super();
        if (company) {
            this.id = company.id;
            this.name = company.name;
            this.fiscalCode = company.fiscalCode;
            this.vatNumber = company.vatNumber;
            this.legalForm = company.legalForm;
            this.area = company.area;
            this.province = company.province;
            this.city = company.city;
            this.address = company.address;
            this.conferenceType = company.conferenceType;
            this.editableConferenceStep = company.editableConferenceStep;
            this.stepList = company.stepList;
        }
    }

    static fromDto(data: any) {
        const _sL = [];
        data.stepList.forEach(value => {
            switch (value) {
                case 0:
                    _sL.push({ key: value, value: 'Pratica' });
                    break;
                case 1:
                    _sL.push({
                        key: value,
                        value: 'Definizione'
                    });
                    break;
                case 2:
                    _sL.push({
                        key: value,
                        value: 'Partecipanti'
                    });
                    break;
                case 3:
                    _sL.push({
                        key: value,
                        value: 'Documentazione'
                    });
                    break;
                default:
                    break;
            }
        });
        return new Company({
            id: data.idPreemptiveCompany,
            name: data.name,
            fiscalCode: data.fiscalCode,
            vatNumber: data.vatNumber,
            legalForm: data.legalForm,
            area: data.area,
            province: data.province,
            city: data.city,
            address: data.address,
            conferenceType: data.conferenceType,
            editableConferenceStep: data.editableConferenceStep,
            stepList: _sL
        });
    }

    static fromDtoForNewCompany(data: any) {
        return new Company({
            id: data.idPreemptiveCompany,
            name: data.denomination,
            fiscalCode: data.fiscalCode,
            vatNumber: data.vatNumber,
            legalForm: data.legalForm,
            area: data.area,
            province: data.province,
            city: data.city,
            address: data.address,
            conferenceType: data.conferenceType,
            editableConferenceStep: data.editableConferenceStep,
            stepList: data.stepList
        });
    }

    static toDto(data: Company, conferenceType?: number) {
        const _sL = [];
        data.stepList.forEach(value => {
            _sL.push(+value.key);
        });
        return {
            id: data.id,
            name: data.name,
            fiscalCode: data.fiscalCode,
            vatNumber: data.vatNumber,
            legalForm: data.legalForm,
            area: data.area,
            province: data.province,
            city: data.city,
            address: data.address,
            conferenceType: conferenceType
                ? { key: conferenceType, value: '' }
                : null,
            editableConferenceStep: data.editableConferenceStep,
            stepList: _sL
        };
    }
}
