package com.bangkit.electrateam.qualityumapp.data.remote.network

import com.bangkit.electrateam.qualityumapp.data.remote.response.QualityResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @Multipart
    @POST("image")
    suspend fun uploadImage(
        @Part part: MultipartBody.Part
    ): QualityResponse
}