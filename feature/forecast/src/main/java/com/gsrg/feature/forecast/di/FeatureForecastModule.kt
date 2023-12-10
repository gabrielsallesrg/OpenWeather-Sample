package com.gsrg.feature.forecast.di

import com.gsrg.feature.forecast.service.LocationService
import com.gsrg.feature.forecast.service.LocationServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class FeatureForecastModule {

    @Binds
    abstract fun providesLocationService(impl: LocationServiceImpl): LocationService
}
