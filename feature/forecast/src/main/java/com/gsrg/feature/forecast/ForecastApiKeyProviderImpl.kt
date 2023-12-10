package com.gsrg.feature.forecast

import javax.inject.Inject

class ForecastApiKeyProviderImpl @Inject constructor(): ForecastApiKeyProvider {
    override fun apiKey(): String {
        return BuildConfig.API_KEY
    }
}
