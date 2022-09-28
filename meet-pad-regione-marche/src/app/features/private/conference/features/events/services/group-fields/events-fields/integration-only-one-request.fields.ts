import { FormControl, Validators } from '@angular/forms';

import { ErrorLabelConstants } from '@eng-ds/ng-toolkit';

import { ActionForm, EventType, ConferenceType } from '@app/core';

import { FormFieldGroup, FormField, BaseFile } from '@common';

import { IntegrationOnlyOneRequest } from '@features/private/conference/core';
import { EventGroupFieldsService } from '../event-group-fields.service';

import { EventAttach } from '../../../../../core';

export function integrationOnlyOneRequest(
    this: EventGroupFieldsService,
    mode: ActionForm,
    model: IntegrationOnlyOneRequest = new IntegrationOnlyOneRequest()
): Map<string, FormFieldGroup> {
    const gf: Map<string, FormFieldGroup> = new Map();
    this.formGroupsFields.set(EventType.INTEGRATION_ONLY_ONE_REQUEST, gf);

    if (!(model instanceof IntegrationOnlyOneRequest))
        model = new IntegrationOnlyOneRequest(model);

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
            })
            
            this.createPanelDocument(gf,model,EventType.INTEGRATION_ONLY_ONE_REQUEST);

            /*
			// xmfAddedAttaches
            .set('attach', {
                panel: true,
                panelHead:'Lista degli allegati (opzionali)',
                oneToMany: true,
                accordion: false,
                model: EventAttach,
                saveFn: this.saveAttachFile,
                listStructure: this.fnStruct(),
                listTitle: 'CONFERENCE.WIZARD.PARTICIPANTS.MODAL.USER.LIST_TITLE',
                emptyTextList: '',
                listMany: model.attaches,
                readonly: false,
                fields: new Map<string, FormField>()
					.set('attachment', {
                        type: 'file',
                        required: true,
                        control: new FormControl(),
                        onSelect: file => { 
                            this._tmpArrayFiles = file; 
                        },
                        hideSubmitBtn: true,
                        size: '12|6|6'
					})
            })

            .set('document', {
                panel: true,
                panelHead: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.ATTACHED_FILE',
                fields: new Map<string, FormField>()
                    .set('category', {
                        type: 'select',
                        label:
                            'CONFERENCE.WIZARD.DOCUMENTATION.ADDITIONAL.FILE.CATEGORY',
                        control: new FormControl({
                            value: model.document.category,
                            disabled: false
                        }),
                        size: '12|6|4',
                        options: this.utilityService.getCategoriesFile(
                            this.conferenceType,
                            EventType.INTEGRATION_ONLY_ONE_REQUEST
                        )
                    })
                    .set('protocolNumber', {
                        type: 'text',
                        label:
                            'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.PROTOCOL_NUMBER',
                        control: new FormControl(
                            {
                                value: model.document.protocolNumber,
                                disabled: !(this.conferenceType == ConferenceType.BROADBAND || this.conferenceType == ConferenceType.PRELIMINARY)
                            },
                            [Validators.required]
                        ),
                        errorLabels: [ErrorLabelConstants.REQUIRED],
                        size: '12|6|4'
                    })
                    .set('protocolDate', {
                        type: 'date',
                        label:
                            'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.PROTOCOL_DATE',
                        control: new FormControl(
                            {
                                value: model.document.protocolDate,
                                disabled: !(this.conferenceType == ConferenceType.BROADBAND || this.conferenceType == ConferenceType.PRELIMINARY)
                            },
                            [Validators.required]
                        ),
                        errorLabels: [ErrorLabelConstants.REQUIRED],
                        size: '12|6|4'
                    })
                    .set('file', {
                        type: 'file',
                        required: true,
                        control: new FormControl(),
                        onSelect: file => {
                            this.loggerService.log('onSelect', file);
                            gf.get('document')
                                .fields.get('file')
                                .control.setValue(file);
                        },
                        disabled: true,
                        hideSubmitBtn: true,
                        size: '12|6|6'
                    })
            });
            */
            this.asyncPopulateDefaultIntegration(
                gf,
                EventType.INTEGRATION_ONLY_ONE_REQUEST
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
                            EventType.INTEGRATION_ONLY_ONE_REQUEST
                        )
                    })
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
