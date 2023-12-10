package com.gsrg.feature.forecast.ui

import android.location.Location
import android.os.Looper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gsrg.domain.forecast.model.Forecast
import com.gsrg.domain.forecast.repository.ForecastRepository
import com.gsrg.feature.forecast.ForecastApiKeyProvider
import com.gsrg.feature.forecast.service.LocationService
import com.gsrg.helper.test.RandomData
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class ForecastViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val apiKey = RandomData.string()
    private val latitude = RandomData.double()
    private val longitude = RandomData.double()

    private val mockkRepository: ForecastRepository = mockk()
    private val mockkLocationService: LocationService = mockk()
    private val mockkForecastApiKeyProvider: ForecastApiKeyProvider = mockk()

    private lateinit var sut: ForecastViewModel

    @ExperimentalCoroutinesApi
    private val standardTestDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        Dispatchers.setMain(standardTestDispatcher)
        mockkStatic(Looper::class)
        val looper = mockk<Looper> {
            every { thread } returns Thread.currentThread()
        }
        every { Looper.getMainLooper() } returns looper

        generalMocks()
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `after initializing the viewModel, it should request Forecast when location is NOT null`() = runTest {
        val location: Location = mockk()
        every { location.latitude } returns latitude
        every { location.longitude } returns longitude

        coEvery { mockkLocationService.locationFlow() } returns flowOf(location)

        startViewModel()

        coVerify(exactly = 1) { mockkRepository.requestForecast(lat = latitude, lon = longitude, apiKey = apiKey)}
    }

    @Test
    fun `after initializing the viewModel, it should NOT request Forecast when location is null`() = runTest {
        coVerify(exactly = 0) { mockkRepository.requestForecast(lat = any(), lon = any(), apiKey = any())}
    }

    @Test
    fun `when requesting forecast, it will ask for the current location`() = runTest {
        startViewModel()

        coEvery { mockkLocationService.updateCurrentLocation() } returns Unit

        startViewModel()
        sut.requestForecast()

        coVerify(exactly = 1) { mockkLocationService.updateCurrentLocation() }
    }

    @Test
    fun `getting forecast should be grouped and sorted by`() = runTest {
        val year = RandomData.int(from = 2000, until = 2100)
        val month = RandomData.int(from = 1, until = 12)

        val repositoryList = listOf(
            createForecast(year = year, month = month, dayOfMonth = 2, hour = 6),
            createForecast(year = year, month = month, dayOfMonth = 3, hour = 5),
            createForecast(year = year, month = month, dayOfMonth = 2, hour = 4),
            createForecast(year = year, month = month, dayOfMonth = 1, hour = 3),
            createForecast(year = year, month = month, dayOfMonth = 4, hour = 2),
            createForecast(year = year, month = month, dayOfMonth = 1, hour = 1),
        )

        val expected = listOf(
            1 to listOf(repositoryList[5], repositoryList[3]),
            2 to listOf(repositoryList[2], repositoryList[0]),
            3 to listOf(repositoryList[1]),
            4 to listOf(repositoryList[4]),
        )

        coEvery { mockkRepository.getForecast() } returns flowOf(repositoryList)

        startViewModel()

        val actual = sut.getForecast().first()

        assertEquals(expected, actual)
    }

    private fun startViewModel() {
        sut = ForecastViewModel(
            repository = mockkRepository,
            locationService = mockkLocationService,
            forecastApiKeyProvider = mockkForecastApiKeyProvider,
        )
    }

    private fun generalMocks() {
        coEvery { mockkRepository.requestForecast(lat = any(), lon = any(), apiKey = any()) } returns Unit
        coEvery { mockkLocationService.locationFlow() } returns flowOf(null)
        every { mockkForecastApiKeyProvider.apiKey() } returns apiKey
    }

    private fun createForecast(
        year: Int,
        month: Int,
        dayOfMonth: Int,
        hour: Int,
    ) = Forecast(
        dateTime = RandomData.offsetDateTime(randomOffset = false)
            .withYear(year)
            .withMonth(month)
            .withDayOfMonth(dayOfMonth)
            .withHour(hour),
        temp = RandomData.float(),
        tempMin = RandomData.float(),
        tempMax = RandomData.float(),
    )
}
