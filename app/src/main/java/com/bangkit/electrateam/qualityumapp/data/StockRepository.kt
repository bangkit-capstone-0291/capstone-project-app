package com.bangkit.electrateam.qualityumapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.bangkit.electrateam.qualityumapp.data.local.LocalDataSource
import com.bangkit.electrateam.qualityumapp.data.remote.RemoteDataSource
import com.bangkit.electrateam.qualityumapp.model.StockData
import com.bangkit.electrateam.qualityumapp.utils.DataMapper

class StockRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : StockDataSource {

    override fun getAllStock(): LiveData<List<StockData>> {
        return Transformations.map(localDataSource.getAllStockList()) {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun getFavStock(): LiveData<List<StockData>> {
        return Transformations.map(localDataSource.getAllFavList()) {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun insertStock(stock: StockData) {
        val stockEntity = DataMapper.mapDomainToEntity(stock)
        localDataSource.insertStock(stockEntity)
    }

    override fun setFavTvShow(stock: StockData, state: Boolean) {
        val stockEntity = DataMapper.mapDomainToEntity(stock)
        localDataSource.setFavStock(stockEntity, state)
    }

    companion object {
        @Volatile
        private var instance: StockRepository? = null

        private const val TAG = "StockRepository"

        fun getInstance(
            localDataSource: LocalDataSource,
            remoteDataSource: RemoteDataSource
        ): StockRepository =
            instance ?: synchronized(this) {
                instance ?: StockRepository(localDataSource, remoteDataSource).apply {
                    instance = this
                }
            }
    }
}