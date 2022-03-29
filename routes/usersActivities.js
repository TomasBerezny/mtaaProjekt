const express = require('express');
const UserActivity = require('../models/UserActivity');
const router = express.Router();

router.get('/', async(req, res) => {
    try{
        const userActivities = await UserActivity.find();
        res.json(userActivities);
    } catch (err){
        res.status(404).json({message: err});
    }
});

router.get('/user/:userId', async(req, res) => {
    try{
        const userActivities = await UserActivity.find({user_id: req.params.userId});
        res.json(userActivities);
    } catch (err){
        res.status(404).json({message: err});
    }
});

router.get('/activity/:activityId', async(req, res) => {
    try{
        const userActivites = await UserActivity.find({activity_id: req.params.activityId});
        res.json(userActivites);
    } catch (err){
        res.status(404).json({message: err});
    }
});

router.post('/', async(req, res) => {
    const userActivity = new UserActivity({
        user_id: req.body.user_id,
        activity_id: req.body.activity_id,
        });
    try{
        const newUserActivity = await userActivity.save();
        res.json(newUserActivity);
    } catch (err){
        res.status(404).json({message: err});
    }
});

router.delete('/:Id', async(req, res) => {
    try{
        const userActivity = await UserActivity.remove({_id: req.params.Id});
        res.json(userActivity);
    } catch (err){
        res.status(404).json({message: err});
    }
});

module.exports = router