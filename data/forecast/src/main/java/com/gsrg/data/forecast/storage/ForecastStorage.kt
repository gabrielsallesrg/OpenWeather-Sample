package com.gsrg.data.forecast.storage

import com.gsrg.data.database.forecast.entity.ForecastEntity
import kotlinx.coroutines.flow.Flow

interface ForecastStorage {

    suspend fun insertUpdateForecastList(forecastList: List<ForecastEntity>)

    fun getForecastList(): Flow<List<ForecastEntity>>
}
