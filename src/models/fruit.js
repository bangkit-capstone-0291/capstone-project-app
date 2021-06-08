const mongoose = require('mongoose')

const fruitSchema = new mongoose.Schema({
    name: {
        type: String,
        required: true,
        trim: true
    },
    category: {
        type: String,
        required: true,
        trim: true
    },
    description : {
        type: String
    },
    owner: {
        type: mongoose.Schema.Types.ObjectId,
        required: true,
        ref: 'User'
    },
    image: {
        type: String
    },
    fruitClassificationPrediction : {
        type: String
    },
    agePrediction : {
        type: Number
    }

}, {
    timestamps : true
})

const Fruit = mongoose.model('Fruit', fruitSchema)

module.exports = Fruit