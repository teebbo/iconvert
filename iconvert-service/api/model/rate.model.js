const mongoose = require('mongoose'),
    Schema = mongoose.Schema;

let options = {collection: 'latestRates'};
let rateDef = {
    code: String,
    rate: Number,
    base: String,
    timestamp: Number,
    date: String
};
const rateSchema = new Schema(rateDef, options);
module.exports = mongoose.model('Rate', rateSchema);


