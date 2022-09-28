import { FormControl, Validators } from '@angular/forms';
import { of } from 'rxjs';
import { ErrorLabelConstants } from '@eng-ds/ng-toolkit';

import { ActionForm, EventType, ConferenceType } from '@app/core';

import { FormFieldGroup, FormField, BaseFile } from '@common';
import { IntegrationRegistered } from '@features/private/conference/core';
import { EventGroupFieldsService } from '../event-group-fields.service';

import { EventAttach } from '../../../../../core';

export function integrationRegistered(
    this: EventGroupFieldsService,
    mode: ActionForm,
    model: IntegrationRegistered = new IntegrationRegistered()
): Map<string, FormFieldGroup> {
    const gf: Map<string, FormFieldGroup> = new Map();
    this.formGroupsFields.set(EventType.INTEGRATION_REGISTERED, gf);

    if (!(model instanceof IntegrationRegistered))
        model = new IntegrationRegistered(model);

    this._currentEvent = model;

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
                    .set('recipients', {
                        type: 'select-two',
                        label:
                            'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.RECIPIENTS',
                        control: new FormControl(
                            {
                                value: model.recipients,
                                disabled: false
                            },
                            [Validators.required]
                        ),
                        placeholder: 'COMMON.NOBODY',
                        size: '12|12|12',
                        options: of(this.participants)
                    })
                    .set('body', {
                        type: 'text-area',
                        label: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.BODY',
                        control: new FormControl({
                            value: model.body,
                            disabled: false
                        }),
                        rows: 8,
                        maxLength: 1000,
                        size: '12|12|12'
                    })
            });

            this.createPanelDocument(gf, model, EventType.INTEGRATION_REGISTERED);
            
            this.asyncPopulateDefaultIntegration(
                gf,
                EventType.INTEGRATION_REGISTERED
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
                        size: '12|6|6'
                    })
                    .set('subject', {
                        type: 'text',
                        label:
                            'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.SUBJECT_GENERIC_COMMUNICATION',
                        control: new FormControl({
                            value: model.subject.value,
                            disabled: true
                        }),
                        size: '12|6|6'
                    })
                    .set('recipients', {
                        type: 'select-two',
                        label:
                            'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.RECIPIENTS',
                        control: new FormControl({
                            value: model.recipients,
                            disabled: true
                        }),
                        placeholder: 'COMMON.NOBODY',
                        size: '12|12|12'
                    })
                    .set('body', {
                        type: 'text-area',
                        label: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.BODY',
                        control: new FormControl({
                            value: model.body,
                            disabled: true
                        }),
                        rows: 8,
                        maxLength: 1000,
                        size: '12|12|12'
                    })
            }).set('document', {
                panel: true,
                panelHead: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.ATTACHED_FILE',
                fields: new Map<string, FormField>()
                    .set('protocolNumber', {
                        type: 'text',
                        label:
                            'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.PROTOCOL_NUMBER',
                        control: new FormControl({
                            value: model.document.protocolNumber,
                            disabled: true
                        }),
                        size: '12|6|6'
                    })
                    .set('protocolDate', {
                        type: 'date',
                        label:
                            'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.PROTOCOL_DATE',
                        control: new FormControl({
                            value: model.document.protocolDate,
                            disabled: true
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
