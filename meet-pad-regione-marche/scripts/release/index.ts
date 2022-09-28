import { resolve } from 'path';
import * as branch_name from 'current-git-branch';
import * as release from 'release-it';
import chalk from 'chalk';
import {
    mapBranchsEnvironments,
    projectName,
    EnvName,
    BranchName,
    ReleaseOptions,
    Task
} from './conf';

function main(): void {
    const env = Array.from(process.argv)
        .slice(2, 3)
        .join(' ')
        .replace('--env=', '') as EnvName;

    const tasks = Array.from(process.argv)
        .slice(3, 4)
        .join(' ')
        .replace('--tasks=', '')
        .split('') as Task[];

    const currentBranch = branch_name() as string;
    const customerBranch = currentBranch.substring(
        0,
        currentBranch.indexOf('/')
    );

    if (!customerBranch) {
        console.log(
            chalk.red(`Can't release on ${chalk.bold(currentBranch)} branch`)
        );
        return;
    }

    if (mapBranchsEnvironments.has(env)) {
        const releaseOptions = mapBranchsEnvironments.get(env);
        if (
            releaseOptions.branches.find((b: BranchName) => b === currentBranch)
        ) {
            console.log(
                chalk.green(
                    `Release ${chalk.bold(env)} environment on ${chalk.bold(
                        currentBranch
                    )} branch`
                )
            );
            _release(customerBranch, env, tasks, releaseOptions);
        } else {
            console.log(
                chalk.red(
                    `Can't execute ${chalk.bold(
                        env
                    )} environment on ${chalk.bold(currentBranch)} branch`
                )
            );
        }
    } else {
        console.log(chalk.red(`${chalk.bold(env)} environment not exist`));
    }
}

function _release(
    customerBranch: string,
    env: string,
    tasks: Task[],
    options: ReleaseOptions
): void {
    const releaseItJson: any = require('./release-it.json');
    releaseItJson.git.tagName = `${customerBranch}-${env}-\${version}`;
    releaseItJson.git.commitMessage = `release(\${version}): ${customerBranch}-${env}`;

    if (tasks.includes(Task.BUILD)) {
        releaseItJson.hooks = Object.assign({}, releaseItJson.hooks, {
            'before:init': [
                'npm run showdown',
                `${_buildCmd(options.buildBaseHref, env)}`
            ]
        });
    }
    if (tasks.includes(Task.ARCHIVE)) {
        releaseItJson.hooks['after:bump'] = `${_archiveCmd(
            customerBranch,
            env
        )}`;
    }
    release(releaseItJson).then(
        output => {
            // console.log(output);
        },
        err => {
            // console.log(err);
        }
    );
}

function _buildCmd(buildBaseHref: string, env: string): string {
    return [
        'node',
        require.resolve('@angular/cli/bin/ng'),
        'build',
        `--base-href=${buildBaseHref}`,
        `--configuration=${env}`,
        '--delete-output-path',
        '--sourceMap=false'
    ].join(' ');
}

function _archiveCmd(customerBranch: string, env: string): string {
    return [
        'node',
        resolve('scripts/release/archive.js'),
        `--customerBranch=${customerBranch}`,
        `--env=${env}`,
        `--projectName=${projectName}`
    ].join(' ');
}

main();
