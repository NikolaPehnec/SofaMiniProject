package com.sofaacademy.sofaminiproject.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sofaacademy.sofaminiproject.utils.Constants
import java.io.Serializable

@Entity(tableName = "team")
data class Team2(
    @PrimaryKey
    val id: Int,
    val name: String,
    @Embedded
    val country: Country,
    val managerName: String?,
    val venue: String?,
    // Iz searcha
    @Embedded
    val sport: Sport?,
    @ColumnInfo(name = "item_type")
    val itemType: Int = Constants.TYPE_TEAM
) : Serializable
