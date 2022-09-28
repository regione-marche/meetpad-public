import { BaseModel } from '@eng-ds/ng-toolkit';
import { ComboBox } from '@common';

export class SearchParticipant extends BaseModel {
    id: any;
    email: string;
    authority: ComboBox<string> | string;
    conferenceType: ComboBox<string> | string;

    constructor(participant?: Partial<SearchParticipant>) {
        super();
        if (participant) {
            this.id = participant.id;
            this.email = participant.email;
            this.conferenceType = participant.conferenceType;
            this.authority = participant.authority;
        }
    }

    static fromDto(data: any) {
        return new SearchParticipant({
            id: data.id,
            email: data.email,
            conferenceType: data.conferenceType,
            authority: data.authority
        });
    }
}
