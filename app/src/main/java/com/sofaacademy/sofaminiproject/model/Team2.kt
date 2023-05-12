package com.sofaacademy.sofaminiproject.model

import java.io.Serializable

data class Team2(
    val id: Int,
    val name: String,
    val country: Country,
    val managerName: String?,
    val venue: String?
) : Serializable

data class Team3(
    val id: Int,
    val name: String,
    val country: Country
) : Serializable
