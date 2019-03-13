const mongoose = require('mongoose'),
    Schema = mongoose.Schema;

let opts = {
    collection: 'etags'
};

let def = {
    lastModified: String,
    etag: String,
    endpoint: String,
    timestamp: Number
};

let ETagSchema = new Schema(def, opts);
module.exports = mongoose.model('ETag', ETagSchema);

