package com.capstone.productdetection.di

import android.content.Context
import com.capstone.productdetection.datasource.LocalDS
import com.capstone.productdetection.room.RecommendedDB
import com.capstone.productdetection.source.RecommendedRepository
import com.capstone.productdetection.source.RemoteDataSource
import com.capstone.productdetection.utils.AppExecutors

object Injection {

    fun provideRepository(context: Context): RecommendedRepository {

        val db = RecommendedDB.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance()
        val localDS = LocalDS.getInstance(db.recommendedDao())
        val appExecutors = AppExecutors()

        return RecommendedRepository.getInstance(remoteDataSource, localDS, appExecutors)
    }
}