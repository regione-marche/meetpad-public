import { FilePanelComponent, FilePanelSignComponent } from '@app/features/private/conference/features';
import {
    ApplicationRole
} from '@common';

export function conferenceSignCommon(cmp: FilePanelSignComponent, plusSignIsVisbleForRole: boolean = false, isAdmin: boolean = false): void {
    cmp.plusSignIsVisbleForRole = plusSignIsVisbleForRole;
    cmp.isAdmin = isAdmin;
}
