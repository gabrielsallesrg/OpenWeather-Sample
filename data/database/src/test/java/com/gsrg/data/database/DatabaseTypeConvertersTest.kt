package com.gsrg.data.database

import com.gsrg.helper.test.RandomData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import java.time.OffsetDateTime
import java.time.ZoneOffset

class DatabaseTypeConvertersTest {

    private lateinit var sut: DatabaseTypeConverters

    @Before
    fun setup() {
        sut = DatabaseTypeConverters()
    }

    @Test
    fun `string should be converted to OffsetDateTime object`() {
        val expected = RandomData.offsetDateTime()

        val dateTimeString = expected.toString()

        assertEquals(expected, sut.toOffsetDateTime(dateTimeString))
    }

    @Test
    fun `null string should return null OffsetDateTime object`() {
        assertNull(sut.toOffsetDateTime(null))
    }

    @Test
    fun `offsetDateTime object should be converted to string correctly`() {
        val offsetDateTime = RandomData.offsetDateTime()

        val expected = offsetDateTime.toString()

        assertEquals(expected, sut.fromOffsetDateTime(offsetDateTime))
    }

    @Test
    fun `null offsetDateTime object should return null`() {
        assertNull(sut.fromOffsetDateTime(null))
    }
}
