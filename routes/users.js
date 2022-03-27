const express = require('express');
const router = express.Router();
const User = require('../models/User');


router.get('/', async(req,res) => {
    try{
        const users = await User.find()
        res.json(users)
    } catch (err){
        res.json({message: err})
    }
});


router.post('/', async (req, res) => {
    const user = new User({
        username: req.body.username,
        password: req.body.password,
        first_name: req.body.first_name,
        last_name: req.body.last_name,
        profile_photo_path: req.body.profile_photo_path,
        bio: req.body.bio,
        phone_number: req.body.phone_number
    });
    try{
        const savedUser = await user.save();
        res.json(savedUser);
    } catch (err) {
        res.json({message: err});
    }
});

router.delete('/:userId', async (req, res) => {
    try{
        const removeUser = await User.remove({_id:req.params.userId});
        res.json(removeUser);
    } catch (err) {
        res.json({message: err})
    }
})

module.exports = router