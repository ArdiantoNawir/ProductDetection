package com.capstone.productdetection.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.capstone.productdetection.source.RecommendedRepository
import com.capstone.productdetection.model.utils.DataModel

class DetailViewModel(private val recommendedRepository: RecommendedRepository): ViewModel() {

    fun getDetail(id: Int): LiveData<DataModel> = recommendedRepository.loadDetailRecommended(id)
}