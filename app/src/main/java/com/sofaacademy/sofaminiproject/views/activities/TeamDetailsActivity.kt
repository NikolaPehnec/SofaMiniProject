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
import com.sofaacademy.sofaminiproject.databinding.ActivityTeamDetailBinding
import com.sofaacademy.sofaminiproject.model.Team2
import com.sofaacademy.sofaminiproject.utils.Constants
import com.sofaacademy.sofaminiproject.utils.UtilityFunctions.getTeamDetailsTabLayoutConfigStrategy
import com.sofaacademy.sofaminiproject.utils.helpers.EventHelpers.getTeamFromIntent
import com.sofaacademy.sofaminiproject.utils.helpers.FlagHelper
import com.sofaacademy.sofaminiproject.views.adapters.pagerAdapters.TeamDetailsPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTeamDetailBinding
    private lateinit var team: Team2

    companion object {
        fun start(team: Team2, context: Context) {
            val intent = Intent(context, TeamDetailsActivity::class.java).apply {
                putExtra(Constants.TEAM_ID_ARG, team)
            }
            ContextCompat.startActivity(context, intent, bundleOf())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTeamDetailBinding.inflate(layoutInflater)
        setSupportActionBar(binding.activityToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        setContentView(binding.root)

        team = getTeamFromIntent(intent)
        loadHeaderData()

        val pagerAdapter = TeamDetailsPagerAdapter(team, this)
        binding.viewPager.adapter = pagerAdapter
        TabLayoutMediator(
            binding.tabLayout,
            binding.viewPager,
            getTeamDetailsTabLayoutConfigStrategy(this)
        ).attach()

        setListeners()
    }

    private fun loadHeaderData() {
        binding.activityToolbar.title = ""
        binding.teamName.text = team.name
        binding.countryName.text = team.country.name
        binding.teamLogo.load(
            "${Constants.BASE_TEAM_URL}${team.id}${Constants.IMG_ENDPOINT}"
        )
        binding.countryLogo.load(
            FlagHelper.getFlagBitmap(this, team.country.name)
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
                    if (binding.activityToolbar.title != team.name) {
                        binding.activityToolbar.title = team.name
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
