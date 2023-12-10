package com.gsrg.feature.forecast.service

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class LocationServiceImpl @Inject constructor(
    private val context: Context,
): LocationService {
    private val cancellationTokenSource = CancellationTokenSource()

    private val _locationFlow = MutableStateFlow<Location?>(null)
    override fun locationFlow(): Flow<Location?> = _locationFlow

    @SuppressLint("MissingPermission")
    override fun updateCurrentLocation() {
        val priority = Priority.PRIORITY_HIGH_ACCURACY
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        fusedLocationProviderClient.getCurrentLocation(priority, cancellationTokenSource.token)
            .addOnSuccessListener {
                _locationFlow.value = it
            }
    }
}