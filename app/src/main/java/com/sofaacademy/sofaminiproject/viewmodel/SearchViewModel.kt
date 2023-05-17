package com.sofaacademy.sofaminiproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sofaacademy.sofaminiproject.model.Result
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
            val teamsRes = teamsDeff.await()
            val playersRes = playersDeff.await()

            val searchResult = mutableListOf<Any>()
            when (teamsRes) {
                is Result.Success -> searchResult.addAll(teamsRes.data)
                is Result.Error -> {}
            }
            when (playersRes) {
                is Result.Success -> searchResult.addAll(playersRes.data)
                is Result.Error -> {}
            }

            _searchTeamsPlayers.value = searchResult
        }
    }
}
