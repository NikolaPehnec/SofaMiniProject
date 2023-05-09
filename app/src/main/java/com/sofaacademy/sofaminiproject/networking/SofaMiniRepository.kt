package com.sofaacademy.sofaminiproject.networking

import android.content.Context
import com.sofaacademy.sofaminiproject.model.NetworkIncident
import com.sofaacademy.sofaminiproject.model.Result
import com.sofaacademy.sofaminiproject.model.SportEvent
import com.sofaacademy.sofaminiproject.model.Tournament
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
}
