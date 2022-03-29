const express = require('express');
const Group = require('../models/Group');
const UserGroup = require('../models/UserGroup');
const router = express.Router();

router.get('/', async(req, res) => {
    try{
        const groups = await Group.find();
        res.json(groups);
    } catch (err){
        res.status(404).json({message: err});
    }
});

router.get('/:groupId', async(req, res) => {
    try{
        const group = await Group.find({_id: req.params.groupId});
        res.json(group);
    } catch (err){
        res.status(404).json({message: err});
    }
});

router.post('/', async(req, res) => {
    const group = new Group({
        name: req.body.name,
        profile_photo_path: req.body.profile_photo_path,
        bio: req.body.bio
        });
    try{
        const newGroup = await group.save();
        const userGroup = new UserGroup({
            user_id:req.body.user_id,
            group_id:newGroup._id
        })
        await userGroup.save();
        res.json(newGroup);
    } catch (err){
        res.status(404).json({message: err});
    }
});

router.delete('/:groupId', async(req, res) => {
    try{
        const group = await Group.remove({_id: req.params.groupId});
        res.status(404).json(group);
    } catch (err){
        res.json({message: err});
    }
});

router.get('/user/:userId', async(req, res) => {
    let groups = [];
    try{
        const userGroups = await UserGroup.find({user_id:req.params.userId});
        userGroups.forEach(async function(usrGrp) {
            const group = await Group.findOne({_id:usrGrp.group_id})
            groups.push(group)
        res.json(groups);
        });
    } catch (err) {
        res.status(400).json({message: err});
    }
})

module.exports = router