package com.sofaacademy.sofaminiproject.networking

import com.sofaacademy.sofaminiproject.model.SportEvent
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SofaMiniApi {

    @GET("sport/{slug}/events/{date}")
     fun getSportEvent(
        @Path(value = "slug") slug: String,
        @Path(value = "date") date: String
    ): Call<List<SportEvent>>
}