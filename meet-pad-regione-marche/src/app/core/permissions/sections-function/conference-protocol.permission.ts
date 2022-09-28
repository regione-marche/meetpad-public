import { ProtocolTableComponent } from '@app/features/private/conference/features/protocol/components/protocolling-table/protocolling-table.component';
import { ActionForm } from '../../enums/action-form.enum';

export function conferenceProtocolCommon(cmp: ProtocolTableComponent): void {
    cmp.action = ActionForm.READONLY;
}

export function conferenceProtocolCreatorAndManager(cmp: ProtocolTableComponent): void {
    cmp.action = ActionForm.EDIT;
}

