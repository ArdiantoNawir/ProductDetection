package com.capstone.productdetection.di

import android.content.Context
import com.capstone.productdetection.source.RecommendedRepository
import com.capstone.productdetection.source.RemoteDataSource

object Injection {

    fun provideRepository(context: Context): RecommendedRepository {
        val remoteDataSource = RemoteDataSource.getInstance()

        return RecommendedRepository.getInstance(remoteDataSource)
    }
}