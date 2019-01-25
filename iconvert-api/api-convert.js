const express = require('express'),
        Router = express.Router(); // instance of express.Router

const service = require('./services/iconvert.service');

Router.use((req, res, next) => {
        console.log('Time', Date.now());
        next();
});

Router.get('/:amount/:from/:to', (req, res) => {
        let amount = req.params['amount'],
            source = req.params['from'],
            target = req.params['to'];

        return '';
        /*return res.json({
                success: true,
                query: {
                        amount: amount,
                        from: source,
                        to: target
                },
                meta: {
                        timestamp: 0,
                        rate: 0,
                },
                value: 0
            });*/
});


module.exports = Router;