import { Observable } from 'rxjs';

export interface PermissionType {
    read?: boolean;
    apply?: (
        el: HTMLElement | any,
        params: any
    ) => void | boolean | Observable<boolean>;
}
