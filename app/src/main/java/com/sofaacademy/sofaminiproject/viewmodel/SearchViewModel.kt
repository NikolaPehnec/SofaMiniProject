package com.sofaacademy.sofaminiproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sofaacademy.sofaminiproject.networking.SofaMiniRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val sofaMiniRepository: SofaMiniRepository) :
    ViewModel() {

    private val _searchTeamsPlayers = MutableLiveData<List<Any>>()
    val searchTeamPlayers = _searchTeamsPlayers

    fun searchTeamsPlayers(query: String) {
        viewModelScope.launch {
            val teamsDeff = async { sofaMiniRepository.searchTeams(query) }
            val playersDeff = async { sofaMiniRepository.searchPlayers(query) }
            val searchedTeams = teamsDeff.await()
            val searchedPlayers = playersDeff.await()

            _searchTeamsPlayers.value = searchedTeams + searchedPlayers
        }
    }
}
