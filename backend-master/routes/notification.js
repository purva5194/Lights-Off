var express = require('express');
var router = express.Router();
var Notification = require('../models/notification');


router.get('/', function (req, res, next) {
    Notification.findOne()
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


router.patch('/:id', function (req, res, next) {
    Notification.findById(req.params.id, function (err, data) {
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
        data.notification = req.body.notification;
        data.save(function (err, result) {
            if (err) {
                return res.status(500).json({
                    title: 'An error occurred',
                    error: err
                });
            }
            res.status(200).json({
                message: 'Updated notification details',
                success:1,
                obj: result
            });
        });
    });
});

module.exports = router;

