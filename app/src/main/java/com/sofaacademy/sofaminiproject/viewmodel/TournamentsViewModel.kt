package com.sofaacademy.sofaminiproject.viewmodel

import androidx.lifecycle.*
import androidx.paging.*
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
            _tournamentsList.value = sofaMiniRepository.getTournaments(slug)
        }
    }

    fun getTournamentStandings(tournamentId: String) {
        viewModelScope.launch {
            _tournamentStandings.value = sofaMiniRepository.getTournamentStandings(tournamentId)
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
