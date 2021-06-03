package com.bangkit.electrateam.qualityumapp.data

import androidx.lifecycle.LiveData
import com.bangkit.electrateam.qualityumapp.model.StockData

interface StockDataSource {

    fun getAllStock(): LiveData<List<StockData>>

    fun getFavStock(): LiveData<List<StockData>>

    fun setFavTvShow(stock: StockData, state: Boolean)
}