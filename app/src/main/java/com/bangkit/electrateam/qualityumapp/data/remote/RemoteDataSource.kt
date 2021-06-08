package com.bangkit.electrateam.qualityumapp.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bangkit.electrateam.qualityumapp.data.remote.network.ApiResponse
import com.bangkit.electrateam.qualityumapp.data.remote.network.ApiService
import com.bangkit.electrateam.qualityumapp.data.remote.response.PredictionResponse
import com.bangkit.electrateam.qualityumapp.data.remote.response.QualityResponse
import com.bangkit.electrateam.qualityumapp.ui.camera.uploadimage.UploadRequest
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class RemoteDataSource private constructor(private val apiService: ApiService) {

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(service: ApiService): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(service)
            }
    }

    fun getClassification(file: File, body: UploadRequest): LiveData<ApiResponse<QualityResponse>> {
        val resultData = MutableLiveData<ApiResponse<QualityResponse>>()
        val client = apiService.getClassification(MultipartBody.Part.createFormData("image", file.name, body))
        client.enqueue(object : Callback<QualityResponse> {
            override fun onResponse(
                call: Call<QualityResponse>,
                response: Response<QualityResponse>
            ) {
                val dataPredict = response.body()
                resultData.value =
                    if (dataPredict != null) ApiResponse.Success(dataPredict) else ApiResponse.Empty
            }

            override fun onFailure(call: Call<QualityResponse>, t: Throwable) {
                resultData.value = ApiResponse.Error(t.message.toString())
                Log.e("RemoteDataSource", t.message.toString())
            }
        })
        return resultData
    }

    fun getBananaPrediction(file: File, body: UploadRequest): LiveData<ApiResponse<PredictionResponse>> {
        val resultData = MutableLiveData<ApiResponse<PredictionResponse>>()
        val client = apiService.getBananaPrediction(MultipartBody.Part.createFormData("image", file.name, body))
        client.enqueue(object : Callback<PredictionResponse> {
            override fun onResponse(
                call: Call<PredictionResponse>,
                response: Response<PredictionResponse>
            ) {
                val dataPredict = response.body()
                resultData.value =
                    if (dataPredict != null) ApiResponse.Success(dataPredict) else ApiResponse.Empty
            }

            override fun onFailure(call: Call<PredictionResponse>, t: Throwable) {
                resultData.value = ApiResponse.Error(t.message.toString())
                Log.e("RemoteDataSource", t.message.toString())
            }
        })
        return resultData
    }

    fun getOrangePrediction(file: File, body: UploadRequest): LiveData<ApiResponse<PredictionResponse>> {
        val resultData = MutableLiveData<ApiResponse<PredictionResponse>>()
        val client = apiService.getOrangePrediction(MultipartBody.Part.createFormData("image", file.name, body))
        client.enqueue(object : Callback<PredictionResponse> {
            override fun onResponse(
                call: Call<PredictionResponse>,
                response: Response<PredictionResponse>
            ) {
                val dataPredict = response.body()
                resultData.value =
                    if (dataPredict != null) ApiResponse.Success(dataPredict) else ApiResponse.Empty
            }

            override fun onFailure(call: Call<PredictionResponse>, t: Throwable) {
                resultData.value = ApiResponse.Error(t.message.toString())
                Log.e("RemoteDataSource", t.message.toString())
            }
        })
        return resultData
    }
}