package com.gsrg.helper.test

import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import kotlin.random.Random

object RandomData {

    private val random = Random.Default

    fun int(from: Int = Int.MIN_VALUE, until: Int = Int.MAX_VALUE) = random.nextInt(from = from, until = until)

    fun float() = random.nextFloat()

    fun double() = random.nextDouble()

    /**
     * This function was mostly created by ChatGPT
     */
    fun string(length: Int = 10): String {
        val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..length)
            .map { Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
    }

    /**
     * This function was created by ChatGPT
     */
    fun offsetDateTime(randomOffset: Boolean = true): OffsetDateTime {
        val now = OffsetDateTime.now()
        val randomDays = random.nextLong(365 * 10) // Random number of days (up to 10 years)
        val randomHours = random.nextLong(24)
        val randomMinutes = random.nextLong(60)
        val randomSeconds = random.nextLong(60)

        val randomOffsetDateTime = now
            .plus(randomDays, ChronoUnit.DAYS)
            .plus(randomHours, ChronoUnit.HOURS)
            .plus(randomMinutes, ChronoUnit.MINUTES)
            .plus(randomSeconds, ChronoUnit.SECONDS)

        return if (randomOffset) {
            // Randomly set the time zone offset
            val randomTimeZoneOffset = ZoneOffset.ofTotalSeconds(Random.nextInt(-18, 18) * 3600)

            randomOffsetDateTime.withOffsetSameInstant(randomTimeZoneOffset)
        } else {
            randomOffsetDateTime
        }
    }
}
