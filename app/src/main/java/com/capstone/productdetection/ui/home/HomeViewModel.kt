package com.capstone.productdetection.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Ini buat rekomendasi seller, atau ada saran lain?"
    }
    val text: LiveData<String> = _text
}