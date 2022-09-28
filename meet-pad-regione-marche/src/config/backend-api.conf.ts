import { ApiConfig } from '@eng-ds/ng-core';

export const backendApi: ApiConfig[] = [
    {
        name: 'simpleSearch',
        method: 'GET',
        url: '/conferences/simple'
    },
    {
        name: 'advancedSearch',
        method: 'GET',
        url: '/conferences/advanced'
    },
    {
        name: 'unifyResearch',
        method: 'GET',
        url: '/conferences/unifyResearch'
    },
    {
        name: 'createConference',
        method: 'POST',
        url: '/conferences'
    },
    {
        name: 'editConference',
        method: 'PUT',
        headers: {
            'Accept-Language': 'it-IT,it;q=0.9,en-US;q=0.8,en;q=0.7'
        },
        url: '/conferences/{id}'
    },
    {
        name: 'deleteConference',
        method: 'DELETE',
        headers: {
            'Accept-Language': 'it-IT,it;q=0.9,en-US;q=0.8,en;q=0.7'
        },
        url: '/conferences/{id}'
    },
    {
        name: 'cloneConference',
        method: 'GET',
        url: '/conferences/{id}/clone'
    },
    {
        name: 'updateStep',
        method: 'PATCH',
        headers: {
            'Accept-Language': 'it-IT,it;q=0.9,en-US;q=0.8,en;q=0.7'
        },
        url: '/conferences/{id}'
    },
    {
        name: 'updateState',
        method: 'PATCH',
        headers: {
            'Accept-Language': 'it-IT,it;q=0.9,en-US;q=0.8,en;q=0.7'
        },
        url: '/conferences/{id}'
    },
    {
        name: 'getConferenceDetail',
        method: 'GET',
        url: '/conferences/{id}'
    },
    {
        name: 'getConferencePermission',
        method: 'GET',
        url: '/conferences/{id}/permissions'
    },
    {
        name: 'patchEnabled',
        method: 'PATCH',
        url: '/conferences/{id}'
    },
    {
        name: 'getApplicantList',
        method: 'GET',
        url: '/utils/applicant'
    },
    {
        name: 'getAuthorities',
        method: 'GET',
        url: '/utils/authorities'
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
        name: 'conferenceTypes',
        method: 'GET',
        url: '/utils/conferenceSpecializationTypes'
    },
    {
        name: 'addressTypes',
        method: 'GET',
        url: '/utils/addressTypes'
    },
    {
        name: 'getApplicantTypes',
        method: 'GET',
        url: '/utils/practicesTypes'
    },
    {
        name: 'getConferenceActivities',
        method: 'GET',
        url: '/utils/activities'
    },
    {
        name: 'getConferenceActions',
        method: 'GET',
        url: '/utils/actions'
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
        name: 'getConferenceDocumentationCategory',
        method: 'GET',
        url: '/utils/documentCategoriesForConferenceTypes'
    },
    {
        name: 'getConferenceDocumentationCategoryForDocumentType',
        method: 'GET',
        url: '/utils/documentCategoriesForDocumentType'
    },
    {
        name: 'getCompany',
        method: 'GET',
        url: '/utils/company'
    },
    {
        name: 'getCompanyById',
        method: 'GET',
        url: '/utils/company/{id}'
    },
    {
        name: 'getAuthority',
        method: 'GET',
        url: '/utils/authority'
    },
    {
        name: 'participantRoles',
        method: 'GET',
        url: '/utils/participantRoles'
    },
    {
        name: 'getActorCompetence',
        method: 'GET',
        url: '/utils/companyExpertises'
    },
    {
        name: 'getPersonRoles',
        method: 'GET',
        url: '/utils/personRoles'
    },
    {
        name: 'getAccreditationRoles',
        method: 'GET',
        url: '/utils/accreditationRoles'
    },
    {
        name: 'getConferenceTemplate',
        method: 'GET',
        url: '/utils/conferenceTemplate'
    },
    {
        name: 'getUserInfo',
        method: 'GET',
        url: '/users/profile'
    },
    {
        name: 'getPendingAccreditations',
        method: 'GET',
        url: '/users/pendingAccreditations'
    },
    {
        name: 'getConferenceParticipant',
        method: 'GET',
        url: '/users/conferenceParticipant'
    },
    {
        name: 'getConferenceProfile',
        method: 'GET',
        url: '/users/conferenceProfile'
    },
    {
        name: 'getConferenceDelegate',
        method: 'GET',
        url: '/users/conferenceProfileDelegate'
    },
    {
        name: 'getParticipants',
        method: 'GET',
        url: '/conferences/{conferenceId}/participants'
    },
    {
        name: 'createParticipant',
        method: 'POST',
        url: '/conferences/{conferenceId}/participants'
    },
    {
        name: 'editParticipant',
        method: 'PUT',
        url: '/participants/{participantId}'
    },
    {
        name: 'deleteParticipant',
        method: 'DELETE',
        url: '/participants/{participantId}'
    },
    {
        name: 'getUsersParticipant',
        method: 'GET',
        url: '/conferences/{conferenceId}/participants/{participantId}/users'
    },
    {
        name: 'getAccreditedPerPartecipants',
        method: 'GET',
        url: '/conference/{conferenceId}/accreditationsParticipants'
    },
    {
        name: 'createUserParticipant',
        method: 'POST',
        url: '/conferences/{conferenceId}/participants/{participantId}/users'
    },
    {
        name: 'editUserParticipant',
        method: 'PUT',
        url: '/conferences/participants/users/{userId}'
    },
    {
        name: 'deleteUserParticipant',
        method: 'DELETE',
        url: '/conferences/participants/users/{userId}'
    },
    {
        name: 'getDocuments',
        method: 'GET',
        url: '/conferences/{conferenceId}/documents',
        timeout: 300000
    },
    {
        name: 'syncronizeDocuments',
        method: 'PATCH',
        url: '/conferences/{conferenceId}/syncronizeDocuments',
        timeout: 600000
    },
    {
        name: 'deleteDocumentList',
        method: 'PATCH',
        url: '/documents/deleteList'
    },
    {
        name: 'uploadFile',
        method: 'POST',
        url: '/conferences/{conferenceId}/documents'
    },
    {
        name: 'downloadFile',
        method: 'GET',
        url: '/downloadFile/{id}',
        timeout: 60000
    },
    {
        name: 'downloadZipFile',
        method: 'GET',
        url: '/downloadZipFile',
        timeout: 120000
    },
    {
        name: 'lockAndDownload',
        method: 'GET',
        url: '/lockAndDownload/{token}'
    },
    {
        name: 'unlockSignatureFile',
        method: 'PATCH',
        url: '/documents/{documentId}/unlock'
    },
    {
        name: 'editFile',
        method: 'PUT',
        url: '/documents/{id}',
    },
    {
        name: 'deleteFile',
        method: 'DELETE',
        url: '/documents/{id}'
    },
    {
        name: 'getAccreditations',
        method: 'GET',
        url: '/accreditations'
    },
    {
        name: 'postAccreditations',
        method: 'POST',
        url: '/accreditations'
    },
    {
        name: 'putAccreditations',
        method: 'PUT',
        headers: {
            'Accept-Language': 'it-IT,it;q=0.9,en-US;q=0.8,en;q=0.7'
        },
        url: '/accreditations/{id}'
    },
    {
        name: 'getListAccreditations',
        method: 'GET',
        url: '/conferences/{conferenceId}/accreditations'
    },
    {
        name: 'confirmAccreditation',
        method: 'PATCH',
        headers: {
            'Accept-Language': 'it-IT,it;q=0.9,en-US;q=0.8,en;q=0.7'
        },
        url: '/conferences/{conferenceId}/accreditations/{accreditationId}'
    },
    {
        name: 'getConferencesResults',
        method: 'get',
        url: '/utils/conferencesResults'
    },
    {
        name: 'getDocModelsForConferenceType',
        method: 'get',
        url: '/utils/documentModelsForConferenceType'
    },
    {
        name: 'getEventTypes',
        method: 'get',
        url: '/utils/eventTypes'
    },
    {
        name: 'getDateTypes',
        method: 'get',
        url: '/utils/dateTypes'
    },
    {
        name: 'getEventTypesForConference',
        method: 'get',
        url: '/conferences/{idConference}/eventTypes'
    },
    {
        name: 'getEvents',
        method: 'GET',
        url: '/conferences/{conferenceId}/events'
    },
    {
        name: 'searchEvents',
        method: 'GET',
        url: '/conferences/{idConference}/eventResearches'
    },
    {
        name: 'getEventById',
        method: 'GET',
        url: '/conferences/{conferenceId}/events/{eventId}'
    },
    {
        name: 'getSubjectsEvent',
        method: 'GET',
        url: '/utils/subjectsEvent'
    },
    {
        name: 'determinationTypes',
        method: 'GET',
        url: '/utils/determinationTypes'
    },
    {
        name: 'createEvent',
        method: 'POST',
        url: '/conferences/{conferenceId}/events'
    },
    {
        name: 'getPec',
        method: 'GET',
        url: '/conferences/{conferenceId}/pec'
    },
    {
        name: 'getPecStatus',
        method: 'GET',
        url: '/utils/pecStatus'
    },
    {
        name: 'getVotings',
        method: 'GET',
        url: '/conferences/{conferenceId}/votings'
    },
    {
        name: 'deleteVoting',
        method: 'DELETE',
        url: '/conferences/{conferenceId}/votings/{votingId}'
    },
    {
        name: 'editVoting',
        method: 'PUT',
        url: '/conferences/{conferenceId}/votings/{votingId}'
    },
    {
        name: 'postVoting',
        method: 'POST',
        url: '/conferences/{conferenceId}/votings'
    },
    {
        name: 'postVote',
        method: 'POST',
        url: '/conferences/{conferenceId}/votings/{votingId}/vote'
    },
    {
        name: 'getContacts',
        method: 'GET',
        url: '/conferences/{conferenceId}/supportContacts'
    },
    {
        name: 'createContact',
        method: 'POST',
        url: '/conferences/{conferenceId}/supportContacts'
    },
    {
        name: 'editContact',
        method: 'PUT',
        url: '/supportContacts/{contactId}'
    },
    {
        name: 'deleteContact',
        method: 'DELETE',
        url: '/supportContacts/{contactId}'
    },
    {
        name: 'calendarEvents',
        method: 'GET',
        url: '/utils/calendaries'
    },
    {
        name: 'downloadMemoConferenceTemplate',
        method: 'GET',
        url: '/conferences/{conferenceId}/downloadFile/template'
    },
    {
        name: 'createConferenceFromPaleo',
        method: 'POST',
        url: '/paleo/createconference'
    },
    {
        name: 'createConferenceFromDomus',
        method: 'POST',
        url: '/domus/createconference/{comune}'
    },
    {
        name: 'createConferenceFromDomusAndAccountable',
        method: 'POST',
        url: '/domus/createconference/{comune}/{conferenceManagersDomus}'
    },
    {
        name: 'getOOData',
        method: 'GET',
        url: '/docucondivisa/ooeditdoc/{token}'
    },
    {
        name: 'getMediaLibrary',
        method: 'GET',
        url: '/media-library'
    },
    {
        name: 'getCalamaioStatus',
        method: 'GET',
        url: 'http://127.0.0.1:10200/status'
    },
    {
        name: 'postCalamaioFillxml',
        method: 'POST',
        url: '/calamaio/fillxml',
        timeout: 30000
    },
    {
        name: 'postCalamaioSign',
        method: 'POST',
        headers: {
            'Content-Type': 'application/xml'
        },
        url: 'http://127.0.0.1:10200/sign',
        //url: 'http://localhost:3000/cdst_be_marche/calamaio/sign',
        timeout: 30000
    },
    {
        name: 'unlockSignatureFileWithCallabck',
        method: 'PATCH',
        url: '/documents/{documentId}/unlockcallback',
        timeout: 30000
    },
    {
        name: 'downloadCalamaioApplication',
        method: 'GET',
        url: '/calamaio/download'
    },
    {
        name: 'getReportList',
        method: 'GET',
        url: '/report/'  
    },
    {
        name: 'downloadReport',
        method: 'GET',
        url: '/report/{code}'  
    },
    {
        name: 'getAllDocumentInSign',
        method: 'GET',
        url: '/conferences/advanced'
    },
    {
        name: 'getDocumentsInDraft',
        method: 'GET',
        url: '/sign/documentsindraft'
    },
    {
        name: 'getSignedDocuments',
        method: 'GET',
        url: '/sign/documentssigned'
    },   
    {
        name: 'getRejectedDocuments',
        method: 'GET',
        url: '/sign/documentsrejected'
    },
    {
        name: 'rejectDocumentList',
        method: 'PATCH',
        url: '/sign/documents/reject'
    },
    {
        name: 'signDocumentList',
        method: 'PATCH',
        url: '/sign/documents/signdocuments'
    },
    {
        name: 'getManagerSignatures',
        method: 'GET',
        url: '/conferences/{conferenceId}/listSignatures'
    },
    {
        name: 'markDocumentToSign',
        method: 'POST',
        url: '/conferences/{idConference}/marktosigndocuments'
    },
    {
        name: 'createEventSendToSign',
        method: 'POST',
        url: '/conferences/{idConference}/eventsSendToSign'
    },
    {
        name: 'getPermissionsToSign',
        method: 'GET',
        url: '/conferences/{conferenceId}/permissionsToSign'
    },
    {
        name: 'getProtocollings',
        method: 'GET',
        url: '/conferences/{conferenceId}/Protocols'
    },
    {
        name: 'updateProtocolState',
        method: 'PATCH',
        headers: {
            'Accept-Language': 'it-IT,it;q=0.9,en-US;q=0.8,en;q=0.7'
        },
        url: '/conferences/Protocols/{protocolId}'
    },
    {
        name: 'deleteParticipantList',
        method: 'PATCH',
        url: '/participants/deleteList'
    },
    {
        name: 'getConferenceStatus',
        method: 'GET',
        url: '/utils/conferenceStatus'
    }
];
