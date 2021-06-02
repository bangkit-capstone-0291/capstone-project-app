const express = require('express')
const multer = require('multer')
const Fruit = require('../models/fruit')
const auth = require('../middleware/auth')
const router = new express.Router()

router.post('/fruits', auth, async (req, res) => {
    // const image = new Image(req.body)
    const fruit = new Fruit({
        ...req.body,
        owner: req.user._id
    })

    try {
        await fruit.save()
        res.status(201).send(fruit)
    } catch(e) {
        res.status(400).send(e)
    }
})

const upload = multer({
    limits: {
        fileSize: 5000000
    },
    fileFilter(req, file, cb) {
        if (!file.originalname.match(/\.(jpg|jpeg|png)$/)) {
            return cb(new Error('Please upload an image'))
        }
        cb(undefined, true)
    }
})

router.post('/fruits/:id/image', auth, upload.single('image'), async (req, res) => {
    const _id = req.params.id

    try {
        const fruit = await Fruit.findOne({_id, owner: req.user._id})

        if (!fruit) {
            return res.status(404).send()
        }
        fruit.image = req.file.buffer
        await fruit.save()
        res.send(fruit)

    } catch (e) {
        res.status(500).send()
    }
}, (error, req, res, next) => {
    res.status(400).send({ error: error.message})
})

router.get('/fruits', auth, async (req, res) => {
    try {
        await req.user.populate('fruits').execPopulate()
        res.send()
    } catch (e) {
        res.status(500).send()
    }
})

router.get('/fruits/:id', auth, async (req, res) => {
    const _id = req.params.id

    try {
        const fruit = await Fruit.findOne({_id, owner: req.user._id})

        if (!fruit) {
            return res.status(404).send()
        }
        res.send(fruit)

    } catch (e) {
        res.status(500).send()
    }
})

router.get('/fruits/:id/image', auth, async (req, res) => {
    try {
        const fruit = await Fruit.findById(req.params.id)
        if (!fruit || !fruit.image) {
            throw new Error()
        }

        res.set('Content-Type', 'image/png')
        res.send(fruit.image)
    } catch (e) {
        res.send(404).send()
    }
})

router.patch('/fruits/:id', auth, async (req, res) => {
    const updates = Object.keys(req.body)
    const allowedUpdates = ['name', 'category']
    const isValidOperation = updates.every((update) => allowedUpdates.includes(update))

    if (!isValidOperation) {
        return res.status(400).send({error: "Invalid Updates!"})
    }

    try {
        const fruit = await Fruit.findOne({_id: req.params.id, owner:req.user._id})
        if (!fruit) {
            return res.status(404).send()
        }
        updates.forEach((update) => fruit[update]=req.body[update])
        await fruit.save()
        res.send(fruit)
    } catch (e) {
        res.status(400).send(e)
    }
})

router.delete('/fruits/:id', auth, async (req, res) => {
    try {
        const fruit = await Fruit.findOneAndDelete({ _id: req.params.id, owner: req.user._id})

        if (!fruit) {
            res.status(404).send()
        }

        res.send(fruit)
    } catch (e) {
        res.status(500).send(e)
    }
})

module.exports = router