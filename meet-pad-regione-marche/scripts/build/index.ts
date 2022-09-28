import { exec } from '../utils';

const pkgJson: any = require('../../package.json');

function main(): void {
    const env: string = Array.from(process.argv)
        .slice(2, 3)
        .join(' ')
        .replace('--env=', '');

    console.log(`Build ${env} environment`);
    _build(env);
}

function _build(env: string): void {

    console.info("write changelog.html")
    exec(
        [
            'npm',
            `run showdown makehtml -i CHANGELOG.md -o src/changelog.html`,
        ],
        {}
    );

    console.info("build application")
    exec(
        [
            'node',
            require.resolve(
                'mobile-app-distribution/libs/bin/distribute-angular.js'
            ),
            pkgJson.version,
            '--config',
            `scripts/build/environments/build.${env}.json`,
            '--tasks',
            'bv', // b=build, v=update version on footer, d=deploy to server
            '-f', // not wait the yes tap
            '-v' // verbose
        ],
        {}
    );
}

main();
