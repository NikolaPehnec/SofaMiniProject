package com.sofaacademy.sofaminiproject.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.sofaacademy.sofaminiproject.model.Player
import com.sofaacademy.sofaminiproject.model.Searched
import com.sofaacademy.sofaminiproject.model.Team2
import com.sofaacademy.sofaminiproject.utils.Constants.TYPE_PLAYER
import com.sofaacademy.sofaminiproject.utils.Constants.TYPE_TEAM

@Dao
interface SofaDao {
    @Upsert
    suspend fun saveSearchedPlayer(player: Player)

    @Upsert
    suspend fun saveSearchedTeam(team2: Team2)

    @Upsert
    suspend fun saveSearched(searched: Searched)

    @Query("SELECT P.* FROM searched S INNER JOIN player P ON S.searched_id=P.id WHERE S.item_type=$TYPE_PLAYER")
    suspend fun getAllSearchedPlayers(): List<Player>

    @Query("SELECT T.* FROM searched S INNER JOIN team T ON S.searched_id=T.id WHERE S.item_type=$TYPE_TEAM")
    suspend fun getAllSearchedTeams(): List<Team2>
}
