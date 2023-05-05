package com.sofaacademy.sofaminiproject.model

data class SportEvent(
    val id:Int,
    val slug:String,
    val tournament:Tournament,
    val homeTeam:Team2,
    val awayTeam:Team2,
    val status:String,
    val startDate:String?,
    val homeScore:Score,
    val awayScore:Score,
) {
}