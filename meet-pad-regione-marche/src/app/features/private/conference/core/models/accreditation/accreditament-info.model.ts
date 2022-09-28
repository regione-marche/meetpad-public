import { AccreditamentPreview } from './accreditament-preview.model';

export class AccreditamentInfo {
    accreditation: AccreditamentPreview;
    autoAccreditation: boolean;
    idConference: string;

    constructor(accInfo?: Partial<AccreditamentInfo>) {
        if (accInfo) {
            this.accreditation = new AccreditamentPreview(
                accInfo.accreditation
            );
            this.autoAccreditation = accInfo.autoAccreditation;
            this.idConference = accInfo.idConference;
        }
    }

    existAccreditation(): boolean {
        return this.accreditation && !!this.accreditation.id;
    }

    isAccreditated(): boolean {
        return this.accreditation.accreditamentFlag;
    }
}
