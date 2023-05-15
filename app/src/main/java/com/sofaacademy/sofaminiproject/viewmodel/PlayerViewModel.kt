package com.sofaacademy.sofaminiproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.sofaacademy.sofaminiproject.networking.SofaMiniRepository
import com.sofaacademy.sofaminiproject.views.adapters.PlayerEventsPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(private val sofaMiniRepository: SofaMiniRepository) :
    ViewModel() {

    fun getAllPlayerEvents(playerId: String): LiveData<PagingData<Any>> {
        return getEventsPaging(playerId).cachedIn(viewModelScope)
    }

    private fun getEventsPaging(playerId: String): LiveData<PagingData<Any>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                initialLoadSize = 1
            ),
            pagingSourceFactory = {
                PlayerEventsPagingSource(sofaMiniRepository, playerId)
            },
            initialKey = 0
        ).liveData
    }
}
