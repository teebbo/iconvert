'use strict';

const Opera = require('@aleengo/opera');
const mongoose = require('mongoose');

const Currency = require('./model/currency');
const Etag = require('./model/etag');

const db = require('./db')('dev');
const appID = require('./load-api-key')('dev');

mongoose.connect(db.MONGO_URI, db.options, function(e) {
    if(e) {
        throw e
    }
    console.log('Successfully connected to MongoDB.');
});

const opera = new Opera(appID);

async function get_all_currencies() {
    try {
        // get all currencies
        return await opera.currencies({show_alternative: true, show_inactive: true});
    } catch (e) {
        throw e;
    }
}

async function getLatestEtag() {
    try {
        const etag = await Etag.find({endpoint: 'currencies'}).exec();
        console.log(etag);
        return etag;
    } catch (e) {
        
    }
}

//get_all_currencies();
function updateCurrencies() {

    // before updating the currencies collections, check if update is needed
    // compare the etag from the response and the latest etag of the currencies collections
    let latestEtag = getLatestEtag();
    const currencies = get_all_currencies();

    const currentLastModified = currencies.headers.etag['last_modified'];
    const currentEtag = currencies.headers.etag['etag'];

    if (latestEtag.lastModified === currentLastModified &&
        latestEtag.etag === currentEtag) {
            // do nothing
            return;
        }

    // update the currency collection and the
    // etag collection for 'endpoint == 'currencies'

}
