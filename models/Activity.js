const mongoose = require('mongoose');

const ActivitySchema = mongoose.Schema({
    category_id: {
        type: String,
        required: true
    },
    date: {
        type: Date,
        required: true
    },
    Place: {
        type: String,
        required: true
    },
    share_phone: {
        type: Boolean,
        required: true
    },
    as_group: {
        type: Boolean,
        required: true
    },
    user_id: {
        type: String,
        required: true
    },
    group_id: {
        type: String,
        required: true
    },
    description:{
        type: String,
        require: true
    }
});

module.exports = mongoose.model('Activities', ActivitySchema)