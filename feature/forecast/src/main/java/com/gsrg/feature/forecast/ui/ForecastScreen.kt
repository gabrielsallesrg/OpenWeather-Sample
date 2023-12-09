package com.gsrg.feature.forecast.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gsrg.domain.forecast.model.Forecast
import com.gsrg.feature.forecast.ui.component.CurrentDay
import com.gsrg.feature.forecast.ui.component.ForecastDayItem
import java.time.OffsetDateTime
import kotlin.random.Random

@Composable
fun ForecastScreenEntry() {
    val viewModel: ForecastViewModel = viewModel()
    val forecastList by viewModel.getForecast().collectAsState(initial = emptyList())

    ForecastScreen(forecastList = forecastList)
}

@Composable
private fun ForecastScreen(forecastList: List<Pair<Int, List<Forecast>>>) {
    val todayForecast = forecastList.firstOrNull()
    val futureForecast = forecastList.toMutableList()
    futureForecast.remove(todayForecast)

    val lazyListState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = lazyListState,
    ) {
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
        forecastList = forecastList
    )
}
