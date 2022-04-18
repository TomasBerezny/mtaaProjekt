const express = require('express');
const router = express.Router();
const User = require('../models/User');


router.post('/login', async (req, res) => {
    const loginParams = {
        username: req.body.username,
        password: req.body.password,
    };
    try{
        const savedUser = await User.findOne({
            username: loginParams.username
        });
        if(savedUser.password == loginParams.password){
            res.status(200).json(savedUser);
        }
        else{
            res.status(404).json({"message": "Could not authenticate user"});
        }
    } catch (err) {
        res.status(400).json({message: err});
    }
});

router.post('/register', async (req, res) => {
    const user = new User({
        username: req.body.username,
        password: req.body.password,
        first_name: req.body.first_name,
        last_name: req.body.last_name
    });
    try{
        const userAlready = await User.findOne({username: user.username})
        if(userAlready == null){
            const savedUser = await user.save();
            res.status(201).json(savedUser);
        }
        else{
            res.status(406).json({"message": "Chosen username is already in use"});
        }
    } catch (err) {
        res.status(500).json({message: err});
    }
});

module.exports = router