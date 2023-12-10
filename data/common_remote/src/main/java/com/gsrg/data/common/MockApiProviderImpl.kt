package com.gsrg.data.common

import javax.inject.Inject

class MockApiProviderImpl @Inject constructor(): MockApiProvider {

    override fun useMockApi(): Boolean = BuildConfig.MOCK_API
}
