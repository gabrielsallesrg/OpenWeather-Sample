package com.gsrg.data.database.forecast.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gsrg.data.database.AppDatabase
import com.gsrg.data.database.forecast.entity.ForecastEntity
import com.gsrg.helper.test.RandomData
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ForecastDaoTest {

    private lateinit var sut: ForecastDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        sut = db.forecastDao()
    }

    @After
    fun closeDb() = db.close()

    @Test
    fun insertForecastEntityList() = runBlocking {
        val expectedList = createForecastEntityList()

        sut.insertUpdateForecastList(forecastList = expectedList)

        val actualList = sut.getForecastList().first()

        assertEquals(expectedList, actualList)
    }

    @Test
    fun deletingFromDatabaseShouldDeleteAll() = runBlocking {
        val originalList = createForecastEntityList()

        sut.insertUpdateForecastList(forecastList = originalList)
        sut.deleteAllForecast()

        val actualList = sut.getForecastList().first()

        assertEquals(emptyList<ForecastEntity>(), actualList)
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
