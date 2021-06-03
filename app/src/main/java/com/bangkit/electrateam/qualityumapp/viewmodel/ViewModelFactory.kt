package com.bangkit.electrateam.qualityumapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.electrateam.qualityumapp.data.StockRepository
import com.bangkit.electrateam.qualityumapp.di.Injection
import com.bangkit.electrateam.qualityumapp.ui.add.AddViewModel
import com.bangkit.electrateam.qualityumapp.ui.detail.DetailViewModel
import com.bangkit.electrateam.qualityumapp.ui.favorite.FavoriteViewModel
import com.bangkit.electrateam.qualityumapp.ui.home.HomeViewModel
import com.bangkit.electrateam.qualityumapp.ui.stock.StockViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory private constructor(private val repository: StockRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }

            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(repository) as T
            }

            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(repository) as T
            }

            modelClass.isAssignableFrom(StockViewModel::class.java) -> {
                StockViewModel(repository) as T
            }

            modelClass.isAssignableFrom(AddViewModel::class.java) -> {
                AddViewModel(repository) as T
            }

            else -> throw Throwable("Unknown ViewModel Class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context)).apply {
                    instance = this
                }
            }
    }
}