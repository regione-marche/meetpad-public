import { BaseModel, DateModel } from '@eng-ds/ng-toolkit';
import { BaseFile, ComboBox, ConferenceRole } from '@common';
import { Owner } from '../../conference/core/models/documentation/owner.model';
import { ItemsList } from '@ng-select/ng-select/ng-select/items-list';

export class SearchSign extends BaseModel {
   
    id: number;
    idConference: number;
    requestReference: string;
    name: string;
    type: ComboBox;
    documentType: string;
    eventType: ComboBox;
    protocolNumber: string;
    protocolDate: DateModel = new DateModel('');
    status: string;
    owner: Owner;
    selected: boolean;
    totalNumber: string;
    visibility: ComboBox[];
    recipients: string;
    title: string;
    url: string;
    denomination: string;
    documentsAttachment: BaseFile[];

    constructor(document?: Partial<SearchSign>) {
           super();
           if (document) {
            this.id = document.id;
            this.idConference = document.idConference;
            this.requestReference = document.requestReference;
            this.name = document.name;
            this.type = document.type;
            this.denomination = document.denomination;
            this.protocolNumber = document.protocolNumber;
            if (document.protocolDate) {
                this.protocolDate = new DateModel(
                    document.protocolDate.toString()
                );
            }
           this.status = document.status;
           this.owner = document.owner;
           this.totalNumber = document.totalNumber;
           this.visibility = document.visibility;
           this.eventType = document.eventType;
           this.documentsAttachment = document.documentsAttachment;
           this.title = document.idConference.toString().concat(" - ").concat(document.type.value.toString()).concat(" - ").concat(document.requestReference.toString());
           if(this.eventType !== undefined && this.eventType !== null && this.eventType.value)
                this.title= this.title.concat(' - ').concat(document.eventType.value.toString());
            if(this.denomination)
                 this.title= this.title.concat(' - ').concat(document.denomination.toString());
            this.url = document.url;
            for (var i = 0; i <document.visibility.length; i++) {
                if(this.recipients)
                    this.recipients = this.recipients.concat('; ').concat(document.visibility[i].value);
                else
                    this.recipients = document.visibility[i].value;
            }
        }
    }

    static fromDto(data: any) {
        return new SearchSign({
            id: data.id,
            idConference: data.idConference,
            requestReference: data.requestReference,
            name: data.name,
            type: data.type,
            eventType: data.eventType,
            visibility: data.visibility,
            protocolNumber: data.protocolNumber,
            protocolDate: data.protocolDate,
            status: data.status,
            owner: data.owner,
            url: data.url,
            documentsAttachment: data.documentsAttachment,
            denomination: data.denomination,
            selected: data.selected,
            totalNumber: data.totalNumber
        });
    }

    toggleSelection(){
        this.selected = !this.selected
    }

    isSelected(){
        return this.selected
    }

    getUser(){
        return this.owner === null ? '---' : this.owner.name.toString().concat(" ").concat(this.owner.surname.toString())
    }

    getDocumentIstance(){
        return this.idConference.toString().concat("-").concat(this.requestReference.toString());
    }
}
