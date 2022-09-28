// var parseChangelog = require('changelog-parser')

import * as parseChangelog from 'changelog-parser';

parseChangelog('./CHANGELOG.md', function(err, result) {
    if (err) throw err;

    // changelog object
    console.log(result.versions[1]);
});
