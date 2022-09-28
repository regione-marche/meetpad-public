import { Subscription, Observable } from 'rxjs';

import { ApplicationRole } from '../enums/application-role.enum';
import { ConferenceRole } from '../enums/conference-role.enum';
import { AppSections } from '../enums/app-sections.enum';
import { PermissionType } from '../interfaces/permission-type.interface';
import { UserService } from '../services/user/user.service';

export declare type AppRolePerm = Map<ApplicationRole, PermissionType>;
export declare type ConfRolePerm = Map<ConferenceRole, PermissionType>;

export abstract class BasePermission {
    protected _subscription: Subscription;
    protected abstract _params: any;
    protected abstract _sectionName: AppSections;

    constructor(protected userService: UserService) {}

    protected _authAppRoleInit(roleSectionMapper: Map<any, any>): void {
        this._subscription = this.userService
            .getRole()
            .subscribe((userRole: ApplicationRole) => {
                this._checkSectionPermission(roleSectionMapper, userRole);
            });
    }

    protected _getSection(
        roleSectionMapper: Map<any, any>
    ): ConfRolePerm | AppRolePerm {
        return roleSectionMapper.get(this._sectionName);
    }

    protected abstract get _hostComponent(): any;

    protected abstract _noPermission(): void;

    protected _checkSectionPermission(
        roleSectionMapper: Map<any, any>,
        role: ConferenceRole | ApplicationRole
    ): void {
        const section = this._getSection(roleSectionMapper);

        if (section) {
            const permission = this._getPermission(section, role);

            if (!permission) {
                return this._noPermission();
            }

            // se non ha il permesso di lettura
            // l'elemento html viene rimosso
            if (permission.read !== undefined && permission.read === false) {
                return this._noPermission();
            }

            // altrimenti si delega la gestione del permesso
            // alla lista di mappings di tutti i permessi

            // si controlla che la calback sia stata definita
            if (!permission.apply) {
                return console.error(
                    'appAuth directive: apply callback not defined'
                );
            }

            this._handleApply(permission);
        } else {
            // se non è dichiarato alcun permesso per l'elemento html
            // default: nessun permesso per tutti i ruoli
            return this._noPermission();
        }
    }

    protected _getPermission(
        section: Map<any, any>,
        role: ConferenceRole | ApplicationRole
    ): PermissionType {
        // tslint:disable-next-line: curly
        if (!section) return undefined;
        return section.get(role);
    }

    protected _handleApply(permission: PermissionType): void {
        // richiama la callback con gli eventuali parametri
        const applyReturn = permission.apply(this._hostComponent, this._params);

        // controlla se la callback prevede un ritorno
        if (applyReturn !== undefined) {
            // controlla se la callback prevede un ritorno di tipo Observable
            if (applyReturn instanceof Observable) {
                applyReturn.subscribe((res: boolean) => {
                    if (res === false) {
                        this._noPermission();
                    }
                });
            } // controlla se la callback prevede un ritorno di tibo boolean
            else if (
                typeof applyReturn === 'boolean' &&
                applyReturn === false
            ) {
                this._noPermission();
            }
        }
    }
}
