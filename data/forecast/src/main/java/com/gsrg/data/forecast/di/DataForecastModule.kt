package com.gsrg.data.forecast.di

import com.gsrg.data.forecast.remote.ForecastRemote
import com.gsrg.data.forecast.remote.ForecastRemoteImpl
import com.gsrg.data.forecast.storage.ForecastStorage
import com.gsrg.data.forecast.storage.ForecastStorageImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataForecastModule {

    @Binds
    @Singleton
    abstract fun providesForecastRemote(impl: ForecastRemoteImpl): ForecastRemote

    @Binds
    @Singleton
    abstract fun providesForecastStorage(impl: ForecastStorageImpl): ForecastStorage
}
