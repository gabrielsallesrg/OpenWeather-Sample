package com.gsrg.data.forecast.storage

import com.gsrg.data.database.forecast.dao.ForecastDao
import com.gsrg.data.database.forecast.entity.ForecastEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ForecastStorageImpl @Inject constructor(
    private val dao: com.gsrg.data.database.forecast.dao.ForecastDao,
): ForecastStorage {
    override suspend fun insertUpdateForecastList(forecastList: List<com.gsrg.data.database.forecast.entity.ForecastEntity>) {
        dao.insertUpdateForecastList(forecastList = forecastList)
    }

    override fun getForecastList(): Flow<List<com.gsrg.data.database.forecast.entity.ForecastEntity>> = dao.getForecastList()
}
