import { Injectable } from '@angular/core';
import { HttpParams } from '@angular/common/http';

import { Observable, Subject, of } from 'rxjs';
import { map } from 'rxjs/operators';

import { ApiService } from '@eng-ds/ng-core';

import { BaseService, ComboBox, WrapperGetData, FormField } from '@common';
import { environment } from '@env/environment';

import { EventType, ConferenceType } from '@app/core/enums';

import {
    Participant,
    ConferenceTemplate
} from '@features/private/conference/core';

@Injectable()
export class UtilityService extends BaseService {
    constructor(protected api: ApiService) {
        super(api);
    }

    getAreasAutocomplete(
        field: FormField
    ): { options$: Observable<ComboBox[]>; typeahead$: Subject<string> } {
        const _apiRequest = (search: string) => {
            let params = new HttpParams();
            params = params.set('search', search);
            return this.comboBoxRequest('getAreas', params);
        };

        return this.getAutocomplete(field, _apiRequest);
    }

    getAreas(search: string = ''): Observable<ComboBox[]> {
        let params = new HttpParams();
        params = params.set('search', search);
        return this.get<ComboBox[]>(
            this.comboBoxRequest('getAreas', params),
            'areas'
        );
    }

    /**
     *
     * @param areaId
     * @param search
     * @deprecated
     */
    getProvincies(
        areaId?: string,
        search: string = ''
    ): Observable<ComboBox[]> {
        let cacheKey = `provincies`;
        let params = new HttpParams();

        if (areaId) {
            params = params.set('key', areaId);
            cacheKey = `${areaId}.provincies`;
        }

        params = params.set('search', search);

        return this.comboBoxRequest('getProvincies', params);
    }

    getProvinciesAutocomplete(
        areaId: string,
        field: FormField
    ): { options$: Observable<ComboBox[]>; typeahead$: Subject<string> } {
        const _apiRequest = (search: string) => {
            let params = new HttpParams();
            if (areaId) {
                params = params.set('key', areaId);
            }
            params = params.set('search', search);
            return this.comboBoxRequest('getProvincies', params);
        };

        return this.getAutocomplete(field, _apiRequest);
    }

    getCitiesAutocomplete(
        provinceId: string,
        field: FormField
    ): { options$: Observable<ComboBox[]>; typeahead$: Subject<string> } {
        const _apiRequest = (search: string) => {
            let params = new HttpParams();
            params = params.set('key', provinceId);
            params = params.set('search', search);
            return this.comboBoxRequest('getCities', params);
        };

        return this.getAutocomplete(field, _apiRequest);
    }

    getCities(provinceId: string, search: string = ''): Observable<ComboBox[]> {
        let params = new HttpParams();
        params = params.set('key', provinceId);
        params = params.set('search', search);
        return this.get<ComboBox[]>(
            this.comboBoxRequest('getCities', params),
            `${provinceId}.cities`
        );
    }

    getEventTypes(): Observable<ComboBox[]> {
        return this.get<ComboBox[]>(
            this.comboBoxRequest('getEventTypes'),
            'eventTypes'
        );
    }

    getDateTypes(): Observable<ComboBox[]> {
        return this.get<ComboBox[]>(
            this.comboBoxRequest('getDateTypes'),
            'dateTypes'
        );
    }

    getEventTypesForConference(idConference: string): Observable<ComboBox[]> {
        return this.comboBoxRequest('getEventTypesForConference', null, {
            idConference
        });
    }

    getConferencesResults(): Observable<ComboBox[]> {
        return this.get<ComboBox[]>(
            this.comboBoxRequest('getConferencesResults'),
            'getConferencesResults'
        );
    }

    getDocumentModels(conferenceType: string): Observable<ComboBox[]> {
        let params = new HttpParams();
        params = params.set('conferenceType', conferenceType);
        // return this.get<ComboBox[]>(
        //     this.comboBoxRequest('getDocModelsForConferenceType', params),
        //     `${conferenceType}.models`
        // );
        return this.api
            .request<WrapperGetData<ComboBox[]>>(
                'getDocModelsForConferenceType',
                null,
                params
            )
            .pipe(
                map((result: WrapperGetData<ComboBox[]>) => {
                    const arr = [];
                    arr.push({ key: '-1', value: 'Scegli modello' });
                    if (result && result.list) {
                        for (const k in result.list) {
                            if (result.list[k]) {
                                arr.push(result.list[k]);
                            }
                        }
                    }
                    return arr;
                })
            );
    }

    getConferenceTypes(): Observable<ComboBox[]> {
        return this.get<ComboBox[]>(
            this.comboBoxRequest('conferenceTypes'),
            'conferenceTypes'
        );
    }

    getAddressTypes(): Observable<ComboBox[]> {
        return this.get<ComboBox[]>(
            this.comboBoxRequest('addressTypes'),
            'addressTypes'
        );
    }

    getApplicantTypes(): Observable<ComboBox[]> {
        return this.get<ComboBox[]>(
            this.comboBoxRequest('getApplicantTypes'),
            'types'
        );
    }

    getApplicantActivities(applicantTypeId: string): Observable<ComboBox[]> {
        let params = new HttpParams();
        params = params.set('key', applicantTypeId);
        return this.get<ComboBox[]>(
            this.comboBoxRequest('getConferenceActivities', params),
            `${applicantTypeId}.activities`
        );
    }

    getApplicantActions(applicantActivityId: string): Observable<ComboBox[]> {
        let params = new HttpParams();
        params = params.set('key', applicantActivityId);
        return this.get<ComboBox[]>(
            this.comboBoxRequest('getConferenceActions', params),
            `${applicantActivityId}.actions`
        );
    }

    getLegalForms(): Observable<ComboBox[]> {
        return this.get<ComboBox[]>(
            this.comboBoxRequest('getConferenceLegalForms'),
            'legalForms'
        );
    }

    getStates(): Observable<ComboBox[]> {
        return this.get<ComboBox[]>(
            this.comboBoxRequest('getStates'),
            'states'
        );
    }

    getCategoriesFileForDocumentType(
        conferenceType: string = null,
        documentType: string = null
    ): Observable<ComboBox[]> {
        let params = new HttpParams();
        if (conferenceType) {
            params = params.set('conferenceType', conferenceType);
        }
        if (documentType) {
            params = params.set('documentType', documentType);
        }
        return this.get<ComboBox[]>(
            this.comboBoxRequest(
                'getConferenceDocumentationCategoryForDocumentType',
                params
            ),
            `${conferenceType ? conferenceType + '.' : ''}${
                documentType ? documentType + '.' : ''
            }categoriesFile`
        );
    }

    getCategoriesFile(
        conferenceType: string = null,
        eventType: string = null
    ): Observable<ComboBox[]> {
        let params = new HttpParams();
        if (conferenceType) {
            params = params.set('conferenceType', conferenceType);
        }
        if (eventType) {
            params = params.set('eventType', eventType);
        }
        return this.get<ComboBox[]>(
            this.comboBoxRequest('getConferenceDocumentationCategory', params),
            `${conferenceType ? conferenceType + '.' : ''}${
                eventType ? eventType + '.' : ''
            }categoriesFile`
        );
    }

    getCategoryFileByKey(key: string): Observable<ComboBox> {
        return this.getCategoriesFile().pipe(
            map((val: ComboBox[]) =>
                val.find((item: ComboBox) => item.key === key)
            )
        );
    }

    getApplicantList(search: string): Observable<ComboBox[]> {
        let params = new HttpParams();
        params = params.set('search', search);

        return this.api
            .request<WrapperGetData<ComboBox[]>>(
                'getApplicantList',
                null,
                params
            )
            .pipe(
                map((result: WrapperGetData<ComboBox[]>) => {
                    const arr = [];
                    if (result && result.list) {
                        for (const k in result.list) {
                            if (result.list[k]) {
                                arr.push(result.list[k]);
                            }
                        }
                    }
                    return arr;
                })
            );
    }

    getConferenceTemplate(
        type: ConferenceType
    ): Observable<ConferenceTemplate> {
        let params = new HttpParams();
        params = params.set('type', type);

        return this.get<ConferenceTemplate>(
            this.api.request<WrapperGetData<ConferenceTemplate>>(
                'getConferenceTemplate',
                null,
                params
            ),
            `${type}.conferenceTemplate`
        );
    }

    getConferenceAdministrations(): Observable<ComboBox[] & any> {
        return this.get<ComboBox[]>(
            this.comboBoxRequest('getAuthorities'),
            'authorities'
        );
    }

    getConferenceManagers(): Observable<ComboBox[] & any> {
        return this.get<ComboBox[]>(
            this.comboBoxRequest('getConferenceManagers'),
            'conferenceManagers'
        );
    }

    getConferenceManagersDomus(): Observable<ComboBox[] & any> {
        return this.get<ComboBox[]>(
            this.comboBoxRequest('getConferenceManagersDomus'),
            'conferenceManagersDomus'
        );
    }

    /*  getConferenceTypes(): Observable<ComboBox[]> {
        return this.get<ComboBox[]>(
            this.comboBoxRequest('conferenceTypes'),
            'conferenceTypes'
        );
    }*/

    getConferenceManagersWithAdmin(idEnte: string): Observable<ComboBox[]> {
        let params = new HttpParams();
        params = params.set('key', idEnte);
        return this.get<ComboBox[]>(
            this.comboBoxRequest('getConferenceManagers', params),
            `${idEnte}.conferenceManagers`
        );
    }

    getConferenceManagersWithAdminByKey(
        idEnte: string,
        key: string
    ): Observable<ComboBox> {
        return this.getConferenceManagersWithAdmin(idEnte).pipe(
            map((val: ComboBox[]) =>
                val.find((item: ComboBox) => item.key === key)
            )
        );
    }

    getConferenceManagerByKey(key: string): Observable<ComboBox> {
        return this.getConferenceManagers().pipe(
            map((val: ComboBox[]) =>
                val.find((item: ComboBox) => item.key === key)
            )
        );
    }

    getCompanyAutocomplete(
        field: FormField
    ): { options$: Observable<ComboBox[]>; typeahead$: Subject<string> } {
        const _apiRequest = (search: string) => {
            let params = new HttpParams();

            params = params.set('search', search);
            return this.api
                .request<WrapperGetData<ComboBox[]>>('getCompany', null, params)
                .pipe(
                    map((result: WrapperGetData<ComboBox[]>) => {
                        const arr = [];
                        if (result && result.list) {
                            for (const k in result.list) {
                                if (result.list[k]) {
                                    arr.push(result.list[k]);
                                }
                            }
                        }
                        return arr;
                    })
                );
        };

        return this.getAutocomplete(field, _apiRequest);
    }

    getCompanyById(id: string): Observable<any> {
        return this.get<ComboBox[]>(
            this.api.request('getCompanyById', null, null, { id }),
            `company.${id}`
        );
    }

    getAllParticipantRoles(): Observable<ComboBox[]> {
        return this.get<ComboBox[]>(
            this.comboBoxRequest('participantRoles'),
            'participantRoles'
        );
    }

    /**
     * Questo metodo ritorna i ruoli del partecipante
     * se actualRole non corrisponde al ruolo richiedente,
     * allora il ruolo richiedente viene eliminato dalla lista
     * Questo perchè il ruolo richiedente non può essere inserito
     * manualmente dall'utente creatore
     * Stesso discorso per il ruolo Amministrazione procedente
     * @param actualRole
     */
    getParticipantRoles(actualRole: ComboBox): Observable<ComboBox[]> {
        return this.get<ComboBox[]>(
            this.comboBoxRequest('participantRoles'),
            'participantRoles'
        ).pipe(
            // TODO: questi controlli andrebbero centralizzati sul server
            // espandendo il modello ComboBox così: {key, value, hidden, default}
            map((combo: ComboBox[]) => {
                const comboRet = Object.assign([], combo);
                const keyApplicantRole =
                    environment.defaultComboBox.conference.defaultParticipant
                        .applicant.participantRole.key;
                const keyPCmpRole =
                    environment.defaultComboBox.conference.defaultParticipant
                        .administrationProceding.participantRole.key;

                if (actualRole.key !== keyApplicantRole) {
                    comboRet.splice(
                        comboRet.findIndex(
                            (c: ComboBox) => c.key === keyApplicantRole
                        ),
                        1
                    );
                }

                if (actualRole.key !== keyPCmpRole) {
                    comboRet.splice(
                        comboRet.findIndex(
                            (c: ComboBox) => c.key === keyPCmpRole
                        ),
                        1
                    );
                }

                return comboRet;
            })
        );
    }

    // getParticipantRoleByKey(key: string): Observable<any> {
    //     return this.getParticipantRoles().pipe(
    //         map((val: ComboBox[]) =>
    //             val.find((item: ComboBox) => item.key === key)
    //         )
    //     );
    // }

    getActorCompetence(): Observable<ComboBox[]> {
        return this.get<ComboBox[]>(
            this.comboBoxRequest('getActorCompetence'),
            'actorCompetence'
        );
    }

    getPersonRoles(participantRoleId: string): Observable<ComboBox[]> {
        let params = new HttpParams();
        params = params.set('key', participantRoleId);
        return this.get<ComboBox[]>(
            this.comboBoxRequest('getPersonRoles', params),
            `${participantRoleId}.personRoles`
        );
    }

    getAuthority(idAuthority?: string): Observable<Partial<Participant>> {
        let cacheKey = `authority`;
        let params = {};

        if (idAuthority) {
            params = {'idAuthority': idAuthority};
            cacheKey = `${idAuthority}.authority`;
        }

        return this.get<Partial<Participant>>(
            this.api.request<WrapperGetData<Partial<Participant>>>(
                'getAuthority',
                null,
                params
            ),
            cacheKey
        );
    }

    getAccreditationRoles(): Observable<ComboBox[]> {
        return this.get<ComboBox[]>(
            this.comboBoxRequest('getAccreditationRoles'),
            'accreditationRoles'
        );
    }

    getSubjectsEvent(eventYpe: EventType): Observable<ComboBox> {
        let params = new HttpParams();
        params = params.set('key', eventYpe);
        return this.get<ComboBox>(
            // this.comboBoxRequest('getSubjectsEvent', params),
            this.api.request<ComboBox>('getSubjectsEvent', null, params),
            `${eventYpe}.subjectsEvent`
        );
    }

    getPecStatus(): Observable<ComboBox[]> {
        return this.get<ComboBox[]>(
            this.comboBoxRequest('getPecStatus'),
            'pecStatus'
        );
    }

    getDeterminationType(): Observable<ComboBox[]> {
        return this.get<ComboBox[]>(
            this.comboBoxRequest('determinationTypes'),
            'determinationTypes'
        );
    }

    getConferenceStatus(): Observable<ComboBox[]> {
        return this.get<ComboBox[]>(
            this.comboBoxRequest('getConferenceStatus'),
            'conferenceStatus'
        );
    }
}
