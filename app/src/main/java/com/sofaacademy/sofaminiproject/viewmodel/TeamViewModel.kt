package com.sofaacademy.sofaminiproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sofaacademy.sofaminiproject.model.Player
import com.sofaacademy.sofaminiproject.model.Result
import com.sofaacademy.sofaminiproject.model.SportEvent
import com.sofaacademy.sofaminiproject.model.Team2
import com.sofaacademy.sofaminiproject.model.Tournament
import com.sofaacademy.sofaminiproject.networking.SofaMiniRepository
import com.sofaacademy.sofaminiproject.utils.Constants.NEXT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamViewModel @Inject constructor(private val sofaMiniRepository: SofaMiniRepository) :
    ViewModel() {

    private val _teamDetails = MutableLiveData<Team2>()
    val teamDetails = _teamDetails

    private val _teamDetailsError = MutableLiveData<String>()
    val teamDetailsErrorr: LiveData<String> = _teamDetailsError

    private val _nextTeamEvents = MutableLiveData<List<SportEvent>>()
    val nextTeamEvents = _nextTeamEvents

    private val _nextTeamEventsError = MutableLiveData<String>()
    val nextTeamEventsError: LiveData<String> = _nextTeamEventsError

    private val _teamPlayers = MutableLiveData<List<Player>>()
    val teamPlayers = _teamPlayers

    private val _teamPlayersError = MutableLiveData<String>()
    val teamPlayersError: LiveData<String> = _teamPlayersError

    private val _teamTournaments = MutableLiveData<List<Tournament>>()
    val teamTournaments = _teamTournaments

    private val _teamTournamentsError = MutableLiveData<String>()
    val teamTournamentsError: LiveData<String> = _teamTournamentsError

    private val _teamEvents = MutableLiveData<List<SportEvent>>()
    val teamEvents = _teamEvents

    private val _teamEventsError = MutableLiveData<String>()
    val teamEventsError: LiveData<String> = _teamEventsError

    fun getAllTeamDetails(teamId: String) {
        viewModelScope.launch {
            val detailsDeff = async { sofaMiniRepository.getTeamDetails(teamId) }
            val eventsDeff = async { sofaMiniRepository.getTeamEvents(teamId, NEXT, "0") }
            val playersDeff = async { sofaMiniRepository.getTeamPlayers(teamId) }
            val tournamentsDeff = async { sofaMiniRepository.getTeamTournaments(teamId) }

            val resDetails = detailsDeff.await()
            val resEvents = eventsDeff.await()
            val resPlayers = playersDeff.await()
            val resTournaments = tournamentsDeff.await()
            when (resDetails) {
                is Result.Success -> _teamDetails.value = resDetails.data
                is Result.Error -> _teamDetailsError.value = resDetails.exception.toString()
            }
            when (resEvents) {
                is Result.Success -> _nextTeamEvents.value = resEvents.data
                is Result.Error -> _nextTeamEventsError.value = resEvents.exception.toString()
            }
            when (resPlayers) {
                is Result.Success -> _teamPlayers.value = resPlayers.data
                is Result.Error -> _teamPlayersError.value = resPlayers.exception.toString()
            }
            when (resTournaments) {
                is Result.Success -> _teamTournaments.value = resTournaments.data
                is Result.Error -> _teamTournamentsError.value = resTournaments.exception.toString()
            }
        }
    }

    fun getEvents(teamId: String, span: String, page: String) {
        viewModelScope.launch {
            when (val result = sofaMiniRepository.getTeamEvents(teamId, span, page)) {
                is Result.Success ->
                    _teamEvents.value = result.data

                is Result.Error ->
                    _teamEventsError.value = result.exception.toString()
            }
        }
    }
}
