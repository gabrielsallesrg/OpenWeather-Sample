package com.gsrg.data.forecast.remote.dto

import com.google.gson.annotations.SerializedName

data class FiveDaysForecastDto(
    @SerializedName("list") val forecastList: List<ForecastDto>
)
