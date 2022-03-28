const express = require('express');
const Category = require('../models/Category');
const Invite = require('../models/Invite');
const router = express.Router();

router.get('/', async(req, res) => {
    try{
        const categories = await Category.find();
        res.json(categories);
    } catch (err){
        res.status(404).json({message: err});
    }
});

router.get('/:categoryId', async(req, res) => {
    try{
        const category = await Category.find({_id: req.params.categoryId});
        res.json(category);
    } catch (err){
        res.status(404).json({message: err});
    }
});

router.post('/', async(req, res) => {
    const category = new Category({
        name: req.body.name
        });
    try{
        const newCategory = await category.save();
        res.json(newCategory);
    } catch (err){
        res.status(404).json({message: err});
    }
});

router.delete('/:categoryId', async(req, res) => {
    try{
        const category = await Category.remove({_id: req.params.categoryId});
        res.status(404).json(category);
    } catch (err){
        res.json({message: err});
    }
});

module.exports = router