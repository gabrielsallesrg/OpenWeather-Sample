package com.gsrg.data.forecast.remote

import com.gsrg.data.forecast.remote.dto.ForecastDto

fun interface ForecastRemote {

    suspend fun getForecast(lat: Double, lon: Double, apiKey: String) : List<ForecastDto>
}
