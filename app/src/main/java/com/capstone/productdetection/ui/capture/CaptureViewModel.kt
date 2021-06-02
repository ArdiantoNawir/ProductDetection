package com.capstone.productdetection.ui.capture

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.capstone.productdetection.model.utils.MaterialModel
import com.capstone.productdetection.source.RecommendedRepository

class CaptureViewModel(private val recommendedRepository: RecommendedRepository): ViewModel() {

    fun getMaterial(name: String): LiveData<MaterialModel> = recommendedRepository.loadMaterial(name)
}