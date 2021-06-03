package com.bangkit.electrateam.qualityumapp.data

import androidx.lifecycle.LiveData
import com.bangkit.electrateam.qualityumapp.model.StockData

interface StockDataSource {

    fun getAllStock(): LiveData<List<StockData>>

    fun getAllCategory(category: String): LiveData<List<StockData>>

    fun getFavStock(): LiveData<List<StockData>>

    fun getDetailStock(id: Int): LiveData<StockData>

    fun insertStock(stock: StockData)

    fun setFavStock(stock: StockData, state: Boolean)
}