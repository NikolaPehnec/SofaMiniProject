package com.sofaacademy.sofaminiproject.utils

import android.content.Context
import androidx.appcompat.content.res.AppCompatResources
import com.google.android.material.tabs.TabLayoutMediator
import com.sofaacademy.sofaminiproject.R
import com.sofaacademy.sofaminiproject.model.MatchStatus
import com.sofaacademy.sofaminiproject.utils.Constants.MAX_DAYS
import com.sofaacademy.sofaminiproject.utils.Constants.MIN_DAYS
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

object UtilityFunctions {
    private val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    private val hourFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    val tabDateFormat = DateTimeFormatter.ofPattern("EEE dd.MM.", Locale.US)
    val yearFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val dateLongFormat = DateTimeFormatter.ofPattern("EEE, dd.MM.yyyy.", Locale.US)

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

    fun getTabLayoutConfigStrategy(context: Context): TabLayoutMediator.TabConfigurationStrategy =
        TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            val icons = arrayOf(
                R.drawable.icon_football,
                R.drawable.icon_basketball,
                R.drawable.icon_american_football
            )
            tab.icon = AppCompatResources.getDrawable(context, icons[position])
            tab.text = context.resources.getStringArray(R.array.tabs)[position]
        }

    fun getTabTitlesByDate(context: Context): MutableMap<LocalDate, String> {
        val dateTabs = mutableMapOf<LocalDate, String>()

        for (i in MIN_DAYS..MAX_DAYS) {
            val date = LocalDate.now().plusDays(i.toLong())
            val day =
                if (date == LocalDate.now()) {
                    context.resources.getString(R.string.today)
                } else {
                    tabDateFormat.format(
                        date
                    ).uppercase().split(" ")[0]
                }

            val dateStr = day + "\n" + tabDateFormat.format(date).split(" ")[1]
            dateTabs[date] = dateStr
        }
        return dateTabs
    }

    /**
     * Za home i away score na docsima pise da su object tipa Score,
     * ali kad nema podataka onda je prazna lista, primjer:
     * "homeScore": [], ili
     * "homeScore": {"total": 4,"period1": 2,"period2": 2}, ne moze biti ni score ni lista scorea
     */
    fun getCurrentMatchStatus(status: String, matchStartTime: String): String {
        return when (status) {
            MatchStatus.NOT_STARTED.status -> "-"
            MatchStatus.FINISHED.status -> "FT"
            MatchStatus.IN_PROGRESS.status -> {
                elapsedMinutesFromDate(matchStartTime) + "'"
            }

            else -> ""
        }
    }

    fun getResultValue(score: Any?): String {
        return when (score) {
            is Map<*, *> -> {
                (score["total"] as? Double)?.toInt().toString()
            }

            else -> ""
        }
    }

    fun getTeamColorBasedOnTimeAndResult(
        matchStatus: String,
        teamResult: String,
        otherTeamResult: String,
        context: Context
    ): Int {
        return when (matchStatus) {
            MatchStatus.FINISHED.status -> {
                if (teamResult.toInt() > otherTeamResult.toInt()) {
                    context.getColor(R.color.on_surface_on_surface_lv_1)
                } else {
                    context.getColor(R.color.on_surface_on_surface_lv_2)
                }
            }

            else -> context.getColor(R.color.on_surface_on_surface_lv_1)
        }
    }

    fun getCurrentStatusColor(
        matchStatus: String,
        context: Context
    ): Int {
        return when (matchStatus) {
            MatchStatus.IN_PROGRESS.status -> context.getColor(R.color.specific_live)
            else -> context.getColor(R.color.on_surface_on_surface_lv_2)
        }
    }

    fun getTeamScoreColorBasedOnTimeAndResult(
        matchStatus: String,
        teamResult: String,
        otherTeamResult: String,
        context: Context
    ): Int {
        return when (matchStatus) {
            MatchStatus.FINISHED.status -> {
                getTeamColorBasedOnTimeAndResult(matchStatus, teamResult, otherTeamResult, context)
            }

            MatchStatus.IN_PROGRESS.status -> context.getColor(R.color.specific_live)
            else -> context.getColor(R.color.on_surface_on_surface_lv_1)
        }
    }
}
