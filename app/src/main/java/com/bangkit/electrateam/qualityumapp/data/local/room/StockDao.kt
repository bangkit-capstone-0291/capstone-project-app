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

    @Query("SELECT * FROM stock_entities where category like :category")
    fun getListCategory(category: String): LiveData<List<StockEntity>>

    @Transaction
    @Query("SELECT * FROM stock_entities WHERE id = :id")
    fun getDetailStock(id: Int): LiveData<StockEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStock(stock: StockEntity)

    @Update
    suspend fun updateStock(stock: StockEntity)
}