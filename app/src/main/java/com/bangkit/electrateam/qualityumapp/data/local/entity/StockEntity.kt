package com.bangkit.electrateam.qualityumapp.data.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stock_entities")
data class StockEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "image")
    var image: Int,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "category")
    var category: String,

    @ColumnInfo(name = "quantity")
    var quantity: Int? = 0,

    @ColumnInfo(name = "description")
    var description: String? = "",

    @ColumnInfo(name = "quality")
    var quality: String? = "",

    @ColumnInfo(name = "expDate")
    var expDate: String? = "",

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
)
