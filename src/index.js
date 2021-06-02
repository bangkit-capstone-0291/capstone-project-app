const express = require('express')
require('./db/mongoose')
const userRouter = require('./routers/user')
const fruitRouter = require('./routers/fruit')


const app = express()
const port = process.env.PORT || 3000

app.use(express.json())
app.use(userRouter)
app.use(fruitRouter )

app.listen(port, () => {
    console.log('Server is up on port ' + 3000)
})