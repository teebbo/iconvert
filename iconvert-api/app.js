const express = require('express'),
        bodyParser = require('body-parser'),
        logger = require('morgan'),
        mongoose = require('mongoose');

const apiConvert = require('./api-convert');
const config = require('./config/settings.config');

const app = express();

mongoose.connect(config.db.url)
    .then( () => console.log('Successfully connected to db'))
    .catch (err => { throw err });

//////////// MIDDLEWARES ////////////////

app.use(logger('dev'));

app.use(bodyParser.urlencoded({extended: true}));
app.use(bodyParser.json());

/////// MIDDLEWARES /////////////////

app.use('/api/convert', apiConvert);

app.get('/', (req, res) => {
    res.send('Welcome to iconvert API');
});




module.exports = app;