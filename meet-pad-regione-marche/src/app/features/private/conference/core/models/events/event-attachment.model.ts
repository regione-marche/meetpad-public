import { BaseModel } from '@eng-ds/ng-toolkit';
import { ComboBox } from '@common';

import { DateModel } from '@eng-ds/ng-toolkit';

export class EventAttach extends BaseModel {
    id: any;
    attachment: File;

    constructor(event?: Partial<EventAttach>) {
        super();
        if (event) {
            this.attachment = event.attachment;
        }
    }

    static fromDto(data: any) {
        return new EventAttach({
            id: data.idUser,
            attachment: data.attachment
        });
    }
    static toDto(data: EventAttach) {
        return {
            id: data.id,
            attachment: data.attachment
        };
    }

}

export class EventDocument  {
    protocolNumber: string;
    protocolDate: DateModel;
    name: string;
    url: string;
    category: ComboBox;
    file?: File;
    visibility?: ComboBox[];
    determinationType?: ComboBox

    constructor(event?: Partial<EventDocument>) {
        if (event) {
            this.protocolNumber = event.protocolNumber;
            this.protocolDate = event.protocolDate;
            this.name = event.name;
            this.url = event.url;
            this.category = event.category;
            this.file = event.file;
            this.visibility = event.visibility;
            this.determinationType = event.determinationType;
        }
    }

    static fromDto(nameE: string, urlE: string,
                  protocolNumberE: string, protocolDateE: DateModel,
                  categoryE: ComboBox, file?: File, visibilityE?: ComboBox[],
                  determinationTypeE?: ComboBox)
         {
        return new EventDocument({
            name: nameE,
            url: urlE,
            protocolNumber: protocolNumberE,
            protocolDate: protocolDateE,
            category: categoryE,
            visibility: visibilityE,
            determinationType: determinationTypeE,
        });
    }


    
}
