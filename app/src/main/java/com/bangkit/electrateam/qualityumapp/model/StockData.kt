package com.bangkit.electrateam.qualityumapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StockData(
    var id: Int?,
    var image: Int?,
    var name: String,
    var category: String,
    var quantity: Int?,
    var description: String?,
    var quality: String?,
    var expDate: String?,
    var isFavorite: Boolean = false
) : Parcelable