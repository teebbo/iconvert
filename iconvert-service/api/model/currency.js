const mongoose = require('mongoose')
        Schema = mongoose.Schema;

let options = {
    collection: 'currencies'
};

const currencySchema = new Schema({
    code: {
        type: String,
        required: true
    }, 
    liblong: {
        type: String,
        required: true
    }
}, options);

module.exports = mongoose.model('Currency', currencySchema);