import { ConferenceType, ActionForm } from '@app/core';

import { Applicant, Company } from '../procedure';

import { ProcedureTemplate } from './procedure-template.model';
import { DefinitionTemplate } from './definition-template.model';
import { ParticipantTemplate } from './participant-template.model';

export interface ConferenceTemplateToApply {
    type?: ConferenceType;
    procedure: {
        applicant: Applicant;
        company: Company;
    };
    definition: DefinitionTemplate;
    participant: ParticipantTemplate;
}

export class ConferenceTemplate {
    procedure: ProcedureTemplate;
    definition: DefinitionTemplate;
    participant: ParticipantTemplate;

    constructor(conferenceTemplate: Partial<ConferenceTemplate>) {
        if (conferenceTemplate) {
            this.procedure = new ProcedureTemplate(
                conferenceTemplate.procedure
            );
            this.definition = new DefinitionTemplate(
                conferenceTemplate.definition
            );
            this.participant = new ParticipantTemplate(
                conferenceTemplate.participant
            );
        }
    }

    extract(
        {
            conferenceAction,
            companyId
        }: {
            conferenceAction?: ActionForm;
            companyId: string;
        } = { conferenceAction: ActionForm.EDIT, companyId: null }
    ): ConferenceTemplateToApply {
        return {
            procedure:
                conferenceAction === ActionForm.CREATE
                    ? this.procedure.extract(companyId)
                    : null,
            definition: this.definition.extract(),
            participant: this.participant.extract()
        };
    }
}
