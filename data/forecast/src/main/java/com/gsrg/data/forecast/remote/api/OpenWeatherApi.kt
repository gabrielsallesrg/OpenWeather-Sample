package com.gsrg.data.forecast.remote.api

import com.gsrg.data.forecast.remote.dto.FiveDaysForecastDto
import retrofit2.http.GET
import retrofit2.http.Query

fun interface OpenWeatherApi {

    @GET("/data/2.5/forecast?units=metric")
    suspend fun getForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("apiKey") apiKey: String,
    ) : FiveDaysForecastDto
}
