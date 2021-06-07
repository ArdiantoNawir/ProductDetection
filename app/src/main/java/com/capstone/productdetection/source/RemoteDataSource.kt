package com.capstone.productdetection.source

import android.util.Log
import com.capstone.productdetection.model.utils.DetailResult
import com.capstone.productdetection.model.utils.MaterialResult
import com.capstone.productdetection.model.utils.RecommendedResponse
import com.capstone.productdetection.model.utils.RecommendedResult
import com.capstone.productdetection.service.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {

    companion object {
        const val TAG = "Remote Data Source"

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource().apply { instance = this }
            }
    }

    fun getListRecommended(callback: LoadRecommended) {
        ApiConfig.getApiService().getProducer()
            .enqueue(object : Callback<RecommendedResponse> {
                override fun onResponse(
                    call: Call<RecommendedResponse>,
                    response: Response<RecommendedResponse>
                ) {
                    callback.onAllRecommendedReceived(response.body()?.results)
                }

                override fun onFailure(call: Call<RecommendedResponse>, t: Throwable) {
                    Log.e(TAG, "Failure ${t.message}")
                }

            })
    }

    fun getDetailRecommended(callback: LoadDetailRecommended, id: Int) {
        ApiConfig.getApiService().getDetail(id)
            .enqueue(object : Callback<DetailResult> {
                override fun onResponse(
                    call: Call<DetailResult>,
                    response: Response<DetailResult>
                ) {
                    callback.onDetailRecommendedReceived(response.body())
                }

                override fun onFailure(call: Call<DetailResult>, t: Throwable) {
                    Log.e(TAG, "Failure ${t.message}")
                }

            })
    }

    fun getMaterial(callback: LoadMaterial, name: String) {
        ApiConfig.getApiService().getMaterial(name)
            .enqueue(object : Callback<MaterialResult> {
                override fun onResponse(
                    call: Call<MaterialResult>,
                    response: Response<MaterialResult>
                ) {
                    callback.onDetailMaterialReceived(response.body())
                }

                override fun onFailure(call: Call<MaterialResult>, t: Throwable) {
                    Log.e(TAG, "Failure ${t.message}")
                }

            })
    }

    interface LoadRecommended {
        fun onAllRecommendedReceived(response: List<RecommendedResult>?)
    }

    interface LoadDetailRecommended {
        fun onDetailRecommendedReceived(recommendedDetail: DetailResult?)
    }

    interface LoadMaterial {
        fun onDetailMaterialReceived(materialResponse: MaterialResult?)
    }
}