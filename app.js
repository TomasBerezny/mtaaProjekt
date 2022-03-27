const express = require('express');
const mongoose = require('mongoose');

const app = express();

mongoose.connect(
    'mongodb+srv://BerkoAndDanci:BerkoAndDanciBestPeople@cluster0.b3qn0.mongodb.net/test',
    () => console.log('connected to db')
    )


//routes
app.get('/', (req, res) => {
    res.send('danci smrdi');
});

app.get('/rysavec', (req, res) => {
    res.send('danci je rysavy');
});


app.listen(8000);