package com.bangkit.electrateam.qualityumapp.data

import androidx.lifecycle.LiveData
import com.bangkit.electrateam.qualityumapp.data.remote.network.ApiResponse
import com.bangkit.electrateam.qualityumapp.data.remote.response.QualityResponse
import com.bangkit.electrateam.qualityumapp.model.StockData
import com.bangkit.electrateam.qualityumapp.ui.camera.uploadimage.UploadRequest
import java.io.File

interface StockDataSource {

    fun getClassification(file: File, body: UploadRequest): LiveData<ApiResponse<QualityResponse>>

    fun getBananaPrediction(file: File, body: UploadRequest): LiveData<ApiResponse<QualityResponse>>

    fun getOrangePrediction(file: File, body: UploadRequest): LiveData<ApiResponse<QualityResponse>>

    fun getAllStock(): LiveData<List<StockData>>

    fun getAllCategory(category: String): LiveData<List<StockData>>

    fun getFavStock(): LiveData<List<StockData>>

    fun getDetailStock(id: Int): LiveData<StockData>

    fun insertStock(stock: StockData)

    fun setFavStock(stock: StockData, state: Boolean)
}