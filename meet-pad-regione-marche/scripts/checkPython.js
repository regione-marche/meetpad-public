const { exec } = require('child_process');
const semver = require('semver');

const EXPECTED_PYTHON_VERSION = "2";

exec('python -c "import platform; print(platform.python_version())"',
     function(err, stdout, stderr) {
  let currentPythonVersion = stdout.toString();
  if (!currentPythonVersion) {
    throw new Error(`Python not found, please install version ${EXPECTED_PYTHON_VERSION}`);  
  } else {
      currentPythonVersion = currentPythonVersion.replace('\r', '').replace('\n', '');
  }
  
  if(!semver.satisfies(currentPythonVersion, EXPECTED_PYTHON_VERSION)) { 
    throw new Error(`Expected Python versions '${EXPECTED_PYTHON_VERSION}' but found '${currentPythonVersion}'. Please fix your Python installation.`);
  } else {
      console.log(`Python version found '${currentPythonVersion}', it satisfy expected '${EXPECTED_PYTHON_VERSION}'.`)
  }
});