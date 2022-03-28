const express = require('express');
const Activity = require('../models/Activity');
const router = express.Router();

router.get('/', async(req, res) => {
    try{
        const activities = await Activity.find();
        res.json(activities);
    } catch (err){
        res.json({message: err});
    }
});

router.get('/:activityId', async(req, res) => {
    try{
        const activity = await Activity.find({_id: req.params.activityId});
        res.json(activity);
    } catch (err){
        res.json({message: err});
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
        res.json({message: err});
    }
});

router.delete('/:activityId', async(req, res) => {
    try{
        const activity = await Activity.remove({_id: req.params.activityId});
        res.json(activity);
    } catch (err){
        res.json({message: err});
    }
});

module.exports = router