package com.capstone.productdetection.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.capstone.productdetection.model.utils.DataModel
import com.capstone.productdetection.model.utils.MaterialModel
import com.capstone.productdetection.vo.Resource

interface RecommendedDataSource {

    fun loadRecommended(): LiveData<Resource<PagedList<DataModel>>>

    fun loadDetailRecommended(id: Int): LiveData<Resource<DataModel>>

    fun loadMaterial(name: String): LiveData<MaterialModel>

    fun setRecommendedFav(recommended: DataModel, state: Boolean)

    fun getRecommendedFav(): LiveData<PagedList<DataModel>>
}