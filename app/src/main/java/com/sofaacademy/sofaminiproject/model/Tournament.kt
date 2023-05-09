package com.sofaacademy.sofaminiproject.model

import java.io.Serializable

data class Tournament(
    val id: Int,
    val name: String,
    val slug: String,
    val sport: Sport,
    val country: Country
) : Serializable
