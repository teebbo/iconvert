'use strict';

const Opera = require('@aleengo/opera');
const mongoose = require('mongoose');

const Currency = require('./model/currency');
const Rate = require('./model/rate');
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

async function getAllCurrencies() {
    try {
        // get all currencies
        let res =  await opera.currencies({
            show_alternative: true, 
            show_inactive: true
        });
        
        let json = JSON.parse(res.body);
        let currencies = [];
        for ( key in json) {
            currencies.push(new Currency(key, json[key]));
        }
        // insert elements to MongoDB database
        return await Currency.create(currencies);
    } catch (e) {
        throw e;
    }
}

async function saveEtag(headers, endpoint) {
    const { etag } = headers;
    const doc = new Etag({
        lastModified: etag['last-modified'],
        etag: etag['etag'],
        endpoint,
        timestamp: etag['timestamp']
    });

    const query = {
        lastModified: etag['last-modified'],
        etag: etag['etag'],
        timestamp: etag['timestamp']
    };

    Etag.findOneAndUpdate(query, doc, {upsert: true}, (err, doc, res) => {
        if (err) throw err;
        console.log('doc:', doc, 'save with success!!!');
    });
}

async function saveRates() {
    try {
        const res = await opera.latest({show_alternative: true})
        const json = JSON.parse(res.body);

        const rates = [];
        for (key in json.rates) {
            rates.push(new rates(key, json.rates[key]));
        }

        saveEtag(JSON.parse(res.headers), 'rates');

        return await Rate.create(rates);
    } catch (e) {
        throw e;
    }
}

async function getLatestEtag() {
    try {
        const etag = await Etag.find({endpoint: 'latest'}).exec();
        console.log(etag);
        return etag;
    } catch (e) {
        throw e;
    }
}

function updateCurrencies() {

    // before updating the currencies collections, check if update is needed
    // compare the etag from the response and the latest etag of the currencies collections
    let latestEtag = getLatestEtag();
    const currencies = getAllCurrencies();

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
