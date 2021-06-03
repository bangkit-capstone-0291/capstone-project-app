package com.bangkit.electrateam.qualityumapp.ui.stock

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkit.electrateam.qualityumapp.data.StockRepository
import com.bangkit.electrateam.qualityumapp.model.StockData
import com.bangkit.electrateam.qualityumapp.utils.DataDummy

class StockViewModel(private val stockRepository: StockRepository)  : ViewModel() {

    fun getAllStock(): LiveData<List<StockData>> = stockRepository.getAllStock()

    fun getDummyStock(): List<StockData> = DataDummy.generateDummyStock()
}