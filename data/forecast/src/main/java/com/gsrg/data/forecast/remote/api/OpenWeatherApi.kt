package com.gsrg.data.forecast.remote.api

import co.infinum.retromock.meta.Mock
import co.infinum.retromock.meta.MockResponse
import com.gsrg.data.forecast.remote.dto.FiveDaysForecastDto
import retrofit2.http.GET
import retrofit2.http.Query

fun interface OpenWeatherApi {

    @Mock
    @MockResponse(body = "mock_get_forecast.json")
    @GET("/data/2.5/forecast?units=metric")
    suspend fun getForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("apiKey") apiKey: String,
    ) : FiveDaysForecastDto
}
