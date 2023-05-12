package com.sofaacademy.sofaminiproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.sofaacademy.sofaminiproject.model.*
import com.sofaacademy.sofaminiproject.networking.SofaMiniRepository
import com.sofaacademy.sofaminiproject.utils.Constants.LAST
import com.sofaacademy.sofaminiproject.utils.Constants.NEXT
import com.sofaacademy.sofaminiproject.utils.helpers.EventHelpers.groupEventsByTournamentAndDate
import com.sofaacademy.sofaminiproject.utils.helpers.EventHelpers.sortedByDateDesc
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

    private val _teamEventsPaging = MutableLiveData<PagingData<Any>>()
    val teamEventsPaging = _teamEventsPaging

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

    /**
     * If firstFetch true, fetch one next and one previous page
     */
    fun getEvents(teamId: String, span: String, page: String, firstFetch: Boolean) {
        viewModelScope.launch {
            if (firstFetch) {
                val nextEventsDeff = async { sofaMiniRepository.getTeamEvents(teamId, NEXT, "0") }
                val previousEventsDeff =
                    async { sofaMiniRepository.getTeamEvents(teamId, LAST, "0") }
                val nextEvents = nextEventsDeff.await()
                val previousEvents = previousEventsDeff.await()

                if (nextEvents is Result.Success && previousEvents is Result.Success) {
                    _teamEvents.value = nextEvents.data + previousEvents.data
                } else {
                    _teamEventsError.value =
                        if (nextEvents is Result.Error) {
                            nextEvents.exception.toString()
                        } else {
                            (previousEvents as Result.Error).exception.toString()
                        }
                }
            } else {
                when (val result = sofaMiniRepository.getTeamEvents(teamId, span, page)) {
                    is Result.Success ->
                        _teamEvents.value = result.data

                    is Result.Error ->
                        _teamEventsError.value = result.exception.toString()
                }
            }
        }
    }

    fun getAllTeamEvents(teamId: String): LiveData<PagingData<Any>> {
        return getEventsPaging(teamId).cachedIn(viewModelScope)
    }

    fun getEventsPaging(teamId: String): LiveData<PagingData<Any>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                initialLoadSize = 1
            ),
            pagingSourceFactory = {
                EventPagingSource(teamId)
            }, initialKey = 1
        ).liveData
    }

    inner class EventPagingSource(val teamId: String) : PagingSource<Int, Any>() {
        override fun getRefreshKey(state: PagingState<Int, Any>): Int? {
            return state.anchorPosition?.let { anchorPosition ->
                state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                    ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
            }
        }

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Any> {
            var page = params.key ?: 0
            val initialKey = params.key ?: 0

            val result =
                if (page <= 0) {
                    page *= -1
                    sofaMiniRepository.getTeamEvents(
                        teamId,
                        NEXT,
                        page.toString()
                    )
                } else {
                    page -= 1
                    sofaMiniRepository.getTeamEvents(teamId, LAST, page.toString())
                }

            if (result is Result.Success) {
                val groupedData = groupEventsByTournamentAndDate(result.data.sortedByDateDesc())
                return LoadResult.Page(
                    data = groupedData,
                    prevKey = initialKey - 1,
                    nextKey = initialKey + 1
                )
            } else {
                return LoadResult.Error(Throwable("Err"))
            }
        }

    }


}
