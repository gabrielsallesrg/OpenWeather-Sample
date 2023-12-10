package com.gsrg.domain.forecast.repository

import com.gsrg.data.database.forecast.entity.ForecastEntity
import com.gsrg.data.forecast.remote.ForecastRemote
import com.gsrg.data.forecast.remote.dto.ForecastDto
import com.gsrg.data.forecast.remote.dto.TemperatureDto
import com.gsrg.data.forecast.storage.ForecastStorage
import com.gsrg.domain.forecast.helper.Converter
import com.gsrg.domain.forecast.model.Forecast
import com.gsrg.helper.test.RandomData
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ForecastRepositoryImplTest {

    private val mockRemote: ForecastRemote = mockk()
    private val mockStorage: ForecastStorage = mockk()
    private val mockConverter: Converter = Converter { this }

    private val sut = ForecastRepositoryImpl(
        remote = mockRemote,
        storage = mockStorage,
        converter = mockConverter,
    )

    @Before
    fun setup() {
        generalMocks()
    }

    @Test
    fun `when requesting from remote, should store in storage`() = runBlocking {
        val lat = RandomData.double()
        val lon = RandomData.double()
        val apiKey = RandomData.string()

        val offsetDateTime_00 = RandomData.offsetDateTime()
        val temp_00 = RandomData.float()
        val tempMin_00 = RandomData.float()
        val tempMax_00 = RandomData.float()

        val offsetDateTime_01 = RandomData.offsetDateTime()
        val temp_01 = RandomData.float()
        val tempMin_01 = RandomData.float()
        val tempMax_01 = RandomData.float()

        val dtoList = listOf(
            ForecastDto(
                dateTimeUtc = offsetDateTime_00,
                temperatureDto = TemperatureDto(
                    temp = temp_00,
                    tempMin = tempMin_00,
                    tempMax = tempMax_00,
                ),
            ),
            ForecastDto(
                dateTimeUtc = offsetDateTime_01,
                temperatureDto = TemperatureDto(
                    temp = temp_01,
                    tempMin = tempMin_01,
                    tempMax = tempMax_01,
                ),
            ),
        )

        val expectedEntityList = listOf(
            ForecastEntity(
                offsetDateTime = offsetDateTime_00,
                temp = temp_00,
                tempMin = tempMin_00,
                tempMax = tempMax_00,
            ),
            ForecastEntity(
                offsetDateTime = offsetDateTime_01,
                temp = temp_01,
                tempMin = tempMin_01,
                tempMax = tempMax_01,
            ),
        )

        coEvery { mockRemote.getForecast(lat = lat, lon = lon, apiKey = apiKey) } returns dtoList

        sut.requestForecast(lat = lat, lon = lon, apiKey = apiKey)

        coVerify(exactly = 1) { mockRemote.getForecast(lat = lat, lon = lon, apiKey = apiKey) }
        coVerify(exactly = 1) { mockStorage.insertUpdateForecastList(forecastList = expectedEntityList) }
    }

    @Test
    fun `getting forecastList should get from storage and be mapped`() = runBlocking {
        val offsetDateTime_00 = RandomData.offsetDateTime()
        val temp_00 = RandomData.float()
        val tempMin_00 = RandomData.float()
        val tempMax_00 = RandomData.float()

        val offsetDateTime_01 = RandomData.offsetDateTime()
        val temp_01 = RandomData.float()
        val tempMin_01 = RandomData.float()
        val tempMax_01 = RandomData.float()

        val entityList = listOf(
            ForecastEntity(
                offsetDateTime = offsetDateTime_00,
                temp = temp_00,
                tempMin = tempMin_00,
                tempMax = tempMax_00,
            ),
            ForecastEntity(
                offsetDateTime = offsetDateTime_01,
                temp = temp_01,
                tempMin = tempMin_01,
                tempMax = tempMax_01,
            ),
        )

        val expected = listOf(
            Forecast(
                dateTime = offsetDateTime_00,
                temp = temp_00,
                tempMin = tempMin_00,
                tempMax = tempMax_00,
            ),
            Forecast(
                dateTime = offsetDateTime_01,
                temp = temp_01,
                tempMin = tempMin_01,
                tempMax = tempMax_01,
            ),
        )

        coEvery { mockStorage.getForecastList() } returns flowOf(entityList)

        val actual = sut.getForecast().first()

        assertEquals(expected, actual)
    }

    private fun generalMocks() {
        coEvery { mockStorage.insertUpdateForecastList(forecastList = any()) } returns Unit
    }
}
