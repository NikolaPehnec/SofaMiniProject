package com.sofaacademy.sofaminiproject.views.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.ConcatAdapter
import com.sofaacademy.sofaminiproject.R
import com.sofaacademy.sofaminiproject.databinding.ActivityFavoriteBinding
import com.sofaacademy.sofaminiproject.model.Player
import com.sofaacademy.sofaminiproject.model.Team2
import com.sofaacademy.sofaminiproject.utils.Constants
import com.sofaacademy.sofaminiproject.utils.listeners.OnPlayerClicked
import com.sofaacademy.sofaminiproject.utils.listeners.OnSearchDelete
import com.sofaacademy.sofaminiproject.utils.listeners.OnTeamClicked
import com.sofaacademy.sofaminiproject.viewmodel.FavoriteViewModel
import com.sofaacademy.sofaminiproject.viewmodel.PlayerViewModel
import com.sofaacademy.sofaminiproject.viewmodel.TeamViewModel
import com.sofaacademy.sofaminiproject.views.adapters.arrayAdapters.SearchArrayAdapter
import com.sofaacademy.sofaminiproject.views.adapters.headerAdapters.SquadHeaderAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteActivity : AppCompatActivity(), OnTeamClicked, OnPlayerClicked, OnSearchDelete {

    private lateinit var binding: ActivityFavoriteBinding
    private val teamViewModel: TeamViewModel by viewModels()
    private val playerViewModel: PlayerViewModel by viewModels()
    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private lateinit var searchAdapterDB: SearchArrayAdapter
    private lateinit var searchedHeaderAdapter: SquadHeaderAdapter

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, FavoriteActivity::class.java)
            ContextCompat.startActivity(context, intent, bundleOf())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setContentView(binding.root)

        searchAdapterDB = SearchArrayAdapter(mutableListOf(), this, this, this, true, this)
        searchedHeaderAdapter = SquadHeaderAdapter(getString(R.string.favorites))
        binding.favoriteRv.adapter = ConcatAdapter(searchedHeaderAdapter, searchAdapterDB)
        setListeners()

        favoriteViewModel.getAllFavoriteItems()
    }

    // Favorites update on back pressed
    override fun onResume() {
        favoriteViewModel.getAllFavoriteItems()
        super.onResume()
    }

    private fun setListeners() {
        favoriteViewModel.favoriteTeamsPlayers.observe(this) {
            searchAdapterDB.setItems(it)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPlayerClicked(player: Player) {
        PlayerDetailsActivity.start(player, null, this)
    }

    override fun onTeamClicked(team: Team2) {
        TeamDetailsActivity.start(team, this)
    }

    override fun onPlayerDeleteClicked(player: Player) {
        player.favorite = false
        playerViewModel.savePlayer(player)
        searchAdapterDB.deleteItem(player.id, Constants.TYPE_PLAYER)
    }

    override fun onTeamDeleteClicked(team: Team2) {
        team.favorite = false
        teamViewModel.saveTeam(team)
        searchAdapterDB.deleteItem(team.id, Constants.TYPE_TEAM)
    }
}
