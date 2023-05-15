package com.sofaacademy.sofaminiproject.utils.helpers

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import com.sofaacademy.sofaminiproject.R
import com.sofaacademy.sofaminiproject.model.MatchStatus
import com.sofaacademy.sofaminiproject.model.Score
import com.sofaacademy.sofaminiproject.model.SportEvent
import com.sofaacademy.sofaminiproject.model.Team2
import com.sofaacademy.sofaminiproject.model.Tournament
import com.sofaacademy.sofaminiproject.utils.Constants
import com.sofaacademy.sofaminiproject.utils.UtilityFunctions
import java.io.Serializable
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object EventHelpers {

    fun getTeamColorBasedOnTimeAndResult(
        matchStatus: String,
        teamResult: Score?,
        otherTeamResult: Score?,
        context: Context
    ): Int {
        return when (matchStatus) {
            MatchStatus.FINISHED.status -> {
                if ((teamResult?.total ?: 1) > (otherTeamResult?.total ?: 0)) {
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
        teamResult: Score?,
        otherTeamResult: Score?,
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

    fun getEventFromIntent(intent: Intent): SportEvent {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(
                Constants.EVENT_ID_KEY,
                SportEvent::class.java
            ) as SportEvent
        } else {
            intent.getSerializableExtra(Constants.EVENT_ID_KEY) as SportEvent
        }
    }

    fun getTeamFromIntent(intent: Intent): Team2 {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(
                Constants.TEAM_ID_ARG,
                Team2::class.java
            ) as Team2
        } else {
            intent.getSerializableExtra(Constants.TEAM_ID_ARG) as Team2
        }
    }

    fun getTournamentFromIntent(intent: Intent): Tournament {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(
                Constants.TOURNAMENT_ARG,
                Tournament::class.java
            ) as Tournament
        } else {
            intent.getSerializableExtra(Constants.TOURNAMENT_ARG) as Tournament
        }
    }

    fun Bundle.getTeam(): Team2 {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getSerializable(Constants.TEAM_ID_ARG, Team2::class.java) as Team2
        } else {
            getSerializable(Constants.TEAM_ID_ARG) as Team2
        }
    }

    fun Bundle.getTournament(): Tournament {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getSerializable(Constants.TOURNAMENT_ARG, Tournament::class.java) as Tournament
        } else {
            getSerializable(Constants.TOURNAMENT_ARG) as Tournament
        }
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
                UtilityFunctions.elapsedMinutesFromDate(matchStartTime) + "'"
            }

            else -> ""
        }
    }

    fun List<SportEvent>.sortedByDate(): List<SportEvent> {
        return sortedBy { e ->
            ZonedDateTime.parse(e.startDate!!, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        }
    }

    fun List<SportEvent>.sortedByDateDesc(): List<SportEvent> {
        return sortedByDescending { e ->
            ZonedDateTime.parse(e.startDate!!, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        }
    }

    fun groupEventsByTournamentAndDate(events: List<SportEvent>): MutableList<Serializable> {
        var finished = mutableListOf<Serializable>()

        // Specific grouping and ordering as seen in sofascore
        for (i in events.indices) {
            if (i == 0) {
                finished.add(events[i].tournament)
                finished.add(events[i])
            } else {
                if (events[i - 1].tournament == events[i].tournament) {
                    finished.add(events[i])
                } else {
                    finished.add(events[i].tournament)
                    finished.add(events[i])
                }
            }
        }
        return finished
    }
}
