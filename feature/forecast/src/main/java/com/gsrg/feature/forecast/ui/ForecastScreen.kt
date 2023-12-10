package com.gsrg.feature.forecast.ui

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.gsrg.domain.forecast.helper.RequestResult
import com.gsrg.domain.forecast.model.Forecast
import com.gsrg.feature.forecast.R
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
        Text(text = stringResource(id = R.string.location_needed))
        Button(onClick = {
            permissionState.launchPermissionRequest()
        }) {
            Text(text = stringResource(id = R.string.request_permission))
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
                Text(
                    text = stringResource(id = R.string.something_went_wrong),
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge,
                )
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
