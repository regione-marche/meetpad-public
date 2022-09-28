/* tslint:disable:no-inferrable-types */
import { DateModel, BaseModel } from '@eng-ds/ng-toolkit';
import { ComboBox } from '@common';
import { EventAttach } from './event-attachment.model';

export class Event extends BaseModel {
    id: any;

    date: DateModel | string;
    type: ComboBox;

    sender: string;
    subject: ComboBox;

    //xmf
    attaches: EventAttach[] = [];
    attachedFiles: Array<File> = [];

    multipleSign: boolean ; 

    //sign
    statusDocument: string;

    newDateTermine?: DateModel | string = new DateModel('');
    newDateTermineEsprPareri?: DateModel | string = new DateModel('');
    newDateTermineRichInteg?: DateModel | string = new DateModel('');
    newDatePrimaSessioneSimultanea?: DateModel | string = new DateModel('');
    attachmentFlag: boolean = false;

    constructor(event?: Partial<Event>) {
        super();
        if (event) {
            this.id = event.id;
            this.date = new DateModel(event.date as string);
            this.type = event.type;
            this.sender = event.sender;
            this.subject = event.subject;
            this.attaches = event.attaches;
            this.multipleSign = event.multipleSign; 
            this.statusDocument = event.statusDocument;
        }
    }

    static fromDto(data: any) {
        return new Event({
            id: data.id,
            date: data.date,
            type: data.type,
            sender: data.sender,
            subject: data.subject,
            attaches: data.attaches,
            statusDocument: data.statusDocument
        });
    }

    toDto(): Event {
        const _that = Object.assign({}, this);
        _that.date = (this.date as DateModel).date.format('DD-MM-YYYY');
        return _that;
    }

    setMultiSign(_multipleSign: boolean) {
        this.multipleSign = _multipleSign;
    }
}
