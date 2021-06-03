package com.bangkit.electrateam.qualityumapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bangkit.electrateam.qualityumapp.data.StockRepository
import com.bangkit.electrateam.qualityumapp.model.StockData

class DetailViewModel(private val stockRepository: StockRepository) : ViewModel() {

    val stockId = MutableLiveData<Int>()

    var detailStock: LiveData<StockData> =
        Transformations.switchMap(stockId) {
            stockRepository.getDetailStock(it)
        }

    fun setSelectedStock(stockId: Int) {
        this.stockId.value = stockId
    }

    fun setFavStock(stock: StockData) {
        val newState = !stock.isFavorite
        stockRepository.setFavStock(stock, newState)
    }
}