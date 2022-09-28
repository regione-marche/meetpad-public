import { FormControl, Validators } from '@angular/forms';

import { ActionForm, EventType } from '@app/core';

import { FormFieldGroup, FormField } from '@common';

import { ConferenceClosing } from '@features/private/conference/core';
import { EventGroupFieldsService } from '../event-group-fields.service';

export function conferenceClosing(
    this: EventGroupFieldsService,
    mode: ActionForm,
    model: ConferenceClosing = new ConferenceClosing()
): Map<string, FormFieldGroup> {
    const gf: Map<string, FormFieldGroup> = new Map();

    if (!(model instanceof ConferenceClosing)) {
        model = new ConferenceClosing(model);
    }

    switch (mode) {
        case ActionForm.CREATE:
            gf.set('head', {
                panel: true,
                panelHead: false,
                fields: new Map<string, FormField>()
                    .set('administration', {
                        type: 'text',
                        label:
                            'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.ADMINISTRATION',
                        control: new FormControl({
                            value: model.administration,
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
                        disabled: true,
                        size: '12|12|12'
                    })
            }).set('additionalFields', {
                panel: true,
                panelHead: false,
                fields: new Map<string, FormField>()
                    .set('result', {
                        type: 'select',
                        label: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.RESULT',
                        control: new FormControl(
                            {
                                value: model.result,
                                disabled: false
                            },
                            [Validators.required]
                        ),
                        options: this.utilityService.getConferencesResults(),
                        size: '12|12|12'
                    })
                    .set('notes', {
                        type: 'text-area',
                        label: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.NOTE',
                        control: new FormControl({
                            value: model.notes,
                            disabled: false
                        }),
                        rows: 8,
                        maxLength: 1000,
                        size: '12|12|12'
                    })
            });

            this.asyncPopulateDefaultIntegration(
                gf,
                EventType.CONFERENCE_CLOSING
            );

            break;

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
            }).set('additionalFields', {
                panel: true,
                panelHead: false,
                fields: new Map<string, FormField>()
                    .set('result', {
                        type: 'select',
                        label: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.RESULT',
                        control: new FormControl({
                            value: model.result,
                            disabled: true
                        }),
                        options: this.utilityService.getConferencesResults(),
                        size: '12|12|12'
                    })
                    .set('notes', {
                        type: 'text-area',
                        label: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.NOTE',
                        control: new FormControl({
                            value: model.notes,
                            disabled: true
                        }),
                        rows: 8,
                        maxLength: 1000,
                        size: '12|12|12'
                    })
            });
            break;
    }

    return gf;
}
