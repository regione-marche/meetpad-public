import {
    defaultComboBox,
    toastrConf,
    autocompleteConf,
    backendApi,
    customConfigurationConf,
    loading
} from '@config';

export const environment = {
    appName: 'APP_NAME',
    production: false,
    auth: {
        enabled: true,
        discovery: true,
        logoutUrl: 'http://localhost:4200',
        defaultLoginRoute: '/',
        defaultAdminLoginRoute: '/admin',
        defaultPublicRoute: '/public',
        oauth2: {
            issuer: 'https://demo.identityserver.io',
            redirectUri: window.location.origin + '/auth',
            clientId: 'implicit',
            scope: 'openid profile email api',
            clearHashAfterLogin: false
        }
    },
    dateFormat: {
        toBe: 'yyyy-MM-dd[T]HH:mm:ss[Z]',
        toUI: 'dd-MM-yyyy'
    },
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
        environment: 'MOCKS',
        baseUrl: 'http://localhost:3000/cdst_be_marche',
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
import 'zone.js/dist/zone-error';
