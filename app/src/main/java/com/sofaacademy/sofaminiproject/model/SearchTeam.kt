package com.sofaacademy.sofaminiproject.model

import java.io.Serializable

data class SearchTeam(
    val id: Int,
    val name: String,
    val sport: Sport,
    val country: Country
) : Serializable

fun SearchTeam.toTeam2(): Team2 = Team2(this.id, this.name, this.country, null, null)
