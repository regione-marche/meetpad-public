import { BaseModel } from '@eng-ds/ng-toolkit';

export class ConferenceType extends BaseModel {
    id: any;
    name: string;

    constructor(conferenceType: Partial<ConferenceType>) {
        super();
        this.id = conferenceType.id;
        this.name = conferenceType.name;
    }

    static fromDto(data: any) {
        return new ConferenceType({
            id: data.key,
            name: data.value
        });
    }
}
