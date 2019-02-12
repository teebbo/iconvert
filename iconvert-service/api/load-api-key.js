'use strict';

const path = require('path');

const load_api_key = (mode) => {
    if(mode === 'dev') {
        const config = require('dotenv').config({ path : path.resolve(__dirname, '.env')});
        if(config.error) {
            throw config.error;
        }

        const parsed = config.parsed;
        return parsed.APP_ID
    }
};

module.exports = load_api_key;
