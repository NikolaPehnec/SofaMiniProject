package com.sofaacademy.sofaminiproject.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sofaacademy.sofaminiproject.utils.Constants
import java.io.Serializable

@Entity(tableName = "player")
data class Player(
    @PrimaryKey
    val id: Int,
    val name: String?,
    val slug: String?,
    @Embedded
    val country: Country?,
    val position: String?,
    // Iz searcha
    @Embedded
    val sport: Sport?,
    @ColumnInfo(name = "item_type")
    val itemType: Int = Constants.TYPE_PLAYER
) : Serializable
