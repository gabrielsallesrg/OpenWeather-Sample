package com.gsrg.data.forecast.remote.dto

import com.google.gson.annotations.SerializedName
import java.time.OffsetDateTime

data class ForecastDto(
    @SerializedName("main") val temperatureDto: TemperatureDto,
    @SerializedName("dt_txt") val dateTimeUtc: OffsetDateTime,
)
