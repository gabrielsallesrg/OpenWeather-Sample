package com.gsrg.data.forecast.storage

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import com.gsrg.data.database.forecast.dao.ForecastDao
import com.gsrg.data.database.forecast.entity.ForecastEntity
import com.gsrg.helper.test.RandomData
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ForecastStorageImplTest {

    private val mockDao: ForecastDao = mockk()

    private val sut = ForecastStorageImpl(dao = mockDao)

    @Before
    fun setup() {
        generalMocks()
    }

    @Test
    fun `when sending a new list do DAO, DAO should replace the old list`() = runBlocking {
        val forecastList = createForecastEntityList()
        sut.insertUpdateForecastList(forecastList = forecastList)

        coVerify(exactly = 1) { mockDao.deleteAllForecast() }
        coVerify(exactly = 1) { mockDao.insertUpdateForecastList(forecastList = forecastList) }
    }

    @Test
    fun `when requesting list from DAO, DAO send the list`() = runBlocking {
        val forecastList = createForecastEntityList()

        coEvery { mockDao.getForecastList() } returns flowOf(forecastList)

        val actual = sut.getForecastList().first()

        assertEquals(forecastList, actual)
    }

    private fun generalMocks() {
        coEvery { mockDao.deleteAllForecast() } returns Unit
        coEvery { mockDao.insertUpdateForecastList(forecastList = any()) } returns Unit
    }

    private fun createForecastEntityList(size: Int = (0..20).random()): List<ForecastEntity> {
        val forecastList = mutableListOf<ForecastEntity>()
        for (i in 0..< size) {
            forecastList.add(createForecastEntity())
        }
        return forecastList
    }

    private fun createForecastEntity(): ForecastEntity {
        return ForecastEntity(
            offsetDateTime = RandomData.offsetDateTime(),
            temp = RandomData.float(),
            tempMin = RandomData.float(),
            tempMax = RandomData.float(),
        )
    }
}
