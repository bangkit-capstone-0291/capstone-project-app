package com.bangkit.electrateam.qualityumapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StockData(
    var id: Int,
    var image: Int,
    var name: String,
    var category: String,
    var quantity: Int,
    var description: String,
    var quality: String,
    var isFavorite: Boolean = false
) : Parcelable