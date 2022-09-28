import { ComboBox } from '@common';

export class DomusConference {
    folderCode: string;
    folderDescription: string;
    conferenceType: ComboBox;
    conferenceId?: string;
    conferenceManagersDomus: ComboBox;

    constructor(data?: Partial<DomusConference>) {
        if (data) {
            this.folderCode = data.folderCode;
            this.folderDescription = data.folderDescription;
            this.conferenceType = data.conferenceType;
            this.conferenceId = data.conferenceId;
            this.conferenceManagersDomus = data.conferenceManagersDomus;
        }
    }

    toDto() {
        const _that: any = Object.assign({}, this);
        _that.conferenceType = _that.conferenceType.key;
        return _that;
    }
}
