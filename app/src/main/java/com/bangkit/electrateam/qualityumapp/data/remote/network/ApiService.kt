package com.bangkit.electrateam.qualityumapp.data.remote.network

import com.bangkit.electrateam.qualityumapp.data.remote.response.PredictionResponse
import com.bangkit.electrateam.qualityumapp.data.remote.response.QualityResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

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

    /*@Multipart
    @POST("/food")
    fun postNewStock(
        @Part image: MultipartBody.Part
    ): Call<StockResponse>

    @Multipart
    @POST("/food/{id}/image")
    fun postImgNewStock(
        @Part image: MultipartBody.Part,
        @Path("id") id: Int
    ): Call<StockResponse>

    @GET("/food")
    fun getAllStock(): Call<StockResponse>

    @GET("/food/category/")
    fun getAllStockByCategory(): Call<StockResponse>

    @GET("/food/{id}")
    fun getDetailStock(
        @Path("id") id: Int
    ): Call<StockDetailResponse>*/
}