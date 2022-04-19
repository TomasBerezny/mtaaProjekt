const express = require('express');
const Activity = require('../models/Activity');
const UserActivity = require('../models/UserActivity');
const User = require('../models/User');
const Category = require('../models/Category');
const router = express.Router();



router.get('/', async(req, res) => {
    try{
        const activities = await Activity.find();
        res.json(activities);
    } catch (err){
        res.status(404).json({message: err});
    }
});

router.get('/home', async(req, res) => {
    try{
        let response = [];
        const activities = await Activity.find();

        for (let i = 0; i < activities.length; i++) {
            let user = await User.findOne({_id: activities[i].user_id});
            let category = await Category.findOne({_id: activities[i].category_id});
            response.push({
                _id:activities[i]._id,
                profile_pic: user.profile_photo_path,
                username: user.username,
                category_name: category.name,
                description:activities[i].description,
            });
          }
        res.json(response);
    } catch (err){
        res.status(404).json({message: err});
    }
});

router.get('/:activityId', async(req, res) => {
    try{
        const activity = await Activity.find({_id: req.params.activityId});
        res.json(activity);
    } catch (err){
        res.status(404).json({message: err});
    }
});

router.post('/', async(req, res) => {
    const activity = new Activity({
        category_id: req.body.category_id,
        date: req.body.date,
        place: req.body.place,
        share_phone: req.body.share_phone,
        as_group: req.body.as_group,
        user_id: req.body.user_id,
        group_id: req.body.group_id,
        description: req.body.description
        });
    try{
        const newActivity = await activity.save();
        res.json(newActivity);
    } catch (err){
        res.status(400).json({message: err});
    }
});

router.delete('/:activityId', async(req, res) => {
    try{
        const activity = await Activity.remove({_id: req.params.activityId});
        res.json(activity);
    } catch (err){
        res.status(400).json({message: err});
    }
});

router.get('/user/:userId', async(req, res) => {
    try{
        const userActivities = await UserActivity.find({user_id:req.params.userId});
        let activites = userActivities.map(activity => activity.activity_id);
        const newActivities = await Activity.find({_id: {$in: activites}});
        res.json(newActivities);
    } catch (err) {
        res.status(400).json({message: err});
    }
});

router.get('/group/:groupId', async(req, res) => {
    try{
        let activities = await Activity.find({group_id:req.params.groupId})
        res.json(activities);
    } catch (err) {
        res.status(400).json({message: err});
    }
});

module.exports = router