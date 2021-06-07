package com.capstone.productdetection.model.utils

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "model_data")
data class DataModel(
    @PrimaryKey
    @NonNull

    @field: SerializedName("producer_id")
    val id: Int?,

    @field:SerializedName("picture")
    val image: String?,

    @field:SerializedName("name")
    val title: String?,

    @field:SerializedName("address")
    val location: String?,

    @field:SerializedName("description")
    val desc: String?,

    @field:SerializedName("isFav")
    var isFav: Boolean = false
)
