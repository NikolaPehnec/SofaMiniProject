package com.sofaacademy.sofaminiproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sofaacademy.sofaminiproject.db.SofaDao
import com.sofaacademy.sofaminiproject.model.Player
import com.sofaacademy.sofaminiproject.model.Team2
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val sofaDao: SofaDao
) : ViewModel() {

    private val _favoriteTeamsPlayers = MutableLiveData<List<Any>>()
    val favoriteTeamsPlayers = _favoriteTeamsPlayers

    private val _favoritePlayers = MutableLiveData<List<Player>>()
    val favoritePlayers = _favoritePlayers

    private val _favoriteTeams = MutableLiveData<List<Team2>>()
    val favoriteTeams = _favoriteTeams

    fun getAllFavoriteItems() {
        viewModelScope.launch {
            _favoriteTeamsPlayers.postValue(sofaDao.getAllFavoritePlayers() + sofaDao.getAllFavoriteTeams())
        }
    }

    fun getAllFavoritePlayers() {
        viewModelScope.launch {
            _favoritePlayers.postValue(sofaDao.getAllFavoritePlayers())
        }
    }

    fun getAllFavoriteTeams() {
        viewModelScope.launch {
            _favoriteTeams.postValue(sofaDao.getAllFavoriteTeams())
        }
    }
}
