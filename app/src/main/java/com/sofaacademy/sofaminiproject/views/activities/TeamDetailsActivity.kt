package com.sofaacademy.sofaminiproject.views.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import com.google.android.material.tabs.TabLayoutMediator
import com.sofaacademy.sofaminiproject.databinding.ActivityTeamDetailBinding
import com.sofaacademy.sofaminiproject.utils.Constants
import com.sofaacademy.sofaminiproject.utils.UtilityFunctions.getTeamDetailsTabLayoutConfigStrategy
import com.sofaacademy.sofaminiproject.views.adapters.TeamDetailsPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTeamDetailBinding
    private var teamId: Int? = null

    companion object {
        fun start(teamId: Int, context: Context) {
            val intent = Intent(context, TeamDetailsActivity::class.java).apply {
                putExtra(Constants.TEAM_ID_ARG, teamId)
            }
            ContextCompat.startActivity(context, intent, bundleOf())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTeamDetailBinding.inflate(layoutInflater)
        setSupportActionBar(binding.activityToolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        setContentView(binding.root)

        teamId = intent.getIntExtra(Constants.TEAM_ID_ARG, 0)

        val pagerAdapter = TeamDetailsPagerAdapter(teamId!!, this)
        binding.viewPager.adapter = pagerAdapter

        TabLayoutMediator(
            binding.tabLayout,
            binding.viewPager,
            getTeamDetailsTabLayoutConfigStrategy(this)
        ).attach()
    }


}
