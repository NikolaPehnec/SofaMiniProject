package com.sofaacademy.sofaminiproject.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "searched", primaryKeys = ["searched_id", "item_type"])
class Searched(
    @ColumnInfo(name = "searched_id")
    val searchedId: Int,
    @ColumnInfo(name = "item_type")
    val itemType: Int
)
