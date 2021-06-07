package com.bangkit.electrateam.qualityumapp.ui.camera

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CameraViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
//        value = result
    }
    val text: LiveData<String> = _text
}