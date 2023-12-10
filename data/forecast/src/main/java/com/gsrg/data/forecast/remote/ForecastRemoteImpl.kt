package com.gsrg.data.forecast.remote

import com.gsrg.data.common.remote.adapter.RetrofitAdapter
import com.gsrg.data.forecast.remote.api.OpenWeatherApi
import com.gsrg.data.forecast.remote.dto.ForecastDto
import javax.inject.Inject

class ForecastRemoteImpl @Inject constructor(
    private val retrofitAdapter: RetrofitAdapter,
): ForecastRemote {

    private val apiClient: OpenWeatherApi by lazy {
        retrofitAdapter.getRetrofitInterface(api = OpenWeatherApi::class.java)
    }

    override suspend fun getForecast(lat: Double, lon: Double, apiKey: String): List<ForecastDto> {
        return apiClient.getForecast(lat = lat, lon = lon, apiKey = apiKey).forecastList
    }
}
