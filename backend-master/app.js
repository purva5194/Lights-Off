var express = require('express');
var mongoose = require('mongoose');
var bodyParser = require('body-parser');

var userRoutes = require('./routes/user');
var formRoutes = require('./routes/form');
var sensor_dataRoutes = require('./routes/sensor_data');
var led_dataRoutes = require('./routes/led_data');
var notification = require('./routes/notification');
var latest_sensor_data = require('./routes/latest_sensor_data');


var app = express();
mongoose.connect('');

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: false}));

app.use('/users', userRoutes);
app.use('/form', formRoutes);
app.use('/sensor_data', sensor_dataRoutes);
app.use('/latest_sensor_data', latest_sensor_data);
app.use('/led_data', led_dataRoutes);
app.use('/notification', notification);


module.exports = app;
