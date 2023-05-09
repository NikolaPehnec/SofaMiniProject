package com.sofaacademy.sofaminiproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sofaacademy.sofaminiproject.model.Result
import com.sofaacademy.sofaminiproject.model.Tournament
import com.sofaacademy.sofaminiproject.networking.SofaMiniRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TournamentsViewModel @Inject constructor(private val sofaMiniRepository: SofaMiniRepository) :
    ViewModel() {

    private val _tournamentsList = MutableLiveData<List<Tournament>>()
    val tournamentsList = _tournamentsList

    private val _tournamentsResponseError = MutableLiveData<String>()
    val tournamentsResponseError: LiveData<String> = _tournamentsResponseError

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
