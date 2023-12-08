package com.gsrg.data.forecast.remote.dto

import com.google.gson.annotations.SerializedName

data class TemperatureDto(
    val temp: Float,
    @SerializedName("temp_min") val tempMin: Float,
    @SerializedName("temp_max") val tempMax: Float,
)
