package com.sofaacademy.sofaminiproject.model

import java.io.Serializable

data class Player(
    val id: Int,
    val name: String?,
    val slug: String?,
    val country: Country?,
    val position: String?
): Serializable
