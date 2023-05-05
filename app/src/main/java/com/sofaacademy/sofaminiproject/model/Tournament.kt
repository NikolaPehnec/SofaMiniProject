package com.sofaacademy.sofaminiproject.model

data class Tournament(
    val id:Int,
    val name:String,
    val slug:String,
    val sport: Sport,
    val country:Country,
) {
}