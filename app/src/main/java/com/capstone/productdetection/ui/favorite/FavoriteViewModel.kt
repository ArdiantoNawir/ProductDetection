package com.capstone.productdetection.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FavoriteViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Ini niatnya mau kubuat biar hasil rekomendasi seller ntar bisa disimpen di halaman favorite ini"
    }
    val text: LiveData<String> = _text
}