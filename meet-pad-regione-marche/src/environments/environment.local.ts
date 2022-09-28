import {
    defaultComboBox,
    toastrConf,
    autocompleteConf,
    backendApi,
    customConfigurationConf,
    loading,
    auth
} from '@config';
import { environmentLocal } from '@common';

const wso2Host = 'https://wso2.meetpad-dev.eng.it';
const appHost = 'http://localhost:4200';

export const environment = {
    appName: 'APP_NAME',
    production: false,
    dateFormat: {
        toBe: 'yyyy-MM-dd[T]HH:mm:ss[Z]',
        toUI: 'dd-MM-yyyy'
    },
    onlyOfficeEnabled: true,
    ...auth({
        cohesionClientId: 'FvtYegh1pcYySvIMim2s8HowxLoa',
        spidClientId: 'hfF6B1cFyE4aieGhmrejtH6nrWAa',
        wso2Host,
        appHost
    }),
    defaultComboBox,
    toastrConf,
    autocompleteConf,
    loading,
    feature: {
        desktop: {
            search: {
                mutexOpen: false,
                baseInitialOpen: true,
                advancedInitialOpen: false
            }
        }
    },
    backend: {
        environment: 'SVIL',
        baseUrl: environmentLocal.backend.baseUrl,
        api: backendApi
    },
    logger: {
        level: 0, // all log
        hasRemote: false,
        remoteLogUrl: null,
        cache: false
    },
    devMode: false,
    jitsi: 'https://jitsitest.regione.marche.it',
    customConfigurationConf
};
