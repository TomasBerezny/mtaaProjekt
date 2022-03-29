const mongoose = require('mongoose');

const UserActivitySchema = mongoose.Schema({
   user_id: {
       type: String,
       required: true
   },
   activity_id: {
       type: String,
       required: true
   }
});

module.exports = mongoose.model('UsersActivities', UserActivitySchema)