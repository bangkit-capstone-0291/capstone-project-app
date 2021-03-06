package com.bangkit.electrateam.qualityumapp.di

import android.content.Context
import com.bangkit.electrateam.qualityumapp.data.StockRepository
import com.bangkit.electrateam.qualityumapp.data.local.LocalDataSource
import com.bangkit.electrateam.qualityumapp.data.local.room.StockDatabase
import com.bangkit.electrateam.qualityumapp.data.remote.RemoteDataSource
import com.bangkit.electrateam.qualityumapp.data.remote.network.ApiConfig

object Injection {

    fun provideRepository(context: Context): StockRepository {

        val database = StockDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(ApiConfig.invoke())
        val localDataSource = LocalDataSource.getInstance(database.stockDao())

        return StockRepository.getInstance(localDataSource, remoteDataSource)
    }
}