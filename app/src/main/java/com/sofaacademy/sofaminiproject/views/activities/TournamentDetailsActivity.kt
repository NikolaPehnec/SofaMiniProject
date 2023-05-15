package com.sofaacademy.sofaminiproject.views.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import coil.load
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.sofaacademy.sofaminiproject.databinding.ActivityTournamentDetailBinding
import com.sofaacademy.sofaminiproject.model.Tournament
import com.sofaacademy.sofaminiproject.utils.Constants
import com.sofaacademy.sofaminiproject.utils.UtilityFunctions.getTournamentDetailsTabLayoutConfigStrategy
import com.sofaacademy.sofaminiproject.utils.helpers.EventHelpers.getTournamentFromIntent
import com.sofaacademy.sofaminiproject.utils.helpers.FlagHelper
import com.sofaacademy.sofaminiproject.views.adapters.pagerAdapters.TournamentDetailsPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TournamentDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTournamentDetailBinding
    private lateinit var tournament: Tournament

    companion object {
        fun start(tournament: Tournament, context: Context) {
            val intent = Intent(context, TournamentDetailsActivity::class.java).apply {
                putExtra(Constants.TOURNAMENT_ARG, tournament)
            }
            ContextCompat.startActivity(context, intent, bundleOf())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTournamentDetailBinding.inflate(layoutInflater)
        setSupportActionBar(binding.activityToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        setContentView(binding.root)

        tournament = getTournamentFromIntent(intent)
        loadHeaderData()

        val pagerAdapter = TournamentDetailsPagerAdapter(tournament, this)
        binding.viewPager.adapter = pagerAdapter
        TabLayoutMediator(
            binding.tabLayout,
            binding.viewPager,
            getTournamentDetailsTabLayoutConfigStrategy(this)
        ).attach()

        setListeners()
    }

    private fun loadHeaderData() {
        binding.activityToolbar.title = ""
        binding.tournamentName.text = tournament.name
        binding.countryName.text = tournament.country.name
        binding.tournamentLogo.load(
            "${Constants.BASE_TOURNAMENT_URL}${tournament.id}${Constants.IMG_ENDPOINT}"
        )
        binding.countryLogo.load(
            FlagHelper.getFlagBitmap(this, tournament.country.name)
        )
    }

    private fun setListeners() {
        binding.appbarlayout.addOnOffsetChangedListener(object :
                AppBarLayout.OnOffsetChangedListener {
                var isShow: Boolean? = null
                var scrollRange: Int = -1

                override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                    if (scrollRange == -1) {
                        scrollRange = appBarLayout!!.totalScrollRange
                    }
                    if (scrollRange + verticalOffset <= 0) {
                        if (binding.activityToolbar.title != tournament.name) {
                            binding.activityToolbar.title = tournament.name
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
}
