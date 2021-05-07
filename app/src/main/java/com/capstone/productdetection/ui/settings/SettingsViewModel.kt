package com.capstone.productdetection.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Niatnya mau kukasih setting bahasa sama ala2 notification tapi kalo emg gaseberapa penting dihapus aja bagian setting ini"
    }
    val text: LiveData<String> = _text
}