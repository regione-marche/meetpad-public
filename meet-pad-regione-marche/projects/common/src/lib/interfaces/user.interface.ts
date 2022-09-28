import { ApplicationRole } from '../enums/application-role.enum';

declare type ConferenceRole = any;

export interface User {
    name: string;
    lastname: string;
    fiscalCode: string;
    profile: { key: ApplicationRole | ConferenceRole; value: string };
    creationOtherAuthorities: boolean;
    flagSignatory: boolean;
    idUser: any;
}
