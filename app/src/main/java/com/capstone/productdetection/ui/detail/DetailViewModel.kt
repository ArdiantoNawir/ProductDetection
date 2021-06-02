package com.capstone.productdetection.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.capstone.productdetection.Source.RecommendedRepository
import com.capstone.productdetection.model.utils.DataDummy
import com.capstone.productdetection.model.utils.DataModel

class DetailViewModel(private val recommendedRepository: RecommendedRepository): ViewModel() {
//    fun getDetail(name: String): DataModel {
//        lateinit var result: DataModel
//        val listProducer = DataDummy.generateSeller()
//
//        for (producer in listProducer) {
//            if (producer.title == name) {
//                result = producer
//                break
//            }
//        }
//        return result
//    }

    fun getDetail(id: Int): LiveData<DataModel> = recommendedRepository.loadDetailRecommended(id)
}