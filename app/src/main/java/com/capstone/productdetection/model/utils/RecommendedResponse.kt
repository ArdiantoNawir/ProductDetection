package com.capstone.productdetection.model.utils

import com.google.gson.annotations.SerializedName

data class RecommendedResponse (

    @field:SerializedName("results")
    val results: List<RecommendedResult>

)