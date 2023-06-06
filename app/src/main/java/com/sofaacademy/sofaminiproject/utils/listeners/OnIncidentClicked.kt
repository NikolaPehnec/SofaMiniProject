package com.sofaacademy.sofaminiproject.utils.listeners

import com.sofaacademy.sofaminiproject.model.Player
import com.sofaacademy.sofaminiproject.model.Team2

interface OnIncidentClicked {
    fun onIncidentClicked(player: Player, team2: Team2)
}
