const LatestRate = require('../model/latest-rate.model');

const service = {};


module.exports = service;

function getRate(currencyCode) {
    return new Promise( (resolve, reject) => {
        LatestRate
            .find({'rates.code': currencyCode})
            .exec()
            .then( latestRate => resolve(latestRate) )
            .catch( err => reject(err) );

    });
}

function computeRealRate(rateSrc, rateDst) {
    return rateDst/rateSrc;
}

service.convert = (query) => {
    let rateValue = [];
    let src = 0, dst = 0;
    Promise.all([getRate(query.source), getRate(query.target)])
        .then( rates => {
           return rates.map( (rate, index) => rate.rates );
        })
        .then(rates => rateValue = rate)
        .catch (err => { throw err });

    for( let idx in rateValue) {
        let val = rateValue[idx];
        for(prop in val) {
            if (prop === val[query.source]) {
                src = val[prop];
            }
            if (prop === val[query.target]) {
                dst = val[prop];
            }
        }
    }

    let realRate = computeRealRate(src, dst);
};