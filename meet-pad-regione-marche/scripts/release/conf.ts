export enum BranchName {
    DEVELOP = 'marche/develop',
    MASTER = 'marche/master',
    OO = 'features/onlyoffice'
}

export enum EnvName {
    DEV = 'dev',
    STAGING = 'staging',
    TEST = 'test',
    PRE_PROD = 'pre-prod',
    PROD = 'prod'
}

export enum Task {
    BUILD = 'b',
    ARCHIVE = 'a'
}

export interface ReleaseOptions {
    branches: BranchName[];
    buildBaseHref: string;
}

export const projectName = 'ngx-eng-blank-template';

export const mapBranchsEnvironments = new Map<EnvName, ReleaseOptions>()
    .set(EnvName.DEV, {
        branches: [BranchName.DEVELOP],
        buildBaseHref: '/meet-pad-svil/'
    })
    .set(EnvName.STAGING, {
        branches: [BranchName.DEVELOP],
        buildBaseHref: '/meet-pad/'
    })
    .set(EnvName.PROD, {
        branches: [BranchName.DEVELOP],
        buildBaseHref: '/'
    })
    .set(EnvName.PRE_PROD, {
        branches: [BranchName.DEVELOP],
        buildBaseHref: '/'
    })
    .set(EnvName.TEST, {
        branches: [BranchName.DEVELOP, BranchName.OO],
        buildBaseHref: '/'
    });
