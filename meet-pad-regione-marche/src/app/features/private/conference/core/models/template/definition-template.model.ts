import { ComboBox } from '@common';

import { AddressType } from '@app/core/enums/address-type.enum';
import { environment } from '@env/environment';

export enum FieldDateName {
    EXPIRATION_DATE = 'expirationDate',
    END_OPINION_DATE = 'endOpinionDate',
    END_INTEGRATION_DATE = 'endIntegrationDate',
    FIRST_SESSION_DATE = 'firstSessionDate'
}

type FieldBaseDateName =
    | 'creationDate'
    | 'endOpinionDate'
    | 'endIntegrationDate'
    | 'firstSessionDate';

export class DateRule {
    offsetBusinessDay: string;
    offsetDay: string;
    baseDate: FieldBaseDateName;
    required: boolean;
    order: string;

    constructor(rule: DateRule) {
        this.offsetBusinessDay = rule.offsetBusinessDay;
        this.offsetDay = rule.offsetDay;
        this.baseDate = rule.baseDate;
        this.required = rule.required;
        this.order = rule.order;
    }

    mustBeEmpty(): boolean {
        return (
            this.baseDate === null ||
            this.baseDate === undefined ||
            this.offsetBusinessDay === null ||
            this.offsetBusinessDay === undefined ||
            this.offsetDay === null ||
            this.offsetDay === undefined
        );
    }
}

export class InstanceTemplate {
    endIntegrationDate: DateRule;
    endOpinionDate: DateRule;
    firstSessionDate: DateRule;
    expirationDate: DateRule;
    conferenceTime: {
        visible: boolean;
    };
    address: {
        type: AddressType;
        disabled: boolean;
    };

    constructor(template: Partial<InstanceTemplate>) {
        if (template) {
            this.endIntegrationDate = new DateRule(template.endIntegrationDate);
            this.endOpinionDate = new DateRule(template.endOpinionDate);
            this.firstSessionDate = new DateRule(template.firstSessionDate);
            this.expirationDate = new DateRule(template.expirationDate);

            this.conferenceTime = template.conferenceTime;
            this.address = template.address;

            // se è null imposta il valore di default
            this.address.type = this.address.type
                ? this.address.type
                : (environment.defaultComboBox.conference.definition.instance
                      .address.type.key as AddressType);
        }
    }

    getDateRule(fieldDateName: FieldDateName): DateRule {
        return this[fieldDateName];
    }

    mustBeEmptyDate(fieldDateName: FieldDateName): boolean {
        return this.getDateRule(fieldDateName).mustBeEmpty();
    }

    mustBeMandatoryDate(fieldDateName: FieldDateName): boolean {
        const rule = this.getDateRule(fieldDateName);
        return rule.required;
    }

    getAddressTypeComboBox(): ComboBox {
        return {
            key: this.address.type
        };
    }

    getSortedDatesNotEmpty(): Map<FieldDateName, DateRule> {
        return new Map(
            Array.from(this._getMapDatesNotEmpty()).sort(
                (a: [FieldDateName, DateRule], b: [FieldDateName, DateRule]) =>
                    parseInt(a[1].order, 10) - parseInt(b[1].order, 10)
            )
        );
    }

    private _getMapDatesNotEmpty(): Map<FieldDateName, DateRule> {
        const datesMap = new Map<FieldDateName, DateRule>();

        if (!this.firstSessionDate.mustBeEmpty()) {
            datesMap.set(
                FieldDateName.FIRST_SESSION_DATE,
                this.firstSessionDate
            );
        }
        if (!this.endIntegrationDate.mustBeEmpty()) {
            datesMap.set(
                FieldDateName.END_INTEGRATION_DATE,
                this.endIntegrationDate
            );
        }
        if (!this.endOpinionDate.mustBeEmpty()) {
            datesMap.set(FieldDateName.END_OPINION_DATE, this.endOpinionDate);
        }
        if (!this.expirationDate.mustBeEmpty()) {
            datesMap.set(FieldDateName.EXPIRATION_DATE, this.expirationDate);
        }
        return datesMap;
    }

    setDates(
        callback: (
            fieldDateName: FieldDateName,
            fieldDateRule: DateRule
        ) => void
    ): void {
        const dates = this.getSortedDatesNotEmpty();
        for (const [dateName, dateRule] of dates) {
            callback(dateName, dateRule);
        }
    }
}

export class DefinitionTemplate {
    instance: InstanceTemplate;

    constructor(template: Partial<DefinitionTemplate>) {
        if (template) {
            this.instance = new InstanceTemplate(template.instance);
        }
    }

    extract(): DefinitionTemplate {
        return this;
    }
}
