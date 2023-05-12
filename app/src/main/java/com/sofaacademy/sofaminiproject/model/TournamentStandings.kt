package com.sofaacademy.sofaminiproject.model

data class TournamentStandings(
    val id: Int?,
    val tournament: Tournament?,
    val type: String?,
    val sortedStandingsRows: List<StandingsRow>?
)

data class StandingsRow(
    val id: Int?,
    val team: Team3?,
    val points: Int?,
    val scoresFor: Int?,
    val scoresAgainst: Int?,
    val played: Int?,
    val wins: Int?,
    val draws: Int?,
    val losses: Int?,
    val percentage: Float?
)
