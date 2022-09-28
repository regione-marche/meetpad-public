import { FormControl } from '@angular/forms';

import { ActionForm } from '@app/core';

import { FormFieldGroup, FormField } from '@common';

import { IntegrationClosed } from '@features/private/conference/core';
import { EventGroupFieldsService } from '../event-group-fields.service';

export function integrationClosed(
    this: EventGroupFieldsService,
    mode: ActionForm,
    model: IntegrationClosed = new IntegrationClosed()
): Map<string, FormFieldGroup> {
    const gf: Map<string, FormFieldGroup> = new Map();

    if (!(model instanceof IntegrationClosed)) {
        model = new IntegrationClosed(model);
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
                    .set('subject', {
                        type: 'text',
                        label: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.SUBJECT',
                        control: new FormControl({
                            value: model.subject.value,
                            disabled: true
                        }),
                        size: '12|6|4'
                    })
                    .set('type', {
                        type: 'text',
                        label: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.TYPE',
                        control: new FormControl({
                            value: model.type.value,
                            disabled: true
                        }),
                        size: '12|6|4'
                    })
                    .set('date', {
                        type: 'date',
                        label: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.DATE',
                        control: new FormControl({
                            value: model.date,
                            disabled: true
                        }),
                        size: '12|6|4'
                    })
            });
            break;
    }

    return gf;
}
