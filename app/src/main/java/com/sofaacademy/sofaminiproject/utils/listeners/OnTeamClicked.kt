package com.sofaacademy.sofaminiproject.utils.listeners

interface OnTeamClicked {
    fun onTeamHomeClicked()
    fun onTeamAwayClicked()
    fun onTeamClicked(teamId: Int)
}
