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

const domain = 'https://meetpad.regione.marche.it';

export const environment = {
    appName: 'APP_NAME',
    production: true,
    dateFormat: {
        toBe: 'yyyy-MM-dd[T]HH:mm:ss[Z]',
        toUI: 'dd-MM-yyyy'
    },
    ...auth({
        cohesionClientId: 'q9VrOAuv1DOUPVkgMFMoT0pSVZIa',
        spidClientId: 'z036OTH5DM0oMsWQsMbYIbdlGA0a',
        wso2Host: domain
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
    jitsi: 'https://jitsi.regione.marche.it/',
    customConfigurationConf
};
