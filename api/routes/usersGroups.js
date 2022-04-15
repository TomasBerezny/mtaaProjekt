const express = require('express');
const UserGroup = require('../models/UserGroup');
const router = express.Router();

router.get('/', async(req, res) => {
    try{
        const userActivities = await UserActivity.find();
        res.json(userActivities);
    } catch (err){
        res.status(404).json({message: err});
    }
});


router.post('/', async(req, res) => {
    const userGroup = new UserGroup({
        user_id: req.body.user_id,
        group_id: req.body.group_id,
        });
    try{
        const newUserGroup = await userGroup.save();
        res.json(newUserGroup);
    } catch (err){
        res.status(404).json({message: err});
    }
});

router.delete('/:Id', async(req, res) => {
    try{
        const userGroup = await UserGroup.remove({_id: req.params.Id});
        res.json(userGroup);
    } catch (err){
        res.status(404).json({message: err});
    }
});

router.get('/user/:userId', async(req, res) => {
    try{
        const userGroups = await UserGroup.find({user_id: req.params.userId});
        res.json(userGroups);
    } catch (err){
        res.status(404).json({message: err});
    }
});

router.get('/activity/:activityId', async(req, res) => {
    try{
        const userGroups = await UserActivity.find({activity_id: req.params.activityId});
        res.json(userActivites);
    } catch (err){
        res.status(404).json({message: err});
    }
});



module.exports = router