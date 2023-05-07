package com.sofaacademy.sofaminiproject.networking

import com.sofaacademy.sofaminiproject.model.SportEvent
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
        @Path(value = "slug") slug: String,
    ): Response<List<Tournament>>

}