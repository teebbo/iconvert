'use strict';

const path = require('path');

const _db = (mode) => {
    if(mode === 'dev') {
        const config = require('dotenv').config({ path : path.resolve(__dirname, '.env')});
        if(config.error) {
            throw config.error;
        }

        const db = config.parsed;
        return {
            MONGO_URI: `mongodb+srv://${db.USERNAME}:${db.PASSWORD}@${db.SERVER}/${db.DBNAME}?retryWrites=true`,
            options: {
                useNewUrlParser: true,
                useCreateIndex: true,
                poolSize: 3,
                connectTimeoutMS: 5000
            }
        }
    }
};

module.exports = _db;
