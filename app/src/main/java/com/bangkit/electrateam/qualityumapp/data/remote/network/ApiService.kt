package com.bangkit.electrateam.qualityumapp.data.remote.network

import com.bangkit.electrateam.qualityumapp.data.remote.response.PredictionResponse
import com.bangkit.electrateam.qualityumapp.data.remote.response.QualityResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @Multipart
    @POST("/image/fruit-classification")
    fun getClassification(
        @Part image: MultipartBody.Part
    ): Call<QualityResponse>

    @Multipart
    @POST("/image/banana-prediction")
    fun getBananaPrediction(
        @Part image: MultipartBody.Part
    ): Call<PredictionResponse>

    @Multipart
    @POST("/image/orange-prediction")
    fun getOrangePrediction(
        @Part image: MultipartBody.Part
    ): Call<PredictionResponse>
}