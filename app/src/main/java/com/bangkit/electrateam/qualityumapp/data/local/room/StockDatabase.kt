package com.bangkit.electrateam.qualityumapp.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bangkit.electrateam.qualityumapp.data.local.entity.StockEntity


@Database(entities = [StockEntity::class], version = 1, exportSchema = false)
abstract class StockDatabase : RoomDatabase() {

    abstract fun stockDao(): StockDao

    companion object {

        @Volatile
        private var INSTANCE: StockDatabase? = null

        fun getInstance(context: Context): StockDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    StockDatabase::class.java,
                    "Stock.db"
                ).build().apply {
                    INSTANCE = this
                }
            }
    }
}