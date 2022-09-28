/* tslint:disable:no-inferrable-types */

import { DateModel } from '@eng-ds/ng-toolkit';
import { ComboBox } from '@common';
import { ConferenceType } from '@app/core';
import { environment } from '@env/environment';
import { MeetAddress } from './meet-address.model';
import { DateUpdate } from './update-date.model';

export class Instance {
    requestReference: string = '';

    creationDate?: DateModel | string = new DateModel('');

    conferenceType: ComboBox = Object.assign(
        {},
        environment.defaultComboBox.conference.conferenceType
    );

    endIntegrationDate?: DateModel | string = new DateModel('');
    endOpinionDate?: DateModel | string = new DateModel('');
    firstSessionDate?: DateModel | string = new DateModel('');
    expirationDate?: DateModel | string = new DateModel('');

    conferenceTime?: string = '';

    address: MeetAddress = null;
    dateUpdateList: DateUpdate[] = null;

    constructor(instance: Partial<Instance>) {
        if (instance) {
            this.requestReference = instance.requestReference || '';

            this.creationDate = new DateModel(instance.creationDate as string);
            this.endIntegrationDate = new DateModel(
                instance.endIntegrationDate as string
            );
            this.endOpinionDate = new DateModel(
                instance.endOpinionDate as string
            );
            this.firstSessionDate = new DateModel(
                instance.firstSessionDate as string
            );
            this.expirationDate = new DateModel(
                instance.expirationDate as string
            );

            if (instance.conferenceType) {
                this.conferenceType = instance.conferenceType;
            }

            this.address = new MeetAddress(instance.address);
            this.dateUpdateList = [];
            if(instance.dateUpdateList && instance.dateUpdateList.length > 0){
                instance.dateUpdateList.forEach( up => {
                    this.dateUpdateList.push( new DateUpdate(up))
                })
            }
           
            this.conferenceTime = instance.conferenceTime;
        }
    }

    update(instance: Partial<Instance>) {
        if (instance) {
            this.requestReference = instance.requestReference || '';
            this.creationDate = instance.creationDate;
            this.conferenceType = instance.conferenceType;
            this.endIntegrationDate = instance.endIntegrationDate;
            this.endOpinionDate = instance.endOpinionDate;
            this.firstSessionDate = instance.firstSessionDate;
            this.expirationDate = instance.expirationDate;
            this.conferenceTime = instance.conferenceTime;

            this.address.update(instance.address);
            this.dateUpdateList = [];
            
            if(instance.dateUpdateList && instance.dateUpdateList.length > 0){
                instance.dateUpdateList.forEach( up => {
                    this.dateUpdateList.push( new DateUpdate(up))
                })
            }
        }
    }

    toDto(): Instance {
        const _that = Object.assign({}, this);
        if (this.creationDate) {
            _that.creationDate = (this.creationDate as DateModel).date.format(
                'DD-MM-YYYY'
            );
        }
        if (
            this.endIntegrationDate &&
            (this.endIntegrationDate as DateModel).date
        ) {
            _that.endIntegrationDate = (this
                .endIntegrationDate as DateModel).date.format('DD-MM-YYYY');
        }
        if (this.endOpinionDate && (this.endOpinionDate as DateModel).date) {
            _that.endOpinionDate = (this
                .endOpinionDate as DateModel).date.format('DD-MM-YYYY');
        }
        if (
            this.firstSessionDate &&
            (this.firstSessionDate as DateModel).date
        ) {
            _that.firstSessionDate = (this
                .firstSessionDate as DateModel).date.format('DD-MM-YYYY');
        }
        if (this.expirationDate && (this.expirationDate as DateModel).date) {
            _that.expirationDate = (this
                .expirationDate as DateModel).date.format('DD-MM-YYYY');
        }
        return _that;
    }

    setType(type: ConferenceType): void {
        this.conferenceType.key = type;
    }
}
