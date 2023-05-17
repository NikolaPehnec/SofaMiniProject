package com.sofaacademy.sofaminiproject.networking

import android.content.Context
import com.sofaacademy.sofaminiproject.model.NetworkIncident
import com.sofaacademy.sofaminiproject.model.Player
import com.sofaacademy.sofaminiproject.model.Result
import com.sofaacademy.sofaminiproject.model.SearchPlayer
import com.sofaacademy.sofaminiproject.model.SearchTeam
import com.sofaacademy.sofaminiproject.model.SportEvent
import com.sofaacademy.sofaminiproject.model.Team2
import com.sofaacademy.sofaminiproject.model.Tournament
import com.sofaacademy.sofaminiproject.model.TournamentStandings
import javax.inject.Inject

open class SofaMiniRepository @Inject constructor(
    val context: Context,
    private val sofaMiniApi: SofaMiniApi
) : BasicRepository() {

    suspend fun getSportEvents(slug: String, date: String): Result<List<SportEvent>> =
        apiCall(call = { sofaMiniApi.getSportEvents(slug, date) })

    suspend fun getTournaments(slug: String): Result<List<Tournament>> =
        apiCall(call = { sofaMiniApi.getTournaments(slug) })

    suspend fun getEventDetails(eventId: String): Result<SportEvent> =
        apiCall(call = { sofaMiniApi.getEventDetail(eventId) })

    suspend fun getEventIncidents(eventId: String): Result<List<NetworkIncident>> =
        apiCall(call = { sofaMiniApi.getEventIncidents(eventId) })

    suspend fun getTeamDetails(teamId: String): Result<Team2> =
        apiCall(call = { sofaMiniApi.getTeamDetails(teamId) })

    suspend fun getTeamPlayers(teamId: String): Result<List<Player>> =
        apiCall(call = { sofaMiniApi.getTeamPlayers(teamId) })

    suspend fun getTeamEvents(
        teamId: String,
        span: String,
        page: String
    ): Result<List<SportEvent>> =
        apiCall(call = { sofaMiniApi.getTeamEvents(teamId, span, page) })

    suspend fun getTournamentEvents(
        tournamentId: String,
        span: String,
        page: String
    ): Result<List<SportEvent>> =
        apiCall(call = { sofaMiniApi.getTournamentEvents(tournamentId, span, page) })

    suspend fun getPlayerEvents(
        playerId: String,
        span: String,
        page: String
    ): Result<List<SportEvent>> =
        apiCall(call = { sofaMiniApi.getPlayerEvents(playerId, span, page) })

    suspend fun getTeamTournaments(teamId: String): Result<List<Tournament>> =
        apiCall(call = { sofaMiniApi.getTeamTournaments(teamId) })

    suspend fun getTournamentStandings(tournamentId: String): Result<List<TournamentStandings>> =
        apiCall(call = { sofaMiniApi.getTournamentStandings(tournamentId) })

    suspend fun searchTeams(query: String): Result<List<SearchTeam>> =
        apiCall(call = { sofaMiniApi.searchTeams(query) })

    suspend fun searchPlayers(query: String): Result<List<SearchPlayer>> =
        apiCall(call = { sofaMiniApi.searchPlayers(query) })
}
