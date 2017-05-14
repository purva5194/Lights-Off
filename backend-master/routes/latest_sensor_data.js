var express = require('express');
var router = express.Router();
var Sensor_data = require('../models/sensor_data');
var jwt = require('jsonwebtoken');

router.get('/', function (req, res, next) {
    Sensor_data.find().limit(1).sort({$natural:-1})
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
module.exports = router;