package com.sofaacademy.sofaminiproject.model

import java.io.Serializable

data class SearchPlayer(
    val id: Int,
    val name: String,
    val slug: String,
    val sport: Sport,
    val country: Country,
    val position: String
) : Serializable

fun SearchPlayer.toPlayer() = Player(this.id, this.name, this.slug, this.country, this.position)
