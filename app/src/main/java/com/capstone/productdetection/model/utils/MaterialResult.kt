package com.capstone.productdetection.model.utils

import com.google.gson.annotations.SerializedName

data class MaterialResult (
    @field:SerializedName("original_name")
    var name: String,

    @field:SerializedName("data_material")
    var material: List<String>
)