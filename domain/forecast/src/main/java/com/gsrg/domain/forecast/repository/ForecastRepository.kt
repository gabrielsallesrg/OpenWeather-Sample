package com.gsrg.domain.forecast.repository

import com.gsrg.domain.forecast.model.Forecast
import kotlinx.coroutines.flow.Flow

interface ForecastRepository {

    suspend fun requestForecast(lat: Double, lon: Double,  apiKey: String)

    fun getForecast(): Flow<List<Forecast>>
}
