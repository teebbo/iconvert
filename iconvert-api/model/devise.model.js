const mongoose = require('mongoose')
        Schema = mongoose.Schema;

let options = {
    collection: 'devises'
};

let definition = {
    code: {
        type: String,
        required: true
    }, 
    libelle: {
        type: String,
        required: true
    }
};


const deviseSchema = new Schema(definition, options);
module.exports = mongoose.model('Devise', deviseSchema);