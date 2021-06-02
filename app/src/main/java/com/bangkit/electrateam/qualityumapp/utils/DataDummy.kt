package com.bangkit.electrateam.qualityumapp.utils

import com.bangkit.electrateam.qualityumapp.R
import com.bangkit.electrateam.qualityumapp.data.StockData

object DataDummy {

    fun generateDummyStock(): List<StockData> {

        val data = ArrayList<StockData>()

        data.add(
            StockData(
                1,
                R.drawable.apple_example,
                "Apple",
                "Fruits",
                20,
                "nothing",
                "Good Fruit",
                false
            )
        )

        data.add(
            StockData(
                2,
                R.drawable.banana_example,
                "Banana",
                "Fruits",
                47,
                "nothing",
                "Bad Fruit",
                false
            )
        )

        data.add(
            StockData(
                3,
                R.drawable.orange_example,
                "Orange",
                "Fruits",
                108,
                "nothing",
                "Good Fruit",
                false
            )
        )

        data.add(
            StockData(
                4,
                R.drawable.fuji_apple,
                "Fuji Apple",
                "Fruits",
                31,
                "nothing",
                "Good Fruit",
                false
            )
        )

        data.add(
            StockData(
                5,R.drawable.broccoli,
                "Broccoli",
                "Vegetables",
                5,
                "nothing",
                " ",
                false
            )
        )

        data.add(
            StockData(
                6,
                R.drawable.flour,
                "Flour",
                "Others",
                80,
                "nothing",
                " ",
                false
            )
        )

        data.add(
            StockData(
                7,
                R.drawable.strawberry,
                "Strawberry",
                "Fruits",
                33,
                "nothing",
                "Good Fruit",
                false
            )
        )

        return data
    }
}