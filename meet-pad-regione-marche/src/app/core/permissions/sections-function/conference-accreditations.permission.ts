import { AccreditationTabComponent } from '@app/features/private/conference/features';
import { ActionForm } from '../../enums/action-form.enum';

export function conferenceAccreditationCommon(
    cmp: AccreditationTabComponent
): void {
    cmp.action = ActionForm.READONLY;
}
