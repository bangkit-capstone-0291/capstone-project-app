package com.bangkit.electrateam.qualityumapp.ui.camera.uploadimage

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface API {
    @Multipart
    @POST("/image")
    fun uploadImage(
        @Part image: MultipartBody.Part,
        @Part("desc") desc: RequestBody
    ): Call<ResponseFromUpload>

    companion object {
        operator fun invoke(): API {
            return Retrofit.Builder()
                .baseUrl("http://34.101.102.57/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(API::class.java)
        }
    }
}