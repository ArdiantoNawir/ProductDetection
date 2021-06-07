package com.capstone.productdetection.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.capstone.productdetection.source.RecommendedRepository
import com.capstone.productdetection.model.utils.DataModel
import com.capstone.productdetection.vo.Resource

class HomeViewModel(private val recommendedRepository: RecommendedRepository) : ViewModel() {

//    fun getRecommended(): List<DataModel> = DataDummy.generateSeller()

    fun getRecommended(): LiveData<Resource<PagedList<DataModel>>> = recommendedRepository.loadRecommended()
}
