package com.sofaacademy.sofaminiproject.model

import java.io.Serializable

/**
 * Za home i away score na docsima pise da su object tipa Score,
 * ali kad nema podataka onda je prazna lista, primjer:
 * "homeScore": [], ili
 * "homeScore": {"total": 4,"period1": 2,"period2": 2}, ne moze biti ni score ni lista
 */
data class SportEvent(
    val id: Int,
    val slug: String,
    val tournament: Tournament,
    val homeTeam: Team2,
    val awayTeam: Team2,
    val status: String,
    val startDate: String?,
    val homeScore: Any?,
    val awayScore: Any?,
    val winnerCode: String?,
    val round: Int
) : Serializable
