var express = require('express');
var router = express.Router();
var Led_data = require('../models/led_data');
var jwt = require('jsonwebtoken');


router.get('/', function (req, res, next) {
    Led_data.findOne()
        .exec(function (err, data) {
            if (err) {
                return res.status(500).json({
                    title: 'An error occurred',
                    error: err
                });
            }
            res.status(200).json(data);
        });
});


router.put('/:id', function (req, res, next) {
    var decoded = jwt.decode(req.body.token);
    Led_data.findById(req.params.id, function (err, data) {
        if (err) {
            return res.status(500).json({
                title: 'An error occurred',
                error: err
            });
        }
        if (!data) {
            return res.status(500).json({
                title: 'No data Found!',
                error: {message: 'data not found'}
            });
        }
        data.led = req.body.led;
        data.save(function (err, result) {
            if (err) {
                return res.status(500).json({
                    title: 'An error occurred',
                    error: err
                });
            }
            res.status(200).json({
                message: 'Updated led details',
                success:1,
                obj: result
            });
        });
    });
});

module.exports = router;

