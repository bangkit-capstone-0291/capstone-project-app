const express = require('express')
const tf = require('@tensorflow/tfjs')
const tfnode = require('@tensorflow/tfjs-node')
const multer = require('multer')

const router = new express.Router()

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


router.post('/image', upload.single("image"), async (req, res) => {
    const model = await tf.loadLayersModel('file://mlModel/fruit-classification/model.json')
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

    res.send({result : result})

}, (error, req, res, next) => {
    res.status(400).send({error: error.message})
})


module.exports = router