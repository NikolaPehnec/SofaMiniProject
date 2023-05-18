package com.sofaacademy.sofaminiproject.views.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import com.sofaacademy.sofaminiproject.R
import com.sofaacademy.sofaminiproject.databinding.ActivityMainBinding
import com.sofaacademy.sofaminiproject.utils.Constants
import com.sofaacademy.sofaminiproject.utils.UtilityFunctions.getSportsTabLayoutConfigStrategy
import com.sofaacademy.sofaminiproject.utils.UtilityFunctions.getTabTitlesByDate
import com.sofaacademy.sofaminiproject.utils.UtilityFunctions.getThemePreferences
import com.sofaacademy.sofaminiproject.views.adapters.pagerAdapters.SportPagerAdapter
import com.sofaacademy.sofaminiproject.views.fragments.SportFragment
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var dateTabTitles = mutableMapOf<LocalDate, String>()
    private var dateTabs = mutableMapOf<LocalDate, TabLayout.Tab>()
    private var initialDateSelected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadTheme()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setSupportActionBar(binding.activityToolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        setContentView(binding.root)
        val pagerAdapter = SportPagerAdapter(this)
        binding.viewPager.adapter = pagerAdapter

        dateTabTitles.putAll(getTabTitlesByDate(this))
        TabLayoutMediator(
            binding.tabLayout,
            binding.viewPager,
            getSportsTabLayoutConfigStrategy(this)
        ).attach()

        for (date in dateTabTitles.toSortedMap().keys) {
            dateTabs[date] = binding.datesTabLayout.newTab().setText(dateTabTitles[date])
            binding.datesTabLayout.addTab(dateTabs[date]!!)
        }

        binding.datesTabLayout.post {
            dateTabs[LocalDate.now()]!!.select()
            binding.datesTabLayout.setScrollPosition(
                binding.datesTabLayout.selectedTabPosition,
                0f,
                true
            )
            initialDateSelected = true
        }

        binding.datesTabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    val selectedDate = dateTabs.filterValues { it.text == tab.text }.keys.first()
                    for (fragment in supportFragmentManager.fragments) {
                        if (fragment is SportFragment) {
                            fragment.reloadSportData(selectedDate)
                        }
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    fun getCurrentDate(): LocalDate {
        binding.datesTabLayout.let {
            // Ako nije zavrsio scroll do danasnjeg datuma, kod pokretanja je moguci load podataka za krivi datumn
            return when (initialDateSelected) {
                true -> {
                    val selectedTab = it.getTabAt(it.selectedTabPosition)
                    dateTabs.filterValues { tab -> tab.text == selectedTab?.text }.keys.first()
                }

                false -> LocalDate.now()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.leagues -> {
                LeaguesActivity.start(binding.tabLayout.selectedTabPosition, this)
                true
            }

            R.id.settings -> {
                SettingsActivity.start(this)
                true
            }

            R.id.search -> {
                SearchActivity.start(this)
                true
            }

            R.id.favorite -> {
                FavoriteActivity.start(this)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun loadTheme() {
        when (getThemePreferences(this)) {
            Constants.NIGHT_THEME -> {
                if (AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
            }

            Constants.LIGHT_THEME -> {
                if (AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_NO) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }
    }
}
