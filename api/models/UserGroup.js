const mongoose = require('mongoose');

const UserGroupSchema = mongoose.Schema({
   user_id: {
       type: String,
       required: true
   },
   group_id: {
       type: String,
       required: true
   }
});

module.exports = mongoose.model('UsersGroups', UserGroupSchema)