package com.gsrg.data.database.forecast.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gsrg.data.database.forecast.entity.ForecastEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpdateForecastList(forecastList: List<ForecastEntity>)

    @Query("SELECT * FROM forecast")
    fun getForecastList(): Flow<List<ForecastEntity>>
}
