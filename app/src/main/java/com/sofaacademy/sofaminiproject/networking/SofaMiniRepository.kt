package com.sofaacademy.sofaminiproject.networking

import android.content.Context
import com.sofaacademy.sofaminiproject.model.*
import javax.inject.Inject

open class SofaMiniRepository @Inject constructor(
    val context: Context,
    private val sofaMiniApi: SofaMiniApi
) : BasicRepository() {

    suspend fun getSportEvents(slug: String, date: String): List<SportEvent> {
        return when (val res = apiCall(call = { sofaMiniApi.getSportEvents(slug, date) })) {
            is Result.Success -> return res.data
            else -> emptyList()
        }
    }

    suspend fun getTournaments(slug: String): List<Tournament> {
        return when (val res = apiCall(call = { sofaMiniApi.getTournaments(slug) })) {
            is Result.Success -> return res.data
            else -> emptyList()
        }
    }

    suspend fun getEventDetails(eventId: String): SportEvent? {
        return when (val res = apiCall(call = { sofaMiniApi.getEventDetail(eventId) })) {
            is Result.Success -> return res.data
            else -> null
        }
    }

    suspend fun getEventIncidents(eventId: String): List<NetworkIncident> {
        return when (val res = apiCall(call = { sofaMiniApi.getEventIncidents(eventId) })) {
            is Result.Success -> return res.data
            else -> emptyList()
        }
    }

    suspend fun getTeamDetails(teamId: String): Team2? {
        return when (val res = apiCall(call = { sofaMiniApi.getTeamDetails(teamId) })) {
            is Result.Success -> return res.data
            else -> null
        }
    }

    suspend fun getTeamPlayers(teamId: String): List<Player> {
        return when (val res = apiCall(call = { sofaMiniApi.getTeamPlayers(teamId) })) {
            is Result.Success -> return res.data
            else -> emptyList()
        }
    }

    suspend fun getTeamEvents(
        teamId: String,
        span: String,
        page: String
    ): List<SportEvent> {
        return when (val res = apiCall(call = { sofaMiniApi.getTeamEvents(teamId, span, page) })) {
            is Result.Success -> return res.data
            else -> emptyList()
        }
    }

    suspend fun getTournamentEvents(
        tournamentId: String,
        span: String,
        page: String
    ): List<SportEvent> {
        return when (
            val res =
                apiCall(call = { sofaMiniApi.getTournamentEvents(tournamentId, span, page) })
        ) {
            is Result.Success -> return res.data
            else -> emptyList()
        }
    }

    suspend fun getPlayerEvents(
        playerId: String,
        span: String,
        page: String
    ): List<SportEvent> {
        return when (
            val res =
                apiCall(call = { sofaMiniApi.getPlayerEvents(playerId, span, page) })
        ) {
            is Result.Success -> return res.data
            else -> emptyList()
        }
    }

    suspend fun getTeamTournaments(teamId: String): List<Tournament> {
        return when (val res = apiCall(call = { sofaMiniApi.getTeamTournaments(teamId) })) {
            is Result.Success -> return res.data
            else -> emptyList()
        }
    }

    suspend fun getTournamentStandings(tournamentId: String): List<TournamentStandings> {
        return when (
            val res =
                apiCall(call = { sofaMiniApi.getTournamentStandings(tournamentId) })
        ) {
            is Result.Success -> return res.data
            else -> emptyList()
        }
    }

    suspend fun searchTeams(query: String): List<Team2> {
        return when (val res = apiCall(call = { sofaMiniApi.searchTeams(query) })) {
            is Result.Success -> return res.data
            else -> emptyList()
        }
    }

    suspend fun searchPlayers(query: String): List<Player> {
        return when (val res = apiCall(call = { sofaMiniApi.searchPlayers(query) })) {
            is Result.Success -> return res.data
            else -> emptyList()
        }
    }
}
