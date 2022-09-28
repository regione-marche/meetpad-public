import { Injectable } from '@angular/core';

import { Observable, forkJoin, of } from 'rxjs';
import { map, mergeMap, catchError } from 'rxjs/operators';

import { ApiService } from '@eng-ds/ng-core';

import {
    BaseService,
    WrapperGetData,
    WrapperPostPutData,
    WrapperDeleteData,
    ComboBox
} from '@common';

import { Participant, User } from '@features/private/conference/core';
import {AccreditationPartecipants} from "@features/private/conference/core/models/accreditation/accreditations-participants.model";

@Injectable()
export class ParticipantsService extends BaseService {
    constructor(protected api: ApiService) {
        super(api);
    }

    getUsers(
        conferenceId: string,
        participantId: string,
        refresh: boolean = false
    ): Observable<User[]> {
        return this.get(
            this.api
                .request<WrapperGetData<User>>(
                    'getUsersParticipant',
                    null,
                    null,
                    {
                        conferenceId,
                        participantId
                    }
                )
                .pipe(map(response => response.list)),
            `conference.${conferenceId}.participant.${participantId}.users`,
            refresh
        );
    }



    createUser(
        conferenceId: string,
        participantId: string,
        user: User
    ): Observable<WrapperPostPutData> {
        return this.api.request('createUserParticipant', user, null, {
            conferenceId,
            participantId
        });
    }

    editUser(user: User): Observable<WrapperPostPutData> {
        return this.api.request('editUserParticipant', user, null, {
            userId: user.id
        });
    }

    deleteUser(userId: string): Observable<WrapperDeleteData> {
        return this.api.request('deleteUserParticipant', null, null, {
            userId
        });
    }

    getAccreditedPerPartecipants(conferenceId: string): Observable<AccreditationPartecipants[]> {

        return this.api
            .request<WrapperGetData<AccreditationPartecipants>>(
                'getAccreditedPerPartecipants',
                null,
                null,
                {
                    conferenceId
                }
            )
            .pipe(map(response => response.list));
    }

    getAccreditedPerPartecipantsComboBox(conferenceId: string): ComboBox<string>[] {
        const comboBox: ComboBox<string>[] = [];

        this.getAccreditedPerPartecipants(conferenceId).subscribe(participant => {
            participant.forEach(p =>
            {
                if(p.accreditations.length > 0)
                {
                    p.accreditations.forEach(d => {
                        comboBox.push({
                            key: d.key,
                            value: p.participant.value +" - "+ d.value
                        })
                    })
                }
            }
            );
        });

        return comboBox;
    }

    getParticipants(conferenceId: string): Observable<Participant[]> {
        return this.api
            .request<WrapperGetData<Participant>>(
                'getParticipants',
                null,
                null,
                {
                    conferenceId
                }
            )
            .pipe(map(response => response.list));
    }

    getParticipantComboBox(conferenceId: string): Observable<ComboBox[]> {
        const comboBox: ComboBox<string>[] = [];
        this.getParticipants(conferenceId).subscribe(participant => {
            participant.forEach(p =>
                comboBox.push({
                    key: p.authority.key,
                    value: p.authority.value
                })
            );
        });
        return of(comboBox);
    }

    /**
     * Utilizzata per creare un partecipante e tutti i sui utenti
     * @param conferenceId
     * @param participant
     */
    createParticipant(
        conferenceId: string,
        participant: Participant
    ): Observable<WrapperPostPutData> {
        const users = participant.users;
        delete participant.users;

        return this.api
            .request<WrapperPostPutData>(
                'createParticipant',
                participant,
                null,
                {
                    conferenceId
                }
            )
            .pipe(
                mergeMap((res: WrapperPostPutData) => {
                    participant.id = res.id;

                    if (users.length) {
                        const reqs = [];
                        // tslint:disable-next-line:forin
                        for (const k in users) {
                            reqs.push(
                                this.createUser(
                                    conferenceId,
                                    res.id,
                                    users[k]
                                ).pipe(
                                    catchError(() => {
                                        // TODO: gestire specifico errore
                                        // ovvero caso in qui questo utente non venga inserito
                                        return of();
                                    })
                                )
                            );
                        }

                        return forkJoin(reqs).pipe(
                            map((_res: WrapperPostPutData[]) => {
                                // mappa gli id degli utenti creati
                                // tslint:disable-next-line:forin
                                for (let i = 0; i < users.length; i++) {
                                    if (_res[i].id) {
                                        users[i].id = _res[i].id;
                                    }
                                }
                                // riassegna l'oggetto user al partecipante
                                participant.users = users;
                                // l'id del partecipante creato
                                return res;
                            })
                        );
                    } else {
                        participant.users = [];
                        return of(res);
                    }
                })
            );
    }

    /**
     *
     * @param conferenceId
     * @param participant
     */
    // createParticipant(conferenceId: string, participant: Participant): Observable<WrapperPostPutData> {
    //     return this.api.request<WrapperPostPutData>('createParticipant', participant, null, {
    //         conferenceId
    //     });
    // }

    editParticipant(
        participant: Participant,
        saveUser = true
    ): Observable<WrapperPostPutData> {
        return this.api.request<WrapperPostPutData>(
            'editParticipant',
            participant,
            null,
            {
                participantId: participant.id
            }
        );
    }

    deleteParticipant(participantId: string): Observable<any> {
        return this.api.request('deleteParticipant', null, null, {
            participantId
        });
    }

    deletePartecipantList(
        idParticipantsList : Number[]
    ): Observable<Participant> {
        return this.api.request<Participant>('deleteParticipantList', { "idParticipantList": idParticipantsList }, null, null);
    }
}
