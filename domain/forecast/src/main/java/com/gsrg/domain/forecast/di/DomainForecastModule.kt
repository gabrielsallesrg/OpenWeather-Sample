package com.gsrg.domain.forecast.di

import com.gsrg.domain.forecast.helper.Converter
import com.gsrg.domain.forecast.helper.ConverterImpl
import com.gsrg.domain.forecast.repository.ForecastRepository
import com.gsrg.domain.forecast.repository.ForecastRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainForecastModule {

    @Binds
    @Singleton
    abstract fun providesForecastRepository(impl: ForecastRepositoryImpl): ForecastRepository

    @Binds
    @Singleton
    abstract fun providesConverter(impl: ConverterImpl): Converter
}
