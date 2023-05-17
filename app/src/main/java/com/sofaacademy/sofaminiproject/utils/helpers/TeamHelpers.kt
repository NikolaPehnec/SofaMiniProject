package com.sofaacademy.sofaminiproject.utils.helpers

import android.content.Context
import com.sofaacademy.sofaminiproject.R

object TeamHelpers {

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
