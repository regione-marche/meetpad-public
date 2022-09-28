import { Validators, FormControl } from '@angular/forms';

import { ActionForm, EventType, ConferenceType } from '@app/core';

import { FormFieldGroup, FormField, BaseFile } from '@common';

import { of } from 'rxjs';
import { EventGroupFieldsService } from '../event-group-fields.service';
import { EditDate } from '@app/features/private/conference/core/models/events/edit-date.model';
import { ComboBox } from 'projects/common/src/public_api';
import { takeUntil, map } from 'rxjs/operators';

import { environment } from '@env/environment';
import { DateType } from '@app/core/enums/date-type.enum';
import { DateModel } from '@eng-ds/ng-toolkit';

import { EventAttach, EventDocument } from '../../../../../core';

export function editDate(
    this: EventGroupFieldsService,
    mode: ActionForm,
    model: EditDate = new EditDate()
): Map<string, FormFieldGroup> {
    const gf: Map<string, FormFieldGroup> = new Map();
    this.formGroupsFields.set(EventType.EDIT_DATE, gf);

    if (!(model instanceof EditDate))
        model = new EditDate(model);

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
                        label: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.SUBJECT_GENERIC_COMMUNICATION',
                        control: new FormControl({
                            value: model.subject.value,
                            disabled: true
                        }),
                        disabled: true,
                        size: '12|12|12'
                    })
                    /*
                    .set('dateType', {
                        type: 'select',
                        label:
                            'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.DATE_TYPE',
                        control: new FormControl(
                            {
                                value: model.editDate.type,
                                disabled: false
                            },
                            [Validators.required]
                        ),
                        placeholder: 'COMMON.NOBODY',
                        size: '12|12|12',
                        options: this.getDateTypes,
                        valueChange: evt => {
                            
                            let result = this.getDateValueByType(evt.key)
                            gf.get('head')
                                .fields.get('oldDate')
                                .control.setValue(result);
                        }
                    })
                    .set('oldDate', {
                        type: 'date',
                        label: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.OLD_DATE',
                        control: new FormControl(
                            {
                                value: this.getDateValueByType( DateType.DATA_TERMINE ),
                                disabled: true
                            }
                        ),
                        placeholder: 'COMMON.NOBODY',
                        size: '12|12|12',
                    })
                    .set('delayDate', {
                        type: 'text',
                        label: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.DELAY_DATE',
                        control: new FormControl(
                            {
                                value: 0,
                                disabled: false
                            }
                        ),
                        size: '12|12|12',
                        valueChange: delay => {
                            
                            let oldDate = new DateModel(gf.get('head')
                                .fields.get('oldDate')
                                .control.value.date.toJSON());

                            oldDate.date.add('days', delay)
                            
                            gf.get('head')
                                .fields.get('newDate')
                                .control.setValue(oldDate); 
                        }
                    })
                    .set('newDate', {
                        type: 'date',
                        label: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.NEW_DATE',
                        control: new FormControl(
                            {
                                value: model.newDate,
                                disabled: false
                            },
                            [Validators.required]
                        ),
                        placeholder: 'COMMON.NOBODY',
                        size: '12|12|12'
                    })
                  
                    .set('dataTermine', {
                        type: 'text',
                        label: 'Tipo Data',
                        control: new FormControl(
                            {
                                value: 'Termine',
                                disabled: true
                            },
                            []
                        ),
                        size: '12|3|3'
                    })
                    .set('oldDatedataTermine', {
                        type: 'date',
                        label: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.OLD_DATE',
                        control: new FormControl(
                            {
                                value: this.getDateValueByType( DateType.DATA_TERMINE ),
                                disabled: true
                            }
                        ),
                        placeholder: 'COMMON.NOBODY',
                        size: '12|3|3',
                    })
                    .set('delayDatedataTermine', {
                        type: 'text',
                        label: 'Aggiungi giorni alla scadenza',
                        control: new FormControl(
                            {
                                value: 0,
                                disabled: false
                            }
                        ),
                        size: '12|3|3',
                        valueChange: delay => {
                            
                            let oldDate = new DateModel(gf.get('head')
                                .fields.get('oldDatedataTermine')
                                .control.value.date.toJSON());
                            oldDate.date.add('days', delay)
                            
                            gf.get('head')
                                .fields.get('newDateTermine')
                                .control.setValue(oldDate); 
                        }
                    })
                    .set('newDateTermine', {
                        type: 'date',
                        label: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.NEW_DATE',
                        control: new FormControl(
                            {
                                value: model.newDateTermine == null || model.newDateTermine  ? this.getDateValueByType( DateType.DATA_TERMINE) : model.newDateTermine,
                                disabled: false
                            },
                            //[Validators.required]
                        ),
                        placeholder: 'COMMON.NOBODY',
                        size: '12|3|3'
                    })
                    */
                    .set('body', {
                        type: 'text-area',
                        label: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.EDIT_REASON',
                        control: new FormControl(
                            {
                                value: model.body,
                                disabled: false
                            },
                            [Validators.required]
                        ),
                        rows: 8,
                        maxLength: 1000,
                        size: '12|12|12'
                    })
            });
          
            gf.set('dates', {
                    panel: true,
                    panelHead: 'Lista date modifcabili',
                    fields: new Map<string, FormField>()
                        .set('dateTermine', {
                            type: 'text',
                            label: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.DATE_TYPE',
                            control: new FormControl(
                                {
                                    value: 'Termine',
                                    disabled: true
                                },
                                []
                            ),
                            size: '12|3|3'
                        })
                        .set('oldDateTermine', {
                            type: 'date',
                            label: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.OLD_DATE',
                            control: new FormControl(
                                {
                                    value: this.getDateValueByType(DateType.DATA_TERMINE),
                                    disabled: true
                                }
                            ),
                            placeholder: 'COMMON.NOBODY',
                            size: '12|3|3',
                        })
                        .set('delayDateTermine', {
                            type: 'text',
                            label: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.DELAY_DATE_TERMINE',
                            control: new FormControl(
                                {
                                    value: 0,
                                    disabled: false
                                }
                            ),
                            size: '12|3|3',
                            valueChange: delay => {

                                let oldDate = new DateModel(gf.get('dates')
                                    .fields.get('oldDateTermine')
                                    .control.value.date.toJSON());
                                oldDate.date.add('days', delay)

                                gf.get('dates')
                                    .fields.get('newDateTermine')
                                    .control.setValue(oldDate);
                            }
                        })
                        .set('newDateTermine', {
                            type: 'date',
                            label: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.NEW_DATE',
                            control: new FormControl(
                                {
                                    value: model.newDateTermine == null || model.newDateTermine ? this.getDateValueByType(DateType.DATA_TERMINE) : model.newDateTermine,
                                    disabled: false
                                },
                                //[Validators.required]
                            ),
                            placeholder: 'COMMON.NOBODY',
                            size: '12|3|3'
                        })
                        .set('dateTermineEsprPareri', {
                            type: 'text',
                            label: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.DATE_TYPE',
                            control: new FormControl(
                                {
                                    value: 'Termine Espressione pareri',
                                    disabled: true
                                },
                                []
                            ),
                            size: '12|3|3'
                        })
                        .set('oldDateTermineEsprPareri', {
                            type: 'date',
                            label: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.OLD_DATE',
                            control: new FormControl(
                                {
                                    value: this.getDateValueByType(DateType.DATA_TERMINE_ESPRESSIONE_PARERI),
                                    disabled: true
                                }
                            ),
                            placeholder: 'COMMON.NOBODY',
                            size: '12|3|3',
                        })
                        .set('delayDateTermineEsprPareri', {
                            type: 'text',
                            label: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.DELAY_DATE_TERMINE',
                            control: new FormControl(
                                {
                                    value: 0,
                                    disabled: false
                                }
                            ),
                            size: '12|3|3',
                            valueChange: delay => {

                                let oldDate = new DateModel(gf.get('dates')
                                    .fields.get('oldDateTermineEsprPareri')
                                    .control.value.date.toJSON());
                                oldDate.date.add('days', delay)

                                gf.get('dates')
                                    .fields.get('newDateTermineEsprPareri')
                                    .control.setValue(oldDate);
                            }
                        })
                        .set('newDateTermineEsprPareri', {
                            type: 'date',
                            label: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.NEW_DATE',
                            control: new FormControl(
                                {
                                    value: model.newDateTermineEsprPareri == null || model.newDateTermineEsprPareri == '' ? this.getDateValueByType(DateType.DATA_TERMINE_ESPRESSIONE_PARERI) : model.newDateTermineEsprPareri,
                                    disabled: false
                                },
                                //[Validators.required]
                            ),
                            placeholder: 'COMMON.NOBODY',
                            size: '12|3|3'
                        })
                        .set('dateTermineRichInteg', {
                            type: 'text',
                            label: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.DATE_TYPE',
                            control: new FormControl(
                                {
                                    value: 'Termine Richiesta Integrazione',
                                    disabled: true
                                },
                                []
                            ),
                            size: '12|3|3'
                        })
                        .set('oldDateTermineRichInteg', {
                            type: 'date',
                            label: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.OLD_DATE',
                            control: new FormControl(
                                {
                                    value: this.getDateValueByType(DateType.DATA_TERMINE_RICHIESTA_INTEGRAZIONI_CONFERENZA),
                                    disabled: true
                                }
                            ),
                            placeholder: 'COMMON.NOBODY',
                            size: '12|3|3',
                        })
                        .set('delayDateTermineRichInteg', {
                            type: 'text',
                            label: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.DELAY_DATE_TERMINE',
                            control: new FormControl(
                                {
                                    value: 0,
                                    disabled: false
                                }
                            ),
                            size: '12|3|3',
                            valueChange: delay => {

                                let oldDate = new DateModel(gf.get('dates')
                                    .fields.get('oldDateTermineRichInteg')
                                    .control.value.date.toJSON());
                                oldDate.date.add('days', delay)

                                gf.get('dates')
                                    .fields.get('newDateTermineRichInteg')
                                    .control.setValue(oldDate);
                            }
                        })
                        .set('newDateTermineRichInteg', {
                            type: 'date',
                            label: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.NEW_DATE',
                            control: new FormControl(
                                {
                                    value: model.newDateTermineRichInteg == null || model.newDateTermineRichInteg == '' ? this.getDateValueByType(DateType.DATA_TERMINE_RICHIESTA_INTEGRAZIONI_CONFERENZA) : model.newDateTermineRichInteg,
                                    disabled: false
                                },
                                //[Validators.required]
                            ),
                            placeholder: 'COMMON.NOBODY',
                            size: '12|3|3'
                        })
                        .set('datePrimaSessioneSimultanea', {
                            type: 'text',
                            label: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.DATE_TYPE',
                            control: new FormControl(
                                {
                                    value: 'Prima Sessione Simultanea',
                                    disabled: true
                                },
                                []
                            ),
                            size: '12|3|3'
                        })
                        .set('oldDatePrimaSessioneSimultanea', {
                            type: 'date',
                            label: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.OLD_DATE',
                            control: new FormControl(
                                {
                                    value: this.getDateValueByType(DateType.DATA_PRIMA_SESSIONE_SIMULTANEA),
                                    disabled: true
                                }
                            ),
                            placeholder: 'COMMON.NOBODY',
                            size: '12|3|3',
                        })
                        .set('delayDatePrimaSessioneSimultanea', {
                            type: 'text',
                            label: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.DELAY_DATE_TERMINE',
                            control: new FormControl(
                                {
                                    value: 0,
                                    disabled: false
                                }
                            ),
                            size: '12|3|3',
                            valueChange: delay => {

                                let oldDate = new DateModel(gf.get('dates')
                                    .fields.get('oldDatePrimaSessioneSimultanea')
                                    .control.value.date.toJSON());
                                oldDate.date.add('days', delay)

                                gf.get('dates')
                                    .fields.get('newDatePrimaSessioneSimultanea')
                                    .control.setValue(oldDate);
                            }
                        })
                        .set('newDatePrimaSessioneSimultanea', {
                            type: 'date',
                            label: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.NEW_DATE',
                            control: new FormControl(
                                {
                                    value: model.newDatePrimaSessioneSimultanea == null || model.newDatePrimaSessioneSimultanea ? this.getDateValueByType(DateType.DATA_PRIMA_SESSIONE_SIMULTANEA) : model.newDatePrimaSessioneSimultanea,
                                    disabled: false
                                },
                                //[Validators.required]
                            ),
                            placeholder: 'COMMON.NOBODY',
                            size: '12|3|3'
                        })

            });

            this.createPanelDocument(gf,model,EventType.EDIT_DATE);
            /*
            gf.set('docPrinc', {
                    panel: true,
                    panelHead: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.MAIN_DOCUMENT',
                    fields: new Map<string, FormField>()
                        .set('file', {
                            type: 'file',
                            required: false,
                            control: new FormControl(),
                            onSelect: file => {
                                this.loggerService.log('onSelect', file);
                                gf.get('docPrinc')
                                    .fields.get('file')
                                    .control.setValue(file);

                            },
                            disabled: true,
                            hideSubmitBtn: true,
                            size: '12|6|6'
                        })
            });
           
            // xmfAddedAttaches
            gf.set('attach', {
                panel: true,
                panelHead: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.ATTACHMENTS_LIST',
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
                            this._attachmentFlag = true;
                            this._tmpArrayFiles = file;                            
                        },
                        hideSubmitBtn: true,
                        size: '12|6|6'
                    })
            });
            
            gf.set('document', {
                    panel: true,
                    panelHead: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.PROTOCOL_NUMBER_DATE',
                    fields: new Map<string, FormField>()
                        .set('protocolNumber', {
                            type: 'text',
                            label: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.PROTOCOL_NUMBER',
                            control: new FormControl(
                                {
                                    value: model.document.protocolNumber,
                                    disabled: !(this.conferenceType == ConferenceType.BROADBAND || this.conferenceType == ConferenceType.PRELIMINARY)
                                },
                                []
                            ),
                            size: '12|6|6'
                        })
                        .set('protocolDate', {
                            type: 'date',
                            label: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.PROTOCOL_DATE',
                            control: new FormControl(
                                {
                                    value: model.document.protocolDate,
                                    disabled: !(this.conferenceType == ConferenceType.BROADBAND || this.conferenceType == ConferenceType.PRELIMINARY)
                                },
                                []
                            ),
                            valueChange: evt => {
                                gf.get('document')
                                    .fields.get('protocolDate')
                                    .control.setErrors(null);
                            },
                            size: '12|6|6'
                        })
            });
            */
                
            this.asyncPopulateDefaultIntegration(
                gf,
                EventType.EDIT_DATE
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
                    .set('dateType', {
                        type: 'text',
                        label:
                            'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.DATE_TYPE',
                        control: new FormControl(
                            {
                                value: model.editDate.description,
                                disabled: true
                            },
                            [Validators.required]
                        ),
                        //placeholder: 'COMMON.NOBODY',
                        size: '12|12|12'
                    })
                    .set('editDate', {
                        type: 'date',
                        label:
                            'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.EDIT_DATE',
                        control: new FormControl(
                            {
                                value: model.editDate.editDate,
                                disabled: true
                            },
                            [Validators.required]
                        ),
                        placeholder: 'COMMON.NOBODY',
                        size: '12|12|12'
                    })
                    .set('oldDate', {
                        type: 'date',
                        label:
                            'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.LAST_VALUE',
                        control: new FormControl(
                            {
                                value: model.editDate.date,
                                disabled: true
                            },
                            [Validators.required]
                        ),
                        placeholder: 'COMMON.NOBODY',
                        size: '12|12|12'
                    })
                    .set('newDate', {
                        type: 'date',
                        label:
                            'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.NEW_DATE',
                        control: new FormControl(
                            {
                                value: model.editDate.dateNew,
                                disabled: true
                            },
                            [Validators.required]
                        ),
                        placeholder: 'COMMON.NOBODY',
                        size: '12|12|12'
                    })
                    .set('body', {
                        type: 'text-area',
                        label: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.EDIT_REASON',
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
                panelHead: 'CONFERENCE.WIZARD.EVENTS.MODAL.PANEL.MAIN_DOCUMENT',
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
