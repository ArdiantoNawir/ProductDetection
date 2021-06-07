package com.capstone.productdetection.datasource

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.capstone.productdetection.model.utils.DataModel
import com.capstone.productdetection.room.RecommendedDao

class LocalDS private constructor(private val recommendedDao: RecommendedDao) {

    companion object {
        private var INSTANCE: LocalDS? = null

        fun getInstance(recommendedDao: RecommendedDao): LocalDS =
            INSTANCE ?: LocalDS(recommendedDao).apply {
                INSTANCE = this
            }
    }

    fun getDataRecommended() : DataSource.Factory<Int, DataModel> = recommendedDao.getRecommended()

    fun getFavRecommended(): DataSource.Factory<Int, DataModel> = recommendedDao.getFavRecommended()

    fun getRecommendedId(id: Int): LiveData<DataModel> = recommendedDao.getRecommendedId(id)

    fun insertRecommended(recommended: List<DataModel>) = recommendedDao.insertRecommended(recommended)

    fun updateFavRecommended(recommended: DataModel, newState: Boolean) {
        recommended.isFav = newState
        recommendedDao.updateRecommended(recommended)
    }

}