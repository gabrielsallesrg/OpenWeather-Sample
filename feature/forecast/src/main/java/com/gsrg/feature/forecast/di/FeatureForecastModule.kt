package com.gsrg.feature.forecast.di

import com.gsrg.feature.forecast.ForecastApiKeyProvider
import com.gsrg.feature.forecast.ForecastApiKeyProviderImpl
import com.gsrg.feature.forecast.service.LocationService
import com.gsrg.feature.forecast.service.LocationServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class FeatureForecastModule {

    @Binds
    abstract fun providesLocationService(impl: LocationServiceImpl): LocationService

    @Binds
    abstract fun bindsForecastApiKeyProvider(impl: ForecastApiKeyProviderImpl): ForecastApiKeyProvider
}
