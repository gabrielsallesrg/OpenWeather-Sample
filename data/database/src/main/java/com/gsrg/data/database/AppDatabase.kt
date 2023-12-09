package com.gsrg.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gsrg.data.database.forecast.dao.ForecastDao
import com.gsrg.data.database.forecast.entity.ForecastEntity

@Database(
    entities = [
        ForecastEntity::class
    ],
    version = 1, // TODO move this somewhere else
    exportSchema = true,
)

@TypeConverters(DatabaseTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun forecastDao(): ForecastDao
}
