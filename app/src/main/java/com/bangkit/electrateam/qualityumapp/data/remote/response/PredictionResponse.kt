package com.bangkit.electrateam.qualityumapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class PredictionResponse(
    @SerializedName("result")
    val result: Int
)
