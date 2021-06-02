package com.capstone.productdetection.model.utils

import com.google.gson.annotations.SerializedName

data class DetailResult(
    @field: SerializedName("producer_id")
    val id: Int,

    @field:SerializedName("picture")
    val image: String,

    @field:SerializedName("name")
    val title: String,

    @field:SerializedName("address")
    val location: String,

    @field:SerializedName("description")
    val desc: String
)
