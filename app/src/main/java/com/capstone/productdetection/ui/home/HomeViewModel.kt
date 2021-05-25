package com.capstone.productdetection.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.productdetection.model.utils.DataDummy
import com.capstone.productdetection.model.utils.DataModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Ini buat rekomendasi seller, atau ada saran lain?"
    }
    val text: LiveData<String> = _text

    fun getRecomended(): List<DataModel> = DataDummy.generateSeller()
}