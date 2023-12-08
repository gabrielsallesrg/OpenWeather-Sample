package com.gsrg.data.database.di

import android.content.Context
import androidx.room.Room
import com.gsrg.data.database.AppDatabase
import com.gsrg.data.database.forecast.dao.ForecastDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DATABASE_NAME = "open-weather"

    @Provides
    @Singleton
    fun providesDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(context = context, AppDatabase::class.java, DATABASE_NAME)
            .enableMultiInstanceInvalidation()
            .build()
}
