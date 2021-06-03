const express = require('express')
require('./db/mongoose')
const userRouter = require('./routers/user')
const fruitRouter = require('./routers/fruit')
const imageTestRouter = require('./routers/imageTest')


const app = express()
const port = process.env.PORT || 8080

app.use(express.json())
app.use(userRouter)
app.use(fruitRouter )
app.use(imageTestRouter)

app.listen(port, () => {
    console.log('Server is up on port ' + port)
})