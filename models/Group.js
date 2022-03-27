const mongoose = require('mongoose');

const GroupSchema = mongoose.Schema({
    name: {
        type: String,
        required: true
    },
    profile_photo_path: {
        type: String,
        required: false
    },
    bio: {
        type: String,
        required: true
    }
});

module.exports = mongoose.model('Groups', GroupSchema)