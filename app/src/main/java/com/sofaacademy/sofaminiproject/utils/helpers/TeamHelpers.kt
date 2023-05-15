package com.sofaacademy.sofaminiproject.utils.helpers

import android.content.Context
import com.sofaacademy.sofaminiproject.R
import com.sofaacademy.sofaminiproject.model.Team2
import com.sofaacademy.sofaminiproject.model.Team3

object TeamHelpers {

    fun Team3.mapToTeam2(): Team2 {
        return Team2(this.id, this.name, this.country, null, null)
    }

    fun getPlayerPositionName(shortName: String, context: Context): String {
        return when (shortName) {
            "M" -> context.getString(R.string.midfield)
            "D" -> context.getString(R.string.defence)
            "G" -> context.getString(R.string.goalkeeper)
            "F" -> context.getString(R.string.forward)
            else -> context.getString(R.string.midfield)
        }
    }
}
