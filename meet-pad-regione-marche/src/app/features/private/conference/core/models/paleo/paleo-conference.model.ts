import { ComboBox } from '@common';

import { PaleoFile } from './paleo-file.model';

import { environment } from '@env/environment';

export class PaleoConference {
    folderCode: string;
    folderDescription: string;
    conferenceType: ComboBox;
    conferenceId?: string;
    files: PaleoFile[] = [];
    manager: ComboBox = Object.assign(
        {},
        environment.defaultComboBox.conference.manager
    );
    administration?: ComboBox = Object.assign(
        {},
        environment.defaultComboBox.conference.administration
    );

    constructor(data?: Partial<PaleoConference>) {
        if (data) {
            this.folderCode = data.folderCode;
            this.folderDescription = data.folderDescription;
            this.conferenceType = data.conferenceType;
            this.conferenceId = data.conferenceId;
            this.manager = data.manager;
            this.administration = data.administration;
 
            for (let index = 0; index < data.files.length; index++) {
                this.files.push(data.files[index]);
            }
        }
    }

    toDto() {
        const _that: any = Object.assign({}, this);
        _that.manager = _that.manager.key;
        _that.administration= _that.administration.key;
        _that.conferenceType = _that.conferenceType.key;
        return _that;
    }
}
