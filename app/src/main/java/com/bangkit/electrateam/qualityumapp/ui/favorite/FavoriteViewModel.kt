package com.bangkit.electrateam.qualityumapp.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkit.electrateam.qualityumapp.data.StockRepository
import com.bangkit.electrateam.qualityumapp.model.StockData

class FavoriteViewModel(private val stockRepository: StockRepository) : ViewModel() {

    fun getFavStock(): LiveData<List<StockData>> = stockRepository.getFavStock()
}