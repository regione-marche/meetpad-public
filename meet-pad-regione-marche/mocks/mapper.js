module.exports = {
    get: {
        '/cdst_be_marche/conferences/advanced':
            '/cdst_be_marche/conferences/advanced',
        '/cdst_be_marche/conferences/participants':
            '/cdst_be_marche/conferences/:id/participants',
        '/cdst_be_marche/events/events':
            '/cdst_be_marche/conferences/:id/events',
        '/cdst_be_marche/events/event':
            '/cdst_be_marche/conferences/:id/events/:eventId',
        '/cdst_be_marche/pec/pec': '/cdst_be_marche/conferences/:id/pec',
        '/cdst_be_marche/votings/voting': '/cdst_be_marche/votings/voting',
        '/cdst_be_marche/accreditations/listAccreditations':
            '/cdst_be_marche/listAccreditations/:conferenceId',
        '/cdst_be_marche/conferences/participantUser':
            '/cdst_be_marche/conferences/:id/participants/:participantId/users',
        '/cdst_be_marche/conferences/cloneConference':
            '/cdst_be_marche/conferences/:id/clone',
        '/cdst_be_marche/calendar/calendarEvents':
            '/cdst_be_marche/calendar/calendarEvents',
        '/cdst_be_marche/utils/eventRecipients':
            '/cdst_be_marche/utils/eventRecipients',
        '/cdst_be_marche/utils/applicantEditor':
            '/cdst_be_marche/utils/applicantEditor',
        '/cdst_be_marche/utils/competence': '/cdst_be_marche/utils/competence',

        '/cdst_be_marche/conferences/documents':
            '/cdst_be_marche/conferences/:id/documents',
        '/cdst_be_marche/conferences/accreditationsParticipants':
            '/cdst_be_marche/conferences/:id/accreditationsParticipants',            
        '/cdst_be_marche/conferences/downlaodSignatureFileAndLock':
        '/cdst_be_marche/conferences/:id/documents/:documentId/sign',
        '/cdst_be_marche/documents/downloadFile':
            '/cdst_be_marche/downloadFile/:id',

        '/cdst_be_marche/chatbot/getAllDomande': '/cdst_be_marche/chatbot/getAllDomande',
        '/cdst_be_marche/chatbot/questions': '/cdst_be_marche/chatbot/questions/:id/',

        '/cdst_be_marche/calamaio/status': '/cdst_be_marche/calamaio/status'
    },
    put: {
        '/cdst_be_marche/conferences/conference':
            '/cdst_be_marche/conferences/:id',
        '/cdst_be_marche/conferences/participantUser':
            '/cdst_be_marche/conferences/participants/users/:id',
        '/cdst_be_marche/accreditations/accreditations':
            '/cdst_be_marche/accreditations/:id',
            '/cdst_be_marche/documents/document':
                '/cdst_be_marche/documents/:id'
    },
    post: {
        '/cdst_be_marche/conferences/conference': '/cdst_be_marche/conferences',
        '/cdst_be_marche/conferences/participantUser':
            '/cdst_be_marche/conferences/:id/participants/:participantId/users',
        '/cdst_be_marche/conferences/documents':
            '/cdst_be_marche/conferences/:id/documents',
        '/cdst_be_marche/accreditations/accreditations':
            '/cdst_be_marche/accreditations',
            '/cdst_be_marche/documents/document':
                '/cdst_be_marche/conferences/:id/documents',
        
        '/cdst_be_marche/calamaio/fillxml':'/cdst_be_marche/calamaio/fillxml',
        '/cdst_be_marche/calamaio/sign':'/cdst_be_marche/calamaio/sign',
    },
    patch: {
        '/cdst_be_marche/conferences/conference': '/cdst_be_marche/conferences',
        '/cdst_be_marche/conferences/participantUser':
            '/cdst_be_marche/conferences/:id/participants/:participantId/users',
        '/cdst_be_marche/conferences/documents':
            '/cdst_be_marche/conferences/:id/documents',
        '/cdst_be_marche/conferences/unlockSignatureFile':
            '/cdst_be_marche/conferences/:conferenceId/documents/:documentId/unlock',
        '/cdst_be_marche/conferences/signDocument':
        '/cdst_be_marche/conferences/:conferenceId/documents/:documentId/sign',
        
        '/cdst_be_marche/calamaio/unlockcallback':'/cdst_be_marche/documents/:documentId/unlockcallback'
    },
    delete: {
        '/cdst_be_marche/conferences/participantUser':
            '/cdst_be_marche/conferences/participants/users/:id',
        '/cdst_be_marche/conferences/signatureFile':
        '/cdst_be_marche/conferences/:conferenceId/documents/:documentId',
        '/cdst_be_marche/documents/document':
        '/cdst_be_marche/documents/:documentId'
    }
};
