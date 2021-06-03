const express = require('express')
const multer = require('multer')
const tf = require('@tensorflow/tfjs')
const tfnode = require('@tensorflow/tfjs-node')
const {Storage} = require('@google-cloud/storage')
const request = require('request')
const Fruit = require('../models/fruit')
const auth = require('../middleware/auth')
const router = new express.Router()

const storage = new Storage()

const bucket = storage.bucket('b21-cap0291')

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
        // fruit.image = req.file.buffer
        // await fruit.save()
        // res.send(fruit)
        const blob = bucket.file(_id+'.png')
        const blobStream = blob.createWriteStream()

        blobStream.on('finish', async () => {
            const publicUrl = `https://storage.googleapis.com/b21-cap0291/${blob.name}`
            fruit.image = publicUrl
            await fruit.save()
        })

        const model = await tf.loadLayersModel('file://mlModel/model.json')
        var tensor = tfnode.node.decodeImage(req.file.buffer, 3)
        tensor = tf.image.resizeBilinear(tensor, [100,100])
        var tensor_4d = tf.tensor4d(tensor.dataSync(), [1,100,100,3])
        tensor_4d = tensor_4d.div(tf.scalar(255))

        
        const prediction = model.predict(tensor_4d)
    
        const arrPrediction = await prediction.dataSync()

        var indexMax = -1
        var valueMax = 0
        var index = 0
        
        arrPrediction.forEach((element) => {
            if (element>valueMax) {
                indexMax = index
                valueMax = element
            }
            index += 1
        })
    
        var result
        switch (indexMax) {
            case 0:
                result = "Apple Good"
                break
            case 1:
                result = "Apple Bad"
                break
            case 2:
                result = "Banana Bad"
                break
            case 3:
                result = "Banana Good"
                break
            case 4:
                result = "Orange Bad"
                break
            case 5:
                result = "Orange Good"
        }
        

        fruit.predictionResult = result
        await fruit.save()
        
        blobStream.end(req.file.buffer)

        res.status(200).send(fruit)

    } catch (e) {
        res.status(500).send(e)
    }
}, (error, req, res, next) => {
    res.status(400).send({ error: error.message})
})



router.get('/fruits', auth, async (req, res) => {
    try {
        await req.user.populate('fruits').execPopulate()
        res.send(req.user.fruits)
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