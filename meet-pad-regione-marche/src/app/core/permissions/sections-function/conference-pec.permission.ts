import { PecComponent } from '@app/features/private/conference/features';
import { ActionForm } from '../../enums/action-form.enum';

export function conferencePecCommon(cmp: PecComponent): void {
    cmp.action = ActionForm.READONLY;
}
