package com.sofaacademy.sofaminiproject.views.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import com.google.android.material.tabs.TabLayoutMediator
import com.sofaacademy.sofaminiproject.databinding.ActivityLeaguesBinding
import com.sofaacademy.sofaminiproject.utils.Constants.SLUG_ARG
import com.sofaacademy.sofaminiproject.utils.UtilityFunctions.getSportsTabLayoutConfigStrategy
import com.sofaacademy.sofaminiproject.views.adapters.LeaguesPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LeaguesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLeaguesBinding

    companion object {
        fun start(slugIndex: Int, context: Context) {
            val intent = Intent(context, LeaguesActivity::class.java).apply {
                putExtra(SLUG_ARG, slugIndex)
            }
            ContextCompat.startActivity(context, intent, bundleOf())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLeaguesBinding.inflate(layoutInflater)
        setSupportActionBar(binding.activityToolbar)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val selectedTabIndex = intent.getIntExtra(SLUG_ARG, 0)
        val pagerAdapter = LeaguesPagerAdapter(this)
        binding.viewPager.adapter = pagerAdapter

        TabLayoutMediator(
            binding.tabLayout,
            binding.viewPager,
            getSportsTabLayoutConfigStrategy(this)
        ).attach()

        binding.tabLayout.post {
            binding.tabLayout.getTabAt(selectedTabIndex)?.select()
            binding.tabLayout.setScrollPosition(selectedTabIndex, 0f, true)
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
}
