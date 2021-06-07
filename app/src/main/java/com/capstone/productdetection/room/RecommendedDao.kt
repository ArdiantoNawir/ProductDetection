package com.capstone.productdetection.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.capstone.productdetection.model.utils.DataModel
import javax.sql.DataSource

@Dao
interface RecommendedDao {

    @Query("SELECT * FROM model_data")
    fun getRecommended(): androidx.paging.DataSource.Factory<Int, DataModel>

    @Query("SELECT * FROM model_data WHERE isFav = 1")
    fun getFavRecommended() : androidx.paging.DataSource.Factory<Int, DataModel>

    @Query("SELECT * FROM model_data WHERE id = :id")
    fun getRecommendedId(id: Int): LiveData<DataModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecommended(recommended: List<DataModel>)

    @Update
    fun updateRecommended(recommended: DataModel)


}