package com.sofaacademy.sofaminiproject.utils.listeners

import com.sofaacademy.sofaminiproject.model.SportEvent

interface OnEventClicked {
    fun onEventClicked(sportEvent: SportEvent)
}
