package com.capstone.productdetection.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.productdetection.model.utils.DataDummy
import com.capstone.productdetection.model.utils.DataModel

class HomeViewModel : ViewModel() {
    fun getRecomended(): List<DataModel> = DataDummy.generateSeller()
}