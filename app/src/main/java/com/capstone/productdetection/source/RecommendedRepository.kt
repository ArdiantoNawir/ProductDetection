package com.capstone.productdetection.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.productdetection.model.utils.*

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

    override fun loadMaterial(name: String): LiveData<MaterialModel> {
        val getMaterial = MutableLiveData<MaterialModel>()
        remoteDataSource.getMaterial(object : RemoteDataSource.LoadMaterial {
            override fun onDetailMaterialReceived(materialResponse: MaterialResult?) {
                lateinit var detailMaterial: MaterialModel

                materialResponse?.apply {
                    val listMaterial = ArrayList<String>()
                    for (material in material) {
                        listMaterial.add(material.name)
                    }

                    detailMaterial = MaterialModel(
                        name = name,
                        material = listMaterial
                    )
                    getMaterial.postValue(detailMaterial)
                }
            }

        }, name)
        return getMaterial
    }
}