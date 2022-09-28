import { Injectable } from '@angular/core';

import { BaseService, FormField, ComboBox, WrapperGetData } from '@common';

import { ApiService } from '@eng-ds/ng-core';

import { Observable, Subject } from 'rxjs';

import { HttpParams } from '@angular/common/http';

import { map } from 'rxjs/operators';
import { Applicant } from '../../features/preloading/features/applicant/models/applicant.model';
import { SearchApplicant } from '../../features/preloading/features/applicant/models/search-applicant.model';

@Injectable()
export class UtilityService extends BaseService {
    conferenceType: string = '';
    constructor(protected api: ApiService) {
        super(api);
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

    getCompanyCombo() {
        return this.api
            .request<WrapperGetData<ComboBox[]>>('getCompanyAutocomplete', null)
            .pipe(
                map((result: WrapperGetData<ComboBox[]>) => {
                    return result.list;
                })
            );
    }

    getCompanyAutocompleteForApplicant(
        field: FormField
    ): { options$: Observable<ComboBox[]>; typeahead$: Subject<string> } {
        const _apiRequest = (search: string) => {
            let params = new HttpParams();
            if (this.conferenceType !== '') {
                params = params.set('conferenceType', this.conferenceType);
            }
            params = params.set('value', search);
            return this.api
                .request<WrapperGetData<ComboBox[]>>(
                    'getCompanyAutocompleteForApplicant',
                    null,
                    params
                )
                .pipe(
                    map((result: WrapperGetData<ComboBox[]>) => {
                        return result.list;
                    })
                );
        };

        return this.getAutocomplete(field, _apiRequest);
    }
    getNewCompanyAutocomplete(
        field: FormField
    ): { options$: Observable<ComboBox[]>; typeahead$: Subject<string> } {
        const _apiRequest = (search: string) => {
            let params = new HttpParams();
            params = params.set('key', search);
            return this.api
                .request<WrapperGetData<ComboBox[]>>(
                    'getCompanyAutocomplete',
                    null,
                    params
                )
                .pipe(
                    map((result: WrapperGetData<ComboBox[]>) => {
                        return result.list;
                    })
                );
        };

        return this.getAutocomplete(field, _apiRequest);
    }

    getApplicantAutocomplete(
        field: FormField
    ): { options$: Observable<ComboBox[]>; typeahead$: Subject<string> } {
        const _apiRequest = (search: string) => {
            let params = new HttpParams();
            params = params.set('key', search);
            return this.api
                .request<WrapperGetData<ComboBox[]>>(
                    'getApplicantAutocomplete',
                    null,
                    params
                )
                .pipe(
                    map((result: WrapperGetData<ComboBox[]>) => {
                        return result.list;
                    })
                );
        };

        return this.getAutocomplete(field, _apiRequest);
    }

    getCompany(id: string): Observable<ComboBox[]> {
        return this.api
            .request<WrapperGetData<any>>('getCompany', null, {
                id: id
            })
            .pipe(
                map((result: WrapperGetData<any>) => {
                    const arr = [];
                    arr.push({ key: '-1', value: 'Seleziona profilo' });
                    if (result && result.list) {
                        for (const k in result.list) {
                            if (result.list[k]) {
                                arr.push({
                                    key: result.list[k].id.toString(),
                                    value: result.list[k].profileType.value
                                });
                            }
                        }
                    }
                    return arr;
                })
            );
    }

    getProfiles(id: string): Observable<ComboBox[]> {
        return this.api
            .request<WrapperGetData<any>>('getProfiles', null, {
                idAdministrationProceeding: id
            })
            .pipe(
                map((result: WrapperGetData<any>) => {
                    const arr = [];
                    arr.push({ key: '-1', value: 'Seleziona profilo' });
                    if (result && result.list) {
                        for (const k in result.list) {
                            if (result.list[k]) {
                                arr.push({
                                    key: result.list[k].id.toString(),
                                    value: result.list[k].profileType.value
                                });
                            }
                        }
                    }
                    return arr;
                })
            );
    }

    getConferenceManagersWithAdmin(idEnte: string): Observable<ComboBox[]> {
        let params = new HttpParams();
        params = params.set('key', idEnte);
        return this.get<ComboBox[]>(
            this.comboBoxRequest('getConferenceManagers', params),
            `${idEnte}.conferenceManagers`
        );
    }

    getParticipantForConference(idConference: string): Observable<ComboBox[]> {
        return this.api
            .request<WrapperGetData<any>>(
                'getParticipantForConference',
                null,
                null,
                {
                    id: idConference
                }
            )
            .pipe(
                map((result: WrapperGetData<any>) => {
                    return result.list;
                })
            );
    }

//  METODO PER RECUPERARE GLI STATI
    getStateForConference(idTipologia: string): Observable<ComboBox[]> {
        return this.api
            .request<WrapperGetData<any>>(
                'getStateConference',
                null,
                null,
                {
                    idTipologia: idTipologia
                }
            )
            .pipe(
                map((result: WrapperGetData<any>) => {
                    return result.list;
                })
            );
    }

    getConferenceAdministrations(): Observable<ComboBox[] & any> {
        return this.get<ComboBox[]>(
            this.comboBoxRequest('getAuthoritiesCombo'),
            'authorities'
        );
    }

    getAllConferenzaCreatorUsers(): Observable<ComboBox[] & any> {
        return this.get<ComboBox[]>(
            this.comboBoxRequest('getAllConferenzaCreatorUsersCombo'),
            'users'
            
        );
    }
    

    getIstatType(): Observable<ComboBox[] & any> {
        return this.get<ComboBox[]>(
            this.comboBoxRequest('getIstatTypes'),
            'istatType'
        );
    }

    getAdministrativeType(): Observable<ComboBox[] & any> {
        return this.get<ComboBox[]>(
            this.comboBoxRequest('getAdministrativeTypes'),
            'administrativeType'
        );
    }

    getParticipantRoles(): Observable<ComboBox[]> {
        return this.get<ComboBox[]>(
            this.comboBoxRequest('participantRoles'),
            'participantRoles'
        );
    }

    getCompetenceAuthorizationForConferenceTypes(
        type: string
    ): Observable<ComboBox[]> {
        let params = new HttpParams();
        params = params.set('conferenceType', type);

        return this.get<ComboBox[]>(
            this.comboBoxRequest(
                'competenceAuthorizationForConferenceTypes',
                params
            ),
            `${type}.conferenceTemplate`
        );
    }

    getAreasAutocomplete(
        field: FormField
    ): { options$: Observable<ComboBox[]>; typeahead$: Subject<string> } {
        const _apiRequest = (search: string) => {
            let params = new HttpParams();
            params = params.set('search', search);
            return this.api
                .request<WrapperGetData<ComboBox[]>>('getAreas', null, params)
                .pipe(
                    map((result: WrapperGetData<ComboBox[]>) => {
                        return result.list;
                    })
                );
        };

        return this.getAutocomplete(field, _apiRequest);
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
            return this.api
                .request<WrapperGetData<ComboBox[]>>(
                    'getProvincies',
                    null,
                    params
                )
                .pipe(
                    map((result: WrapperGetData<ComboBox[]>) => {
                        return result.list;
                    })
                );
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
            return this.api
                .request<WrapperGetData<ComboBox[]>>('getCities', null, params)
                .pipe(
                    map((result: WrapperGetData<ComboBox[]>) => {
                        return result.list;
                    })
                );
        };

        return this.getAutocomplete(field, _apiRequest);
    }

    getLegalForms(): Observable<ComboBox[]> {
        return this.api
            .request<WrapperGetData<ComboBox>>('getConferenceLegalForms')
            .pipe(
                map((result: WrapperGetData<ComboBox>) => {
                    return result.list;
                })
            );
    }

    setConferenceType(cT: string): void {
        this.conferenceType = cT;
    }

    getDocumentType(): Observable<ComboBox[]> {
        return this.api
            .request<WrapperGetData<ComboBox[]>>('getConsoleDocumentType')
            .pipe(
                map((result: WrapperGetData<ComboBox[]>) => {
                    const arr = [];
                    arr.push({ key: '-1', value: 'Scegli tipo documento' });
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
        return this.api
            .request<WrapperGetData<ComboBox[]>>(
                'getConferenceDocumentationCategoryForDocumentType',
                null,
                params
            )
            .pipe(
                map((result: WrapperGetData<ComboBox[]>) => {
                    const arr = [];
                    arr.push({ key: '-1', value: 'Scegli categoria' });
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
    
    getDelegateAutocomplete(
        field: FormField
    ): { options$: Observable<ComboBox[]>; typeahead$: Subject<string> } {
        const _apiRequest = (search: string) => {
            let params = new HttpParams();
            params = params.set('key', search);
            return this.api
                .request<WrapperGetData<ComboBox[]>>(
                    'getDelegateAutocomplete',
                    null,
                    params
                )
                .pipe(
                    map((result: WrapperGetData<ComboBox[]>) => {
                        return result.list;
                    })
                );
        };

        return this.getAutocomplete(field, _apiRequest);
    }

    getCompanyAutocompleteForDelegate(
        field: FormField
    ): { options$: Observable<ComboBox[]>; typeahead$: Subject<string> } {
        const _apiRequest = (search: string) => {
            let params = new HttpParams();
            if (this.conferenceType !== '') {
                params = params.set('conferenceType', this.conferenceType);
            }
            params = params.set('value', search);
            return this.api
                .request<WrapperGetData<ComboBox[]>>(
                    'getCompanyAutocompleteForApplicant',
                    null,
                    params
                )
                .pipe(
                    map((result: WrapperGetData<ComboBox[]>) => {
                        return result.list;
                    })
                );
        };

        return this.getAutocomplete(field, _apiRequest);
    }

    getPreaccreditationAutocomplete(
        field: FormField
    ): { options$: Observable<ComboBox[]>; typeahead$: Subject<string> } {
        const _apiRequest = (search: string) => {
            let params = new HttpParams();
            params = params.set('key', search);
            return this.api
                .request<WrapperGetData<ComboBox[]>>(
                    'getPreaccreditationAutocomplete',
                    null,
                    params
                )
                .pipe(
                    map((result: WrapperGetData<ComboBox[]>) => {
                        return result.list;
                    })
                );
        };

        return this.getAutocomplete(field, _apiRequest);
    }

    
    getPreaccreditationPersonRoles(): Observable<ComboBox[]> {
        let params = new HttpParams();
        
        return this.get<ComboBox[]>(
            this.comboBoxRequest('preloading/getPersonRoles'),
            'profile');
    }
}
