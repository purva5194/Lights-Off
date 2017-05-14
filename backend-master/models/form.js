var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var User = require('./user');

var schema = new Schema({
    age: {type: String, required: true},
    address: {type: String, required: true},
    city: {type: String, required: true},
    state: {type: String, required: true},
    zip: {type: Number, required: true},
    phone: {type: String, required: true},
    user_id: {type: Schema.Types.ObjectId, ref: 'User_id'}
});

schema.post('remove', function (form) {
    User.findById(form.user_id, function (err, user) {
        user.form_id.pull(form);
        user.save();
    });
});

module.exports = mongoose.model('Form', schema,'forms');