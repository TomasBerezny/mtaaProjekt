const express = require('express');
const UserCategory = require('../models/UserCategory');
const router = express.Router();

router.get('/', async(req, res) => {
    try{
        const userCategories = await UserCategory.find();
        res.json(userCategories);
    } catch (err){
        res.status(404).json({message: err});
    }
});


router.post('/', async(req, res) => {
    const userCategory = new UserCategory({
        user_id: req.body.user_id,
        category_id: req.body.category_id,
        });
    try{
        const newUserCategory = await userCategory.save();
        res.json(newUserCategory);
    } catch (err){
        res.status(404).json({message: err});
    }
});

router.delete('/:Id', async(req, res) => {
    try{
        const userCategory = await UserCategory.remove({_id: req.params.Id});
        res.json(userCategory);
    } catch (err){
        res.status(404).json({message: err});
    }
});

module.exports = router