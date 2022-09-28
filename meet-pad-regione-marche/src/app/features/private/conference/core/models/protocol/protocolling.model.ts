/* tslint:disable:no-inferrable-types */
import { DateModel, BaseModel } from '@eng-ds/ng-toolkit';
import { ComboBox } from '@common';
import { environment } from '@env/environment';
import { debug } from 'util';

export class Protocol extends BaseModel {
    id: any;
    error: string = '';
    documentName: string = '';
    protocolNumber: string = '';
    protocolDate: DateModel | string = new DateModel('');
    protocolState: string = '';
    idConference: any;
    event: string = '';
    

    constructor(protocol?: Partial<Protocol>) {
        super();
        if (protocol) {
            
            this.id = protocol.id;
            this.error = protocol.error;
            this.documentName = protocol.documentName;
            this.protocolNumber= protocol.protocolNumber;
            this.protocolDate = new DateModel(protocol.protocolDate as string);
            this.protocolState = protocol.protocolState;
            this.idConference = protocol.idConference;
            this.event = protocol.event;
               
        }
    }

    static fromDto(data: any) {
        return new Protocol({
            id: data.idProtocollo,
            error: data.error,
            documentName: data.documentName,
            protocolNumber: data.protocolNumber,
            protocolDate: data.protocolDate,
            protocolState: data.protocolState, 
            idConference: data.idConference, 
            event: data.event,           
        });
    }

    update(protocol: Protocol): void {
        this.protocolState = protocol.protocolState;
    }
}