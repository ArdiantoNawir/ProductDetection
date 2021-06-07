package com.capstone.productdetection

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.productdetection.source.RecommendedRepository
import com.capstone.productdetection.di.Injection
import com.capstone.productdetection.ui.capture.CaptureViewModel
import com.capstone.productdetection.ui.detail.DetailViewModel
import com.capstone.productdetection.ui.home.HomeViewModel

class ViewModelFactory(private val mRecommendedRepository: RecommendedRepository): ViewModelProvider.NewInstanceFactory(){

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context)).apply {
                    instance = this
                }
            }
    }


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(mRecommendedRepository) as T
            }

            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(mRecommendedRepository) as T
            }

            modelClass.isAssignableFrom(CaptureViewModel::class.java) -> {
                CaptureViewModel(mRecommendedRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel Class: " + modelClass.name)
        }
    }
}