package com.gsrg.domain.forecast.model

import java.time.OffsetDateTime

data class Forecast(
    val dateTime: OffsetDateTime,
    val temp: Float,
    val tempMin:Float,
    val tempMax: Float,
)
