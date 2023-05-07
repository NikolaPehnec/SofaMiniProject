package com.sofaacademy.sofaminiproject.views.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sofaacademy.sofaminiproject.utils.Constants.SLUG_AMERICAN_FOOTBALL
import com.sofaacademy.sofaminiproject.utils.Constants.SLUG_BASKETBALL
import com.sofaacademy.sofaminiproject.utils.Constants.SLUG_FOOTBALL
import com.sofaacademy.sofaminiproject.views.fragments.LeagueFragment

class LeaguesPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> LeagueFragment.newInstance(SLUG_FOOTBALL)
            1 -> LeagueFragment.newInstance(SLUG_BASKETBALL)
            2 -> LeagueFragment.newInstance(SLUG_AMERICAN_FOOTBALL)
            else -> LeagueFragment.newInstance(SLUG_FOOTBALL)
        }
    }
}
