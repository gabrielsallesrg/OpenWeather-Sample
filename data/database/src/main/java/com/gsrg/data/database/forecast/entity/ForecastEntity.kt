package com.gsrg.data.database.forecast.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity(tableName = "forecast")
data class ForecastEntity(
    @PrimaryKey val offsetDateTime: OffsetDateTime,
    val temp: Float,
    val tempMin:Float,
    val tempMax: Float,
)
