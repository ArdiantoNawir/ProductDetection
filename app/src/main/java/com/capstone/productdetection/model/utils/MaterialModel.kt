package com.capstone.productdetection.model.utils

import com.google.gson.annotations.SerializedName

data class MaterialModel (
    @SerializedName("original_name")
    var name: String?,

    @SerializedName("data_material")
    var material: List<String>
)