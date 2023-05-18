package com.sofaacademy.sofaminiproject.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sofaacademy.sofaminiproject.model.Player
import com.sofaacademy.sofaminiproject.model.Searched
import com.sofaacademy.sofaminiproject.model.Team2

@Database(
    entities = [Searched::class, Player::class, Team2::class],
    version = 1
)
abstract class SofaDB : RoomDatabase() {

    abstract fun sofaDao(): SofaDao
}
