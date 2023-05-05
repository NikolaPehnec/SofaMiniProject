package com.sofaacademy.sofaminiproject.views.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.google.android.material.tabs.TabLayoutMediator
import com.sofaacademy.sofaminiproject.R
import com.sofaacademy.sofaminiproject.adapters.ScreenSlidePagerAdapter
import com.sofaacademy.sofaminiproject.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pagerAdapter = ScreenSlidePagerAdapter(this)
        binding.viewPager.adapter = pagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                var icons = arrayOf(
                    R.drawable.icon_football,
                    R.drawable.icon_basketball,
                    R.drawable.icon_american_football
                )
                tab.icon = AppCompatResources.getDrawable(baseContext,icons[position])
                tab.text = resources.getStringArray(R.array.tabs)[position]

                /*  val customTabViewBinding:CustomTabViewBinding =
                    CustomTabViewBinding.inflate(LayoutInflater.from(baseContext))
                customTabViewBinding.textView.text = resources.getStringArray(R.array.tabs)[position]
                customTabViewBinding.icon.setImageDrawable(getDrawable(icons[position]))
                tab.customView=customTabViewBinding.root
            }).attach()*/
            }).attach()
    }
}