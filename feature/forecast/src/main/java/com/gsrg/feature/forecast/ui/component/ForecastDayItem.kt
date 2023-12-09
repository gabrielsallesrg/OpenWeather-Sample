package com.gsrg.feature.forecast.ui.component

import android.content.res.Configuration
import android.text.format.DateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gsrg.design.theme.OpenWeatherSampleTheme
import com.gsrg.domain.forecast.model.Forecast
import com.gsrg.feature.forecast.R
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.roundToInt
import kotlin.random.Random

@Composable
fun ForecastDayItem(forecastList: List<Forecast>) {
    OpenWeatherSampleTheme {
        Column(modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)) {
            Header(dateTime = forecastList.first().dateTime, modifier = Modifier)
            ForecastTemperature(forecastList = forecastList)
        }
    }
}

@Composable
private fun Header(dateTime: OffsetDateTime, modifier: Modifier) {
    Text(
        text = dateTime.toFormattedDate(),
        modifier = modifier.padding(top = 12.dp, bottom = 4.dp, start = 16.dp, end = 16.dp),
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onBackground,
    )
}

@Composable
private fun ForecastTemperature(forecastList: List<Forecast>) {
    Card(
        modifier = Modifier.padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            forecastList.forEach {
                ForecastTemperatureItem(forecast = it, modifier = Modifier.weight(1F))
            }
        }
    }
}

@Composable
private fun ForecastTemperatureItem(forecast: Forecast, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = forecast.tempMax.formatToTemp(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Text(
            text = forecast.tempMin.formatToTemp(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
private fun Float.formatToTemp(): String {
    return stringResource(id = R.string.format_temp, formatArgs = arrayOf(this.roundToInt().toString()))
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ForecastDayDarModePreview() {
    val forecastList = mutableListOf<Forecast>()
    val random = Random(1)
    for (i in 0..5) {
        forecastList.add(
            Forecast(
                dateTime = OffsetDateTime.now(),
                temp = random.nextFloat() * 100,
                tempMin = random.nextFloat() * 100,
                tempMax = random.nextFloat() * 100,
            )
        )
    }
    OpenWeatherSampleTheme {
        ForecastDayItem(forecastList = forecastList)
    }
}

@Preview(showBackground = true)
@Composable
private fun ForecastDayPreview() {
    val forecastList = mutableListOf<Forecast>()
    val random = Random(1)
    for (i in 0..5) {
        forecastList.add(
            Forecast(
                dateTime = OffsetDateTime.now(),
                temp = random.nextFloat() * 100,
                tempMin = random.nextFloat() * 100,
                tempMax = random.nextFloat() * 100,
            )
        )
    }
    OpenWeatherSampleTheme {
        ForecastDayItem(forecastList = forecastList)
    }
}

@Preview(showBackground = true)
@Composable
private fun ForecastTemperaturePreview() {
    val forecastList = mutableListOf<Forecast>()
    val random = Random(1)
    for (i in 0..5) {
        forecastList.add(
            Forecast(
                dateTime = OffsetDateTime.now(),
                temp = random.nextFloat() * 100,
                tempMin = random.nextFloat() * 100,
                tempMax = random.nextFloat() * 100,
            )
        )
    }
    OpenWeatherSampleTheme {
        ForecastTemperature(forecastList = forecastList)
    }
}

@Preview(showBackground = true)
@Composable
private fun ForecastTemperatureItemPreview() {
    val random = Random(1)
    OpenWeatherSampleTheme {
        ForecastTemperatureItem(
            forecast = Forecast(
                dateTime = OffsetDateTime.now(),
                temp = random.nextFloat() * 100,
                tempMin = random.nextFloat() * 100,
                tempMax = random.nextFloat() * 100,
            ),
        )
    }
}

private fun OffsetDateTime.toFormattedDate(): String {
    return this.format(
        DateTimeFormatter.ofPattern(
            DateFormat.getBestDateTimePattern(
                Locale.getDefault(), "EEEE dd MMMM"
            )
        )
    )
}
