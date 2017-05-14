var express = require('express');
var router = express.Router();

var Sensor_data = require('../models/sensor_data');

router.get('/', function (req, res, next) {
    Sensor_data.find().limit(100).sort({$natural:-1})
        .exec(function (err, data) {
            if (err) {
                return res.status(500).json({
                    title: 'An error occurred',
                    error: err
                });
            }
            res.status(200).json({
                message: 'Success',
                success: 1,
                obj: data
            });
        });
});

router.post('/', function (req, res, next) {
    var sensor_data = new Sensor_data({
        temperature : req.body.temperature,
    	brightness : req.body.brightness,
        motion: req.body.motion,
    	timestamp: req.body.timestamp
    });
    sensor_data.save(function (err, result) {
        if (err) {
            return res.status(500).json({
                title: 'An error occurred',
                error: err
            });
        }
        res.status(201).json({
            message: 'Saved message',
	    success: 1,
            obj: result
        });
    });
});


module.exports = router;
