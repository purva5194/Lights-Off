var express = require('express');
var router = express.Router();
var jwt = require('jsonwebtoken');

var User = require('../models/user');
var Form = require('../models/form');

router.get('/', function (req, res, next) {
    Form.find()
        .populate('user', 'firstName')
        .exec(function (err, formdetails) {
            if (err) {
                return res.status(500).json({
                    title: 'An error occurred',
                    error: err
                });
            }
            res.status(200).json({
                message: 'Success',
                obj: formdetails
            });
        });
});

router.use('/', function (req, res, next) {
    jwt.verify(req.body.token, 'thisisaveryhiglysecuremessage1234567890!@#$%^&*()', function (err, decoded) {
        if (err) {
            return res.status(401).json({
                title: 'Not Authenticated',
                error: err
            });
        }
        next();
    })
});

router.post('/', function (req, res, next) {
    var decoded = jwt.decode(req.body.token);
    User.findById(decoded.user._id, function (err, user) {

        if (user.form_id){
            return res.status(500).json({
                title: 'Form Already Exist, Please use patch/update form for any changes'
            });
        }
        if (err) {
            return res.status(500).json({
                title: 'An error occurred',
                error: err
            });
        }
        var form = new Form({
            age: req.body.age,
            address: req.body.address,
            city: req.body.city,
            state: req.body.state,
            zip: req.body.zip,
            phone: req.body.phone,
            user_id: user._id
        });
        form.save(function (err, result) {
            if (err) {
                return res.status(500).json({
                    title: 'An error occurred',
                    error: err
                });
            }
            user.form_id;
            user.save();
            res.status(201).json({
                message: 'Saved form details',
                obj: result
            });
        });
    });
});

router.patch('/:id', function (req, res, next) {
    var decoded = jwt.decode(req.body.token);
    Form.findById(req.params.id, function (err, form) {
        if (err) {
            return res.status(500).json({
                title: 'An error occurred',
                error: err
            });
        }
        if (!form) {
            return res.status(500).json({
                title: 'No form Found!',
                error: {message: 'form not found'}
            });
        }
        if (form.user_id != decoded.user._id) {
            return res.status(401).json({
                title: 'Not Authenticated',
                error: {message: 'Users do not match'}
            });
        }
        form.age = req.body.age;
        form.address = req.body.address;
        form.city = req.body.city;
        form.state = req.body.state;
        form.zip = req.body.zip;
        form.phone = req.body.phone;
        form.save(function (err, result) {
            if (err) {
                return res.status(500).json({
                    title: 'An error occurred',
                    error: err
                });
            }
            res.status(200).json({
                message: 'Updated form details',
                obj: result
            });
        });
    });
});

router.delete('/:id', function (req, res, next) {
    var decoded = jwt.decode(req.body.token);
    Form.findById(req.params.id, function (err, form) {
        if (err) {
            return res.status(500).json({
                title: 'An error occurred',
                error: err
            });
        }
        if (!form) {
            return res.status(500).json({
                title: 'No form Found!',
                error: {message: 'form not found'}
            });
        }
        if (form.user_id != decoded.user._id) {
            return res.status(401).json({
                title: 'Not Authenticated',
                error: {message: 'Users do not match'}
            });
        }
        form.remove(function (err, result) {
            if (err) {
                return res.status(500).json({
                    title: 'An error occurred',
                    error: err
                });
            }
            res.status(200).json({
                message: 'Deleted form details',
                obj: result
            });
        });
    });
});

module.exports = router;
