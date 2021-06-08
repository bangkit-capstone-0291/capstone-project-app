package com.bangkit.electrateam.qualityumapp.ui.camera

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkit.electrateam.qualityumapp.data.StockRepository
import com.bangkit.electrateam.qualityumapp.data.remote.network.ApiResponse
import com.bangkit.electrateam.qualityumapp.data.remote.response.QualityResponse
import java.io.File

class CameraPreviewModel(private val stockRepository: StockRepository) : ViewModel() {

    /*fun getPrediction(file: File): LiveData<ApiResponse<QualityResponse>> =
        stockRepository.getPrediction(file)*/
}