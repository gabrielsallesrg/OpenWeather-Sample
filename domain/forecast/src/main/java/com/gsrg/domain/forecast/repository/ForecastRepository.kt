package com.gsrg.domain.forecast.repository

import com.gsrg.domain.forecast.helper.RequestResult
import com.gsrg.domain.forecast.model.Forecast
import kotlinx.coroutines.flow.Flow

interface ForecastRepository {

    suspend fun requestForecast(lat: Double, lon: Double,  apiKey: String): RequestResult<Any>

    fun getForecast(): Flow<List<Forecast>>
}
