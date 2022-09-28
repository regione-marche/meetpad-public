import { execSync } from 'child_process';
import * as branch_name from 'current-git-branch';
import chalk from 'chalk';
import { mapBranchsEnvironments, BranchName, defaultServEnv } from './conf';

const currentBranch = branch_name() as string;
const skipBranchCheckString = "skip-branch-check";
function main(): void {

    let skipBranchCheck = false;
    process.argv.forEach( arg => {
        if(arg === skipBranchCheckString){
            skipBranchCheck = true;
        }
    });
    
    if(skipBranchCheck){
        _serve();
        return;
    }
   
    
    if (
        mapBranchsEnvironments
            .get(defaultServEnv)
            .find((b: BranchName) => b === currentBranch)
    ) {
        console.log(
            chalk.green(
                `Serve ${chalk.bold(
                    defaultServEnv
                )} environment on ${chalk.bold(currentBranch)} branch`
            )
        );
        _serve();
    } else {
        console.log(
            chalk.red(
                `Can't execute ${chalk.bold(
                    defaultServEnv
                )} environment on ${chalk.bold(currentBranch)} branch`
            )
        );
    }
}

function _exec(args: string[], opts: { cwd?: string }) {
    execSync(args.join(' '), {
        stdio: 'inherit',
        ...opts
    });
}

function _serve() {
    _exec(
        [
            'node',
            require.resolve('@angular/cli/bin/ng'),
            'serve',
            `-c ${defaultServEnv}`
        ],
        {}
    );
}

main();
