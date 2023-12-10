package com.gsrg.data.common.di

import com.gsrg.data.common.MockApiProvider
import com.gsrg.data.common.MockApiProviderImpl
import com.gsrg.data.common.remote.adapter.RetrofitAdapter
import com.gsrg.data.common.remote.adapter.RetrofitAdapterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataCommonModule {

    @Binds
    @Singleton
    abstract fun providesRetrofitAdapter(impl: RetrofitAdapterImpl): RetrofitAdapter

    @Binds
    @Singleton
    abstract fun providesMockApiProvider(impl: MockApiProviderImpl): MockApiProvider
}
