const mongoose = require('mongoose'),
        Schema = mongoose.Schema;

const rateSchema = new Schema({
    code: {
        type: String,
        required: true
    },
    rate: {
        type: Number,
        required: true
    }
});

let options = {
    collection: 'latestRates'
};

let latestRateDefinition = {
    timestamp: {
        type: Number,
        required: true
    },
    base: {
        type: String,
        required: true
    },
    rates: {
        type: rateSchema,
        required: true
    }
};

const latestRateSchema = new Schema(latestRateDefinition, options);

module.exports = mongoose.model('LatestRate', latestRateSchema)






