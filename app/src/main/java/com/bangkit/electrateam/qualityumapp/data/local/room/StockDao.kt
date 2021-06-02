package com.bangkit.electrateam.qualityumapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bangkit.electrateam.qualityumapp.data.local.entity.StockEntity

@Dao
interface StockDao {

    @Query("SELECT * FROM stock_entities")
    fun getALlStock(): LiveData<List<StockEntity>>

    @Query("SELECT * FROM stock_entities where isFavorite = 1")
    fun getAllFav(): LiveData<List<StockEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(stock: StockEntity)

    @Update
    fun updateTvShow(stock: StockEntity)

    @Delete
    suspend fun deleteUser(stock: StockEntity)
}