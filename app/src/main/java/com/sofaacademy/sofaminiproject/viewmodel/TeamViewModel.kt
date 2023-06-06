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
import com.sofaacademy.sofaminiproject.db.SofaDao
import com.sofaacademy.sofaminiproject.model.Player
import com.sofaacademy.sofaminiproject.model.SportEvent
import com.sofaacademy.sofaminiproject.model.Team2
import com.sofaacademy.sofaminiproject.model.Tournament
import com.sofaacademy.sofaminiproject.networking.SofaMiniRepository
import com.sofaacademy.sofaminiproject.utils.Constants
import com.sofaacademy.sofaminiproject.utils.Constants.NEXT
import com.sofaacademy.sofaminiproject.views.adapters.SportEventsPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamViewModel @Inject constructor(
    private val sofaMiniRepository: SofaMiniRepository,
    private val sofaDao: SofaDao
) : ViewModel() {

    private val _teamDetails = MutableLiveData<Team2>()
    val teamDetails = _teamDetails

    private val _nextTeamEvents = MutableLiveData<List<SportEvent>>()
    val nextTeamEvents = _nextTeamEvents

    private val _teamPlayers = MutableLiveData<List<Player>>()
    val teamPlayers = _teamPlayers

    private val _teamTournaments = MutableLiveData<List<Tournament>>()
    val teamTournaments = _teamTournaments

    fun getAllTeamDetails(teamId: String) {
        viewModelScope.launch {
            val detailsDeff = async { sofaMiniRepository.getTeamDetails(teamId) }
            val eventsDeff = async { sofaMiniRepository.getTeamEvents(teamId, NEXT, "0") }
            val playersDeff = async { sofaMiniRepository.getTeamPlayers(teamId) }
            val tournamentsDeff = async { sofaMiniRepository.getTeamTournaments(teamId) }
            val teamDetails = detailsDeff.await()
            val teamEvents = eventsDeff.await()
            val teamPlayers = playersDeff.await()
            val teamTournaments = tournamentsDeff.await()

            teamDetails?.let {
                _teamDetails.value = it
            }
            _nextTeamEvents.value = teamEvents
            _teamPlayers.value = teamPlayers
            _teamTournaments.value = teamTournaments
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
                SportEventsPagingSource(
                    fetchNextEvents = { page ->
                        sofaMiniRepository.getTeamEvents(
                            teamId,
                            NEXT,
                            page
                        )
                    },
                    fetchLastEvents = { page ->
                        sofaMiniRepository.getTeamEvents(
                            teamId,
                            Constants.LAST,
                            page
                        )
                    }
                )
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

    fun saveTeam(team: Team2) {
        viewModelScope.launch {
            sofaDao.saveTeam(team)
        }
    }
}
