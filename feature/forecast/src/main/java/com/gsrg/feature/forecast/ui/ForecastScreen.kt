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

@Composable
fun ForecastScreen() {
    val viewModel: ForecastViewModel = viewModel()
    val forecastList by viewModel.getForecast().collectAsState(initial = emptyList())

    val lazyListState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = lazyListState,
    ) {
        items(forecastList) {
            Text(text = it.toString())
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ForecastScreenPreview() {
    ForecastScreen()
}
