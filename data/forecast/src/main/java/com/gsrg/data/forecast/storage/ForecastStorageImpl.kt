package com.gsrg.data.forecast.storage

import com.gsrg.data.database.forecast.dao.ForecastDao
import com.gsrg.data.database.forecast.entity.ForecastEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ForecastStorageImpl @Inject constructor(
    private val dao: ForecastDao,
): ForecastStorage {
    override suspend fun insertUpdateForecastList(forecastList: List<ForecastEntity>) {
        dao.deleteAllForecast()
        dao.insertUpdateForecastList(forecastList = forecastList)
    }

    override fun getForecastList(): Flow<List<ForecastEntity>> = dao.getForecastList()
}
