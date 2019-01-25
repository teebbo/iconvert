const fs = require('fs'),
      path = require('path');

const MONGODB_SCHEME = 'mongodb://'
const params = {};

( (filepath) => {
  let config = fs.readFileSync(filepath, {encoding: 'utf-8'});
  if(config) {
    let o = JSON.parse(config);
    params['db'] = o['db'];
    params['remote_server'] = o['remote_server']
  }
}) (path.resolve(path.dirname(__dirname), 'settings.json'));

params.db.url = `${MONGODB_SCHEME}${params.db.user}:${params.db.password}@`
                  + `${params.db.host}:${params.db.port}/${params.db.name}`;


//console.log('params', params);
module.exports = params;