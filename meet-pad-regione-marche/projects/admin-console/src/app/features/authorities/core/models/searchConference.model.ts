import { BaseModel, DateModel } from '@eng-ds/ng-toolkit';
import { ComboBox, ConferenceRole } from '@common';

export class SearchConference extends BaseModel {
    id: string;
    address: string;
    requestReference: string;
    conferenceType: ComboBox;
    endProcedure: DateModel = new DateModel('');
    state: ComboBox;
    proceedingCompany: string;
    applicant: ComboBox;
    endIntegration: DateModel = new DateModel('');
    endDetermine: DateModel = new DateModel('');
    endNextSession: DateModel = new DateModel('');
    profile: ComboBox<ConferenceRole>;

    constructor(conference?: Partial<SearchConference>) {
        super();
        if (conference) {
            this.id = conference.id;
            this.address = conference.address;
            this.requestReference = conference.requestReference;
            this.conferenceType = conference.conferenceType;
            this.profile = conference.profile;

            if (conference.endProcedure) {
                this.endProcedure = new DateModel(
                    conference.endProcedure.toString()
                );
            }
            if (conference.endIntegration) {
                this.endIntegration = new DateModel(
                    conference.endIntegration.toString()
                );
            }
            if (conference.endDetermine) {
                this.endDetermine = new DateModel(
                    conference.endDetermine.toString()
                );
            }
            if (conference.endNextSession) {
                this.endNextSession = new DateModel(
                    conference.endNextSession.toString()
                );
            }

            this.state = conference.state;
            this.proceedingCompany = conference.proceedingCompany;
            this.applicant = conference.applicant;
        }
    }

    static fromDto(data: any) {
        return new SearchConference({
            applicant: data.applicant,
            requestReference: data.requestReference,
            conferenceType: data.conferenceType,
            endProcedure: data.endProcedureDate,
            endIntegration: data.endIntegrationDate,
            endNextSession: data.nextSessionDate,
            endDetermine: data.endDeterminesDate,
            state: data.status,
            proceedingCompany: data.proceedingCompany,
            address: data.address,
            profile: data.profile,
            id: data.id
        });
    }
}
