package com.capstone.productdetection.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.productdetection.Source.RecommendedRepository
import com.capstone.productdetection.model.utils.DataDummy
import com.capstone.productdetection.model.utils.DataModel

class HomeViewModel(private val recommendedRepository: RecommendedRepository) : ViewModel() {

//    fun getRecommended(): List<DataModel> = DataDummy.generateSeller()

    fun getRecommended(): LiveData<List<DataModel>> = recommendedRepository.loadRecommended()
}
