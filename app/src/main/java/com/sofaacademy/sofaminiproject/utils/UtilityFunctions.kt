package com.sofaacademy.sofaminiproject.utils

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

object UtilityFunctions {
    private val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    private val hourFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    val tabDateFormat = DateTimeFormatter.ofPattern("EEE dd.MM.", Locale.US)
    val yearFormat=DateTimeFormatter.ofPattern("yyyy-MM-dd")

    fun getHourFromDate(date: String): String {
        return try {
            val localDateTime = LocalDateTime.parse(date, dateTimeFormatter)
            hourFormatter.format(localDateTime)
        } catch (e: java.lang.Exception) {
            ""
        }
    }

    fun elapsedMinutesFromDate(date: String): String {
        return try {
            val currentDateTime = LocalDateTime.now(ZoneOffset.UTC)
            ChronoUnit.MINUTES.between(LocalDateTime.parse(date), currentDateTime).toString()
        } catch (e: java.lang.Exception) {
            ""
        }
    }

}