package com.capstone.productdetection.source

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.productdetection.model.utils.*
import com.capstone.productdetection.service.ApiConfig
import com.capstone.productdetection.utils.EspressoIdlingResource
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

    fun getListRecommended(): LiveData<APIResponse<List<RecommendedResult>>> {
        EspressoIdlingResource.increment()
        val result = MutableLiveData<APIResponse<List<RecommendedResult>>>()
        ApiConfig.getApiService().getProducer()
            .enqueue(object : Callback<RecommendedResponse> {
                override fun onResponse(
                    call: Call<RecommendedResponse>,
                    response: Response<RecommendedResponse>
                ) {
                    result.value = APIResponse.success(response.body()?.results as List<RecommendedResult>)
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<RecommendedResponse>, t: Throwable) {
                    Log.e(TAG, "Failure ${t.message}")
                    EspressoIdlingResource.decrement()
                }

            })
        return result
    }

    fun getDetailRecommended(id: Int): LiveData<APIResponse<DetailResult>> {
        EspressoIdlingResource.increment()
        val result = MutableLiveData<APIResponse<DetailResult>>()
        ApiConfig.getApiService().getDetail(id)
            .enqueue(object : Callback<DetailResult> {
                override fun onResponse(
                    call: Call<DetailResult>,
                    response: Response<DetailResult>
                ) {
                    result.value = APIResponse.success(response.body() as DetailResult)
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<DetailResult>, t: Throwable) {
                    Log.e(TAG, "Failure to get Detail Movies : ${t.message}")
                    EspressoIdlingResource.decrement()
                }

            })
        return result
    }

    fun getMaterial(callback: LoadMaterial, name: String) {
        ApiConfig.getApiService().getMaterial(name)
            .enqueue(object : Callback<MaterialResult> {
                override fun onResponse(
                    call: Call<MaterialResult>,
                    response: Response<MaterialResult>
                ) {
                    Log.e(TAG, "SUccess Remote")
                    callback.onDetailMaterialReceived(response.body())
                }

                override fun onFailure(call: Call<MaterialResult>, t: Throwable) {
                    Log.e(TAG, "Failure ${t.message}")
                }

            })
    }

    interface LoadMaterial {
        fun onDetailMaterialReceived(materialResponse: MaterialResult?)
    }
}