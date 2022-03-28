const express = require('express');
const mongoose = require('mongoose');
const bodyParseer = require('body-parser');

const app = express();
app.use(bodyParseer.json());

mongoose.connect(
    'mongodb+srv://BerkoAndDanci:BerkoAndDanciBestPeople@cluster0.b3qn0.mongodb.net/test',
    () => console.log('connected to db')
    )

//Routes
const usersRoute = require('./routes/users');
const groupsRoute = require('./routes/groups');
const activitiesRoute = require('./routes/activities');
const invitesRoute = require('./routes/invites');
app.use('/users', usersRoute);
app.use('/activities', activitiesRoute);
app.use('/groups', groupsRoute);
app.use('/invites', invitesRoute);

//routes

app.get('/', (req, res) => {
    res.send('danci smrdi');
});

app.get('/rysavec', (req, res) => {
    res.send('danci je rysavy');
});


app.listen(8000);