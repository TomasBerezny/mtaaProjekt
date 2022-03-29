const express = require('express');
const req = require('express/lib/request');
const router = express.Router();
const User = require('../models/User');
const UserCategory = require('../models/UserCategory');
const multer = require('multer');


const storage = multer.diskStorage({
    destination: function(req, file, cb){
        cb(null, './uploads/')
    },
    filename: function(req, file, cb){
        cb(null, file.originalname);
    }
});

const upload = multer({storage: storage});


router.get('/', async(req,res) => {
    try{
        const users = await User.find()
        res.json(users)
    } catch (err){
        res.status(404).json({message: err})
    }
});

router.get('/:userId', async (req, res) => {
    try{
        const user = await User.find({_id:req.params.userId});
        res.json(user)
    } catch (err) {
        res.status(404).json({message: err})
    }
})


router.post('/',upload.single('profilePicture'), async (req, res) => {
    console.log(req.file);
    const user = new User({
        username: req.body.username,
        password: req.body.password,
        first_name: req.body.first_name,
        last_name: req.body.last_name,
        profile_photo_path: req.file.path,
        bio: req.body.bio,
        phone_number: req.body.phone_number
    });
    try{
        const savedUser = await user.save();
        const userCategory = new UserCategory({
            user_id:savedUser.id,
            category_id:'6241e99a8fbc484eeb864b0d'
        })
        await userCategory.save();
        res.json(savedUser);
    } catch (err) {
        res.status(400).json({message: err});
    }
});

router.delete('/:userId', async (req, res) => {
    try{
        const removeUser = await User.remove({_id:req.params.userId});
        res.json(removeUser);
    } catch (err) {
        res.status(400).json({message: err})
    }
})



module.exports = router