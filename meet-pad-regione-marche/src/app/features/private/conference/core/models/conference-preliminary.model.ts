import { ComboBox } from '@common';

import { BaseModel } from '@eng-ds/ng-toolkit';

import { environment } from '@env/environment';

export class ConferencePreliminary extends BaseModel {
    id: any;
    administration?: ComboBox = Object.assign(
        {},
        environment.defaultComboBox.conference.administration
    );
    manager: ComboBox = Object.assign(
        {},
        environment.defaultComboBox.conference.manager
    );
    conferenceType: ComboBox = Object.assign(
        {},
        environment.defaultComboBox.conference.conferenceType
    );
    company: ComboBox;
}
