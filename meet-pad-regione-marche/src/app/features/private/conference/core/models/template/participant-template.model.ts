import { ComboBox } from '@common';

export class ParticipantTemplate {
    competence: ComboBox[];
    constructor(template: Partial<ParticipantTemplate>) {
        if (template) {
            this.competence = template.competence;
        }
    }

    /**
     * Se competence è vuoto allora il
     * rispettivo campo è testuale
     */
    isCompetenceText(): boolean {
        return this.competence.length === 0;
    }

    /**
     * Se il campo competence contiene una lista
     * di elementi allora il campo è di tipo select
     */
    isCompetenceSelect(): boolean {
        return this.competence.length > 0;
    }

    extract(): ParticipantTemplate {
        return this;
    }
}
