const express = require('express')
const multer = require('multer')
const tf = require('@tensorflow/tfjs')
const tfnode = require('@tensorflow/tfjs-node')
const {Storage} = require('@google-cloud/storage')
const https = require('https')
const Stream = require('stream').Transform
const Fruit = require('../models/fruit')
const auth = require('../middleware/auth')
const router = new express.Router()

const storage = new Storage()

const bucket = storage.bucket('b21-cap0291')

router.post('/food', auth, async (req, res) => {
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

router.post('/food/:id/image', auth, upload.single('image'), async (req, res) => {
    const _id = req.params.id

    try {
        const fruit = await Fruit.findOne({_id, owner: req.user._id})

        if (!fruit) {
            return res.status(404).send()
        }
        const blob = bucket.file(_id+'.png')
        const blobStream = blob.createWriteStream()

        let publicUrl = `https://storage.googleapis.com/b21-cap0291/${blob.name}`

        blobStream.on('finish', async () => {
            fruit.image = publicUrl
            fruit.save()
            res.status(200).send({image: publicUrl})
        })

        blobStream.end(req.file.buffer);

    } catch (e) {
        res.status(500).send(e)
    }
}, (error, req, res, next) => {
    res.status(400).send({ error: error.message})
})



router.get('/food', auth, async (req, res) => {
    try {
        await req.user.populate('fruits').execPopulate()
        res.send(req.user.fruits)
    } catch (e) {
        res.status(500).send()
    }
})

router.get('/food/category/:category', auth, async (req, res) => {
    const category = req.params.category
    const allowedCategories = ['fruits', 'vegetables', 'others']
    
    if (!allowedCategories.includes(category)) {
        res.status(400).send({error: "Category not valid"})
    }    

    try {
        const fruits = await Fruit.find({category})
        res.send(fruits)
    } catch (e) {
        res.status(500).send()
    }
})

router.get('/food/:id', auth, async (req, res) => {
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

router.get('/food/:id/image', auth, async (req, res) => {
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

router.patch('/food/:id', auth, async (req, res) => {
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

router.delete('/food/:id', auth, async (req, res) => {
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

router.get('/food/:id/fruit-classification', auth, async (req, res) => {
    const _id = req.params.id

    try {
        const fruit = await Fruit.findOne({_id, owner: req.user._id})

        if (!fruit) {
            return res.status(404).send()
        }
        if ( fruit.category !== "fruits") {
            return res.status(400).send({"message" : "Food is not fruits"})
        }
        if (!fruit.image) {
            return res.status(400).send({"message" : "Food does not have image"})
        }

        https.get(fruit.image, async (res_) => {
            let data = new Stream();
            
            res_.on("data", async (chunk) => data.push(chunk) )

            res_.on('end', async () => {
                const model = await tf.loadLayersModel('file://mlModel/fruit-classification/model.json')
                var tensor = tfnode.node.decodeImage(data.read(), 3)
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
                
            
                fruit.fruitClassificationPrediction = result
                await fruit.save()

                res.status(200).send({result: result})
            })


        }) 

    } catch (e) {
        res.status(500).send()
    }
})

router.get('/food/:id/orange-prediction', auth, async (req, res) => {
    const _id = req.params.id

    try {
        const fruit = await Fruit.findOne({_id, owner: req.user._id})

        if (!fruit) {
            return res.status(404).send()
        }
        if ( fruit.name !== "orange") {
            return res.status(400).send({"message" : "Food is not orange"})
        }
        if (!fruit.image) {
            return res.status(400).send({"message" : "Food does not have image"})
        }


        https.get(fruit.image, async (res_) => {
            let data = new Stream();
            
            res_.on("data", async (chunk) => data.push(chunk) )

            res_.on('end', async () => {
                const model = await tf.loadLayersModel('file://mlModel/orange-prediction/model.json')
                var tensor = tfnode.node.decodeImage(data.read(), 3)
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
                        result = 1
                        break
                    case 1:
                        result = 12
                        break
                    case 2:
                        result = 3
                        break
                    case 3:
                        result = 7
                        break
                }
                fruit.agePrediction = result
                await fruit.save()

                res.status(200).send({result: result})
            })


        })        

    } catch (e) {
        res.status(500).send()
    }
})

router.get('/food/:id/banana-prediction', auth, async (req, res) => {
    const _id = req.params.id

    try {
        const fruit = await Fruit.findOne({_id, owner: req.user._id})

        if (!fruit) {
            return res.status(404).send()
        }
        if ( fruit.name !== "banana") {
            return res.status(400).send({"message" : "Food is not banana"})
        }
        if (!fruit.image) {
            return res.status(400).send({"message" : "Food does not have image"})
        }


        https.get(fruit.image, async (res_) => {
            let data = new Stream();
            
            res_.on("data", async (chunk) => data.push(chunk) )

            res_.on('end', async () => {
                const model = await tf.loadLayersModel('file://mlModel/banana-prediction/model.json')
                var tensor = tfnode.node.decodeImage(data.read(), 3)
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
                        result = 4
                        break
                    case 1:
                        result = 6
                        break
                    case 2:
                        result = 10
                        break
                    case 3:
                        result = 2
                        break
                    case 4:
                        result = 8
                        break
                }
                fruit.agePrediction = result
                await fruit.save()

                res.status(200).send({result: result})
            })


        })        

    } catch (e) {
        res.status(500).send()
    }
})

module.exports = router