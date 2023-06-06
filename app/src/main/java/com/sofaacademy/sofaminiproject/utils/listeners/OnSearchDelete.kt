package com.sofaacademy.sofaminiproject.utils.listeners

import com.sofaacademy.sofaminiproject.model.Player
import com.sofaacademy.sofaminiproject.model.Team2

interface OnSearchDelete {
    fun onPlayerDeleteClicked(player: Player)
    fun onTeamDeleteClicked(team: Team2)
}
