package com.sofaacademy.sofaminiproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import com.sofaacademy.sofaminiproject.model.*
import com.sofaacademy.sofaminiproject.model.SportEvent
import com.sofaacademy.sofaminiproject.networking.SofaMiniRepository
import com.sofaacademy.sofaminiproject.utils.Constants.NEXT
import com.sofaacademy.sofaminiproject.views.adapters.TeamEventsPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.map
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

    private val _teamPlayers = MutableLiveData<List<Player>>()
    val teamPlayers = _teamPlayers

    private val _teamTournaments = MutableLiveData<List<Tournament>>()
    val teamTournaments = _teamTournaments

    private val _teamEvents = MutableLiveData<List<SportEvent>>()
    val teamEvents = _teamEvents

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
                is Result.Error -> _nextTeamEvents.value = emptyList()
            }
            when (resPlayers) {
                is Result.Success -> _teamPlayers.value = resPlayers.data
                is Result.Error -> _teamPlayers.value = emptyList()
            }
            when (resTournaments) {
                is Result.Success -> _teamTournaments.value = resTournaments.data
                is Result.Error -> _teamTournaments.value = emptyList()
            }
        }
    }

    fun getAllTeamEvents(teamId: String): LiveData<PagingData<Any>> {
        return getEventsPaging(teamId).cachedIn(viewModelScope)
    }

    private fun getEventsPaging(teamId: String): LiveData<PagingData<Any>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                initialLoadSize = 1
            ),
            pagingSourceFactory = {
                TeamEventsPagingSource(sofaMiniRepository, teamId)
            },
            initialKey = 1
        ).flow.map { pagingData: PagingData<Any> ->
            pagingData.insertSeparators { before: Any?, after: Any? ->
                if ((before as SportEvent?)?.tournament?.id.toString() !=
                    (after as SportEvent?)?.tournament?.id.toString()
                ) {
                    after?.let {
                        return@insertSeparators after.tournament
                    }
                } else {
                    null
                }
            }
        }.asLiveData()
    }
}
