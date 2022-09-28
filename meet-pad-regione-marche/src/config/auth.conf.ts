function commonOauthConf({
    wso2Host,
    appHost = wso2Host,
    baseUrl = ''
}: Partial<AuthParams>) {
    function _getAppUrl() {
        if (baseUrl) {
            return `${appHost}/${baseUrl}`;
        }
        return appHost;
    }
    return {
        scope: 'openid',
        issuer: `${wso2Host}/oauth2/token`,
        loginUrl: `${wso2Host}/oauth2/authorize`,
        logoutUrl: `${wso2Host}/oidc/logout`,
        redirectUri: `${_getAppUrl()}/auth`,
        postLogoutRedirectUri: `${_getAppUrl()}/public`,
        silentRefreshRedirectUri: `${_getAppUrl()}/silent-refresh.html`,
        // timeoutFactor: 0.002,
        timeoutFactor: 0.9,
        disableAtHashCheck: true,
        clearHashAfterLogin: false
    };
}

interface AuthParams {
    cohesionClientId: string;
    spidClientId: string;
    wso2Host: string;
    appHost?: string;
    baseUrl?: string;
}

export function auth({
    cohesionClientId,
    spidClientId,
    wso2Host,
    appHost = wso2Host,
    baseUrl = ''
}: AuthParams) {
    return {
        auth: {
            enabled: true,
            discovery: false,
            defaultLoginRoute: '/',
            defaultAdminLoginRoute: '/admin',
            defaultPublicRoute: '/public',

            oauth2Cohesion: {
                clientId: cohesionClientId,
                ...commonOauthConf({
                    wso2Host,
                    appHost,
                    baseUrl
                })
            },

            oauth2Spid: {
                clientId: spidClientId,
                ...commonOauthConf({
                    wso2Host,
                    appHost,
                    baseUrl
                })
            }
        }
    };
}
