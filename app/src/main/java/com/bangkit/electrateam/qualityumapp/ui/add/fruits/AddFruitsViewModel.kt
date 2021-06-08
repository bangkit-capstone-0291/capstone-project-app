package com.bangkit.electrateam.qualityumapp.ui.add.fruits

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.electrateam.qualityumapp.data.StockRepository
import com.bangkit.electrateam.qualityumapp.model.StockData

class AddFruitsViewModel(private val stockRepository: StockRepository) : ViewModel() {

    private val state = MutableLiveData<Boolean>()

    val observableState: LiveData<Boolean>
        get() = state

    fun addStock(stock: StockData) {
        state.value = try {
            stockRepository.insertStock(stock)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}