package com.bangkit.electrateam.qualityumapp.data.local

import androidx.lifecycle.LiveData
import com.bangkit.electrateam.qualityumapp.data.local.entity.StockEntity
import com.bangkit.electrateam.qualityumapp.data.local.room.StockDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LocalDataSource private constructor(private val stockDao: StockDao) {

    fun getAllStockList(): LiveData<List<StockEntity>> = stockDao.getALlStock()

    fun getAllFavList(): LiveData<List<StockEntity>> = stockDao.getAllFav()

    fun getListCategory(category: String): LiveData<List<StockEntity>> =
        stockDao.getListCategory(category)

    fun getDetailStock(id: Int): LiveData<StockEntity> = stockDao.getDetailStock(id)

    fun insertStock(stock: StockEntity) = runBlocking {
        this.launch(Dispatchers.IO) {
            stockDao.insertStock(stock)
        }
    }

    fun setFavStock(stock: StockEntity, newState: Boolean) = runBlocking {
        this.launch(Dispatchers.IO) {
            stock.isFavorite = newState
            stockDao.updateStock(stock)
        }
    }

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(stockDao: StockDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(stockDao)
    }
}