import { FormControl } from '@angular/forms';

import { ActionForm, EventType, ConferenceType } from '@app/core';

import { FormFieldGroup, FormField, BaseFile } from '@common';
import { ConferenceIndiction } from '@features/private/conference/core';

import { EventGroupFieldsService } from '../event-group-fields.service';

export function conferenceIndiction(
    this: EventGroupFieldsService,
    mode: ActionForm,
    model: ConferenceIndiction = new ConferenceIndiction()
): Map<string, FormFieldGroup> {
    const gf: Map<string, FormFieldGroup> = new Map();

    if (!(model instanceof ConferenceIndiction)) {
        model = new ConferenceIndiction(model);
    }
    switch (mode) {
        case ActionForm.READONLY:
            gf.set('head', {
                panel: true,
                panelHead: false,
                fields: new Map<string, FormField>()
                    .set('sender', {
                        type: 'text',
                        label: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.SENDER',
                        control: new FormControl({
                            value: model.sender,
                            disabled: true
                        }),
                        size: '12|12|12'
                    })
                    .set('type', {
                        type: 'text',
                        label: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.TYPE',
                        control: new FormControl({
                            value: model.type.value,
                            disabled: true
                        }),
                        size: '12|6|6'
                    })
                    .set('date', {
                        type: 'date',
                        label: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.DATE',
                        control: new FormControl({
                            value: model.date,
                            disabled: true
                        }),
                        size: '12|6|6'
                    })
            }).set('document', {
                panel: true,
                panelHead: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.ATTACHED_FILE',
                fields: new Map<string, FormField>()
                    .set('category', {
                        type: 'select',
                        label:
                            'CONFERENCE.WIZARD.DOCUMENTATION.ADDITIONAL.FILE.CATEGORY',
                        control: new FormControl({
                            value: model.document.category,
                            disabled: true
                        }),
                        size: '12|6|6',
                        options: this.utilityService.getCategoriesFile(
                            this.conferenceType,
                            EventType.CONFERENCE_INDICTION
                        )
                    })
                    .set('protocolNumber', {
                        type: 'text',
                        label:
                            'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.PROTOCOL_NUMBER',
                        control: new FormControl({
                            value: model.document.protocolNumber,
                                // xmf enabled just for BULs
                                disabled: this.conferenceStore.conference.definition.instance.conferenceType.key!=ConferenceType.BROADBAND
                                            && this.conferenceStore.conference.definition.instance.conferenceType.key!=ConferenceType.REGIONAL
                        }),
                        size: '12|6|6'
                    })
                    .set('protocolDate', {
                        type: 'date',
                        label:
                            'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.PROTOCOL_DATE',
                        control: new FormControl({
                            value: model.document.protocolDate,
                                // xmf enabled just for BULs
                                disabled: this.conferenceStore.conference.definition.instance.conferenceType.key!=ConferenceType.BROADBAND
                                            && this.conferenceStore.conference.definition.instance.conferenceType.key!=ConferenceType.REGIONAL
                        }),
                        size: '12|6|6'
                    })
                    .set('file', {
                        type: 'file',
                        label:
                            'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.ATTACHED_FILE',
                        readonly: true,
                        file: new BaseFile({
                            url: model.document.url,
                            name: model.document.name
                        }),
                        onDownload: (file: BaseFile) => {
                            this.fileDownload(file);
                        },
                        size: '12|6|6'
                    })
            });
            break;
    }

    return gf;
}
