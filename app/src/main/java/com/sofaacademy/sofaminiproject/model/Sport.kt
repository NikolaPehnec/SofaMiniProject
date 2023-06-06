package com.sofaacademy.sofaminiproject.model

import androidx.room.ColumnInfo
import java.io.Serializable

data class Sport(
    @ColumnInfo(name = "sport_id")
    val id: Int,
    @ColumnInfo("sport_name")
    val name: String,
    @ColumnInfo(name = "sport_slug")
    val slug: String
) : Serializable
