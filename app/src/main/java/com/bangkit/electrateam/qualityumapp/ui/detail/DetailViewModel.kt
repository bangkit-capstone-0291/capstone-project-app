package com.bangkit.electrateam.qualityumapp.ui.detail

import androidx.lifecycle.ViewModel
import com.bangkit.electrateam.qualityumapp.data.StockRepository
import com.bangkit.electrateam.qualityumapp.model.StockData

class DetailViewModel(private val stockRepository: StockRepository) : ViewModel() {

    /*fun addFavStock(stock: StockData) = stockRepository.insert(user)

    fun setFavStock() {
        val stockResource = detailMovie.value
        if (movieResource != null) {
            val movie = movieResource.data
            if (movie != null) {
                val movieEntity = movie.mMovie
                val newState = !movieEntity.isFavorite
                repository.setFavMovie(movieEntity, newState)
            }
        }
    }*/
}