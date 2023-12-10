package com.gsrg.openweather.di

import android.app.Application
import android.content.Context
import com.gsrg.feature.forecast.ForecastApiKeyProvider
import com.gsrg.feature.forecast.ForecastApiKeyProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindContext(application: Application): Context
}
