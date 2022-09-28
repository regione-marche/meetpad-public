import { BaseModel } from '@eng-ds/ng-toolkit';
import { ComboBox } from '@common';

export class Participant extends BaseModel {
    id: any;
    authority: ComboBox<string> | string;
    conferenceType: ComboBox<string> | string;
    competenceAuthorization: ComboBox<string>[] | string;
    participantRole: ComboBox<string> | string;

    constructor(participant?: Partial<Participant>) {
        super();
        if (participant) {
            this.id = participant.id;
            this.conferenceType = participant.conferenceType;
            this.authority = participant.authority;
            this.competenceAuthorization = participant.competenceAuthorization;
            this.participantRole = participant.participantRole;
        }
    }

    static fromDto(data: any) {
        return new Participant({
            id: data.idPreemptiveParticipant,
            conferenceType: data.conferenceType,
            authority: data.authority,
            competenceAuthorization: data.competenceAuthorization,
            participantRole: data.role
        });
    }

    static toDto(data: Participant, conferenceType?: string) {
        return {
            id: data.id,
            conferenceType: { key: conferenceType },
            authority: data.authority,
            competenceAuthorization: data.competenceAuthorization,
            participantRole: data.participantRole
        };
    }
}
