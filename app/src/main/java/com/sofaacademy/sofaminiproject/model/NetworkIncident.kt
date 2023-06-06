package com.sofaacademy.sofaminiproject.model

import java.io.Serializable

class NetworkIncident(
    private val player: Player?,
    private val teamSide: String?,
    private val color: String?,
    private val id: Int?,
    private val time: Int?,
    private val scoringTeam: String?,
    private val homeScore: Int?,
    private val awayScore: Int?,
    private val goalType: String?,
    private val text: String?,
    private val type: String
) : Serializable {

    fun mapIncident() = when (type) {
        IncidentEnum.TYPE_GOAL.incident -> Incident.GoalIncident(
            player,
            scoringTeam,
            homeScore,
            awayScore,
            goalType,
            id,
            time,
            type
        )

        IncidentEnum.TYPE_CARD.incident -> Incident.CardIncident(
            player,
            teamSide,
            color,
            id,
            time,
            type
        )

        IncidentEnum.TYPE_PERIOD.incident -> Incident.PeriodIncident(
            text,
            id,
            time,
            type
        )

        else -> null
    }
}

sealed class Incident(
    var id: Int?,
    val time: Int?,
    val type: String
) : Serializable {

    var shouldReverseTeams = false
    var firstItem = false
    var lastItem = false
    var showDivider = true

    var sport: String? = null

    open fun getPlayerId(): Int? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Incident

        if (id != other.id) return false
        if (time != other.time) return false
        if (type != other.type) return false

        return true
    }

    class CardIncident(
        val player: Player?,
        val teamSide: String?,
        val color: String?,
        id: Int?,
        time: Int?,
        type: String
    ) : Incident(id, time, type) {

        companion object {
            const val CARD_RED = "red"
            const val CARD_YELLOW = "yellow"
            const val CARD_YELLOW_RED = "yellowRed"
        }

        override fun getPlayerId(): Int? = player?.id

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            if (!super.equals(other)) return false

            other as CardIncident

            if (player != other.player) return false

            return true
        }
    }

    class GoalIncident(
        val player: Player?,
        val scoringTeam: String?,
        val homeScore: Int?,
        val awayScore: Int?,
        val goalType: String?,
        id: Int?,
        time: Int?,
        type: String
    ) : Incident(id, time, type) {

        companion object {
            const val TYPE_REGULAR = "regular"
            const val TYPE_OWN_GOAL = "owngoal"
            const val TYPE_PENALTY = "penalty"
            const val TYPE_ONE_POINT = "onePoint"
            const val TYPE_TWO_POINT = "twoPoints"
            const val TYPE_THREE_POINT = "threePoints"
            const val TYPE_TOUCHDOWN = "touchdown"
            const val TYPE_SAFETY = "safety"
            const val TYPE_FIELD_GOAL = "fieldGoal"
            const val TYPE_EXTRA_POINT = "extraPoint"
        }

        override fun getPlayerId(): Int? = player?.id
    }

    class PeriodIncident(
        val text: String?,
        id: Int?,
        time: Int?,
        type: String
    ) : Incident(id, time, type) {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            if (!super.equals(other)) return false

            other as PeriodIncident

            return true
        }
    }
}
