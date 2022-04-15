const mongoose = require('mongoose');

const InviteSchema = mongoose.Schema({
    group_id: {
        type: String,
        required: true
    },
    user_id: {
        type: String,
        required: true
    }
});

module.exports = mongoose.model('Invites', InviteSchema)