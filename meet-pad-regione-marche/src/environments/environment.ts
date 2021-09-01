// This file can be replaced during build by using the `fileReplacements` array.
// `ng build ---prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
    appName: 'APP_NAME',
    production: false,
    backend: {
        environment: 'PROD',
        baseUrl: 'http://localhost:3000',
        api: [
            {
                name: 'getDesktopList',
                method: 'GET',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                url: '/rest/v1/list'
            },
            {
                name: 'credentials',
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                url: '/authentication/credentials'
            }
        ]
    },
    logger: {
        level: 0, // all log
        hasRemote: false,
        remoteLogUrl: null
    },
    devMode: false
};

/*
 * In development mode, to ignore zone related error stack frames such as
 * `zone.run`, `zoneDelegate.invokeTask` for easier debugging, you can
 * import the following file, but please comment it out in production mode
 * because it will have performance impact when throw error
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
