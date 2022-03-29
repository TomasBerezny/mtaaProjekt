const express = require('express');
const Invite = require('../models/Invite');
const router = express.Router();

router.get('/', async(req, res) => {
    try{
        const invites = await Invite.find();
        res.json(invites);
    } catch (err){
        res.status(404).json({message: err});
    }
});

router.get('/:inviteId', async(req, res) => {
    try{
        const invite = await Invite.find({_id: req.params.inviteId});
        res.json(invite);
    } catch (err){
        res.status(404).json({message: err});
    }
});

router.get('/user/:userId', async(req, res) => {
    try{
        const invite = await Invite.find({user_id: req.params.userId});
        res.json(invite);
    } catch (err){
        res.status(404).json({message: err});
    }
});





router.post('/', async(req, res) => {
    const invite = new Invite({
        group_id: req.body.group_id,
        user_id: req.body.user_id,
        });
    try{
        const newInvite = await invite.save();
        res.json(newInvite);
    } catch (err){
        res.status(404).json({message: err});
    }
});

router.delete('/:inviteId', async(req, res) => {
    try{
        const invite = await Group.remove({_id: req.params.inviteId});
        res.json(invite);
    } catch (err){
        res.status(404).json({message: err});
    }
});

module.exports = router