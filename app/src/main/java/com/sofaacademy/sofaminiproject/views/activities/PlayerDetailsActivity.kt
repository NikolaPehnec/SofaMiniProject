package com.sofaacademy.sofaminiproject.views.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import com.google.android.material.appbar.AppBarLayout
import com.sofaacademy.sofaminiproject.R
import com.sofaacademy.sofaminiproject.databinding.ActivityPlayerDetailBinding
import com.sofaacademy.sofaminiproject.model.Player
import com.sofaacademy.sofaminiproject.model.SportEvent
import com.sofaacademy.sofaminiproject.model.Team2
import com.sofaacademy.sofaminiproject.model.Tournament
import com.sofaacademy.sofaminiproject.utils.Constants
import com.sofaacademy.sofaminiproject.utils.UtilityFunctions.loadCountryFlag
import com.sofaacademy.sofaminiproject.utils.UtilityFunctions.loadPlayerImg
import com.sofaacademy.sofaminiproject.utils.UtilityFunctions.loadTeamImg
import com.sofaacademy.sofaminiproject.utils.helpers.EventHelpers.getPlayerFromIntent
import com.sofaacademy.sofaminiproject.utils.helpers.EventHelpers.getTeamFromIntent
import com.sofaacademy.sofaminiproject.utils.helpers.TeamHelpers.getPlayerPositionName
import com.sofaacademy.sofaminiproject.utils.listeners.OnEventClicked
import com.sofaacademy.sofaminiproject.utils.listeners.OnTournamentClicked
import com.sofaacademy.sofaminiproject.viewmodel.PlayerViewModel
import com.sofaacademy.sofaminiproject.viewmodel.TeamViewModel
import com.sofaacademy.sofaminiproject.views.adapters.arrayAdapters.EventPagingAdapter
import com.sofaacademy.sofaminiproject.views.adapters.headerAdapters.PlayerMatchesHeaderAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlayerDetailsActivity : AppCompatActivity(), OnTournamentClicked, OnEventClicked {

    private lateinit var binding: ActivityPlayerDetailBinding
    private lateinit var player: Player
    private var team: Team2? = null
    private val playerViewModel: PlayerViewModel by viewModels()
    private val teamViewModel: TeamViewModel by viewModels()
    private lateinit var pagingAdapter: EventPagingAdapter
    private lateinit var playerMatchesHeaderAdapter: PlayerMatchesHeaderAdapter

    companion object {
        fun start(player: Player, team2: Team2?, context: Context) {
            val intent = Intent(context, PlayerDetailsActivity::class.java).apply {
                putExtra(Constants.PLAYER_ARG, player)
                putExtra(Constants.TEAM_ID_ARG, team2)
            }
            ContextCompat.startActivity(context, intent, bundleOf())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlayerDetailBinding.inflate(layoutInflater)
        playerMatchesHeaderAdapter = PlayerMatchesHeaderAdapter(getString(R.string.matches_header))
        pagingAdapter = EventPagingAdapter(this, this)
        setSupportActionBar(binding.activityToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        setContentView(binding.root)

        player = getPlayerFromIntent(intent)
        team = getTeamFromIntent(intent)
        binding.playerContentLayout.eventsRv.adapter =
            ConcatAdapter(playerMatchesHeaderAdapter, pagingAdapter)

        loadHeaderData()
        setListeners()
    }

    private fun loadHeaderData() {
        binding.activityToolbar.title = ""
        binding.playerName.text = player.name
        binding.playerLogo.loadPlayerImg(player.id.toString())

        binding.playerContentLayout.apply {
            // Tim je null jer kod searcha player (SearchPlayer entitet) ne dolazi sa timom,
            // treba se dohvatit s apija
            team?.let {
                teamLogo.loadTeamImg(it.id.toString())
                teamName.text = it.name
            }
            nationalityItem.apply {
                attribute.text = getString(R.string.nationality)
                attributeValue.text = player.country!!.name.substring(0, 3)
                img.loadCountryFlag(player.country!!.name, this@PlayerDetailsActivity)
            }

            positionItem.apply {
                attribute.text = getString(R.string.position)
                attributeValue.text =
                    getPlayerPositionName(player.position.toString(), this@PlayerDetailsActivity)
                img.visibility = View.GONE
            }

            // Nema podataka na APIju?
            ageItem.apply {
                attribute.text = "9 Sep 1985"
                attributeValue.text = "37 Yrs"
                img.visibility = View.GONE
            }
        }

        binding.playerContentLayout.teamLayout.setOnClickListener {
            team?.let { it1 -> TeamDetailsActivity.start(it1, this) }
        }
    }

    private fun setListeners() {
        lifecycleScope.launch {
            playerViewModel.getAllPlayerEvents(player.id.toString())
                .observe(this@PlayerDetailsActivity) {
                    it?.let {
                        pagingAdapter.submitData(lifecycle, it)
                    }
                }
        }

        binding.appbarlayout.addOnOffsetChangedListener(object :
                AppBarLayout.OnOffsetChangedListener {
                var isShow: Boolean? = null
                var scrollRange: Int = -1

                override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                    if (scrollRange == -1) {
                        scrollRange = appBarLayout!!.totalScrollRange
                    }
                    if (scrollRange + verticalOffset <= 0) {
                        if (binding.activityToolbar.title != player.name) {
                            binding.activityToolbar.title = player.name
                        }
                        isShow = true
                    } else if (isShow == true) {
                        binding.activityToolbar.title = ""
                        isShow = false
                    }
                }
            })
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

    override fun onEventClicked(sportEvent: SportEvent) {
        EventDetailsActivity.start(sportEvent, this)
    }

    override fun onTournamentClicked(tournamet: Tournament) {
        TournamentDetailsActivity.start(tournamet, this)
    }
}
