import {
    defaultComboBox,
    toastrConf,
    autocompleteConf,
    backendApi,
    customConfigurationConf,
    loading,
    auth
} from '@config';
import { environmentPreProd } from '@common';
// import { environmentLocal } from '@common';

const domain = 'https://meetpad.regione.marche.it';
// const wso2Host = 'https://wso2.meetpad-dev.eng.it';
// const appHost = 'http://localhost:4200';

export const environment = {
    appName: 'APP_NAME',
    production: true,
    dateFormat: {
        toBe: 'yyyy-MM-dd[T]HH:mm:ss[Z]',
        toUI: 'dd-MM-yyyy'
    },
    onlyOfficeEnabled: true,
    ...auth({
        cohesionClientId: 'q9VrOAuv1DOUPVkgMFMoT0pSVZIa',
        spidClientId: 'z036OTH5DM0oMsWQsMbYIbdlGA0a',
        wso2Host: domain
    }),
    // ...auth({
    //     cohesionClientId: 'FvtYegh1pcYySvIMim2s8HowxLoa',
    //     spidClientId: 'hfF6B1cFyE4aieGhmrejtH6nrWAa',
    //     wso2Host,
    //     appHost
    // }),
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
        environment: 'PRODUCTION',
        baseUrl: environmentPreProd.backend.baseUrl,
        api: backendApi
    },
    logger: {
        level: 5, // none log
        hasRemote: false,
        remoteLogUrl: null,
        cache: false
    },
    devMode: false,
    jitsi: 'https://jitsitest.regione.marche.it',
    customConfigurationConf
};
