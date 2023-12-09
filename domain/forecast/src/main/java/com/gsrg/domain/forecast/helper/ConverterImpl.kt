package com.gsrg.domain.forecast.helper

import java.time.Clock
import java.time.OffsetDateTime
import javax.inject.Inject

class ConverterImpl  @Inject constructor() : Converter {

    override fun OffsetDateTime.toCurrentTimeZone(): OffsetDateTime =
        this.atZoneSameInstant(Clock.systemDefaultZone().zone).toOffsetDateTime()
}
