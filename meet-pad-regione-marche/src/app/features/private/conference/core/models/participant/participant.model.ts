/* tslint:disable:no-inferrable-types */

import { BaseModel } from '@eng-ds/ng-toolkit';
import { ComboBox } from '@common';
import { ParticipantRole } from '@app/core';

import { environment } from '@env/environment';
import { User } from './user.model';
import {AccreditationPartecipants} from "@features/private/conference/core/models/accreditation/accreditations-participants.model";

export class Participant extends BaseModel {
    id: any;

    authority: ComboBox;
    role: ComboBox = environment.defaultComboBox.conference.participants.role;
    // competence: ComboBox = environment.defaultComboBox.conference.participants.competence;
    competence: string = '';
    competenceAuthorization: ComboBox<string>[] = [];
    description: string = '';
    pec: string = '';
    fiscalCode: string = '';
    determination: boolean = true;
    emails: string[] = [];
    users: User[] = [];
    AccreditatedPersons: AccreditationPartecipants[] = [];

    determinationExpressed: boolean = false;
    integrationRequired?: boolean = false;

    note: string = '';

    readonly: boolean = false;

    selected: boolean = false;

    constructor(participant?: Partial<Participant>) {
        super();
        if (participant) {
            if (participant.users) {
                for (let i = 0; i < participant.users.length; i++) {
                    this.users.push(new User(participant.users[i]));
                }
            }
            this.id = participant.id;
            this.authority = participant.authority;
            this.role = participant.role;
            this.description = participant.description;
            this.pec = participant.pec;
            this.fiscalCode = participant.fiscalCode;
            this.competence = participant.competence;
            this.determination = participant.determination;
            this.competenceAuthorization = participant.competenceAuthorization;
            this.determinationExpressed = participant.determinationExpressed;
            this.integrationRequired = participant.integrationRequired;
            if (participant.emails) {
                for (let i = 0; i < participant.emails.length; i++) {
                    this.emails.push(participant.emails[i]);
                }
            }
            this.note = participant.note;
            this.readonly = participant.readonly;
            this.selected = participant.selected;
        }
    }

    update(participant: Participant): void {
        if (participant) {
            this.authority = participant.authority;
            this.role = participant.role;
            this.description = participant.description;
            this.pec = participant.pec;
            this.fiscalCode = participant.fiscalCode;
            this.competence = participant.competence;
            this.determination = participant.determination;
            this.note = participant.note;
            this.competenceAuthorization = participant.competenceAuthorization;

            if (participant.emails) {
                for (let i = 0; i < participant.emails.length; i++) {
                    // push if not alredy exist
                    this.emails = this.emails.reduce(function(a, b) {
                        if (a.indexOf(b) === -1) {
                            a.push(b);
                        }
                        return a;
                    }, []);
                }
            }

            if (participant.users) {
                for (let i = 0; i < participant.users.length; i++) {
                    this.editUser(participant.users[i]);
                }
            }

            this.note = participant.note;
            this.readonly = participant.readonly;
        }
    }

    setUsers(users: User[]): void {
        const _users = [];
        for (let i = 0; i < users.length; i++) {
            _users.push(new User(users[i]));
        }
        this.users = _users;
    }

    editUser(user: User): void {
        if (!user.id) {
            return;
        }
        const u = this.users.find((_u: User) => _u.id === user.id);

        if (!u) {
            return;
        }

        u.update(user);
    }

    deleteUser(userId: string): void {
        if (!userId) {
            return;
        }
        const iu = this.users.findIndex((_u: User) => _u.id === userId);

        if (iu < 0) {
            return;
        }

        this.users.splice(iu, 1);
    }

    isApplicant(): boolean {
        return this.role.key === ParticipantRole.APPLICANT;
    }

    getPrincipalApplicant(): User {
        return this.users.find((user: User) => user.principalApplicant);
    }

    getApplicant(): User {
        return this.users[0];
    }

    toggleSelection(){
        this.selected = !this.selected
    }

    isSelected(){
        return this.selected
    }
}
