package com.bangkit.electrateam.qualityumapp.ui.camera.uploadimage

import com.bangkit.electrateam.qualityumapp.data.remote.response.QualityResponse
import com.bangkit.electrateam.qualityumapp.ui.login.UserRequest
import com.bangkit.electrateam.qualityumapp.ui.login.UserResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface API {

    @Multipart
    @POST("image")
    fun uploadImage(
        @Part image: MultipartBody.Part
    ): Call<QualityResponse>

    @POST
    fun login(
        @Body userRequest: UserRequest
    ): Call<UserResponse>

    companion object {

        operator fun invoke(): API {
            return Retrofit.Builder()
                .baseUrl("https://apt-index-313812.et.r.appspot.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(API::class.java)
        }
    }
    /*@Multipart
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
    }*/
}