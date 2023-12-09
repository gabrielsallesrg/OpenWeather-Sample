package com.gsrg.data.database

import androidx.room.TypeConverter
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class DatabaseTypeConverters {

    @TypeConverter
    fun toOffsetDateTime(value: String?): OffsetDateTime? {
        return value?.let {
            return OffsetDateTime.parse(it, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        }
    }

    @TypeConverter
    fun fromOffsetDateTime(dateTime: OffsetDateTime?): String {
        return dateTime.toString()
    }
}
