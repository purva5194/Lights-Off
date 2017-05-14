var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var schema = new Schema({
    temperature: {type: Number},
    brightness:{type: Number},
    motion:{type: Number},
    timestamp: {type: Date}
});

module.exports = mongoose.model('Sensor_data', schema,'sensor_data');
