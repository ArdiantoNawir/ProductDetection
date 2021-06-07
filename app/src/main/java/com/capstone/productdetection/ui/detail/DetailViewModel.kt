package com.capstone.productdetection.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.capstone.productdetection.source.RecommendedRepository
import com.capstone.productdetection.model.utils.DataModel
import com.capstone.productdetection.vo.Resource

class DetailViewModel(private val recommendedRepository: RecommendedRepository): ViewModel() {

    private lateinit var dataDetail: LiveData<Resource<DataModel>>

    fun getDetail(id: Int): LiveData<Resource<DataModel>> = recommendedRepository.loadDetailRecommended(id)

    fun setFavorite() {
        val dataRecom = dataDetail.value
        if (dataRecom?.data !=null) {
            val newState = !dataRecom.data.isFav
            recommendedRepository.setRecommendedFav(dataRecom.data, newState)
        }
    }
}