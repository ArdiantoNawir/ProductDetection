package com.capstone.productdetection.Source

import androidx.lifecycle.LiveData
import com.capstone.productdetection.model.utils.DataModel

interface RecommendedDataSource {

    fun loadRecommended(): LiveData<List<DataModel>>

    fun loadDetailRecommended(id: Int): LiveData<DataModel>
}