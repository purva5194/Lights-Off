var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var schema = new Schema({
    notification: {type: Boolean}
});

module.exports = mongoose.model('Notification', schema,'notification');
