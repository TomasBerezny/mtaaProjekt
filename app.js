const express = require('express');

const app = express();


//routes
app.get('/', (req, res) => {
    res.send('danci smrdi');
});

app.get('/rysavec', (req, res) => {
    res.send('danci je rysavy');
});


app.listen(8000);