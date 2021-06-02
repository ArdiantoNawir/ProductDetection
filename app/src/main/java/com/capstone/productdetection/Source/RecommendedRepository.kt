package com.capstone.productdetection.Source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.productdetection.model.utils.DataModel
import com.capstone.productdetection.model.utils.DetailResult
import com.capstone.productdetection.model.utils.RecommendedResult

class RecommendedRepository private constructor(private val remoteDataSource: RemoteDataSource): RecommendedDataSource {

    companion object {
        @Volatile
        private var instance: RecommendedRepository? = null

        fun getInstance(remoteData: RemoteDataSource): RecommendedRepository =
            instance ?: synchronized(this) {
                RecommendedRepository(remoteData).apply { instance = this }
            }
    }

    override fun loadRecommended(): LiveData<List<DataModel>> {
        val getRecommended = MutableLiveData<List<DataModel>>()
        remoteDataSource.getListRecommended(object : RemoteDataSource.LoadRecommended {
            override fun onAllRecommendedReceived(response: List<RecommendedResult>?) {
                val list = ArrayList<DataModel>()

                if (response != null) {
                    for(recommended in response) {
                        recommended.apply {
                            val listed = DataModel(
                                id, image, title, location, desc
                            )
                            list.add(listed)
                        }
                    }
                    getRecommended.postValue(list)
                }
            }

        })
        return getRecommended
    }

    override fun loadDetailRecommended(id: Int): LiveData<DataModel> {
        val getDetail = MutableLiveData<DataModel>()
        remoteDataSource.getDetailRecommended(object : RemoteDataSource.LoadDetailRecommended {
            override fun onDetailRecommendedReceived(recommendedDetail: DetailResult?) {
                lateinit var detailRecommended : DataModel
                recommendedDetail?.apply {
                    detailRecommended = DataModel(
                        id = id,
                        title = title,
                        image = image,
                        desc =  desc,
                        location = location
                    )
                    getDetail.postValue(detailRecommended)
                }
            }

        }, id)
        return getDetail
    }
}