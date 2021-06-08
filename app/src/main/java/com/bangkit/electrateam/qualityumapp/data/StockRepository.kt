package com.bangkit.electrateam.qualityumapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.bangkit.electrateam.qualityumapp.data.local.LocalDataSource
import com.bangkit.electrateam.qualityumapp.data.remote.RemoteDataSource
import com.bangkit.electrateam.qualityumapp.data.remote.network.ApiResponse
import com.bangkit.electrateam.qualityumapp.data.remote.response.QualityResponse
import com.bangkit.electrateam.qualityumapp.model.StockData
import com.bangkit.electrateam.qualityumapp.utils.DataMapper
import java.io.File

class StockRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : StockDataSource {

    override fun getPrediction(file: File): LiveData<ApiResponse<QualityResponse>> {
        return remoteDataSource.getPrediction(file)
    }

    override fun getAllStock(): LiveData<List<StockData>> {
        return Transformations.map(localDataSource.getAllStockList()) {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun getAllCategory(category: String): LiveData<List<StockData>> {
        return Transformations.map(localDataSource.getListCategory(category)) {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun getFavStock(): LiveData<List<StockData>> {
        return Transformations.map(localDataSource.getAllFavList()) {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun getDetailStock(id: Int): LiveData<StockData> {
        return Transformations.map(localDataSource.getDetailStock(id)) {
            DataMapper.mapEntityToDomain(it)
        }
    }

    override fun insertStock(stock: StockData) {
        val stockEntity = DataMapper.mapDomainToEntity(stock)
        localDataSource.insertStock(stockEntity)
    }

    override fun setFavStock(stock: StockData, state: Boolean) {
        val stockEntity = DataMapper.mapDomainToEntity(stock)
        localDataSource.setFavStock(stockEntity, state)
    }

    companion object {
        @Volatile
        private var instance: StockRepository? = null

        fun getInstance(
            localDataSource: LocalDataSource,
            remoteDataSource: RemoteDataSource
        ): StockRepository =
            instance ?: synchronized(this) {
                instance ?: StockRepository(localDataSource, remoteDataSource)
            }
    }
}