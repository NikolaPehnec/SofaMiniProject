package com.sofaacademy.sofaminiproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sofaacademy.sofaminiproject.model.Result
import com.sofaacademy.sofaminiproject.model.SportEvent
import com.sofaacademy.sofaminiproject.model.Tournament
import com.sofaacademy.sofaminiproject.networking.SofaMiniRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SportEventViewModel @Inject constructor(private val sofaMiniRepository: SofaMiniRepository) :
    ViewModel() {
    private val _sportEventsList = MutableLiveData<List<SportEvent>>()
    val sportEventsList = _sportEventsList

    private val _tournamentsList = MutableLiveData<List<Tournament>>()
    val tournamentsList = _tournamentsList

    private val _sportEventsResponseError = MutableLiveData<String>()
    val sportEventsResponseError: LiveData<String> = _sportEventsResponseError

    private val _tournamentsResponseError = MutableLiveData<String>()
    val tournamentsResponseError: LiveData<String> = _tournamentsResponseError

    fun getSportEvents(slug: String, date: String) {
        viewModelScope.launch {
            when (val result = sofaMiniRepository.getSportEvents(slug, date)) {
                is Result.Success ->
                    _sportEventsList.value = result.data

                is Result.Error ->
                    _sportEventsResponseError.value =
                        result.exception.toString()
            }
        }
    }

    fun getTournaments(slug: String) {
        viewModelScope.launch {
            when (val result = sofaMiniRepository.getTournaments(slug)) {
                is Result.Success ->
                    _tournamentsList.value = result.data

                is Result.Error ->
                    _tournamentsResponseError.value =
                        result.exception.toString()
            }
        }
    }
}
