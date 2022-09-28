import {
    defaultComboBox,
    toastrConf,
    autocompleteConf,
    backendApi,
    customConfigurationConf,
    loading,
    auth
} from '@config';

import { environmentTest } from '@common';

const domain = 'https://meetpad-test.regione.marche.it';

export const environment = {
    appName: 'APP_NAME',
    production: true,
    dateFormat: {
        toBe: 'yyyy-MM-dd[T]HH:mm:ss[Z]',
        toUI: 'dd-MM-yyyy'
    },
    onlyOfficeEnabled: true,
    ...auth({
        cohesionClientId: 'tiRYWfMOdNdnjvT8XgCa5eB21jUa',
        spidClientId: '1EMobfSyazZsKcqbNVS5PWUeo9Aa',
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
        baseUrl: environmentTest.backend.baseUrl,
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
