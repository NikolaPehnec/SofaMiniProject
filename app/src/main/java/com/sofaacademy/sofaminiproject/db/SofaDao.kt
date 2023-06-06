package com.sofaacademy.sofaminiproject.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
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
    suspend fun savePlayer(player: Player)

    // If player is already inserted through favorites, dont overwrite
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPlayer(player: Player)

    @Upsert
    suspend fun saveTeam(team: Team2)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTeam(team: Team2)

    @Upsert
    suspend fun saveSearched(searched: Searched)

    @Query("SELECT P.* FROM searched S INNER JOIN player P ON S.searched_id=P.id WHERE S.item_type=$TYPE_PLAYER")
    suspend fun getAllSearchedPlayers(): List<Player>

    @Query("SELECT T.* FROM searched S INNER JOIN team T ON S.searched_id=T.id WHERE S.item_type=$TYPE_TEAM")
    suspend fun getAllSearchedTeams(): List<Team2>

    @Query("DELETE FROM searched  WHERE searched_id=:id AND item_type=:itemType")
    suspend fun deleteFromSearched(id: Int, itemType: String)

    @Query("SELECT * FROM player P WHERE P.favorite=1")
    suspend fun getAllFavoritePlayers(): List<Player>

    @Query("SELECT * FROM team T WHERE T.favorite=1")
    suspend fun getAllFavoriteTeams(): List<Team2>
}
