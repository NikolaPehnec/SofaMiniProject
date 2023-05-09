package com.sofaacademy.sofaminiproject.model

import java.io.Serializable

data class Sport(
    val id: Int,
    val name: String,
    val slug: String
) : Serializable
