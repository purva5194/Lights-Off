var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var schema = new Schema({
    led: {type: Boolean}
});

module.exports = mongoose.model('Led_data', schema,'led_data');

