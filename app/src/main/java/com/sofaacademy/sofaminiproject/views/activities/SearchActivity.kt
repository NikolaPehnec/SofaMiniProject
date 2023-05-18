package com.sofaacademy.sofaminiproject.views.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.ConcatAdapter
import com.sofaacademy.sofaminiproject.R
import com.sofaacademy.sofaminiproject.databinding.ActivitySearchBinding
import com.sofaacademy.sofaminiproject.model.Player
import com.sofaacademy.sofaminiproject.model.Team2
import com.sofaacademy.sofaminiproject.utils.Constants
import com.sofaacademy.sofaminiproject.utils.Constants.SEARCH_TRESHOLD
import com.sofaacademy.sofaminiproject.utils.listeners.OnPlayerClicked
import com.sofaacademy.sofaminiproject.utils.listeners.OnSearchDelete
import com.sofaacademy.sofaminiproject.utils.listeners.OnTeamClicked
import com.sofaacademy.sofaminiproject.viewmodel.SearchViewModel
import com.sofaacademy.sofaminiproject.views.adapters.arrayAdapters.SearchArrayAdapter
import com.sofaacademy.sofaminiproject.views.adapters.headerAdapters.SquadHeaderAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity(), OnTeamClicked, OnPlayerClicked, OnSearchDelete {

    private lateinit var binding: ActivitySearchBinding
    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var searchAdapter: SearchArrayAdapter
    private lateinit var searchAdapterDB: SearchArrayAdapter
    private lateinit var searchedHeaderAdapter: SquadHeaderAdapter

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, SearchActivity::class.java)
            ContextCompat.startActivity(context, intent, bundleOf())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setContentView(binding.root)

        searchAdapter = SearchArrayAdapter(mutableListOf(), this, this, this, false, this)
        searchAdapterDB = SearchArrayAdapter(mutableListOf(), this, this, this, true, this)
        searchedHeaderAdapter = SquadHeaderAdapter(getString(R.string.recent_searches))
        binding.searchRv.adapter = searchAdapter
        binding.searchedDBRv.adapter = ConcatAdapter(searchedHeaderAdapter, searchAdapterDB)
        setListeners()

        searchViewModel.getAllSearchedItems()
        binding.autoCompleteTv.requestFocus()
    }

    private fun setListeners() {
        searchViewModel.searchTeamPlayers.observe(this) {
            searchAdapter.setItems(it)
            binding.searchedDBRv.visibility = View.GONE
            binding.searchRv.visibility = View.VISIBLE
        }

        searchViewModel.searchedTeamsPlayersDB.observe(this) {
            searchAdapterDB.setItems(it)
            binding.searchedDBRv.visibility = View.VISIBLE
            binding.searchRv.visibility = View.GONE
        }

        binding.autoCompleteTv.apply {
            setCompoundDrawablesWithIntrinsicBounds(
                ContextCompat.getDrawable(
                    this@SearchActivity,
                    R.drawable.ic_baseline_search_24
                ),
                null,
                null,
                null
            )

            addTextChangedListener {
                setCompoundDrawablesWithIntrinsicBounds(
                    ContextCompat.getDrawable(
                        this@SearchActivity,
                        R.drawable.ic_baseline_search_24
                    ),
                    null,
                    if (it.toString().isEmpty()) {
                        null
                    } else {
                        ContextCompat.getDrawable(
                            this@SearchActivity,
                            R.drawable.ic_baseline_close_24
                        )
                    },
                    null
                )

                if (it.toString().length >= SEARCH_TRESHOLD) {
                    searchViewModel.searchTeamsPlayers(it.toString())
                } else {
                    searchViewModel.getAllSearchedItems()
                }
            }

            // Search ili brisanje teksta ovisno o kliku na koji drawable
            setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    val drawableRightIndex = 2
                    if (event?.action == MotionEvent.ACTION_UP) {
                        binding.autoCompleteTv.compoundDrawables[drawableRightIndex]?.let {
                            if (event.rawX >= (binding.autoCompleteTv.right - it.bounds.width() + 100)) {
                                binding.autoCompleteTv.setText("")
                                return true
                            }
                        }
                    }
                    return false
                }
            })
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
        searchViewModel.saveSearchedPlayer(player)
        PlayerDetailsActivity.start(player, null, this)
    }

    override fun onTeamClicked(team: Team2) {
        searchViewModel.saveSearchedTeam(team)
        TeamDetailsActivity.start(team, this)
    }

    override fun onPlayerDeleteClicked(player: Player) {
        searchViewModel.removeItemFromSearches(player.id, Constants.TYPE_PLAYER.toString())
        searchAdapterDB.deleteItem(player.id, Constants.TYPE_PLAYER)
    }

    override fun onTeamDeleteClicked(team: Team2) {
        searchViewModel.removeItemFromSearches(team.id, Constants.TYPE_TEAM.toString())
        searchAdapterDB.deleteItem(team.id, Constants.TYPE_TEAM)
    }
}
