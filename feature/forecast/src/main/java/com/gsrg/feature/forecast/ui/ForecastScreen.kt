package com.gsrg.feature.forecast.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import android.location.LocationRequest
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.gsrg.domain.forecast.helper.RequestResult
import com.gsrg.domain.forecast.model.Forecast
import com.gsrg.feature.forecast.ui.component.CurrentDay
import com.gsrg.feature.forecast.ui.component.ForecastDayItem
import java.time.OffsetDateTime
import kotlin.random.Random

@SuppressLint("MissingPermission")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ForecastScreenEntry() {
    val viewModel: ForecastViewModel = viewModel()
    val forecastList by viewModel.getForecast().collectAsState(initial = emptyList())
    val requestStatus by viewModel.requestStatus().collectAsState(initial = null)

    val locationPermissionState = rememberPermissionState(permission = Manifest.permission.ACCESS_COARSE_LOCATION)

    if (locationPermissionState.status.isGranted) {
        ForecastScreen(
            forecastList = forecastList,
            requestError = requestStatus is RequestResult.Error,
        )
    } else {
        RequestLocationPermission(permissionState = locationPermissionState)
    }
    LaunchedEffect(locationPermissionState.status.isGranted) {
        if (locationPermissionState.status.isGranted) {
            viewModel.requestForecast()
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun RequestLocationPermission(
    permissionState: PermissionState,
) {
    Column {
        val textToShow = if (permissionState.status.shouldShowRationale) {
            "Rationale permission to text" // TODO move to strings.xml
        } else {
            "Location is needed" // TODO move to strings.xml
        }
        Text(text = textToShow)
        Button(onClick = {
            permissionState.launchPermissionRequest()
        }) {
            Text(text = "Request permission") // TODO move to strings.xml
        }
    }
}

@Composable
private fun ForecastScreen(
    forecastList: List<Pair<Int, List<Forecast>>>,
    requestError: Boolean,
) {
    val todayForecast = forecastList.firstOrNull()
    val futureForecast = forecastList.toMutableList()
    futureForecast.remove(todayForecast)

    val lazyListState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = lazyListState,
    ) {
        if (requestError) {
            item {
                Text(text = "Something is wrong. Please check your internet connection and reopen the app")
            }
        }
        item {
            CurrentDay(forecastList = todayForecast?.second ?: emptyList())
        }
        items(futureForecast) {
            ForecastDayItem(forecastList = it.second)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ForecastScreenPreview() {
    val random = Random(1)
    val forecastList = mutableListOf<Pair<Int, List<Forecast>>>()
    for (i in 0..5) {
        val dateTime = OffsetDateTime.now().plusDays(i.toLong())
        val forecastMutableList = mutableListOf<Forecast>()
        for (j in 0..5) {
            forecastMutableList.add(
                Forecast(
                    dateTime = dateTime,
                    temp = random.nextFloat() * 100,
                    tempMin = random.nextFloat() * 100,
                    tempMax = random.nextFloat() * 100,
                )
            )
        }
        forecastList.add(dateTime.dayOfMonth to forecastMutableList)
    }

    ForecastScreen(
        forecastList = forecastList,
        requestError = true,
    )
}
