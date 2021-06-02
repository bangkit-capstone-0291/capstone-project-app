const tf = require('@tensorflow/tfjs')
const tfnode = require('@tensorflow/tfjs-node')
const fs = require('fs')

tf.loadLayersModel('file://D:/bangkit/Tubes/mlModel/model.json').then((model) => {
    fs.readFile('./src/images/apple.png', (err, data) => {
        if (err) {
            return console.log('Error', err)
        }
        var tensor = tfnode.node.decodeImage(data,3)
        tensor.print()

        tensor = tf.image.resizeBilinear(tensor, [100,100])
        var tensor_4d = tf.tensor4d(tensor.dataSync(), [1,100,100,3])

        tensor_4d = tensor_4d.div(tf.scalar(255))
        // tensor_4d.print()
        // console.log(tensor_4d)
        const prediction = model.predict(tensor_4d)
        
        prediction.print()
        
    })
})


// fs.readFile('./src/images/banana.png', (err, data) => {
//     if (err) {
//         return console.log('Error', err)
//     }
//     const tensor = tfnode.node.decodeImage(data)
//     const predictions = model.predict()
//     console.log(predictions)
// })