package com.capstone.productdetection.source

import androidx.lifecycle.LiveData
import com.capstone.productdetection.model.utils.DataModel
import com.capstone.productdetection.model.utils.MaterialModel

interface RecommendedDataSource {

    fun loadRecommended(): LiveData<List<DataModel>>

    fun loadDetailRecommended(id: Int): LiveData<DataModel>

    fun loadMaterial(name: String): LiveData<MaterialModel>
}