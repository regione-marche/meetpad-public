import {
    defaultComboBox,
    toastrConf,
    autocompleteConf,
    backendApi,
    customConfigurationConf,
    loading,
    auth
} from '@config';
import { environmentDev } from '@common';

const domain = 'https://wso2.meetpad-dev.eng.it';

export const environment = {
    appName: 'APP_NAME',
    production: false,
    dateFormat: {
        toBe: 'yyyy-MM-dd[T]HH:mm:ss[Z]',
        toUI: 'dd-MM-yyyy'
    },
    onlyOfficeEnabled: true,
    ...auth({
        cohesionClientId: 'hfF6B1cFyE4aieGhmrejtH6nrWAa',
        spidClientId: 'hfF6B1cFyE4aieGhmrejtH6nrWAa',
        wso2Host: domain,
        baseUrl: 'meet-pad-svil'
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
        baseUrl: environmentDev.backend.baseUrl,
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

/*
 * In development mode, to ignore zone related error stack frames such as
 * `zone.run`, `zoneDelegate.invokeTask` for easier debugging, you can
 * import the following file, but please comment it out in production mode
 * because it will have performance impact when throw error
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
