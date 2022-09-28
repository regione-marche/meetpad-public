export enum BranchName {
    DEVELOP = 'marche/develop',
    VOTING = 'features/voting',
    REPORT = 'features/report',
    MY_BRANCH = 'marche/feature/npm_run_serve'
}

export enum EnvName {
    LOCAL = 'local'
}

export const defaultServEnv = EnvName.LOCAL;

export const mapBranchsEnvironments = new Map<EnvName, BranchName[]>().set(
    EnvName.LOCAL,
    [BranchName.DEVELOP, BranchName.VOTING, BranchName.REPORT, BranchName.MY_BRANCH] 
);
