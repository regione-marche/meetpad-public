export const backend = {
    environment: 'SVIL',
    api: [
        {
            name: 'simpleSearch',
            method: 'GET',
            url: '/conferences/simple'
        },
        {
            name: 'getAuthorities',
            method: 'GET',
            url: '/adminConsolle/authorities'
        },
        {
            name: 'getCompanies',
            method: 'GET',
            url: '/adminConsolle/companies'
        },
        {
            name: 'preloading/getCompanies',
            method: 'GET',
            url: '/adminConsolle/preemptiveCompanies'
        },
        {
            name: 'preloading/deleteParticipant',
            method: 'DELETE',
            url: '/adminConsolle/preemptiveParticipants/{id}'
        },
        {
            name: 'preloading/newParticipant',
            method: 'POST',
            url: '/adminConsolle/preemptiveParticipants'
        },
        {
            name: 'participantRoles',
            method: 'GET',
            url: '/utils/participantRoles'
        },
        {
            name: 'competenceAuthorizationForConferenceTypes',
            method: 'GET',
            url: '/utils/competenceAuthorizationForConferenceTypes'
        },
        {
            name: 'preloading/getParticipants',
            method: 'GET',
            url: '/adminConsolle/preemptiveParticipants'
        },
        {
            name: 'preloading/getParticipantDetail',
            method: 'GET',
            url: '/adminConsolle/preemptiveParticipants/{id}'
        },
        {
            name: 'preloading/editParticipant',
            method: 'PATCH',
            url: '/adminConsolle/preemptiveParticipants/{id}'
        },
        {
            name: 'preloading/getApplicants',
            method: 'GET',
            url: '/adminConsolle/preemptiveApplicants'
        },
        {
            name: 'preloading/getApplicantDetail',
            method: 'GET',
            url: '/adminConsolle/preemptiveApplicants/{id}'
        },
        {
            name: 'preloading/editApplicant',
            method: 'PATCH',
            url: '/adminConsolle/preemptiveApplicants/{id}'
        },
        {
            name: 'preloading/deleteApplicant',
            method: 'DELETE',
            url: '/adminConsolle/preemptiveApplicants/{id}'
        },
        {
            name: 'preloading/newApplicant',
            method: 'POST',
            url: '/adminConsolle/preemptiveApplicants'
        },
        {
            name: 'preloading/getCompanyDetail',
            method: 'GET',
            url: '/adminConsolle/preemptiveCompanies/{id}'
        },
        {
            name: 'preloading/getNewCompanyDetail',
            method: 'GET',
            url: '/adminConsolle/companies/{id}'
        },
        {
            name: 'preloading/deleteCompany',
            method: 'DELETE',
            url: '/adminConsolle/preemptiveCompanies/{id}'
        },
        {
            name: 'preloading/editCompany',
            method: 'PUT',
            url: '/adminConsolle/preemptiveCompanies/{id}'
        },
        {
            name: 'preloading/newCompany',
            method: 'POST',
            url: '/adminConsolle/preemptiveCompanies'
        },
        {
            name: 'preloading/getDelegates',
            method: 'GET',
            url: '/adminConsolle/preemptiveDelegates'
        },
        {
            name: 'preloading/getDelegateDetail',
            method: 'GET',
            url: '/adminConsolle/preemptiveDelegates/{id}'
        },
        {
            name: 'preloading/editDelegate',
            method: 'PATCH',
            url: '/adminConsolle/preemptiveDelegates/{id}'
        },
        {
            name: 'preloading/deleteDelegate',
            method: 'DELETE',
            url: '/adminConsolle/preemptiveDelegates/{id}'
        },
        {
            name: 'preloading/saveDelegateFile',
            method: 'POST',
            url: '/adminConsolle/saveDelegateFile'  
        },
        {
            name: 'preloading/downloadDelegateFile',
            method: 'GET',
            url: '/adminConsolle/downloadDelegateFile/{id}'
            
        },
        {
            name: 'preloading/deleteDelegateFile',
            method: 'POST',
            url: '/adminConsolle/deleteDelegateFile/{id}'  
        },
        {
            name: 'getDelegateAutocomplete',
            method: 'GET',
            url: '/adminConsolle/people'
        },
        {
            name: 'getDelegatePrecomplete',
            method: 'GET',
            url: '/adminConsolle/people/{id}'
        },
        {
            name: 'getAuthoritiesCombo',
            method: 'GET',
            url: '/utils/authorities'
        },
        {
            name: 'getAllConferenzaCreatorUsersCombo',
            method: 'GET',
            url: '/utils/authorities/conferenzaCreators'
        },
        {
            name: 'getConferenceDetail',
            method: 'GET',
            url: '/adminConsolle/conferences/{id}'
        },
        {
            name: 'getConferenceManagers',
            method: 'GET',
            url: '/utils/conferenceManagers'
        },
        {
            name: 'getConferenceManagersDomus',
            method: 'GET',
            url: '/utils/conferenceManagersDomus'
        },
        {
            name: 'createConferenceFromDomusAndAccountable',
            method: 'POST',
            url: '/domus/createconference/{comune}/{accountable}'
        },
        {
            name: 'getApplicantAutocomplete',
            method: 'GET',
            url: '/adminConsolle/people'
        },
        {
            name: 'getApplicantPrecomplete',
            method: 'GET',
            url: '/adminConsolle/people/{id}'
        },
        {
            name: 'uploadConsoleFile',
            method: 'POST',
            url: '/adminConsolle/conferences/{idConference}/documents'
        },
        {
            name: 'getConferenceDocumentationCategoryForDocumentType',
            method: 'GET',
            url: '/utils/documentCategoriesForDocumentType'
        },
        {
            name: 'getConsoleDocumentType',
            method: 'GET',
            url: '/adminConsolle/documentTypesConsolle'
        },
        {
            name: 'conferenceTypes',
            method: 'GET',
            url: '/utils/conferenceSpecializationTypes'
            //url: '/utils/conferenceTypes'
        },
        {
            name: 'editConference',
            method: 'PATCH',
            url: '/adminConsolle/conferences/{id}/conferenceManagerUpdate'
        },
        {
            name: 'newAuthority',
            method: 'POST',
            url: '/adminConsolle/authorities'
        },
        {
            name: 'editProceedingAuthority',
            method: 'PATCH',
            url: '/adminConsolle/authorities/{id}'
        },
        {
            name: 'editAuthority',
            method: 'PUT',
            url: '/adminConsolle/authorities/{id}'
        },
        {
            name: 'editUser',
            method: 'PATCH',
            url: '/adminConsolle/users/{id}'
        },
        {
            name: 'newUser',
            method: 'POST',
            url: '/adminConsolle/users'
        },
        {
            name: 'getManagers',
            method: 'GET',
            url: '/adminConsolle/conferenceManagers'
        },
        {
            name: 'deleteManager',
            method: 'DELETE',
            url: '/adminConsolle/conferenceManagers/{id}'
        },
        {
            name: 'deleteUser',
            method: 'DELETE',
            url: '/adminConsolle/users/{id}'
        },
        {
            name: 'newManager',
            method: 'POST',
            url: '/adminConsolle/conferenceManagers'
        },
        {
            name: 'getCompany',
            method: 'GET',
            url: '/utils/company'
        },
        {
            name: 'getIstatTypes',
            method: 'GET',
            url: '/utils/istatTypesAuthority'
        },
        {
            name: 'getAdministrativeTypes',
            method: 'GET',
            url: '/utils/administrativeTypesAuthority'
        },
        {
            name: 'getCompanyAutocomplete',
            method: 'GET',
            url: '/adminConsolle/noPaginatedCompanies'
        },
        {
            name: 'getCompanyAutocompleteForApplicant',
            method: 'GET',
            url: '/adminConsolle/noPaginatedpreemptiveCompanies'
        },
        {
            name: 'sendIndictionMail',
            method: 'POST',
            url: '/adminConsolle/sendIndictionMail/{idConference}'
        },
        {
            name: 'getStateConference',
            method: 'GET',
            url: '/adminConsolle/changeStatesConference/{idTipologia}'
        },
        {
            name: 'changeStateConference',
            method: 'POST',
            url: '/adminConsolle/updateStateConference/{id}/state/{idState}'
        },
        {
            name: 'getParticipantForConference',
            method: 'GET',
            url: '/adminConsolle/conferences/{id}/participants'
        },
        {
            name: 'getManagersDetail',
            method: 'GET',
            url: '/adminConsolle/conferenceManagers/{id}'
        },
        {
            name: 'getUsers',
            method: 'GET',
            url: '/adminConsolle/users'
        },
        {
            name: 'getUserDetail',
            method: 'GET',
            url: '/adminConsolle/users/{id}'
        },
        {
            name: 'getAuthorityDetail',
            method: 'GET',
            url: '/adminConsolle/authorities/{id}'
        },
        {
            name: 'getProfiles',
            method: 'GET',
            url: '/adminConsolle/profiles'
        },
        {
            name: 'getConference',
            method: 'GET',
            url: '/adminConsolle/conferences'
        },
        {
            name: 'getProtocols',
            method: 'GET',
            url: '/adminConsolle/protocols'
        },
        {
            name: 'getProvincies',
            method: 'GET',
            url: '/utils/provinces'
        },
        {
            name: 'getCities',
            method: 'GET',
            url: '/utils/cities'
        },
        {
            name: 'getConferenceLegalForms',
            method: 'GET',
            url: '/utils/legalForms'
        },
        {
            name: 'getAreas',
            method: 'GET',
            url: '/utils/areas'
        },
        {
            name: 'getStates',
            method: 'GET',
            url: '/utils/state'
        },
        {
            name: 'getQuestionsList',
            method: 'GET',
            url: '/adminConsolle/chatbot/getAllDomande'
            //url: 'http://localhost:3000/cdst_be_marche/chatbot/getAllDomande'
        },
        {
            name: 'getQuestionDetail',
            method: 'GET',
             url: '/adminConsolle/chatbot/questions/{id}'
            //url: 'http://localhost:3000/cdst_be_marche/chatbot/questions/{id}'
        },
        {
            name: 'editQuestion',
            method: 'PATCH',
            url: '/adminConsolle/chatbot/questions/{id}'
            //url: 'http://localhost:3000/cdst_be_marche/chatbot/questions/{id}'
        },
        {
            name: 'newQuestion',
            method: 'POST',
             url: '/adminConsolle/chatbot/newQuestion'
            //url: 'http://localhost:3000/cdst_be_marche/chatbot/newQuestion'
        },
        {
            name: 'deleteQuestion',
            method: 'DELETE',
             url: '/adminConsolle/chatbot/questions/{id}'
            //url: 'http://localhost:3000/cdst_be_marche/chatbot/questions/{id}'
        },
        {
            name: 'updateProtocolState',
            method: 'PATCH',
            headers: {
                'Accept-Language': 'it-IT,it;q=0.9,en-US;q=0.8,en;q=0.7'
            },
            url: '/adminConsolle/Protocols/{protocolId}'
        },
        {
            name: 'preloading/getPreaccreditation',
            method: 'GET',
            url: '/adminConsolle/preAccreditations'
        },
        {
            name: 'preloading/getPreaccreditation',
            method: 'GET',
            url: '/adminConsolle/preAccreditations/{id}'
        },
        {
            name: 'preloading/getPreaccreditationPrecomplete',
            method: 'GET',
            url: '/adminConsolle/people/{id}'
        },
        {
            name: 'preloading/getPersonRoles',
            method: 'GET',
            url: '/utils/accreditationRoles'
        },
        {
            name: 'getPreaccreditationAutocomplete',
            method: 'GET',
            url: '/adminConsolle/people'
        },       
        {
            name: 'preloading/deletePreaccreditation',
            method: 'DELETE',
            url: '/adminConsolle/preAccreditations/{id}'
        },
        {
            name: 'preloading/newPreaccreditation',
            method: 'POST',
            url: '/adminConsolle/preAccreditations'
        },
        {
            name: 'preloading/getPreaccreditationDetail',
            method: 'GET',
            url: '/adminConsolle/preAccreditations/{id}'
        },
        {
            name: 'preloading/editPreaccreditation',
            method: 'PATCH',
            url: '/adminConsolle/preAccreditations/{id}'
        }

    ]
};
