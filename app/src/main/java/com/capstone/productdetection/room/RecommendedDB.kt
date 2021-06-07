package com.capstone.productdetection.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.capstone.productdetection.model.utils.DataModel

@Database(
    entities = [DataModel::class],
    version = 3,
    exportSchema = false
)
abstract class RecommendedDB : RoomDatabase() {
    abstract fun recommendedDao(): RecommendedDao

    companion object {
        @Volatile
        private var INSTANCE: RecommendedDB? = null

        fun getInstance(context: Context): RecommendedDB =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    RecommendedDB::class.java,
                    "recommended.db"
                ).fallbackToDestructiveMigration().build().apply { INSTANCE = this }
            }
    }
}