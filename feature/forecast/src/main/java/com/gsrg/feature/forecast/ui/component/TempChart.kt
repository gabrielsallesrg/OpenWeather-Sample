package com.gsrg.feature.forecast.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.gsrg.design.theme.OpenWeatherSampleTheme
import kotlin.math.roundToInt


@Composable
fun TempChart(
    modifier: Modifier,
    temperatureList: List<Float>,
) {
    val steps = 6
    Card(
        modifier = modifier
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (canvasRef, textRef) = createRefs()
            Column(
                modifier = Modifier
                    .constrainAs(textRef) {
                        linkTo(top = parent.top, bottom = parent.bottom)
                        end.linkTo(parent.end, margin = 8.dp)
                        this.width = Dimension.wrapContent
                        this.height = Dimension.fillToConstraints
                    },
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                for (i in 0..steps) {
                    val text = calculateTempForString(
                        maxTemp = temperatureList.max(),
                        minTemp = temperatureList.min(),
                        steps = steps,
                        currentStep = i,
                    )
                    Text(
                        text = text,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelSmall,
                    )
                }
            }

            Canvas(
                modifier = Modifier.constrainAs(canvasRef) {
                    linkTo(top = parent.top, bottom = parent.bottom, topMargin = 8.dp, bottomMargin = 8.dp)
                    linkTo(start = parent.start, end = textRef.start, endMargin = 8.dp, startMargin = 8.dp)
                    this.width = Dimension.fillToConstraints
                    this.height = Dimension.fillToConstraints
                },
            ) {
                val divisionPath = createDivisionPath(size.width, size.height, steps = steps)
                drawPath(
                    path = divisionPath,
                    color = Color.LightGray,
                    style = Stroke(width = 4F),
                )

                val path = createLinePath(size.width, size.height, temperatureList = temperatureList)
                drawPath(
                    path = path,
                    color = Color.Blue,
                    style = Stroke(width = 4F),
                )
            }
        }
    }
}

private fun calculateTempForString(maxTemp: Float, minTemp: Float, steps: Int, currentStep: Int): String {
    return ((((maxTemp + 5) - ((maxTemp + 5) - (minTemp - 5)) / steps * currentStep) * 10).roundToInt() / 10F).toString()
}

private fun createDivisionPath(
    width: Float,
    height: Float,
    steps: Int,
): Path {
    return Path().apply {
        for (i in 0..steps) {
            val yPos = height * i / steps
            this.moveTo(x = 0F, y = yPos)
            this.lineTo(x = width, y = yPos)
        }
    }
}

private fun createLinePath(width: Float, height: Float, temperatureList: List<Float>): Path {
    val minTemp = temperatureList.min() - 5
    val maxTemp = temperatureList.max() + 5
    val tempDiff = maxTemp - minTemp

    val position = mutableListOf <Pair<Float, Float>>()

    temperatureList.forEachIndexed { index, temp ->
        val xPos = width * index / temperatureList.lastIndex
        val yPos = height - (temp - minTemp) / tempDiff * height

        position.add(xPos to yPos)
    }

    return Path().apply {
        this.moveTo(x = position[0].first, y = position[0].second)
        for (i in 0..position.lastIndex) {
            this.lineTo(x = position[i].first, y = position[i].second)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TempChartPreview() {
    OpenWeatherSampleTheme {
        TempChart(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp),
            temperatureList = listOf(15.41F, 24F, 15.21F),
        )
    }
}