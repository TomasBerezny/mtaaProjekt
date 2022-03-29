const express = require('express');
const Group = require('../models/Group');
const UserGroup = require('../models/UserGroup');
const router = express.Router();
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

router.patch('/:groupId',upload.single('profilePicture'), async (req, res) => {
    try{
        let group = await Group.findOne({_id:req.params.groupId});
        if(req.body.name != null){
            group.name = req.body.name;
        }
        if(req.file != null){
            group.profile_photo_path = req.file.path;
        }
        if(req.body.bio != null){
            group.bio = req.body.bio;
        }
        const updatedGroup = await group.save();
        res.json(updatedGroup);
    } catch{
        res.status(400).json({message: err});
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
    try{
        const userGroups = await UserGroup.find({user_id:req.params.userId});
        let groups = userGroups.map(group => group.group_id);
        console.log(groups)
        const newGroups = await Group.find({_id: {$in: groups}});
        res.json(newGroups);
    } catch (err) {
        res.status(400).json({message: err});
    }
});

module.exports = router