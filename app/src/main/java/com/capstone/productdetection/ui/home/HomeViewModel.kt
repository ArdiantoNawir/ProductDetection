package com.capstone.productdetection.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.capstone.productdetection.source.RecommendedRepository
import com.capstone.productdetection.model.utils.DataModel

class HomeViewModel(private val recommendedRepository: RecommendedRepository) : ViewModel() {

    fun getRecommended(): LiveData<List<DataModel>> = recommendedRepository.loadRecommended()
}
