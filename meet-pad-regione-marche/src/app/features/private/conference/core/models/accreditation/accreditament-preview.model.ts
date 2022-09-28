import { BaseModel } from '@eng-ds/ng-toolkit';
import { ComboBox } from '@common';
import { environment } from '@env/environment';

export class AccreditamentPreview extends BaseModel {
    id: string = '';
    inviteFlag: boolean = false;
    accreditamentFlag: boolean = false;
    email: string = '';
    fiscalCode: string = '';
    name: string = '';
    surname: string = '';
    profile: ComboBox =
        environment.defaultComboBox.conference.accreditation.role;
    file: File;
    idConference: string = '';
    idParticipant: string = '';
    participantDescription: string = '';
    token1: string = '';
    token2: string = '';
    rejectedFlag: boolean = false;
    rejectedDescription: string = '';
    user: string = '';
    url: string = '';
    // creationDate: DateModel = new DateModel(new Date());
    creationDate: string = '';

    constructor(accreditation?: Partial<AccreditamentPreview>) {
        super();
        this._assign(accreditation);
    }

    _assign(accreditation: Partial<AccreditamentPreview>): void {
        if (accreditation.id) {
            this.id = accreditation.id;
        }
        if (accreditation.accreditamentFlag) {
            this.accreditamentFlag = accreditation.accreditamentFlag;
        }
        if (accreditation.inviteFlag) {
            this.inviteFlag = accreditation.inviteFlag;
        }
        if (accreditation.email) {
            this.email = accreditation.email;
        }
        if (accreditation.fiscalCode) {
            this.fiscalCode = accreditation.fiscalCode;
        }
        if (accreditation.name) {
            this.name = accreditation.name;
        }
        if (accreditation.surname) {
            this.surname = accreditation.surname;
        }
        if (accreditation.file) {
            this.file = accreditation.file;
        }
        if (accreditation.idConference) {
            this.idConference = accreditation.idConference;
        }
        if (accreditation.idParticipant) {
            this.idParticipant = accreditation.idParticipant;
        }
        if (accreditation.token1) {
            this.token1 = accreditation.token1;
        }
        if (accreditation.token2) {
            this.token2 = accreditation.token2;
        }
        if (accreditation.rejectedFlag) {
            this.rejectedFlag = accreditation.rejectedFlag;
        }
        if (accreditation.rejectedDescription) {
            this.rejectedDescription = accreditation.rejectedDescription;
        }

        if (accreditation.name && accreditation.surname) {
            this.user = accreditation.name + ' ' + accreditation.surname;
        }
        if (accreditation.url) {
            this.url = accreditation.url;
        }
        if (accreditation.participantDescription) {
            this.participantDescription = accreditation.participantDescription;
        }
        if (accreditation.creationDate) {
            this.creationDate = accreditation.creationDate;
        }
        if (accreditation.profile) {
            this.profile = accreditation.profile;
        }
    }

    update(accreditation: AccreditamentPreview): void {
        this._assign(accreditation);
    }

    // tslint:disable-next-line: member-ordering
    static fromDto(data: any) {
        return new AccreditamentPreview({
            id: data.id,
            accreditamentFlag: data.accreditamentFlag,
            inviteFlag: data.inviteFlag,
            email: data.email,
            fiscalCode: data.fiscalCode,
            name: data.name,
            surname: data.surname,
            profile: data.profile,
            idConference: data.idConference,
            idParticipant: data.idParticipant,
            rejectedFlag: data.rejectedFlag,
            rejectedDescription: data.rejectedDescription,
            url: data.url,
            participantDescription: data.participantDescription
        });
    }

    setFile(_file: File) {
        this.file = _file;
    }
}
