package com.gsrg.data.database.forecast.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forecast")
data class ForecastEntity(
    @PrimaryKey val dateTimeUtc: String,
    val temp: Float,
    val tempMin:Float,
    val tempMax: Float,
)
