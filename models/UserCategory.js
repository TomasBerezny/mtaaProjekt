const mongoose = require('mongoose');

const UserCategorySchema = mongoose.Schema({
   user_id: {
       type: String,
       required: true
   },
   category_id: {
       type: String,
       required: true
   }
});

module.exports = mongoose.model('UsersCategories', UserCategorySchema)