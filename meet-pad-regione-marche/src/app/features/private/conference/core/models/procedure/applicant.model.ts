/* tslint:disable:no-inferrable-types */
import { DateModel, BaseModel } from '@eng-ds/ng-toolkit';
import { ComboBox } from '@common';

import { Moment } from 'moment';
import * as moment from 'moment';

import { environment } from '@env/environment';

export class Applicant extends BaseModel {
    today: Moment = moment(new Date());

    id: any;
    requestReference: string = '';
    startDate: DateModel | string = new DateModel(this.today);
    name: string = '';
    surname: string = '';

    activity: ComboBox = Object.assign(
        {},
        environment.defaultComboBox.conference.procedure.activity
    );
    type: ComboBox = Object.assign(
        {},
        environment.defaultComboBox.conference.procedure.type
    );
    action: ComboBox = Object.assign(
        {},
        environment.defaultComboBox.conference.procedure.action
    );

    fiscalCode: string = '';
    pec: string = '';

    constructor(applicant: Partial<Applicant>) {
        super();
        if (applicant) {
            this.requestReference = applicant.requestReference;
            this.startDate = new DateModel(applicant.startDate as string);
            this.name = applicant.name;
            this.type = applicant.type;
            this.surname = applicant.surname;
            this.activity = applicant.activity;
            this.fiscalCode = applicant.fiscalCode;
            this.action = applicant.action;
            this.pec = applicant.pec;

            if (!applicant.type) {
                this.type = Object.assign(
                    {},
                    environment.defaultComboBox.conference.procedure.type
                );
            }

            if (!applicant.action) {
                this.action = Object.assign(
                    {},
                    environment.defaultComboBox.conference.procedure.action
                );
            }

            if (!applicant.activity) {
                this.activity = Object.assign(
                    {},
                    environment.defaultComboBox.conference.procedure.activity
                );
            }
        }
    }

    update(applicant: Partial<Applicant>) {
        if (applicant) {
            this.requestReference = applicant.requestReference;
            this.startDate = applicant.startDate;
            this.name = applicant.name;
            this.type = applicant.type;
            this.surname = applicant.surname;
            this.activity = applicant.activity;
            this.fiscalCode = applicant.fiscalCode;
            this.action = applicant.action;
            this.pec = applicant.pec;
        }
    }

    toDto(): Applicant {
        const _that = Object.assign({}, this);
        _that.startDate = (this.startDate as DateModel).date.format(
            'DD-MM-YYYY'
        );
        delete _that.today;
        return _that;
    }

    setPreliminaryType(): void {
        this.type = Object.assign(
            {},
            environment.defaultComboBox.conference.procedure.preliminaryType
        );
    }
}
