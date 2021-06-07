package com.capstone.productdetection.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.capstone.productdetection.model.utils.DataModel
import com.capstone.productdetection.source.RecommendedRepository

class FavoriteViewModel(private val recommendedRepository: RecommendedRepository) : ViewModel() {

    fun getFavListRecommended(): LiveData<PagedList<DataModel>> =
        recommendedRepository.getRecommendedFav()

    fun setFavListRecommended(recommended: DataModel) {
        val newState = !recommended.isFav
        recommendedRepository.setRecommendedFav(recommended, newState)
    }
}