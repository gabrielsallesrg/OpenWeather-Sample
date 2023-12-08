package com.gsrg.domain.forecast.repository

import com.gsrg.data.database.forecast.entity.ForecastEntity
import com.gsrg.data.forecast.remote.ForecastRemote
import com.gsrg.data.forecast.storage.ForecastStorage
import com.gsrg.domain.forecast.model.Forecast
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor(
    private val remote: ForecastRemote,
    private val storage: ForecastStorage,
) : ForecastRepository {

    private val apiKey = "9ceeb31aeb346c6809b713d60d664d55" // TODO move to a proper place

    override suspend fun requestForecast(lat: Double, lon: Double) {
        val forecastDtoList = remote.getForecast(lat = lat, lon = lon, apiKey = apiKey)
        val forecastEntityList = forecastDtoList.map {
            ForecastEntity(
                dateTimeUtc = it.dateTimeUtc,
                temp = it.temperatureDto.temp,
                tempMin = it.temperatureDto.tempMin,
                tempMax = it.temperatureDto.tempMax,
            )
        }
        storage.insertUpdateForecastList(forecastList = forecastEntityList)
    }

    override fun getForecast(): Flow<List<Forecast>> {
        return storage.getForecastList().map { list ->
            list.map {
                Forecast(
                    dateTimeUtc = it.dateTimeUtc,
                    temp = it.temp,
                    tempMin = it.tempMin,
                    tempMax = it.tempMax,
                )
            }
        }
    }

}
