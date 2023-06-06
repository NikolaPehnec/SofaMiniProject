package com.sofaacademy.sofaminiproject.model

import androidx.room.ColumnInfo

data class Country(
    @ColumnInfo(name = "country_id")
    val id: Int,
    @ColumnInfo(name = "country_name")
    val name: String
) : java.io.Serializable
