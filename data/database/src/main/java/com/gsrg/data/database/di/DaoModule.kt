package com.gsrg.data.database.di

import com.gsrg.data.database.AppDatabase
import com.gsrg.data.database.forecast.dao.ForecastDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    fun providesForecastDao(appDatabase: AppDatabase): ForecastDao = appDatabase.forecastDao()
}