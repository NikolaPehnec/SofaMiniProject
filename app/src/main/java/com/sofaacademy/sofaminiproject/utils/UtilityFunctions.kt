package com.sofaacademy.sofaminiproject.utils

import android.content.Context
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import coil.load
import com.google.android.material.tabs.TabLayoutMediator
import com.sofaacademy.sofaminiproject.R
import com.sofaacademy.sofaminiproject.utils.Constants.MAX_DAYS
import com.sofaacademy.sofaminiproject.utils.Constants.MIN_DAYS
import com.sofaacademy.sofaminiproject.utils.helpers.FlagHelper
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.math.roundToInt

object UtilityFunctions {
    private val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    private val hourFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    var tabDateFormat = DateTimeFormatter.ofPattern("EEE dd.MM.", Locale.US)
    val yearFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val dateLongFormat = DateTimeFormatter.ofPattern("EEE, dd.MM.yyyy.", Locale.US)
    val detailDateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy.", Locale.US)

    fun getFormattedDetailDate(date: String): String {
        return try {
            val localDateTime = LocalDateTime.parse(date, dateTimeFormatter)
            detailDateFormat.format(localDateTime)
        } catch (e: java.lang.Exception) {
            ""
        }
    }

    fun getHourFromDate(date: String): String {
        return try {
            val localDateTime = LocalDateTime.parse(date, dateTimeFormatter)
            hourFormatter.format(localDateTime)
        } catch (e: java.lang.Exception) {
            ""
        }
    }

    fun elapsedMinutesBetweenTwoDates(localDate: LocalDateTime, date2: String): Long {
        return try {
            val localDate2 = LocalDateTime.parse(date2, dateTimeFormatter)
            ChronoUnit.MINUTES.between(localDate, localDate2)
        } catch (e: java.lang.Exception) {
            0L
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

    fun getSportsTabLayoutConfigStrategy(context: Context): TabLayoutMediator.TabConfigurationStrategy =
        TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            val icons = arrayOf(
                R.drawable.icon_football,
                R.drawable.icon_basketball,
                R.drawable.icon_american_football
            )
            tab.icon = AppCompatResources.getDrawable(context, icons[position])
            tab.text = context.resources.getStringArray(R.array.tabs)[position]
        }

    fun getTeamDetailsTabLayoutConfigStrategy(context: Context): TabLayoutMediator.TabConfigurationStrategy =
        TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            tab.text = context.resources.getStringArray(R.array.tabs_detail_page)[position]
        }

    fun getTournamentDetailsTabLayoutConfigStrategy(context: Context): TabLayoutMediator.TabConfigurationStrategy =
        TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            tab.text =
                context.resources.getStringArray(R.array.tabs_tournament_detail_page)[position]
        }

    fun getTabTitlesByDate(context: Context): MutableMap<LocalDate, String> {
        val dateTabs = mutableMapOf<LocalDate, String>()
        tabDateFormat = DateTimeFormatter.ofPattern(
            "EEE dd.MM.", context.resources.configuration.locales.get(0)
        )

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

    fun getForeignPlayersPercentIndicator(totalPlayers: Int, foreignPlayers: Int): Int =
        ((foreignPlayers * 1f / totalPlayers) * 100).roundToInt()

    fun ImageView.loadTournamentImg(tournamentId: String) {
        this.load(
            "${Constants.BASE_TOURNAMENT_URL}${tournamentId}${Constants.IMG_ENDPOINT}"
        )
    }

    fun ImageView.loadTeamImg(teamId: String) {
        this.load(
            "${Constants.BASE_TEAM_URL}${teamId}${Constants.IMG_ENDPOINT}"
        )
    }

    fun ImageView.loadPlayerImg(playerId: String) {
        this.load(
            "${Constants.BASE_PLAYER_URL}${playerId}${Constants.IMG_ENDPOINT}"
        )
    }

    fun ImageView.loadCountryFlag(countryName: String, context: Context) {
        this.load(
            FlagHelper.getFlagBitmap(context, countryName)
        )
    }

    fun saveThemePreference(theme: String, context: Context) {
        val sharedPreferences =
            context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(Constants.PREF_THEME_KEY, theme)
        editor.apply()
    }

    fun getThemePreferences(context: Context) =
        context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE).getString(
            Constants.PREF_THEME_KEY,
            Constants.LIGHT_THEME
        )!!

}
