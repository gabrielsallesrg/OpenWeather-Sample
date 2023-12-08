package com.gsrg.domain.forecast.model

data class Forecast(
    val dateTimeUtc: String,
    val temp: Float,
    val tempMin:Float,
    val tempMax: Float,
)
