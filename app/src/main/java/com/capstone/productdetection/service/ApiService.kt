package com.capstone.productdetection.service

import com.capstone.productdetection.model.utils.DataModel
import com.capstone.productdetection.model.utils.DetailResult
import com.capstone.productdetection.model.utils.RecommendedResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("producer")
    fun getProducer(): Call<RecommendedResponse>

    @GET("producer/{producer_id}")
    fun getDetail(
        @Path("producer_id") id: Int
    ): Call<DetailResult>
}