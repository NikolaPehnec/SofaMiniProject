package com.sofaacademy.sofaminiproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sofaacademy.sofaminiproject.model.Incident
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
            _sportEventsList.value = sofaMiniRepository.getSportEvents(slug, date)
        }
    }

    fun getEventDetails(eventId: String) {
        viewModelScope.launch {
            sofaMiniRepository.getEventDetails(eventId)?.let {
                _eventDetails.value = it
            }
        }
    }

    fun getEventIncidents(eventId: String) {
        viewModelScope.launch {
            _incidentsList.value = sofaMiniRepository.getEventIncidents(eventId)
                .map { networkIncident -> networkIncident.mapIncident()!! }
        }
    }
}
