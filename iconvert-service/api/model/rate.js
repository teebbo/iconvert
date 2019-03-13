const mongoose = require('mongoose'),
    Schema = mongoose.Schema;

let options = {collection: 'latestRates'};
/*let rateDef = {
    code: String,
    rate: Number,
    base: String,
    timestamp: Number,
    date: String
};*/

let def = {
    code: {
        type: String,
        required: true
    }, 
    value: {
        type: String,
        required: true
    }
};
const rateSchema = new Schema(def, options);
module.exports = mongoose.model('Rate', rateSchema);


