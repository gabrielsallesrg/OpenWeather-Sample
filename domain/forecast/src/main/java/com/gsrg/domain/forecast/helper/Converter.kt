package com.gsrg.domain.forecast.helper

import java.time.OffsetDateTime

fun interface Converter {

    fun OffsetDateTime.toCurrentTimeZone(): OffsetDateTime
}
