package com.gsrg.feature.forecast.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gsrg.domain.forecast.model.Forecast
import com.gsrg.feature.forecast.R
import java.time.OffsetDateTime
import kotlin.math.roundToInt
import kotlin.random.Random

@Composable
fun CurrentDay(
    forecastList: List<Forecast>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.then(Modifier.fillMaxWidth()),
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "It is now",
                style = MaterialTheme.typography.headlineSmall,
            )
            forecastList.firstOrNull()?.temp?.let {
                val currentTemperature = ((it * 10).roundToInt() / 10F).toString()
                Text(
                    text = stringResource(id = R.string.format_temp, formatArgs = arrayOf(currentTemperature)),
                    style = MaterialTheme.typography.displayLarge,
                )
            }
        }
        if (forecastList.size > 1) {
            TempChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp),
                temperatureList = forecastList.map { it.temp },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CurrentDayPreview() {
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
    CurrentDay(forecastList = forecastList)
}
