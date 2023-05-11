package com.sofaacademy.sofaminiproject.networking

import com.sofaacademy.sofaminiproject.model.NetworkIncident
import com.sofaacademy.sofaminiproject.model.Player
import com.sofaacademy.sofaminiproject.model.SportEvent
import com.sofaacademy.sofaminiproject.model.Team2
import com.sofaacademy.sofaminiproject.model.Tournament
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SofaMiniApi {

    @GET("sport/{slug}/events/{date}")
    suspend fun getSportEvents(
        @Path(value = "slug") slug: String,
        @Path(value = "date") date: String
    ): Response<List<SportEvent>>

    @GET("sport/{slug}/tournaments")
    suspend fun getTournaments(
        @Path(value = "slug") slug: String
    ): Response<List<Tournament>>

    @GET("event/{id}")
    suspend fun getEventDetail(
        @Path(value = "id") id: String
    ): Response<SportEvent>

    @GET("event/{id}/incidents")
    suspend fun getEventIncidents(
        @Path(value = "id") id: String
    ): Response<List<NetworkIncident>>

    @GET("team/{id}")
    suspend fun getTeamDetails(@Path(value = "id") id: String): Response<Team2>

    @GET("team/{id}/players")
    suspend fun getTeamPlayers(@Path(value = "id") id: String): Response<List<Player>>

    @GET("team/{id}/events/{span}/{page}")
    suspend fun getTeamEvents(
        @Path(value = "id") id: String,
        @Path(value = "span") span: String,
        @Path(value = "page") page: String
    ): Response<List<SportEvent>>

    @GET("team/{id}/tournaments")
    suspend fun getTeamTournaments(
        @Path(value = "id") id: String
    ): Response<List<Tournament>>


}
