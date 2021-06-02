const tf = require('@tensorflow/tfjs')
const tfnode = require('@tensorflow/tfjs-node')
const fs = require('fs')

// const input = [
//     -91,-85,-94,-76,-79,-84,-86,-76,-100,-61,
//     -75,-60,-86,-80,-90,-97,-77,-75,-88,-88,
//     -71,-92,-87,-81,-96,-70,-75,-65,-70,-61,
//     -67,-68,-70,-70,-98,-62,-76,-91,-84,-81,
//     -65,-89,-71,-62,-85,-74,-75,-78,-87,-60,
//     -61,-89,-99,-77,-88,-64,-69,-94,-61,-75
//     // -91,-66,-76,-74,-60,-63,-83,-76,-69,-61
// ]

// const input_3d = tf.tensor3d(input, [1,10,6]);

fs.readFile('./src/images/banana.png', (err, data) => {
    if (err) {
        return console.log('Error', err)
    }
    const tensor = tfnode.node.decodeImage(data)
    tensor.print()
    //console.log(tensor)
    // console.log(typeof(tensor))
})


// .then( (imageBuffer) => {
//     tfnode.node.decodeImage(imageBuffer)
// })
// .then( (tensor) => {
//     console.log(tensor)
// })
// .catch( (e) => {
//     console.log('Error', e)
// })




// tf.loadLayersModel(
//     'https://raw.githubusercontent.com/after23/tfjs_model/main/noise-augmented-18-apr/new_conf/2FC%2B1LSTM/model.json'

// ).then((model) => {
//     const output = model.predict(input_3d)
//     console.log(output.dataSync())
// }).catch((e) => {
//     console.log('Error', e)
// })