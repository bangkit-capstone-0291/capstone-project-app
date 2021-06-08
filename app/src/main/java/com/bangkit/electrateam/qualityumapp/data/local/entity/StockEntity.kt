package com.bangkit.electrateam.qualityumapp.data.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stock_entities")
data class StockEntity(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int? = null,

    @ColumnInfo(name = "image")
    var image: String?,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "category")
    var category: String,

    @ColumnInfo(name = "quantity")
    var quantity: Int? = 1,

    @ColumnInfo(name = "description")
    var description: String? = "",

    @ColumnInfo(name = "quality")
    var quality: String? = "",

    @ColumnInfo(name = "expDate")
    var expDate: String? = "",

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
)
