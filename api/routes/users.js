const express = require('express');
const req = require('express/lib/request');
const router = express.Router();
const User = require('../models/User');
const UserGroup = require('../models/UserGroup');
const UserActivity = require('../models/UserActivity');
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
    const user = new User({
        username: req.body.username,
        password: req.body.password,
        first_name: req.body.first_name,
        last_name: req.body.last_name,
        //profile_photo_path: req.file.path,
        bio: req.body.bio,
        phone_number: req.body.phone_number
    });
    try{
        const savedUser = await user.save();
        res.json(savedUser);
    } catch (err) {
        res.status(400).json({message: err});
    }
});

router.patch('/:userId',upload.single('profilePicture'), async (req, res) => {
    try{
        let user = await User.findOne({_id:req.params.userId});
        if(req.body.username != null){
            user.username = req.body.username;
            console.log(user.username);
        }
        if(req.body.password != null){
            user.password = req.body.password;
            console.log("password")
        }
        if(req.body.first_name != null){
            user.first_name = req.body.first_name
            console.log("firstname")
        }
        if(req.body.last_name != null){
            user.last_name = req.body.last_name
            console.log("last")
        }
        if(req.file != null){
            user.profile_photo_path = req.file.path
            console.log("file")
        }
        if(req.body.bio != null){
            user.bio = req.body.bio
            console.log("bio")
        }
        if(req.body.phone_number != null){
            user.phone_number = req.body.phone_number
            console.log("phonenumber")
        }
        console.log(user)
        const updatedUser = await user.save();
        res.json(updatedUser);
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
});

router.get('/group/:groupId', async(req, res) => {
    try{
        const userGroups = await UserGroup.find({group_id:req.params.groupId});
        let users = userGroups.map(user => user.user_id);
        console.log(users)
        const newUsers = await User.find({_id: {$in: users}});
        res.json(newUsers);
    } catch (err) {
        res.status(400).json({message: err});
    }
});

router.get('/activity/:activityId', async(req, res) => {
    try{
        const userActivities = await UserActivity.find({activity_id:req.params.activityId});
        let users = userActivities.map(user => user.user_id);
        console.log(users)
        const newUsers = await User.find({_id: {$in: users}});
        res.json(newUsers);
    } catch (err) {
        res.status(400).json({message: err});
    }
});

router.get('/category/:categoryId', async(req, res) => {
    try{
        const userCategories = await UserCategory.find({category_id:req.params.categoryId});
        let users = userCategories.map(user => user.user_id);
        console.log(users)
        const newUsers = await User.find({_id: {$in: users}});
        res.json(newUsers);
    } catch (err) {
        res.status(400).json({message: err});
    }
});

module.exports = router