const mongoose = require('mongoose')

mongoose.connect('mongodb+srv://gustav:mcdonald@cluster0.rubm2.mongodb.net/myFirstDatabase?retryWrites=true&w=majority', {
    useNewUrlParser: true,
    useCreateIndex: true
})

console.log('Connected to database!')