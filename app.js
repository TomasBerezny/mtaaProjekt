const express = require('express');
const mongoose = require('mongoose');
const bodyParseer = require('body-parser');
const swaggerJsDoc = require('swagger-jsdoc');
const swaggerUi = require('swagger-ui-express');
const YAML = require('yamljs');

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
const categoryRoute = require('./routes/categories');
const authRoute = require('./routes/auth');

app.use('/users', usersRoute);
app.use('/activities', activitiesRoute);
app.use('/groups', groupsRoute);
app.use('/invites', invitesRoute);
app.use('/categories', categoryRoute);
app.use('/auth', authRoute);


// const swaggerOptions = {
//     swaggerDefinition: {
//         info: {
//             title: "Kde si kamos Api",
//             description: "Kde si kamos dokumentacia nasej api",
//             servers: ["http://localhost:8000"]
//         }
//     },
//     apis: [
//         "./routes/*.js"
//     ]
// };

const swaggerDocs = YAML.load("./api.yaml");
app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(swaggerDocs));

//routes

app.get('/', (req, res) => {
    res.send('danci smrdi');
});

app.get('/rysavec', (req, res) => {
    res.send('danci je rysavy');
});


app.listen(8000);