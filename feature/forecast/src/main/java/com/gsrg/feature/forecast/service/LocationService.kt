package com.gsrg.feature.forecast.service

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationService {

    fun locationFlow(): Flow<Location?>
    fun updateCurrentLocation()
}
