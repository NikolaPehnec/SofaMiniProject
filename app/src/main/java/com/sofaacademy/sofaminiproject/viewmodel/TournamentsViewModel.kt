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
import com.sofaacademy.sofaminiproject.model.Result
import com.sofaacademy.sofaminiproject.model.SportEvent
import com.sofaacademy.sofaminiproject.model.Tournament
import com.sofaacademy.sofaminiproject.model.TournamentStandings
import com.sofaacademy.sofaminiproject.networking.SofaMiniRepository
import com.sofaacademy.sofaminiproject.views.adapters.TournamentEventsPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TournamentsViewModel @Inject constructor(private val sofaMiniRepository: SofaMiniRepository) :
    ViewModel() {

    private val _tournamentsList = MutableLiveData<List<Tournament>>()
    val tournamentsList = _tournamentsList

    private val _tournamentStandings = MutableLiveData<List<TournamentStandings>>()
    val tournamentStandings = _tournamentStandings

    fun getTournaments(slug: String) {
        viewModelScope.launch {
            when (val result = sofaMiniRepository.getTournaments(slug)) {
                is Result.Success ->
                    _tournamentsList.value = result.data

                is Result.Error ->
                    _tournamentsList.value = emptyList()
            }
        }
    }

    fun getTournamentStandings(tournamentId: String) {
        viewModelScope.launch {
            when (val result = sofaMiniRepository.getTournamentStandings(tournamentId)) {
                is Result.Success -> _tournamentStandings.value = result.data
                is Result.Error -> _tournamentStandings.value = emptyList()
            }
        }
    }

    fun getAllTournamentEvents(tournamentId: String): LiveData<PagingData<Any>> {
        return getEventsPaging(tournamentId).cachedIn(viewModelScope)
    }

    private fun getEventsPaging(tournamentId: String): LiveData<PagingData<Any>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                initialLoadSize = 1
            ),
            pagingSourceFactory = {
                TournamentEventsPagingSource(sofaMiniRepository, tournamentId)
            },
            initialKey = 1
        ).flow.map { pagingData: PagingData<Any> ->
            pagingData
                .insertSeparators { before: Any?, after: Any? ->
                    if ((before as SportEvent?)?.round.toString()
                        != (after as SportEvent?)?.round.toString()
                    ) {
                        after?.let {
                            return@insertSeparators after.round
                        }
                    } else {
                        null
                    }
                }
        }.asLiveData()
    }
}
