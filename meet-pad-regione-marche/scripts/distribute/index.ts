import { exec } from '../utils';

const pkgJson: any = require('../../package.json');

function main(): void {
    const env: string = Array.from(process.argv)
        .slice(2, 3)
        .join(' ')
        .replace('--env=', '');

    console.log(`Distribute ${env} environment`);
    _distribute(env);
}

function _distribute(env: string): void {
    exec(
        [
            'node',
            require.resolve(
                'mobile-app-distribution/libs/bin/distribute-angular.js'
            ),
            pkgJson.version,
            '--config',
            `scripts/distribute/environments/distribute.${env}.json`,
            '--tasks',
            'bvd', // b=build, v=update version on footer, d=deploy to server
            '-f', // not wait the yes tap
            '-v' // verbose
        ],
        {}
    );
}

main();
