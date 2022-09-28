/*
    The mock server maps the url to a folder in the roote folder according to:
    ->  /mocks/api/getTest.json is mapped to -> http://localhost:3000/api/getTest
    ->  /mocks/api/sub/getTest.json is mapped to -> http://localhost:3000/api/sub/getTest

    It accepts .get .post . put. patch .delete requests

    To change the corrispondence between json file and api path for specific use cases use the mapper.js

*/

/***
 * Server start
 * node .\mocks\server.js
 */

var express = require('express');
var path = require('path');
var logger = require('morgan');
var bodyParser = require('body-parser');
var fs = require('fs');
var cors = require('cors');
var glob = require('glob');
const chalk = require('chalk');
var applicationRoot = __dirname.replace(/\\/g, '/');
var mockRoot = applicationRoot + '/v1';
var mockFilePattern = '.json';
var mockRootPattern = mockRoot + '/**/*' + mockFilePattern;
var apiRoot = '/cdst_be_marche'; // /rest/v1/macrosezione/entità/: param      GET /rest/inventory/v1/products/1234
var mapper = require('./mapper');
var order = require('./sort');
const delay = 200;

var app = express();
app.set('port', process.env.PORT || 3000);

console.log(
    chalk.green.bold(
        '==================================================================='
    )
);

// MDLEWARES
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(cors()); /* CORS-enabled for all origins! */

/* Read the directory tree according to the pattern specified above. */
var files = glob.sync(mockRootPattern, { nosort: false });

/* Register mappings for each file found in the directory tree. */
if (files && files.length > 0) {
    /** esponse prima i file specificati nel file sort.js */
    for (const k in order) {
        const fileName = order[k];
        const fileIndex = files.findIndex(file => {
            return file.indexOf(fileName) > -1;
        });
        expose(files[fileIndex]);
        files.splice(fileIndex, 1);
    }

    files.forEach(function(fileName) {
        expose(fileName);
    });
} else {
    console.log(
        chalk.red.bold('No mappings found! Please check the configuration.')
    );
}

function expose(fileName) {
    /** nome del file senza estenzione e path con methodo*/
    const fileNameWithoutPath = fileName
        .replace(mockRoot, '')
        .replace(mockFilePattern, '');

    /** Controllo se il nome del file contiene la keyword del method */
    const index_ = fileNameWithoutPath.indexOf('_');
    const pre_method = fileNameWithoutPath.substring(1, index_);

    /** estrazione del path di sottocartelle all'interno di v1 */
    const indexSlash = pre_method.indexOf('/');
    let method = pre_method;
    let oneLevelDir = '';
    if (indexSlash > -1) {
        method = pre_method.substr(indexSlash - (pre_method.length - 1));
        oneLevelDir = pre_method.replace('/' + method, '');
    }

    /** estrae la parte del file name che contiene il methodo es. "get_" */
    let mapping =
        apiRoot +
        '/' +
        oneLevelDir +
        '/' +
        fileNameWithoutPath.substring(index_ + 1);

    /** legge eventuali mapper per il metodo */
    if (mapper[method.toLowerCase()]) {
        Object.keys(mapper[method.toLowerCase()]).forEach(elem => {
            if (elem === mapping) mapping = mapper[method.toLowerCase()][elem];
        });
    }
    console.log(
        chalk.cyan.bold(
            method.toUpperCase() + ' ' + mapping + ' -> ' + fileName
        )
    );

    app[method](mapping, function(req, res) {
        var data = fs.readFileSync(fileName, 'utf8');
        res.writeHead(200, {
            'Content-Type': 'application/json'
        });
        setTimeout(() => {
            res.write(data);
            res.end();
        }, delay);
    });
}

// catch 404 and forward to error handler
app.use(function(req, res, next) {
    res.status(404).json({
        message: "Sorry can't find that!"
    });
});

// error handlers

// development error handler
// will print stacktrace
/* if (app.get('env') === 'development') {
  app.use(function (err, req, res, next) {
    console.log("second");
    res.status(err.status || 500);
    res.render('error', {
      message: err.message,
      error: err
    });
  });
}

// production error handler
// no stacktraces leaked to user
app.use(function (err, req, res, next) {
  console.log("tre");
  res.status(err.status || 500);
  res.render('error', {
    message: err.message,
    error: {}
  });
}); */

app.listen(app.get('port'), function() {
    console.log(
        chalk.green.bold(
            '==================================================================='
        )
    );
    console.log(
        chalk.green.bold('Mock Server is listening on port: ' + app.get('port'))
    );
});

module.exports = app;
