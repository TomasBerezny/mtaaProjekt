const mongoose = require('mongoose');

const UserSchema = mongoose.Schema({
    username: {
        type: String,
        required: true
    },
    password: {
        type: String,
        required: true
    },
    first_name: {
        type: String,
        required: true
    },
    last_name: {
        type: String,
        required: true
    },
    profile_photo_path: {
        type: String,
        required: false
    },
    bio: {
        type: String,
        required: false
    },
    phone_number: {
        type: String,
        required: false
    }
});

module.exports = mongoose.model('Users', UserSchema)