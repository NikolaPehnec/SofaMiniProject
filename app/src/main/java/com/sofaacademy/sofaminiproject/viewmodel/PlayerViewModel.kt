package com.sofaacademy.sofaminiproject.viewmodel

import androidx.lifecycle.LiveData
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
import com.sofaacademy.sofaminiproject.networking.SofaMiniRepository
import com.sofaacademy.sofaminiproject.views.adapters.PlayerEventsPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val sofaMiniRepository: SofaMiniRepository,
    private val sofaDao: SofaDao
) :
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

    fun savePlayer(player: Player) {
        viewModelScope.launch {
            sofaDao.savePlayer(player)
        }
    }
}
