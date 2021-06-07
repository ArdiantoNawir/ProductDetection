package com.capstone.productdetection.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.capstone.productdetection.datasource.LocalDS
import com.capstone.productdetection.datasource.NetworkBoundResource
import com.capstone.productdetection.model.utils.*
import com.capstone.productdetection.utils.AppExecutors
import com.capstone.productdetection.vo.Resource

class RecommendedRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDS: LocalDS,
    private val appExecutors: AppExecutors
) : RecommendedDataSource {

    companion object {
        @Volatile
        private var instance: RecommendedRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localDS: LocalDS,
            appExecutors: AppExecutors
        ): RecommendedRepository =
            instance ?: synchronized(this) {
                RecommendedRepository(remoteData, localDS, appExecutors).apply { instance = this }
            }
    }

    override fun loadRecommended(): LiveData<Resource<PagedList<DataModel>>> {
        return object :
            NetworkBoundResource<PagedList<DataModel>, List<RecommendedResult>>(appExecutors) {

            override fun shouldFetch(data: PagedList<DataModel>?): Boolean =
                data == null || data.isEmpty()

            override fun loadFromDb(): LiveData<PagedList<DataModel>> {
                val conf = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDS.getDataRecommended(), conf).build()
            }

            override fun createCall(): LiveData<APIResponse<List<RecommendedResult>>> =
                remoteDataSource.getListRecommended()

            override fun saveCallResult(data: List<RecommendedResult>) {
                val listRecommended = ArrayList<DataModel>()
                for (dataRecommended in data) {
                    dataRecommended.apply {
                        val recommended = DataModel(
                            id, title, location, image, desc
                        )
                        listRecommended.add(recommended)
                    }
                }
                localDS.insertRecommended(listRecommended)
            }
        }.asLiveData()
    }

    override fun loadDetailRecommended(id: Int): LiveData<Resource<DataModel>> {
        return object : NetworkBoundResource<DataModel, DetailResult>(appExecutors) {

            override fun shouldFetch(data: DataModel?): Boolean =
                data == null

            override fun loadFromDb(): LiveData<DataModel> = localDS.getRecommendedId(id)
            override fun createCall(): LiveData<APIResponse<DetailResult>> =
                remoteDataSource.getDetailRecommended(id)

            override fun saveCallResult(data: DetailResult) {
                with(data) {
                    val dataDetail = DataModel(
                        id = this.id,
                        title = title,
                        location = location,
                        image = image,
                        desc = desc,
                        isFav = false
                    )
                    localDS.updateFavRecommended(dataDetail, false)
                }
            }
        }.asLiveData()
    }

    override fun setRecommendedFav(recommended: DataModel, state: Boolean) =
        appExecutors.diskIO().execute {
            localDS.updateFavRecommended(recommended, state)
        }

    override fun getRecommendedFav(): LiveData<PagedList<DataModel>> {
        val conf = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()

        return LivePagedListBuilder(localDS.getFavRecommended(), conf).build()
    }

    override fun loadMaterial(name: String): LiveData<MaterialModel> {
        val getMaterial = MutableLiveData<MaterialModel>()
        remoteDataSource.getMaterial(object : RemoteDataSource.LoadMaterial {
            override fun onDetailMaterialReceived(materialResponse: MaterialResult?) {
                lateinit var detailMaterial: MaterialModel

                materialResponse?.apply {
                    val listMaterial = ArrayList<String>()
                    for (material in material) {
                        listMaterial.add(material)
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