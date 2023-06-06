package com.sofaacademy.sofaminiproject.utils.listeners

import com.sofaacademy.sofaminiproject.model.Tournament

interface OnTournamentClicked {
    fun onTournamentClicked(tournament: Tournament)
}
