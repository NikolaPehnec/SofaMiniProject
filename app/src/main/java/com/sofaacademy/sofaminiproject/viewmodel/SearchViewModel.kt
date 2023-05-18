package com.sofaacademy.sofaminiproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sofaacademy.sofaminiproject.db.SofaDao
import com.sofaacademy.sofaminiproject.model.Player
import com.sofaacademy.sofaminiproject.model.Searched
import com.sofaacademy.sofaminiproject.model.Team2
import com.sofaacademy.sofaminiproject.networking.SofaMiniRepository
import com.sofaacademy.sofaminiproject.utils.Constants.TYPE_PLAYER
import com.sofaacademy.sofaminiproject.utils.Constants.TYPE_TEAM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val sofaMiniRepository: SofaMiniRepository,
    private val sofaDao: SofaDao
) : ViewModel() {

    private val _searchTeamsPlayers = MutableLiveData<List<Any>>()
    val searchTeamPlayers = _searchTeamsPlayers

    private val _searchedTeamsPlayersDB = MutableLiveData<List<Any>>()
    val searchedTeamsPlayersDB = _searchedTeamsPlayersDB

    fun searchTeamsPlayers(query: String) {
        viewModelScope.launch {
            val teamsDeff = async { sofaMiniRepository.searchTeams(query) }
            val playersDeff = async { sofaMiniRepository.searchPlayers(query) }
            val searchedTeams = teamsDeff.await()
            val searchedPlayers = playersDeff.await()

            _searchTeamsPlayers.value = searchedTeams + searchedPlayers
        }
    }

    fun getAllSearchedItems() {
        viewModelScope.launch {
            val a = sofaDao.getAllSearchedTeams()
            val b = sofaDao.getAllSearchedPlayers()
            _searchedTeamsPlayersDB.postValue(a + b)
        }
    }

    fun saveSearchedPlayer(player: Player) {
        viewModelScope.launch {
            try {
                sofaDao.saveSearchedPlayer(player)
                sofaDao.saveSearched(Searched(player.id, TYPE_PLAYER))
            } catch (e: Exception) {
                println()
            }
        }
    }

    fun saveSearchedTeam(team: Team2) {
        viewModelScope.launch {
            try {
                sofaDao.saveSearchedTeam(team)
                sofaDao.saveSearched(Searched(team.id, TYPE_TEAM))
            } catch (e: Exception) {
                println()
            }
        }
    }
}
