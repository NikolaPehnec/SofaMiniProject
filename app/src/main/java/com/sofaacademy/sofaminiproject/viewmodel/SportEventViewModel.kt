package com.sofaacademy.sofaminiproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sofaacademy.sofaminiproject.model.Incident
import com.sofaacademy.sofaminiproject.model.Result
import com.sofaacademy.sofaminiproject.model.SportEvent
import com.sofaacademy.sofaminiproject.networking.SofaMiniRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SportEventViewModel @Inject constructor(private val sofaMiniRepository: SofaMiniRepository) :
    ViewModel() {
    private val _sportEventsList = MutableLiveData<List<SportEvent>>()
    val sportEventsList = _sportEventsList

    private val _eventDetails = MutableLiveData<SportEvent>()
    val eventDetails = _eventDetails

    private val _incidentsList = MutableLiveData<List<Incident>>()
    val incidentsList = _incidentsList

    fun getSportEvents(slug: String, date: String) {
        viewModelScope.launch {
            when (val result = sofaMiniRepository.getSportEvents(slug, date)) {
                is Result.Success ->
                    _sportEventsList.value = result.data

                is Result.Error ->
                    _sportEventsList.value = emptyList()
            }
        }
    }

    fun getEventDetails(eventId: String) {
        viewModelScope.launch {
            when (val result = sofaMiniRepository.getEventDetails(eventId)) {
                is Result.Success ->
                    _eventDetails.value = result.data

                is Result.Error -> _sportEventsList.value = emptyList()
            }
        }
    }

    fun getEventIncidents(eventId: String) {
        viewModelScope.launch {
            when (val result = sofaMiniRepository.getEventIncidents(eventId)) {
                is Result.Success ->
                    _incidentsList.value =
                        result.data.map { networkIncident -> networkIncident.mapIncident()!! }

                is Result.Error -> _sportEventsList.value = emptyList()
            }
        }
    }
}
