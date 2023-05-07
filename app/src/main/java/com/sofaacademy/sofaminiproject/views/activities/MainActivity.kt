package com.sofaacademy.sofaminiproject.views.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import com.sofaacademy.sofaminiproject.R
import com.sofaacademy.sofaminiproject.databinding.ActivityMainBinding
import com.sofaacademy.sofaminiproject.utils.Constants.MAX_DAYS
import com.sofaacademy.sofaminiproject.utils.Constants.MIN_DAYS
import com.sofaacademy.sofaminiproject.utils.UtilityFunctions.tabDateFormat
import com.sofaacademy.sofaminiproject.views.adapters.ScreenSlidePagerAdapter
import com.sofaacademy.sofaminiproject.views.fragments.SportFragment
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val dateTabs = mutableMapOf<LocalDate, TabLayout.Tab>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setSupportActionBar(binding.activityToolbar)
        setContentView(binding.root)
        val pagerAdapter = ScreenSlidePagerAdapter(this)
        binding.viewPager.adapter = pagerAdapter

        for (i in MIN_DAYS..MAX_DAYS) {
            val date = LocalDate.now().plusDays(i.toLong())
            var day =
                if (date == LocalDate.now()) resources.getString(R.string.today) else tabDateFormat.format(
                    date
                ).uppercase().split(" ")[0]

            val dateStr = day + "\n" + tabDateFormat.format(date).split(" ")[1]
            dateTabs[date] = binding.datesTabLayout.newTab().setText(dateStr)
        }

        TabLayoutMediator(binding.tabLayout,
            binding.viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                var icons = arrayOf(
                    R.drawable.icon_football,
                    R.drawable.icon_basketball,
                    R.drawable.icon_american_football
                )
                tab.icon = AppCompatResources.getDrawable(baseContext, icons[position])
                tab.setText(
                    resources.getStringArray(R.array.tabs)[position]
                )
            }).attach()

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

        for (tab in dateTabs.toSortedMap().values) binding.datesTabLayout.addTab(tab)

        binding.datesTabLayout.post {
            dateTabs[LocalDate.now()]!!.select()
            binding.datesTabLayout.setScrollPosition(
                binding.datesTabLayout.selectedTabPosition, 0f, true
            )
        }
    }

    fun getCurrentDate(): LocalDate {
        binding.datesTabLayout.let {
            val selectedTab = it.getTabAt(it.selectedTabPosition)
            return dateTabs.filterValues { tab -> tab.text == selectedTab?.text }.keys.first()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.leagues -> {
                // Handle menu item 1 click
                true
            }
            R.id.settings -> {
                // Handle menu item 2 click
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}